package parte1;

/**
 * La clase Hospital esta compuesta por la clase Recepcion, salaDescanso,
 * salaObservacion y salaVacunacion. Usamos esta clase para agrupar todas las
 * salas y poder tener un mejor manejo de las mismas
 */
public class Hospital {

    private Recepcion recepcion;
    private SalaDescanso salaDescanso;
    private SalaObservacion salaObservacion;
    private SalaVacunacion salaVacunacion;

    public Hospital(Recepcion recepcion, SalaDescanso salaDescanso, SalaObservacion salaObservacion, SalaVacunacion salaVacunacion) {

        this.recepcion = recepcion;
        this.salaDescanso = salaDescanso;
        this.salaObservacion = salaObservacion;
        this.salaVacunacion = salaVacunacion;
    }

    /**
     * Creamos los getter y setter para acceder a la recepción
     *
     * @return
     */
    public Recepcion getRecepcion() {
        return recepcion;
    }

    public void setRecepcion(Recepcion recepcion) {
        this.recepcion = recepcion;
    }

    /**
     * Creamos los getter y setter para acceder a la sala de descanso
     *
     * @return
     */
    public SalaDescanso getSalaDescanso() {
        return salaDescanso;
    }

    public void setSalaDescanso(SalaDescanso salaDescanso) {
        this.salaDescanso = salaDescanso;
    }

    /**
     * Creamos los getter y setter para acceder a la sala de observación
     *
     * @return
     */
    public SalaObservacion getSalaObservacion() {
        return salaObservacion;
    }

    public void setSalaObservacion(SalaObservacion salaObservacion) {
        this.salaObservacion = salaObservacion;
    }

    /**
     * Creamos los getter y setter para acceder a la sala de vacunación
     *
     * @return
     */
    public SalaVacunacion getSalaVacunacion() {
        return salaVacunacion;
    }

    public void setSalaVacunacion(SalaVacunacion salaVacunacion) {
        this.salaVacunacion = salaVacunacion;
    }

}
