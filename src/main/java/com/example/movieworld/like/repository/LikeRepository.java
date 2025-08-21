package com.example.movieworld.like.repository;

import com.example.movieworld.like.domain.Like;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LikeRepository extends JpaRepository<Long, Like> {
}
