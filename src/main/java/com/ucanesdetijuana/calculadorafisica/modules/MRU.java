package com.ucanesdetijuana.calculadorafisica.modules;

import com.ucanesdetijuana.calculadorafisica.core.convertions.ConverterFactory;
import com.ucanesdetijuana.calculadorafisica.core.convertions.FractionalUnit;
import com.ucanesdetijuana.calculadorafisica.core.convertions.LengthUnit;
import com.ucanesdetijuana.calculadorafisica.core.convertions.TimeUnit;
import com.ucanesdetijuana.calculadorafisica.core.convertions.UnitMetric;
import com.ucanesdetijuana.calculadorafisica.core.logic.Ecuation;
import com.ucanesdetijuana.calculadorafisica.core.logic.Variable;
import java.util.ArrayList;

/**
 * @author José Alfredo Interián Polanco
 * Implementación del Movimiento Rectilíneo Uniforme como una Ecuación
 */
public class MRU extends Ecuation {

    public MRU() {
        super("Movimiento Rectilíneo Uniforme", "v = \\frac{d}{t}", new ArrayList<>());

        this.getVariables().add(new Variable("Velocidad", "v", null, new FractionalUnit(LengthUnit.METRO, TimeUnit.SEGUNDO)) {
            @Override
            public double calculate() {
                clearProcedure();

                Variable dVar = getVariable("Distancia");
                Variable tVar = getVariable("Tiempo");

                FractionalUnit targetVUnit = (FractionalUnit) this.getUnit();
                UnitMetric targetDUnit = targetVUnit.getNumerator();
                UnitMetric targetTUnit = targetVUnit.getDenominator();

                double d = dVar.getValue();
                UnitMetric currentDUnit = dVar.getUnit();
                
                if (currentDUnit != targetDUnit) {
                    double convertedD = ConverterFactory.convert(currentDUnit.getMagnitudeType(), d, currentDUnit, targetDUnit);
                    addStep("\\text{Conversión } d: " + d + "\\text{ " + currentDUnit.getSymbol() + "} \\rightarrow " + String.format("%.4f", convertedD) + "\\text{ " + targetDUnit.getSymbol() + "}");
                    d = convertedD;
                }

                double t = tVar.getValue();
                UnitMetric currentTUnit = tVar.getUnit();
                
                if (currentTUnit != targetTUnit) {
                    double convertedT = ConverterFactory.convert(currentTUnit.getMagnitudeType(), t, currentTUnit, targetTUnit);
                    addStep("\\text{Conversión } t: " + t + "\\text{ " + currentTUnit.getSymbol() + "} \\rightarrow " + String.format("%.4f", convertedT) + "\\text{ " + targetTUnit.getSymbol() + "}");
                    t = convertedT;
                }

                addStep("v = \\frac{d}{t}");
                addStep("v = \\frac{" + String.format("%.2f", d) + "\\text{ " + targetDUnit.getSymbol() + "}}{" + String.format("%.2f", t) + "\\text{ " + targetTUnit.getSymbol() + "}}");

                double v = d / t;
                String finalUnit = targetDUnit.getSymbol() + "/" + targetTUnit.getSymbol();
                addStep("v = " + String.format("%.4f", v) + "\\text{ " + finalUnit + "}");

                return v;
            }
        });

        this.getVariables().add(new Variable("Distancia", "d", null, LengthUnit.METRO) {
            @Override
            public double calculate() {
                clearProcedure();
                Variable vVar = getVariable("Velocidad");
                Variable tVar = getVariable("Tiempo");

                FractionalUnit vUnit = (FractionalUnit) vVar.getUnit();
                UnitMetric vLenUnit = vUnit.getNumerator();
                UnitMetric vTimeUnit = vUnit.getDenominator();

                double v = vVar.getValue();
                double t = tVar.getValue();
                UnitMetric currentTUnit = tVar.getUnit();

                if (currentTUnit != vTimeUnit) {
                    double convertedT = ConverterFactory.convert(currentTUnit.getMagnitudeType(), t, currentTUnit, vTimeUnit);
                    addStep("\\text{Conversión } t: " + t + "\\text{ " + currentTUnit.getSymbol() + "} \\rightarrow " + String.format("%.4f", convertedT) + "\\text{ " + vTimeUnit.getSymbol() + "}");
                    t = convertedT;
                }

                addStep("d = v \\cdot t");
                String vSymbol = vLenUnit.getSymbol() + "/" + vTimeUnit.getSymbol();
                addStep("d = (" + String.format("%.2f", v) + "\\text{ " + vSymbol + "}) \\cdot (" + String.format("%.2f", t) + "\\text{ " + vTimeUnit.getSymbol() + "})");

                double d = v * t;

                UnitMetric targetDUnit = this.getUnit();
                if (vLenUnit != targetDUnit) {
                    addStep("d_{calculada} = " + String.format("%.4f", d) + "\\text{ " + vLenUnit.getSymbol() + "}");
                    double convertedD = ConverterFactory.convert(vLenUnit.getMagnitudeType(), d, vLenUnit, targetDUnit);
                    addStep("\\text{Conversión a unidad final: } " + String.format("%.4f", d) + "\\text{ " + vLenUnit.getSymbol() + "} \\rightarrow " + String.format("%.4f", convertedD) + "\\text{ " + targetDUnit.getSymbol() + "}");
                    d = convertedD;
                } else {
                    addStep("d = " + String.format("%.4f", d) + "\\text{ " + targetDUnit.getSymbol() + "}");
                }

                return d;
            }
        });

        this.getVariables().add(new Variable("Tiempo", "t", null, TimeUnit.SEGUNDO) {
            @Override
            public double calculate() {
                clearProcedure();
                Variable dVar = getVariable("Distancia");
                Variable vVar = getVariable("Velocidad");

                FractionalUnit vUnit = (FractionalUnit) vVar.getUnit();
                UnitMetric vLenUnit = vUnit.getNumerator();
                UnitMetric vTimeUnit = vUnit.getDenominator();

                double d = dVar.getValue();
                UnitMetric currentDUnit = dVar.getUnit();
                double v = vVar.getValue();

                if (currentDUnit != vLenUnit) {
                    double convertedD = ConverterFactory.convert(currentDUnit.getMagnitudeType(), d, currentDUnit, vLenUnit);
                    addStep("\\text{Conversión } d: " + d + "\\text{ " + currentDUnit.getSymbol() + "} \\rightarrow " + String.format("%.4f", convertedD) + "\\text{ " + vLenUnit.getSymbol() + "}");
                    d = convertedD;
                }

                addStep("t = \\frac{d}{v}");
                String vSymbol = vLenUnit.getSymbol() + "/" + vTimeUnit.getSymbol();
                addStep("t = \\frac{" + String.format("%.2f", d) + "\\text{ " + vLenUnit.getSymbol() + "}}{" + String.format("%.2f", v) + "\\text{ " + vSymbol + "}}");

                double t = d / v;

                UnitMetric targetTUnit = this.getUnit();
                if (vTimeUnit != targetTUnit) {
                    addStep("t_{calculado} = " + String.format("%.4f", t) + "\\text{ " + vTimeUnit.getSymbol() + "}");
                    double convertedT = ConverterFactory.convert(vTimeUnit.getMagnitudeType(), t, vTimeUnit, targetTUnit);
                    addStep("\\text{Conversión a unidad final: } " + String.format("%.4f", t) + "\\text{ " + vTimeUnit.getSymbol() + "} \\rightarrow " + String.format("%.4f", convertedT) + "\\text{ " + targetTUnit.getSymbol() + "}");
                    t = convertedT;
                } else {
                    addStep("t = " + String.format("%.4f", t) + "\\text{ " + targetTUnit.getSymbol() + "}");
                }

                return t;
            }
        });
    }
}