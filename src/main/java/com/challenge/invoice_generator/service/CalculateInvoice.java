package com.challenge.invoice_generator.service;

import com.challenge.invoice_generator.dto.InvoiceItem;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CalculateInvoice {
    private static final double LIMITE_VALOR_COBRANCA = 25000.0;

    public void calculate(List<InvoiceItem> items) {
        double total = 0.0;

        for (InvoiceItem item : items) {
            double valorCobranca = item.getQuantidade() * item.getValorUnitario();

            if (valorCobranca > LIMITE_VALOR_COBRANCA) {
                item.setStatus("BLOQUEADO");
            } else {
                item.setStatus("PENDENTE");
            }

            total += valorCobranca;
        }

        System.out.println("Valor total da fatura: " + total);
    }
}



