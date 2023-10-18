package com.springproject.model;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@Builder
@ToString
public class Lecture {
    private String image;
    private String subject;
    private String professor;
    private String url;
}
