package com.example.demo.repository;

import com.example.demo.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    public Employee findOneByNameAndDepartmentAndDesignationIgnoreCase
            (String employeeName, String department, String designation);
    public List<Employee> findByDepartmentContainingIgnoreCase(String department);

    public List<Employee> findByNameContainingIgnoreCase(String employeeName);

}
