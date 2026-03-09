package com.example.kinoxpbackend.Model;

import jakarta.persistence.*;

@Entity
public class Hall {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private int rows;
    private int cols;

    @Enumerated(EnumType.STRING)
    private ModularSeating modularSeating;


    public Hall() {
    }

    public Hall(Long id, String name, int rows, int cols) {
        this.id = id;
        this.name = name;
        this.rows = rows;
        this.cols = cols;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getRows() {
        return rows;
    }

    public void setRows(int rows) {
        this.rows = rows;
    }

    public int getCols() {
        return cols;
    }

    public void setCols(int cols) {
        this.cols = cols;
    }

    public ModularSeating getModularSeating() {
        return modularSeating;
    }

    public void setModularSeating(ModularSeating modularSeating) {
        this.modularSeating = modularSeating;
    }

}
