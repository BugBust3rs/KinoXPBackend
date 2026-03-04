package com.example.kinoxpbackend.Service;

import com.example.kinoxpbackend.Model.Movie;
import com.example.kinoxpbackend.Repository.MovieRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MovieService {

    private final MovieRepository movieRepository;

    public MovieService(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

    public List<Movie> getAllMovies() {
        return movieRepository.findAll();
    }
}
