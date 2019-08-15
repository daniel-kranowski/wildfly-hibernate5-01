package com.example.model;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import java.io.IOException;

/**
 * This custom serializer simply adds 'deptId' to the serialized form of Employee.
 * Without it, since we're using @JsonBackReference on Employee#department, normally the serialized Employee would not
 * have any info whatsoever about the parent Department.
 *
 * Instead of @JsonManagedReference/@JsonBackReference, we could put @JsonIdentityInfo on the Employee and Department,
 * but then a serialized Employee would contain its entire parent Department, just like the serialized Department
 * contains its Employees.  No worry of infinite recursion, but I do not wish the serialized child entity to contain
 * its parent, only have a small reference to the parent id.
 */
public class EmployeeSerializer extends StdSerializer<Employee> {

    public EmployeeSerializer() {
        this(null);
    }

    public EmployeeSerializer(Class<Employee> t) {
        super(t);
    }

    @Override
    public void serialize(Employee employee, JsonGenerator jsonGenerator, SerializerProvider serializerProvider)
            throws IOException {
        jsonGenerator.writeStartObject();
        jsonGenerator.writeNumberField("emplId", employee.getEmplId());
        jsonGenerator.writeStringField("firstName", employee.getFirstName());
        jsonGenerator.writeStringField("lastName", employee.getLastName());
        jsonGenerator.writeNumberField("deptId",
                employee.getDepartment() == null ? 0 : employee.getDepartment().getDeptId());
        jsonGenerator.writeEndObject();
    }
}
