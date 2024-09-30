package com.filmfellows.cinemates.domain.reservation.model.mapper;

import com.filmfellows.cinemates.domain.reservation.model.vo.ReservationDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ReservationMapper {
    List<ReservationDTO> showReservationPage();

    int insertReservationInfo();
}
