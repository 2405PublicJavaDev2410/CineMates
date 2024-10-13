package com.filmfellows.cinemates.domain.kakaologin.model.service;

import com.filmfellows.cinemates.domain.kakaologin.model.vo.KakaoProfile;

public interface KakaoApiService {
    String getAccessToken(String code);
    KakaoProfile getUserInfo(String accessToken);
}
