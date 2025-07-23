package com.example.movieworld.movie;

import com.example.movieworld.Genre;
import com.example.movieworld.Like;
import com.example.movieworld.config.MovieDataDto;
import com.example.movieworld.config.MovieInputDto;
import jakarta.persistence.*;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "TB_MOVIE")
@NoArgsConstructor
public class Movie {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "MOVIE_ID")
    private Long movieId;

    @Column(name = "TITLE")
    private String title;

    @Column(name = "OVERVIEW",length = 1000)
    private String overview;

    @Column(name = "ADULT")
    private boolean adult;

    @Column(name = "RELEASE_DATE")
    private String releaseDate;

    @Column(name = "POSTER_PATH")
    private String posterPath;

//    @ManyToMany(mappedBy = "MOVIE_ID")
//    @Column(name = "GENRE")
//    private List<Genre> genre;
//
//    @OneToMany(mappedBy = "MOVIE_ID", cascade = CascadeType.ALL, orphanRemoval = true)
//    @Column(name = "LIKE")
//    private List<Like> likes = new ArrayList<>();

    // Movie를 연관 관계의 주인으로 설정하고 @JoinTable 사용
    // mappedBy 값을 Genre 엔티티의 movieList 필드 이름인 "movieList"로 수정 (현재는 이쪽이 주인이므로 mappedBy 없음)
    // @Column(name = "GENRE") 제거
    @ManyToMany
    @JoinTable(
            name = "TB_MOVIE_GENRE", // 중간 테이블 이름
            joinColumns = @JoinColumn(name = "MOVIE_ID"), // Movie 엔티티의 FK
            inverseJoinColumns = @JoinColumn(name = "GENRE_ID") // Genre 엔티티의 FK
    )
    private List<Genre> genre = new ArrayList<>(); // 초기화 추가

    // mappedBy 값을 Like 엔티티의 movie 필드 이름인 "movie"로 수정
    // @Column(name = "LIKE") 제거
    @OneToMany(mappedBy = "movie", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Like> likes = new ArrayList<>();

    public Movie(MovieDataDto movieDataDto){
        this.adult = movieDataDto.isAdult();
        this.overview =movieDataDto.getOverview();
        this.posterPath = movieDataDto.getPoster_path();
        this.releaseDate = movieDataDto.getRelease_date();
        this.title = movieDataDto.getTitle();
    }




}
