package com.example.kinoxpbackend.Repository;

import com.example.kinoxpbackend.Model.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {
}
