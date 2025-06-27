package main.java.views;

import main.java.clases.Asistente;

import javax.swing.*;
import java.awt.*;

public class CrearAsistenteDialog extends JDialog {
    private JTextField nombreField, emailField;
    private boolean confirmado = false;

    public CrearAsistenteDialog(JFrame parent) {
        super(parent, "Agregar Asistente", true);
        setSize(350, 180);
        setLocationRelativeTo(parent);
        setLayout(new GridLayout(3, 2, 10, 10));

        add(new JLabel("Nombre:"));
        nombreField = new JTextField();
        add(nombreField);

        add(new JLabel("Email:"));
        emailField = new JTextField();
        add(emailField);

        JButton guardarBtn = new JButton("Guardar");
        guardarBtn.addActionListener(e -> {
            if (nombreField.getText().isEmpty() || emailField.getText().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Completa todos los campos.");
                return;
            }
            confirmado = true;
            dispose();
        });
        add(guardarBtn);

        JButton cancelarBtn = new JButton("Cancelar");
        cancelarBtn.addActionListener(e -> dispose());
        add(cancelarBtn);
    }

    public boolean isConfirmado() {
        return confirmado;
    }

    public Asistente getAsistente() {
        return new Asistente(nombreField.getText(), emailField.getText(), false);
    }
}
