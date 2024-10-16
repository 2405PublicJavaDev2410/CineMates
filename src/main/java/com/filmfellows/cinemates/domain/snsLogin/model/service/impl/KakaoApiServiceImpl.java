package com.filmfellows.cinemates.domain.snsLogin.model.service.impl;

import com.filmfellows.cinemates.domain.snsLogin.model.service.KakaoApiService;
import com.filmfellows.cinemates.domain.snsLogin.model.vo.KakaoApi;
import com.filmfellows.cinemates.domain.snsLogin.model.vo.SnsProfile;
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

    // 프로필 정보 조회 및 저장
    public SnsProfile getUserInfo(String accessToken) {
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

        String snsId = jsonObject.get("id").getAsString();
        // properties 객체에서 nickname과 profile_image 가져오기
        JsonObject properties = jsonObject.getAsJsonObject("properties");
        String nickname = properties.get("nickname").getAsString();
        String profileImg = properties.get("profile_image").getAsString();
        // kakao_account 객체에서 email, birthday, phone_number 가져오기
        JsonObject kakaoAccount = jsonObject.getAsJsonObject("kakao_account");
        String email = kakaoAccount.has("email") ? kakaoAccount.get("email").getAsString() : null;
        String birthday = kakaoAccount.has("birthday") ? kakaoAccount.get("birthday").getAsString() : null;
        String phoneNumber = kakaoAccount.has("phone_number") ? kakaoAccount.get("phone_number").getAsString() : null;

        // SnsProfile 객체 생성 및 반환
        SnsProfile snsProfile = new SnsProfile();
        snsProfile.setSnsId(snsId);
        snsProfile.setName(nickname);
        snsProfile.setProfileImg(profileImg);
        snsProfile.setEmail(email);
        snsProfile.setBirthDate(birthday);
        snsProfile.setMobile(phoneNumber);
        snsProfile.setSnsType("KAKAO");

        // SnsProfile 객체 반환
        return snsProfile;
    }

    // 로그인 연동 해제(탈퇴)
    @Override
    public String revokeKakaoAccessToken(String accessToken) {
        String reqUrl = "https://kapi.kakao.com/v1/user/unlink";
        // RestTemplate 인스턴스 생성
        RestTemplate restTemplate = new RestTemplate();
        // Http 헤더 객체 생성
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + accessToken);
        // HttpEntity 객체 생성 (헤더만 필요)
        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(headers);
        // HTTP 요청 및 응답 처리
        ResponseEntity<String> response = restTemplate.exchange(reqUrl, HttpMethod.POST, request, String.class);

        // 요청 본문 반환
        return response.getBody();
    }
}
