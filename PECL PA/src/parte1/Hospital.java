
package parte1;

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

    public Recepcion getRecepcion() {
        return recepcion;
    }

    public void setRecepcion(Recepcion recepcion) {
        this.recepcion = recepcion;
    }

    public SalaDescanso getSalaDescanso() {
        return salaDescanso;
    }

    public void setSalaDescanso(SalaDescanso salaDescanso) {
        this.salaDescanso = salaDescanso;
    }

    public SalaObservacion getSalaObservacion() {
        return salaObservacion;
    }

    public void setSalaObservacion(SalaObservacion salaObservacion) {
        this.salaObservacion = salaObservacion;
    }

    public SalaVacunacion getSalaVacunacion() {
        return salaVacunacion;
    }

    public void setSalaVacunacion(SalaVacunacion salaVacunacion) {
        this.salaVacunacion = salaVacunacion;
    }

  
    
}
