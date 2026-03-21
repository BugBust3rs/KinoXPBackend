package com.example.kinoxpbackend.cinema;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
interface HallRepository extends JpaRepository<Hall, Long> {
}