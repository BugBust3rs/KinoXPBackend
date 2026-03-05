package com.example.kinoxpbackend.Service;

import com.example.kinoxpbackend.Exception.NotfoundException;
import com.example.kinoxpbackend.Repository.MovieRepository;
import com.example.kinoxpbackend.Repository.ReservationRepository;
import com.example.kinoxpbackend.Repository.ScreeningRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class AdminService {

    private final MovieRepository movieRepository;
    private final ScreeningRepository screeningRepository;
    private final ReservationRepository reservationRepository;

    public AdminService(MovieRepository movieRepository,
                        ScreeningRepository screeningRepository,
                        ReservationRepository reservationRepository) {
        this.movieRepository = movieRepository;
        this.screeningRepository = screeningRepository;
        this.reservationRepository = reservationRepository;
    }

    public void removePost(Long id) {
        Optional<Post> post = postRepository.findById(id);
        if (post.isPresent()) {
            postRepository.delete(post.get());
            return;
        }
        throw new NotfoundException("Post Could not be found");
    }


}


