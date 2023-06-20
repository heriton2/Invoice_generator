package com.challenge.invoice_generator.controller;

import com.challenge.invoice_generator.converter.ImportedItemConverter;
import com.challenge.invoice_generator.dto.ErrorResponseDto;
import com.challenge.invoice_generator.dto.ImportedItemDto;
import com.challenge.invoice_generator.exception.ImportException;
import com.challenge.invoice_generator.exception.InvalidFileException;
import com.challenge.invoice_generator.interfaces.controller.IImportController;
import com.challenge.invoice_generator.interfaces.service.ICSVImporter;
import com.challenge.invoice_generator.service.CSVImporter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
public class ImportController implements IImportController {
    private final ICSVImporter csvImporter;

    @Autowired
    public ImportController(CSVImporter csvImporter) {
        this.csvImporter = csvImporter;
    }
    @PostMapping(value ="/import", produces = "application/json")
    public ResponseEntity<Object> importData(@RequestParam("file") MultipartFile file) {
        try {
            int registerCount = csvImporter.importData(file);
            return ResponseEntity.status(HttpStatus.CREATED).body(registerCount);
        } catch (ImportException | InvalidFileException e) {
            ErrorResponseDto errorResponse = new ErrorResponseDto(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }

    @GetMapping(value ="/import", produces = "application/json")
    public ResponseEntity<List<ImportedItemDto>> getAllImportedItems() {
        List<ImportedItemDto> importedItems = csvImporter.getAllImportedItems()
                .stream()
                .map(ImportedItemConverter::toDto).toList();

        return ResponseEntity.ok(importedItems);
    }
}



