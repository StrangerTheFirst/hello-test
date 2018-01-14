package com.assignments.admin;

import com.assignments.admin.db.DatabaseInitializer;
import com.assignments.exception.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin")
public class AdminController {
    private final DatabaseInitializer databaseInitializer;

    @Autowired
    public AdminController(DatabaseInitializer databaseInitializer) {
        this.databaseInitializer = databaseInitializer;
    }

    @PostMapping("db/init")
    public ResponseEntity<?> initDatabase() {
        databaseInitializer.fillDatabase();

        return ResponseEntity.ok(
                ApiResponse.success("Database initialized"));
    }

    @PostMapping("db/drop")
    public ResponseEntity<?> dropDatabase() {
        databaseInitializer.dropDatabase();

        return ResponseEntity.ok(
                ApiResponse.success("Database dropped"));
    }
}
