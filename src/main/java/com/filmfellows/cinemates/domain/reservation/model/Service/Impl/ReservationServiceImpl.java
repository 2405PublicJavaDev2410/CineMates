package com.filmfellows.cinemates.domain.reservation.model.Service.Impl;

import com.filmfellows.cinemates.domain.reservation.model.Service.ReservationService;
import com.filmfellows.cinemates.domain.reservation.model.mapper.ReservationMapper;
import com.filmfellows.cinemates.domain.reservation.model.vo.ReservationDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReservationServiceImpl implements ReservationService {

    @Autowired
    private ReservationMapper rmapper;

    @Override
    public List<ReservationDTO> showReservationPage() {
        return rmapper.showReservationPage();
    }

    @Override
    public int insertReservationInfo(ReservationDTO rDTO) {
        return rmapper.insertReservationInfo();
    }

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
    public List<String> selectShowInfo(String cinemaName, String title) {
        return rmapper.selectShowInfo(cinemaName,title);
    }


}
