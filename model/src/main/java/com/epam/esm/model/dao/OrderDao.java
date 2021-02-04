package com.epam.esm.model.dao;

import com.epam.esm.model.dao.entity.Order;
import com.epam.esm.model.service.dto.TopUserInfo;

public interface OrderDao extends GenericDao<Order> {

    void orderCertificate(Long id, Long idCertificate);

    TopUserInfo getTopUserInfo();
}
