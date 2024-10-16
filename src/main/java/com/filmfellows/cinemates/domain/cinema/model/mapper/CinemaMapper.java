package com.filmfellows.cinemates.domain.cinema.model.mapper;

import com.filmfellows.cinemates.domain.cinema.model.vo.Cinema;
import com.filmfellows.cinemates.domain.cinema.model.vo.Screen;
import com.filmfellows.cinemates.domain.cinema.model.vo.Showtime;
import com.filmfellows.cinemates.domain.movie.model.vo.Movie;
import org.apache.ibatis.annotations.Mapper;

import java.sql.Date;
import java.util.List;

@Mapper
public interface CinemaMapper {

    List<Cinema> locationsearch(int locationcode);

    Cinema onecinemasearch(int cinemaNo);

    List<Showtime> showtimelist(int cinemaNo);

    List<Cinema> allcinema();

    int insertcinema(Cinema cinema);

    int updatecinema(Cinema cinema);

    int deletecinema(int cinemaNo);

    List<Screen> onecinemascreen(int cinemaNo);

    int showtimeinsert(Showtime showtime);

    int insertScreen(Screen screen);

    Screen onescreen(int screenNo);

    int updatescreen(Screen screen);

    int deletescreen(int screenNo);

    List<Showtime> allshowtimelist(int cinemaNo);

    Showtime oneshowtime(int showtimeNo);

    int updateshowtime(Showtime showtime);

    int deleteshowtime(int showtimeNo);

    List<Showtime> showtimelistdate(int cinemaNo, Date selectDate);

    List<Showtime> showtimelistcurrent(int cinemaNo);

    List<Movie> searchmovie();
}
