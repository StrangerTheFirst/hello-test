package com.assignments.admin.db;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class DatabaseInitializer {

    private final List<TableInitializer> tableInitializerList;

    @Autowired
    public DatabaseInitializer(List<TableInitializer> tableInitializerList) {
        this.tableInitializerList = tableInitializerList;
    }

    @Transactional
    public void fillDatabase() {
        tableInitializerList.forEach(initializer -> initializer.fill());
    }

    @Transactional
    public void dropDatabase() {
        tableInitializerList.forEach(initializer -> initializer.drop());
    }
}
