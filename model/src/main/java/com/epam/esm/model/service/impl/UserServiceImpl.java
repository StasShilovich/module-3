package com.epam.esm.model.service.impl;

import com.epam.esm.model.dao.UserDao;
import com.epam.esm.model.dao.entity.User;
import com.epam.esm.model.service.Page;
import com.epam.esm.model.service.UserService;
import com.epam.esm.model.service.converter.impl.UserDTOMapper;
import com.epam.esm.model.service.dto.UserDTO;
import com.epam.esm.model.service.exception.IncorrectArgumentException;
import com.epam.esm.model.service.exception.NotExistEntityException;
import com.epam.esm.model.service.exception.ServiceException;
import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    private static final Logger logger = Logger.getLogger(UserServiceImpl.class);
    private final UserDao userDao;
    private final UserDTOMapper userMapper;

    public UserServiceImpl(UserDao userDao, UserDTOMapper userMapper) {
        this.userDao = userDao;
        this.userMapper = userMapper;
    }

    @Override
    @Transactional
    public UserDTO find(Long id) throws ServiceException, NotExistEntityException {
        try {
            Optional<User> user = userDao.findById(id);
            return user.map(userMapper::toDTO)
                    .orElseThrow(() -> new NotExistEntityException("User with id=" + id + " not exist!"));
        } catch (DataAccessException e) {
            logger.error("Find user service exception", e);
            throw new ServiceException("Find user service exception", e);
        }
    }

    @Override
    public List<UserDTO> findAll(int page, int size) throws ServiceException, IncorrectArgumentException {
        try {
            long count = count();
            Page userPage = new Page(page, size, count);
            List<User> tags = userDao.findAll(userPage.getOffset(), userPage.getLimit());
            return tags.stream().map(userMapper::toDTO).collect(Collectors.toList());
        } catch (DataAccessException e) {
            logger.error("Find all users service exception", e);
            throw new ServiceException("Find all users service exception", e);
        }
    }

    @Override
    public long count() throws ServiceException {
        try {
            return userDao.getCountOfEntities();
        } catch (DataAccessException e) {
            logger.error("Count users service exception", e);
            throw new ServiceException("Count users service exception", e);
        }
    }
}
