package com.example.kinoxpbackend.movie;

import org.springframework.data.jpa.repository.JpaRepository;

interface MovieRepository extends JpaRepository<Movie, Long> {
//    List<Movie> getAllBysc
}
