package com.filmfellows.cinemates.domain.emailverification.model.service.impl;

import com.filmfellows.cinemates.domain.emailverification.model.mapper.EmailVerificationMapper;
import com.filmfellows.cinemates.domain.emailverification.model.service.EmailVerificationService;
import com.filmfellows.cinemates.domain.emailverification.model.vo.PwResetToken;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.UUID;

@Service
@Transactional
@Slf4j
@RequiredArgsConstructor
public class EmailVerificationServiceImpl implements EmailVerificationService {
    private final JavaMailSender mailSender;
    private final EmailVerificationMapper evMapper;

    @Override
    public void sendPasswordResetEmail(String toEmail, String memberId) {
        String resetToken = generateResetToken(memberId);
        String resetLink = "http://localhost:9000/reset-pw?token=" + resetToken;

        MimeMessage message = mailSender.createMimeMessage();
        try {
            // html 태그를 본문에 넣어주기 위해 MimeMessageHelper 사용
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setFrom("thtw28@gmail.com");
            helper.setTo(toEmail);
            helper.setSubject("씨네메이트 비밀번호 재설정 안내");
            helper.setText("<html><body style='font-family: Arial, sans-serif;'>" +
                    "<div style='max-width: 600px; margin: 30px 0; height:200px; padding: 20px; border: 1px solid #ccc; border-radius: 8px;'>" +
                    "<h2 style='color: #333;'>비밀번호 재설정 요청</h2>" +
                    "<p style='font-size: 16px; color: #555;'>비밀번호 재설정 요청에 대한 안내 메일입니다. <br> 아래 링크를 클릭하시면 비밀번호 재설정 페이지로 이동합니다.</p><br>" +
                    "<a href=\"" + resetLink + "\" style='padding: 10px 20px; background-color: #D54946; color: white; text-decoration: none; border-radius: 5px;'>재설정 바로가기</a>" +
                    "</p></div></body></html>", true);
            mailSender.send(message);
            System.out.println("이메일이 발송 성공!");
        } catch (MessagingException e) {
            System.out.println("이메일 발송 중 에러 발생: " + e.getMessage());
        }
    }

    public boolean isValidToken(String token) {
        PwResetToken resetToken = evMapper.findTokenByToken(token);
        if (resetToken == null) {
            return false;
        }
        // 현재 시간이 만료 시간보다 이전이면 유효한 토큰
        Timestamp now = new Timestamp(System.currentTimeMillis());
        return now.before(resetToken.getExpireTime());
    }

    public String verifyToken(String token) {
        PwResetToken resetToken = evMapper.findTokenByToken(token);
        log.info(resetToken.toString());
        if (resetToken == null) {
            return null;
        }
        // 토큰이 존재할 경우 회원 ID 반환
        return resetToken.getMemberId();
    }

    @Override
    public String generateResetToken(String memberId) {
        String token = UUID.randomUUID().toString();
        Timestamp expireTime = Timestamp.valueOf(LocalDateTime.now().plusHours(1));

        PwResetToken resetToken = new PwResetToken();
        resetToken.setToken(token);
        resetToken.setMemberId(memberId);
        resetToken.setCreateTime(new Timestamp(System.currentTimeMillis()));
        resetToken.setExpireTime(expireTime);
        int result = evMapper.insertToken(resetToken);
        return result == 1 ? token : "";
    }
}
