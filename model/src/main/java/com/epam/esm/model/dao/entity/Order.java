package com.epam.esm.model.dao.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
@Entity(name = "order")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    private BigDecimal cost;
    @Column(name = "purchase_time")
    private LocalDateTime purchaseTime;
    @Column(name = "user_id")
    private Long userId;
    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name = "order_certificate", joinColumns = {@JoinColumn(name = "id_order")},
            inverseJoinColumns = {@JoinColumn(name = "id_certificate"), @JoinColumn(name = "quantity")})
    private List<GiftCertificate> certificates;
}
