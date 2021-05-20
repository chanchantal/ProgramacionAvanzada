
package parte1;

import java.util.logging.Level;
import java.util.logging.Logger;


public class CreaPacientes extends Thread{
    private final Hospital hospital;
    private final EscrituraTexto et;
    
    
    public CreaPacientes(Hospital hospital, EscrituraTexto et) {
        this.hospital = hospital;
        this.et = et;
    }
    
    @Override
    public void run(){
        for (int i = 1; i < 2001 ; i++){
            try {
                boolean cita = true;
                int comprobacion = (int) (Math.random() * (100 - 1 + 1) + 1);
              
                if (comprobacion == 1) {
                    cita = false;
                }
                Paciente p = new Paciente(i, cita, hospital, et);
                p.start();
                Thread.sleep((int) (Math.random() * ((3000 - 1000 + 1) + 1000)));
            } catch (InterruptedException ex) {
                Logger.getLogger(CreaPacientes.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
}
