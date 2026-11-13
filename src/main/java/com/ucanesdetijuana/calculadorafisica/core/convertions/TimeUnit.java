package com.ucanesdetijuana.calculadorafisica.core.convertions;

/**
 *
 * @author José Alfredo Interián Polanco
 */
public enum TimeUnit implements UnitMetric {
    
    SEGUNDO (1.0, "Segundo", "s"),
    MINUTO (60.0, "Minuto", "min"),
    HORA (3600, "Hora", "h");
    
    private final double baseFactor;
    private final String name;
    private final String abr;

    private TimeUnit(double baseFactor, String name, String abr) {
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
    public ConverterFactory.MagnitudeType getMagnitudeType() { return ConverterFactory.MagnitudeType.TIME; }
    
    @Override
    public String toString () { return getSymbol();}
}
