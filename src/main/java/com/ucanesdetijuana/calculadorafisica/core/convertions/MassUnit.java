/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ucanesdetijuana.calculadorafisica.core.convertions;


/**
 *
 * @author esgd9
 */
public enum MassUnit implements UnitMetric {
    
    KILOGRAMO(1.0, "Kilogramo", "kg"),
    GRAMO(0.001, "Gramo", "g"),
    MILIGRAMO(0.000001, "Miligramo", "mg");
    
    private final double baseFactor;
    private final String name;
    private final String abr;

    private MassUnit(double baseFactor, String name, String abr) {
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
    public ConverterFactory.MagnitudeType getMagnitudeType() { 
        return ConverterFactory.MagnitudeType.MASS; 
    }
    
    @Override
    public String toString() { return getSymbol(); }
}