package com.jfms.aaa.dal.repository;

import com.google.gson.Gson;
import com.jfms.aaa.dal.entity.GroupEntity;
import com.mongodb.BasicDBObject;
import com.mongodb.client.MongoCollection;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.json.GsonJsonParser;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Repository;

//@Repository
public class CustomGroupRepositoryImpl implements CustomGroupRepository {
    @Autowired
    private MongoTemplate mongoTemplate;
    Gson gson = new Gson();
    @Override
    public void update(GroupEntity groupEntity) {
        MongoCollection<Document> doc = mongoTemplate.getCollection("group_entity");
        BasicDBObject basicDBObject = new BasicDBObject();
        basicDBObject.append("_id", groupEntity.getId());
        basicDBObject.append("_class", GroupEntity.class.getName());
        groupEntity.setId(null);
        String groupEntityInJson = gson.toJson(groupEntity);
        doc.updateOne(basicDBObject,
                new Document("$set", Document.parse(groupEntityInJson))
        );

    }
}
