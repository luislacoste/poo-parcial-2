package main.java.model;


public class Asistente {
    private String nombre;
    private String email;
    private boolean asistio;

    public Asistente(String nombre, String email, boolean asistio) {
        this.nombre = nombre;
        this.email = email;
        this.asistio = asistio;
    }

    public Asistente(String nombre, String email) {
        setNombre(nombre);
        setEmail(email);
        setAsistio(false);
    }

    public String getNombre() {
        return nombre;
    }

    public String getEmail() {
        return email;
    }

    public boolean isAsistio() {
        return asistio;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setAsistio(boolean asistio) {
        this.asistio = asistio;
    }

    public String toJSON() {
        return String.format("{\"nombre\":\"%s\",\"email\":\"%s\",\"asistio\":%s}",
                nombre, email, asistio);
    }

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
