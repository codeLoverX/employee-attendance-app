package com.example.demo.repository;

import com.example.demo.entity.OfficeHour;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OfficeHourRepository extends JpaRepository<OfficeHour, Long> {
}
