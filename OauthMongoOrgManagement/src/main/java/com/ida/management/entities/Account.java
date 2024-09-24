package com.ida.management.entities;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
@Document(collection = "account")
public class Account {

    @Id
    private String id;

    private String firstName;

    private String lastName;

    private String email;

    private String phone;

    private List<String> role;

    public Account(String firstName, String lastName, String email, String phone) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phone = phone;

    }
}

