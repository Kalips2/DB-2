package com.example.db2.controllers;

import com.example.db2.services.UserNoSqlService;
import com.example.db2.services.UserSqlService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.repository.Update;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UserController {
  @Autowired
  private UserSqlService userService;
  @Autowired
  private UserNoSqlService userNoSqlServiceService;

  @PostMapping("/create/{amount}")
  public ResponseEntity<String> createUsers(@PathVariable("amount") int amount) {
    long time = userService.createUsers(amount);
    return ResponseEntity.ok("[POSTGRES] Users created successfully. Elapsed time - " + time + " ms");
  }

  @DeleteMapping("/deleteAll")
  public ResponseEntity<String> deleteAllUsers() {
    long time = userService.deleteAllUsers();
    return ResponseEntity.ok("[POSTGRES] All users deleted successfully. Elapsed time - " + time + " ms");
  }

  @PutMapping("/updateAll")
  public ResponseEntity<String> updateAllUsers() {
    long time = userService.addRestrictionsToUsers();
    return ResponseEntity.ok("[POSTGRES] All users updated successfully. Elapsed time - " + time + " ms");
  }

  @PostMapping("/nosql/create/{amount}")
  public ResponseEntity<String> createNoUsers(@PathVariable("amount") int amount) {
    long time = userNoSqlServiceService.createUsers(amount);
    return ResponseEntity.ok("[MONGO] Users created successfully. Elapsed time - " + time + " ms");
  }

  @DeleteMapping("/nosql/deleteAll")
  public ResponseEntity<String> deleteNoAllUsers() {
    long time = userNoSqlServiceService.deleteAllUsers();
    return ResponseEntity.ok("[MONGO] All users deleted successfully. Elapsed time - " + time + " ms");
  }

  @PutMapping("/nosql/updateAll")
  public ResponseEntity<String> updateNoAllUsers() {
    long time = userNoSqlServiceService.addRestrictionsToUsers();
    return ResponseEntity.ok("[MONGO] All users updated successfully. Elapsed time - " + time + " ms");
  }
}
