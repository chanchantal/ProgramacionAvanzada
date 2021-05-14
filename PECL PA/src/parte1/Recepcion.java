package parte1;

import java.util.ArrayList;
import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JTextField;

public class Recepcion {
    private final javax.swing.JTextField colaEsperaTxt;
    private final javax.swing.JTextField pacienteTxt;
    private final javax.swing.JTextField auxiliarTxt;
    private Auxiliar auxiliar;
    private Paciente paciente;
    
    private final ArrayList<Paciente> colaRecepcion = new ArrayList<>();
    private final ArrayList<Paciente> paraVacunar = new ArrayList<>();

    private SalaVacunacion salaVacuna;
    
    private final Lock lock = new ReentrantLock();

    private final Semaphore auxiliarRegistra = new Semaphore(0);
    private final Semaphore pacienteEspera = new Semaphore(0);

    private final Condition esperar = lock.newCondition();
    private final Condition recepcion = lock.newCondition();

    public Recepcion(JTextField colaEsperaTxt, JTextField pacienteTxt, JTextField auxiliarTxt) {
        this.colaEsperaTxt = colaEsperaTxt;
        this.pacienteTxt = pacienteTxt;
        this.auxiliarTxt = auxiliarTxt;
    }



    public void auxiliarRegistra() throws InterruptedException {
        for (int i = 0; i < 10; i++) {
            auxiliarRegistra.acquire();
            Thread.sleep((int) (Math.random() * ((1000 - 500 + 1) + 500)));
        }
    }

    public void pacienteEspera() {
        try {
            auxiliarRegistra.release();
            pacienteEspera.acquire();
        } catch (InterruptedException ex) {
            Logger.getLogger(Recepcion.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void pacienteEntra(Paciente p) {
        lock.lock();
        try {
            colaRecepcion.add(p);
            imprimirColaEspera();
            
        } finally {
            lock.unlock();
        }
    }

    public void salir(Paciente p) throws InterruptedException {
        lock.lock();
        try {
            colaRecepcion.remove(p);
            recepcion.signalAll();
            System.out.println("El paciente se va a la sala de Vacunación");
            paraVacunar.add(p);
        } finally {
            lock.unlock();
        }
    }
    private synchronized void imprimirColaEspera(){
        String texto = "";
        if (colaRecepcion.isEmpty()){
            for (int i = 0; i< colaRecepcion.size(); i++){
                texto += colaRecepcion.get(i).getIdentificador() + "";
            }
        }
        colaEsperaTxt.setText(texto);
    }

    public Auxiliar getAuxiliar() {
        return auxiliar;
    }

    public void setAuxiliar(Auxiliar auxiliar) {
        auxiliarTxt.setText(auxiliar.getIdentificador());
        this.auxiliar = auxiliar;
    }
}
