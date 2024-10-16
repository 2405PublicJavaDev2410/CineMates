package com.filmfellows.cinemates.domain.cinema.model.service.impl;

import com.filmfellows.cinemates.domain.cinema.model.service.CinemaService;
import com.filmfellows.cinemates.domain.cinema.model.mapper.CinemaMapper;
import com.filmfellows.cinemates.domain.cinema.model.vo.Cinema;
import com.filmfellows.cinemates.domain.cinema.model.vo.Screen;
import com.filmfellows.cinemates.domain.cinema.model.vo.Showtime;
import com.filmfellows.cinemates.domain.movie.model.vo.Movie;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;


@Service
public class CinemaServiceImpl implements CinemaService {

    private CinemaMapper mapper;
    @Autowired
    public CinemaServiceImpl(CinemaMapper mapper) {
        this.mapper=mapper;
    }

    @Override
    public List<Cinema> locationsearch(int locationcode) {

        List<Cinema> cList=mapper.locationsearch(locationcode);

        return cList;
    }

    @Override
    public Cinema onecinemasearch(int cinemaNo) {
        Cinema cinema = mapper.onecinemasearch(cinemaNo);
        return cinema;
    }

    @Override
    public List<Showtime> showtimelist(int cinemaNo) {
        List<Showtime> sList=mapper.showtimelist(cinemaNo);
        return sList;
    }

    @Override
    public List<Cinema> allcinema() {
        List<Cinema> cList=mapper.allcinema();
        return cList;
    }

    @Override
    public int insertcinema(Cinema cinema) {
        int result=mapper.insertcinema(cinema);
        return result;
    }

    @Override
    public int updatecinema(Cinema cinema) {
        int result=mapper.updatecinema(cinema);
        return result;
    }

    @Override
    public int deletecinema(int cinemaNo) {
        int result=mapper.deletecinema(cinemaNo);
        return result;
    }

    @Override
    public List<Screen> onecinemascreen(int cinemaNo) {
        List<Screen> scrList=mapper.onecinemascreen(cinemaNo);
        return scrList;
    }

    @Override
    public int showtimeinsert(Showtime showtime) {
        int result =mapper.showtimeinsert(showtime);
        return result;
    }

    @Override
    public int insertScreen(Screen screen) {
        int result =mapper.insertScreen(screen);
        return result;
    }

    @Override
    public Screen onescreen(int screenNo) {
        Screen screen=mapper.onescreen(screenNo);
        return screen;
    }

    @Override
    public int updatescreen(Screen screen) {
        int result = mapper.updatescreen(screen);
        return result;
    }

    @Override
    public int deletescreen(int screenNo) {
        int result = mapper.deletescreen(screenNo);
        return result;
    }

    @Override
    public List<Showtime> allshowtimelist(int cinemaNo) {
        List<Showtime> sList=mapper.allshowtimelist(cinemaNo);
        return sList;
    }

    @Override
    public Showtime oneshowtime(int showtimeNo) {
        Showtime showtime=mapper.oneshowtime(showtimeNo);
        return showtime;
    }

    @Override
    public int updateshowtime(Showtime showtime) {
        int result =mapper.updateshowtime(showtime);
        return result;
    }

    @Override
    public int deleteshowtime(int showtimeNo) {
        int result =mapper.deleteshowtime(showtimeNo);
        return result;
    }

    @Override
    public List<Showtime> showtimelistdate(int cinemaNo, Date selectDate) {
        List<Showtime> sList=mapper.showtimelistdate(cinemaNo,selectDate);
        return sList;
    }

    @Override
    public List<Showtime> showtimelistcurrent(int cinemaNo) {
        List<Showtime> sList=mapper.showtimelistcurrent(cinemaNo);
        return sList;
    }

    @Override
    public List<Movie> searchmovie() {
        List<Movie> mList=mapper.searchmovie();
        return mList;
    }
}
