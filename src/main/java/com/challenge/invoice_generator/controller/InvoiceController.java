package com.challenge.invoice_generator.controller;

import com.challenge.invoice_generator.dto.InvoiceItemDto;
import com.challenge.invoice_generator.entity.ImportedItem;
import com.challenge.invoice_generator.repository.ImportedItemRepository;
import com.challenge.invoice_generator.service.InvoiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class InvoiceController {
    private final ImportedItemRepository importedItemRepository;
    private final InvoiceService invoiceService;

    @Autowired
    public InvoiceController(ImportedItemRepository importedItemRepository, InvoiceService invoiceService) {
        this.importedItemRepository = importedItemRepository;
        this.invoiceService = invoiceService;
    }

    @GetMapping("/generate-invoice/{id}")
    public ResponseEntity<InvoiceItemDto> generateInvoice(@PathVariable Long id) {
        ImportedItem importedItem = importedItemRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid ID"));

        InvoiceItemDto invoiceItemDto = invoiceService.generateInvoice(importedItem);
        return ResponseEntity.ok(invoiceItemDto);
    }
}


