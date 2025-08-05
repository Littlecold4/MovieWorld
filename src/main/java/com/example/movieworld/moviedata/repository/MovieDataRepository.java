package com.example.movieworld.moviedata.repository;

import com.example.movieworld.movie.domain.Movie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MovieDataRepository extends JpaRepository<Movie, Long> {
}
