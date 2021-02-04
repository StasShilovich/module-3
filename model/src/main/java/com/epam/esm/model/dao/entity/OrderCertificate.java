package com.epam.esm.model.dao.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.Serializable;
import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
@Entity(name = "order_certificate")
public class OrderCertificate implements Serializable {
    @Id
    @Column(name = "id_order")
    private Long orderId;
    @Id
    @Column(name = "id_certificate")
    private Long certificateId;
    @Column(name = "quantity")
    private Long quantity;
    @Column(name = "one_cost")
    private BigDecimal oneCost;
}
