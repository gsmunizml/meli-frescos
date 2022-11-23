package com.bootcamp.melifrescos.dto;

import com.bootcamp.melifrescos.enums.OrderStatus;
import com.bootcamp.melifrescos.model.Product;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PurchaseOrderEmailDTO {
    private Long id;
    private LocalDateTime date;
    private int buyer_id;
    private OrderStatus status;
    private PurchaseProductDTO products;

    public PurchaseOrderEmailDTO(Long id, LocalDateTime date, Long buyer_id, OrderStatus status, Product product) {
        this.id = id;
        this.date = date;
        this.buyer_id = buyer_id.intValue();
        this.status = status;
        this.products = new PurchaseProductDTO(
                product.getId(),
                product.getProductPurchaseOrders().get(0).getProductQuantity(),
                product.getProductPurchaseOrders().get(0).getProductPrice()
        );
    }
}
