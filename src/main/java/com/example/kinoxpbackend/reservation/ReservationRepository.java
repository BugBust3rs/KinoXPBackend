package com.example.kinoxpbackend.reservation;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

interface ReservationRepository extends JpaRepository<Reservation, Long> {
    @EntityGraph(attributePaths = {"screening", "seats"})
    @Query("select r from Reservation r")
    List<Reservation> getAllWithScreeningAndSeats();

}
