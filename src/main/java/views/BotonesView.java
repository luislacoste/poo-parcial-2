package main.java.views;

import javax.swing.*;
import java.awt.*;


/**
 * Clase base para los diálogos de la aplicación.
 * Proporciona un diseño común y botones de acción.
 * tambien para poder usar un extend (visto en clases)
 **/
public abstract class BaseDialog extends JDialog {
    protected JButton guardarBtn, cancelarBtn;

    public BaseDialog(JFrame parent, String titulo, boolean modal) {
        super(parent, titulo, modal);
        setLayout(new GridLayout(0, 2, 10, 10)); // se adapta a la cantidad de filas
    }

    protected void agregarBotones(Runnable onGuardar) {
        guardarBtn = new JButton("Guardar");
        cancelarBtn = new JButton("Cancelar");

        guardarBtn.addActionListener(e -> onGuardar.run());
        cancelarBtn.addActionListener(e -> dispose());

        add(guardarBtn);
        add(cancelarBtn);
    }
}
