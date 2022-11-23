package com.bootcamp.melifrescos.model;

import com.bootcamp.melifrescos.enums.EmailStatus;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;


@Entity
@Table(name = "emails")
@Getter
@Setter
public class Email {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String emailFrom;
    private String emailTo;
    private String subject;
    @Column(columnDefinition = "TEXT")
    private String message;
    private LocalDateTime sendDate;
    @Enumerated(EnumType.STRING)
    private EmailStatus emailStatus;
}
