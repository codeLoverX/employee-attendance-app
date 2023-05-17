package com.example.demo.controller;

import com.example.demo.dto.OutputOfficeHourDto;
import com.example.demo.dto.PatchOfficeHourDto;
import com.example.demo.entity.OfficeHour;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.repository.OfficeHourRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.format.DateTimeFormatter;
import java.util.Optional;

import static com.example.demo.utils.DateFormatter.convertToAMPM;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api")
public class OfficeHourController {
    private final OfficeHourRepository officeHourRepository;

    @Autowired
    public OfficeHourController(OfficeHourRepository officeHourRepository) {
        this.officeHourRepository = officeHourRepository;
    }

    @GetMapping("/officeHour")
    public ResponseEntity<OutputOfficeHourDto> getOfficeStart() throws Exception {
        Optional<OfficeHour> _officeHour = officeHourRepository.findAll().stream().findFirst();
        if (_officeHour.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        OutputOfficeHourDto officeHour = new OutputOfficeHourDto(
                _officeHour.get().getId(),
                convertToAMPM(_officeHour.get().getOfficeStart()),
                convertToAMPM(_officeHour.get().getOfficeEnd())
        );
        return new ResponseEntity<>(officeHour, HttpStatus.OK);
    }

    @PatchMapping("officeHour")
    public ResponseEntity<OfficeHour> updateOfficeHour(@RequestBody PatchOfficeHourDto dto) throws Exception {
        Optional<OfficeHour> optional = officeHourRepository.findAll().stream().findFirst();
        if (optional.isEmpty()) {
            throw new Exception("Office hour timing not set!");
        }
        OfficeHour _officeHour = optional.get();
        if (dto.getOp().equalsIgnoreCase("update") && dto.getKey().equalsIgnoreCase("officeStart")) {
            _officeHour.setOfficeStart(dto.getValue());

        } else if (dto.getOp().equalsIgnoreCase("update") && dto.getKey().equalsIgnoreCase("officeEnd")) {
            _officeHour.setOfficeEnd(dto.getValue());
        } else {
            throw new ResourceNotFoundException("Can't find _officeHour");
        }
        officeHourRepository.save(_officeHour);
        return new ResponseEntity<>(_officeHour, HttpStatus.ACCEPTED);
    }
}
