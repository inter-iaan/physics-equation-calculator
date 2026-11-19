/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ucanesdetijuana.calculadorafisica.core.convertions;

/**
 *
 * @author esgd9
 */
public enum AccelerationUnit implements UnitMetric {
    M_S2(1.0, "Metro por segundo al cuadrado", "m/s²"),
    KM_H2(1000.0 / (3600.0 * 3600.0), "Kilómetro por hora al cuadrado", "km/h²");

    private final double baseFactor;
    private final String name;
    private final String abr;

    private AccelerationUnit(double baseFactor, String name, String abr) {
        this.baseFactor = baseFactor;
        this.name = name;
        this.abr = abr;
    }

    @Override
    public String getName() { return name; }

    @Override
    public String getSymbol() { return abr; }

    @Override
    public double toBase(double value) { return value * baseFactor; }

    @Override
    public double fromBaseTo(double unitValue) { return unitValue / baseFactor; }

    @Override
    public ConverterFactory.MagnitudeType getMagnitudeType() {
        return ConverterFactory.MagnitudeType.ACCELERATION;
    }

    @Override
    public String toString() { return getSymbol(); }
}
