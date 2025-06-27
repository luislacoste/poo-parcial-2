package main.java.clases;


public class Asistente {
    private String nombre;
    private String email;
    private boolean asistio;

    public Asistente(String nombre, String email, boolean asistio) {
        this.nombre = nombre;
        this.email = email;
        this.asistio = asistio;
    }

    // Getters
    public String getNombre() {
        return nombre;
    }

    public String getEmail() {
        return email;
    }

    // Setters
    public void setAsistio(boolean asistio) {
        this.asistio = asistio;
    }

    public boolean isAsistio() {
        return asistio;
    }









    // Paso el asistente a JSON (gpt para el regex)
    public String toJSON() {
        return String.format("{\"nombre\":\"%s\",\"email\":\"%s\",\"asistio\":%s}",
                nombre, email, asistio);
    }

    // Leo el txt y paso el json a la clase asistente (incializandola)
    public static Asistente fromJSON(String json) {
        try {
            String nombre = json.replaceAll(".*\"nombre\":\"([^\"]+)\".*", "$1");
            String email = json.replaceAll(".*\"email\":\"([^\"]+)\".*", "$1");
            boolean asistio = json.contains("\"asistio\":true");
            return new Asistente(nombre, email, asistio);
        } catch (Exception e) {
            return null;
        }
    }
}
