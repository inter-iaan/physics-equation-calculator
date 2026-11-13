package com.ucanesdetijuana.calculadorafisica.forms;

import java.awt.*;
import javax.swing.border.AbstractBorder;

/**
 *
 * @author José Alfredo Interián Polanco
 * Permite crear un borde de líneas en cualquiera de los bordes de un JPanel
 */
public class LinedBorder extends AbstractBorder {
    private final Color color;
    private final int grosor;
    private final float length;
    private final float between;
    private final boolean up, down, left, right;

    public LinedBorder(Color color, int grosor, float length, float between, boolean up, boolean down, boolean left, boolean right) {
        this.color = color;
        this.grosor = grosor;
        this.length = length;
        this.between = between;
        this.up = up;
        this.down = down;
        this.left = left;
        this.right = right;
    }

    @Override
    public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setColor(color);
        
        float[] patron = {length, between};
            g2.setStroke(new BasicStroke(
                grosor,
                BasicStroke.CAP_BUTT,
                BasicStroke.JOIN_MITER,
                10f,
                patron,
                0f
        ));
        int ajuste = grosor / 2;
        int x1 = x + ajuste;
        int y1 = y + ajuste;
        int x2 = x + width - 1 - ajuste;
        int y2 = y + height - 1 - ajuste;

        if (up) g2.drawLine(x1, y1, x2, y1);
        if (down)  g2.drawLine(x1, y2, x2, y2);
        if (left)    g2.drawLine(x1, y1, x1, y2);
        if (right)    g2.drawLine(x2, y1, x2, y2);

        g2.dispose();
    }

    @Override
    public Insets getBorderInsets(Component c) {
        return new Insets(grosor, grosor, grosor, grosor);
    }
}
