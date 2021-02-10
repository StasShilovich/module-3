package com.epam.esm.model.service.impl;

import com.epam.esm.model.dao.OrderDao;
import com.epam.esm.model.dao.UserDao;
import com.epam.esm.model.dao.entity.Order;
import com.epam.esm.model.dao.entity.OrderCertificate;
import com.epam.esm.model.dao.entity.Tag;
import com.epam.esm.model.dao.entity.User;
import com.epam.esm.model.service.OrderService;
import com.epam.esm.model.service.converter.impl.OrderDTOMapper;
import com.epam.esm.model.service.converter.impl.TagDTOMapper;
import com.epam.esm.model.service.dto.OrderDTO;
import com.epam.esm.model.service.dto.TagDTO;
import com.epam.esm.model.service.exception.ServiceException;
import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.Set;

@Service
public class OrderServiceImpl implements OrderService {

    private static final Logger logger = Logger.getLogger(OrderServiceImpl.class);
    private final OrderDao orderDao;
    private final OrderDTOMapper orderMapper;
    private final TagDTOMapper tagMapper;
    private final UserDao userDao;

    public OrderServiceImpl(OrderDao orderDao, OrderDTOMapper orderMapper, UserDao userDao, TagDTOMapper tagMapper) {
        this.orderDao = orderDao;
        this.orderMapper = orderMapper;
        this.userDao = userDao;
        this.tagMapper = tagMapper;
    }

    @Override
    @Transactional
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
    @Transactional(rollbackFor = ServiceException.class)
    public OrderDTO add(OrderDTO orderDTO) throws ServiceException {
        try {
            Order order = orderMapper.fromDTO(orderDTO);
            Order orderDb = orderDao.create(order);
            Set<OrderCertificate> certificates = orderDb.getCertificates();
            if (!certificates.isEmpty()) {
                Optional<BigDecimal> reduce = certificates.stream()
                        .map(c -> c.getOneCost().multiply(new BigDecimal(c.getQuantity())))
                        .reduce(BigDecimal::add);
                if (reduce.isPresent()) {
                    Optional<User> user = userDao.findById(orderDb.getUserId());
                    if (user.isPresent()) {
                        BigDecimal userCash = user.get().getCash();
                        if (userCash.compareTo(reduce.get()) < 0) {
                            throw new ServiceException("Not enough money, you need at least " + reduce.get());
                        }
                        user.get().setCash(userCash.subtract(reduce.get()));
                        userDao.update(user.get());
                    }
                }
            }
            return orderMapper.toDTO(orderDb);
        } catch (DataAccessException e) {
            logger.error("Create order service exception", e);
            throw new ServiceException("Create order service exception", e);
        }
    }

    @Override
    @Transactional
    public TagDTO getTopUserTag() throws ServiceException {
        try {
            Tag tag = orderDao.getTopUserTag();
            return tagMapper.toDTO(tag);
        } catch (DataAccessException e) {
            logger.error("Top tag service exception", e);
            throw new ServiceException("Top tag service exception", e);
        }
    }
}
