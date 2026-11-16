package com.ucanesdetijuana.calculadorafisica.modules;

import com.ucanesdetijuana.calculadorafisica.core.convertions.AngleUnit;
import com.ucanesdetijuana.calculadorafisica.core.convertions.ConverterFactory;
import com.ucanesdetijuana.calculadorafisica.core.convertions.DimensionlessUnit;
import com.ucanesdetijuana.calculadorafisica.core.convertions.UnitMetric;
import com.ucanesdetijuana.calculadorafisica.core.logic.Ecuation;
import com.ucanesdetijuana.calculadorafisica.core.logic.Variable;
import java.util.ArrayList;

/**
 *
 * @author Emanuel Alejandro Juárez López
 */
public class Snell extends Ecuation {
    
    public Snell() {
        super("Ley de Snell", "n_1 * sen(θ_1) = n_2 * sen(θ_2)", new ArrayList<>());
        this.getVariables().add(new Variable("Primer medio", "n_1", null, new DimensionlessUnit()) {
            @Override
            public double calculate() {
                clearProcedure();

                Variable n2Var = getVariable("Segundo medio");
                Variable theta1Var = getVariable("Incidencia");
                Variable theta2Var = getVariable("Refraccion");

                double n2 = n2Var.getValue();
                
                double theta1 = theta1Var.getValue();
                UnitMetric currentTheta1Unit = theta1Var.getUnit();
                
                if (currentTheta1Unit != AngleUnit.RADIAN) {
                    double convertedTheta1 = ConverterFactory.convert(ConverterFactory.MagnitudeType.ANGLE, theta1, currentTheta1Unit, AngleUnit.RADIAN);
                    addStep("\\text{Conversión } \\theta_1: " + theta1 + "\\text{ " + currentTheta1Unit.getSymbol() + "} \\rightarrow " + String.format("%.4f", convertedTheta1) + "\\text{ rad}");
                    theta1 = convertedTheta1;
                }
                
                double theta2 = theta2Var.getValue();
                UnitMetric currentTheta2Unit = theta2Var.getUnit();
                
                
                if (currentTheta2Unit != AngleUnit.RADIAN) {
                    double convertedTheta2 = ConverterFactory.convert(ConverterFactory.MagnitudeType.ANGLE, theta2, currentTheta2Unit, AngleUnit.RADIAN);
                    addStep("\\text{Conversión } \\theta_2: " + theta2 + "\\text{ " + currentTheta2Unit.getSymbol() + "} \\rightarrow " + String.format("%.4f", convertedTheta2) + "\\text{ rad}");
                    theta2 = convertedTheta2;
                }

                if (n2 <= 0) {
                    throw new IllegalArgumentException("El indice de refraccion n2 debe ser positivo.");
                }

                double sinTheta1 = Math.sin(theta1);

                if (Math.abs(sinTheta1) < 1e-12) {
                    throw new IllegalArgumentException("El seno del angulo de incidencia no puede ser cero.");
                }

                addStep("n_1 = \\frac{n_2 \\cdot \\sin(\\theta_2)}{\\sin(\\theta_1)}");
                addStep("n_1 = \\frac{" + String.format("%.4f", n2) + " \\cdot \\sin(" + String.format("%.4f", theta2) + ")}{\\sin(" + String.format("%.4f", theta1) + ")}");

                double n1 = (n2 * Math.sin(theta2)) / sinTheta1;
                addStep("n_1 = " + String.format("%.4f", n1));
                return n1;
            }
        });
        
        this.getVariables().add(new Variable("Segundo medio", "n_2", null, new DimensionlessUnit()) {
            @Override
            public double calculate() {
                clearProcedure();

                Variable n1Var = getVariable("Primer medio");
                Variable theta1Var = getVariable("Incidencia");
                Variable theta2Var = getVariable("Refraccion");

                double n1 = n1Var.getValue();
                
                double theta1 = theta1Var.getValue();
                UnitMetric currentTheta1Unit = theta1Var.getUnit();
                
                if (currentTheta1Unit != AngleUnit.RADIAN) {
                    double convertedTheta1 = ConverterFactory.convert(ConverterFactory.MagnitudeType.ANGLE, theta1, currentTheta1Unit, AngleUnit.RADIAN);
                    addStep("\\text{Conversión } \\theta_1: " + theta1 + "\\text{ " + currentTheta1Unit.getSymbol() + "} \\rightarrow " + String.format("%.4f", convertedTheta1) + "\\text{ rad}");
                    theta1 = convertedTheta1;
                }
                
                double theta2 = theta2Var.getValue();
                UnitMetric currentTheta2Unit = theta2Var.getUnit();
                
                
                if (currentTheta2Unit != AngleUnit.RADIAN) {
                    double convertedTheta2 = ConverterFactory.convert(ConverterFactory.MagnitudeType.ANGLE, theta2, currentTheta2Unit, AngleUnit.RADIAN);
                    addStep("\\text{Conversión } \\theta_2: " + theta2 + "\\text{ " + currentTheta2Unit.getSymbol() + "} \\rightarrow " + String.format("%.4f", convertedTheta2) + "\\text{ rad}");
                    theta2 = convertedTheta2;
                }

                if (n1 <= 0) {
                    throw new IllegalArgumentException("El indice de refraccion n1 debe ser positivo.");
                }

                double sinTheta2 = Math.sin(theta2);

                if (Math.abs(sinTheta2) < 1e-12) {
                    throw new IllegalArgumentException("El seno del angulo de refraccion no puede ser cero.");
                }

                addStep("n_2 = \\frac{n_1 \\cdot \\sin(\\theta_1)}{\\sin(\\theta_2)}");
                addStep("n_2 = \\frac{" + String.format("%.4f", n1) + " \\cdot \\sin(" + String.format("%.4f", theta1) + ")}{\\sin(" + String.format("%.4f", theta2) + ")}");

                double n2 = (n1 * Math.sin(theta1)) / sinTheta2;
                addStep("n_2 = " + String.format("%.4f", n2));
                return n2;
            }
        });

        this.getVariables().add(new Variable("Incidencia", "θ_1", null, AngleUnit.GRADO) {
            @Override
            public double calculate() {
                clearProcedure();

                Variable n1Var = getVariable("Primer medio");
                Variable n2Var = getVariable("Segundo medio");
                Variable theta2Var = getVariable("Refraccion");

                double n1 = n1Var.getValue();
                double n2 = n2Var.getValue();

                double theta2 = theta2Var.getValue();
                UnitMetric currentTheta2Unit = theta2Var.getUnit();

                if (currentTheta2Unit != AngleUnit.RADIAN) {
                    double convertedTheta2 = ConverterFactory.convert(ConverterFactory.MagnitudeType.ANGLE, theta2, currentTheta2Unit, AngleUnit.RADIAN);
                    addStep("\\text{Conversión } \\theta_2: " + theta2 + "\\text{ " + currentTheta2Unit.getSymbol() + "} \\rightarrow " + String.format("%.4f", convertedTheta2) + "\\text{ rad}");
                    theta2 = convertedTheta2;
                }

                if (n1 <= 0 || n2 <= 0) {
                    throw new IllegalArgumentException("Los indices de refraccion deben ser positivos.");
                }

                double argument = (n2 * Math.sin(theta2)) / n1;

                if (Math.abs(argument) > 1.0) {
                    throw new IllegalArgumentException("No existe un angulo de incidencia real para estos valores.");
                }

                addStep("\\theta_1 = \\arcsin\\left(\\frac{n_2 \\cdot \\sin(\\theta_2)}{n_1}\\right)");
                addStep("\\theta_1 = \\arcsin\\left(\\frac{" + String.format("%.4f", n2) + " \\cdot \\sin(" + String.format("%.4f", theta2) + ")}{" + String.format("%.4f", n1) + "}\\right)");

                double theta1Rad = Math.asin(argument);

                AngleUnit targetUnit = (AngleUnit) this.getUnit();

                double theta1 = ConverterFactory.convert(ConverterFactory.MagnitudeType.ANGLE, theta1Rad, AngleUnit.RADIAN, targetUnit);
                addStep("\\theta_1 = " + String.format("%.4f", theta1) + "\\text{ " + targetUnit.getSymbol() + "}");
                return theta1;
            }
        });
        
        this.getVariables().add(new Variable("Refraccion", "θ_2", null, AngleUnit.GRADO) {
            @Override
            public double calculate() {
                clearProcedure();

                Variable n1Var = getVariable("Primer medio");
                Variable n2Var = getVariable("Segundo medio");
                Variable theta1Var = getVariable("Incidencia");

                double n1 = n1Var.getValue();
                double n2 = n2Var.getValue();

                double theta1 = theta1Var.getValue();
                UnitMetric currentTheta1Unit = theta1Var.getUnit();

                if (currentTheta1Unit != AngleUnit.RADIAN) {
                    double convertedTheta1 = ConverterFactory.convert(ConverterFactory.MagnitudeType.ANGLE, theta1, currentTheta1Unit, AngleUnit.RADIAN);
                    addStep("\\text{Conversión } \\theta_1: " + theta1 + "\\text{ " + currentTheta1Unit.getSymbol() + "} \\rightarrow " + String.format("%.4f", convertedTheta1) + "\\text{ rad}");
                    theta1 = convertedTheta1;
                }

                if (n1 <= 0 || n2 <= 0) {
                    throw new IllegalArgumentException("Los indices de refraccion deben ser positivos.");
                }

                double argument = (n1 * Math.sin(theta1)) / n2;

                if (Math.abs(argument) > 1.0) {
                    throw new IllegalArgumentException("No existe un angulo de refraccion real para estos valores.");
                }

                addStep("\\theta_2 = \\arcsin\\left(\\frac{n_1 \\cdot \\sin(\\theta_1)}{n_2}\\right)");
                addStep("\\theta_2 = \\arcsin\\left(\\frac{" + String.format("%.4f", n1) + " \\cdot \\sin(" + String.format("%.4f", theta1) + ")}{" + String.format("%.4f", n2) + "}\\right)");

                double theta2Rad = Math.asin(argument);

                AngleUnit targetUnit = (AngleUnit) this.getUnit();

                double theta2 = ConverterFactory.convert(ConverterFactory.MagnitudeType.ANGLE, theta2Rad, AngleUnit.RADIAN, targetUnit);
                addStep("\\theta_2 = " + String.format("%.4f", theta2) + "\\text{ " + targetUnit.getSymbol() + "}");
                return theta2;
            }
        });
    }
}
