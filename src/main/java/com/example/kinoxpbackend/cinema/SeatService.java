package com.example.kinoxpbackend.cinema;

import com.example.kinoxpbackend.movie.MovieService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SeatService {

    private final MovieService movieService;
    private SeatRepository seatRepository;

    public SeatService(SeatRepository seatRepository, MovieService movieService){
        this.seatRepository = seatRepository;
        this.movieService = movieService;
    }


    public Seat findById(Long seatId) {
        return seatRepository.findById(seatId).orElseThrow(() -> new RuntimeException("Seat not found"));
    }

//    public List<Seat> getSeats(Long id) {
////
////        seatRepository.getAllByScreening();
//    }
}
