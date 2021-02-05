package com.epam.esm.model.dao.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
@Entity(name = "order_certificate")
public class OrderCertificate implements Serializable {

    @EmbeddedId
    private OrderCertificateId id;
    @Column(name = "quantity")
    private Long quantity;
    @Column(name = "one_cost")
    private BigDecimal oneCost;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder(toBuilder = true)
    @Embeddable
    public static class OrderCertificateId implements Serializable {
        @Column
        private Long orderId;
        @Column
        private Long certificateId;
    }
}
