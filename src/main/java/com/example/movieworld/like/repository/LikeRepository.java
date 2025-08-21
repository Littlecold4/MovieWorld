package com.example.movieworld.like.repository;

import com.example.movieworld.like.domain.Like;
import com.example.movieworld.movie.domain.Movie;
import com.example.movieworld.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LikeRepository extends JpaRepository<Like, Long> {
    boolean existsByUserAndMovie(User user, Movie movie);
}
