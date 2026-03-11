package com.example.kinoxpbackend.Service;

import com.example.kinoxpbackend.Model.*;
import com.example.kinoxpbackend.Repository.ReservationRepository;
import com.example.kinoxpbackend.Repository.ScreeningRepository;
import com.example.kinoxpbackend.Repository.SeatRepository;
import com.example.kinoxpbackend.dto.ReservationRequest;
import com.example.kinoxpbackend.dto.ReservationResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ReservationServiceTest {

    @Mock
    private ReservationRepository reservationRepository;

    @Mock
    private ScreeningRepository screeningRepository;

    @Mock
    private SeatRepository seatRepository;

    @InjectMocks
    private ReservationService reservationService;

    @Test
    void createReservation() {
        LocalDateTime now = LocalDateTime.now();
        double price = 300;

        ReservationRequest request = new ReservationRequest(
                "peter",
                "peter@marbelii.dk",
                now,
                price,
                1L,
                new ArrayList<>(List.of(1L, 2L))
        );

        Movie movie = new Movie(
                "Inception",
                148,
                "A thief who steals corporate secrets through dream-sharing technology.",
                "Sci-Fi",
                13,
                null
        );

        Hall largeHall = new Hall();
        largeHall.setName("Large Hall");
        largeHall.setRows(25);
        largeHall.setCols(16);
        largeHall.setModularSeating(ModularSeating.COWBOYROW);

        Screening screening = new Screening(
                movie,
                largeHall,
                now,
                150D,
                true
        );

        Seat seat1 = new Seat();
        Seat seat2 = new Seat();

        when(screeningRepository.findById(request.screeningId()))
                .thenReturn(Optional.of(screening));

        when(seatRepository.findById(1L))
                .thenReturn(Optional.of(seat1));

        when(seatRepository.findById(2L))
                .thenReturn(Optional.of(seat2));

        when(reservationRepository.save(any(Reservation.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        ReservationResponse response = reservationService.createReservation(request);

        assertEquals(request.price(), response.price());
    }
}