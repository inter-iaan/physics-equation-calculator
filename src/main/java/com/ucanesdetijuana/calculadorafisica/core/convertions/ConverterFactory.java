package com.ucanesdetijuana.calculadorafisica.core.convertions;

/**
 *
 * @author José Alfredo Interián Polanco
 * Controla la instancia para la magnitud de conversión de forma automática
 */
public class ConverterFactory {
    public enum MagnitudeType {
        LENGHT, TIME, DIMENSIONLESS, ANGLE
    }
    
    public static UnitMetric[] getAllUnits (MagnitudeType type) {
        return switch (type) {
            case LENGHT -> LengthUnit.values();
            case TIME -> TimeUnit.values();
            case DIMENSIONLESS -> new UnitMetric[]{new DimensionlessUnit()};
            case ANGLE -> AngleUnit.values();
        };
    }
    
    public static double convert (MagnitudeType type, double value, UnitMetric input, UnitMetric output) {
        if (!input.getClass().equals(output.getClass())) {
            throw new IllegalArgumentException(
                "Invalid convertion! You can't convert from " + 
                input.getName() + " to " + output.getName()
            );
        }
        return UnitConverter.convert(value, input, output);
    }
}
