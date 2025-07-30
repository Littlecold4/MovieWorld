package com.example.movieworld.movie.repository;

import com.example.movieworld.movie.domain.Movie;
import com.example.movieworld.movie.repository.querydsl.MovieRepositoryQuerydsl;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MovieRepository extends JpaRepository<Movie,Long>, MovieRepositoryQuerydsl {
}
