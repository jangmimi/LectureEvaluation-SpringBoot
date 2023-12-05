package com.springproject.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Table(name="freeboard") // DB 테이블 이름 지정
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
public class Freeboard {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 자동 증가
    private Long id;

    @Column
    private String userId;

    @Column
    private String title;

    @Column
    private String content;

    @Column
    private String regDate;
}
