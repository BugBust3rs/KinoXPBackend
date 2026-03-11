package com.example.kinoxpbackend.Controller;

import com.example.kinoxpbackend.Model.Reservation;
import com.example.kinoxpbackend.Service.ReservationService;
import com.example.kinoxpbackend.dto.ReservationRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/reservations")
public class ReservationController {

    private ReservationService reservationService;

    public ReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    //Henter alle reservations
    @GetMapping
    public ResponseEntity<List<ReservationRequest>> getAllReservations(){
        return ResponseEntity.ok(reservationService.getAllReservations());
    }


    //Henter en bestem reservation på id
    @GetMapping("/{id}")
    public ResponseEntity<ReservationRequest> getReservation(@PathVariable Long id){
       return ResponseEntity.ok(reservationService.getReservationById(id));
    }

    @PostMapping()
    public ResponseEntity<ReservationRequest> createReservation(@RequestBody ReservationRequest request) {
        ReservationRequest reservation = reservationService.createReservation(request);
        return ResponseEntity.ok(reservation);
    }

    //Sletter en reservation
    @DeleteMapping("/{id}")
    public ResponseEntity<Reservation> deleteReservation(@PathVariable Long id){
        reservationService.deleteReservation(id);
        return ResponseEntity.noContent().build();
    }
}
