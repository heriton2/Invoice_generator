package com.challenge.invoice_generator.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class InvoiceItemRowDto {
    private int quantity;
    private String item;
    private double unitValue;
    private double totalValue;

    public InvoiceItemRowDto(int quantity, String item, double unitValue, double totalValue) {
        this.quantity = quantity;
        this.item = item;
        this.unitValue = unitValue;
        this.totalValue = totalValue;
    }
}

