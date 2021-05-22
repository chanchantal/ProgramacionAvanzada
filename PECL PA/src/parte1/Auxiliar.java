package parte1;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;



/*
*La clase Auxiliar es un hilo al que le pasamos el identificador y la clase Hospital.
*En dicha clase podemos observar el funcionamiento de los auxiliares.
*/
public class Auxiliar extends Thread {

    private String identificador;
    private final Hospital hospital;


    public Auxiliar(int id, Hospital hospital) {
        identificador = "A" + id;
        this.hospital = hospital;
    }

    public String getIdentificador() {
        return identificador;
    }

    public void setIdentificador(String identificador) {
        this.identificador = identificador;
    }
    /*
    *El metodo run se encargara de comprobar que funciones hacen cada uno de los auxiliares existentes que diferenciamos segun su identificador.
    *Si el auxiliar tiene identificador A1 entonces dicho auxiliar se encuentra en la recepcion, en donde comprueba que
    *los pacientes tienen cita (0,5-1 seg).En caso afirmativo, comprueba el aforo de salas y envía al paciente a la sala correpondiente.
    *Tambien al acabar de registrar a 10 pacientes, descansa (3-5 seg).
    *Sino, si el auxiliar tiene identificador A2 mete en cola las dosis de vacuna que prepara (prepara una en 0.5-1 seg).
    *Posteriormente al acabar de preparar 20 vacunas, descansa (1-4 seg).
    */
    @Override
    public void run() {
        try {
            while (true) {
                if ("A1".equals(identificador)) {
                    //Este es el run de recepcion
                    hospital.getRecepcion().auxiliarRegistra(this);
                    hospital.getSalaDescanso().descansoAuxiliar1(this);

                } else {
                    //Este es el run de vacunas para A2
                    hospital.getSalaVacunacion().haciendoVacunas(this);
                    hospital.getSalaDescanso().descansoAuxiliar2(this);

                }
            }
        } catch (InterruptedException | IOException ex) {
            Logger.getLogger(Auxiliar.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
}


