package com.challenge.invoice_generator.interfaces.controller;

import com.challenge.invoice_generator.dto.ImportedItemDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface IImportController {
    @PostMapping(value = "/import", produces = "application/json")
    ResponseEntity<Object> importData(@RequestParam("file") MultipartFile file);

    @GetMapping(value = "/import", produces = "application/json")
    ResponseEntity<List<ImportedItemDto>> getAllImportedItems();
}
