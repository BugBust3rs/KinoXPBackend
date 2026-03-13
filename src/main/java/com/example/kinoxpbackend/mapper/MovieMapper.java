package com.example.kinoxpbackend.mapper;

import com.example.kinoxpbackend.Model.Movie;
import com.example.kinoxpbackend.Model.Screening;
import com.example.kinoxpbackend.dto.MovieRequest;
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
}
