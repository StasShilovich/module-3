package com.epam.esm.model.service;

import com.epam.esm.model.service.dto.OrderDTO;
import com.epam.esm.model.service.dto.TagDTO;
import com.epam.esm.model.service.exception.ServiceException;

public interface OrderService {

    OrderDTO find(Long id) throws ServiceException;

    OrderDTO add(OrderDTO orderDTO) throws ServiceException;

    TagDTO getTopUserTag() throws ServiceException;
}
