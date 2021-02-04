package com.epam.esm.model.service.converter.impl;

import com.epam.esm.model.dao.entity.OrderCertificate;
import com.epam.esm.model.service.converter.DTOMapper;
import com.epam.esm.model.service.dto.OrderCertificateDTO;
import org.springframework.stereotype.Component;

@Component
public class OrderCertificateDTOMapper implements DTOMapper<OrderCertificateDTO, OrderCertificate> {
    @Override
    public OrderCertificateDTO toDTO(OrderCertificate orderCertificate) {
        return OrderCertificateDTO.builder()
                .orderId(orderCertificate.getOrderId())
                .certificateId(orderCertificate.getCertificateId())
                .oneCost(orderCertificate.getOneCost())
                .quantity(orderCertificate.getQuantity())
                .build();
    }

    @Override
    public OrderCertificate fromDTO(OrderCertificateDTO orderCertificateDTO) {
        return OrderCertificate.builder()
                .orderId(orderCertificateDTO.getOrderId())
                .certificateId(orderCertificateDTO.getCertificateId())
                .oneCost(orderCertificateDTO.getOneCost())
                .quantity(orderCertificateDTO.getQuantity())
                .build();
    }
}
