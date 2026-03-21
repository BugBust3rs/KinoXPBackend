package com.example.kinoxpbackend.config;

import com.example.kinoxpbackend.cinema.*;
import com.example.kinoxpbackend.dto.MovieRequest;
import com.example.kinoxpbackend.dto.ReservationRequest;
import com.example.kinoxpbackend.dto.ScreeningRequest;
import com.example.kinoxpbackend.mapper.ReservationMapper;
import com.example.kinoxpbackend.mapper.ScreeningMapper;
import com.example.kinoxpbackend.movie.Movie;
import com.example.kinoxpbackend.movie.MovieService;
import com.example.kinoxpbackend.reservation.Reservation;
import com.example.kinoxpbackend.reservation.ReservationService;
import com.example.kinoxpbackend.screening.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

@Configuration
public class InitializeData {

    private String loadImageBytes(String path) throws IOException {
        ClassPathResource resource = new ClassPathResource(path);
        byte[] bytes = resource.getInputStream().readAllBytes();
        return Base64.getEncoder().encodeToString(bytes);
    }

    @Bean
    CommandLineRunner initData(
            MovieService movieService,
            HallService hallService,
            ScreeningService screeningService,
            ReservationService reservationService
    ) {
        return args -> {

            // Optional: clear old data so you do not get duplicates on every startup
            reservationService.deleteAll();
            screeningService.deleteAll();
            hallService.deleteAll();
            movieService.deleteAll();

            // -------------------------
            // MOVIES
            // -------------------------
            List<Movie> movies = new ArrayList<>();

            movies.add(movieService.createMovie(new MovieRequest(
                    "Inception",
                    148,
                    "A thief who steals corporate secrets through dream-sharing technology.",
                    "Sci-Fi",
                    13,
                    loadImageBytes("images/inception.jpg.webp")
            )));

            movies.add(movieService.createMovie(new MovieRequest(
                    "The Dark Knight",
                    152,
                    "Batman faces the Joker, a criminal mastermind spreading chaos in Gotham.",
                    "Action",
                    13,
                    loadImageBytes("images/darkknight.webp")
            )));

            movies.add(movieService.createMovie(new MovieRequest(
                    "Interstellar",
                    169,
                    "A team travels through a wormhole in space to ensure humanity's survival.",
                    "Sci-Fi",
                    10,
                    loadImageBytes("images/Interstellar.jpg")
            )));

            movies.add(movieService.createMovie(new MovieRequest(
                    "Avengers: Endgame",
                    181,
                    "The Avengers assemble once more to reverse Thanos' actions.",
                    "Superhero",
                    11,
                    loadImageBytes("images/avengers-endgame-journey-s-end-i73600.jpg")
            )));

            movies.add(movieService.createMovie(new MovieRequest(
                    "The Lion King",
                    88,
                    "A young lion prince flees his kingdom only to learn the true meaning of responsibility.",
                    "Animation",
                    7,
                    loadImageBytes("images/The-Lion-King-Vintage-Movie-Poster-Original_3b44df74_5000x.jpg")
            )));

            // -------------------------
            // HALLS
            // -------------------------
            Hall smallHall = new Hall();
            smallHall.setName("Small Hall");
            smallHall.setRows(20);
            smallHall.setCols(12);
            smallHall.setModularSeating(ModularSeating.BASICROW);
            smallHall = hallService.createHall(smallHall);

            Hall largeHall = new Hall();
            largeHall.setName("Large Hall");
            largeHall.setRows(25);
            largeHall.setCols(16);
            largeHall.setModularSeating(ModularSeating.COWBOYROW);
            largeHall = hallService.createHall(largeHall);

            List<Hall> halls = List.of(smallHall, largeHall);

            // -------------------------
            // SCREENINGS
            // 4 screenings per movie
            // -------------------------
            List<Screening> screenings = new ArrayList<>();

            LocalDateTime baseDate = LocalDateTime.now()
                    .withHour(12)
                    .withMinute(0)
                    .withSecond(0)
                    .withNano(0);

            int[] hours = {12, 15, 18, 21};

            for (int i = 0; i < movies.size(); i++) {
                Movie movie = movies.get(i);

                for (int j = 0; j < 4; j++) {
                    Hall hall = halls.get((i + j) % halls.size());
                    boolean is3D = (j % 2 == 1);

                    double basePrice = hall.getName().equals("Large Hall") ? 110.0 : 90.0;
                    if (is3D) {
                        basePrice += 25.0;
                    }

                    LocalDateTime screeningTime = baseDate
                            .plusDays(i)
                            .withHour(hours[j])
                            .withMinute(0);

                    ScreeningRequest screening = new ScreeningRequest(
                            movie.getId(),
                            hall.getId(),
                            screeningTime,
                            basePrice,
                            is3D
                    );


                    screenings.add(screeningService.createScreening(screening));
                }
            }

            // -------------------------
            // 10 RESERVATIONS
            // -------------------------
            for (int i = 0; i < 10; i++) {
                Screening screening = screenings.get(i % screenings.size());

                Reservation reservation = new Reservation();
                reservation.setCustomerName("Customer " + (i + 1));
                reservation.setCustomerEmail("customer" + (i + 1) + "@mail.com");
                reservation.setCreationDate(LocalDateTime.now().minusDays(i));

                int seatCount = (i % 3) + 1;
                double totalPrice = screening.getBasePrice() * seatCount;

                reservation.setPrice(totalPrice);
                screening.addReservation(reservation);

                for (int s = 0; s < seatCount; s++) {
                    int row = (i + s) % screening.getHall().getRows() + 1;
                    int col = (s + 1) % screening.getHall().getCols() + 1;

                    Seat seatToReserve = screening.getSeats().stream()
                            .filter(seat -> seat.getSeatRow() == row && seat.getSeatColumn() == col)
                            .findFirst()
                            .orElseThrow(() -> new RuntimeException(
                                    "Seat not found: row " + row + ", col " + col));

                    seatToReserve.setReserved(true);
                    seatToReserve.setReservation(reservation);
                    reservation.addSeat(seatToReserve);
                }
                List<Long> seatIds = reservation.getSeats().stream()
                        .map(Seat::getId)
                        .collect(Collectors.toList());
                ReservationRequest request = new ReservationRequest(
                        reservation.getCustomerName(),
                        reservation.getCustomerEmail(),
                        reservation.getCreationDate(),
                        reservation.getPrice(),
                        reservation.getScreening().getId(),
                        seatIds
                );

                ScreeningRequest screeningRequest = new ScreeningRequest(
                        screening.getMovie().getId(),
                        screening.getHall().getId(),
                        screening.getStartTime(),
                        screening.getBasePrice(),
                        true
                );
                reservationService.createReservation(request);
                screeningService.createScreening(screeningRequest);

            }
        };
    }
}