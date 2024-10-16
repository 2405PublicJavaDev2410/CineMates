package com.filmfellows.cinemates.domain.reservation.model.Service;

import com.filmfellows.cinemates.app.mypage.dto.myReservationRequest;
import com.filmfellows.cinemates.app.mypage.dto.myReservationResponse;
import com.filmfellows.cinemates.domain.cinema.model.vo.Showtime;
import com.filmfellows.cinemates.domain.reservation.model.vo.MemberDTO;
import com.filmfellows.cinemates.domain.reservation.model.vo.ReservationDTO;
import com.filmfellows.cinemates.domain.reservation.model.vo.ShowInfoDTO;

import java.util.List;
import java.util.Map;

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
    List<ReservationDTO> selectReservationSeat(String reservationDate);

    /**
     * 멤버 정보 조회
     *
     * @param memberId
     * @return String
     */
    MemberDTO selectMemberInfo(String memberId);

    /**
     * 예약 수 구하기
     * @return Map<String,Integer>
     */
    Map<String, Integer> getReservationCounts();

    /**
     * 영화 포스터 조회
     * @param title
     * @return
     */
    ShowInfoDTO selectMoviePoster(String title);

    /**
     * 예매번호로 예매 정보 조회
     *
     * @param request
     * @return
     */
    myReservationResponse selectReservationInfo(myReservationRequest request);

    /**
     * 영화 전체 목록 조회
     * @return
     */
    List<String> selectAllMovies();
}
