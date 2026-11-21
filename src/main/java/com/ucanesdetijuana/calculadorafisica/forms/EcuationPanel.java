package com.ucanesdetijuana.calculadorafisica.forms;

import com.formdev.flatlaf.FlatClientProperties;
import com.formdev.flatlaf.extras.FlatSVGIcon;
import com.ucanesdetijuana.calculadorafisica.core.logic.Ecuation;
import com.ucanesdetijuana.calculadorafisica.core.logic.Variable;
import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.util.List;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import org.scilab.forge.jlatexmath.TeXConstants;
import org.scilab.forge.jlatexmath.TeXFormula;
import org.scilab.forge.jlatexmath.TeXIcon;

/**
 *
 * @author José Alfredo Interián Polanco
 * Muestra el contenido de un objeto de tipo Ecuation para que el usuario lo pueda manipular.
 */
public final class EcuationPanel extends javax.swing.JPanel {

    private final EcuationViewModel model;
    private final ProcedurePanel procedures;
    private final ProcedurePanel result;
    private final Start parent;
    
    EditedEvent editedEvt = new EditedEvent();
    
    /**
     * Crea la instancia del panel
     * @param model Modelo de los datos a mostrar
     * @param parent
     */
    public EcuationPanel(EcuationViewModel model, Start parent) {
        this.model = model;
        this.procedures = new ProcedurePanel();
        this.result = new ProcedurePanel();
        this.parent = (Start) parent;
        
        initComponents();
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 1;
        
        gbc.fill = GridBagConstraints.BOTH;
        gbc.anchor = GridBagConstraints.NORTHWEST;
        
        onCreateVariables(VariablesPanel);
        onCreateFormulas(Formulas);
        onCreateProcedures(Procedure);
        onCreateResult(Result);
        
        parent.addLoadListener((equation) -> {
            if (equation.getClass() == model.getEcuation().getClass()) { 
                loadEcuationOnUI(equation); 
            }
        });
    }
    
    /**
     * Método para suscribirse como oyente a la edición en este panel
     * @param listener Oyente a suscribir
     */
    public void suscribe (EditedListener listener) {
        editedEvt.addEditedListener(listener);
    }
    
    /**
     * Reinicia la ecuación del ViewModel
     */
    public void restart () throws Exception {
        Ecuation ec = model.getEcuation()
                .getClass()
                .getDeclaredConstructor()
                .newInstance();
        
        model.setEcuation(ec);
        
        ProblemScrollPane.setVisible(false);
        ProblemTextArea.setText("");

        VariablesPanel.removeAll();
        
        onCreateVariables(VariablesPanel);
        onCreateFormulas(Formulas);
        onCreateProcedures(Procedure);
        onCreateResult(Result);
    }
    
    /**
     * Recarga la ecuación del ViewModel en la UI
     * @param load 
     */
    private void loadEcuationOnUI (Ecuation load) {        
        if (load.getDescription().isBlank()) {
            ProblemScrollPane.setVisible(false);
        } else {
            ProblemScrollPane.setVisible(true);
            ProblemTextArea.setText(load.getDescription());
        }
        
        model.setEcuation(load);
        
        Variable target = null;
        boolean cancelProcedure = false;
        
        for (Variable variable : load.getVariables()) {
            if (variable.getTarget()) {
                if (target != null)  {
                    cancelProcedure = true;
                    break;
                }
                
                target = variable;
            }
        }
        
        if (target == null) { cancelProcedure = true; }
        
        VariablesPanel.removeAll();
        
        onCreateVariables(VariablesPanel);
        onCreateFormulas(Formulas);
        
        if (!cancelProcedure) {
            double procedureResult = target.calculate();
            target.setValue(procedureResult);
            List<String> latex = target.getProcedureSteps();
            
            editContent(latex);
        }   
    }
    
