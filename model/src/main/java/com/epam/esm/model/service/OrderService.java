package com.epam.esm.model.service;

import com.epam.esm.model.service.dto.OrderDTO;
import com.epam.esm.model.service.dto.TopUserInfo;
import com.epam.esm.model.service.exception.ServiceException;

public interface OrderService {

    OrderDTO find(Long id) throws ServiceException;

    OrderDTO add(OrderDTO orderDTO) throws ServiceException;

    void orderCertificate(Long id, Long idCertificate) throws ServiceException;

    TopUserInfo getTopUserInfo() throws ServiceException;
}
