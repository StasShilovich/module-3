package com.epam.esm.model.service.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class TopUserInfo {

    private Long userId;
    private BigDecimal totalCost;
    private TagDTO topTag;
    private Long tagCount;
}
