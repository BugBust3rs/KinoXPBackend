package com.example.kinoxpbackend.reservation;

import com.example.kinoxpbackend.cinema.Seat;
import com.example.kinoxpbackend.screening.Screening;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "reservation")
public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String customerName;
    private String customerEmail;
    private LocalDateTime creationDate;
    private double price;

    @ManyToOne
    @JoinColumn(name = "screening_id")
    private Screening screening;

    @JsonManagedReference("reservation-seats")
    @OneToMany(mappedBy = "reservation", cascade = {CascadeType.MERGE, CascadeType.REFRESH})
    private List<Seat> seats = new ArrayList<>();

    public Reservation() {}

    public Reservation(String customerName, String customerEmail, LocalDateTime creationDate, double price) {
        this.customerName = customerName;
        this.customerEmail = customerEmail;
        this.creationDate = creationDate;
        this.price = price;
    }

    public void addSeat(Seat seat) {
        seats.add(seat);
        seat.setReservation(this);
    }

    public void removeSeat(Seat seat) {
        seats.remove(seat);
        seat.setReservation(null);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getCustomerEmail() {
        return customerEmail;
    }

    public void setCustomerEmail(String customerEmail) {
        this.customerEmail = customerEmail;
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDateTime creationDate) {
        this.creationDate = creationDate;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public Screening getScreening() {
        return screening;
    }

    public void setScreening(Screening screening) {
        this.screening = screening;
    }

    public List<Seat> getSeats() {
        return seats;
    }

    public void setSeats(List<Seat> seats) {
        this.seats = seats;
    }
}