package com.ucanesdetijuana.calculadorafisica.core.logic;
import com.ucanesdetijuana.calculadorafisica.core.convertions.UnitMetric;
import java.util.ArrayList;
import java.util.List;

/**
 * @author José Alfredo Interián Polanco
 * Define las características comunes de una variable del sistema
 */

public abstract class Variable implements Calculator {
    private final String name;
    private final String abr;
    private Double value;
    private UnitMetric unit;
    private List<String> procedureSteps = new ArrayList();

    public Variable(String name, String abr, Double value, UnitMetric unit) {
        this.name = name;
        this.abr = abr;
        this.value = value;
        this.unit = unit;
    }

    @Override
    public abstract double calculate();
    
    public List<String> getProcedureSteps() {
        return procedureSteps;
    }

    public void addStep(String latexStep) {
        this.procedureSteps.add(latexStep);
    }

    public void clearProcedure() {
        this.procedureSteps.clear();
    }

    public String getName() { return name; }
    public String getAbreviation() { return abr; }
    public UnitMetric getUnit() { return unit; }
    public Double getValue() { return value; }
    
    public void setValue (Double value) { this.value = value; }
    public void setUnit (UnitMetric unit) { this.unit = unit; }
}
