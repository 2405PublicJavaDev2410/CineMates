package com.filmfellows.cinemates.domain.naverapi.model.service.impl;

import com.filmfellows.cinemates.domain.member.model.service.MemberService;
import com.filmfellows.cinemates.domain.naverapi.model.service.NaverApiService;
import com.filmfellows.cinemates.domain.naverapi.model.vo.NaverApi;
import com.filmfellows.cinemates.domain.naverapi.model.vo.NaverProfile;
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
        RestTemplate restTemplate = new RestTemplate();

        // HttpHeader Object
        HttpHeaders headers = new HttpHeaders();

        // HttpBody Object
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("grant_type", "authorization_code");
        params.add("client_id", naverApi.getNaverClientId());
        params.add("client_secret", naverApi.getNaverClientSecret());
        params.add("code", code);
        params.add("state", state);
        // http body params 와 http headers 를 가진 엔티티
        HttpEntity<MultiValueMap<String, String>> naverTokenRequest = new HttpEntity<>(params, headers);
        // reqUrl로 Http 요청, POST 방식
        ResponseEntity<String> response
            = restTemplate.exchange(reqUrl, HttpMethod.POST, naverTokenRequest, String.class);
        String responseBody = response.getBody();
        JsonObject asJsonObject = JsonParser.parseString(responseBody).getAsJsonObject();
        return asJsonObject.get("access_token").getAsString();
    }

    // 프로필 정보 조회 및 저장
    public NaverProfile getUserInfo(String accessToken) {
        String apiUrl = "https://openapi.naver.com/v1/nid/me";
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + accessToken);
        RestTemplate restTemplate = new RestTemplate();

        HttpEntity<String> entity = new HttpEntity<>(headers);
        ResponseEntity<String> responseEntity = restTemplate.exchange(apiUrl, HttpMethod.GET, entity, String.class);

        // API 응답 확인
        String responseBody = responseEntity.getBody();
        System.out.println("API Response: " + responseBody);

        // 응답을 JSON으로 파싱하여 NaverProfile 객체 생성
        JsonParser jsonParser = new JsonParser();
        JsonElement element = jsonParser.parse(responseBody);
        JsonElement response = element.getAsJsonObject().get("response").getAsJsonObject();

        // NaverProfile 객체 생성 및 반환
        return new NaverProfile(response);
    }

}
