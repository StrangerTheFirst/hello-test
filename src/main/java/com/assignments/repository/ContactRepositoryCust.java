package com.assignments.repository;

import com.assignments.domain.Contact;

import java.util.List;

public interface ContactRepositoryCust {
    List<Contact> findAll(int offset, int limit);
}
