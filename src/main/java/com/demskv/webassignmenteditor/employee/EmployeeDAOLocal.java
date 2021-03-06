package com.demskv.webassignmenteditor.employee;

import jakarta.ejb.Local;
import jakarta.ejb.Remote;
import java.util.List;
import java.util.Optional;

@Local
@Remote
public interface EmployeeDAOLocal {
    public Long countAll(
            Integer filterId, String filterFirstName, String filterMiddleName, String filterLastName, String filterPosition);

    public List<Employee> findAll(int from, int limit, String columnName, boolean desc, 
            Integer filterId, String filterFirstName, String filterMiddleName, String filterLastName, String filterPosition);

    public void create(Employee item);

    public void update(Employee item);

    public void deleteById(int id);
}
