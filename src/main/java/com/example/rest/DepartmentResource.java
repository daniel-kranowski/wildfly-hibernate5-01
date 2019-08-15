package com.example.rest;

import com.example.model.Department;
import com.example.service.DepartmentService;
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
 * Accepts restful requests to get all departments, or post a new one.
 */
@Path("/department")
public class DepartmentResource {

    private static final Logger LOGGER = LoggerFactory.getLogger(DepartmentResource.class);

    public DepartmentResource() {
        LOGGER.info("DepartmentResource initialized");
    }

    @Inject
    private DepartmentService departmentService;

    @POST
    public void post(@QueryParam("deptName") String deptName,
                     @QueryParam("costCenter") String costCenter) {
        departmentService.saveNew(deptName, costCenter);
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response get() {
        List<Department> list = departmentService.fetchAll();
        return Response.ok(list).build();
    }
}
