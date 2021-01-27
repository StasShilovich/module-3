package com.epam.esm.model.dao;

import com.epam.esm.model.dao.entity.Tag;

import java.util.List;
import java.util.Optional;

public interface TagDao {

    Optional<Tag> findById(Long id);

    Tag create(Tag tag);

    void delete(Long id);

    List<Tag> findAll();
}
