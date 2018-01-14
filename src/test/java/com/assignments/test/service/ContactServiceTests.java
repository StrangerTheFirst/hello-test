package com.assignments.test.service;

import com.assignments.domain.Contact;
import com.assignments.exception.UnprocessableEntityException;
import com.assignments.repository.ContactRepository;
import com.assignments.service.ContactService;
import com.assignments.test.HelloControllerTestApplicationTests;
import com.assignments.web.dto.ContactDto;
import com.assignments.web.dto.ContactListDto;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class ContactServiceTests extends HelloControllerTestApplicationTests {
    @Autowired
    private ContactRepository contactRepository;

    @Autowired
    private ContactService contactService;

    @Before
    @After
    public void setUp() {
        contactRepository.deleteAll();
    }

    @Test
    public void shouldFilterContacts() throws UnprocessableEntityException {
        List<Contact> contactsMock = Arrays.asList(
          new Contact("Ivan Ivan"),
          new Contact("John Doe"),
          new Contact("Jack Daniels"),
          new Contact("Bob Dylan"),
          new Contact("Adam Smith"),
          new Contact("Bill Sharp"),
          new Contact("Bill Murrey"),
          new Contact("Barry B"),
          new Contact("Bono")
        );

        contactRepository.save(contactsMock);

        // Page 1
        ContactListDto contactListDto = contactService.findByNameRegex("^B.*", 0, 2);
        assertEquals(5, contactListDto.getTotalPages());
        assertEquals(9, contactListDto.getTotalElements());

        List<ContactDto> contacts = contactListDto.getContacts();
        assertEquals("Barry B", contacts.get(0).getName());

        // Page 2
        contactListDto = contactService.findByNameRegex("^B.*", 1, 2);
        assertEquals(5, contactListDto.getTotalPages());
        assertEquals(9, contactListDto.getTotalElements());

        contacts = contactListDto.getContacts();
        assertEquals("Bill Murrey", contacts.get(0).getName());
        assertEquals("Bill Sharp", contacts.get(1).getName());

        // Page 3
        contactListDto = contactService.findByNameRegex("^B.*", 2, 2);
        assertEquals(5, contactListDto.getTotalPages());
        assertEquals(9, contactListDto.getTotalElements());

        contacts = contactListDto.getContacts();
        assertEquals("Bob Dylan", contacts.get(0).getName());
        assertEquals("Bono", contacts.get(1).getName());

        // Page 3 (alternative)
        contactListDto = contactService.findByNameRegex("^B.*", 2, 3);
        assertEquals(3, contactListDto.getTotalPages());
        assertEquals(9, contactListDto.getTotalElements());

        contacts = contactListDto.getContacts();
        assertEquals(0, contacts.size());
    }

    @Test(expected = UnprocessableEntityException.class)
    public void shouldHandleInvalidFilter() throws UnprocessableEntityException {
        List<Contact> contactsMock = Arrays.asList(
                new Contact("Ivan Ivan"),
                new Contact("John Doe")
        );

        contactRepository.save(contactsMock);
        contactService.findByNameRegex("[[", 0, 2);
    }
}