    private void renderModelOn (JPanel localVariables, Ecuation ec) {
        localVariables.setLayout(new BoxLayout(localVariables, BoxLayout.Y_AXIS));
        localVariables.removeAll();
        
        for (Variable variable : ec.getVariables()) {
            Variable last = ec.getVariables().getLast();
            
            String valueText = (variable.getTarget() || variable.getValue() == null) 
                ? "?" 
                : variable.getValue() + " " + variable.getUnit().getSymbol();

            TeXFormula nameFormula = new TeXFormula(
                "\\text{" + variable.getName() + " } (" + variable.getAbreviation() + ") = " + valueText
            );
            
            TeXIcon icon = nameFormula.createTeXIcon(TeXConstants.STYLE_DISPLAY, 16);
                        
            JLabel label = new JLabel();
            label.setIcon(icon);
            
            localVariables.add(label);
            if (variable != last) localVariables.add(Box.createVerticalStrut(10));
        } 
        
        this.revalidate();
        this.repaint();
    }
    
    /**
     * Obtiene la fórmula contenida en la ecuación para mostrarla en la interfaz gráfica
     * @param panel JPanel donde se va a visualizar
     * @implNote El JPanel que se envie debe estar configurado como un GridBagLayout
     */
    public void onCreateFormulas (JPanel panel) {
        GridBagConstraints gbc = new GridBagConstraints();
        
        panel.removeAll();
        
        TeXFormula formula = new TeXFormula(model.getEcuation().getLatexFormula());
        TeXIcon icon = formula.createTeXIcon(TeXConstants.STYLE_DISPLAY, 24);
        
        JLabel label = new JLabel(icon);
        label.setVerticalAlignment(JLabel.TOP);
                
        gbc.gridx = 0;
        gbc.gridy = 1;
        
        gbc.gridheight = 1;
        gbc.gridwidth = 1;
        
        gbc.weighty = 0.9;
        gbc.weightx = 1;
        
        gbc.fill = GridBagConstraints.BOTH;
        gbc.anchor = GridBagConstraints.NORTH;
        
        gbc.insets = new Insets(15, 0, 0, 0);
        
        panel.add(label, gbc);
    }
    
    /**
     * Obtiene las variables contenidas en la ecuación para mostrarlas en la interfaz gráfica
     * @param panel JPanel donde se va a visualizar
     * @implNote El JPanel que se envie debe estar configurado como un GridBagLayout
     */
    public void onCreateVariables (JPanel panel) {
        JPanel localVariables = new JPanel();
        localVariables.setLayout(new BoxLayout(localVariables, BoxLayout.Y_AXIS));
        renderModelOn(localVariables, model.getEcuation());
        panel.add(localVariables);
    }
    
    /**
     * Crea el contenedor necesario para poder renderizar el contenido LaTeX en la interfaz gráfica
     * @param panel JPanel que va a contener la información renderizada
     * @implNote El parámetro panel debe contener estrictamente un GridBayLayout
     */
    public void onCreateProcedures (JPanel panel) {
        procedures.removeAll();
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 1;
        
        gbc.fill = GridBagConstraints.BOTH;
        gbc.anchor = GridBagConstraints.BASELINE;
        
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        
        gbc.insets = new Insets(10, 20, 0, 0);
        
        panel.add(procedures, gbc);
    }
    
    /**
     * Crea el contenedor necesario para poder renderizar el contenido LaTeX en la interfaz gráfica, pero enseñando únicamente el resultado final
     * @param panel JPanel que va a contener la información renderizada
     * @implNote El parámetro panel debe contener estrictamente un GridBayLayout
     */
    public void onCreateResult (JPanel panel) {
        result.removeAll();
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 1;
        
        gbc.fill = GridBagConstraints.BOTH;
        gbc.anchor = GridBagConstraints.BASELINE;
        
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        
        gbc.insets = new Insets(10, 20, 0, 0);
        
        panel.add(result, gbc);
    }
    
