package com.filmfellows.cinemates.domain.reservation.model.mapper;

import com.filmfellows.cinemates.app.mypage.dto.myReservationRequest;
import com.filmfellows.cinemates.app.mypage.dto.myReservationResponse;
import com.filmfellows.cinemates.domain.reservation.model.vo.*;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface ReservationMapper {
    List<ReservationDTO> showReservationPage();

    void insertReservationInfo(Map<String, Object> reserveInfo);

    List<ReservationDTO> showReservedSeats();

    List<String> selectCinemas(String address);

    List<String> selectMovies(String cinemaName);

    List<ShowInfoDTO> selectShowInfo(String cinemaName, String title);

    List<ReservationDTO> selectReservationSeat(String reservationDate);

    MemberDTO selectMemberInfo(String memberId);

    Map<String, Integer> getReservationCounts();

    List<String> selectCinemaName();

    ShowInfoDTO selectMoviePoster(String title);

    myReservationResponse selectReservationInfo(myReservationRequest request);


    void deleteReservationInfo(String impUid);

    List<SearchMovieDTO> selectAllMovies();

    List<SearchLocationCodeDTO> selectAllLocationCode();

    List<String> selectCinemasByCode();

    String getAgeRatingByTitle(String title);

    MemberDTO selectTicketCount(String memberId);

    List<String> selectTicketCountByIds(String memberIds);

    int updateTicketCount(String memberId);

}
