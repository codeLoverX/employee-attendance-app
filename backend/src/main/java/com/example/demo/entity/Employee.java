package com.example.demo.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Table(name = "employee")
public class Employee {
    @Id
    @Column(unique = true)
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long id;

    private String name;

    private String designation;

    private String department;

//    @OneToOne(mappedBy = "employee", cascade = CascadeType.ALL)
//    @PrimaryKeyJoinColumn
//    @JsonManagedReference
//    private Attendance attendance;

    public Employee(String name, String designation, String department) {
        this.name = name;
        this.designation = designation;
        this.department = department;
    }

    @Override
    public String toString() {
        return "Employee [id=" + id + ", name=" + name + ", designation=" + designation + ", department=" + department + "]";
    }

}
