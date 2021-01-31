package com.epam.esm.model.dao;

import com.epam.esm.model.dao.entity.User;
import com.epam.esm.model.service.dto.TopUserInfo;

public interface UserDao extends GenericDao<User> {

    void orderCertificate(Long id, Long idCertificate);

    TopUserInfo getTopUserInfo();
}
