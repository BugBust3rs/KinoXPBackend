package com.example.kinoxpbackend.screening;

import com.example.kinoxpbackend.cinema.HallService;
import com.example.kinoxpbackend.movie.Movie;
import com.example.kinoxpbackend.movie.MovieService;
import com.example.kinoxpbackend.dto.*;
import com.example.kinoxpbackend.mapper.HallMapper;
import com.example.kinoxpbackend.mapper.MovieMapper;
import com.example.kinoxpbackend.mapper.ScreeningMapper;
import com.example.kinoxpbackend.mapper.SeatMapper;
import com.example.kinoxpbackend.cinema.ModularSeating;
import com.example.kinoxpbackend.cinema.Seat;

import org.jspecify.annotations.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import com.example.kinoxpbackend.exception.NotfoundException;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ScreeningService {
    private static final Logger log = LoggerFactory.getLogger(ScreeningService.class);
    private final ScreeningRepository screeningRepository;
    private final MovieService movieService;
    private final HallService hallService;

    public ScreeningService(ScreeningRepository screeningRepository, MovieService movieService, HallService hallService) {
        this.screeningRepository = screeningRepository;
        this.movieService = movieService;
        this.hallService = hallService;
    }

    //Metode der henter alle Screening
    public List<Screening> getAllScreenings() {
        return screeningRepository.findAll();
    }

    //Metode der henter en bestemt screening
    @Transactional(readOnly=true)
    public ScreeningResponse getScreeningResponseById(Long id) {
        var screening = screeningRepository.findByIdWithReservations(id)
                .orElseThrow(() -> new NotfoundException("Screening with id " + id + " not found"));


        MovieResponse movieResponse = MovieMapper.movieToMovieResponse(screening.getMovie());
        HallResponse hallResponse = HallMapper.hallToHallResponse(screening.getHall());

        List<SeatResponse> seatResponses = new ArrayList<>();
        for (Seat seat : screening.getSeats()){
            seatResponses.add(SeatMapper.SeatToSeatResponse(seat));
        }

        return ScreeningMapper.screeningToScreeningResponse(screening, movieResponse, hallResponse, seatResponses);
    }

    public Screening getScreeningById(Long id){
        return screeningRepository.findById(id)
                .orElseThrow(() -> new NotfoundException("Screening with id " + id + " not found"));
    }


    //Metode der opretter en screening
    @Transactional
    public Screening createScreening(ScreeningRequest request) {
        Screening screening = ScreeningMapper.requestToScreeningMapper(request);
        var hall = hallService.findById(request.hallId());
        screening.setHall(hall);

        var movie = movieService.getMovieById(request.movieId());
        if (screening.getSeats().isEmpty()) {
            int row = screening.getHall().getRows();
            int col = screening.getHall().getCols();

            for (int i = 1; i < row; i++) {
                for (int j = 1; j < col; j++) {
                    ModularSeating modularSeating = ModularSeating.BASICROW;
                    if (i < 3) {
                        modularSeating = ModularSeating.COWBOYROW;
                    } else if (i > (row - 3)) {
                        modularSeating = ModularSeating.COUCHROW;
                    }

                    Seat seat = new Seat(modularSeating, i, j);
                    screening.addSeat(seat);
                }
            }
        }
        movie.addScreening(screening);

        return screeningRepository.save(screening);
    }

    //Metode der opdaterer en screening
    public Screening updateScreening(Long id, Screening screening) {
        var screening1 = screeningRepository.findById(id)
                .orElseThrow(() -> new NotfoundException("Screening with id " + id + " not found"));
        screening1.setMovie(screening.getMovie());
        screening1.setHall(screening.getHall());
        screening1.setBasePrice(screening.getBasePrice());
        screening1.setIs3D(screening.isIs3D());
        screening1.setStartTime(screening.getStartTime());
        return screeningRepository.save(screening);
    }

    //Metode der sletter en screening
    public void deleteScreening(Long id) {
        screeningRepository.deleteById(id);
    }


    public @Nullable List<Screening> getScreeningsByMovieId(Long movieId) {
        Movie movie = movieService.getMovieById(movieId);
        return screeningRepository.getScreeningsWithMovie(movie);
    }

    public void deleteAll() {
        screeningRepository.deleteAll();
    }
}
