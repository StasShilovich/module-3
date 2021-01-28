package com.epam.esm.model.service.impl;

import com.epam.esm.model.dao.GenericDao;
import com.epam.esm.model.dao.entity.Tag;
import com.epam.esm.model.service.exception.NotExistEntityException;
import com.epam.esm.model.service.TagService;
import com.epam.esm.model.service.converter.impl.TagDTOMapper;
import com.epam.esm.model.service.dto.TagDTO;
import com.epam.esm.model.service.exception.ServiceException;
import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TagServiceImpl implements TagService {

    private final static Logger logger = Logger.getLogger(TagServiceImpl.class);
    private final GenericDao<Tag> tagDao;
    private final TagDTOMapper dtoMapper;

    public TagServiceImpl(GenericDao<Tag> genericDao, TagDTOMapper dtoMapper) {
        this.tagDao = genericDao;
        this.tagDao.setClazz(Tag.class);
        this.dtoMapper = dtoMapper;
    }

    @Override
    @Transactional
    public TagDTO find(Long id) throws ServiceException {
        try {
            Optional<Tag> tag = tagDao.findById(id);
            return tag.map(dtoMapper::toDTO).orElseGet(TagDTO::new);
        } catch (DataAccessException e) {
            logger.error("Tag with id=" + id + " not exist!", e);
            throw new ServiceException("Tag with id=" + id + " not exist!", e);
        }
    }

    @Override
    @Transactional
    public TagDTO add(TagDTO tagDTO) throws ServiceException {
        try {
            Tag fromDTO = dtoMapper.fromDTO(tagDTO);
            return tagDTO;
        } catch (DataAccessException e) {
            logger.error("Add tag service exception", e);
            throw new ServiceException("Add tag service exception", e);
        }
    }

    @Override
    @Transactional
    public void delete(Long id) throws ServiceException, NotExistEntityException {
        try {
            tagDao.delete(id);
        } catch (DataAccessException e) {
            logger.error("Delete tag service exception", e);
            throw new ServiceException("Delete tag service exception", e);
        }
    }

    @Override
    @Transactional
    public List<TagDTO> findAll() throws ServiceException {
        try {
            /// TODO: 28.01.2021
            List<Tag> tags = tagDao.findAll(0, 3);
            return tags.stream().map(dtoMapper::toDTO).collect(Collectors.toList());
        } catch (DataAccessException e) {
            logger.error("Find all tag service exception", e);
            throw new ServiceException("Find all tag service exception", e);
        }
    }
}
