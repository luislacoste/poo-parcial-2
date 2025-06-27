package main.java.clases;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Evento {
    private int id;
    private String titulo;
    private String descripcion;
    private LocalDateTime fecha;
    private String ubicacion;
    private List<Asistente> asistentes;

    public Evento(int id, String titulo, String descripcion, LocalDateTime fecha, String ubicacion) {
        this.id = id;
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.fecha = fecha;
        this.ubicacion = ubicacion;
        this.asistentes = new ArrayList<>();
    }

    // Getters
    public int getId() {
        return id;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public LocalDateTime getFecha() {
        return fecha;
    }

    public String getUbicacion() {
        return ubicacion;
    }

    public List<Asistente> getAsistentes() {
        return asistentes;
    }

    // Setters
    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public void setFecha(LocalDateTime fecha) {
        this.fecha = fecha;
    }

    public void setUbicacion(String ubicacion) {
        this.ubicacion = ubicacion;
    }

    public void agregarAsistente(Asistente asistente) {
        asistentes.add(asistente);
    }

    // Transformo el evento a CSV con los asistentes en formato JSON (GPT ayudo aca)
    // lo podria hacer con lista de listas, pero el uso de diccionarios es mas facil de entender aca
    public String toCSV() {
        StringBuilder asistentesJson = new StringBuilder();
        asistentesJson.append("{\"asistentes\":")
                .append(asistentes.size())
                .append(",\"lista\":[");

        for (int i = 0; i < asistentes.size(); i++) {
            asistentesJson.append(asistentes.get(i).toJSON());
            if (i < asistentes.size() - 1) {
                asistentesJson.append(",");
            }
        }

        asistentesJson.append("]}");

        // La fila CSV contiene: id, titulo, descripcion, fecha, ubicacion, asistentes en formato JSON
        return String.join(",",
                String.valueOf(id),
                titulo,
                descripcion,
                fecha.toString(),
                ubicacion,
                asistentesJson.toString()
        );
    }



    // Parseo el CSV a un objeto Evento (GPT ayudo aca, mas que nada en los regex)
    // Manejo de excepciones hecho por mi
    public static Evento fromCSV(String csv) {
        try {
            String[] parts = csv.split(",", 6); // dejamos json completo en part[5]
            if (parts.length < 6) return null;

            int id = Integer.parseInt(parts[0]);
            String titulo = parts[1];
            String descripcion = parts[2];
            LocalDateTime fecha = LocalDateTime.parse(parts[3]);
            String ubicacion = parts[4];
            String asistentesJson = parts[5];

            Evento evento = new Evento(id, titulo, descripcion, fecha, ubicacion);

            // Parse manual del JSON (suficiente para este TP)
            if (asistentesJson.contains("\"lista\":[")) {
                String lista = asistentesJson.split("\"lista\":\\[", 2)[1];
                lista = lista.substring(0, lista.lastIndexOf("]"));

                if (!lista.trim().isEmpty()) {
                    String[] jsonAsistentes = lista.split("(?<=}),");
                    for (String aJson : jsonAsistentes) {
                        Asistente a = Asistente.fromJSON(aJson.trim());
                        if (a != null) evento.agregarAsistente(a);
                    }
                }
            }

            return evento;
        } catch (Exception e) {
            System.err.println("Error al parsear evento: " + csv);
            return null;
        }
    }

}
