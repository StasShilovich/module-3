package com.epam.esm.model.service.impl;

import com.epam.esm.model.dao.OrderDao;
import com.epam.esm.model.dao.entity.Order;
import com.epam.esm.model.service.OrderService;
import com.epam.esm.model.service.converter.impl.OrderDTOMapper;
import com.epam.esm.model.service.dto.OrderDTO;
import com.epam.esm.model.service.dto.TopUserInfo;
import com.epam.esm.model.service.exception.ServiceException;
import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class OrderServiceImpl implements OrderService {

    private static final Logger logger = Logger.getLogger(OrderServiceImpl.class);
    private final OrderDao orderDao;
    private final OrderDTOMapper orderMapper;

    public OrderServiceImpl(OrderDao orderDao, OrderDTOMapper orderMapper) {
        this.orderDao = orderDao;
        this.orderMapper = orderMapper;
    }

    @Override
    public OrderDTO find(Long id) throws ServiceException {
        try {
            Optional<Order> order = orderDao.findById(id);
            return order.map(orderMapper::toDTO).orElseGet(OrderDTO::new);
        } catch (DataAccessException e) {
            logger.error("Find order service exception", e);
            throw new ServiceException("Find order service exception", e);
        }
    }

    @Override
    public OrderDTO add(OrderDTO orderDTO) throws ServiceException {
        try {
            Order order = orderMapper.fromDTO(orderDTO);
            Order orderDb = orderDao.create(order);
            return orderMapper.toDTO(orderDb);
        } catch (DataAccessException e) {
            logger.error("Create order service exception", e);
            throw new ServiceException("Create order service exception", e);
        }
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
