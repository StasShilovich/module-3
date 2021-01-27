package com.epam.esm.model.impl;

import com.epam.esm.model.dao.TagDao;
import com.epam.esm.model.dao.entity.Tag;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;
import java.util.Optional;

@Repository
public class TagDaoImpl implements TagDao {

    @PersistenceContext
    EntityManager manager;


    @Override
    public Optional<Tag> findById(Long id) {
//        String hql = "FROM Tag where id=:id";
//        Query query = manager.createNamedQuery(hql)
//                .setParameter("id", id);
//        Tag tag = (Tag) query.getSingleResult();
        return Optional.of(manager.find(Tag.class,id));
    }

    @Override
    public Tag create(Tag tag) {
        return null;
    }

    @Override
    public void delete(Long id) {

    }

    @Override
    public List<Tag> findAll() {
        String hql = "FROM Tag";
        Query query = manager.createNamedQuery(hql);
        return query.getResultList();
    }
}
