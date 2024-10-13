package com.filmfellows.cinemates.domain.naverlogin.model.service;

import com.filmfellows.cinemates.domain.naverlogin.model.vo.NaverProfile;

public interface NaverApiService {
    String getAccessToken(String code, String state);
    NaverProfile getUserInfo(String accessToken);
    String revokeNaverAccessToken(String accessToken);
}
