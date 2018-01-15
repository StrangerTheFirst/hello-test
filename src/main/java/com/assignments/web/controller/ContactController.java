package com.assignments.web.controller;

import com.assignments.exception.UnprocessableEntityException;
import com.assignments.service.ContactService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/hello/contacts")
public class ContactController {
    private final static int DEFAULT_LIMIT = 100;
    private final static int MAX_LIMIT = 1000;

    private final ContactService contactService;

    @Autowired
    public ContactController(ContactService contactService) {
        this.contactService = contactService;
    }

    @GetMapping
    public ResponseEntity<?> getContacts(
            @RequestParam("nameFilter") String nameFilter,
            @RequestParam("offset") Integer offset,
            @RequestParam(value = "limit", required = false) Integer limit) throws UnprocessableEntityException {

        if (offset < 0) {
            offset = 0;
        }

        if (limit == null || limit <= 0 || limit > MAX_LIMIT) {
            limit = DEFAULT_LIMIT;
        }

        return ResponseEntity.ok(
            contactService.findByNameRegex(nameFilter, offset, limit));
    }
}
