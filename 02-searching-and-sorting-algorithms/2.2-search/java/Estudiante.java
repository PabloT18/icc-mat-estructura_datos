/**
 * Estudiate
 */

Estudiante e = new Estudiante("c","Juan",10);
e.getCodigo();

public record Estudiante(String codigo,
        String nombre,
        double nota) {

    public Estudiante {
    }

}



Estudiante e = new Estudiante("c","Juan",10);
e.cod;
public class Estudiante {

    public String cod;

    public Estudiante(String cod) {
        this.cod = cod;
    }

}