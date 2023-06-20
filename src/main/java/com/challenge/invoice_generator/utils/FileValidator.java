package com.challenge.invoice_generator.utils;

import com.challenge.invoice_generator.exception.InvalidFileException;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Iterator;
import java.util.List;

public class FileValidator {

    private static final List<String> EXPECTED_COLUMNS = List.of(
            "CNPJ\n (apenas os números)",
            "NOME FANTASIA",
            "NRO DE DIAS UTEIS PARA VECTO DA FATURA",
            "EMAIL COBRANÇA",
            "QTDE MENSALIDADE",
            "VALOR MENSALIDADE",
            "VALOR  UNITARIO EMISSAO CARTÃO",
            "QTDE CARTAO EMITIDOS"
    );

    public static boolean validateColumns(MultipartFile file) throws InvalidFileException {
        try (CSVParser parser = CSVParser.parse(file.getInputStream(), StandardCharsets.UTF_8, CSVFormat.DEFAULT)) {
            Iterator<CSVRecord> recordsIterator = parser.iterator();
            if (!recordsIterator.hasNext()) {
                throw new InvalidFileException("Arquivo vazio.");
            }

            CSVRecord header = recordsIterator.next();
            if (header.size() != EXPECTED_COLUMNS.size()) {
                throw new InvalidFileException("Número de colunas divergente.");
            }

            for (int i = 0; i < EXPECTED_COLUMNS.size(); i++) {
                String expectedColumn = EXPECTED_COLUMNS.get(i);
                String actualColumn = header.get(i).replaceAll("\\s", "");
                if (!actualColumn.equalsIgnoreCase(expectedColumn.replaceAll("\\s", ""))) {
                    throw new InvalidFileException("Coluna não encontrada: " + expectedColumn);
                }
            }

            return true; // Estrutura de colunas válida
        } catch (IOException e) {
            throw new InvalidFileException("Erro ao importar o arquivo: " + e.getMessage());
        }
    }
}

