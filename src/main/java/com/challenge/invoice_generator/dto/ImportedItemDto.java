package com.challenge.invoice_generator.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
@NoArgsConstructor
public class ImportedItemDto {
    private Long id;
    private LocalDate createdDate;
    private LocalTime createdTime;
    private String status;
    private String cnpj;
    private String fantasyName;
    private String email;
    private int numMonthlyFees;
    private String monthlyPrice;
    private String unitValueCard;
    private int numCardsIssued;
    private String dueDate;
}

