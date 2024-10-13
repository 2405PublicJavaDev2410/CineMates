package com.filmfellows.cinemates.domain.kakaologin.model.service.impl;

import com.filmfellows.cinemates.domain.kakaologin.model.service.KakaoApiService;
import com.filmfellows.cinemates.domain.kakaologin.model.vo.KakaoApi;
import com.filmfellows.cinemates.domain.kakaologin.model.vo.KakaoProfile;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

@Service
@Slf4j
@RequiredArgsConstructor
public class KakaoApiServiceImpl implements KakaoApiService {
    private final KakaoApi kakaoApi;

    // 토큰 발급 요청문 생성
    public String getAccessToken(String code) {
        String reqUrl = "https://kauth.kakao.com/oauth/token";
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("grant_type", "authorization_code");
        params.add("client_id", kakaoApi.getKakaoApiKey());
        params.add("redirect_uri", kakaoApi.getKakaoRedirectUri());
        params.add("code", code);

        HttpEntity<MultiValueMap<String, String>> kakaoTokenRequest = new HttpEntity<>(params, headers);
        ResponseEntity<String> response
                = restTemplate.exchange(reqUrl, HttpMethod.POST, kakaoTokenRequest, String.class);
        String responseBody = response.getBody();
        System.out.println(responseBody);
        JsonObject asJsonObject = JsonParser.parseString(responseBody).getAsJsonObject();
        return asJsonObject.get("access_token").getAsString();
    }

    /// 프로필 정보 조회 및 저장
    public KakaoProfile getUserInfo(String accessToken) {
        String apiUrl = "https://kapi.kakao.com/v2/user/me";
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + accessToken);
        headers.set("Content-type", "application/x-www-form-urlencoded;charset=utf-8");
        RestTemplate restTemplate = new RestTemplate();

        HttpEntity<String> entity = new HttpEntity<>(headers);
        ResponseEntity<String> responseEntity = restTemplate.exchange(apiUrl, HttpMethod.POST, entity, String.class);

        String responseBody = responseEntity.getBody();
        System.out.println("API Response: " + responseBody);

        // JSON 응답 파싱
        JsonElement element = JsonParser.parseString(responseBody);
        JsonObject jsonObject = element.getAsJsonObject();

        // KakaoProfile 객체 생성 및 반환
        return new KakaoProfile(jsonObject);
    }

}
