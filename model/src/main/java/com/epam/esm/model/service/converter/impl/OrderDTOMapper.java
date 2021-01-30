package com.epam.esm.model.service.converter.impl;

import com.epam.esm.model.dao.entity.Order;
import com.epam.esm.model.service.converter.DTOMapper;
import com.epam.esm.model.service.dto.OrderDTO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Component
public class OrderDTOMapper implements DTOMapper<OrderDTO, Order> {

    private static final String DATE_TIME_FORMAT = "yyyy-MM-dd'T'HH:mm:ss.s";

    @Override
    public OrderDTO toDTO(Order order) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_TIME_FORMAT);
        String purchaseTime = order.getPurchaseTime().format(formatter);
        return OrderDTO.builder()
                .id(order.getId())
                .certificateId(order.getCertificateId())
                .cost(order.getCost().toString())
                .purchaseTime(purchaseTime)
                .build();
    }

    @Override
    public Order fromDTO(OrderDTO orderDTO) {
        return Order.builder()
                .id(orderDTO.getId())
                .certificateId(orderDTO.getCertificateId())
                .cost(StringUtils.isNotEmpty(orderDTO.getCost()) ? new BigDecimal(orderDTO.getCost()) : BigDecimal.ZERO)
                .purchaseTime(StringUtils.isNotEmpty(orderDTO.getPurchaseTime()) ?
                        LocalDateTime.parse(orderDTO.getPurchaseTime()) : null)
                .build();
    }
}
