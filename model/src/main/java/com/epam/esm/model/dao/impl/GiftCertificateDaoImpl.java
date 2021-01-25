package com.epam.esm.model.dao.impl;

import com.epam.esm.model.dao.GiftCertificateDao;
import com.epam.esm.model.dao.entity.GiftCertificate;
import com.epam.esm.model.dao.entity.SortType;
import com.epam.esm.model.dao.exception.DaoException;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class GiftCertificateDaoImpl implements GiftCertificateDao {


    @Override
    public Optional<GiftCertificate> create(GiftCertificate certificate) throws DaoException {
        return Optional.empty();
    }

    @Override
    public Optional<GiftCertificate> read(Long id) throws DaoException {
        return Optional.empty();
    }

    @Override
    public void update(GiftCertificate certificate) throws DaoException {

    }

    @Override
    public Optional<Long> delete(Long id) throws DaoException {
        return Optional.empty();
    }

    @Override
    public Optional<List<GiftCertificate>> filterByParameters(String tag, String part, String sortBy, SortType type) throws DaoException {
        return Optional.empty();
    }
}
