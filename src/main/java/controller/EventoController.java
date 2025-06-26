package main.java.controller;

import main.java.model.Asistente;
import main.java.model.Evento;
import main.java.util.EventoFileManager;

import java.time.LocalDateTime;
import java.util.*;

public class EventoController {
    private List<Evento> eventos;
    private Scanner scanner;

    public EventoController() {
        this.eventos = EventoFileManager.cargarEventos();
        this.scanner = new Scanner(System.in);
    }


    public void eliminarEventoPorId(int id) {
        List<Evento> eventos = EventoFileManager.cargarEventos();
        eventos.removeIf(e -> e.getId() == id);
        EventoFileManager.guardarEventos(eventos);
    }


    public Evento obtenerEventoPorId(int id) {
        List<Evento> eventos = EventoFileManager.cargarEventos();
        for (Evento e : eventos) {
            if (e.getId() == id) {
                return e;
            }
        }
        return null;
    }

    public void actualizarEvento(Evento eventoActualizado) {
        List<Evento> eventos = EventoFileManager.cargarEventos();
        for (int i = 0; i < eventos.size(); i++) {
            if (eventos.get(i).getId() == eventoActualizado.getId()) {
                eventos.set(i, eventoActualizado);
                break;
            }
        }
        EventoFileManager.guardarEventos(eventos);
    }


    public void agregarAsistente(int eventoId, Asistente asistente) {
        List<Evento> eventos = EventoFileManager.cargarEventos();
        for (Evento evento : eventos) {
            if (evento.getId() == eventoId) {
                evento.agregarAsistente(asistente);
                EventoFileManager.guardarEventos(eventos);
                return;
            }
        }
        System.out.println("Evento no encontrado.");
    }
}
