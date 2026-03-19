package com.example.kinoxpbackend.Repository;

import com.example.kinoxpbackend.Model.Movie;
import com.example.kinoxpbackend.Model.Screening;
import jakarta.persistence.Entity;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ScreeningRepository extends JpaRepository<Screening, Long> {
    @EntityGraph(attributePaths = "movie")
    @Query("Select s FROM Screening s where s.movie = :movie ")
    List<Screening> getScreeningsWithMovie(@Param("movie") Movie movie);

    List<Screening> getScreeningsByMovie(Movie movie);

}
