
package parte1;

import java.util.logging.Level;
import java.util.logging.Logger;


/*
*El hilo CreaPaciente tiene como atributos el hospital perteneciente a la clase Hospital y et perteneciente a la clase EscrituraTexto.
*Dicho hilo se encarga de generar los 2000 pacientes.
*/
public class CreaPacientes extends Thread{
    private final Hospital hospital;
    private final EscrituraTexto et;
    
    
    public CreaPacientes(Hospital hospital, EscrituraTexto et) {
        this.hospital = hospital;
        this.et = et;
    }
    /*
    *El metodo run() recorre los 2001 pacientes y compruba que cada uno de ellos tenga cita.
    *Para evitar aglomeracione dicha accion se realizara en un intervalo de tiempo entre 1 y 3 segundos.
    */
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
