package com.springproject.model;

import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDateTime;

@Table(name="freeboard") // DB 테이블 이름 지정
@EntityListeners(AuditingEntityListener.class)
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@ToString
public class Freeboard {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 자동 증가
    private Long id;

    @Column
    private String title;

    @Column
    private String content;

    @Column
    @CreatedDate
    //@DateTimeFormat(pattern = "yyyy-MM-dd/HH:mm:ss")
    private LocalDateTime regDate;

    // userNumber를 외래 키로 설정
    @ManyToOne
    @JoinColumn(name = "userNumber")
    private User user;
}
