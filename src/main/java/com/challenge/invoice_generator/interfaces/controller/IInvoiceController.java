package com.challenge.invoice_generator.interfaces.controller;

import com.challenge.invoice_generator.dto.InvoiceItemDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

public interface IInvoiceController {
    @GetMapping("/generate-invoice/{id}")
    ResponseEntity<InvoiceItemDto> generateInvoice(@PathVariable Long id);
}
