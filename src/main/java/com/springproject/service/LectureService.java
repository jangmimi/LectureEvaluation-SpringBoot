package com.springproject.service;

import com.springproject.model.Lecture;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class LectureService {
    // 크롤링 대상 URL (인프런)
    public static final String basicURL = "https://nomadcoders.co/courses";
//    public static final String basicURL = "https://www.inflearn.com/courses?types=ONLINE&page=";

    /**
     * 크롤링 데이터 배열 생성
     * */
    public List<Lecture> crawlLectureData(String url) throws IOException {
        // 크롤링 데이터 담는 리스트
        List<Lecture> lectureList = new ArrayList<>();

        // Jsoup을 사용해 현재 페이지 HTML 가져옴
        Document document = Jsoup.connect(basicURL).get();

        // 원하는 데이터 선택 및 추출
        Elements contents = document.select("div.sc-f4fbfce1-0");
//        log.info(contents.toString());
        for (Element content : contents) {
            String imageSrcset = content.select("img").attr("srcset");
            // srcset 값을 파싱하여 이미지 URL을 추출
            String imageUrl = parseImageSrcset(imageSrcset);

            // 데이터 추출 및 처리
            Lecture lectures = Lecture.builder()
                    .productId(content.select(".card").attr("data-productid"))
                    .image("https://nomadcoders.co/" + imageUrl)
                    .subject(content.select("a h3").text())
                    .professor(content.select("a h4").text())
                    .url(content.select(".info a").attr("abs:href"))
                    .reviewCnt(content.select(".card-content .review_cnt").text())
                    .build();
            lectureList.add(lectures);
        }

//        for (Element content : contents) {
//            // 데이터 추출 및 처리
//            Lecture lectures = Lecture.builder()
//                    .productId(content.select(".card").attr("data-productid"))
//                    .image(content.select("a img").attr("abs:src"))
//                    .subject(content.select(".card-content .course_title").text())
//                    .professor(content.select(".card-content .instructor").text())
//                    .url(content.select(".card a").attr("abs:href"))
//                    .reviewCnt(content.select(".card-content .review_cnt").text())
//                    .build();
//            lectureList.add(lectures);
//        }

//        log.info(lectureList.toString());
        return lectureList;
    }

    // srcset 값을 파싱하여 이미지 URL 추출하는 함수
    private static String parseImageSrcset(String srcset) {
        String[] srcsetParts = srcset.split(" ");
        String imageUrl = srcsetParts[0]; // 첫 번째 이미지 URL 선택
        return imageUrl;
    }

    /**
     * 페이지네이션
     * */
    public Page<Lecture> getLectureDataByPage(int pageNumber, Pageable pageable) throws IOException {
        List<Lecture> lectureList = crawlLectureData(basicURL);

        int pageSize = pageable.getPageSize();
        int startItem = pageNumber * pageSize;
        int endItem = Math.min(startItem + pageSize, lectureList.size());

        List<Lecture> pageLectures = lectureList.subList(startItem, endItem);

        return new PageImpl<>(pageLectures, pageable, lectureList.size()); // pageNumber 사용

//        List<Lecture> lectureList = getLectureData(pageNumber);
//
//        int currentPage = pageable.getPageNumber() + 1;
//        int pageSize = pageable.getPageSize();
//
//        int startIndex = (currentPage - 1) * pageSize;
//        int endIndex = Math.min(startIndex + pageSize, lectureList.size());
//
//        List<Lecture> pageLectures = lectureList.subList(startIndex, endIndex);
//
//        return new PageImpl<>(pageLectures, pageable, lectureList.size());
    }
}
