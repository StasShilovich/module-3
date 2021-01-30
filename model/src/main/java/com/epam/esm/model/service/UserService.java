package com.epam.esm.model.service;

import com.epam.esm.model.service.dto.UserDTO;
import com.epam.esm.model.service.exception.NotExistEntityException;
import com.epam.esm.model.service.exception.ServiceException;

public interface UserService {

    UserDTO find(Long id) throws ServiceException, NotExistEntityException;

    void orderCertificate(Long id, Long idCertificate) throws ServiceException;
}
