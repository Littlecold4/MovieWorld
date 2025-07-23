package com.example.movieworld;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "TB_USER")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "USER_ID")
    private Long id;

    @Column(name = "USER_EMAIL",unique = true)
    private String userEmail;

    @Column(name = "USER_NAME",unique = true)
    private String userName;

    @Column(name = "PASSWORD")
    private String password;

//    @Column(name = "LIKE")
//    @OneToMany(mappedBy = "USER_ID", cascade = CascadeType.ALL, orphanRemoval = true)
//    private List<Like> likes = new ArrayList<>();
// mappedBy 값을 Like 엔티티의 user 필드 이름인 "user"로 수정
    // @Column(name = "LIKE") 제거
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Like> likes = new ArrayList<>();



}
