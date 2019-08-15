package com.example.rest;

import com.example.model.Employee;
import com.example.service.EmployeeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;


/**
 * Accepts restful requests to get employees, or post a new one.
 */
@Path("/employee")
public class EmployeeResource {

    private static final Logger LOGGER = LoggerFactory.getLogger(EmployeeResource.class);

    public EmployeeResource() {
        LOGGER.info("EmployeeResource initialized");
    }

    @Inject
    private EmployeeService employeeService;

    @POST
    public Response post(@QueryParam("firstName") String firstName,
                         @QueryParam("lastName") String lastName,
                         @QueryParam("deptName") String deptName) {
        employeeService.saveNew(firstName, lastName, deptName);
        return Response.ok().build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response get(@QueryParam("lastName") String lastName) {
        List<Employee> list = null;
        if (lastName == null || lastName.length() == 0) {
            list = employeeService.fetchAll();
        }
        else {
            list = employeeService.fetchByLastName(lastName);
        }
        return Response.ok(list).build();
    }
}
