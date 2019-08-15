# Hibernate 5 with Wildfly

This is a hello-world of Java EE 7 + Hibernate 5 with the [Wildfly](http://wildfly.org/) application server.
It models a simple datastore of Employees and Departments.  You can use the restful interface to
add some entities and query to see how records are related.  We use an in-memory H2 database and
hbm2ddl create-drop to create the schema afresh when the webapp is deployed.

### How to build and run this project

Start the Wildfly appserver: `./bin/standalone.sh`

Build and deploy: `mvn install wildfly:deploy`

Run a script to populate the tables:
```
./populate_tables.sh
```
Take a look inside that script to see how it posts new records.

Now run some queries.

Get the list of departments:
```
curl localhost:8080/wh1/departments
```

Get the list of employees:
```
curl localhost:8080/wh1/employee
```

Or the employees with a particular last name:
```
curl localhost:8080/wh1/employee?lastName=Young
```
You can use any REST client you like, not just curl.

### Project structure

An inbound REST request passes through these layers, from top to bottom:

* com.example.rest
    * JAX-RS RESTful request interface.
* com.example.service
    * JTA transaction boundaries.
* com.example.dao
    * Data Access Object: JPA persistence context.
* com.example.model
    * JPA Entity.
    * JAX-RS Response body.

In a larger project it would make sense to break out a separate DTO package for JAX-RS Response objects.

### Java EE remarks

Wildfly implements CDI with Weld, JAX-RS with Resteasy, and data binding with Jackson.  Our chosen JPA impl is
Hibernate.

We're using CDI (beans.xml and bean-related annotations) so the Wildfly container will manage the lifecycle
of contextual bean instances.  Since the JPA EntityManager is container-managed, the app doesn't instantiate
the EMF. We use @PersistenceContext in a managed bean (@ApplicationScoped) and the container injects
an EM for us.  The container finds our Entity classes.

The model classes are serving double-duty as JPA Entity and Jackson/JAX-RS Response objects.

### About the database 

The datasource is an in-memory H2 database.  When the webapp is deployed, Wildfly parses the persistence
unit.  We specify `hibernate.hbm2ddl.auto=create-drop` and the the schema drop/creation happens at this
point.

Our code defines the webapp datasource using *-ds.xml, which is convenient but for test purposes only.
For production you're supposed to define the datasource in Wildfly standalone.xml (where it can
be managed live in the Wildfly CLI or admin console), not in this deployable war.

For convenience, we also start up an H2 TCP Server when the first restful request is received, so that you
can separately start an [H2 Web Console](http://www.h2database.com/html/quickstart.html#h2_console) and use
the browser to inspect the H2 database.  With Wildfly 10.1.0.Final, I do this:

```
cd <wildfly-install-home>
java -cp modules/system/layers/base/com/h2database/h2/main/h2-1.3.173.jar \
              org.h2.tools.Server -web
```
The default H2 web port is 8082, tcp is 9092.  Open a browser to localhost:8082, and connect using the parameters
specified in the *-ds.xml file, with just one change: in the browser, when you specify JDBC URL, change `mem` to
`tcp://localhost:9092/mem`.


### Technology summary

* Java EE 7
* JDK 8
* JPA 2.1
* CDI 1.2
* JTA 1.2
* Hibernate 5.0.10.Final
* Jackson 2.7.4
* H2 database 1.3
* Tested on Wildfly 10.1.0.Final

