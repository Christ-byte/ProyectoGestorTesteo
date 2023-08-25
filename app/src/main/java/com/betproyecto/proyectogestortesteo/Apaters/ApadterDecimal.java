package com.betproyecto.proyectogestortesteo.Apaters;

import android.text.InputFilter;
import android.text.Spanned;

public class ApadterDecimal implements InputFilter {
    private static final int MAX_DECIMAL_DIGITS = 2;

    @Override
    public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
        StringBuilder builder = new StringBuilder(dest);
        builder.replace(dstart, dend, source.subSequence(start, end).toString());
        String updatedText = builder.toString();

        // Verificar si el texto actualizado cumple con el formato decimal
        if (isValidDecimal(updatedText)) {
            return null; // Aceptar el cambio
        } else {
            return ""; // Ignorar el cambio
        }
    }

    private boolean isValidDecimal(String str) {
        // Verificar si el texto cumple con el formato decimal (2 decimales)
        return str.matches("^\\d+(\\.\\d{0,2})?$");
    }
}
