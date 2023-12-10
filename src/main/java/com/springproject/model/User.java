package com.springproject.model;


import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.List;

@Table(name="user") // DB 테이블 이름 지정
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 자동 증가
    private Long userNumber;

    @NotBlank(message = "아이디는 필수 입력 값입니다.")
    @Column
    private String userId;

    @Column
    private String userPw;

    @Column
    private String userEmail;

    @Column
    private String userEmailHash;

    @Column
    private int userEmailChecked = 0;

    // Evaluation table과 일대다 관계 매핑
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Evaluation> evaluations;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Freeboard> freeboards;
}
