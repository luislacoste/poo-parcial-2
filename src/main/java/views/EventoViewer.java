package main.java.views;


import main.java.controller.EventoController;
import main.java.model.Asistente;
import main.java.model.Evento;
import main.java.util.EventoFileManager;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class EventoViewer extends JFrame {
    private JTable tablaEventos;
    private DefaultTableModel modeloTabla;
    private EventoController controller;

    public EventoViewer() {
        controller = new EventoController();

        setTitle("Listado de Eventos");
        setSize(800, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        initMenu();
        initTabla();
        cargarEventos();
    }

    private void initTabla() {
        modeloTabla = new DefaultTableModel(
                new String[]{"ID", "Título", "Descripción", "Fecha", "Ubicación", "Asistieron"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        tablaEventos = new JTable(modeloTabla);
        tablaEventos.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent e) {
                if (e.getClickCount() == 2 && tablaEventos.getSelectedRow() != -1) {
                    int row = tablaEventos.getSelectedRow();
                    int id = (int) modeloTabla.getValueAt(row, 0);

                    Evento evento = controller.obtenerEventoPorId(id);
                    if (evento != null) {
                        EditarEventoDialog dialog = new EditarEventoDialog(EventoViewer.this, evento);
                        dialog.setVisible(true);
                        cargarEventos();
                    } else {
                        JOptionPane.showMessageDialog(EventoViewer.this, "No se pudo cargar el evento.");
                    }
                }
            }
        });

        JScrollPane scrollPane = new JScrollPane(tablaEventos);


        // Botón agregar asistente
        JButton botonAgregarAsistente = new JButton("Agregar asistente");
        botonAgregarAsistente.addActionListener(e -> {
            int fila = tablaEventos.getSelectedRow();
            if (fila == -1) {
                JOptionPane.showMessageDialog(this, "Selecciona un evento primero.");
                return;
            }

            int idEvento = (int) modeloTabla.getValueAt(fila, 0);
            CrearAsistenteDialog dialog = new CrearAsistenteDialog(this);
            dialog.setVisible(true);

            if (dialog.isConfirmado()) {
                Asistente nuevo = dialog.getAsistente();
                controller.agregarAsistente(idEvento, nuevo);
                JOptionPane.showMessageDialog(this, "Asistente agregado correctamente.");
                cargarEventos();
            }
        });

        // ✏️ Botón editar asistentes
        JButton botonEditarAsistentes = new JButton("✏️ Ver/Editar asistentes");
        botonEditarAsistentes.addActionListener(e -> {
            int fila = tablaEventos.getSelectedRow();
            if (fila == -1) {
                JOptionPane.showMessageDialog(this, "Selecciona un evento primero.");
                return;
            }

            int idEvento = (int) modeloTabla.getValueAt(fila, 0);
            Evento evento = controller.obtenerEventoPorId(idEvento);
            if (evento != null) {
                VerAsistentesDialog dialog = new VerAsistentesDialog(this, evento, controller);
                dialog.setVisible(true);
                cargarEventos();
            }
        });

        // Panel inferior con los botones
        JPanel panelInferior = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        panelInferior.add(botonAgregarAsistente);
        panelInferior.add(botonEditarAsistentes);


        add(scrollPane, BorderLayout.CENTER);
        add(panelInferior, BorderLayout.SOUTH);
    }


    private void initMenu() {
        JMenuBar menuBar = new JMenuBar();
        JMenu menu = new JMenu("Menú");


        JMenuItem item2 = new JMenuItem("Crear evento");
        JMenuItem item4 = new JMenuItem("Eliminar evento");
        JMenuItem item0 = new JMenuItem("Salir");


        item2.addActionListener(e -> {
            CrearEventoDialog dialog = new CrearEventoDialog(this);
            dialog.setVisible(true);
            cargarEventos();
        });
        item4.addActionListener(e -> {
            int fila = tablaEventos.getSelectedRow();
            if (fila == -1) {
                JOptionPane.showMessageDialog(this, "Selecciona un evento primero.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            int id = (int) modeloTabla.getValueAt(fila, 0);
            Evento evento = controller.obtenerEventoPorId(id);

            if (evento == null) {
                JOptionPane.showMessageDialog(this, "Evento no encontrado.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            int confirm = JOptionPane.showConfirmDialog(this,
                    "¿Está seguro que desea eliminar el evento \"" + evento.getTitulo() + "\"?",
                    "Confirmar eliminación",
                    JOptionPane.YES_NO_OPTION);

            if (confirm == JOptionPane.YES_OPTION) {
                controller.eliminarEventoPorId(id);
                JOptionPane.showMessageDialog(this, "Evento eliminado con éxito.");
                cargarEventos();
            }
        });

        item0.addActionListener(e -> {
            int confirmar = JOptionPane.showConfirmDialog(
                    this,
                    "Desea salir?",
                    "Confirmar",
                    JOptionPane.YES_NO_OPTION
            );
            if (confirmar == JOptionPane.YES_OPTION) {
                dispose();
            }
        });


        menu.add(item2);
        menu.add(item4);
        menu.addSeparator();
        menu.add(item0);

        menuBar.add(menu);
        setJMenuBar(menuBar);
    }

    private void cargarEventos() {
        modeloTabla.setRowCount(0); // limpiar tabla
        List<Evento> eventos = EventoFileManager.cargarEventos();
        for (Evento e : eventos) {
            int total = e.getAsistentes().size();
            long asistieron = e.getAsistentes().stream().filter(Asistente::isAsistio).count();
            String resumen = asistieron + "/" + total;

            modeloTabla.addRow(new Object[]{
                    e.getId(),
                    e.getTitulo(),
                    e.getDescripcion(),
                    e.getFecha().toString(),
                    e.getUbicacion(),
                    resumen // esta es la columna Asistieron
            });
        }
    }
}
