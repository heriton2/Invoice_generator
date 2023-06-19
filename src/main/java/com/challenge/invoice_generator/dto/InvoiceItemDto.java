package com.challenge.invoice_generator.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class InvoiceItemDto {
    private Long id;
    private LocalDate createdDate;
    private LocalTime createdTime;
    private String status;
    private String cnpj;
    private String fantasyName;
    private String email;
    private String dueDate;
    private Double totalValue;
    private Double totalValueToPay;
    private List<InvoiceItemRowDto> rows = new ArrayList<>();

    // Método para adicionar um novo item de fatura na lista
    public void addInvoiceItemRow(InvoiceItemRowDto row) {
        rows.add(row);
    }
}
