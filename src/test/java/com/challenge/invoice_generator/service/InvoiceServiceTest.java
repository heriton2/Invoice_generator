package com.challenge.invoice_generator.service;

import com.challenge.invoice_generator.dto.InvoiceItemDto;
import com.challenge.invoice_generator.entity.ImportedItem;
import com.challenge.invoice_generator.entity.InvoiceItem;
import com.challenge.invoice_generator.interfaces.repository.ImportedItemRepository;
import com.challenge.invoice_generator.interfaces.repository.InvoiceItemRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

class InvoiceServiceTest {
    private InvoiceService invoiceService;

    @Mock
    private InvoiceItemRepository invoiceItemRepository;

    @Mock
    private ImportedItemRepository importedItemRepository;
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        invoiceService = new InvoiceService(invoiceItemRepository, importedItemRepository);
    }

    @Test
    void generateInvoice_ShouldCreateAndSaveInvoiceItem() {
        // Arrange
        ImportedItem importedItem = createSampleImportedItem();

        // Act
        InvoiceItemDto result = invoiceService.generateInvoice(importedItem);

        // Assert
        assertEquals("12.345.678/9012-34", result.getCnpj());
        assertEquals("Company Name", result.getFantasyName());
        assertEquals("company@example.com", result.getEmail());
        assertEquals("R$ 400,00", result.getTotalValueToPay());

        // Verify that the invoiceItemRepository.save method was called once
        verify(invoiceItemRepository, times(1)).save(any(InvoiceItem.class));
    }

    private ImportedItem createSampleImportedItem() {
        ImportedItem importedItem = new ImportedItem();
        importedItem.setCnpj("12345678901234");
        importedItem.setFantasyName("Company Name");
        importedItem.setEmail("company@example.com");
        importedItem.setNumMonthlyFees(3);
        importedItem.setMonthlyPrice(100.0);
        importedItem.setUnitValueCard(50.0);
        importedItem.setNumCardsIssued(2);
        importedItem.setDueDate("2023-06-14");
        importedItem.setCreatedDate(LocalDate.parse("2023-06-14"));
        importedItem.setCreatedTime(LocalTime.parse("12:34:56"));
        importedItem.setStatus("ACTIVE");
        return importedItem;
    }
}
