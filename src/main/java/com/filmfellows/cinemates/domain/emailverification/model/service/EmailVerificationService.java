package com.filmfellows.cinemates.domain.emailverification.model.service;

public interface EmailVerificationService {
    boolean sendVerificationCode(String toEmail);
    void sendPasswordResetEmail(String toEmail, String resetLink);
    String getStoredCode(String email);
    boolean isValidToken(String token);
    boolean verifyCode(String email, String code);
    String verifyToken(String token);
    String generateVerificationCode();
    String generateResetToken(String memberId);
}
