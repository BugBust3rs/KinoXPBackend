package com.example.kinoxpbackend.Service;

import com.example.kinoxpbackend.Model.ModularSeating;
import com.example.kinoxpbackend.Model.Movie;
import com.example.kinoxpbackend.Model.Screening;
import com.example.kinoxpbackend.Model.Seat;
import com.example.kinoxpbackend.Repository.HallRepository;
import com.example.kinoxpbackend.Repository.ScreeningRepository;
import com.example.kinoxpbackend.dto.ScreeningRequest;
import com.example.kinoxpbackend.mapper.ScreeningMapper;
import org.jspecify.annotations.Nullable;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import com.example.kinoxpbackend.Exception.NotfoundException;

import java.util.List;
import java.util.Optional;

@Service
public class ScreeningService {
    private final ScreeningRepository screeningRepository;
    private final MovieService movieService;
    private final HallRepository hallRepository;

    public ScreeningService(ScreeningRepository screeningRepository, MovieService movieService, HallRepository hallRepository) {
        this.screeningRepository = screeningRepository;
        this.movieService = movieService;
        this.hallRepository = hallRepository;
    }

    //Metode der henter alle Screening
    public List<Screening> getAllScreenings(){
        return screeningRepository.findAll();
    }

    //Metode der henter en bestemt screening
    public Screening getScreeningById(Long id){
        Optional<Screening> screeningOptional = screeningRepository.findById(id);
        if(screeningOptional.isEmpty()){
            throw new NotfoundException("Screening with id " + id + " not found");
        }
        return screeningOptional.get();
    }

    //Metode der opretter en screening
    public Screening createScreening(ScreeningRequest request){
        Screening screening = ScreeningMapper.requestToScreeningMapper(request);
        var hall = hallRepository.findById(request.hallId()).orElseThrow(() -> new NotfoundException("hall not found"));
        screening.setHall(hall);

        var movie = movieService.getMovieById(request.movieId());
        screening.setMovie(movie);
            if (screening.getSeats().isEmpty()){
                int row = screening.getHall().getRows();
                int col = screening.getHall().getCols();

                for (int i = 1; i < row; i++){
                    for (int j = 1; j < col; j++){
                        ModularSeating modularSeating = ModularSeating.BASICROW;
                        if(i < 3){
                            modularSeating = ModularSeating.COWBOYROW;
                        } else if (i > (row -3)) {
                            modularSeating = ModularSeating.COUCHROW;
                        }

                        Seat seat = new Seat(modularSeating,i, j);
                        screening.addSeat(seat);
                    }
                }
            }

        return screeningRepository.save(screening);
    }

    //Metode der opdaterer en screening
    public Screening updateScreening(Long id, Screening screening){
//        Optional<Screening> screeningOptional = screeningRepository.findById(id);
//        if(screeningOptional.isEmpty()){
//            throw new NotFoundException("Screening with id " + id + " not found");
//        }
        Screening screening1 = screeningRepository.findById(id)
                .orElseThrow(() -> new NotfoundException("Screening with id " + id + " not found"));
        screening1.setMovie(screening.getMovie());
        screening1.setHall(screening.getHall());
        screening1.setBasePrice(screening.getBasePrice());
        screening1.setIs3D(screening.isIs3D());
        screening1.setStartTime(screening.getStartTime());
        return screeningRepository.save(screening);
    }

    //Metode der sletter en screening
    public void deleteScreening(Long id){
        screeningRepository.deleteById(id);
    }


    public @Nullable List<Screening> getScreeningsByMovieId(Long movieId) {
        Movie movie = movieService.getMovieById(movieId);
        return screeningRepository.getScreeningsByMovie(movie);
    }
}
