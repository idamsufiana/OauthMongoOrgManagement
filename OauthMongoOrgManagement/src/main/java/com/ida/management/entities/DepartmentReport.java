package com.ida.management.entities;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "department_reports")
public class DepartmentReport {

    @Id
    private String id; // Optional, used if you want to keep a unique ID for each report
    private String departmentName;
    private int employeeCount;

    // Constructors
    public DepartmentReport() {}

    public DepartmentReport(String departmentName, int employeeCount) {
        this.departmentName = departmentName;
        this.employeeCount = employeeCount;
    }

}