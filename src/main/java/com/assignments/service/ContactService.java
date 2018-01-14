package com.assignments.service;

import com.assignments.domain.Contact;
import com.assignments.exception.UnprocessableEntityException;
import com.assignments.repository.ContactRepository;
import com.assignments.web.dto.ContactDto;
import com.assignments.web.dto.ContactListDto;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Service
public class ContactService {
    private final ContactRepository contactRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public ContactService(ContactRepository contactRepository, ModelMapper modelMapper) {
        this.contactRepository = contactRepository;
        this.modelMapper = modelMapper;
    }

    @Transactional
    public ContactListDto findByNameRegex(String regex, int page, int limit)
            throws UnprocessableEntityException {
        PageRequest pageRequest = new PageRequest(page, limit, Sort.Direction.ASC, "name");
        Page<Contact> contactPage = contactRepository.findAll(pageRequest);

        try {
            Pattern pattern = Pattern.compile(regex);

            List<ContactDto> contactDtoList =
                    contactPage
                            .getContent()
                            .stream()
                            .filter(contact -> {
                                Matcher matcher = pattern.matcher(contact.getName());
                                return matcher.matches();
                            })
                            .map(contact -> modelMapper.map(contact, ContactDto.class))
                            .collect(Collectors.toList());

            return new ContactListDto(
                    contactDtoList, contactPage.getTotalPages(), contactPage.getTotalElements());

        } catch(Exception e) {
            throw new UnprocessableEntityException("Invalid regexp syntax");
        }
    }
}
