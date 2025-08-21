package com.example.movieworld.like.domain;

import com.example.movieworld.movie.domain.Movie;
import com.example.movieworld.user.domain.User;
import jakarta.persistence.*;

@Entity
@Table(name = "TB_LIKE")
public class Like {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "LIKE_ID")
    private Long likeId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_ID", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MOVIE_ID", nullable = false)
    private Movie movie;
}
