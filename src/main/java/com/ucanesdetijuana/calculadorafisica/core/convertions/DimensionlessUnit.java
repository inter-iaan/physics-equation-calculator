
package com.ucanesdetijuana.calculadorafisica.core.convertions;

public enum DimensionlessUnit implements UnitMetric {

    SIN_UNIDAD("Sin unidad", "");

    private final String name;
    private final String symbol;

    DimensionlessUnit(String name, String symbol) {
        this.name = name;
        this.symbol = symbol;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getSymbol() {
        return symbol;
    }

    @Override
    public ConverterFactory.MagnitudeType getMagnitudeType() {
        return null;
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
