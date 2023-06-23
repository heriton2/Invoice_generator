package com.challenge.invoice_generator.interfaces.controller;

import com.challenge.invoice_generator.dto.InvoiceItemDto;
import com.challenge.invoice_generator.exception.InvalidParameterException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

public interface IInvoiceController {
    @GetMapping("/generate-invoice/{id}")
    ResponseEntity<InvoiceItemDto> generateInvoice(@PathVariable Long id);
    @GetMapping("/invoice/{id}")
    ResponseEntity<InvoiceItemDto> getInvoice(@PathVariable Long id) throws InvalidParameterException;
}
