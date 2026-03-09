package com.example.kinoxpbackend.Controller;

import com.example.kinoxpbackend.Model.Movie;
import com.example.kinoxpbackend.Service.MovieService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/movies")
public class MovieController {

    private final MovieService movieService;

    public MovieController(MovieService movieService) {
        this.movieService = movieService;
    }

    @GetMapping
    public List<Movie> getAllMovies() {
    movieService.getAllMovies();

        return  null;

    }

    @PostMapping
    public Movie createMovie() {

        return null;

    }
}
