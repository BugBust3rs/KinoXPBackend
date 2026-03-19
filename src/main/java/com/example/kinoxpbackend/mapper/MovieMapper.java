package com.example.kinoxpbackend.mapper;

import com.example.kinoxpbackend.Model.Movie;
import com.example.kinoxpbackend.Model.Reservation;
import com.example.kinoxpbackend.Model.Screening;
import com.example.kinoxpbackend.dto.MovieRequest;
import com.example.kinoxpbackend.dto.MovieResponse;
import com.example.kinoxpbackend.dto.ReservationResponse;
import com.example.kinoxpbackend.dto.ScreeningRequest;

public class MovieMapper {
    public static Movie requestToMovieMapper(MovieRequest request){
        Movie movie= new Movie();
        movie.setAgeLimit(request.ageLimit());
        movie.setCategory(request.category());
        movie.setDescription(request.description());
        movie.setDurationMinutes(request.durationMinutes());
        movie.setTitle(request.title());
        return movie;
    }
   public static MovieResponse movieToMovieResponse(Movie movie){
        return new MovieResponse(
                movie.getTitle(),
                movie.getDurationMinutes(),
                movie.getDescription(),
                movie.getCategory(),
                movie.getAgeLimit(),
                movie.getImage()
        );
   }
}
