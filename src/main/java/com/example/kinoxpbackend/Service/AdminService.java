package com.example.kinoxpbackend.Service;

import com.example.kinoxpbackend.Exception.NotfoundException;
import com.example.kinoxpbackend.Model.Movie;
import com.example.kinoxpbackend.Repository.MovieRepository;
import com.example.kinoxpbackend.Repository.ScreeningRepository;
import org.springframework.stereotype.Service;

@Service
public class AdminService {

    private final MovieRepository movieRepository;
    private final ScreeningRepository screeningRepository;

    private static final String ADMIN_USERNAME = "admin";
    private static final String ADMIN_PASSWORD = "password123";

    public AdminService(MovieRepository movieRepository,
                        ScreeningRepository screeningRepository) {
        this.movieRepository = movieRepository;
        this.screeningRepository = screeningRepository;
    }

    public boolean getAdmin(String username, String password) {
        return ADMIN_USERNAME.equals(username) && ADMIN_PASSWORD.equals(password);
    }

    public void removeMovie(Long id) {
        Movie movie = movieRepository.findById(id)
                .orElseThrow(() -> new NotfoundException("Movie not found"));
        movieRepository.delete(movie);
    }
}
