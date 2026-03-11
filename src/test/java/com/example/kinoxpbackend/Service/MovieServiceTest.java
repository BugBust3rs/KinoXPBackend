package com.example.kinoxpbackend.Service;

import com.example.kinoxpbackend.Model.Movie;
import com.example.kinoxpbackend.Repository.MovieRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class MovieServiceTest {

    @Mock
    private MovieRepository movieRepository;

    @InjectMocks
    private MovieService movieService;

    @Test
    void shouldGetMovieById() {
        Long id = 1L;

        Movie movie = new Movie(
                "Inception",
                148,
                "A thief who steals corporate secrets through dream-sharing technology.",
                "Sci-Fi",
                13,
                null
        );

        when(movieRepository.findById(id)).thenReturn(Optional.of(movie));

        Movie movieTest = movieService.getMovieById(id);

        assertEquals(movie.getTitle(), movieTest.getTitle());
        assertEquals(movie.getDurationMinutes(), movieTest.getDurationMinutes());
        assertEquals(movie.getDescription(), movieTest.getDescription());
        assertEquals(movie.getCategory(), movieTest.getCategory());
        assertEquals(movie.getAgeLimit(), movieTest.getAgeLimit());
    }
}