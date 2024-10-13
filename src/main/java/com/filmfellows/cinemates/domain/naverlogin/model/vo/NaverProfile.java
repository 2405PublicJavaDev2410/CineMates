package com.filmfellows.cinemates.domain.naverlogin.model.vo;

import com.google.gson.JsonObject;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.sql.Timestamp;

@Setter
@Getter
@NoArgsConstructor
@ToString
public class NaverProfile {
    private String snsId;
    private String memberId;
    private String snsType;
    private String name;
    private String profileImg;
    private String email;
    private String birthDate;
    private String mobile;
    private Timestamp connectDate;

    // JSON 객체로 parsing 후 필드에 값을 넣어줌
    public NaverProfile(JsonObject response) {
        this.snsId = getStringOrNull(response, "id");
        this.snsType = "NAVER";
        this.name = getStringOrNull(response, "name");
        this.email = getStringOrNull(response, "email");
        this.profileImg = getStringOrNull(response, "profile_image");
        this.birthDate = getStringOrNull(response, "birthday");
        this.mobile = getStringOrNull(response, "mobile");
    }

    private String getStringOrNull(JsonObject jsonObject, String key) {
        return jsonObject.has(key) ? jsonObject.get(key).getAsString() : null;
    }
}