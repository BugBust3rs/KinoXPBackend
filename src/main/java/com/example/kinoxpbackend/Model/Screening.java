package com.example.kinoxpbackend.Model;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
public class Screening {

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    private Movie movie;

    @ManyToOne
    @JoinColumn(name = "hall_id")
    private Hall hall;

    private LocalDateTime startTime;
    private Double basePrice; // lave den om til Double
    private boolean is3D;

    public Screening() {}

    public Screening(Movie movie, Hall hall, LocalDateTime startTime, Double basePrice, boolean is3D) {
        this.movie = movie;
        this.hall = hall;
        this.startTime = startTime;
        this.basePrice = basePrice;
        this.is3D = is3D;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Movie getMovie() {
        return movie;
    }

    public void setMovie(Movie movie) {
        this.movie = movie;
    }

    public Hall getHall() {
        return hall;
    }

    public void setHall(Hall hall) {
        this.hall = hall;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public Double getBasePrice() {
        return basePrice;
    }

    public void setBasePrice(Double basePrice) {
        this.basePrice = basePrice;
    }

    public boolean isIs3D() {
        return is3D;
    }

    public void setIs3D(boolean is3D) {
        this.is3D = is3D;
    }
}
