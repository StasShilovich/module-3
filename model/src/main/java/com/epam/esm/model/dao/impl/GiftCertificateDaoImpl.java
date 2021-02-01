package com.epam.esm.model.dao.impl;

import com.epam.esm.model.dao.GiftCertificateDao;
import com.epam.esm.model.dao.entity.GiftCertificate;
import com.epam.esm.model.dao.entity.SortType;
import org.springframework.stereotype.Repository;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.List;

import static org.apache.commons.lang3.StringUtils.isNotEmpty;

@Repository
public class GiftCertificateDaoImpl extends GenericDaoImpl<GiftCertificate> implements GiftCertificateDao {

    public GiftCertificateDaoImpl() {
        super(GiftCertificate.class);
    }

    @Override
    public List<GiftCertificate> filterByParameters(
            String tag, String part, String sortBy, SortType type, int offset, int limit) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<GiftCertificate> query = builder.createQuery(GiftCertificate.class);
        Root<GiftCertificate> root = query.from(GiftCertificate.class);
        buildFilterQuery(builder, root, query, tag, part, sortBy, type);
        return entityManager.createQuery(query)
                .setFirstResult(offset)
                .setMaxResults(limit)
                .getResultList();
    }

    private void buildFilterQuery(
            CriteriaBuilder builder, Root<GiftCertificate> root, CriteriaQuery<GiftCertificate> query,
            String tag, String part, String sortBy, SortType type) {
        query.select(root);
        Predicate predicatePart = builder.and();
        if (isNotEmpty(part)) {
            predicatePart = predicatePart(builder, root, part);
        }
        Predicate predicateTag = builder.and();
        if (isNotEmpty(tag)) {
            predicateTag = predicateTag(builder, root, tag);
        }
        query.where(builder.and(predicatePart, predicateTag));

        Path<Object> path = isNotEmpty(sortBy) && sortBy.equalsIgnoreCase("date") ?
                root.get("createDate") :
                root.get("name");
        query.orderBy(type == SortType.DESC ? builder.desc(path) : builder.asc(path));
    }

    private Predicate predicatePart(CriteriaBuilder builder, Root<GiftCertificate> root, String part) {
        String partLike = "%" + part + "%";
        Predicate likeName = builder.like(root.get("name"), partLike);
        Predicate likeDescription = builder.like(root.get("description"), partLike);
        return builder.or(likeName, likeDescription);
    }

    private Predicate predicateTag(CriteriaBuilder builder, Root<GiftCertificate> root, String tag) {
        return builder.equal(root.join("tags").get("name"), tag);
    }
}
