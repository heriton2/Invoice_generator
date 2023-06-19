package com.challenge.invoice_generator.converter;

import com.challenge.invoice_generator.dto.ImportedItemDto;
import com.challenge.invoice_generator.entity.ImportedItem;

public class ImportedItemConverter {

    public static ImportedItemDto toDto(ImportedItem importedItem) {
        ImportedItemDto importedItemDto = new ImportedItemDto();
        importedItemDto.setId(importedItem.getId());
        importedItemDto.setCreatedDate(importedItem.getCreatedDate());
        importedItemDto.setCreatedTime(importedItem.getCreatedTime());
        importedItemDto.setStatus(importedItem.getStatus());
        importedItemDto.setCnpj(importedItem.getCnpj());
        importedItemDto.setFantasyName(importedItem.getFantasyName());
        importedItemDto.setEmail(importedItem.getEmail());
        importedItemDto.setNumMonthlyFees(importedItem.getNumMonthlyFees());
        importedItemDto.setMonthlyPrice(importedItem.getMonthlyPrice());
        importedItemDto.setUnitValueCard(importedItem.getUnitValueCard());
        importedItemDto.setNumCardsIssued(importedItem.getNumCardsIssued());
        importedItemDto.setDueDate(importedItem.getDueDate());

        return importedItemDto;
    }
}

