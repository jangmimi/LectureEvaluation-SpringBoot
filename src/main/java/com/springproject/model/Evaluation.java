package com.springproject.model;

import lombok.*;

import javax.persistence.*;

@Table(name="evaluation") // DB 테이블 이름 지정
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
public class Evaluation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 자동 증가
    private Long id;

    @Column(length = 50)
    private int lectureId;

    @Column(length = 2048)
    private String lectureSubject;

    @Column(length = 50)
    private String lectureInfo;

    @Column(length = 2048)
    private String lectureURL;

    @Column(length = 50)
    private String evaluationTitle;

    @Column(length = 2048)
    private String evaluationContent;

    @Column
    private int likeCount;

    // userNumber를 외래 키로 설정
    @ManyToOne
    @JoinColumn(name = "userNumber")
    private User user;
}
