package com.filmfellows.cinemates.domain.snsLogin.model.service.impl;

import com.filmfellows.cinemates.domain.snsLogin.model.service.NaverApiService;
import com.filmfellows.cinemates.domain.snsLogin.model.vo.NaverApi;
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
public class NaverApiServiceImpl implements NaverApiService {
    private final NaverApi naverApi;

    // 토큰 발급 요청문 생성
    public String getAccessToken(String code, String state) {
        String reqUrl = "https://nid.naver.com/oauth2.0/token";
        // RestTemplate 인스턴스 생성
        RestTemplate restTemplate = new RestTemplate();
        // Http 헤더 객체 생성
        HttpHeaders headers = new HttpHeaders();

        // Http 바디 객체 생성
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("grant_type", "authorization_code");
        params.add("client_id", naverApi.getNaverClientId());
        params.add("client_secret", naverApi.getNaverClientSecret());
        params.add("code", code);
        params.add("state", state);

        // HTTP 바디와 헤더 파라미터를 묶어 HttpEntity 객체 생성
        HttpEntity<MultiValueMap<String, String>> naverTokenRequest = new HttpEntity<>(params, headers);
        // HTTP 요청 및 응답 처리
        ResponseEntity<String> response
            = restTemplate.exchange(reqUrl, HttpMethod.POST, naverTokenRequest, String.class);
        // 응답 본문을 문자열로 추출하여 저장
        String responseBody = response.getBody();
        // JsonParser를 사용해 문자열을 JSON 객체로 변환
        JsonObject asJsonObject = JsonParser.parseString(responseBody).getAsJsonObject();
        // JSON 객체에서 access_token을 추출하여 반환
        return asJsonObject.get("access_token").getAsString();
    }

    // 프로필 정보 조회 및 저장
    public SnsProfile getUserInfo(String accessToken) {
        String apiUrl = "https://openapi.naver.com/v1/nid/me";
        // Http 헤더 객체 생성
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + accessToken);
        // RestTemplate 인스턴스 생성
        RestTemplate restTemplate = new RestTemplate();

        // HTTP 엔티티 생성
        HttpEntity<String> entity = new HttpEntity<>(headers);
        // HTTP 요청 및 응답 처리
        ResponseEntity<String> responseEntity = restTemplate.exchange(apiUrl, HttpMethod.GET, entity, String.class);
        // API 응답 확인
        String responseBody = responseEntity.getBody();
        System.out.println("API Response: " + responseBody);
        // 응답을 JSON으로 파싱하여 NaverProfile 객체 생성
        JsonElement element = JsonParser.parseString(responseBody);
        // JSON 응답에서 "response" 객체를 가져옴
        JsonObject response = element.getAsJsonObject().getAsJsonObject("response");
        // SnsProfile 객체 생성
        SnsProfile snsProfile = new SnsProfile();

        // 서비스에서 각 필드 값을 설정
        snsProfile.setSnsId(response.get("id").getAsString());
        snsProfile.setSnsType("NAVER");
        snsProfile.setName(response.has("name") ? response.get("name").getAsString() : null);
        snsProfile.setEmail(response.has("email") ? response.get("email").getAsString() : null);
        snsProfile.setProfileImg(response.has("profile_image") ? response.get("profile_image").getAsString() : null);
        snsProfile.setBirthDate(response.has("birthday") ? response.get("birthday").getAsString() : null);
        snsProfile.setMobile(response.has("mobile") ? response.get("mobile").getAsString() : null);

        // SnsProfile 객체 반환
        return snsProfile;

    }

    // 로그인 연동 해제(탈퇴)
    @Override
    public String revokeNaverAccessToken(String accessToken) {
        String reqUrl = "https://nid.naver.com/oauth2.0/token";
        // RestTemplate 인스턴스 생성
        RestTemplate restTemplate = new RestTemplate();
        // Http 헤더 객체 생성
        HttpHeaders headers = new HttpHeaders();

        // Http 바디 객체 생성
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("grant_type", "delete");
        params.add("client_id", naverApi.getNaverClientId());
        params.add("client_secret", naverApi.getNaverClientSecret());
        params.add("access_token", accessToken);

        // HTTP 바디와 헤더 파라미터를 묶어 HttpEntity 객체 생성
        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(params, headers);
        // HTTP 요청 및 응답 처리
        ResponseEntity<String> response
            = restTemplate.exchange(reqUrl, HttpMethod.POST, request, String.class);
        // 요청 본문 반환
        return response.getBody();
    }

}
