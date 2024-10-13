package com.filmfellows.cinemates.domain.kakaologin.model.vo;

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
public class KakaoProfile {
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
    public KakaoProfile(JsonObject response) {
        this.snsId = getStringOrNull(response, "id");
        this.snsType = "KAKAO";
        JsonObject properties = response.getAsJsonObject("properties");
        if (properties != null) {
            this.name = getStringOrNull(properties, "nickname");
            this.profileImg = getStringOrNull(properties, "profile_image");
        }
        JsonObject kakaoAccount = response.getAsJsonObject("kakao_account");
        if (kakaoAccount != null) {
            this.email = getStringOrNull(kakaoAccount, "email");
            this.birthDate = getStringOrNull(kakaoAccount, "birthday");
            this.mobile = getStringOrNull(kakaoAccount, "phone_number");
        }
    }

    private String getStringOrNull(JsonObject jsonObject, String key) {
        return jsonObject != null && jsonObject.has(key) ? jsonObject.get(key).getAsString() : null;
    }
}
