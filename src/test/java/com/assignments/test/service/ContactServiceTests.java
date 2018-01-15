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

        ContactListDto contactListDto = contactService.findByNameRegex("^B.*$", 0, 2);
        assertEquals(7, contactListDto.getOffset());

        List<ContactDto> contacts = contactListDto.getContacts();
        assertEquals(2, contacts.size());
        assertEquals("Adam Smith", contacts.get(0).getName());
        assertEquals("Ivan Ivan", contacts.get(1).getName());

        contactListDto = contactService.findByNameRegex("^B.*$", 7, 1);
        assertEquals(8, contactListDto.getOffset());

        contacts = contactListDto.getContacts();
        assertEquals(1, contacts.size());
        assertEquals("Jack Daniels", contacts.get(0).getName());

        contactListDto = contactService.findByNameRegex("^B.*$", 8, 10);
        assertEquals(9, contactListDto.getOffset());

        contacts = contactListDto.getContacts();
        assertEquals(1, contacts.size());
        assertEquals("John Doe", contacts.get(0).getName());
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