    protected void editContent (List<String> latex) {
        procedures.renderProcedure(latex);
        result.renderProcedure(latex.getLast());
        renderModelOn(VariablesPanel, model.getEcuation());
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        EcuationName = new javax.swing.JLabel();
        EquationDescription = new javax.swing.JPanel();
        ProblemScrollPane = new javax.swing.JScrollPane();
        ProblemTextArea = new javax.swing.JTextArea();
        FlatSVGIcon editProblemIcon = new FlatSVGIcon("icons/edit.svg", 20, 20);
        EditProblemContent = new javax.swing.JButton(editProblemIcon);
        EcuationContent = new javax.swing.JPanel();
        Data = new javax.swing.JPanel();
        DataTitle = new javax.swing.JLabel();
        VariablesPanel = new javax.swing.JPanel();
        FlatSVGIcon editIcon = new FlatSVGIcon("icons/edit.svg", 20, 20);
        EditData = new javax.swing.JButton(editIcon);
        Result = new javax.swing.JPanel();
        Conclusiones = new javax.swing.JLabel();
        Formulas = new javax.swing.JPanel();
        FormulasTitle = new javax.swing.JLabel();
        Procedure = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();

        EcuationName.setFont(new java.awt.Font("Segoe UI Variable", 1, 18)); // NOI18N
        EcuationName.setText(model.getEcuation().getName());

        ProblemScrollPane.setVisible(!ProblemTextArea.getText().isEmpty());
        ProblemScrollPane.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        ProblemScrollPane.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);

        ProblemTextArea.setColumns(20);
        ProblemTextArea.setFont(new java.awt.Font("Segoe UI Semibold", 0, 14)); // NOI18N
        ProblemTextArea.setRows(5);
        ProblemTextArea.setWrapStyleWord(true);
        ProblemTextArea.setEnabled(false);
        ProblemScrollPane.setViewportView(ProblemTextArea);

