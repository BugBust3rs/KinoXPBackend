package com.example.kinoxpbackend.Service;

import com.example.kinoxpbackend.Exception.NotfoundException;
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

    public Hall getHallById(Long id) {
        return hallRepository.findById(id)
                .orElseThrow(() -> new NotfoundException("Hall with id " + id + " not found"));
    }

    public Hall createHall(Hall hall) {
        return hallRepository.save(hall);
    }

    public Hall updateHall(Long id, Hall hall) {
        Hall existing = hallRepository.findById(id)
                .orElseThrow(() -> new NotfoundException("Hall with id " + id + " not found"));
        existing.setName(hall.getName());
        existing.setRows(hall.getRows());
        existing.setCols(hall.getCols());
        existing.setModularSeating(hall.getModularSeating());
        return hallRepository.save(existing);
    }

    public void deleteHall(Long id) {
        Hall existing = hallRepository.findById(id)
                .orElseThrow(() -> new NotfoundException("Hall with id " + id + " not found"));
        hallRepository.delete(existing);
    }
}