package com.ucanesdetijuana.calculadorafisica.core.logic;

/**
 *
 * @author José Alfredo Interián Polanco
 * Interfaz funcional que permite crear una fórmula para calcular de cierta forma particular.
 */
@FunctionalInterface
public interface Calculator {
    /**
     * Función que retorna un resultado en base a la relación de las variables dadas.
     * @return Resultado de la interacción
     */
    public double calculate();
}
