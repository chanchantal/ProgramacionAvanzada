package parte1;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JTextField;


/**
* La clase Recepción se encargará de recoger a los pacientes registrados por el
* auxiliar 1 al llegar al hospital. 
*/
public class Recepcion {
    private final javax.swing.JTextField colaEsperaTxt;
    private final javax.swing.JTextField pacienteTxt;
    private final javax.swing.JTextField auxiliarTxt;
    private Auxiliar auxiliar;
    private Paciente paciente;
    private final EscrituraTexto et;
    
    private final ArrayList<Paciente> colaRecepcion = new ArrayList<>();
    private final ArrayList<Paciente> paraVacunar = new ArrayList<>();


    private final Lock lock = new ReentrantLock();

    private final Semaphore auxiliarRegistra = new Semaphore(0);
    private final Semaphore pacienteEspera = new Semaphore(0);

    private final Condition esperar = lock.newCondition();
    private final Condition recepcion = lock.newCondition();
    
    /**
    * En el constructor inicializamos los JTextField necesarios para poder poner 
    * en funcionamiento la interfaz, así como la clase de EscrituraTexto, que
    * se encargará de rellenar el log
     */
    public Recepcion(JTextField colaEsperaTxt, JTextField pacienteTxt, JTextField auxiliarTxt, EscrituraTexto et) {
        this.colaEsperaTxt = colaEsperaTxt;
        this.pacienteTxt = pacienteTxt;
        this.auxiliarTxt = auxiliarTxt;
        this.et = et;
    }


    /**
    * auxiliarRegistra se encargará de registrar a los pacientes que lleguen al hospital. 
    * Lo hará 10 veces antes de proceder a tomarse un descanso. 
    */
    public void auxiliarRegistra(Auxiliar a) throws InterruptedException, IOException {
        for (int i = 0; i < 10; i++) {
            auxiliarRegistra.acquire();
            auxiliarTxt.setText(a.getIdentificador());
            et.auxiliarRegistra(a, paciente);
            System.out.println("El auxiliar " + a.getIdentificador() + " ha registrado al paciente " + paciente.getIdentificador());
            Thread.sleep((int) (Math.random() * ((1000 - 500 + 1) + 500)));
            pacienteEspera.release();
        }
        
        auxiliarTxt.setText("");
    }

    /**
    * El método pacienteEspera esperará a que el auxiliar 1 le registre.
    */
    public void pacienteEspera(Paciente p) {
        try {
            auxiliarRegistra.release();
            paciente = p;
            pacienteEspera.acquire();
        } catch (InterruptedException ex) {
            Logger.getLogger(Recepcion.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    /**
    * El método pacienteEntra introduce al paciente en una cola de recepción para
    * posteriormente imprimir esa cola por la pantalla de la interfaz
    */
    public void pacienteEntra(Paciente p) {
        lock.lock();
        try {
            colaRecepcion.add(p);
            imprimirColaEspera();
            
        } finally {
            lock.unlock();
        }
    }

    /**
    * El método salir se encargará de sacar al paciente de la cola de espera de 
    * recepción y añadirlo a una cola que indicará que serán los siguientes
    * en acceder a la sala de vacunación. 
    */
    public void salir(Paciente p) throws InterruptedException {
        lock.lock();
        try {
            colaRecepcion.remove(p);
            recepcion.signalAll();
            imprimirColaEspera();
            pacienteTxt.setText(p.getIdentificador());
            paraVacunar.add(p);
            
        } finally {
            lock.unlock();
        }
    }
    
    /**
    * imprimirColaEspera se encargará de recorrer la cola de recepción para
    * imprimirla por la pantalla de la interfaz.
    */
    private synchronized void imprimirColaEspera(){
        String texto = "";
        if (!colaRecepcion.isEmpty()){
            for (int i = 0; i< colaRecepcion.size(); i++){
                texto += colaRecepcion.get(i).getIdentificador() + " ";
            }
        }
        colaEsperaTxt.setText(texto);
    }

    
    /**
    * Se encarga de devolver el auxiliar
    */
    public Auxiliar getAuxiliar() {
        return auxiliar;
    }

    /**
    * Se encarga de establecer el auxiliar.
    */
    public void setAuxiliar(Auxiliar auxiliar) {
        auxiliarTxt.setText(auxiliar.getIdentificador());
        this.auxiliar = auxiliar;
    }

    public String getColaEspera() {
        return colaEsperaTxt.getText();
    }
    
    public String getPacienteTxt() {
        return pacienteTxt.getText();
    }
    public String getAuxiliarTxt() {
        return auxiliarTxt.getText();
    }
    
    
}

