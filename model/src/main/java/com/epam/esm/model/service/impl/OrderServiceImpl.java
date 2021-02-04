package com.epam.esm.model.service.impl;

import com.epam.esm.model.dao.OrderDao;
import com.epam.esm.model.service.OrderService;
import com.epam.esm.model.service.dto.TopUserInfo;
import com.epam.esm.model.service.exception.ServiceException;
import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OrderServiceImpl implements OrderService {

    private static final Logger logger = Logger.getLogger(UserServiceImpl.class);
    private final OrderDao orderDao;

    public OrderServiceImpl(OrderDao orderDao) {
        this.orderDao = orderDao;
    }

    @Override
    @Transactional
    public void orderCertificate(Long id, Long idCertificate) throws ServiceException {
        try {
            orderDao.orderCertificate(id, idCertificate);
        } catch (DataAccessException e) {
            logger.error("Order certificate service exception", e);
            throw new ServiceException("Order certificate service exception", e);
        }
    }

    @Override
    public TopUserInfo getTopUserInfo() throws ServiceException {
        try {
            return orderDao.getTopUserInfo();
        } catch (DataAccessException e) {
            logger.error("Tag statistic service exception", e);
            throw new ServiceException("Tag statistic service exception", e);
        }
    }
}
