package com.ida.management.entities;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;
@Getter
@Setter
@Document(collation = "employees")
public class Employee {

    @Id
    private String id;
    private String name;
    private String email;
    private Department department;

    @DBRef
    private List<Task> Tasks;
}
