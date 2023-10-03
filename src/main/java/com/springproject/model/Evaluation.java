package com.springproject.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
    private int evaluationID;
    @Column(length = 20)
    private String userId;
    @Column(length = 50)
    private String lectureName;
    @Column(length = 20)
    private String professorName;
    @Column
    private int lectureYear;
    @Column(length = 20)
    private String semesterDivide;
    @Column(length = 10)
    private String lectureDivide;
    @Column(length = 50)
    private String evaluationTitle;
    @Column(length = 2048)
    private String evaluationContent;
    @Column(length = 5)
    private String totalScore;
    @Column(length = 5)
    private String creditScore;
    @Column(length = 5)
    private String comportableScore;
    @Column(length = 5)
    private String lectureScore;
    @Column
    private int likeCount;
}
