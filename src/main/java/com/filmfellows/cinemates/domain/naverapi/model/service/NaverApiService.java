package com.filmfellows.cinemates.domain.naverapi.model.service;

import com.filmfellows.cinemates.domain.naverapi.model.vo.NaverProfile;

public interface NaverApiService {
    String getAccessToken(String code, String state);
    NaverProfile getUserInfo(String accessToken);
//    void handleNaverLogin(String accessToken);
}
