package com.example.db2.services;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Random;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

@Service
public class UserSqlService {

  @Autowired
  private JdbcTemplate jdbcTemplate;

  public long deleteAllUsers() {
    long startTime = System.currentTimeMillis();
    jdbcTemplate.update("DELETE FROM users");
    long endTime = System.currentTimeMillis();
    return endTime - startTime;
  }

  public long createUsers(int numberOfUsers) {
    long startTime = System.currentTimeMillis();
    Random random = new Random();

    for (int i = 0; i < numberOfUsers; i++) {
      jdbcTemplate.update(
          "INSERT INTO users (\"Role\", \"FirstName\", \"LastName\", \"Gender\", \"DateOfBirth\", \"BloodType\", \"ContactNumber\", \"Email\") " +
              "VALUES (?, ?, ?, ?, ?, ?, ?, ?)",
          "Role", "FirstName", "LastName", "Gender", LocalDate.now(), 1, "ContactNumber", "Email");

      int userId =
          jdbcTemplate.queryForObject("SELECT last_value FROM users_id_seq", Integer.class);

      // Додавання 1 рандомного favorite_center
      int randomCenterId =
          random.nextInt(15) + 101; // Генеруємо випадковий ідентифікатор від 101 до 115
      jdbcTemplate.update(
          "INSERT INTO favorite_centers (\"UserID\", \"BloodCenterId\") VALUES (?, ?)", userId,
          randomCenterId);

      // Додавання 2 документів в user_documents
      for (int docId = 1; docId <= 2; docId++) {
        jdbcTemplate.update(
            "INSERT INTO user_documents (\"UserID\", \"Description\", \"File\") VALUES (?, ?, ?)",
            userId, "Document " + docId, "file" + docId + ".txt");
      }

      jdbcTemplate.update("INSERT INTO user_restrictions (\"UserID\", \"Reason\", \"ExpiryDate\") VALUES (?, ?, ?)", userId, "Some reason", LocalDate.now().plusMonths(1));
    }

    long endTime = System.currentTimeMillis();
    return endTime - startTime;
  }

  public long addRestrictionsToUsers() {
    long startTime = System.currentTimeMillis();

    List<Integer> userIds = jdbcTemplate.queryForList("SELECT id FROM users", Integer.class);

    // Додати нові рестрикції для кожного користувача
    for (Integer userId : userIds) {
      String sql = "INSERT INTO user_restrictions (\"UserID\", \"Reason\", \"ExpiryDate\") " +
          "VALUES (?, ?, ?)";
      jdbcTemplate.update(sql, userId, "New reason", LocalDate.now().plusMonths(1));
    }
    long endTime = System.currentTimeMillis();
    return endTime - startTime;
  }
}
