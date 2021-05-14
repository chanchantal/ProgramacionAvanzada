package parte1;

import java.util.ArrayList;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import javax.swing.JTextField;

public class SalaDescanso {


    private final Lock lock = new ReentrantLock();
    private final Lock lockVestidor = new ReentrantLock();

    private final Condition esperar = lock.newCondition();
    private final Condition vacuna = lock.newCondition();

    private final ArrayList<Sanitario> vacunar = new ArrayList<>();
    private final ArrayList<Auxiliar> descansoA = new ArrayList<>();
    private final ArrayList<Sanitario> descansoS = new ArrayList<>();
    
    private final javax.swing.JTextField colaDescansoTxt;

    public SalaDescanso(JTextField colaDescansoTxt) {
        this.colaDescansoTxt = colaDescansoTxt;
    }

    
   

    public void vestidorSanitario(Sanitario s) throws InterruptedException {
        int eleccion;
        lockVestidor.lock();
        try {
            Thread.sleep((int) (Math.random() * ((3000 - 1000 + 1) + 3000)));
            while (vacunar.size() > 10) {
                esperar.await();
            }
            do {
                eleccion = (int) (Math.random() * 10);
            } while (vacunar.get(eleccion) != null);
            vacunar.add(eleccion, s);
        } finally {
            lockVestidor.unlock();
        }
    }

    public boolean descansoSanitario(Sanitario s) throws InterruptedException {
        lock.lock();
        try {
            descansoS.add(s);
            imprimirColaDescansoSanitario();
            Thread.sleep((int) (Math.random() * ((8000 - 5000 + 1) + 5000)));
            descansoS.remove(0);
        } finally {
            lock.unlock();
        }
        return true;
    }

    public void descansoAuxiliar1(Auxiliar a) throws InterruptedException {
        lock.lock();
        try {
            descansoA.add(a);
            imprimirColaDescansoAuxiliar();
            Thread.sleep((int) (Math.random() * ((5000 - 3000 + 1) + 3000)));
            descansoA.remove(0);
        } finally {
            lock.unlock();
        }
    }

    public void descansoAuxiliar2(Auxiliar a) throws InterruptedException {
        lock.lock();
        try {
            descansoA.add(a);
            Thread.sleep((int) (Math.random() * ((4000 - 1000 + 1) + 1000)));
            descansoA.remove(0);
        } finally {
            lock.unlock();
        }
    }
    
    
    // Para poner los auxiliares y los sanitarios en un mismo jtextfield
        private synchronized void imprimirColaDescansoSanitarios(){
        String texto = "";
        if (descansoS.isEmpty()){
            for (int i = 0; i< descansoS.size(); i++){
                texto += descansoS.get(i).getIdentificador() + "";
            }
        }
        colaDescansoTxt.setText(texto);
    }
       private synchronized void imprimirColaDescansoAuxiliar(){
        String texto = "";
        if (descansoA.isEmpty()){
            for (int i = 0; i< descansoA.size(); i++){
                texto += descansoA.get(i).getIdentificador() + "";
            }
        }
        colaDescansoTxt.setText(texto);
    }
       private synchronized void imprimirColaDescansoSanitario(){
        String texto = "";
        if (descansoS.isEmpty()){
            for (int i = 0; i< descansoS.size(); i++){
                texto += descansoS.get(i).getIdentificador() + "";
            }
        }
        colaDescansoTxt.setText(texto);
    }

}
