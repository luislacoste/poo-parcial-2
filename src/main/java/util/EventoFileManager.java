package main.java.util;


import java.io.*;
import java.nio.file.*;
import java.util.*;

import main.java.model.Evento;

public class EventoFileManager {
    private static final String ARCHIVO = "eventos.txt";

    public static void guardarEventos(List<Evento> eventos) {
        try (BufferedWriter writer = Files.newBufferedWriter(Paths.get(ARCHIVO))) {
            for (Evento e : eventos) {
                writer.write(e.toCSV());
                writer.newLine();
            }
        } catch (IOException e) {
            System.err.println("Error al guardar eventos: " + e.getMessage());
        }
    }

    public static List<Evento> cargarEventos() {
        List<Evento> eventos = new ArrayList<>();
        Path path = Paths.get(ARCHIVO);
        if (!Files.exists(path)) return eventos;

        try (BufferedReader reader = Files.newBufferedReader(path)) {
            String linea;
            while ((linea = reader.readLine()) != null) {
                Evento evento = Evento.fromCSV(linea);
                if (evento != null) {
                    eventos.add(evento);
                } else {
                    System.err.println("Línea inválida: " + linea);
                }
            }
        } catch (IOException e) {
            System.err.println("Error al leer eventos: " + e.getMessage());
        }

        // 🔽 Ordenar por fecha descendente (más nuevo primero)
        eventos.sort((e1, e2) -> e2.getFecha().compareTo(e1.getFecha()));

        return eventos;
    }

}

