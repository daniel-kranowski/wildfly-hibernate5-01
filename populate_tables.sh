#!/bin/bash
#
# Uses the restful interface to add departments and employees
#

SERVER=localhost
PORT=8080
CONTEXT=wh1

DEPARTMENT_URL="http://$SERVER:$PORT/$CONTEXT/department"
EMPLOYEE_URL="http://$SERVER:$PORT/$CONTEXT/employee"

curl -X POST "${DEPARTMENT_URL}?deptName=HR&costCenter=100050"
curl -X POST "${DEPARTMENT_URL}?deptName=Finance&costCenter=100060"

curl -X POST "${EMPLOYEE_URL}?firstName=James&lastName=Young&deptName=HR"
curl -X POST "${EMPLOYEE_URL}?firstName=Mary&lastName=Hernandez&deptName=HR"
curl -X POST "${EMPLOYEE_URL}?firstName=Robert&lastName=Morris&deptName=HR"
curl -X POST "${EMPLOYEE_URL}?firstName=David&lastName=Young&deptName=Finance"
curl -X POST "${EMPLOYEE_URL}?firstName=Patricia&lastName=Wood&deptName=Finance"
curl -X POST "${EMPLOYEE_URL}?firstName=Elizabeth&lastName=Barnes&deptName=Finance"
