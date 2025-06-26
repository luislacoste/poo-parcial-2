package main.java.views;


import main.java.controller.EventoController;
import main.java.model.Asistente;
import main.java.model.Evento;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class VerAsistentesDialog extends JDialog {
    private JTable tabla;
    private DefaultTableModel modelo;
    private Evento evento;
    private EventoController controller;

    public VerAsistentesDialog(JFrame parent, Evento evento, EventoController controller) {
        super(parent, "Asistentes del Evento: " + evento.getTitulo(), true);
        this.evento = evento;
        this.controller = controller;

        setSize(500, 300);
        setLocationRelativeTo(parent);

        modelo = new DefaultTableModel(new String[]{"Nombre", "Email", "Asistió"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 2; // solo checkbox editable
            }

            @Override
            public Class<?> getColumnClass(int columnIndex) {
                return (columnIndex == 2) ? Boolean.class : String.class;
            }
        };

        tabla = new JTable(modelo);
        cargarAsistentes();

        JButton guardarBtn = new JButton("Guardar");
        guardarBtn.addActionListener(e -> {
            for (int i = 0; i < modelo.getRowCount(); i++) {
                String nombre = (String) modelo.getValueAt(i, 0);
                String email = (String) modelo.getValueAt(i, 1);
                boolean asistio = (Boolean) modelo.getValueAt(i, 2);

                evento.getAsistentes().get(i).setAsistio(asistio); // actualiza el objeto
            }

            controller.actualizarEvento(evento); // guarda
            JOptionPane.showMessageDialog(this, "Asistentes actualizados.");
            dispose();
        });

        setLayout(new BorderLayout());
        add(new JScrollPane(tabla), BorderLayout.CENTER);
        add(guardarBtn, BorderLayout.SOUTH);
    }

    private void cargarAsistentes() {
        for (Asistente a : evento.getAsistentes()) {
            modelo.addRow(new Object[]{a.getNombre(), a.getEmail(), a.isAsistio()});
        }
    }
}
