package com.ucanesdetijuana.calculadorafisica.core.convertions;

/**
 * Permite implementar magnitudes compuestas de una relación
 * @author José Alfredo Interián Polanco
 */
public class FractionalUnit implements UnitMetric {
    private UnitMetric numerator;
    private UnitMetric denominator;

    public FractionalUnit(UnitMetric numerator, UnitMetric denominator) {
        this.numerator = numerator;
        this.denominator = denominator;
    }

    public UnitMetric getNumerator() { return numerator; }
    public void setNumerator(UnitMetric numerator) { this.numerator = numerator; }
    
    public UnitMetric getDenominator() { return denominator; }
    public void setDenominator(UnitMetric denominator) { this.denominator = denominator; }

    @Override
    public String getName() {
        return numerator.getName() + " por " + denominator.getName();
    }

    @Override
    public String getSymbol() {
        return numerator.getSymbol() + "/" + denominator.getSymbol();
    }

    @Override
    public ConverterFactory.MagnitudeType getMagnitudeType() { return null; }

    @Override
    public double toBase(double value) {
        double numFactor = numerator.toBase(1.0);
        double denFactor = denominator.toBase(1.0);
        return value * (numFactor / denFactor);
    }

    @Override
    public double fromBaseTo(double unitValue) {
        double numFactor = numerator.toBase(1.0);
        double denFactor = denominator.toBase(1.0);
        return unitValue / (numFactor / denFactor);
    }
}