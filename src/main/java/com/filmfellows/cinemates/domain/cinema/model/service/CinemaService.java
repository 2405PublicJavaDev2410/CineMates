package com.filmfellows.cinemates.domain.cinema.model.service;

import com.filmfellows.cinemates.domain.cinema.model.vo.Cinema;
import com.filmfellows.cinemates.domain.cinema.model.vo.Screen;
import com.filmfellows.cinemates.domain.cinema.model.vo.Showtime;

import java.sql.Date;
import java.util.List;

public interface CinemaService {
    /**
     * 지역코드 검색
     * pram locationcode
     * return List<Cinema>

     */
    List<Cinema> locationsearch(int locationcode);
    /**
     * 극장 상세 검색
     * pram cinemaNo
     * return Cinema

     */
    Cinema onecinemasearch(int cinemaNo);
    /**
     * 극장 상영 정보조회
     * pram cinemaNo
     * return Cinema
     */
    List<Showtime> showtimelist(int cinemaNo);

    /**
     * 극장 전체 정보
     * pram cinemaNo
     * return List<Cinema>
     */
    List<Cinema> allcinema();
    /**
     * 극장 추가
     * pram cinema
     * return
     */
    int insertcinema(Cinema cinema);
    /**
     * 극장 수정
     * pram cinema
     * return result
     */
    int updatecinema(Cinema cinema);
    /**
     * 극장 삭제
     * pram cinemaNo
     * return result
     */
    int deletecinema(int cinemaNo);
    /**
     * 극장 상영관검색
     * pram cinemaNo
     * return Screen
     */
    List<Screen> onecinemascreen(int cinemaNo);
    /**
     * 상영일정 추가
     * pram showtime
     * return result
     */
    int showtimeinsert(Showtime showtime);
    /**
     * 상영관 추가
     * pram Screen
     * return result
     */
    int insertScreen(Screen screen);
    /**
     * 상영관 한개조회
     * pram screenNo
     * return Screen
     */
    Screen onescreen(int screenNo);
    /**
     * 상영관 수정
     * pram Screen
     * return result
     */
    int updatescreen(Screen screen);
    /**
     * 상영관 삭제
     * pram screenNo
     * return result
     */
    int deletescreen(int screenNo);
    /**
     * 전체 상영 일정
     * pram cinemaNo
     * return List<Showtime>
     */
    List<Showtime> allshowtimelist(int cinemaNo);
    /**
     * 상영일정 한개조회
     * pram showtimeNo
     * return showtime
     */
    Showtime oneshowtime(int showtimeNo);
    /**
     * 상영일정 수정
     * pram showtime
     * return result
     */
    int updateshowtime(Showtime showtime);
    /**
     * 상영일정 삭제
     * pram showtimeNo
     * return result
     */
    int deleteshowtime(int showtimeNo);
    /**
     * 상영일정 날짜조회
     * pram showtimeNo
     * return result
     */
    List<Showtime> showtimelistdate(int cinemaNo, Date selectDate);
}

