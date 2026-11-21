package com.ucanesdetijuana.calculadorafisica.forms;

import com.ucanesdetijuana.calculadorafisica.core.logic.Ecuation;
import com.ucanesdetijuana.calculadorafisica.core.logic.Variable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

public class EcuationViewModel {
    private Ecuation baseEcuation;
    
    private Consumer<List<String>> onProcedureGenerated;
    
    private Consumer<Map<String, String>> onResultCalculated;
    
    private Consumer<List<String>> onErrorTriggered;

    public EcuationViewModel(Ecuation baseEcuation) {
        this.baseEcuation = baseEcuation;
    }

    public void setOnProcedureGenerated(Consumer<List<String>> listener) {
        this.onProcedureGenerated = listener;
    }

    public void setOnResultCalculated(Consumer<Map<String, String>> listener) {
        this.onResultCalculated = listener;
    }

    public void setOnErrorTriggered(Consumer<List<String>> listener) {
        this.onErrorTriggered = listener;
    }

    /**
     * Procesa las entradas ingresadas en la UI (Mapa de nombreVariable -> textoDelCampo)
     * @param inputs
     */
    public void processCalculation(Map<String, String> inputs) {
        String emptyVariableName = null;
        int emptyCount = 0;
        boolean hasParseError = false;

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

        if (hasParseError) {
            System.err.println("Hay campos con formato numérico inválido.");
            return;
        }

        if (emptyCount == 1 && emptyVariableName != null) {
            Variable targetVar = baseEcuation.getVariable(emptyVariableName);
            
            double result = targetVar.calculate();
            targetVar.setValue(result);

            if (onResultCalculated != null) {
                Map<String, String> resultMap = new HashMap<>();
                resultMap.put(targetVar.getName(), String.valueOf(result));
                onResultCalculated.accept(resultMap);
            }

            if (onProcedureGenerated != null) {
                onProcedureGenerated.accept(targetVar.getProcedureSteps());
            }

        } else if (emptyCount > 1) {
            if (onErrorTriggered != null) {
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
    
    public void setEcuation (Ecuation ec) {
        this.baseEcuation = ec;
    }
}