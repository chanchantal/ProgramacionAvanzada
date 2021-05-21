package parte1;

import java.util.ArrayList;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import javax.swing.JTextField;


    /**
    * La clase SalaDescanso se encargará de manejar los descansos de los sanitarios
    * y de los auxiliares, así como el paso por el vestidor de los sanitarios
    * antes de entrar a trabajar 
    */

public class SalaDescanso {

    private final EscrituraTexto et;

    private final Lock lock = new ReentrantLock();
    private final Lock lockVestidor = new ReentrantLock();

    private final Condition esperar = lock.newCondition();
    private final Condition vacuna = lock.newCondition();

    private final ArrayList<Sanitario> vacunar;
    private final ArrayList<Auxiliar> descansoA = new ArrayList<>();
    private final ArrayList<Sanitario> descansoS = new ArrayList<>();

    private final javax.swing.JTextField colaDescansoTxtSan;
    private final javax.swing.JTextField colaDescansoTxtAux;

    /**
    * En el constructor inicializamos los JTextField necesarios para poder poner 
    * en funcionamiento la interfaz, así como la clase de EscrituraTexto, que
    * se encargará de rellenar el log 
     */
    public SalaDescanso(JTextField colaDescansoTxtSan, JTextField colaDescansoTxtAux, EscrituraTexto et) {
        this.colaDescansoTxtSan = colaDescansoTxtSan;
        this.colaDescansoTxtAux = colaDescansoTxtAux;
        this.et=et;
        vacunar = new ArrayList<>(10);
    }

    
    /**
    * El método vestidorSanitario hará que el Sanitario vaya a la sala de descanso
    * al entrar a trabajar. Se añade a una cola de descanso de sanitarios, imprimirá
    * dicha cola por la interfaz y al salir, la imprimirá de nuevo para actualizarla
    */
    public void vestidorSanitario(Sanitario s) throws InterruptedException {
        lockVestidor.lock();
        try {
            descansoS.add(s);
            imprimirColaDescansoSanitario();
            Thread.sleep((int) (Math.random() * ((3000 - 1000 + 1) + 3000)));
            descansoS.remove(s);
            imprimirColaDescansoSanitario();
        } finally {
            lockVestidor.unlock();
        }
    }
    
    /**
    * El método descansoSanitario se encargará de manejar los descansos de los sanitarios
    * cada vez que administren las vacunas dichas en el enunciado. Añade de nuevo los
    * sanitarios a la cola de descansoS, hacen su descanso, y salen. 
    */
    public boolean descansoSanitario(Sanitario s) throws InterruptedException {
        lock.lock();
        try {
            et.inicioDescansoS(s);
            descansoS.add(s);

            imprimirColaDescansoSanitario();
            Thread.sleep((int) (Math.random() * ((8000 - 5000 + 1) + 5000)));
            descansoS.remove(s);
            imprimirColaDescansoSanitario();
            et.finDescansoS(s);

        } finally {
            lock.unlock();
        }
        return true;
    }

    /**
    * El método descansoAuxiliar1 se encargará de manejar el descanso del auxiliar 1
    * cada vez que registre a los pacientes dichos en el enucniado. Añade al auxiliar 1
    * a su cola de descanso, hacen su descanso, y salen.
    */
    public void descansoAuxiliar1(Auxiliar a) throws InterruptedException {
        lock.lock();
        try {
            descansoA.add(a);
            et.inicioDescansoA(a);
            imprimirColaDescansoAuxiliar();
            Thread.sleep((int) (Math.random() * ((5000 - 3000 + 1) + 3000))); 
            descansoA.remove(a);
            imprimirColaDescansoAuxiliar();
            et.finDescansoA(a);
        } finally {
            lock.unlock();
        }
    }

    /**
    * El método descansoAuxiliar2 se encargará de manejar el descanso del auxiliar 2
    * cada vez que prepare las vacunas dichas en el enucniado. Añade al auxiliar 2
    * a su cola de descanso, hacen su descanso, y salen.
    */
    public void descansoAuxiliar2(Auxiliar a) throws InterruptedException {
        lock.lock();
        try {
            descansoA.add(a);
            et.inicioDescansoA(a);
            imprimirColaDescansoAuxiliar();
            Thread.sleep((int) (Math.random() * ((4000 - 1000 + 1) + 1000)));
            descansoA.remove(a);
            imprimirColaDescansoAuxiliar();
            et.finDescansoA(a);
        } finally {
            lock.unlock();
        }
    }

    /**
    * El método imprimirColaDescansoAuxiliar se encarga de mostrar por pantalla
    * la cola de descanso de los auxiliares en la interfaz
    */
    private synchronized void imprimirColaDescansoAuxiliar() {
        String texto1 = "";
        if (!descansoA.isEmpty()) {
            for (int i = 0; i < descansoA.size(); i++) {
                texto1 += descansoA.get(i).getIdentificador() + " ";
            }
        }
        colaDescansoTxtAux.setText(texto1);
    }
    
    /**
    * El método imprimirColaDescansoSanitario se encarga de mostrar por pantalla
    * la cola de descanso de los sanitarios en la interfaz. 
    */
    private synchronized void imprimirColaDescansoSanitario() {
        String texto = "";
        if (!descansoS.isEmpty()) {
            for (int i = 0; i < descansoS.size(); i++) {
                texto += descansoS.get(i).getIdentificador() + " ";
            }
        }
        colaDescansoTxtSan.setText(texto);
    }

}
