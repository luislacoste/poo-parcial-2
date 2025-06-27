package main.java.controladores;

import main.java.clases.Asistente;
import main.java.clases.Evento;
import main.java.util.EventoFileManager;

import java.util.*;

public class EventoController {
    private List<Evento> eventos;

    public EventoController() {
        this.eventos = EventoFileManager.cargarEventos();
    }


    public void eliminarEventoPorId(int id) {
        List<Evento> eventos = EventoFileManager.cargarEventos();
        eventos.remove(id);
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

    // Edito un evento existente y lo guardo en el txt
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


    // Agrego el asistente a el txt y al evento
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
