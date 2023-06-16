package com.challenge.invoice_generator.service;

import com.challenge.invoice_generator.dto.InvoiceItem;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import java.io.IOException;
import java.nio.charset.Charset;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Component
public class CSVImporter {
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public List<InvoiceItem> importData(MultipartFile file) {
        List<InvoiceItem> items = new ArrayList<>();

        try {
            CSVParser parser = CSVParser.parse(file.getInputStream(), Charset.defaultCharset(), CSVFormat.DEFAULT.withDelimiter(';').withFirstRecordAsHeader());

            for (CSVRecord record : parser) {
                String cnpj = record.get("CNPJ (apenas os números)");
                String nomeFantasia = record.get("NOME FANTASIA");
                int numDiasUteis = Integer.parseInt(record.get("NRO DE DIAS UTEIS PARA VECTO DA FATURA"));
                String emailCobranca = record.get("EMAIL COBRANÇA");
                int qtdeMensalidade = Integer.parseInt(record.get("QTDE MENSALIDADE"));
                double valorMensalidade = Double.parseDouble(record.get("VALOR MENSALIDADE"));
                double valorUnitarioCartao = Double.parseDouble(record.get("VALOR UNITARIO EMISSAO CARTÃO"));
                int qtdeCartaoEmitidos = Integer.parseInt(record.get("QTDE CARTAO EMITIDOS"));

                String dataVencimento = calculateVencimento(numDiasUteis);

                InvoiceItem item = new InvoiceItem();
                item.setId(generateId());
                item.setStatus("PENDENTE");
                item.setCnpj(cnpj);
                item.setNomeFantasia(nomeFantasia);
                item.setEmail(emailCobranca);
                item.setValorMensalidade(valorMensalidade);
                item.setValorUnitarioCartao(valorUnitarioCartao);
                item.setVencimento(dataVencimento);

                items.add(item);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return items;
    }

    private String calculateVencimento(int numDiasUteis) {
        // Calcular a data de vencimento considerando apenas os dias úteis
        LocalDate dataVencimento = LocalDate.now();
        int count = 0;
        while (count < numDiasUteis) {
            dataVencimento = dataVencimento.plusDays(1);
            if (isDiaUtil(dataVencimento)) {
                count++;
            }
        }
        return dataVencimento.format(DATE_FORMATTER);
    }

    private boolean isDiaUtil(LocalDate date) {
        DayOfWeek dayOfWeek = date.getDayOfWeek();
        return dayOfWeek != DayOfWeek.SATURDAY && dayOfWeek != DayOfWeek.SUNDAY;
    }

    private String generateId() {
        // Lógica para gerar um ID
        // ...

        return "123";
    }
}


