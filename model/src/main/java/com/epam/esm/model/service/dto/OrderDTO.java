package com.epam.esm.model.service.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class OrderDTO {

    private Long id;
    private Long certificateId;
    private String cost;
    private String purchaseTime;
}
