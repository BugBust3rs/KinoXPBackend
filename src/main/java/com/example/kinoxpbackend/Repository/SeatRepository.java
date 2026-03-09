package com.example.kinoxpbackend.Repository;

import com.example.kinoxpbackend.Model.Seat;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SeatRepository extends JpaRepository<Seat, Long> {
}
