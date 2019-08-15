package com.example.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

/**
 * An employee belongs to a single department.
 */
@JsonSerialize(using = EmployeeSerializer.class)
@Entity
@Table(name = "EmployeeTbl", uniqueConstraints = @UniqueConstraint(columnNames = "emplId"))
public class Employee implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue
    private Long emplId;

    @NotNull
    @Size(min = 1, max = 25)
    private String firstName;

    @NotNull
    @Size(min = 1, max = 25)
    private String lastName;

    @JsonBackReference //Jackson annotation: avoids infinite recursion when using bidi JPA reference as a Response.
    @ManyToOne
    @JoinColumn(name = "deptId", nullable = false)
    private Department department;

    @Override
    public String toString() {
        return "Employee " + emplId + ": " + firstName + " " + lastName + " (Dept: " + department.getDeptName() + ")";
    }

    /**
     * A way for transactional services to ensure that all the references of this object have been pulled from
     * the datastore.
     */
    public Employee eagerFetch() {
        if (department != null) {
            department.toString();
        }
        return this;
    }

    public Long getEmplId() {
        return emplId;
    }

    public void setEmplId(Long emplId) {
        this.emplId = emplId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }
}
