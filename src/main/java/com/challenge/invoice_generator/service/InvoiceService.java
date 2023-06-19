package com.challenge.invoice_generator.service;

import com.challenge.invoice_generator.dto.InvoiceItemDto;
import com.challenge.invoice_generator.dto.InvoiceItemRowDto;
import com.challenge.invoice_generator.entity.ImportedItem;
import com.challenge.invoice_generator.entity.InvoiceItem;
import com.challenge.invoice_generator.repository.InvoiceItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.challenge.invoice_generator.converter.CurrencyConverter.formatCurrency;
import static com.challenge.invoice_generator.utils.CPNJUtils.formatCNPJ;

@Service
public class InvoiceService {
    private final InvoiceItemRepository invoiceItemRepository;
    private final double DEFAULT_MAX_VALUE = 25000.0;

    @Autowired
    public InvoiceService(InvoiceItemRepository invoiceItemRepository) {
        this.invoiceItemRepository = invoiceItemRepository;
    }

    public InvoiceItemDto generateInvoice(ImportedItem importedItem) {
        InvoiceItem invoiceItem = createInvoiceItem(importedItem);
        calculateInvoiceValue(invoiceItem);
        updateStatus(invoiceItem);
        invoiceItemRepository.save(invoiceItem);

        return convertToDto(invoiceItem);
    }

    private InvoiceItem createInvoiceItem(ImportedItem importedItem) {
        InvoiceItem invoiceItem = new InvoiceItem();
        invoiceItem.setCnpj(importedItem.getCnpj());
        invoiceItem.setFantasyName(importedItem.getFantasyName());
        invoiceItem.setEmail(importedItem.getEmail());
        invoiceItem.setNumMonthlyFees(importedItem.getNumMonthlyFees());
        invoiceItem.setMonthlyPrice(importedItem.getMonthlyPrice());
        invoiceItem.setUnitValueCard(importedItem.getUnitValueCard());
        invoiceItem.setNumCardsIssued(importedItem.getNumCardsIssued());
        invoiceItem.setDueDate(importedItem.getDueDate());
        invoiceItem.setCreatedDate(importedItem.getCreatedDate());
        invoiceItem.setCreatedTime(importedItem.getCreatedTime());
        invoiceItem.setStatus(importedItem.getStatus());
        return invoiceItem;
    }

    private void calculateInvoiceValue(InvoiceItem item) {
        double totalValue = item.getNumMonthlyFees() * item.getMonthlyPrice() + item.getUnitValueCard() * item.getNumCardsIssued();
        item.setTotalValueToPay(totalValue);
    }

    private void updateStatus(InvoiceItem item) {
        if (item.getTotalValueToPay() > DEFAULT_MAX_VALUE) {
            item.setStatus("BLOQUEADO");
        }
    }

    private InvoiceItemDto convertToDto(InvoiceItem item) {
        InvoiceItemDto dto = new InvoiceItemDto();
        dto.setId(item.getId());
        dto.setCreatedDate(item.getCreatedDate());
        dto.setCreatedTime(item.getCreatedTime());
        dto.setStatus(item.getStatus());
        dto.setCnpj(formatCNPJ(item.getCnpj()));
        dto.setFantasyName(item.getFantasyName());
        dto.setEmail(item.getEmail());
        dto.setDueDate(item.getDueDate());
        dto.setTotalValueToPay(formatCurrency(item.getTotalValueToPay()));

        // Adicionar as linhas de fatura à lista de linhas do DTO
        dto.addInvoiceItemRow(new InvoiceItemRowDto(item.getNumMonthlyFees(), "Mensalidade", formatCurrency(item.getMonthlyPrice()), formatCurrency(item.getNumMonthlyFees() * item.getMonthlyPrice())));
        dto.addInvoiceItemRow(new InvoiceItemRowDto(item.getNumCardsIssued(), "Emissão de cartão", formatCurrency(item.getUnitValueCard()), formatCurrency(item.getNumCardsIssued() * item.getUnitValueCard())));

        return dto;
    }
}
