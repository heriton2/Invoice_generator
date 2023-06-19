package com.challenge.invoice_generator.service;

import br.com.caelum.stella.validation.CNPJValidator;
import br.com.caelum.stella.validation.InvalidStateException;
import com.challenge.invoice_generator.entity.ImportedItem;
import com.challenge.invoice_generator.enums.InvoiceStatusEnum;
import com.challenge.invoice_generator.exception.ImportException;
import com.challenge.invoice_generator.exception.InvalidFileException;
import com.challenge.invoice_generator.exception.InvalidParameterException;
import com.challenge.invoice_generator.repository.ImportedItemRepository;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.charset.Charset;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static com.challenge.invoice_generator.utils.FileValidator.validateColumns;

@Component
public class CSVImporter {
    private final ImportedItemRepository importedItemRepository;
    @Autowired
    public CSVImporter(ImportedItemRepository importedItemRepository) {
        this.importedItemRepository = importedItemRepository;
    }

    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    private static final String INVALID_CNPJ_ERROR_MESSAGE = "Número CNPJ inválido. Cobrança não gerada.";

    public int importData(MultipartFile file) throws ImportException, InvalidFileException {

        int registerCount = 0;
        if(validateColumns(file)) {
            try {
                CSVParser parser = CSVParser.parse(file.getInputStream(), Charset.defaultCharset(), CSVFormat.DEFAULT.withDelimiter(',').withFirstRecordAsHeader());

                for (CSVRecord column : parser) {
                    String cnpj = column.get("CNPJ\n (apenas os números)");
                    if (!isCnpjValid(cnpj)) {
                        saveImportedItemWithError(cnpj);
                        continue; // Pula para a próxima iteração
                    }
                    String fantasyName = column.get("NOME FANTASIA");
                    int numDays = Integer.parseInt(column.get("NRO DE DIAS UTEIS PARA VECTO DA FATURA"));
                    String email = column.get("EMAIL COBRANÇA");
                    int numMonthlyFees = Integer.parseInt(column.get("QTDE MENSALIDADE"));
                    double monthlyPrice = parseCurrencyValue(column.get("VALOR MENSALIDADE"));
                    double unitValueCard = parseCurrencyValue(column.get("VALOR  UNITARIO EMISSAO CARTÃO"));
                    int numCardsIssued = Integer.parseInt(column.get("QTDE CARTAO EMITIDOS"));

                    String dueDate = calculatedueDate(numDays);

                    ImportedItem item = new ImportedItem();
                    item.setCreatedDate(LocalDate.now());
                    item.setCreatedTime(LocalTime.now());
                    item.setStatus(InvoiceStatusEnum.PENDENTE.name());
                    item.setCnpj(cnpj);
                    item.setFantasyName(fantasyName);
                    item.setEmail(email);
                    item.setNumMonthlyFees(numMonthlyFees);
                    item.setMonthlyPrice(monthlyPrice);
                    item.setUnitValueCard(unitValueCard);
                    item.setNumCardsIssued(numCardsIssued);
                    item.setDueDate(dueDate);

                    importedItemRepository.save(item);

                    registerCount++;
                }
            } catch (IOException | InvalidParameterException e) {
                String errorMessage = "Erro ao importar o arquivo: " + e.getMessage();
                String errorCode = "import_error";
                throw new ImportException(errorMessage, errorCode, e);
            }
        }

        return registerCount;
    }

    public List<ImportedItem> getAllImportedItems() {
        return importedItemRepository.findAll();
    }

    private boolean isCnpjValid(String cnpj) {
        try {
            CNPJValidator validator = new CNPJValidator();
            validator.assertValid(cnpj);
            return true;
        } catch (InvalidStateException e) {
            return false;
        }
    }

    private void saveImportedItemWithError(String cnpj) throws InvalidParameterException {
        ImportedItem item = new ImportedItem();
        item.setCreatedDate(LocalDate.now());
        item.setCreatedTime(LocalTime.now());
        item.setStatus(InvoiceStatusEnum.ERRO.name());
        item.setCnpj(cnpj);

        importedItemRepository.save(item);

        throw new InvalidParameterException(CSVImporter.INVALID_CNPJ_ERROR_MESSAGE);
    }

    private double parseCurrencyValue(String value) {
        String cleanedValue = value.replace("R$", "").trim();
        // Remove os pontos de milhar e substitui a vírgula decimal por ponto
        cleanedValue = cleanedValue.replace("\\.", "").replace(",", ".");
        return Double.parseDouble(cleanedValue);
    }

    private String calculatedueDate(int numDays) {
        LocalDate dueDate = LocalDate.now();
        int count = 0;
        while (count < numDays) {
            dueDate = dueDate.plusDays(1);
            if (isUtilDay(dueDate)) {
                count++;
            }
        }
        return dueDate.format(DATE_FORMATTER);
    }

    private boolean isUtilDay(LocalDate date) {
        DayOfWeek dayOfWeek = date.getDayOfWeek();
        return dayOfWeek != DayOfWeek.SATURDAY && dayOfWeek != DayOfWeek.SUNDAY;
    }
}


