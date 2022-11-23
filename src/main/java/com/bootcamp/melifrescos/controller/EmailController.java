package com.bootcamp.melifrescos.controller;

import com.bootcamp.melifrescos.dto.EmailDTO;
import com.bootcamp.melifrescos.dto.PurchaseOrderEmailDTO;
import com.bootcamp.melifrescos.interfaces.IEmailService;
import com.bootcamp.melifrescos.model.Email;
import com.bootcamp.melifrescos.model.PurchaseOrder;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/fresh-products/orders")
public class EmailController {


    private final IEmailService service;

    @GetMapping("/open")
    public ResponseEntity<List<PurchaseOrderEmailDTO>> getOpenedCart() {
        List<PurchaseOrderEmailDTO> purchaseOrders = service.getOpenedCarts();

        return new ResponseEntity<>(purchaseOrders, HttpStatus.OK);
    }

    @PostMapping("/send-email")
    public ResponseEntity<Email> sendEmail(@RequestBody @Valid EmailDTO emailDTO) {
        Email email = new Email();
        BeanUtils.copyProperties(emailDTO, email);
        service.sendEmail(email);
        return new ResponseEntity<>(email, HttpStatus.CREATED);

    }
}
