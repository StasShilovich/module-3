package com.epam.esm.model.impl;

import com.epam.esm.model.dao.GenericDao;
import com.epam.esm.model.dao.TagDao;
import com.epam.esm.model.dao.entity.Tag;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManagerFactory;

@Repository
public class TagDaoImpl extends GenericDao<Tag> implements TagDao {

    public TagDaoImpl(EntityManagerFactory entityManagerFactory) {
        super(entityManagerFactory);
    }
}
