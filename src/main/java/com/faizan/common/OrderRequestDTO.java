package com.faizan.common;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderRequestDTO {

    private Long id;
    private String name;
    private Long qty;
    private double price;
    private Long itemId;
}
