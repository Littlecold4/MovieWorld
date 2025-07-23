package com.example.movieworld.movie;

import com.example.movieworld.movie.querydsl.MovieRepositoryQuerydsl;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MovieRepository extends JpaRepository<Movie,Long>, MovieRepositoryQuerydsl {
}
