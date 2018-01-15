package com.assignments.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "contacts", indexes = @Index(name = "name_idx", columnList = "name", unique = true))
public class Contact {
    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "name", length = 100, nullable = false)
    private String name;

    public Contact(String name) {
        this.name = name;
    }
}
