package com.epam.esm.model.impl;

import com.epam.esm.model.dao.GiftCertificateDao;
import com.epam.esm.model.dao.entity.GiftCertificate;
import com.epam.esm.model.dao.entity.SortType;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;
import java.util.Optional;

@Repository
public class GiftCertificateDaoImpl implements GiftCertificateDao {


    @PersistenceContext
    EntityManager manager;

    @Override
    public GiftCertificate create(GiftCertificate certificate) {
        return null;
    }

    @Override
    public Optional<GiftCertificate> findById(Long id) {
        String hql = "FROM GiftCertificate";
        Query query = manager.createNamedQuery(hql);
        return Optional.of((GiftCertificate) query.getSingleResult());
    }

    @Override
    public void update(GiftCertificate certificate) {

    }

    @Override
    public void delete(Long id) {

    }

    @Override
    public List<GiftCertificate> filterByParameters(String tag, String part, String sortBy, SortType type) {
        return null;
    }
}
