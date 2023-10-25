package com.springproject.model;

import lombok.*;

import javax.persistence.*;

@Table(name="evaluation_inflearn") // DB 테이블 이름 지정
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Entity
public class Evaluation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 자동 증가
    private Long id;
    @Column(length = 20)
    private Long userNumber;
    @Column(length = 50)
    private String lectureId;   // 강의번호
    @Column(length = 2048)
    private String lectureName;
    @Column(length = 50)
    private String lectureProfessor;
    @Column(length = 2048)
    private String lectureURL;
    @Column(length = 50)
    private String evaluationTitle;
    @Column(length = 2048)
    private String evaluationContent;
    @Column
    private int likeCount;
}
