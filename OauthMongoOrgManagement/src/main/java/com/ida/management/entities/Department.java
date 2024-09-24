package com.ida.management.entities;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.annotation.processing.Generated;
import java.util.List;

@Data
@Document(collection = "departments")
public class Department {

    @Id
    private String id;
    private String name;

    @DBRef
    private List<Employee> employees;

}
