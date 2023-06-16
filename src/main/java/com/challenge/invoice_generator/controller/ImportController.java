package com.challenge.invoice_generator.controller;

import com.challenge.invoice_generator.dto.InvoiceItem;
import com.challenge.invoice_generator.service.CSVImporter;
import com.challenge.invoice_generator.service.CalculateInvoice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
public class ImportController {
    private final CSVImporter csvImporter;

    @Autowired
    public ImportController(CSVImporter csvImporter, CalculateInvoice calculateInvoice) {
        this.csvImporter = csvImporter;
    }

    @PostMapping("/import")
    public List<InvoiceItem> importData(@RequestParam("file") MultipartFile file) {
        return csvImporter.importData(file);
    }
}



