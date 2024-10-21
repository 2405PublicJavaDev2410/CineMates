package com.filmfellows.cinemates.domain.reservation.model.Service;

import com.filmfellows.cinemates.app.mypage.dto.myReservationRequest;
import com.filmfellows.cinemates.app.mypage.dto.myReservationResponse;
import com.filmfellows.cinemates.domain.reservation.model.vo.*;

import java.util.List;

public interface ReservationService {

    /**
     * 상영관 조회
     *
     * @param Address
     * @return List<ReservationDTO>
     */
    List<String> selectCinemas(String Address);

    /**
     * 영화 조회
     *
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
     * 영화 포스터 조회
     *
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
     *
     * @return
     */
    List<SearchMovieDTO> selectAllMovies();

    /**
     * 지역 코드 조회
     *
     * @return
     */
    List<SearchLocationCodeDTO> selectAllLocationCode();

    /**
     * 극장 정보 코드로 조회
     *
     * @param cinemaLocationCode
     * @return
     */
    List<String> selectCinemasByCode(Integer cinemaLocationCode);

    /**
     * 영화 제목으로 연령대 조회
     * @param title
     * @return
     */
    String getAgeRatingByTitle(String title);

    /**
     * 멤버 아이디 통해 티켓 조회
     *
     * @param memberId
     * @return
     */
    Integer selectTicketCount(String memberId);

    /**
     * 대화 방 인원 티켓 수
     * @param memberIds
     * @return
     */
    List<String> selectTicketCountByIds(String memberIds);

}
