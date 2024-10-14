package com.filmfellows.cinemates.domain.emailverification.model.mapper;

import com.filmfellows.cinemates.domain.emailverification.model.vo.PwResetToken;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface EmailVerificationMapper {
    int insertToken(PwResetToken resetToken);
    PwResetToken findTokenByToken(String token);
}
