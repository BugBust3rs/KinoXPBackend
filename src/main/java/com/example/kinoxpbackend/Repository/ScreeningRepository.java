package com.example.kinoxpbackend.Repository;

import com.example.kinoxpbackend.Model.Movie;
import com.example.kinoxpbackend.Model.Screening;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ScreeningRepository extends JpaRepository<Screening, Long> {
    List<Screening> getScreeningsByMovie(Movie movie);
}
