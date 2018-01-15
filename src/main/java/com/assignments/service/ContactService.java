package com.assignments.service;

import com.assignments.domain.Contact;
import com.assignments.exception.UnprocessableEntityException;
import com.assignments.repository.ContactRepository;
import com.assignments.web.dto.ContactDto;
import com.assignments.web.dto.ContactListDto;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class ContactService {
    private final static int BATCH_SIZE = 1000;

    private final ContactRepository contactRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public ContactService(ContactRepository contactRepository, ModelMapper modelMapper) {
        this.contactRepository = contactRepository;
        this.modelMapper = modelMapper;
    }

    @Transactional
    public ContactListDto findByNameRegex(String regex, int offset, int limit)
            throws UnprocessableEntityException {

        List<ContactDto> result = new ArrayList<>(limit);
        List<Contact> contactBatch = contactRepository.findAll(offset, BATCH_SIZE);

        try {
            Pattern pattern = Pattern.compile(regex);

            int i = 0;
            while (i < contactBatch.size() && result.size() < limit) {
                Contact contact = contactBatch.get(i);
                Matcher matcher = pattern.matcher(contact.getName());

                if(!matcher.matches()) {
                    result.add(modelMapper.map(contact, ContactDto.class));
                }
                i++;
            }

            return new ContactListDto(result, offset + i);

        } catch(Exception e) {
            throw new UnprocessableEntityException("Invalid regexp syntax");
        }
    }
}
