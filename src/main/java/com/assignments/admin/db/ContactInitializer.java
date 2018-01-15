package com.assignments.admin.db;

import com.assignments.domain.Contact;
import com.assignments.repository.ContactRepository;
import org.fluttercode.datafactory.impl.DataFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class ContactInitializer implements TableInitializer {
    private final static int ROW_COUNT = 100;

    private final ContactRepository contactRepository;

    @Autowired
    public ContactInitializer(ContactRepository contactRepository) {
        this.contactRepository = contactRepository;
    }

    @Override
    public void fill() {
        fill(ROW_COUNT);
    }

    @Override
    public void fill(int rowCount) {
        if(contactRepository.count() == 0) {
            DataFactory df = new DataFactory();

            for (int i = 0; i < rowCount; i++) {
                String name = String.format("%s: %s", df.getBusinessName(), UUID.randomUUID());
                contactRepository.save(new Contact(name));
            }
        }
    }

    @Override
    public void drop() {
        contactRepository.deleteAll();
    }
}
