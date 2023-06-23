package com.challenge.invoice_generator.interfaces.service;

import com.challenge.invoice_generator.dto.InvoiceItemDto;
import com.challenge.invoice_generator.entity.ImportedItem;
import com.challenge.invoice_generator.exception.InvalidParameterException;

public interface IInvoiceService {
    InvoiceItemDto generateInvoice(ImportedItem importedItem);
    InvoiceItemDto getGeneratedInvoice(Long id) throws InvalidParameterException;
}
