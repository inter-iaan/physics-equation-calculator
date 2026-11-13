package com.ucanesdetijuana.calculadorafisica.core.convertions;

/**
 *
 * @author José Alfredo Interián Polanco
 * Clase que convierte valores de tipo Longitud (Lenght)
 */
public enum LengthUnit implements UnitMetric {
    
    METRO(1.0, "Metro", "m"),
    KILOMETRO(1000.0, "Kilómetro", "km");
    
    private final double baseFactor;
    private final String name;
    private final String abr;

    private LengthUnit(double baseFactor, String name, String abr) {
        this.baseFactor = baseFactor;
        this.name = name;
        this.abr = abr;
    }
    
    @Override
    public String getName() { return name; }

    @Override
    public String getSymbol() { return abr; }

    @Override
    public double toBase(double value) {
        return value * baseFactor;
    }

    @Override
    public double fromBaseTo(double unitValue) {
        return unitValue / baseFactor;
    }

    @Override
    public ConverterFactory.MagnitudeType getMagnitudeType() { return ConverterFactory.MagnitudeType.LENGHT; }
    
    @Override
    public String toString () {return getSymbol(); }
}
