package com.example.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * Department "owns" a set of Employees.
 */
@Entity
@Table(name = "DepartmentTbl", uniqueConstraints = @UniqueConstraint(columnNames = {"deptId", "deptName"}))
public class Department implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue
    private Long deptId;

    @NotNull
    @Size(min = 1, max = 25)
    private String deptName;

    @NotNull
    @Size(min = 6, max = 6)
    @Digits(integer = 6, fraction = 0)
    private String costCenter;

    @JsonManagedReference
    @OneToMany(mappedBy = "department", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Employee> employees;

    public void addEmployee(Employee employee) {
        if (employees == null) {
            employees = new HashSet<>();
        }
        employees.add(employee);
    }

    @Override
    public String toString() {
        return "Department " + deptId + ": " + deptName + ", cost center " + costCenter + ", "
                + (employees == null ? 0 : employees.size()) + " employees.";
    }

    /**
     * A way for transactional services to ensure that all the references of this object have been pulled from
     * the datastore.
     */
    public Department eagerFetch() {
        if (employees != null) {
            employees.stream().forEach(Employee::toString);
        }
        return this;
    }

    public Long getDeptId() {
        return deptId;
    }

    public void setDeptId(Long id) {
        this.deptId = id;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    public String getCostCenter() {
        return costCenter;
    }

    public void setCostCenter(String costCenter) {
        this.costCenter = costCenter;
    }

    public Set<Employee> getEmployees() {
        return employees;
    }

    public void setEmployees(Set<Employee> employees) {
        this.employees = employees;
    }
}
