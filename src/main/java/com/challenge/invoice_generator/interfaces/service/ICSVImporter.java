package com.challenge.invoice_generator.interfaces.service;

import com.challenge.invoice_generator.entity.ImportedItem;
import com.challenge.invoice_generator.exception.ImportException;
import com.challenge.invoice_generator.exception.InvalidFileException;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ICSVImporter {
    int importData(MultipartFile file) throws ImportException, InvalidFileException;
    List<ImportedItem> getAllImportedItems();
}
