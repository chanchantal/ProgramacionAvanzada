package parte1;


import java.util.concurrent.BrokenBarrierException;
import java.util.logging.Level;
import java.util.logging.Logger;

/*
-Hay 2000 pacientes. 
-Llegan al hospital y se meten en la cola de recepción
-Los auxiliares comprueban su cita
    -Si no están citados, se van (el 1%)
-El auxiliar les deriva a un puesto de vacunación
-Los sanitarios les ponen la vacuna
-Acceden a una sala de observación (10 seg)
    -Si no hay reacción, se van
    -Si hay reacción, les atiende un sanitario (el 5%)
 */
public class Paciente extends Thread {

    private final Hospital hospital;
    private String identificador;
    private boolean cita;
    private int puesto;

    public Paciente(int id, boolean cita, Hospital hospital) {
        if (id < 10) {
            identificador = "P000" + id;
        } else if (id < 100) {
            identificador = "P00" + id;
        } else if (id < 1000) {
            identificador = "P0" + id;
        } else {
            identificador = "P" + id;
        }
        this.cita = cita;
        this.hospital = hospital;
    }

    public String getIdentificador() {
        return identificador;
    }

    public void setId(String id) {
        this.identificador = id;
    }

    public boolean isCita() {
        return cita;
    }

    public void setCita(boolean cita) {
        this.cita = cita;
    }

    public int getPuesto() {
        return puesto;
    }

    public void setPuesto(int puesto) {
        this.puesto = puesto;
    }
    

    @Override
    public void run() {
        try {
            
            hospital.getRecepcion().pacienteEntra(this);
            hospital.getRecepcion().pacienteEspera();
            if (cita == true) {
                hospital.getRecepcion().salir(this);
            
                hospital.getSalaVacunacion().entrar(this);
                hospital.getSalaVacunacion().puestoPaciente(this);
                hospital.getSalaVacunacion().salir(this);

                hospital.getSalaObservacion().entrar(this);
                hospital.getSalaObservacion().estaReposando(this);
                hospital.getSalaObservacion().salir(this);
            }else{
                hospital.getRecepcion().salir(this);
            }
        } catch (InterruptedException | BrokenBarrierException ex) {
            Logger.getLogger(Paciente.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
}
