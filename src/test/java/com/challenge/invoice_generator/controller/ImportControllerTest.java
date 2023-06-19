package com.challenge.invoice_generator.controller;

import com.challenge.invoice_generator.dto.ErrorResponseDto;
import com.challenge.invoice_generator.dto.ImportedItemDto;
import com.challenge.invoice_generator.entity.ImportedItem;
import com.challenge.invoice_generator.exception.ImportException;
import com.challenge.invoice_generator.exception.InvalidFileException;
import com.challenge.invoice_generator.service.CSVImporter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.util.ResourceUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class ImportControllerTest {
    private ImportController importController;

    @Mock
    private CSVImporter csvImporter;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        importController = new ImportController(csvImporter);
    }

    @Test
    void importData_WithValidFile_ShouldReturnCreatedStatusAndRegisterCount() throws ImportException, InvalidFileException, IOException {
        // Arrange
        MultipartFile file = createSampleMultipartFile();
        int registerCount = 10;

        when(csvImporter.importData(file)).thenReturn(registerCount);

        // Act
        ResponseEntity<Object> response = importController.importData(file);

        // Assert
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(registerCount, response.getBody());

        // Verify that the csvImporter.importData method was called once
        verify(csvImporter, times(1)).importData(file);
    }

    @Test
    void importData_WithImportException_ShouldReturnInternalServerErrorAndErrorResponse() throws ImportException, InvalidFileException, IOException {
        // Arrange
        MultipartFile file = createSampleMultipartFile();
        String errorMessage = "Import error";

        when(csvImporter.importData(file)).thenThrow(new ImportException(errorMessage));

        // Act
        ResponseEntity<Object> response = importController.importData(file);

        // Assert
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        ErrorResponseDto errorResponse = (ErrorResponseDto) response.getBody();
        assertEquals(errorMessage, errorResponse.getErrorMessage());

        // Verify that the csvImporter.importData method was called once
        verify(csvImporter, times(1)).importData(file);
    }

    @Test
    void importData_WithInvalidFileException_ShouldReturnInternalServerErrorAndErrorResponse() throws ImportException, InvalidFileException, IOException {
        // Arrange
        MultipartFile file = createSampleMultipartFile();
        String errorMessage = "Invalid file";

        when(csvImporter.importData(file)).thenThrow(new InvalidFileException(errorMessage));

        // Act
        ResponseEntity<Object> response = importController.importData(file);

        // Assert
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        ErrorResponseDto errorResponse = (ErrorResponseDto) response.getBody();
        assertEquals(errorMessage, errorResponse.getErrorMessage());

        // Verify that the csvImporter.importData method was called once
        verify(csvImporter, times(1)).importData(file);
    }

    @Test
    void getAllImportedItems_ShouldReturnListOfImportedItemDto() {
        // Arrange
        List<ImportedItem> expectedImportedItems = createSampleImportedItemDtoList();

        when(csvImporter.getAllImportedItems()).thenReturn(expectedImportedItems);

        // Act
        ResponseEntity<List<ImportedItemDto>> response = importController.getAllImportedItems();

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expectedImportedItems, response.getBody());

        // Verify that the csvImporter.getAllImportedItems method was called once
        verify(csvImporter, times(1)).getAllImportedItems();
    }

    private MultipartFile createSampleMultipartFile() throws IOException {
        // Create a sample file
        File file = ResourceUtils.getFile("classpath:sample.csv");

        // Read the file content into a byte array
        byte[] fileContent = new byte[(int) file.length()];
        FileInputStream fis = new FileInputStream(file);
        fis.read(fileContent);
        fis.close();

        // Create a MockMultipartFile using the byte array
        return new MockMultipartFile("sample.csv", "sample.csv", "text/csv", fileContent);
    }

    private List<ImportedItem> createSampleImportedItemDtoList() {
        List<ImportedItem> importedItems = new ArrayList<>();

        ImportedItemDto item1 = new ImportedItemDto();
        item1.setId(1L);
        item1.setCreatedDate(LocalDate.now());
        item1.setCreatedTime(LocalTime.now());
        item1.setStatus("Status 1");
        item1.setCnpj("CNPJ 1");
        item1.setFantasyName("Fantasy Name 1");
        item1.setEmail("Email 1");
        item1.setNumMonthlyFees(5);
        item1.setMonthlyPrice("100.00");
        item1.setUnitValueCard("50.00");
        item1.setNumCardsIssued(10);
        item1.setDueDate("Due Date 1");

        ImportedItemDto item2 = new ImportedItemDto();
        item2.setId(2L);
        item2.setCreatedDate(LocalDate.now());
        item2.setCreatedTime(LocalTime.now());
        item2.setStatus("Status 2");
        item2.setCnpj("CNPJ 2");
        item2.setFantasyName("Fantasy Name 2");
        item2.setEmail("Email 2");
        item2.setNumMonthlyFees(3);
        item2.setMonthlyPrice("150.00");
        item2.setUnitValueCard("75.00");
        item2.setNumCardsIssued(5);
        item2.setDueDate("Due Date 2");

        return importedItems;
    }
}
