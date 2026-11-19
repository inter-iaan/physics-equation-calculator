
package com.ucanesdetijuana.calculadorafisica.modules;

import com.ucanesdetijuana.calculadorafisica.core.convertions.DimensionlessUnit;
import com.ucanesdetijuana.calculadorafisica.core.convertions.LengthUnit;
import com.ucanesdetijuana.calculadorafisica.core.logic.Ecuation;
import com.ucanesdetijuana.calculadorafisica.core.logic.Variable;
import java.util.ArrayList;

public class lentesConcavos extends Ecuation {

    public lentesConcavos() {
        super(
            "Lentes cóncavos",
            "\\frac{1}{f}=(n-1)\\left(\\frac{1}{R_1}-\\frac{1}{R_2}\\right)",
            new ArrayList<>()
        );

        // Distancia focal
        getVariables().add(new Variable(
            "Distancia focal", "f", null, LengthUnit.METRO
        ) {
            @Override
            public double calculate() {
                clearProcedure();

                double n = getVariable("Índice de refracción").getValue();
                double r1 = obtenerMetros("Radio de curvatura 1");
                double r2 = obtenerMetros("Radio de curvatura 2");

                double denominador = (n - 1) * (1 / r1 - 1 / r2);

                validarNoCero(denominador, "No se puede calcular la distancia focal.");

                double resultado = 1 / denominador;

                addStep("\\frac{1}{f}=(" + n + "-1)\\left(\\frac{1}{" + r1 + "}-\\frac{1}{" + r2 + "}\\right)");
                addStep("\\frac{1}{f}=" + denominador);
                addStep("f=\\frac{1}{" + denominador + "}");
                addStep("f=" + resultado + "\\text{ m}");

                return getVariable("Distancia focal").getUnit().fromBaseTo(resultado);
            }
        });

        // Índice de refracción
        getVariables().add(new Variable(
            "Índice de refracción", "n", null, DimensionlessUnit.SIN_UNIDAD
        ) {
            @Override
            public double calculate() {
                clearProcedure();

                double f = obtenerMetros("Distancia focal");
                double r1 = obtenerMetros("Radio de curvatura 1");
                double r2 = obtenerMetros("Radio de curvatura 2");

                double denominador = 1 / r1 - 1 / r2;

                validarNoCero(denominador, "No se puede calcular el índice de refracción.");

                double resultado = 1 + (1 / f) / denominador;

                addStep("\\frac{1}{f}=(n-1)\\left(\\frac{1}{R_1}-\\frac{1}{R_2}\\right)");
                addStep("n-1=\\frac{\\frac{1}{f}}{\\frac{1}{R_1}-\\frac{1}{R_2}}");
                addStep("n=1+\\frac{\\frac{1}{" + f + "}}{" + denominador + "}");
                addStep("n=" + resultado);

                return resultado;
            }
        });

        // Radio de curvatura R1
        getVariables().add(new Variable(
            "Radio de curvatura 1", "R1", null, LengthUnit.METRO
        ) {
            @Override
            public double calculate() {
                clearProcedure();

                double f = obtenerMetros("Distancia focal");
                double n = getVariable("Índice de refracción").getValue();
                double r2 = obtenerMetros("Radio de curvatura 2");

                validarNoCero(n - 1, "El índice de refracción no puede ser igual a 1.");

                double denominador = 1 / (f * (n - 1)) + 1 / r2;

                validarNoCero(denominador, "No se puede calcular R1.");

                double resultado = 1 / denominador;

                addStep("\\frac{1}{f}=(n-1)\\left(\\frac{1}{R_1}-\\frac{1}{R_2}\\right)");
                addStep("\\frac{1}{R_1}=\\frac{1}{f(n-1)}+\\frac{1}{R_2}");
                addStep("\\frac{1}{R_1}=\\frac{1}{" + (f * (n - 1)) + "}+\\frac{1}{" + r2 + "}");
                addStep("R_1=\\frac{1}{" + denominador + "}");
                addStep("R_1=" + resultado + "\\text{ m}");

                return getVariable("Radio de curvatura 1").getUnit().fromBaseTo(resultado);
            }
        });

        // Radio de curvatura R2
        getVariables().add(new Variable(
            "Radio de curvatura 2", "R2", null, LengthUnit.METRO
        ) {
            @Override
            public double calculate() {
                clearProcedure();

                double f = obtenerMetros("Distancia focal");
                double n = getVariable("Índice de refracción").getValue();
                double r1 = obtenerMetros("Radio de curvatura 1");

                validarNoCero(n - 1, "El índice de refracción no puede ser igual a 1.");

                double denominador = 1 / r1 - 1 / (f * (n - 1));

                validarNoCero(denominador, "No se puede calcular R2.");

                double resultado = 1 / denominador;

                addStep("\\frac{1}{f}=(n-1)\\left(\\frac{1}{R_1}-\\frac{1}{R_2}\\right)");
                addStep("\\frac{1}{R_2}=\\frac{1}{R_1}-\\frac{1}{f(n-1)}");
                addStep("\\frac{1}{R_2}=\\frac{1}{" + r1 + "}-\\frac{1}{" + (f * (n - 1)) + "}");
                addStep("R_2=\\frac{1}{" + denominador + "}");
                addStep("R_2=" + resultado + "\\text{ m}");

                return getVariable("Radio de curvatura 2").getUnit().fromBaseTo(resultado);
            }
        });
    }

    // Convierte cualquier variable de longitud a metros
    private double obtenerMetros(String nombre) {
        Variable variable = getVariable(nombre);

        double valor = variable.getValue();
        double resultado = variable.getUnit().toBase(valor);

        validarNoCero(resultado, nombre + " no puede ser cero.");

        return resultado;
    }

    // Evita divisiones entre cero y resultados no válidos
    private void validarNoCero(double valor, String mensaje) {
        if (!Double.isFinite(valor) || valor == 0.0) {
            throw new IllegalArgumentException(mensaje);
        }
    }
}
