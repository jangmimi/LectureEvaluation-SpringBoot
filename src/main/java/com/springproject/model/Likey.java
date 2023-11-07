package com.springproject.model;

import lombok.*;

import javax.persistence.*;

@Table(name="likey") // DB 테이블 이름 지정
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@ToString
public class Likey {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 자동 증가
    private Long id;

    @Column
    private Long evaluationId;

    // userNumber를 외래 키로 설정
    @ManyToOne
    @JoinColumn(name = "userNumber")
    private User user;
}
