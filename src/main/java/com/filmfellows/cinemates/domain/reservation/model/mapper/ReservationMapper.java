package com.filmfellows.cinemates.domain.reservation.model.mapper;

import com.filmfellows.cinemates.app.mypage.dto.myReservationRequest;
import com.filmfellows.cinemates.app.mypage.dto.myReservationResponse;
import com.filmfellows.cinemates.domain.reservation.model.vo.MemberDTO;
import com.filmfellows.cinemates.domain.reservation.model.vo.ReservationDTO;
import com.filmfellows.cinemates.domain.reservation.model.vo.ShowInfoDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface ReservationMapper {
    List<ReservationDTO> showReservationPage();

//    int insertReservationInfo();

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
    
    ReservationDTO selectReservationInfo(String reservationNo);

    void deleteReservationInfo(String impUid);

    List<String> selectAllMovies();
}
