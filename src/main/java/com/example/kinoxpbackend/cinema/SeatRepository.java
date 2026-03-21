package com.example.kinoxpbackend.cinema;

import com.example.kinoxpbackend.screening.Screening;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

interface SeatRepository extends JpaRepository<Seat, Long> {
    List<Seat> getAllByScreening(Screening screening);
}
