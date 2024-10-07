package com.filmfellows.cinemates.domain.reservation.model.Service;

import com.filmfellows.cinemates.domain.reservation.model.vo.ReservationDTO;
import com.filmfellows.cinemates.domain.reservation.model.vo.ShowInfoDTO;

import java.util.List;

public interface ReservationService {
    /**
     * 예매 페이지 보기
     * @return List<Reservation>
     */
    List<ReservationDTO> showReservationPage();

//    /**
//     * 예매 등록 하기
//     *
//     * @param rDTO
//     * @return ReservationDTO
//     */
//    int insertReservationInfo(ReservationDTO rDTO);

    /**
     * 예약 좌석 조회하기
     *
     * @return List<Integer>
     */
    List<ReservationDTO> showReservedSeats();

    /**
     * 상영관 조회
     *
     * @param Address
     * @return List<ReservationDTO>
     */
    List<String> selectCinemas(String Address);

    /**
     * 영화 조회
     * @param cinemaName
     * @return List<String>
     */
    List<String> selectMovies(String cinemaName);

    /**
     * 극장 이름 조회
     *
     * @return ist<ReservationDTO>
     */
    List<String> selectCinemaName();

    /**
     * 극장,영화제목으로 상영시간 출력
     *
     * @param cinemaName
     * @param title
     * @return List<String>
     */
    List<ShowInfoDTO> selectShowInfo(String cinemaName, String title);

    /**
     * 예약 좌석 조회
     *
     * @return List<ShowInfoDTO>
     */
    List<ReservationDTO> selectReservationSeat();
}
