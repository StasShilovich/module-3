package com.epam.esm.model.service;

import com.epam.esm.model.service.dto.TopUserInfo;
import com.epam.esm.model.service.exception.ServiceException;

public interface OrderService {

    void orderCertificate(Long id, Long idCertificate) throws ServiceException;

    TopUserInfo getTopUserInfo() throws ServiceException;
}
