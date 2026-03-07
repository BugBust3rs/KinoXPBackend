package com.example.kinoxpbackend.Service;

import com.example.kinoxpbackend.Model.Movie;
import com.example.kinoxpbackend.Model.Screening;
import com.example.kinoxpbackend.Repository.MovieRepository;
import exceptions.NotFoundException;
import org.jspecify.annotations.Nullable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MovieService {

    private final MovieRepository movieRepository;

    public MovieService(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

    public List<Movie> getAllMovies() {
        return movieRepository.findAll();
    }

    public Movie createMovie(Movie movie) {
        return movieRepository.save(movie);
    }

    public Movie getMovieById(Long id) {
        Optional<Movie> movieOptional = movieRepository.findById(id);

        if(movieOptional.isEmpty()) {
            throw new NotFoundException("This movie does not exist");
        }
        return movieOptional.get();
    }

    public Movie updateMovie(Long id, Movie movie) {
        Optional<Movie> movieOptional = movieRepository.findById(id);
        if (movieOptional.isEmpty()) {
            throw new NotFoundException("This movie does not exist");
        }
        Movie existingMovie = movieOptional.get();
        existingMovie.setTitle(movie.getTitle());
        existingMovie.setDurationMinutes(movie.getDurationMinutes());
        existingMovie.setDescription(movie.getDescription());
        existingMovie.setCategory(movie.getCategory());
        existingMovie.setAgeLimit(movie.getAgeLimit());
        existingMovie.setImage(movie.getImage());

        return movieRepository.save(existingMovie);
    }

    public void deleteMovie(Long id) {
        Optional<Movie> movieOptional = movieRepository.findById(id);

        if (movieOptional.isEmpty()) {
            throw new NotFoundException("This movie does not exist");
        }
        movieRepository.deleteById(id);
    }
}
