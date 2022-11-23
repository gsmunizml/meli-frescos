package com.bootcamp.melifrescos.interfaces;

import com.bootcamp.melifrescos.dto.PurchaseOrderEmailDTO;
import com.bootcamp.melifrescos.model.Email;
import com.bootcamp.melifrescos.model.PurchaseOrder;

import java.util.List;

public interface IEmailService {
    Email sendEmail(Email email);
    List<PurchaseOrderEmailDTO> getOpenedCarts();
}
