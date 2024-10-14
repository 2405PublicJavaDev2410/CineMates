package com.filmfellows.cinemates.domain.snsLogin.model.service;

import com.filmfellows.cinemates.domain.snsLogin.model.vo.SnsProfile;

public interface NaverApiService {
    String getAccessToken(String code, String state);
    SnsProfile getUserInfo(String accessToken);
    String revokeNaverAccessToken(String accessToken);
}
