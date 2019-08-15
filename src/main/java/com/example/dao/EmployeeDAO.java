package com.example.dao;

import com.example.model.Department;
import com.example.model.Employee;

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

/**
 * Data Access Object for Employee entities.
 */
@ApplicationScoped
public class EmployeeDAO {

    @PersistenceContext
    private EntityManager em;

    public void saveNew(String firstName, String lastName, Department department) {
        Employee employee = new Employee();
        employee.setFirstName(firstName);
        employee.setLastName(lastName);
        employee.setDepartment(department);
        department.addEmployee(employee);
        em.persist(department); //Persist action cascades from department to employee
    }

    public List<Employee> fetchAll() {
        List<Employee> list = em.createQuery("SELECT e FROM Employee e").getResultList();
        return list;
    }

    public List<Employee> fetchByLastName(String lastName) {
        Query query = em.createQuery("SELECT e FROM Employee e where e.lastName = :lastName");
        query.setParameter("lastName", lastName);
        return query.getResultList();
    }

}
