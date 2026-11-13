package com.ucanesdetijuana.calculadorafisica.forms;

import com.ucanesdetijuana.calculadorafisica.core.logic.Ecuation;
import com.ucanesdetijuana.calculadorafisica.core.logic.Variable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

public class EcuationViewModel {
    private final Ecuation baseEcuation;
    
    private Consumer<List<String>> onProcedureGenerated;
    
    private Consumer<Map<String, String>> onResultCalculated; // <--- CORREGIDO AQUÍ
    
    // Callback para notificar errores en la UI (por ejemplo si faltan campos)
    private Consumer<List<String>> onErrorTriggered;

    public EcuationViewModel(Ecuation baseEcuation) {
        this.baseEcuation = baseEcuation;
    }

    // Métodos para suscribir la Vista
    public void setOnProcedureGenerated(Consumer<List<String>> listener) {
        this.onProcedureGenerated = listener;
    }

    public void setOnResultCalculated(Consumer<Map<String, String>> listener) { // <--- CORREGIDO AQUÍ
        this.onResultCalculated = listener;
    }

    public void setOnErrorTriggered(Consumer<List<String>> listener) {
        this.onErrorTriggered = listener;
    }

    /**
     * Procesa las entradas ingresadas en la UI (Mapa de nombreVariable -> textoDelCampo)
     */
    public void processCalculation(Map<String, String> inputs) {
        String emptyVariableName = null;
        int emptyCount = 0;
        boolean hasParseError = false;

        // FASE 1: Parsear e inyectar valores en el Modelo
        for (Variable var : baseEcuation.getVariables()) {
            String content = inputs.getOrDefault(var.getName(), "").trim();

            if (content.isBlank()) {
                emptyVariableName = var.getName();
                emptyCount++;
            } else {
                try {
                    double val = Double.parseDouble(content);
                    var.setValue(val);
                } catch (NumberFormatException e) {
                    hasParseError = true;
                }
            }
        }

        // FASE 2: Validaciones
        if (hasParseError) {
            System.err.println("Hay campos con formato numérico inválido.");
            return;
        }

        if (emptyCount == 1 && emptyVariableName != null) {
            Variable targetVar = baseEcuation.getVariable(emptyVariableName);
            
            // FASE 3: Cálculo y emisión de evento
            double result = targetVar.calculate();
            targetVar.setValue(result);

            // Notificar resultado a la Vista
            if (onResultCalculated != null) {
                Map<String, String> resultMap = new HashMap<>();
                resultMap.put(targetVar.getName(), String.valueOf(result));
                onResultCalculated.accept(resultMap);
            }

            // Notificar pasos LaTeX a la Vista
            if (onProcedureGenerated != null) {
                onProcedureGenerated.accept(targetVar.getProcedureSteps());
            }

        } else if (emptyCount > 1) {
            if (onErrorTriggered != null) {
                // Notifica qué campos están vacíos para marcarlos en rojo
                onErrorTriggered.accept(inputs.entrySet().stream()
                        .filter(e -> e.getValue().trim().isBlank())
                        .map(Map.Entry::getKey)
                        .toList());
            }
        }
    }

    public Ecuation getEcuation() {
        return baseEcuation;
    }
}