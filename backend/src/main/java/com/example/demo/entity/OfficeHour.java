package com.example.demo.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import static com.example.demo.utils.DateFormatter.checkAMPMRemoveSpaceUpperCase;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name = "officeHour")
public class OfficeHour {
    @Id
    @Column(unique = true)
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(name = "office_start", columnDefinition = "TIME")
    private LocalTime officeStart;

    @Column(name = "office_end", columnDefinition = "TIME")
    private LocalTime officeEnd;

    public OfficeHour(String officeStart, String officeEnd) throws Exception {
       this.setOfficeStart(officeStart);
       this.setOfficeEnd(officeEnd);
    }

    public void setOfficeStart(String officeStart)  throws Exception {
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("h:mma");

        this.officeStart = LocalTime.parse(checkAMPMRemoveSpaceUpperCase(officeStart), timeFormatter);
    }

    public void setOfficeEnd(String officeEnd)  throws Exception {
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("h:mma");

        this.officeEnd = LocalTime.parse(checkAMPMRemoveSpaceUpperCase(officeEnd), timeFormatter);
    }
}
