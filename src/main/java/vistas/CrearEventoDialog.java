package main.java.vistas;

import main.java.clases.Evento;
import main.java.util.EventoFileManager;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public class CrearEventoDialog extends BotonesView {
    private JTextField tituloField, descripcionField, ubicacionField;
    private JTextField diaField, mesField, anioField;

    public CrearEventoDialog(JFrame parent) {
        super(parent, "Crear Nuevo Evento", true);
        setSize(600, 300);
        setLocationRelativeTo(parent);

        add(new JLabel("Título:"));
        tituloField = new JTextField();
        add(tituloField);

        add(new JLabel("Descripción:"));
        descripcionField = new JTextField();
        add(descripcionField);

        add(new JLabel("Fecha:"));

        JPanel fechaPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 0));
        diaField = new JTextField(2);
        mesField = new JTextField(2);
        anioField = new JTextField(4);

        fechaPanel.add(new JLabel("Día"));
        fechaPanel.add(diaField);
        fechaPanel.add(new JLabel("Mes"));
        fechaPanel.add(mesField);
        fechaPanel.add(new JLabel("Año"));
        fechaPanel.add(anioField);

        add(fechaPanel);

        add(new JLabel("Ubicación:"));
        ubicacionField = new JTextField();
        add(ubicacionField);

        agregarBotones(this::guardarEvento);
    }

    private void guardarEvento() {
        try {
            String titulo = tituloField.getText();
            String descripcion = descripcionField.getText();
            String ubicacion = ubicacionField.getText();

            int dia = Integer.parseInt(diaField.getText());
            int mes = Integer.parseInt(mesField.getText());
            int anio = Integer.parseInt(anioField.getText());

            LocalDate fecha = LocalDate.of(anio, mes, dia);
            LocalDateTime fechaHora = fecha.atStartOfDay();

            List<Evento> eventos = EventoFileManager.cargarEventos();
            int nuevoId = eventos.stream().mapToInt(Evento::getId).max().orElse(0) + 1;

            Evento nuevo = new Evento(nuevoId, titulo, descripcion, fechaHora, ubicacion);
            eventos.add(nuevo);
            EventoFileManager.guardarEventos(eventos);

            JOptionPane.showMessageDialog(this, "Evento creado con éxito.");
            dispose();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Fecha inválida. Usá números válidos para día, mes y año.");
        }
    }
}
