package com.challenge.invoice_generator.utils;

import com.challenge.invoice_generator.exception.InvalidFileException;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class FileValidatorTest {

    @Test
    void validateColumns_ValidFile_ReturnsTrue() throws InvalidFileException, IOException {
        // Crie um arquivo CSV válido
        String csvData = "\"CNPJ\n (apenas os números)\",NOME FANTASIA,NRO DE DIAS UTEIS PARA VECTO DA FATURA,EMAIL COBRANÇA,QTDE MENSALIDADE,VALOR MENSALIDADE,VALOR  UNITARIO EMISSAO CARTÃO,QTDE CARTAO EMITIDOS\n";
        MultipartFile file = new MockMultipartFile("file.csv", csvData.getBytes());

        // Verifique se a validação de colunas retorna true
        assertTrue(FileValidator.validateColumns(file));
    }

    @Test
    void validateColumns_InvalidEmptyFile_ThrowsInvalidFileException() {
        // Crie um arquivo CSV vazio
        MultipartFile file = new MockMultipartFile("file.csv", new byte[0]);

        // Verifique se é lançada a exceção InvalidFileException
        assertThrows(InvalidFileException.class, () -> {
            FileValidator.validateColumns(file);
        });
    }

    @Test
    void validateColumns_InvalidColumnCount_ThrowsInvalidFileException() {
        // Crie um arquivo CSV com número de colunas divergente
        String csvData = "\"CNPJ\n (apenas os números)\",NOME FANTASIA\n";
        MultipartFile file = new MockMultipartFile("file.csv", csvData.getBytes());

        // Verifique se é lançada a exceção InvalidFileException
        assertThrows(InvalidFileException.class, () -> {
            FileValidator.validateColumns(file);
        });
    }

    @Test
    void validateColumns_InvalidColumnName_ThrowsInvalidFileException() {
        // Crie um arquivo CSV com nome de coluna inválido
        String csvData = "\"CNPJ\n (apenas os números)\",NOME FANTASIA,NRO DE DIAS UTEIS PARA VECTO DA FATURA,EMAIL COBRANÇA,QTDE MENSALIDADE,VALOR MENSALIDADE,VALOR  UNITARIO EMISSAO CARTÃO,QTDE CARTAO\n";
        MultipartFile file = new MockMultipartFile("file.csv", csvData.getBytes());

        // Verifique se é lançada a exceção InvalidFileException
        assertThrows(InvalidFileException.class, () -> {
            FileValidator.validateColumns(file);
        });
    }

    @Test
    void validateColumns_IOException_ThrowsInvalidFileException() throws IOException {
        // Crie um arquivo CSV com um InputStream que lança IOException
        MultipartFile file = new MockMultipartFile("file.csv", new ByteArrayInputStream(new byte[0]) {
            @Override
            public int read() {
                throw new RuntimeException("Erro ao ler o arquivo.");
            }
        });

        // Verifique se é lançada a exceção InvalidFileException
        assertThrows(InvalidFileException.class, () -> {
            FileValidator.validateColumns(file);
        });
    }
}
