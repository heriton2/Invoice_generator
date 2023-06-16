package com.challenge.invoice_generator.utils;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.charset.Charset;
public class FileValidator {
    public boolean validateColumns(MultipartFile file) {
        try {
            CSVParser parser = CSVParser.parse(file.getInputStream(), Charset.defaultCharset(), CSVFormat.DEFAULT);
            CSVRecord header = null;
            for (CSVRecord rec : parser) {
                header = rec;
            }

            if (header == null) {
                return false; // Estrutura de colunas divergente
            }

            String[] expectedColumns = {
                    "CNPJ (apenas os números)",
                    "NOME FANTASIA",
                    "NRO DE DIAS UTEIS PARA VECTO DA FATURA",
                    "EMAIL COBRANÇA",
                    "QTDE MENSALIDADE",
                    "VALOR MENSALIDADE",
                    "VALOR UNITARIO EMISSAO CARTÃO",
                    "QTDE CARTAO EMITIDOS"
            };

            for (String column : expectedColumns) {
                boolean columnFound = false;
                for (int i = 0; i < header.size(); i++) {
                    if (header.get(i).equalsIgnoreCase(column)) {
                        columnFound = true;
                        break;
                    }
                }

                if (!columnFound) {
                    return false; // Coluna ausente na estrutura de colunas
                }
            }

            return true; // Estrutura de colunas válida
        } catch (IOException e) {
            e.printStackTrace();
            return false; // Erro ao ler o arquivo
        }
    }
}




