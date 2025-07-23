package com.example.movieworld;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "TB_GENRE")
public class Genre {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "GENRE_ID")
    private Long genreId;

    @Column(name = "GENRE_NAME")
    private String genreName;

//    @ManyToMany(mappedBy = "GENRE_ID")
//    @Column(name = "MOVIE")
//    private List<Movie> movieList;

    // mappedBy 값을 Movie 엔티티의 genre 필드 이름인 "genre"로 수정
    // @Column(name = "MOVIE") 제거
    // 양방향 @ManyToMany에서 mappedBy를 사용하는 쪽은 연관 관계의 주인이 아닙니다.
    @ManyToMany(mappedBy = "genre") // Movie 엔티티의 genre 필드에 의해 매핑됨
    private List<Movie> movieList = new ArrayList<>(); // 초기화 추가 (좋은 습관)

}
