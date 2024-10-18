package com.filmfellows.cinemates.domain.reservation.model.Service.Impl;

import com.filmfellows.cinemates.app.mypage.dto.myReservationRequest;
import com.filmfellows.cinemates.app.mypage.dto.myReservationResponse;
import com.filmfellows.cinemates.domain.reservation.model.Service.ReservationService;
import com.filmfellows.cinemates.domain.reservation.model.mapper.ReservationMapper;
import com.filmfellows.cinemates.domain.reservation.model.vo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReservationServiceImpl implements ReservationService {

    @Autowired
    private ReservationMapper rmapper;

    @Override
    public List<String> selectCinemas(String Address) {
        return rmapper.selectCinemas(Address);
    }

    @Override
    public List<String> selectMovies(String cinemaName) {
        return rmapper.selectMovies(cinemaName);
    }

    @Override
    public List<String> selectCinemaName() {
        return rmapper.selectCinemaName();
    }

    @Override
    public List<ShowInfoDTO> selectShowInfo(String cinemaName, String title) {
        return rmapper.selectShowInfo(cinemaName, title);
    }

    @Override
    public List<ReservationDTO> selectReservationSeat(String reservationDate) {
        return rmapper.selectReservationSeat(reservationDate);
    }

    @Override
    public MemberDTO selectMemberInfo(String memberId) {
        return rmapper.selectMemberInfo(memberId);
    }

    @Override
    public ShowInfoDTO selectMoviePoster(String title) {
        return rmapper.selectMoviePoster(title);
    }

    @Override
    public myReservationResponse selectReservationInfo(myReservationRequest request) {
        return rmapper.selectReservationInfo(request);
    }

    @Override
    public List<SearchMovieDTO> selectAllMovies() {
        return rmapper.selectAllMovies();
    }

    @Override
    public List<SearchLocationCodeDTO> selectAllLocationCode() {
        return rmapper.selectAllLocationCode();
    }

    @Override
    public List<String> selectCinemasByCode(Integer cinemaLocationCode) {
        return rmapper.selectCinemasByCode();
    }

    @Override
    public String getAgeRatingByTitle(String title) {
        return rmapper.getAgeRatingByTitle(title);
    }

    @Override
    public MemberDTO selectTicketCount(String memberId) {
        return rmapper.selectTicketCount(memberId);
    }

    @Override
    public List<String> selectTicketCountByIds(String memberIds) {
        return rmapper.selectTicketCountByIds(memberIds);
    }

}
