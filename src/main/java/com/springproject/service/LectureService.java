package com.springproject.service;

import com.springproject.model.Lecture;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@Service
public class LectureService {
    // 크롤링 대상 URL
    public static final String BASIC_URL = "https://nomadcoders.co/courses";

    // 크롤링 데이터를 캐싱할 맵 -> 성능 향상을 위해 캐싱 작업 추가 (중복 크롤링 방지, 대상 사이트 변경 있을 경우에 캐시 업데이트)
    private ConcurrentHashMap<Integer, List<Lecture>> cachedLectureData = new ConcurrentHashMap<>();

    // 크롤링 데이터 가져오기
    private List<Lecture> getCachedLectureData(int pageNumber, int pageSize) {
        return cachedLectureData.computeIfAbsent(pageNumber, this::crawlLectureData);
    }

    // productId를 위한 카운터 변수
    public int productIdCounter = 0;

    // 크롤링 데이터 처리
    public List<Lecture> crawlLectureData(int pageNumber) {
        // 크롤링 데이터를 담을 리스트 생성
        List<Lecture> lectureList = new ArrayList<>();

        try {
            // Jsoup을 사용해 현재 페이지 HTML 가져옴
            Document document = Jsoup.connect(BASIC_URL).get();

            // 원하는 데이터 선택 및 추출
            Elements contents = document.select("div.sc-f4fbfce1-0");

            productIdCounter = 0;

            for (Element content : contents) {
                String imageSrcset = content.select("img").attr("srcset");
                String imageUrl = parseImageSrcset(imageSrcset);

                Lecture lectures = buildLectureFromElement(content, imageUrl);
                lectureList.add(lectures);
            }
//            log.info(lectureList.toString());

        } catch (IOException e) {
            log.error("데이터 크롤링 오류 발생 : " + e.getMessage());
        }

        return lectureList;
    }

    private Lecture buildLectureFromElement(Element content, String imageUrl) {
        return Lecture.builder()
                .lectureId(++productIdCounter)  // 고유한 ID 생성
                .lectureSubject(content.select("a h3").text())
                .lectureInfo(content.select("a h4").text())
                .image("https://nomadcoders.co/" + imageUrl)
                .url(content.select(".info a").attr("abs:href"))
                .build();
    }

    // srcset 값을 파싱하여 이미지 URL 추출하는 함수
    private static String parseImageSrcset(String srcset) {
        String[] srcsetParts = srcset.split(" ");
        return srcsetParts[0];     // 첫 번째 이미지 URL 선택
    }

    // 페이지네이션
    public Page<Lecture> getLectureDataByPage(int pageNumber, Pageable pageable) {
        List<Lecture> lectureList = crawlLectureData(pageNumber);

        int pageSize = pageable.getPageSize();
        int startItem = pageNumber * pageSize;
        int endItem = Math.min(startItem + pageSize, lectureList.size());

        List<Lecture> pageLectures = lectureList.subList(startItem, endItem);

        return new PageImpl<>(pageLectures, pageable, lectureList.size());
    }
}
