package com.bootcamp.melifrescos.service;

        import com.bootcamp.melifrescos.dto.EmailDTO;
        import com.bootcamp.melifrescos.dto.PurchaseOrderEmailDTO;
        import com.bootcamp.melifrescos.enums.OrderStatus;
        import com.bootcamp.melifrescos.enums.Type;
        import com.bootcamp.melifrescos.model.Email;
        import com.bootcamp.melifrescos.model.Product;
        import com.bootcamp.melifrescos.model.ProductPurchaseOrder;
        import com.bootcamp.melifrescos.model.PurchaseOrder;
        import com.bootcamp.melifrescos.repository.IEmailRepo;
        import com.bootcamp.melifrescos.repository.IProductPurchaseOrderRepo;
        import org.junit.jupiter.api.BeforeEach;
        import org.junit.jupiter.api.Test;
        import org.junit.jupiter.api.extension.ExtendWith;
        import org.mockito.ArgumentMatchers;
        import org.mockito.InjectMocks;
        import org.mockito.Mock;
        import org.mockito.Mockito;
        import org.mockito.junit.jupiter.MockitoExtension;
        import org.springframework.beans.BeanUtils;
        import org.springframework.mail.SimpleMailMessage;
        import org.springframework.mail.javamail.JavaMailSender;

        import java.math.BigDecimal;
        import java.time.LocalDateTime;
        import java.util.ArrayList;
        import java.util.List;

        import static org.mockito.Mockito.*;
        import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
public class EmailServiceTest {
    @InjectMocks
    private EmailService sendEmailService;
    @Mock
    private IEmailRepo repo;
    @Mock
    private IProductPurchaseOrderRepo ppoRepo;
    @Mock
    private JavaMailSender javaMailSender;
    private Email email = new Email();
    PurchaseOrderEmailDTO orderEmailDTO;

    @BeforeEach
    public void setup() {
        EmailDTO emailDTO = new EmailDTO("gsmuniz17@gmail.com", "gabriel.smuniz@mercadolivre.com", "teste", "teste");
        BeanUtils.copyProperties(emailDTO, email);

        ProductPurchaseOrder productPurchaseOrder = new ProductPurchaseOrder(1L, new BigDecimal(100), 10, 1L, new PurchaseOrder(), new Product());
        List<ProductPurchaseOrder> productPurchaseOrders = new ArrayList<>();
        productPurchaseOrders.add(productPurchaseOrder);
        Product product = new Product(1L, "leite", Type.REFRIGERATED, null, null, productPurchaseOrders);
        orderEmailDTO = new PurchaseOrderEmailDTO(1L, LocalDateTime.now(), 1L, OrderStatus.OPEN, product);

    }

    @Test
    public void sendEmail_returnAnEmail_whenParamsSuccess() {
        Mockito.doNothing().when(javaMailSender).send(ArgumentMatchers.any(SimpleMailMessage.class));
        Mockito.when(repo.save(ArgumentMatchers.any(Email.class)))
                .thenReturn(email);

        Email result = sendEmailService.sendEmail(email);
        verifyNoMoreInteractions(javaMailSender);

        assertThat(result).isEqualTo(email);
    }

    @Test
    public void getOpenedCarts_returnAListOfPurchaseOrders_whenHaveOpenCarts() {
        Mockito.when(ppoRepo.findAllPurchaseOrdersOpen(ArgumentMatchers.any(OrderStatus.class)))
                .thenReturn(List.of(orderEmailDTO));

        List<PurchaseOrderEmailDTO> result = sendEmailService.getOpenedCarts();

        assertThat(result).isNotEmpty();
    }
}