package com.ucanesdetijuana.calculadorafisica.forms;

import org.scilab.forge.jlatexmath.TeXConstants;
import org.scilab.forge.jlatexmath.TeXFormula;
import org.scilab.forge.jlatexmath.TeXIcon;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class ProcedurePanel extends JPanel {

    public ProcedurePanel() {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
    }

    public void renderProcedure(List<String> latexSteps) {
        this.removeAll();

        for (String stepLatex : latexSteps) {
            try {
                TeXFormula formula = new TeXFormula(stepLatex);
                TeXIcon icon = formula.createTeXIcon(TeXConstants.STYLE_DISPLAY, 18);

                JLabel stepLabel = new JLabel(icon);
                stepLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
                stepLabel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
                
                this.add(stepLabel);
            } catch (Exception e) {
                JLabel errLabel = new JLabel("Error al renderizar: " + stepLatex);
                errLabel.setForeground(Color.RED);
                this.add(errLabel);
            }
        }

        this.revalidate();
        this.repaint();
    }
}