package com.ucanesdetijuana.calculadorafisica.core.convertions;

/**
 *
 * @author Emanuel Alejandro Juárez López
 */
public class DimensionlessUnit implements UnitMetric{

    @Override
    public String getName() {
        return "Adimensional";
    }

    @Override
    public String getSymbol() { return ""; }

    @Override
    public ConverterFactory.MagnitudeType getMagnitudeType() {
        return ConverterFactory.MagnitudeType.DIMENSIONLESS;
    }

    @Override
    public double toBase(double value) {
        return value;
    }

    @Override
    public double fromBaseTo(double unitValue) {
        return unitValue;
    }
    
    @Override
    public String toString() {
        return getSymbol();
    }
}