        EditProblemContent.putClientProperty(FlatClientProperties.BUTTON_TYPE, FlatClientProperties.BUTTON_TYPE_TOOLBAR_BUTTON);
        EditProblemContent.setToolTipText("Edita el contenido del problema...");
        EditProblemContent.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                EditProblemContentMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout EquationDescriptionLayout = new javax.swing.GroupLayout(EquationDescription);
        EquationDescription.setLayout(EquationDescriptionLayout);
        EquationDescriptionLayout.setHorizontalGroup(
            EquationDescriptionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(EquationDescriptionLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(EquationDescriptionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(ProblemScrollPane)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, EquationDescriptionLayout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(EditProblemContent)))
                .addContainerGap())
        );
        EquationDescriptionLayout.setVerticalGroup(
            EquationDescriptionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(EquationDescriptionLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(ProblemScrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, 83, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(EditProblemContent)
                .addContainerGap())
        );

        EcuationContent.setLayout(new java.awt.GridBagLayout());

        Data.setLayout(new java.awt.GridBagLayout());

        DataTitle.setFont(new java.awt.Font("Segoe UI Semibold", 0, 16)); // NOI18N
        DataTitle.setText("Datos");
        DataTitle.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weightx = 0.05;
        gridBagConstraints.weighty = 0.05;
        gridBagConstraints.insets = new java.awt.Insets(0, 5, 5, 0);
        Data.add(DataTitle, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        Data.add(VariablesPanel, gridBagConstraints);

        EditData.putClientProperty(FlatClientProperties.BUTTON_TYPE, FlatClientProperties.BUTTON_TYPE_TOOLBAR_BUTTON);
        EditData.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                EditDataMouseClicked(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 5, 5);
        Data.add(EditData, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 0.05;
        gridBagConstraints.weighty = 0.05;
        EcuationContent.add(Data, gridBagConstraints);
        Data.setBorder(new LinedBorder(Color.BLACK, 2, 6f, 4f, false, true, false, true));

        Result.setLayout(new java.awt.GridBagLayout());

        Conclusiones.setFont(new java.awt.Font("Segoe UI Semibold", 0, 16)); // NOI18N
        Conclusiones.setText("Conclusiones");
        Conclusiones.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        Conclusiones.setVerticalTextPosition(javax.swing.SwingConstants.TOP);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(0, 20, 5, 0);
        Result.add(Conclusiones, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 0.95;
        gridBagConstraints.weighty = 0.05;
        EcuationContent.add(Result, gridBagConstraints);
        Result.setBorder(new LinedBorder(Color.BLACK, 2, 6f, 4f, false, true, false, false));

        Formulas.setLayout(new java.awt.GridBagLayout());

        FormulasTitle.setFont(new java.awt.Font("Segoe UI Semibold", 0, 16)); // NOI18N
        FormulasTitle.setText("Fórmulas");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.BASELINE;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 0.1;
        gridBagConstraints.insets = new java.awt.Insets(10, 5, 0, 0);
        Formulas.add(FormulasTitle, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 0.05;
        gridBagConstraints.weighty = 0.95;
        EcuationContent.add(Formulas, gridBagConstraints);
        Formulas.setBorder(new LinedBorder(Color.BLACK, 2, 6f, 4f, false, false, false, true));

        Procedure.setLayout(new java.awt.GridBagLayout());

        jLabel1.setFont(new java.awt.Font("Segoe UI Semibold", 0, 16)); // NOI18N
        jLabel1.setText("Procedimiento");
        jLabel1.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        jLabel1.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.BASELINE;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(10, 20, 0, 0);
        Procedure.add(jLabel1, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHEAST;
        gridBagConstraints.weightx = 0.95;
        gridBagConstraints.weighty = 0.95;
        EcuationContent.add(Procedure, gridBagConstraints);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(EcuationContent, javax.swing.GroupLayout.DEFAULT_SIZE, 1788, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(EquationDescription, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(EcuationName, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addContainerGap())))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(EcuationName, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(EquationDescription, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(14, 14, 14)
                .addComponent(EcuationContent, javax.swing.GroupLayout.DEFAULT_SIZE, 568, Short.MAX_VALUE)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void EditDataMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_EditDataMouseClicked
        Writer w = new Writer(null, true, model);
        
        w.addCalculateListener((latex) -> {
            editContent(latex);
            
            editedEvt.fire(true);
        });
        
        w.setVisible(true);
    }//GEN-LAST:event_EditDataMouseClicked

    private void EditProblemContentMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_EditProblemContentMouseClicked
        ProblemWriter problemWriter = new ProblemWriter(null, true, model.getEcuation());
        
        problemWriter.subscribe((content) -> {
            this.model.getEcuation().setDescription(content);
            
            if (content.isBlank()) {
                ProblemScrollPane.setVisible(false);
            } else {
                ProblemScrollPane.setVisible(true);
                ProblemTextArea.setText(content);
            }
            
            editedEvt.fire(true);
            
            parent.revalidate();
            parent.repaint();
        });
        
        problemWriter.setVisible(true);
    }//GEN-LAST:event_EditProblemContentMouseClicked

    public EcuationViewModel getModel () { return model; }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel Conclusiones;
    private javax.swing.JPanel Data;
    private javax.swing.JLabel DataTitle;
    private javax.swing.JPanel EcuationContent;
    private javax.swing.JLabel EcuationName;
    private javax.swing.JButton EditData;
    private javax.swing.JButton EditProblemContent;
    private javax.swing.JPanel EquationDescription;
    private javax.swing.JPanel Formulas;
    private javax.swing.JLabel FormulasTitle;
    private javax.swing.JScrollPane ProblemScrollPane;
    private javax.swing.JTextArea ProblemTextArea;
    private javax.swing.JPanel Procedure;
    private javax.swing.JPanel Result;
    private javax.swing.JPanel VariablesPanel;
    private javax.swing.JLabel jLabel1;
    // End of variables declaration//GEN-END:variables
}
