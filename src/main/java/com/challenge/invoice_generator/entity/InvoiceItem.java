package com.challenge.invoice_generator.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;

@Setter
@Getter
@Entity
public class InvoiceItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    private LocalDate createdDate;
    @Column
    private LocalTime createdTime;
    @Column
    private String status;
    @Column
    private String cnpj;
    @Column
    private String fantasyName;
    @Column
    private String email;
    @Column
    private int numMonthlyFees;
    @Column
    private double monthlyPrice;
    @Column
    private double unitValueCard;
    @Column
    private int numCardsIssued;
    @Column
    private String dueDate;
    @Column
    private Double totalValue;
    @Column
    private Double totalValueToPay;
}


