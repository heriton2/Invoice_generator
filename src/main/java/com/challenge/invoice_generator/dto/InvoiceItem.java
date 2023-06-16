package com.challenge.invoice_generator.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class InvoiceItem {
    private String id;
    private String status;
    private String cnpj;
    private String nomeFantasia;
    private String email;
    private double valorMensalidade;
    private double valorUnitarioCartao;
    private String vencimento;

    // Getters and setters
}

