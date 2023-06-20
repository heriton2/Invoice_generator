package com.challenge.invoice_generator.controller;

import com.challenge.invoice_generator.dto.InvoiceItemDto;
import com.challenge.invoice_generator.entity.ImportedItem;
import com.challenge.invoice_generator.interfaces.repository.ImportedItemRepository;
import com.challenge.invoice_generator.service.InvoiceService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class InvoiceControllerTest {
    private InvoiceController invoiceController;

    @Mock
    private ImportedItemRepository importedItemRepository;

    @Mock
    private InvoiceService invoiceService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        invoiceController = new InvoiceController(importedItemRepository, invoiceService);
    }

    @Test
    void generateInvoice_WithValidId_ShouldReturnInvoiceItemDto() {
        // Arrange
        Long itemId = 1L;
        ImportedItem importedItem = createSampleImportedItem();
        InvoiceItemDto invoiceItemDto = createSampleInvoiceItemDto();

        when(importedItemRepository.findById(itemId)).thenReturn(Optional.of(importedItem));
        when(invoiceService.generateInvoice(importedItem)).thenReturn(invoiceItemDto);

        // Act
        ResponseEntity<InvoiceItemDto> response = invoiceController.generateInvoice(itemId);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(invoiceItemDto, response.getBody());

        // Verify that the importedItemRepository.findById and invoiceService.generateInvoice methods were called once
        verify(importedItemRepository, times(1)).findById(itemId);
        verify(invoiceService, times(1)).generateInvoice(importedItem);
    }

    @Test
    void generateInvoice_WithInvalidId_ShouldThrowException() {
        // Arrange
        Long itemId = 1L;

        when(importedItemRepository.findById(itemId)).thenReturn(Optional.empty());

        // Act & Assert
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            invoiceController.generateInvoice(itemId);
        });

        assertEquals("Invalid ID", exception.getMessage());

        // Verify that the importedItemRepository.findById method was called once
        verify(importedItemRepository, times(1)).findById(itemId);
        // Verify that the invoiceService.generateInvoice method was never called
        verify(invoiceService, never()).generateInvoice(any(ImportedItem.class));
    }

    private ImportedItem createSampleImportedItem() {
        ImportedItem importedItem = new ImportedItem();
        // Set the properties of the imported item
        return importedItem;
    }

    private InvoiceItemDto createSampleInvoiceItemDto() {
        InvoiceItemDto invoiceItemDto = new InvoiceItemDto();
        // Set the properties of the invoice item DTO
        return invoiceItemDto;
    }
}
