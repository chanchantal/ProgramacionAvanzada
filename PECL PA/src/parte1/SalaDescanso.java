package parte1;

import java.util.ArrayList;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import javax.swing.JTextField;

public class SalaDescanso {
    private Sanitario sanitario;
    
    private final Lock lock = new ReentrantLock();
    private final Lock lockVestidor = new ReentrantLock();

    private final Condition esperar = lock.newCondition();
    private final Condition vacuna = lock.newCondition();

    private final ArrayList<Sanitario> vacunar;
    private final ArrayList<Auxiliar> descansoA = new ArrayList<>();
    private final ArrayList<Sanitario> descansoS = new ArrayList<>();
    
    private final javax.swing.JTextField colaDescansoTxt;
    
    String texto1 , texto2 = "";

    public SalaDescanso(JTextField colaDescansoTxt) {
        this.colaDescansoTxt = colaDescansoTxt;
        vacunar  = new ArrayList<>(10);
    }

    
    public void vestidorSanitario(Sanitario s) throws InterruptedException {
        int eleccion;
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

    public boolean descansoSanitario(Sanitario s) throws InterruptedException {
        lock.lock();
        try {
            descansoS.add(s);
            imprimirColaDescansoSanitario();
            Thread.sleep((int) (Math.random() * ((8000 - 5000 + 1) + 5000)));
            colaDescansoTxt.setText("");
            descansoS.remove(s);
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
            colaDescansoTxt.setText("");
            descansoA.remove(a);
        } finally {
            lock.unlock();
        }
    }

    public void descansoAuxiliar2(Auxiliar a) throws InterruptedException {
        lock.lock();
        try {
            descansoA.add(a);
            Thread.sleep((int) (Math.random() * ((4000 - 1000 + 1) + 1000)));
            colaDescansoTxt.setText("");
            descansoA.remove(a);
        } finally {
            lock.unlock();
        }
    }
    
    
    // Para poner los auxiliares y los sanitarios en un mismo jtextfield
    
       private synchronized void imprimirColaDescansoAuxiliar(){
        String texto1 = "";
        if (!descansoA.isEmpty()){
            for (int i = 0; i< descansoA.size(); i++){
                texto1 += descansoA.get(i).getIdentificador() + " ";
            }
        }
        colaDescansoTxt.setText(texto1 + texto2);
    }
       private synchronized void imprimirColaDescansoSanitario(){
     
        if (!descansoS.isEmpty()){
            for (int i = 0; i< descansoS.size(); i++){
                texto2 += descansoS.get(i).getIdentificador() + " ";
            }
        }
        colaDescansoTxt.setText(texto1+texto2);
    }

}
