package com.assignments.web.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class ContactListDto {
    private List<ContactDto> contacts;

    private int totalPages;
    private long totalElements;
}
