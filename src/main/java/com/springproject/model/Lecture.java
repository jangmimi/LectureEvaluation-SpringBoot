package com.springproject.model;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@Builder
@ToString
public class Lecture {
    private int lectureId;
    private String image;
    private String lectureSubject;
    private String lectureInfo;
    private String url;
}
