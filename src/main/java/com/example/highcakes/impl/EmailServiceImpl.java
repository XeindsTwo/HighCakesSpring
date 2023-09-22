package com.example.highcakes.impl;

import com.example.highcakes.model.Offer;
import com.example.highcakes.model.UniqueOffer;
import com.example.highcakes.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailServiceImpl {
    private final JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String username;

    public void sendEmail(String emailTo, String subject, String message) {
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setFrom(username);
        mailMessage.setTo(emailTo);
        mailMessage.setSubject(subject);
        mailMessage.setText(message);
        mailSender.send(mailMessage);
    }

    public void sendReviewConfirmationEmail(User user) {
        String subject = "Спасибо за ваш отзыв!";
        String text = "Здравствуйте, " + user.getName() + ". Вами был оставлен отзыв на сайте HighCakes\n\n\n"
                + "Ваш отзыв принят, мы благодарим наших клиентов за оставление положительных отзывов. " +
                "Мы рады нести миру отличные продукты кондитерской\n\n"
                + "С уважением, HighCakes!";
        sendEmail(user.getMail(), subject, text);
    }

    public void sendUniqueOfferConfirmationEmail(UniqueOffer uniqueOffer) {
        String subject = "Заявка на индивидуальный заказ";
        String text = "Здравствуйте, " + uniqueOffer.getName() + ". Вами была отправлена заявка на сайте HighCakes\n\n\n"
                + "Ваш заказ принят в обработку, в дальнейшем мы вам сообщим на ваш номер телефона " + uniqueOffer.getPhone() + "\n\n"
                + "С уважением, HighCakes!";
        sendEmail(uniqueOffer.getEmail(), subject, text);
    }

    public void sendOfferConfirmationEmail(Offer offer) {
        String subject = "Заявка на заказ - " + offer.getCake().getName();
        String text = "Здравствуйте, " + offer.getName() + ". Вами была отправлена заявка на сайте HighCakes\n\n\n"
                + "Ваш заказ принят в обработку, в дальнейшем мы вам сообщим на ваш номер телефона " + offer.getPhone() + "\n\n"
                + "С уваженением, HighCakes!";
        sendEmail(offer.getEmail(), subject, text);
    }
}