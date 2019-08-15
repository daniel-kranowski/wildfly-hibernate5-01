package com.example.dao;

import com.example.model.Department;

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

/**
 * Data Access Object for Department entities.
 */
@ApplicationScoped
public class DepartmentDAO {

    @PersistenceContext
    private EntityManager em;

    public void saveNew(String deptName, String costCenter) {
        Department department = new Department();
        department.setDeptName(deptName);
        department.setCostCenter(costCenter);
        // No employees yet
        em.persist(department);
    }

    public List<Department> fetchAll() {
        List<Department> list = em.createQuery("SELECT d FROM Department d").getResultList();
        return list;
    }

    public Department fetchByName(String deptName) {
        TypedQuery<Department> query = em.createQuery("SELECT d FROM Department d where d.deptName = :deptName",
                Department.class);
        query.setParameter("deptName", deptName);
        return query.getSingleResult();
    }

}
