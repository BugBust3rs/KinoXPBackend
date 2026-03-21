package com.example.kinoxpbackend.screening;

import com.example.kinoxpbackend.movie.Movie;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

interface ScreeningRepository extends JpaRepository<Screening, Long> {
    @EntityGraph(attributePaths = "movie")
    @Query("Select s FROM Screening s where s.movie = :movie ")
    List<Screening> getScreeningsWithMovie(@Param("movie") Movie movie);

    List<Screening> getScreeningsByMovie(Movie movie);
    @Query("SELECT s FROM Screening s LEFT JOIN FETCH s.reservations WHERE s.id = :id")
    Optional<Screening> findByIdWithReservations(@Param("id") Long id);
}
