package com.example.kinoxpbackend.cinema;

import com.example.kinoxpbackend.exception.NotfoundException;
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

    public void deleteAll() {
        hallRepository.deleteAll();
    }

    public Hall findById(Long id) {
        return hallRepository.findById(id).orElseThrow(() -> new NotfoundException("hall not found"));
    }

    public Hall getHallById(Long id) {
        return hallRepository.findById(id).orElseThrow(() -> new NotfoundException( "not found"));
    }
}
