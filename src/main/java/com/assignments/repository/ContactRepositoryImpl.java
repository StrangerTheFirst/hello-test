package com.assignments.repository;

import com.assignments.domain.Contact;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.EntityManager;
import java.util.List;

public class ContactRepositoryImpl implements ContactRepositoryCust {

    private final EntityManager entityManager;

    @Autowired
    public ContactRepositoryImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public List<Contact> findAll(int offset, int limit) {
        return (List<Contact>) entityManager
                .createQuery("FROM Contact C ORDER BY C.name ASC")
                .setFirstResult(offset)
                .setMaxResults(limit)
                .getResultList();
    }

}
