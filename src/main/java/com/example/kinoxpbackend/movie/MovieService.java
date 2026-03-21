package com.example.kinoxpbackend.movie;

import com.example.kinoxpbackend.exception.NotfoundException;
import com.example.kinoxpbackend.dto.MovieRequest;
import com.example.kinoxpbackend.mapper.MovieMapper;
import org.springframework.stereotype.Service;

import java.util.Base64;
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

    public Movie createMovie(MovieRequest request) {
        Movie movie = MovieMapper.requestToMovieMapper(request);
        if (request.image() != null) {
            String base64 = request.image().contains(",")
                    ? request.image().split(",")[1]
                    : request.image();
            movie.setImage(Base64.getDecoder().decode(base64));
        }


        return movieRepository.save(movie);
    }

    public Movie getMovieById(Long id) {
        Optional<Movie> movieOptional = movieRepository.findById(id);

        if(movieOptional.isEmpty()) {
            throw new NotfoundException("This movie does not exist");
        }
        return movieOptional.get();
    }

    public Movie updateMovie(Long id, Movie movie) {
        Optional<Movie> movieOptional = movieRepository.findById(id);
        if (movieOptional.isEmpty()) {
            throw new NotfoundException("This movie does not exist");
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
            throw new NotfoundException("This movie does not exist");
        }
        movieRepository.delete(movieOptional.get());
    }

    public void deleteAll() {
        movieRepository.deleteAll();
    }
}
