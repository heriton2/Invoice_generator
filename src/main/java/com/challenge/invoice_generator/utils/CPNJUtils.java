package com.challenge.invoice_generator.utils;

import org.springframework.util.StringUtils;

public class CPNJUtils {

    private static final String CNPJ_MASK = "##.###.###/####-##";

    public static String formatCNPJ(String cnpj) {
        if (StringUtils.isEmpty(cnpj) || cnpj.length() != 14) {
            return cnpj;
        }

        StringBuilder formattedCnpj = new StringBuilder(CNPJ_MASK);
        int cnpjIndex = 0;

        for (int i = 0; i < formattedCnpj.length(); i++) {
            if (formattedCnpj.charAt(i) == '#') {
                formattedCnpj.setCharAt(i, cnpj.charAt(cnpjIndex));
                cnpjIndex++;
            }
        }

        return formattedCnpj.toString();
    }
}

