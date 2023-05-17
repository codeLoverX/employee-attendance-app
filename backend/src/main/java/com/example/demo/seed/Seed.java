package com.example.demo.seed;

import com.example.demo.entity.Employee;
import com.example.demo.entity.OfficeHour;
import com.example.demo.repository.EmployeeRepository;
import com.example.demo.repository.OfficeHourRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
public class Seed implements CommandLineRunner {

    @Autowired
    EmployeeRepository employeeRepository;
    @Autowired
    private OfficeHourRepository officeHourRepository;

    @Override
    public void run(String... args) throws Exception {
        loadUserData();
    }

    private void loadUserData() throws Exception {
        if (employeeRepository.count() < 1) {
        Employee[] employees = {
                new Employee(1L, "Ridwan", "Full stack", "Developer"),
                new Employee(2L, "Mark", "CEO", "Owner"),
                new Employee(3L, "Zucker", "CEO", "Owner"),
        };
        OfficeHour officeHour = new OfficeHour("8:00 AM", "6:00PM");
        officeHourRepository.deleteAll();
        employeeRepository.deleteAll();
        Arrays.stream(employees).forEach(employee -> employeeRepository.save(employee));
        System.out.println(officeHour);
        officeHourRepository.save(officeHour);
//        System.out.println(Arrays.toString(employees));
        System.out.println(officeHour);
//        System.out.println("Inputted the following " + employeeRepository.count() + " data");
    } else {
            System.out.println("Already has data in the system");
        }
    }
}