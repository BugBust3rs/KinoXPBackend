package com.example.kinoxpbackend.cinema;

import com.example.kinoxpbackend.reservation.Reservation;
import com.example.kinoxpbackend.screening.Screening;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;

@Entity
@Table(name = "seat")
public class Seat {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonBackReference("screening-seats")
    @ManyToOne
    @JoinColumn(name = "screening_id")
    private Screening screening;

    @ManyToOne
    @JoinColumn(name = "reservation_id")
    @JsonBackReference("reservation-seats")
    private Reservation reservation;

    private boolean isReserved;

    @Enumerated(EnumType.STRING)
    private ModularSeating modularSeating;

    private int seatRow;
    private int seatColumn;

    public Seat() {}

    public Seat(ModularSeating modularSeating, int seatRow, int seatColumn) {
        this.modularSeating = modularSeating;
        this.seatRow = seatRow;
        this.seatColumn = seatColumn;
    }

    public Screening getScreening() {
        return screening;
    }

    public void setScreening(Screening screening) {
        this.screening = screening;
    }

    public Reservation getReservation() {
        return reservation;
    }

    public void setReservation(Reservation reservation) {
        this.reservation = reservation;
    }

    public boolean isReserved() {
        return isReserved;
    }

    public void setReserved(boolean reserved) {
        isReserved = reserved;
    }

    public ModularSeating getModularSeating() {
        return modularSeating;
    }

    public void setModularSeating(ModularSeating modularSeating) {
        this.modularSeating = modularSeating;
    }

    public Long getId() {
        return id;
    }

    public int getSeatRow() {
        return seatRow;
    }

    public void setSeatRow(int seatRow) {
        this.seatRow = seatRow;
    }

    public int getSeatColumn() {
        return seatColumn;
    }

    public void setSeatColumn(int seatColumn) {
        this.seatColumn = seatColumn;
    }
}