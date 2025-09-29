package com.udea.lab12025v.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name="transactions")
public class Transaction {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;
    @Column(name="sender_account_name", nullable=false)
    private String senderAccountNumber;
    @Column(name="receiver_account_name", nullable=false)
    private String  receiverAccountNumber;
    @Column(nullable=false)
    private Double amount;
    @Column(nullable=false)
    private LocalDateTime transactionDate;
}
