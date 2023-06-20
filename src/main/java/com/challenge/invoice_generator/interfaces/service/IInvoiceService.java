package com.challenge.invoice_generator.interfaces.service;

import com.challenge.invoice_generator.dto.InvoiceItemDto;
import com.challenge.invoice_generator.entity.ImportedItem;

public interface IInvoiceService {
    InvoiceItemDto generateInvoice(ImportedItem importedItem);
}
