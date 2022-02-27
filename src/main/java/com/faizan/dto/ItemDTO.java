package com.faizan.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ItemDTO {
    private String itemName;

    private String itemType;

    private String itemDescription;

    private Double itemPrice;

    private Long quantity;

    @Override
    public String toString() {
        return "ItemDTO [itemName=" + itemName + ", itemType=" + itemType + ", itemDescription=" + itemDescription + ", itemPrice=" + itemPrice + ", quantity=" + quantity + "]";
    }

}
