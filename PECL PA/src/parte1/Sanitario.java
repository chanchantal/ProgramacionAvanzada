package parte1;


import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.logging.Level;
import java.util.logging.Logger;

/*
-Hay 10 sanitarios
-Tienen un id tipo SXX
-Llegan al hospital y se cambian (1-3 segundos)
-Acuden al puesto de vacunación
-Esperan a que los auxiliares preparen la dosis
-Inyectan la dosis (3-5 seg)
-Cuando vacunen a 15 pacientes, descansan (5-8 seg)
-Si los pacientes tienen reacción, el primer sanitario que acabe el descanso va
 */
public class Sanitario extends Thread {

    private String identificador;
    private final Lock lock = new ReentrantLock();
    private Hospital hospital;

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


    @Override
    public void run() {
        try {
            hospital.getSalaDescanso().vestidorSanitario(this);
            while (true) {

                if (hospital.getSalaObservacion().isReaccion() == true) {
                    hospital.getSalaObservacion().sanitarioObserva(this);
                }
                hospital.getSalaVacunacion().sanitarioVacuna(this);
                hospital.getSalaDescanso().descansoSanitario(this);
            }
        } catch (InterruptedException | BrokenBarrierException ex) {
            Logger.getLogger(Sanitario.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
