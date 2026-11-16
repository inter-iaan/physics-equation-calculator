package com.ucanesdetijuana.calculadorafisica.core.convertions;

/**
 *
 * @author Emanuel Alejandro Juárez López
 */
public enum AngleUnit implements UnitMetric{

    RADIAN(1.0, "Radian", "rad"),
    GRADO(Math.PI / 180.0, "Grado", "°");

    private final double baseFactor;
    private final String name;
    private final String abr;

    private AngleUnit(double baseFactor, String name, String abr) {
        this.baseFactor = baseFactor;
        this.name = name;
        this.abr = abr;
    }
    
    @Override
    public String getName() { return name; }

    @Override
    public String getSymbol() { return abr; }

    @Override
    public ConverterFactory.MagnitudeType getMagnitudeType() { return ConverterFactory.MagnitudeType.ANGLE; }

    @Override
    public double toBase(double value) {
        return value * baseFactor;
    }

    @Override
    public double fromBaseTo(double unitValue) {
        return unitValue / baseFactor;
    }
    
    @Override
    public String toString () {return getSymbol(); }
    
}
