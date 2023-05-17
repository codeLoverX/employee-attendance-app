package com.example.demo.controller;

import com.example.demo.entity.Employee;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.repository.AttendanceRepository;
import com.example.demo.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api")
public class EmployeeController {

    private final EmployeeRepository employeeRepository;
    private final AttendanceRepository attendanceRepository;

    @Autowired
    public EmployeeController(EmployeeRepository employeeRepository,
                              AttendanceRepository attendanceRepository) {
        this.employeeRepository = employeeRepository;
        this.attendanceRepository = attendanceRepository;
    }

    @GetMapping("/employee")
    public ResponseEntity<List<Employee>> getAllEmployees(@RequestParam(required = false) String name) {
        List<Employee> employees = new ArrayList<Employee>();

        if (name == null)
            employeeRepository.findAll().forEach(employees::add);
        else
            employeeRepository.findByNameContainingIgnoreCase(name).forEach(employees::add);

        if (employees.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>(employees, HttpStatus.OK);
    }

    @PostMapping("/employee")
    public ResponseEntity<Employee> createEmployee(@RequestBody Employee employee) {
        Employee _employee = employeeRepository.save(new Employee(
                employee.getName(), employee.getDesignation(), employee.getDepartment()));
        return new ResponseEntity<>(_employee, HttpStatus.CREATED);
    }

    @PutMapping("/employee/{id}")
    public ResponseEntity<Employee> updateEmployee(@PathVariable("id") long id, @RequestBody Employee employee) {
        Employee _employee = employeeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Not found Employee with id = " + id));

        _employee.setName(employee.getName());
        _employee.setDepartment(employee.getDepartment());
        _employee.setDesignation(employee.getDesignation());

        return new ResponseEntity<>(employeeRepository.save(_employee), HttpStatus.OK);
    }

    @DeleteMapping("/employee/{id}")
    public ResponseEntity<HttpStatus> deleteEmployee(@PathVariable("id") long id) {
        employeeRepository.deleteById(id);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
