package com.filmfellows.cinemates.domain.reservation.model.Service.Impl;

import com.filmfellows.cinemates.app.mypage.dto.myReservationRequest;
import com.filmfellows.cinemates.app.mypage.dto.myReservationResponse;
import com.filmfellows.cinemates.domain.reservation.model.Service.ReservationService;
import com.filmfellows.cinemates.domain.reservation.model.mapper.ReservationMapper;
import com.filmfellows.cinemates.domain.reservation.model.vo.MemberDTO;
import com.filmfellows.cinemates.domain.reservation.model.vo.ReservationDTO;
import com.filmfellows.cinemates.domain.reservation.model.vo.ShowInfoDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class ReservationServiceImpl implements ReservationService {

    @Autowired
    private ReservationMapper rmapper;

    @Override
    public List<ReservationDTO> showReservationPage() {
        return rmapper.showReservationPage();
    }

//    @Override
//    public int insertReservationInfo(Map<String, Object> reserveInfo) {
//        return rmapper.insertReservationInfo(reserveInfo);
//    }

    @Override
    public List<ReservationDTO> showReservedSeats() {
        return rmapper.showReservedSeats();
    }

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
    public Map<String, Integer> getReservationCounts() {
        return rmapper.getReservationCounts();
    }

    @Override
    public ShowInfoDTO selectMoviePoster(String title) {
        return rmapper.selectMoviePoster(title);
    }

    @Override
    public myReservationResponse selectReservationInfo(myReservationRequest request) {
        return rmapper.selectReservationInfo(request);
    }


}
