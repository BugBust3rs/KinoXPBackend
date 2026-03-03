package com.example.kinoxpbackend.Model;

import jakarta.persistence.*;

@Entity
public class Screening {

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    private Movie movie;

    @ManyToOne
    private Hall hall;

    private int startTime;
    private int basePrice;
    private boolean is3D;

    public Screening() {}

    public Screening(Movie movie, Hall hall, int startTime, int basePrice, boolean is3D) {
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

    public int getStartTime() {
        return startTime;
    }

    public void setStartTime(int startTime) {
        this.startTime = startTime;
    }

    public int getBasePrice() {
        return basePrice;
    }

    public void setBasePrice(int basePrice) {
        this.basePrice = basePrice;
    }

    public boolean isIs3D() {
        return is3D;
    }

    public void setIs3D(boolean is3D) {
        this.is3D = is3D;
    }
}
