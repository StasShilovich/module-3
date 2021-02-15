package com.epam.esm.model.dao;

import com.epam.esm.model.dao.entity.GiftCertificate;
import com.epam.esm.model.common.SortType;

import java.util.List;

public interface GiftCertificateDao extends GenericDao<GiftCertificate> {

    List<GiftCertificate> filterByParameters(
            List<String> tags, String part, String sortBy, SortType type, int offset, int limit);

}
