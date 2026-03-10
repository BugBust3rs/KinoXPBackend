package com.example.kinoxpbackend.config;

import com.example.kinoxpbackend.Model.*;
import com.example.kinoxpbackend.Repository.HallRepository;
import com.example.kinoxpbackend.Repository.MovieRepository;
import com.example.kinoxpbackend.Repository.ReservationRepository;
import com.example.kinoxpbackend.Repository.ScreeningRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Configuration
public class InitializeData {

    private byte[] loadImageBytes(String path) throws IOException {
        ClassPathResource resource = new ClassPathResource(path);
        return resource.getInputStream().readAllBytes();
    }

    @Bean
    CommandLineRunner initData(
            MovieRepository movieRepository,
            HallRepository hallRepository,
            ScreeningRepository screeningRepository,
            ReservationRepository reservationRepository
    ) {
        return args -> {

            // Optional: clear old data so you do not get duplicates on every startup
            reservationRepository.deleteAll();
            screeningRepository.deleteAll();
            hallRepository.deleteAll();
            movieRepository.deleteAll();

            // -------------------------
            // MOVIES
            // -------------------------
            List<Movie> movies = new ArrayList<>();

            movies.add(movieRepository.save(new Movie(
                    "Inception",
                    148,
                    "A thief who steals corporate secrets through dream-sharing technology.",
                    "Sci-Fi",
                    13,
                    loadImageBytes("images/inception.jpg.webp")
            )));

            movies.add(movieRepository.save(new Movie(
                    "The Dark Knight",
                    152,
                    "Batman faces the Joker, a criminal mastermind spreading chaos in Gotham.",
                    "Action",
                    13,
                    loadImageBytes("images/darkknight.webp")
            )));

            movies.add(movieRepository.save(new Movie(
                    "Interstellar",
                    169,
                    "A team travels through a wormhole in space to ensure humanity's survival.",
                    "Sci-Fi",
                    10,
                    loadImageBytes("images/Interstellar.jpg")
            )));

            movies.add(movieRepository.save(new Movie(
                    "Avengers: Endgame",
                    181,
                    "The Avengers assemble once more to reverse Thanos' actions.",
                    "Superhero",
                    11,
                    loadImageBytes("images/avengers-endgame-journey-s-end-i73600.jpg")
            )));

            movies.add(movieRepository.save(new Movie(
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
            smallHall = hallRepository.save(smallHall);

            Hall largeHall = new Hall();
            largeHall.setName("Large Hall");
            largeHall.setRows(25);
            largeHall.setCols(16);
            largeHall.setModularSeating(ModularSeating.COWBOYROW);
            largeHall = hallRepository.save(largeHall);

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

                    Screening screening = new Screening(
                            movie,
                            hall,
                            screeningTime,
                            basePrice,
                            is3D
                    );

                    screenings.add(screeningRepository.save(screening));
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

                // 1-3 seats per reservation
                int seatCount = (i % 3) + 1;
                double totalPrice = screening.getBasePrice() * seatCount;

                reservation.setPrice(totalPrice);
                reservation.setScreening(screening);

                for (int s = 0; s < seatCount; s++) {
                    Seat seat = new Seat();
                    seat.setSeatRow((i + s) % screening.getHall().getRows() + 1);
                    seat.setSeatColumn((s + 1) % screening.getHall().getCols() + 1);
                    reservation.addSeat(seat);
                }

                reservationRepository.save(reservation);
            }
        };
    }
}