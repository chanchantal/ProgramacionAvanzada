package parte1;


import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.logging.Level;
import java.util.logging.Logger;

/*
*El hilo Sanitario tiene como atributos un identificador , la clase hospital y un puesto.
*/
public class Sanitario extends Thread {

    private String identificador;
    private final Lock lock = new ReentrantLock();
    private Hospital hospital;
    private int puesto;
    /*
    *Dentro del constructor se creara el identificador del sanitario.
    */
    public Sanitario(int id, Hospital hospital) {
        if(id<10){
           identificador = "S0" + id; 
        }else{
           identificador = "S" + id;
        }
        this.hospital = hospital;
        
    }

    public String getIdentificador() {
        return identificador;
    }

    public void setIdentificador(String identificador) {
        this.identificador = identificador;
    }

    public int getPuesto() {
        return puesto;
    }

    public void setPuesto(int puesto) {
        this.puesto = puesto;
    }
   
    /*
    *El metodo run() se encarga del funcionamiento de los sanitarios.
    *Los sanitarios acuden al hospital y se cambian (1-3 segundos).Posteriormente acuden al puesto de vacunacion, en dicho puesto
    *esperaran a que el auxiliar acabe de preparar la dosis. Inyectan la dosis en un intervalo de tiempo entre 3 y 5 segundos..
    *Al acabar de vacunar a 15 pacientes se toman un descanso de 5 a 8 segundos.
    *Si un paciente tiene reaccion, el primer sanitario que acabe el descanso lo atendera. 
    */

    @Override
    public void run() {
        try {
            hospital.getSalaDescanso().vestidorSanitario(this);
            
            while (true) {

                if (hospital.getSalaObservacion().isReaccion() == true) {
                    hospital.getSalaObservacion().sanitarioObserva(this);
                }
                for (int i = 0; i < 15; i++) {
                hospital.getSalaVacunacion().sanitarioVacuna(this);
                hospital.getSalaVacunacion().puestoSanitario(this);
                hospital.getSalaVacunacion().salirSan(this);
                }
                hospital.getSalaDescanso().descansoSanitario(this);
            }
        } catch (InterruptedException | BrokenBarrierException ex) {
            Logger.getLogger(Sanitario.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
