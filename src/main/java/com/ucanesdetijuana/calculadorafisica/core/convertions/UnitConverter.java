package com.ucanesdetijuana.calculadorafisica.core.convertions;

/**
 *
 * @author José Alfredo Interiánm Polanco
 * Controla el flujo de entradas y salidas para las conversiones
 */
public class UnitConverter {
    public static <T extends UnitMetric> double convert (double value, T input, T output) {
        if (input == output) return value;
        double base = input.toBase(value);
        return output.fromBaseTo(base);
    }
}
