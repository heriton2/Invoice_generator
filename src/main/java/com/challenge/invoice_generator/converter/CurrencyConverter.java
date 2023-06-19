package com.challenge.invoice_generator.converter;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;

public class CurrencyConverter {
    public static String formatCurrency(double value) {
        DecimalFormatSymbols symbols = new DecimalFormatSymbols(Locale.getDefault());
        symbols.setCurrencySymbol("R$");
        symbols.setGroupingSeparator('.');
        symbols.setDecimalSeparator(',');

        DecimalFormat decimalFormat = new DecimalFormat("#,##0.00", symbols);
        String formattedValue = decimalFormat.format(value);

        return "R$ " + formattedValue;
    }
}
