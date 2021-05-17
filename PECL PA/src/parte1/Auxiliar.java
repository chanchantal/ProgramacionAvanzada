package parte1;

import java.util.logging.Level;
import java.util.logging.Logger;

/*
-Hay 2 auxiliares. A1 y A2
-A1 comprueba que los pacientes tienen cita (0,5-1 seg)
-Si tienen cita, comprueba el aforo de salas y envía al paciente.
    -Si está lleno, esperan
-Cuando registre a 10 pacientes, descansa (3-5 seg);
-Lleva un registro de vacunación y de errores. 
-A2 mete en cola las dosis de vacuna que prepara (prepara una en 0.5-1 seg)
-Cuando prepara 20 vacunas, descansa (1-4 seg)
 */
public class Auxiliar extends Thread {

    private String identificador;
    private Hospital hospital;
    private boolean cita;
    private Paciente paciente;

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

    @Override
    public void run() {
        try {
            while (true) {
                if ("A1".equals(identificador)) {
                    //Este es el run de recepcion
                  
                    hospital.getRecepcion().auxiliarRegistra(this);
                    
                    hospital.getSalaDescanso().descansoAuxiliar1(this);

                } else {
                    //este es el run de vacunas para A2
                    hospital.getSalaVacunacion().haciendoVacunas(this);
                    hospital.getSalaDescanso().descansoAuxiliar2(this);

                }
            }
        } catch (InterruptedException ex) {
            Logger.getLogger(Auxiliar.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
}

//    public void salir(Paciente p) throws InterruptedException {
//        lock.lock();
//        boolean cita = true;
//        int comprobacion = (int) (Math.random() * (100 - 1 + 1) + 1);
//        if (comprobacion == 1) {
//            cita = false;
//            System.out.println("El paciente no tiene cita y procede a marcharse");
//        } else {
//            try {
//                colaRecepcion.remove(p);
//                recepcion.signalAll();
//                System.out.println("El paciente se va a la sala de Vacunación");
//                paraVacunar.add(p);
//            } finally {
//                lock.unlock();
//            }
//        }
//
//    }
