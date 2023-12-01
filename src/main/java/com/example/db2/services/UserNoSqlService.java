package com.example.db2.services;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

@Service
public class UserNoSqlService {
  @Autowired
  private MongoTemplate mongoTemplate;

  public long createUsers(int numberOfUsers) {
    long startTime = System.currentTimeMillis();
    Random random = new Random();

    for (int i = 0; i < numberOfUsers; i++) {
      // Додавання користувача з вкладеними даними
      Map<String, Object> userDocument = new HashMap<>();
      userDocument.put("Role", "Role");
      userDocument.put("FirstName", "FirstName");
      userDocument.put("LastName", "LastName");
      userDocument.put("Gender", "Gender");
      userDocument.put("DateOfBirth", LocalDate.now().toString());
      userDocument.put("BloodType", 1);
      userDocument.put("ContactNumber", "ContactNumber");
      userDocument.put("Email", "Email");

      List<Map<String, Object>> favoriteCenters = new ArrayList<>();
      List<Map<String, Object>> userDocuments = new ArrayList<>();
      List<Map<String, Object>> userRestrictions = new ArrayList<>();

      int randomCenterId = random.nextInt(15) + 101;
      Map<String, Object> favoriteCenter = new HashMap<>();
      favoriteCenter.put("BloodCenterId", randomCenterId);
      favoriteCenters.add(favoriteCenter);

      for (int docId = 1; docId <= 2; docId++) {
        Map<String, Object> userDocumentItem = new HashMap<>();
        userDocumentItem.put("Description", "Document " + docId);
        userDocumentItem.put("File", "file" + docId + ".txt");
        userDocuments.add(userDocumentItem);
      }

      Map<String, Object> userRestriction = new HashMap<>();
      userRestriction.put("Reason", "Some reason");
      userRestriction.put("ExpiryDate", LocalDate.now().plusMonths(1).toString());
      userRestrictions.add(userRestriction);

      userDocument.put("FavoriteCenters", favoriteCenters);
      userDocument.put("UserDocuments", userDocuments);
      userDocument.put("UserRestrictions", userRestrictions);

      mongoTemplate.save(userDocument, "users");
    }
    long endTime = System.currentTimeMillis();
    return endTime - startTime;
  }

  public long deleteAllUsers() {
    long startTime = System.currentTimeMillis();
    mongoTemplate.remove(new org.springframework.data.mongodb.core.query.Query(), "users");
    long endTime = System.currentTimeMillis();
    return endTime - startTime;
  }

  public long addRestrictionsToUsers() {
    long startTime = System.currentTimeMillis();

    Map<String, Object> newRestriction = Map.of(
        "Reason", "New reason",
        "ExpiryDate", LocalDate.now().plusMonths(1).toString()
    );

    Query query = new Query();
    Update update = new Update().push("UserRestrictions", newRestriction);
    mongoTemplate.updateMulti(query, update, "users");
    long endTime = System.currentTimeMillis();
    return endTime - startTime;
  }

}
