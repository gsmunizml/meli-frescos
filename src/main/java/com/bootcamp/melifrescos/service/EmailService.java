package com.bootcamp.melifrescos.service;

import com.bootcamp.melifrescos.dto.PurchaseOrderEmailDTO;
import com.bootcamp.melifrescos.dto.PurchaseProductDTO;
import com.bootcamp.melifrescos.enums.EmailStatus;
import com.bootcamp.melifrescos.enums.OrderStatus;
import com.bootcamp.melifrescos.interfaces.IEmailService;
import com.bootcamp.melifrescos.model.Email;
import com.bootcamp.melifrescos.model.PurchaseOrder;
import com.bootcamp.melifrescos.repository.IEmailRepo;
import com.bootcamp.melifrescos.repository.IProductPurchaseOrderRepo;
import com.bootcamp.melifrescos.repository.IPurchaseOrderRepo;
import lombok.RequiredArgsConstructor;

import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;


import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class EmailService implements IEmailService {


    private final IEmailRepo repo;
    private final IProductPurchaseOrderRepo ppoRepo;
    private final IPurchaseOrderRepo poRepo;

    private final JavaMailSender emailSender;

    /**
     * Send an email, set the send date, and if it fails, set the email status to error
     *
     * @param email The email object that we want to send.
     * @return The email object is being returned.
     */
    @Override
    public Email sendEmail(Email email) {
        email.setSendDate(LocalDateTime.now());
        try{
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom(email.getEmailFrom());
            message.setTo(email.getEmailTo());
            message.setSubject(email.getSubject());
            message.setText(email.getMessage());
            emailSender.send(message);

            email.setEmailStatus(EmailStatus.SENT);
        } catch (MailException e) {
            email.setEmailStatus(EmailStatus.ERROR);
        }
        return repo.save(email);
    }


    /**
     * Get all the purchase orders that have the status of OPEN.
     *
     * @return A list of all the open carts.
     */
    public List<PurchaseOrderEmailDTO> getOpenedCarts() {
        return ppoRepo.findAllPurchaseOrdersOpen(OrderStatus.OPEN);
    }
}
