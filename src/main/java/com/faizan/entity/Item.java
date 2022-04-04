package com.faizan.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "ITEM_DETAILS")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class Item {

    public enum ItemStatus {
        NOT_SOLD, SOLD
    }

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "ITEM_NAME")
    private String itemName;

    @Column(name = "ITEM_TYPE")
    private String itemType;

    @Column(name = "ITEM_DESCRIPTION")
    private String itemDescription;

    @Column(name = "PRICE")
    private Double itemPrice;

    @Column(name = "TOTOL_QUANTITY")
    private Long quantity;

    @Column(name = "SOLD_QUANTITY")
    private long soldQty = 0;

    @Column(name = "AVAILABLE_QTY")
    private long availableQty = 0;

    @Enumerated(EnumType.STRING)
    @Column(name = "ITEM_STATUS", nullable = false)
    private ItemStatus itemStatus = ItemStatus.NOT_SOLD;

    @Column(name = "IMAGE_PATH")
    private String imageLocation;

    /*
     * @OneToMany private List<Purchase> puchases = new ArrayList<Purchase>();;
     */

    /*
     * public ItemStatus getStatus() { return itemStatus; }
     *
     * public void setStatus(final ItemStatus itemStatus) { if (itemStatus == null) { this.itemStatus = ItemStatus.NOT_SOLD; } else { this.itemStatus = itemStatus; }
     */
    // }

}
