package com.example.service;

import com.example.dao.DepartmentDAO;
import com.example.dao.EmployeeDAO;
import com.example.model.Department;
import com.example.model.Employee;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import java.util.List;

/**
 * Defines transactional activities on an Employee.
 */
@ApplicationScoped
public class EmployeeService {

    @Inject
    private DepartmentDAO departmentDAO;

    @Inject
    private EmployeeDAO employeeDAO;

    @Transactional
    public void saveNew(String firstName, String lastName, String deptName) {
        Department department = departmentDAO.fetchByName(deptName);
        employeeDAO.saveNew(firstName, lastName, department);
    }

    @Transactional
    public List<Employee> fetchAll() {
        return eagerFetch(employeeDAO.fetchAll());
    }

    @Transactional
    public List<Employee> fetchByLastName(String lastName) {
        return eagerFetch(employeeDAO.fetchByLastName(lastName));
    }

    /**
     * Avoids LazyInitializationException for the client of this service.
     */
    private List<Employee> eagerFetch(List<Employee> list) {
        if (list != null) {
            list.stream().forEach(Employee::eagerFetch);
        }
        return list;
    }
}