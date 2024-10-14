package com.filmfellows.cinemates.common;

public class Validation {
    // ID 정규표현식
    public static final String ID_VALIDATION = "^(?=.*[A-Za-z])(?=.*[0-9])[A-Za-z0-9]{5,10}$";
    // PW 정규표현식
    public static final String PW_VALIDATION = "^(?=.*[A-Z])(?=.*[0-9])(?=.*[!@#$%^*+=-])[A-Za-z0-9!@#$%^*+=-]{8,16}$";
    // 이름 정규식
    public static final String NAME_VALIDATION = "^[가-힣]{2,5}$";
    // 생년월일 정규식
    public static final String BIRTH_DATE_VALIDATION = "^(19[0-9][0-9]|20\\d{2})(0[0-9]|1[0-2])(0[1-9]|[1-2][0-9]|3[0-1])$";
    // 이메일 정규식
    public static final String EMAIL_VALIDATION = "^[a-zA-Z0-9+-_.]+@[a-zA-Z0-9-]+(\\.[a-zA-Z]{2,})+$";
    // 핸드폰번호 정규식
    public static final String PHONE_VALIDATION = "^01[016789][0-9]{3,4}[0-9]{4}$";
}
