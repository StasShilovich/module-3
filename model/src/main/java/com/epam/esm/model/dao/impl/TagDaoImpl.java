package com.epam.esm.model.dao.impl;

import com.epam.esm.model.dao.TagDao;
import com.epam.esm.model.dao.entity.Tag;
import com.epam.esm.model.dao.exception.DaoException;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class TagDaoImpl implements TagDao {

    @Override
    public Optional<Tag> create(Tag tag) throws DaoException {
        return Optional.empty();
    }

    @Override
    public Optional<Tag> read(Long id) throws DaoException {
        return Optional.empty();
    }

    @Override
    public Optional<Long> delete(Long id) throws DaoException {
        return Optional.empty();
    }

    @Override
    public Optional<List<Tag>> findAll() throws DaoException {
        return Optional.empty();
    }

    @Override
    public void updateNewTagByCertificate(List<Tag> tags, List<Tag> dbTags, Long id) {

    }

    @Override
    public void updateOldTagByCertificate(List<Tag> tags, List<Tag> dbTags, Long id) {

    }
}
