package parte1;


import java.util.concurrent.BrokenBarrierException;
import java.util.logging.Level;
import java.util.logging.Logger;



/**
*La clase Paciente es un hilo al que le pasamos como atributos la clase EscrituraTexto et, la clase Hospital, un identificador,
* si tiene cita y el numero de puestos.
*/
public class Paciente extends Thread {
    private final EscrituraTexto et;
    private final Hospital hospital;
    private String identificador;
    private boolean cita;
    private int puesto;
    /**
    *En el constructor vamos a crear el identificador del paciente.
     * @param id
     * @param cita
     * @param hospital
     * @param et
    */
    public Paciente(int id, boolean cita, Hospital hospital, EscrituraTexto et) {
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
        this.et = et;
    }
    
    /**
     * Creamos los métodos setter y getter para poder acceder al identificador¡
     * @return 
     */
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

    /**
    *El metodo run() nos muestra las distintas acciones que pueden realizar los pacientes.
    *Los pacientes llegaran al hospital y se meteran en la cola de recepcion esperando su turno. Los auxiliares comprobaran si 
    *tienen cita. Si tienen cita el auxiliar les derivara a un puesto de vacunacion , en donde los sanitarios les pondra la vacuna
    *y posteriormente seras enviados a la sala de observacion .Deberan permanecer durante 10 segundos para observar si
    *hay reaccion a la vacuna.Solo el 5% tiene reaccion y es atendido por un sanitario.
    *Si el paciente no tiene cita se ira, en este caso es el 1%.
    */
    @Override
    public void run() {
        try {
            
            hospital.getRecepcion().pacienteEntra(this);
            hospital.getRecepcion().pacienteEspera(this);
            if (cita == true) {
                hospital.getRecepcion().salir(this);
            
                hospital.getSalaVacunacion().entrar(this);
                hospital.getSalaVacunacion().puestoPaciente(this);
                hospital.getSalaVacunacion().salir(this);

                hospital.getSalaObservacion().entrar(this);
                hospital.getSalaObservacion().puesto(this);
                hospital.getSalaObservacion().salir(this);
            }else{
                System.out.println("El paciente " + identificador + " ha acudido sin cita");
                et.pacienteSinCita(this);
                hospital.getRecepcion().salir(this);
                
                
            }
        } catch (InterruptedException | BrokenBarrierException ex) {
            Logger.getLogger(Paciente.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
}
