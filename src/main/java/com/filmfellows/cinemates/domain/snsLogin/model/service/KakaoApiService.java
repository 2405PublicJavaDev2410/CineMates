package com.filmfellows.cinemates.domain.snsLogin.model.service;

import com.filmfellows.cinemates.domain.snsLogin.model.vo.SnsProfile;

public interface KakaoApiService {
    String getAccessToken(String code);
    SnsProfile getUserInfo(String accessToken);
}
