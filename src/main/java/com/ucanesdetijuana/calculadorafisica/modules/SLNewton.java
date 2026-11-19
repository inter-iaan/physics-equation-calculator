package com.ucanesdetijuana.calculadorafisica.modules;

import com.ucanesdetijuana.calculadorafisica.core.convertions.AccelerationUnit;
import com.ucanesdetijuana.calculadorafisica.core.convertions.ConverterFactory;
import com.ucanesdetijuana.calculadorafisica.core.convertions.ForceUnit;
import com.ucanesdetijuana.calculadorafisica.core.convertions.MassUnit;
import com.ucanesdetijuana.calculadorafisica.core.convertions.UnitMetric;
import com.ucanesdetijuana.calculadorafisica.core.logic.Ecuation;
import com.ucanesdetijuana.calculadorafisica.core.logic.Variable;
import java.util.ArrayList;

/**
 * Implementación de la Segunda Ley de Newton como una Ecuación (F = m * a)
 */
public class SLNewton extends Ecuation {

    public SLNewton() {
        super("Segunda Ley de Newton", "F = m \\cdot a", new ArrayList<>());

        // 1. Variable: Fuerza (F) = m * a (solo en Newtons)
        this.getVariables().add(new Variable("Fuerza", "F", null, ForceUnit.NEWTON) {
            @Override
            public double calculate() {
                clearProcedure();

                Variable mVar = getVariable("Masa");
                Variable aVar = getVariable("Aceleración");

                double m = mVar.getValue();
                UnitMetric currentMUnit = mVar.getUnit();
                UnitMetric targetMUnit = MassUnit.KILOGRAMO;

                if (currentMUnit != targetMUnit) {
                    double convertedM = ConverterFactory.convert(targetMUnit.getMagnitudeType(), m, currentMUnit, targetMUnit);
                    addStep("\\text{Conversión } m: " + m + "\\text{ " + currentMUnit.getSymbol() + "} \\rightarrow " + String.format("%.4f", convertedM) + "\\text{ " + targetMUnit.getSymbol() + "}");
                    m = convertedM;
                }

                double a = aVar.getValue();
                UnitMetric currentAUnit = aVar.getUnit();
                UnitMetric targetAUnit = AccelerationUnit.M_S2;

                if (currentAUnit != targetAUnit) {
                    double convertedA = ConverterFactory.convert(targetAUnit.getMagnitudeType(), a, currentAUnit, targetAUnit);
                    addStep("\\text{Conversión } a: " + a + "\\text{ " + currentAUnit.getSymbol() + "} \\rightarrow " + String.format("%.6f", convertedA) + "\\text{ " + targetAUnit.getSymbol() + "}");
                    a = convertedA;
                }

                addStep("F = m \\cdot a");
                addStep("F = (" + String.format("%.2f", m) + "\\text{ kg}) \\cdot (" + String.format("%.4f", a) + "\\text{ m/s}^2)");

                double f = m * a;
                addStep("F = " + String.format("%.4f", f) + "\\text{ N}");

                return f;
            }
        });

        // 2. Variable: Masa (m) = F / a
        this.getVariables().add(new Variable("Masa", "m", null, MassUnit.KILOGRAMO) {
            @Override
            public double calculate() {
                clearProcedure();

                Variable fVar = getVariable("Fuerza");
                Variable aVar = getVariable("Aceleración");

                double f = fVar.getValue();
                double a = aVar.getValue();
                UnitMetric currentAUnit = aVar.getUnit();
                UnitMetric targetAUnit = AccelerationUnit.M_S2;

                if (currentAUnit != targetAUnit) {
                    double convertedA = ConverterFactory.convert(targetAUnit.getMagnitudeType(), a, currentAUnit, targetAUnit);
                    addStep("\\text{Conversión } a: " + a + "\\text{ " + currentAUnit.getSymbol() + "} \\rightarrow " + String.format("%.6f", convertedA) + "\\text{ " + targetAUnit.getSymbol() + "}");
                    a = convertedA;
                }

                addStep("m = \\frac{F}{a}");
                addStep("m = \\frac{" + String.format("%.2f", f) + "\\text{ N}}{" + String.format("%.4f", a) + "\\text{ m/s}^2}");

                double m = f / a;
                UnitMetric targetMUnit = this.getUnit();

                if (targetMUnit != MassUnit.KILOGRAMO) {
                    addStep("m_{calculada} = " + String.format("%.4f", m) + "\\text{ kg}");
                    double convertedM = ConverterFactory.convert(targetMUnit.getMagnitudeType(), m, MassUnit.KILOGRAMO, targetMUnit);
                    addStep("\\text{Conversión a unidad final: } " + String.format("%.4f", m) + "\\text{ kg} \\rightarrow " + String.format("%.4f", convertedM) + "\\text{ " + targetMUnit.getSymbol() + "}");
                    m = convertedM;
                } else {
                    addStep("m = " + String.format("%.4f", m) + "\\text{ kg}");
                }

                return m;
            }
        });

        // 3. Variable: Aceleración (a) = F / m
        this.getVariables().add(new Variable("Aceleración", "a", null, AccelerationUnit.M_S2) {
            @Override
            public double calculate() {
                clearProcedure();

                Variable fVar = getVariable("Fuerza");
                Variable mVar = getVariable("Masa");

                double f = fVar.getValue();
                double m = mVar.getValue();
                UnitMetric currentMUnit = mVar.getUnit();
                UnitMetric targetMUnit = MassUnit.KILOGRAMO;

                if (currentMUnit != targetMUnit) {
                    double convertedM = ConverterFactory.convert(targetMUnit.getMagnitudeType(), m, currentMUnit, targetMUnit);
                    addStep("\\text{Conversión } m: " + m + "\\text{ " + currentMUnit.getSymbol() + "} \\rightarrow " + String.format("%.4f", convertedM) + "\\text{ " + targetMUnit.getSymbol() + "}");
                    m = convertedM;
                }

                addStep("a = \\frac{F}{m}");
                addStep("a = \\frac{" + String.format("%.2f", f) + "\\text{ N}}{" + String.format("%.2f", m) + "\\text{ kg}}");

                double a = f / m;
                UnitMetric targetAUnit = this.getUnit();

                if (targetAUnit != AccelerationUnit.M_S2) {
                    addStep("a_{calculada} = " + String.format("%.4f", a) + "\\text{ m/s}^2");
                    double convertedA = ConverterFactory.convert(targetAUnit.getMagnitudeType(), a, AccelerationUnit.M_S2, targetAUnit);
                    addStep("\\text{Conversión a unidad final: } " + String.format("%.4f", a) + "\\text{ m/s}^2 \\rightarrow " + String.format("%.4f", convertedA) + "\\text{ " + targetAUnit.getSymbol() + "}");
                    a = convertedA;
                } else {
                    addStep("a = " + String.format("%.4f", a) + "\\text{ m/s}^2");
                }

                return a;
            }
        });
    }
}