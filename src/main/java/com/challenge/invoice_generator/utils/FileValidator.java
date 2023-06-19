package com.challenge.invoice_generator.utils;

import com.challenge.invoice_generator.exception.InvalidFileException;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class FileValidator {

    private static final Set<String> EXPECTED_COLUMNS = new HashSet<>(Arrays.asList(
            "CNPJ\n (apenas os números)",
            "NOME FANTASIA",
            "NRO DE DIAS UTEIS PARA VECTO DA FATURA",
            "EMAIL COBRANÇA",
            "QTDE MENSALIDADE",
            "VALOR MENSALIDADE",
            "VALOR  UNITARIO EMISSAO CARTÃO",
            "QTDE CARTAO EMITIDOS"
    ));

    public static boolean validateColumns(MultipartFile file) throws InvalidFileException {
        try (CSVParser parser = CSVParser.parse(file.getInputStream(), StandardCharsets.UTF_8, CSVFormat.DEFAULT)) {
            Iterator<CSVRecord> recordsIterator = parser.iterator();
            if (!recordsIterator.hasNext()) {
                return false; // Arquivo vazio
            }

            CSVRecord header = recordsIterator.next();
            if (header == null) {
                return false; // Estrutura de colunas divergente
            }

            for (String expectedColumn : EXPECTED_COLUMNS) {
                boolean columnFound = false;
                for (String headerColumn : header) {
                    if (headerColumn.equalsIgnoreCase(expectedColumn)) {
                        columnFound = true;
                        break;
                    }
                }

                if (!columnFound) {
                    throw new InvalidFileException("Coluna não encontrada: " + expectedColumn);
                }
            }

            return true; // Estrutura de colunas válida
        } catch (IOException e) {
            String errorMessage = "Erro ao importar o arquivo: " + e.getMessage();
            throw new InvalidFileException(errorMessage);
        }
    }
}
