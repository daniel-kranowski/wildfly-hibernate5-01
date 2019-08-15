package com.example.service;

import com.example.dao.DepartmentDAO;
import com.example.model.Department;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import java.util.List;

/**
 * Defines transactional activities on a Department.
 */
@ApplicationScoped
public class DepartmentService {

    @Inject
    private DepartmentDAO departmentDAO;

    @Transactional
    public void saveNew(String deptName, String costCenter) {
        departmentDAO.saveNew(deptName, costCenter);
    }

    @Transactional
    public List<Department> fetchAll() {
        return eagerFetch(departmentDAO.fetchAll());
    }

    /**
     * Avoids LazyInitializationException for the client of this service.
     */
    private List<Department> eagerFetch(List<Department> list) {
        if (list != null) {
            list.stream().forEach(Department::eagerFetch);
        }
        return list;
    }

}
