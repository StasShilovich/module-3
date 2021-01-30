package com.epam.esm.model.dao;

import com.epam.esm.model.dao.entity.User;

public interface UserDao extends GenericDao<User> {
    void orderCertificate(Long id, Long idCertificate);
}
