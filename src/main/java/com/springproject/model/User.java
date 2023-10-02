package com.springproject.model;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

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
}
