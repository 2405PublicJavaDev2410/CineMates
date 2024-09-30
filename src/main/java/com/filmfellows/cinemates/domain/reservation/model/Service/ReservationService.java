package com.filmfellows.cinemates.domain.reservation.model.Service;

import com.filmfellows.cinemates.domain.reservation.model.vo.ReservationDTO;

import java.util.List;

public interface ReservationService {
    /**
     * 예매 페이지 보기
     * @return List<Reservation>
     */
    List<ReservationDTO> showReservationPage();

    /**
     * 예매 등록 하기
     *
     * @param rDTO
     * @return ReservationDTO
     */
    int insertReservationInfo(ReservationDTO rDTO);
}
