package com.example.kinoxpbackend.screening;

import com.example.kinoxpbackend.dto.ScreeningRequest;
import com.example.kinoxpbackend.dto.ScreeningResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/screenings")
class ScreeningController {
    private ScreeningService screeningService;

    public ScreeningController(ScreeningService screeningService) {
        this.screeningService = screeningService;
    }

    //henter alle film
    @GetMapping
    public ResponseEntity<List<Screening>> getAllScreenings(){
        return  ResponseEntity.ok(screeningService.getAllScreenings());

    }

    //Henter en bestemt screening
    @GetMapping("{id}")
    public ResponseEntity<ScreeningResponse> getScreening(@PathVariable Long id){
        return ResponseEntity.ok(screeningService.getScreeningResponseById(id));
    }

    // Get screenings for a movie
    @GetMapping("/movie/{movieId}")
    public ResponseEntity<List<Screening>> getScreeningsByMovieId(@PathVariable Long movieId){
        return ResponseEntity.ok(screeningService.getScreeningsByMovieId(movieId));
    }

    //tilføjer en ny film
    @PostMapping
    public ResponseEntity<Screening> createScreening(@RequestBody ScreeningRequest request){
        Screening newScreening = screeningService.createScreening(request);
        return ResponseEntity.created(URI.create("/screenings/" + newScreening.getId())).body(newScreening);
    }

    //redigerer en screening
    @PutMapping("/{id}")
    public ResponseEntity<Screening> updateScreening(@PathVariable Long id, @RequestBody Screening screening){

        Screening updatedScreening = screeningService.updateScreening(id,screening);
        return ResponseEntity.ok(updatedScreening);
    }

    //Sletter en screening
    @DeleteMapping("/{id}")
    public ResponseEntity<Screening> deleteScreening(@PathVariable Long id){
     screeningService.deleteScreening(id);
     return ResponseEntity.noContent().build();
    }






}
