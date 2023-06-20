package com.challenge.invoice_generator.service;

import com.challenge.invoice_generator.entity.ImportedItem;
import com.challenge.invoice_generator.enums.InvoiceStatusEnum;
import com.challenge.invoice_generator.exception.ImportException;
import com.challenge.invoice_generator.exception.InvalidFileException;
import com.challenge.invoice_generator.exception.InvalidParameterException;
import com.challenge.invoice_generator.repository.ImportedItemRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
class CSVImporterTest {

    @Mock
    private ImportedItemRepository importedItemRepository;

    @InjectMocks
    private CSVImporter csvImporter;

    @BeforeEach
    public void setup() {
        importedItemRepository = mock(ImportedItemRepository.class);
        csvImporter = new CSVImporter(importedItemRepository);
    }

    @Test
    void importData_ValidFile_SuccessfulImport() throws ImportException, InvalidFileException, IOException {
        // Arrange
        MultipartFile file = createValidFile();
        List<ImportedItem> expectedItems = createExpectedItems();

        // Mock repository save method
        when(importedItemRepository.save(any(ImportedItem.class))).thenReturn(null);

        // Act
        int result = csvImporter.importData(file);

        // Assert
        assertEquals(expectedItems.size(), result);
        verify(importedItemRepository, times(expectedItems.size())).save(any(ImportedItem.class));
    }

    @Test
    void importData_InvalidCnpj_ThrowsInvalidParameterException() {
        // Crie o arquivo com o CNPJ inválido
        MultipartFile file = createInvalidCnpjFile();

        // Verifique se é lançada a exceção InvalidParameterException
        assertThrows(ImportException.class, () -> {
            csvImporter.importData(file);
        });
    }

    private MultipartFile createInvalidCnpjFile() {
        String csvData = "\"CNPJ\n (apenas os números)\",NOME FANTASIA,NRO DE DIAS UTEIS PARA VECTO DA FATURA,EMAIL COBRANÇA,QTDE MENSALIDADE,VALOR MENSALIDADE,VALOR  UNITARIO EMISSAO CARTÃO,QTDE CARTAO EMITIDOS\n" +
                "36709273000157897,FTG,7,fgt@fgt.com,1,\"R$250,00\",\"R$19,90\",100";

        return new MockMultipartFile("file.csv", csvData.getBytes());
    }

    private MultipartFile createValidFile() {
        String csvData = "\"CNPJ\n (apenas os números)\",NOME FANTASIA,NRO DE DIAS UTEIS PARA VECTO DA FATURA,EMAIL COBRANÇA,QTDE MENSALIDADE,VALOR MENSALIDADE,VALOR  UNITARIO EMISSAO CARTÃO,QTDE CARTAO EMITIDOS\n" +
                "36709273000157,FTG,7,fgt@fgt.com,1,\"R$250,00\",\"R$19,90\",100\n" +
                "36709273000157,ABC,5,abc@abc.com,2,\"R$150,00\",\"R$15,50\",50";

        return new MockMultipartFile("file.csv", csvData.getBytes());
    }

    private List<ImportedItem> createExpectedItems() {
        ImportedItem item1 = new ImportedItem();
        item1.setCreatedDate(LocalDate.now());
        item1.setCreatedTime(LocalTime.now());
        item1.setStatus(InvoiceStatusEnum.PENDENTE.name());
        item1.setCnpj("36709273000157");
        item1.setFantasyName("FTG");
        item1.setEmail("fgt@fgt.com");
        item1.setNumMonthlyFees(1);
        item1.setMonthlyPrice(250.00);
        item1.setUnitValueCard(19.90);
        item1.setNumCardsIssued(100);

        ImportedItem item2 = new ImportedItem();
        item2.setCreatedDate(LocalDate.now());
        item2.setCreatedTime(LocalTime.now());
        item2.setStatus(InvoiceStatusEnum.PENDENTE.name());
        item2.setCnpj("36709273000157");
        item2.setFantasyName("ABC");
        item2.setEmail("abc@abc.com");
        item2.setNumMonthlyFees(2);
        item2.setMonthlyPrice(150.00);
        item2.setUnitValueCard(15.50);
        item2.setNumCardsIssued(50);

        return Arrays.asList(item1, item2);
    }
}
