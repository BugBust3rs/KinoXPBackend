package com.example.kinoxpbackend.Service;

import com.example.kinoxpbackend.Model.Hall;
import com.example.kinoxpbackend.Repository.HallRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HallService {

    private final HallRepository hallRepository;

    public HallService(HallRepository hallRepository) {
        this.hallRepository = hallRepository;
    }

    public List<Hall> getAllHalls() {
        return hallRepository.findAll();
    }


    public Hall createHall(Hall hall) {
        return hallRepository.save(hall);
    }
}
