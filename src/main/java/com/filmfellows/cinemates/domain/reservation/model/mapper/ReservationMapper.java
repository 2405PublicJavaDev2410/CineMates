package com.filmfellows.cinemates.domain.reservation.model.mapper;

import com.filmfellows.cinemates.domain.reservation.model.vo.ReservationDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface ReservationMapper {
    List<ReservationDTO> showReservationPage();

    int insertReservationInfo();

    void insertReservationInfo2(Map<String, Object> reserveInfo);

    List<ReservationDTO> showReservedSeats();
}
