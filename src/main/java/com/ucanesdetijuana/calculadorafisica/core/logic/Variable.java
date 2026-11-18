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
    private final List<String> procedureSteps = new ArrayList();
    private boolean isTarget;

    public Variable(String name, String abr, Double value, UnitMetric unit) {
        this.name = name;
        this.abr = abr;
        this.value = value;
        this.unit = unit;
        isTarget = false;
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
    public boolean getTarget () { return isTarget;}
    
    public void setValue (Double value) { this.value = value; }
    public void setUnit (UnitMetric unit) { this.unit = unit; }
    public void setTarget (boolean t) { isTarget = t; }
}
