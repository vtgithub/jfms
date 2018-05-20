package com.jfms.aaa.dal.repository;

import com.google.gson.Gson;
import com.jfms.aaa.dal.entity.ChannelEntity;
import com.mongodb.BasicDBObject;
import com.mongodb.client.MongoCollection;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;

//@Repository
public class CustomChannelRepositoryImpl implements CustomChannelRepository {
    @Autowired
    private MongoTemplate mongoTemplate;
    Gson gson = new Gson();
    @Override
    public void update(ChannelEntity channelEntity) {
        MongoCollection<Document> doc = mongoTemplate.getCollection("channel_entity");
        BasicDBObject basicDBObject = new BasicDBObject();
        basicDBObject.append("_id", channelEntity.getId());
        basicDBObject.append("_class", ChannelEntity.class.getName());
        channelEntity.setId(null);
        String channelEntityInJson = gson.toJson(channelEntity);
        doc.updateOne(basicDBObject,
                new Document("$set", Document.parse(channelEntityInJson))
        );

    }
}
