package com.epam.esm.model.impl;

import com.epam.esm.model.dao.GiftCertificateDao;
import com.epam.esm.model.dao.UserDao;
import com.epam.esm.model.dao.entity.GiftCertificate;
import com.epam.esm.model.dao.entity.Order;
import com.epam.esm.model.dao.entity.User;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.NoSuchElementException;
import java.util.Optional;

@Repository
public class UserDaoImpl extends GenericDaoImpl<User> implements UserDao {

    private final GiftCertificateDao certificateDao;

    public UserDaoImpl(GiftCertificateDao certificateDao) {
        super(User.class);
        this.certificateDao = certificateDao;
    }

    @Override
    public void orderCertificate(Long id, Long idCertificate) {
        Optional<GiftCertificate> certificate = certificateDao.findById(idCertificate);
        BigDecimal price = certificate.map(GiftCertificate::getPrice)
                .orElseThrow(() -> new NoSuchElementException("No certificate with id=" + idCertificate));
        Order order = Order.builder()
                .certificateId(idCertificate)
                .userId(id)
                .cost(price)
                .purchaseTime(LocalDateTime.now())
                .build();
        entityManager.persist(order);
    }
}
