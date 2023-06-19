package com.challenge.invoice_generator.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class InvoiceItemRowDto {
    private int quantity;
    private String item;
    private String unitValue;
    private String totalValue;
}

