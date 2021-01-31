package com.epam.esm.model.impl;

import com.epam.esm.model.dao.GiftCertificateDao;
import com.epam.esm.model.dao.UserDao;
import com.epam.esm.model.dao.entity.GiftCertificate;
import com.epam.esm.model.dao.entity.Order;
import com.epam.esm.model.dao.entity.User;
import com.epam.esm.model.service.dto.TagDTO;
import com.epam.esm.model.service.dto.TopUserInfo;
import org.springframework.stereotype.Repository;

import javax.persistence.Tuple;
import javax.persistence.criteria.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

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

                .userId(id)
                .cost(price)
                .purchaseTime(LocalDateTime.now())
                .build();
        entityManager.persist(order);
    }

    @Override
    public TopUserInfo getTopUserInfo() {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Tuple> query = builder.createTupleQuery();
        String sql = "SELECT o.user_id,sum(o.cost),count(t.name),t.id,t.name  FROM certificates.order o " +
                "join gift_certificate c on o.certificate_id=c.id " +
                "join tag_certificate tc on c.id=tc.id_certificate " +
                "join tag t on tc.id_tag=t.id " +
                "group by o.user_id, t.name " +
                "order by sum(o.cost) desc limit 1";
        Root<Order> root = query.from(Order.class);
        Join<Object, Object> tag = root.join("certificate").join("tags");
        query.select(builder.tuple(root.get("userId"),
                builder.sum(root.get("cost")),
                builder.count(tag.get("name")),
                tag.get("id"), tag.get("name")));
        query.groupBy(root.get("userId"), tag.get("name"))
                .orderBy(builder.desc(builder.sum(root.get("cost"))));
        Optional<Tuple> tuple = entityManager.createQuery(query).setMaxResults(1).getResultList().stream().findFirst();
        TopUserInfo topUserInfo = new TopUserInfo();
        if (tuple.isPresent()) {
            topUserInfo = TopUserInfo.builder()
                    .userId((Long) tuple.get().get(0))
                    .totalCost(new BigDecimal(tuple.get().get(1).toString()))
                    .tagCount((Long) tuple.get().get(2))
                    .topTag(TagDTO.builder()
                            .id((Long) tuple.get().get(3))
                            .name(tuple.get().get(4).toString())
                            .build())
                    .build();
        }
        return topUserInfo;
    }
}
