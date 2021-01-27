package com.epam.esm.model.dao;

import com.epam.esm.model.dao.entity.GiftCertificate;
import com.epam.esm.model.dao.entity.SortType;

import java.util.List;
import java.util.Optional;

public interface GiftCertificateDao {

    GiftCertificate create(GiftCertificate certificate);

    Optional<GiftCertificate> findById(Long id);

    void update(GiftCertificate certificate);

    void delete(Long id);

    List<GiftCertificate> filterByParameters(String tag, String part, String sortBy, SortType type);

}
