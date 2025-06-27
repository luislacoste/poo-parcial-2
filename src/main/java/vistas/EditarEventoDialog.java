package main.java.views;

import main.java.clases.Evento;
import main.java.util.EventoFileManager;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.util.List;

public class EditarEventoDialog extends BotonesView {
    private JTextField tituloField, descripcionField, ubicacionField;
    private JTextField diaField, mesField, anioField;
    private Evento evento;

    public EditarEventoDialog(JFrame parent, Evento evento) {
        super(parent, "Editar Evento", true);
        this.evento = evento;

        setSize(600, 300);
        setLocationRelativeTo(parent);

        add(new JLabel("Título:"));
        tituloField = new JTextField(evento.getTitulo());
        add(tituloField);

        add(new JLabel("Descripción:"));
        descripcionField = new JTextField(evento.getDescripcion());
        add(descripcionField);

        add(new JLabel("Fecha:"));

        JPanel fechaPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 0));
        diaField = new JTextField(2);
        mesField = new JTextField(2);
        anioField = new JTextField(4);

        LocalDate fecha = evento.getFecha().toLocalDate();
        diaField.setText(String.valueOf(fecha.getDayOfMonth()));
        mesField.setText(String.valueOf(fecha.getMonthValue()));
        anioField.setText(String.valueOf(fecha.getYear()));

        fechaPanel.add(new JLabel("Día"));
        fechaPanel.add(diaField);
        fechaPanel.add(new JLabel("Mes"));
        fechaPanel.add(mesField);
        fechaPanel.add(new JLabel("Año"));
        fechaPanel.add(anioField);
        add(fechaPanel);

        add(new JLabel("Ubicación:"));
        ubicacionField = new JTextField(evento.getUbicacion());
        add(ubicacionField);

        agregarBotones(this::guardarCambios);

        JButton eliminarBtn = new JButton("Eliminar");
        eliminarBtn.addActionListener(e -> eliminarEvento());
        add(eliminarBtn);
    }

    private void guardarCambios() {
        try {
            evento.setTitulo(tituloField.getText());
            evento.setDescripcion(descripcionField.getText());
            evento.setUbicacion(ubicacionField.getText());

            int dia = Integer.parseInt(diaField.getText());
            int mes = Integer.parseInt(mesField.getText());
            int anio = Integer.parseInt(anioField.getText());

            LocalDate fecha = LocalDate.of(anio, mes, dia);
            evento.setFecha(fecha.atStartOfDay());

            List<Evento> eventos = EventoFileManager.cargarEventos();
            for (int i = 0; i < eventos.size(); i++) {
                if (eventos.get(i).getId() == evento.getId()) {
                    eventos.set(i, evento);
                    break;
                }
            }

            EventoFileManager.guardarEventos(eventos);
            JOptionPane.showMessageDialog(this, "Evento actualizado.");
            dispose();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error: datos inválidos.");
        }
    }

    private void eliminarEvento() {
        int confirmar = JOptionPane.showConfirmDialog(
                this,
                "¿Está seguro que desea eliminar el evento \"" + evento.getTitulo() + "\"?",
                "Confirmar eliminación",
                JOptionPane.YES_NO_OPTION
        );

        if (confirmar == JOptionPane.YES_OPTION) {
            List<Evento> eventos = EventoFileManager.cargarEventos();
            eventos.remove(evento);
            EventoFileManager.guardarEventos(eventos);
            JOptionPane.showMessageDialog(this, "Evento eliminado.");
            dispose();
        }
    }
}
