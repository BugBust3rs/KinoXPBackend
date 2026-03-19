package com.example.kinoxpbackend.Repository;

import com.example.kinoxpbackend.Model.Reservation;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {
    @EntityGraph(attributePaths = {"screening", "seats"})
    @Query("select r from Reservation r")
    List<Reservation> getAllWithScreeningAndSeats();
}
