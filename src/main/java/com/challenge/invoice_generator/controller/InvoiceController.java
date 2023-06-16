package com.challenge.invoice_generator.controller;

import com.challenge.invoice_generator.service.InvoiceCalculator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/invoice")
public class InvoiceController {
    private InvoiceCalculator invoiceCalculator;

    @Autowired
    public InvoiceController(InvoiceCalculator invoiceCalculator) {
        this.invoiceCalculator = invoiceCalculator;
    }

    @PostMapping("/generate")
    public ResponseEntity<String> generateInvoice() {
        invoiceCalculator.calculateInvoice();

        return ResponseEntity.ok("Fatura gerada com sucesso.");
    }
}

