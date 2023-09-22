package com.example.highcakes.impl;

import com.example.highcakes.dao.UniqueOfferDao;
import com.example.highcakes.dao.UserDao;
import com.example.highcakes.model.UniqueOffer;
import com.example.highcakes.model.User;
import com.example.highcakes.repo.UniqueOfferRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UniqueOfferImpl implements UniqueOfferDao {
    private final UniqueOfferRepo uniqueOfferRepo;
    private final EmailServiceImpl emailService;
    private final UserDao userDao;

    @Override
    public UniqueOffer save(UniqueOffer uniqueOffer, Principal principal) {
        String username = principal.getName();
        User user = userDao.findByUsername(username);
        uniqueOffer.setUser(user);

        UniqueOffer savedUniqueOffer = uniqueOfferRepo.save(uniqueOffer);
        String to = uniqueOffer.getEmail();
        String subject = "Заявка на индивидуальный заказ";
        String text = "Здравствуйте, " + uniqueOffer.getName() + ". Вами была отправлена заявка на сайте HighCakes\n\n\n"
                + "Ваш заказ принят в обработку, в дальнейшем мы вам сообщим на ваш номер телефона " + uniqueOffer.getPhone() + "\n\n"
                + "С уваженением, HighCakes!";
        emailService.send(to, subject, text);
        System.out.print("Добавлена индивидуальная заявка!");

        return savedUniqueOffer;
    }

    @Override
    public void delete(Long id) {
        uniqueOfferRepo.deleteById(id);
    }

    @Override
    public List<UniqueOffer> findAll() {
        return uniqueOfferRepo.findAll();
    }

    @Override
    public Optional<UniqueOffer> findById(Long id) {
        return uniqueOfferRepo.findById(id);
    }

    public List<UniqueOffer> searchUniqueOffer(String param, String paramTwo, String paramThree) {
        if (param != null && !param.isEmpty()) {
            return uniqueOfferRepo.findByNameContainingIgnoreCaseOrPhoneContainingIgnoreCaseOrEmailContainingIgnoreCase(param, paramTwo, paramThree);
        } else {
            return findAll();
        }
    }
}