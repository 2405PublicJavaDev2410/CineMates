package com.filmfellows.cinemates.domain.naverapi.model.vo;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import lombok.Getter;

import java.sql.Timestamp;

@Getter
public class NaverProfile {
    private String snsId;
    private String snsType;
    private String name;
    private String profileImg;
    private String email;
    private String birthDate;
    private String mobile;
    private Timestamp connectDate;

    // JSON 객체로 parsing 후 필드에 값을 넣어줌
    public NaverProfile(JsonElement response) {
        this.snsId = getStringOrNull(response, "id");
        this.snsType = "NAVER";
        this.name = getStringOrNull(response, "name");
        this.email = getStringOrNull(response, "email");
        this.profileImg = getStringOrNull(response, "profile_image");
        this.birthDate = getStringOrNull(response, "birthday");
        this.mobile = getStringOrNull(response, "mobile");
    }

    private String getStringOrNull(JsonElement jsonElement, String key) {
        return jsonElement.getAsJsonObject().has(key) ?
                jsonElement.getAsJsonObject().get(key).getAsString() : null;
    }
}