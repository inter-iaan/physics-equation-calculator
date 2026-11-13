package com.ucanesdetijuana.calculadorafisica.forms;

import com.ucanesdetijuana.calculadorafisica.core.convertions.ConverterFactory;
import com.ucanesdetijuana.calculadorafisica.core.convertions.FractionalUnit;
import com.ucanesdetijuana.calculadorafisica.core.convertions.UnitMetric;
import com.ucanesdetijuana.calculadorafisica.core.logic.EcuationViewModel;
import com.ucanesdetijuana.calculadorafisica.core.logic.Variable;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.util.ArrayList;
import java.util.List;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import org.scilab.forge.jlatexmath.TeXConstants;
import org.scilab.forge.jlatexmath.TeXFormula;
import org.scilab.forge.jlatexmath.TeXIcon;

/**
 *
 * @author José Alfredo Interián Polanco
 * Diálogo que permite la inserción de contenido a las variables internas del sistema
 */
public class Writer extends javax.swing.JDialog {
    
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(Writer.class.getName());

    private EcuationViewModel model;
    private final List<Writer.FormRelation> variableList = new ArrayList();
    
    private record FormRelation(Variable variable, JTextField field, JLabel errorLabel) {
        public void showErr() { errorLabel.setVisible(true); }
        public void hideErr() { errorLabel.setVisible(false); }
    }
    
    /**
     * Creates new form Writer
     * @param parent 
     * @param modal 
     * @param model 
     * 
     */
    public Writer(java.awt.Frame parent, boolean modal, EcuationViewModel model) {
        super(parent, modal);
        this.model = model;
        
        initComponents();
        
        Variables.add(generateForm());
        Variables.revalidate();
        Variables.repaint();
        
        this.pack();
        this.setResizable(false);
        
        this.setLocationRelativeTo(parent);
    }
    
    private void processFractional (JPanel localVariable, FractionalUnit fracUnit) {
        UnitMetric[] numUnits = ConverterFactory.getAllUnits(fracUnit.getNumerator().getMagnitudeType());
        JComboBox<UnitMetric> comboNum = new JComboBox<>(new DefaultComboBoxModel<>(numUnits));
        comboNum.setPreferredSize(new Dimension(50, 25));
        comboNum.setSelectedItem(fracUnit.getNumerator());

        UnitMetric[] denUnits = ConverterFactory.getAllUnits(fracUnit.getDenominator().getMagnitudeType());
        JComboBox<UnitMetric> comboDen = new JComboBox<>(new DefaultComboBoxModel<>(denUnits));
        comboDen.setPreferredSize(new Dimension(50, 25));
        comboDen.setSelectedItem(fracUnit.getDenominator());

        comboNum.addActionListener(e -> fracUnit.setNumerator((UnitMetric) comboNum.getSelectedItem()));
        comboDen.addActionListener(e -> fracUnit.setDenominator((UnitMetric) comboDen.getSelectedItem()));

        localVariable.add(comboNum);
        localVariable.add(new JLabel(" / "));
        localVariable.add(comboDen);
    }
    
    private void processNormally (JPanel localVariable, Variable variable) {
        UnitMetric unit = variable.getUnit();
        
        UnitMetric[] units = ConverterFactory.getAllUnits(unit.getMagnitudeType());
        JComboBox<UnitMetric> magnitudes = new JComboBox<>(new DefaultComboBoxModel<>(units));
        magnitudes.setPreferredSize(new Dimension(50,25));
        magnitudes.setSelectedItem(unit);

        magnitudes.addActionListener(e -> { 
            UnitMetric selectedUnit = (UnitMetric) magnitudes.getSelectedItem();
            variable.setUnit(selectedUnit);
        });

        localVariable.add(magnitudes);
    }
    
    /**
     * Genera el formulario base a partir de las variables que tenga la ecuación a mostrar
     * @return JPanel Panel de visualización
     */
    public JPanel generateForm () {
        GridBagConstraints gbc = new GridBagConstraints();
        
        JPanel formPanel = new JPanel(new GridBagLayout());
        
        int x = 0;
        
        for (Variable variable : model.getEcuation().getVariables()) {
            JPanel localVariable = new JPanel(new BorderLayout());
            
            JPanel header = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 5));
            JPanel body = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 5));
            
            JLabel error = new JLabel("Por favor. Verifique la entrada.");
            error.setForeground(Color.red);
            error.setVisible(false);
            
            TeXFormula nameFormula = new TeXFormula("\\text{" + variable.getName() + " } (" + variable.getAbreviation() + ")");
            TeXIcon icon = nameFormula.createTeXIcon(TeXConstants.STYLE_DISPLAY, 18);
            
            JLabel label = new JLabel();
            label.setIcon(icon);

            JTextField field = new JTextField(5);
            
            header.add(label);
            header.add(error);
            
            body.add(field);

            UnitMetric unit = variable.getUnit();

            if (unit instanceof FractionalUnit fracUnit) {
                processFractional(body, fracUnit);
            } else {
                processNormally(body, variable);
            }
            
            localVariable.add(header, BorderLayout.NORTH);
            localVariable.add(body, BorderLayout.SOUTH);            
            
            variableList.add(new Writer.FormRelation(variable, field, error));
            
            gbc.gridx = 0;
            gbc.gridy = x;
            gbc.fill = GridBagConstraints.BOTH;
            gbc.anchor = GridBagConstraints.NORTHWEST;
            gbc.weightx = 1.0;
            
            x++;
            
            formPanel.add(localVariable, gbc);
        }
        
        JButton calculateBtn = new JButton("Calcular");
        
        calculateBtn.addActionListener((evt) -> {
            Writer.FormRelation emptyContent = null;
            int emptyCount = 0;
            boolean hasErrors = false;

            for (Writer.FormRelation relation : variableList) {
                relation.hideErr();
                String content = relation.field.getText().trim();

                if (content.isBlank()) {
                    emptyContent = relation;
                    emptyCount++;
                } else {
                    try {
                        double val = Double.parseDouble(content);
                        relation.variable.setValue(val);
                    } catch (NumberFormatException exception) {
                        relation.showErr();
                        hasErrors = true;
                    }
                }
            }

            if (hasErrors) {
                System.err.println("Hay campos con formato numérico inválido.");
                return;
            }

            if (emptyCount == 1 && emptyContent != null) {
                double result = emptyContent.variable.calculate();
                emptyContent.variable.setValue(result);
                emptyContent.field.setText(String.valueOf(result));

                List<String> latex = emptyContent.variable.getProcedureSteps();
                
                System.out.println("TODO!!!!: MANDAR A LLAMAR A UN EVENTO PARA QUE RENDERICE EL CONTENIDO: // procedures.renderProcedure(latex);");                
            } else if (emptyCount > 1) {
                for (Writer.FormRelation relation : variableList) {
                    if (relation.field.getText().trim().isBlank()) {
                        relation.showErr();
                    }
                }                
            }
        });

        gbc.gridx = 0;
        gbc.gridy = x + 1;
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.EAST; 
        gbc.weightx = 1.0;

        formPanel.add(calculateBtn, gbc);
        
        return formPanel;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        Variables = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Asignación de variables - Calculadora para Ecuaciones Físicas");

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel1.setText("Asignando valores...");

        jLabel2.setText("<html>En las siguientes casillas de variables, deje vacía <u>únicamente</u> la que desea calcular.</html>");

        Variables.setLayout(new javax.swing.BoxLayout(Variables, javax.swing.BoxLayout.Y_AXIS));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(Variables, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(Variables, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel Variables;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    // End of variables declaration//GEN-END:variables
}
