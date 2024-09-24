package com.ida.management.entities;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "tasks")
public class Task {

    @Id
    private String id;
    private String title;
    private String description;

    private Employee employeeId;
    private String status;

    @DBRef
    private Employee assignedEmployee;

}
