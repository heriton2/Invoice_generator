package com.challenge.invoice_generator.controller;

import com.challenge.invoice_generator.dto.InvoiceItemDto;
import com.challenge.invoice_generator.entity.ImportedItem;
import com.challenge.invoice_generator.interfaces.controller.IInvoiceController;
import com.challenge.invoice_generator.interfaces.repository.ImportedItemRepository;
import com.challenge.invoice_generator.interfaces.service.IInvoiceService;
import com.challenge.invoice_generator.service.InvoiceService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class InvoiceController implements IInvoiceController {
    private final ImportedItemRepository importedItemRepository;
    private final IInvoiceService invoiceService;
    private final Logger logger = LoggerFactory.getLogger(InvoiceController.class);

    @Autowired
    public InvoiceController(ImportedItemRepository importedItemRepository, InvoiceService invoiceService) {
        this.importedItemRepository = importedItemRepository;
        this.invoiceService = invoiceService;
    }

    @GetMapping("/generate-invoice/{id}")
    public ResponseEntity<InvoiceItemDto> generateInvoice(@PathVariable Long id) {
        try {
            ImportedItem importedItem = importedItemRepository.findById(id)
                    .orElseThrow(() -> new IllegalArgumentException("Invalid ID"));

            InvoiceItemDto invoiceItemDto = invoiceService.generateInvoice(importedItem);
            return ResponseEntity.ok(invoiceItemDto);
        } catch (Exception e) {
            logger.error("Erro ao gerar fatura para o ID %d: {1} ", id, e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}


