package com.ucanesdetijuana.calculadorafisica.core.logic;

import java.util.ArrayList;

/**
 * 
 * @author José Alfredo Interián Polanco
 */
public class Ecuation {
    private final String name;
    private final String latexFormula;
    private final ArrayList<Variable> variables;
    
    /**
     * Crea una instancia de tipo Ecuación
     * @param name Nombre de la Ecuación
     * @param variables Lista de Variables de la Ecuación
     * @param latex Cadena de Texto que contiene la fórmula base en formato LaTe
     */
    public Ecuation (String name, String latex, ArrayList<Variable> variables) {
        this.variables = variables;
        this.name = name;
        latexFormula = latex;
    }

    public String getName() { return name; }
    public ArrayList<Variable> getVariables() { return variables; }
    public Variable getVariable(String name) {
        for (Variable var : variables) {
            if (var.getName().equalsIgnoreCase(name)) {
                return var;
            }
        }
        return null;
    }

    public String getLatexFormula() { return latexFormula; }
}
