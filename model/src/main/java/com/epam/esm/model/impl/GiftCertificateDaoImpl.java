package com.epam.esm.model.impl;

import com.epam.esm.model.dao.GenericDao;
import com.epam.esm.model.dao.GiftCertificateDao;
import com.epam.esm.model.dao.entity.GiftCertificate;
import com.epam.esm.model.dao.entity.SortType;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManagerFactory;
import java.util.List;

@Repository
public class GiftCertificateDaoImpl extends GenericDao<GiftCertificate> implements GiftCertificateDao {

    public GiftCertificateDaoImpl(EntityManagerFactory entityManagerFactory) {
        super(entityManagerFactory);
    }

    @Override
    public List<GiftCertificate> filterByParameters(String tag, String part, String sortBy, SortType type) {
        return null;
    }
}
