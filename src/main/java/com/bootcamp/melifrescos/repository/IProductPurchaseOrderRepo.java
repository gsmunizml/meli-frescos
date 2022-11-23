package com.bootcamp.melifrescos.repository;

import com.bootcamp.melifrescos.dto.PurchaseOrderEmailDTO;
import com.bootcamp.melifrescos.enums.OrderStatus;
import com.bootcamp.melifrescos.model.ProductPurchaseOrder;
import com.bootcamp.melifrescos.model.PurchaseOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface IProductPurchaseOrderRepo extends JpaRepository<ProductPurchaseOrder, Long> {
    ProductPurchaseOrder findProductPurchaseOrderByPurchaseOrder(PurchaseOrder purchaseOrder);
    List<ProductPurchaseOrder> findAllProductPurchaseOrderByPurchaseOrder(PurchaseOrder purchaseOrder);

    @Query("SELECT " +
            "new com.bootcamp.melifrescos.dto.PurchaseOrderEmailDTO(po.id, po.date, po.buyer.id, po.status, ppo.product) " +
            "FROM ProductPurchaseOrder as ppo " +
            "INNER JOIN Product as p ON (p.id = ppo.product.id) " +
            "INNER JOIN PurchaseOrder as po ON (po.id = ppo.purchaseOrder.id) " +
            "WHERE po.status = :status")
    List<PurchaseOrderEmailDTO> findAllPurchaseOrdersOpen(@Param("status")OrderStatus status);
}
