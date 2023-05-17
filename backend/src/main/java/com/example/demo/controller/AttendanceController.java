package com.example.demo.controller;

import com.example.demo.dto.CSVAttendanceCreateDto;
import com.example.demo.dto.OutputAttendanceDto;
import com.example.demo.entity.Attendance;
import com.example.demo.entity.Employee;
import com.example.demo.entity.OfficeHour;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.repository.AttendanceRepository;
import com.example.demo.repository.EmployeeRepository;
import com.example.demo.repository.OfficeHourRepository;
import com.lowagie.text.DocumentException;
import com.opencsv.CSVParser;
import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.bean.ColumnPositionMappingStrategy;
import com.opencsv.bean.CsvToBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.ByteArrayInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.example.demo.utils.DateFormatter.convertToAMPM;
import static com.example.demo.utils.DateFormatter.convertToDayAndDate;
import static com.example.demo.utils.PDFUtils.generatePdfFromHtml;
import static com.example.demo.utils.PDFUtils.parseThymeleafTemplate;


@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api")
public class AttendanceController {
    private final EmployeeRepository employeeRepository;
    private final AttendanceRepository attendanceRepository;
    private final OfficeHourRepository officeHourRepository;

    @Autowired
    public AttendanceController(EmployeeRepository employeeRepository,
                                AttendanceRepository attendanceRepository,
                                OfficeHourRepository officeHourRepository
    ) {
        this.employeeRepository = employeeRepository;
        this.attendanceRepository = attendanceRepository;
        this.officeHourRepository = officeHourRepository;
    }

    @GetMapping("/")
    public ResponseEntity<HttpStatus> getHello() {
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/employee/attendance")
    public ResponseEntity<List<OutputAttendanceDto>> getAllAttendance(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String department
    ) throws Exception {

        List<Employee> employees = new ArrayList<>();
        if (name != null && department != null) {
            throw new Exception("Don't send both name and employee");
        } else if (name != null)
            employeeRepository.findByNameContainingIgnoreCase(name).forEach(employees::add);
        else if (department != null)
            employeeRepository.findByDepartmentContainingIgnoreCase(department).forEach(employees::add);
        else employeeRepository.findAll().forEach(employees::add);
        if (employees.isEmpty()) throw new ResourceNotFoundException("Wrong query parameter");
        System.out.println(employees);
        List<Long> employeeIds = new ArrayList<>();
        for (Employee employee : employees) {
            employeeIds.add(employee.getId());
        }
        System.out.println(employeeIds);
        List<OutputAttendanceDto> attendances = new ArrayList<>();
        Optional<OfficeHour> _officeHour = officeHourRepository.findAll().stream().findFirst();
        if (_officeHour.isEmpty()) {
            throw new ResourceNotFoundException("No office hour for this user");
        }
        attendanceRepository.findByEmployeeIdInOrderByEmployeeId(employeeIds).forEach((value) -> {
                    try {
                        attendances.add(new OutputAttendanceDto(
                                value.getId(),
                                convertToAMPM(value.getOfficeIn()),
                                convertToAMPM(value.getOfficeOut()),
                                value.getOfficeIn().isAfter(_officeHour.get().getOfficeStart()),
                                value.getOfficeOut().isBefore(_officeHour.get().getOfficeEnd()),
                                convertToDayAndDate(value.getDate()),
                                value.getEmployee()
                        ));
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                }
        );
        System.out.println(attendances);

        return new ResponseEntity<>(attendances, HttpStatus.OK);
    }

    @GetMapping("attendance/CSVAttendanceCreate")
    public ResponseEntity<HttpStatus> importAttendanceFromExcel() {
        try {
            FileReader filereader = new FileReader("src/main/resources/static/CSVSheet1.csv");
            CSVParser parser = new CSVParserBuilder().withSeparator(',').build();
            CSVReader csvReader = new CSVReaderBuilder(filereader)
                    .withCSVParser(parser)
                    .build();

            ColumnPositionMappingStrategy<CSVAttendanceCreateDto> beanStrategy = new ColumnPositionMappingStrategy<CSVAttendanceCreateDto>();
            beanStrategy.setType(CSVAttendanceCreateDto.class);
            beanStrategy.setColumnMapping(new String[]{
                    "EmployeeName", "Designation", "Department", "OfficeIN", "OfficeOut", "Date"
            });
            CsvToBean<CSVAttendanceCreateDto> csvToBean = new CsvToBean<>();
            List<CSVAttendanceCreateDto> emps = csvToBean.parse(beanStrategy, csvReader);
            for (CSVAttendanceCreateDto emp : emps) {
                System.out.println(emp);
                Employee employee = employeeRepository.findOneByNameAndDepartmentAndDesignationIgnoreCase(
                        emp.getEmployeeName(), emp.getDepartment(), emp.getDesignation()
                );
                System.out.println(employee);
                if (employee != null) {
                    DateTimeFormatter df = DateTimeFormatter.ofPattern("d-MMM-yyyy");
                    Attendance attendance =
                            new Attendance(
                                    emp.getOfficeIN(),
                                    emp.getOfficeOut(),
                                    emp.getDate()
                            );
                    attendance.setEmployee(employee);
                    System.out.println(attendance);
                    attendanceRepository.save(attendance);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @RequestMapping(value = "employee/generatePdf",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_PDF_VALUE)
    public ResponseEntity<InputStreamResource> generatePdf(
            @RequestBody List<OutputAttendanceDto> dto
    ) throws DocumentException, IOException {
        System.out.println(dto);
        var headers = new HttpHeaders();
        headers.add("Content-Disposition", "inline; filename=citiesreport.pdf");
        String html = parseThymeleafTemplate(dto);
        ByteArrayInputStream bis = generatePdfFromHtml(html);
        return ResponseEntity
                .ok()
                .headers(headers)
                .contentType(MediaType.APPLICATION_PDF)
                .body(new InputStreamResource(bis));
    }

    @DeleteMapping("/employee/attendance")
    public ResponseEntity<HttpStatus> deleteAllAttendances() {
        attendanceRepository.deleteAll();
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


}
