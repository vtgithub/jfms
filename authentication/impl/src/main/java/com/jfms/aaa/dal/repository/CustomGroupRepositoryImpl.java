package com.jfms.aaa.dal.repository;

import com.jfms.aaa.dal.entity.GroupEntity;
import com.mongodb.client.MongoCollection;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;

public class CustomGroupRepositoryImpl implements CustomGroupRepository {
//    @Autowired
//    private MongoTemplate mongoTemplate;
//
//    @Override
//    public void update(GroupEntity groupEntity) {
//        MongoCollection<Document> doc = mongoTemplate.getCollection("group_entity");
//        doc.updateOne(basicDBObject,
//                new Document("$set", Document.parse(DaoHelper.getEntityJson(newEntity)))
//        );
//
//    }
}
