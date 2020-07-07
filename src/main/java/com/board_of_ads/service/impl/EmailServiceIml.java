package com.board_of_ads.service.impl;
import com.board_of_ads.models.User;
import com.board_of_ads.service.interfaces.EmailService;
import lombok.AllArgsConstructor;
import org.springframework.core.env.Environment;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@AllArgsConstructor
public class EmailServiceIml implements EmailService {

    private final JavaMailSender mailSender;
    private final Environment env;

    /*Метод для отправки почты
    String subject - Название сообщения
    String body - Тело сообщения
     */
    @Override
    public void sendMail(String subject, String body, User user){
        mailSender.send(constructEmail(subject, body, user));
    }

    private SimpleMailMessage constructEmail(String subject, String body, User user) {
        final SimpleMailMessage email = new SimpleMailMessage();
        email.setSubject(subject);
        email.setText(body);
        email.setTo(user.getEmail());
        email.setFrom(Objects.requireNonNull(env.getProperty("support.email")));
        return email;
    }
}
