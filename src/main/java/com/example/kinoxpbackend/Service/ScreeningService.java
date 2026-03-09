package com.example.kinoxpbackend.Service;

import com.example.kinoxpbackend.Model.Screening;
import com.example.kinoxpbackend.Repository.ScreeningRepository;
import exceptions.NotFoundException;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import com.example.kinoxpbackend.Exception.NotfoundException;

import java.util.List;
import java.util.Optional;

@Service
public class ScreeningService {
    private ScreeningRepository screeningRepository;

    public ScreeningService(ScreeningRepository screeningRepository) {
        this.screeningRepository = screeningRepository;
    }

    //Metode der henter alle Screening
    public List<Screening> getAllScreenings(){
        return screeningRepository.findAll();
    }

    //Metode der henter en bestemt screening
    public Screening getScreeningById(Long id){
        Optional<Screening> screeningOptional = screeningRepository.findById(id);
        if(screeningOptional.isEmpty()){
            throw new NotFoundException("Screening with id " + id + " not found");
        }
        return screeningOptional.get();
    }

    //Metode der opretter en screening
    public Screening createScreening(Screening screening){
        return screeningRepository.save(screening);
    }

    //Metode der opdaterer en screening
    public Screening updateScreening(Long id, Screening screening){
//        Optional<Screening> screeningOptional = screeningRepository.findById(id);
//        if(screeningOptional.isEmpty()){
//            throw new NotFoundException("Screening with id " + id + " not found");
//        }
        Screening screening1 = screeningRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Screening with id " + id + " not found"));
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


}
