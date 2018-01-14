package com.assignments.admin.db;

public interface TableInitializer {
    void fill();
    void fill(int rowCount);
    void drop();
}
