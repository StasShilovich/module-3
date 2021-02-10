package com.epam.esm.model.dao.impl;

import com.epam.esm.model.dao.GiftCertificateDao;
import com.epam.esm.model.dao.entity.GiftCertificate;
import com.epam.esm.model.dao.entity.SortType;
import com.epam.esm.model.dao.entity.Tag;
import org.springframework.stereotype.Repository;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.CriteriaUpdate;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.Collections;
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

    @Override
    public GiftCertificate update(GiftCertificate giftCertificate) {
        Long id = giftCertificate.getId();
        GiftCertificate certificate = new GiftCertificate();
        if (id != null && id > 0) {
            GiftCertificate certificateDB = findById(id).get();
            if (!giftCertificate.equals(certificateDB)) {
                CriteriaBuilder builder = entityManager.getCriteriaBuilder();
                CriteriaUpdate<GiftCertificate> update = builder.createCriteriaUpdate(GiftCertificate.class);
                update.from(GiftCertificate.class);
                if (buildUpdateQuery(update, certificateDB, giftCertificate)) {
                    entityManager.createQuery(update).executeUpdate();
                }
                List<Tag> tagsDB = certificateDB.getTags();
                List<Tag> tagsNew = giftCertificate.getTags();
                if (tagsNew != null && isNotEqualsList(tagsNew, tagsDB)) {
                    certificateDB.setTags(tagsNew);
                    entityManager.merge(certificateDB);
                }

            }
            certificate = certificateDB;
        }
        return certificate;
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

    private boolean buildUpdateQuery(CriteriaUpdate<GiftCertificate> update,
                                     GiftCertificate certificateDB, GiftCertificate giftCertificate) {
        int count = 0;
        if (isStringChanged(certificateDB.getName(), giftCertificate.getName())) {
            update.set("name", giftCertificate.getName());
            certificateDB.setName(giftCertificate.getName());
            count++;
        }
        if (isStringChanged(certificateDB.getDescription(), giftCertificate.getDescription())) {
            update.set("description", giftCertificate.getDescription());
            certificateDB.setDescription(giftCertificate.getDescription());
            count++;
        }
        if (giftCertificate.getPrice() != null &&
                certificateDB.getPrice().compareTo(giftCertificate.getPrice()) != 0) {
            update.set("price", giftCertificate.getPrice());
            certificateDB.setPrice(giftCertificate.getPrice());
            count++;
        }
        if (giftCertificate.getDuration() != null &&
                !certificateDB.getDuration().equals(giftCertificate.getDuration())) {
            update.set("duration", giftCertificate.getDuration());
            certificateDB.setDuration(giftCertificate.getDuration());
            count++;
        }
        if (giftCertificate.getCreateDate() != null &&
                !certificateDB.getCreateDate().equals(giftCertificate.getCreateDate())) {
            update.set("createDate", giftCertificate.getCreateDate());
            certificateDB.setCreateDate(giftCertificate.getCreateDate());
            count++;
        }
        if (giftCertificate.getLastUpdateDate() != null &&
                !certificateDB.getLastUpdateDate().equals(giftCertificate.getLastUpdateDate())) {
            update.set("lastUpdateDate", giftCertificate.getLastUpdateDate());
            certificateDB.setLastUpdateDate(giftCertificate.getLastUpdateDate());
            count++;
        }
        return count > 0;
    }

    private boolean isNotEqualsList(List<Tag> tagsNew, List<Tag> tagsDB) {
        if (tagsNew.size() != tagsDB.size()) {
            return true;
        }
        Collections.sort(tagsNew);
        Collections.sort(tagsDB);
        return !tagsNew.equals(tagsDB);
    }

    private boolean isStringChanged(String dbField, String field) {
        return isNotEmpty(field) && dbField.equals(field);
    }
}
