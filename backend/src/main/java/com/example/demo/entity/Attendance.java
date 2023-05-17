package com.example.demo.entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import static com.example.demo.utils.DateFormatter.checkAMPMRemoveSpaceUpperCase;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id")
@Builder
@Table(name = "attendance")
public class Attendance {
    @Id
    @Column(unique = true)
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(name = "office_in", columnDefinition = "TIME")
    private LocalTime officeIn;

    @Column(name = "office_out", columnDefinition = "TIME")
    private LocalTime officeOut;

    @Column(name = "date", columnDefinition = "DATE")
    private LocalDate date;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "employee_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonProperty("employee")
    private Employee employee;

    public Attendance(String officeIn, String officeOut, String date) throws Exception {
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("h:mma");
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        this.officeIn = LocalTime.parse(checkAMPMRemoveSpaceUpperCase(officeIn), timeFormatter);
        this.officeOut = LocalTime.parse(checkAMPMRemoveSpaceUpperCase(officeOut), timeFormatter);
        this.date = LocalDate.parse(date, dateFormatter);
    }

}
