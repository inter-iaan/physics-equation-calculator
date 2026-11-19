/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ucanesdetijuana.calculadorafisica.core.convertions;

/**
 *
 * @author esgd9
 */
public enum ForceUnit implements UnitMetric {
    NEWTON(1.0, "Newton", "N");

    private final double baseFactor;
    private final String name;
    private final String abr;

    private ForceUnit(double baseFactor, String name, String abr) {
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
        return ConverterFactory.MagnitudeType.FORCE;
    }

    @Override
    public String toString() { return getSymbol(); }
}