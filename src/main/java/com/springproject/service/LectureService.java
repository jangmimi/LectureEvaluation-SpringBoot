package com.springproject.service;

import com.springproject.model.Lecture;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class LectureService {
    private static String lecture_URL = "https://www.inflearn.com/courses?order=seq";

    @PostConstruct
    public List<Lecture> getLectureData() throws IOException {
        List<Lecture> lectureList = new ArrayList<>();
        Document document = Jsoup.connect(lecture_URL).get();

        Elements contents = document.select("div.card");
//        log.info(contents.toString());

        for (Element content : contents) {
            Lecture lectures = Lecture.builder()
                    .image(content.select("a img").attr("abs:src"))
                    .subject(content.select(".card-content .course_title").text())
                    .professor(content.select(".card-content .instructor").text())
                    .url(content.select(".card a").attr("abs:href"))
                    .build();
            lectureList.add(lectures);
        }
        log.info(lectureList.toString());
        return lectureList;
    }

}
