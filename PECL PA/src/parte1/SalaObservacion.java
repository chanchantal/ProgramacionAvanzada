package parte1;

import java.util.ArrayList;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import javax.swing.JTextField;

public class SalaObservacion {

    private final javax.swing.JTextField puesto1Txt;
    private final javax.swing.JTextField puesto2Txt;
    private final javax.swing.JTextField puesto3Txt;
    private final javax.swing.JTextField puesto4Txt;
    private final javax.swing.JTextField puesto5Txt;
    private final javax.swing.JTextField puesto6Txt;
    private final javax.swing.JTextField puesto7Txt;
    private final javax.swing.JTextField puesto8Txt;
    private final javax.swing.JTextField puesto9Txt;
    private final javax.swing.JTextField puesto10Txt;
    private final javax.swing.JTextField puesto11Txt;
    private final javax.swing.JTextField puesto12Txt;
    private final javax.swing.JTextField puesto13Txt;
    private final javax.swing.JTextField puesto14Txt;
    private final javax.swing.JTextField puesto15Txt;
    private final javax.swing.JTextField puesto16Txt;
    private final javax.swing.JTextField puesto17Txt;
    private final javax.swing.JTextField puesto18Txt;
    private final javax.swing.JTextField puesto19Txt;
    private final javax.swing.JTextField puesto20Txt;

    public Paciente paciente;
    public Sanitario sanitario;

    private boolean reaccion;
    private final Lock lock = new ReentrantLock();

    private final ArrayList<Paciente> reposar = new ArrayList<>();
;
    private ArrayList<JTextField> arrayTxt = new ArrayList<>();

    private final Semaphore sanitarioObserva = new Semaphore(0);
    private final Semaphore pacienteReacciona = new Semaphore(0);

    private final Condition esperar = lock.newCondition();

    private final CyclicBarrier puesto1 = new CyclicBarrier(2);
    private final CyclicBarrier puesto2 = new CyclicBarrier(2);
    private final CyclicBarrier puesto3 = new CyclicBarrier(2);
    private final CyclicBarrier puesto4 = new CyclicBarrier(2);
    private final CyclicBarrier puesto5 = new CyclicBarrier(2);
    private final CyclicBarrier puesto6 = new CyclicBarrier(2);
    private final CyclicBarrier puesto7 = new CyclicBarrier(2);
    private final CyclicBarrier puesto8 = new CyclicBarrier(2);
    private final CyclicBarrier puesto9 = new CyclicBarrier(2);
    private final CyclicBarrier puesto10 = new CyclicBarrier(2);
    private final CyclicBarrier puesto11 = new CyclicBarrier(2);
    private final CyclicBarrier puesto12 = new CyclicBarrier(2);
    private final CyclicBarrier puesto13 = new CyclicBarrier(2);
    private final CyclicBarrier puesto14 = new CyclicBarrier(2);
    private final CyclicBarrier puesto15 = new CyclicBarrier(2);
    private final CyclicBarrier puesto16 = new CyclicBarrier(2);
    private final CyclicBarrier puesto17 = new CyclicBarrier(2);
    private final CyclicBarrier puesto18 = new CyclicBarrier(2);
    private final CyclicBarrier puesto19 = new CyclicBarrier(2);
    private final CyclicBarrier puesto20 = new CyclicBarrier(2);

    public SalaObservacion(JTextField puesto1Txt, JTextField puesto2Txt, JTextField puesto3Txt, JTextField puesto4Txt, JTextField puesto5Txt, JTextField puesto6Txt, JTextField puesto7Txt, JTextField puesto8Txt, JTextField puesto9Txt, JTextField puesto10Txt, JTextField puesto11Txt, JTextField puesto12Txt, JTextField puesto13Txt, JTextField puesto14Txt, JTextField puesto15Txt, JTextField puesto16Txt, JTextField puesto17Txt, JTextField puesto18Txt, JTextField puesto19Txt, JTextField puesto20Txt) {
        arrayTxt = new ArrayList<>();
        arrayTxt.add(puesto1Txt);
        arrayTxt.add(puesto2Txt);
        arrayTxt.add(puesto3Txt);
        arrayTxt.add(puesto4Txt);
        arrayTxt.add(puesto5Txt);
        arrayTxt.add(puesto6Txt);
        arrayTxt.add(puesto7Txt);
        arrayTxt.add(puesto8Txt);
        arrayTxt.add(puesto9Txt);
        arrayTxt.add(puesto10Txt);
        arrayTxt.add(puesto11Txt);
        arrayTxt.add(puesto12Txt);
        arrayTxt.add(puesto13Txt);
        arrayTxt.add(puesto14Txt);
        arrayTxt.add(puesto15Txt);
        arrayTxt.add(puesto16Txt);
        arrayTxt.add(puesto17Txt);
        arrayTxt.add(puesto18Txt);
        arrayTxt.add(puesto19Txt);
        arrayTxt.add(puesto20Txt);
        this.puesto1Txt = puesto1Txt;
        this.puesto2Txt = puesto2Txt;
        this.puesto3Txt = puesto3Txt;
        this.puesto4Txt = puesto4Txt;
        this.puesto5Txt = puesto5Txt;
        this.puesto6Txt = puesto6Txt;
        this.puesto7Txt = puesto7Txt;
        this.puesto8Txt = puesto8Txt;
        this.puesto9Txt = puesto9Txt;
        this.puesto10Txt = puesto10Txt;
        this.puesto11Txt = puesto11Txt;
        this.puesto12Txt = puesto12Txt;
        this.puesto13Txt = puesto13Txt;
        this.puesto14Txt = puesto14Txt;
        this.puesto15Txt = puesto15Txt;
        this.puesto16Txt = puesto16Txt;
        this.puesto17Txt = puesto17Txt;
        this.puesto18Txt = puesto18Txt;
        this.puesto19Txt = puesto19Txt;
        this.puesto20Txt = puesto20Txt;
        this.reaccion = false;
    }

    public void entrar(Paciente p) throws BrokenBarrierException {
        lock.lock();
        try {
            while (reposar.size() > 20) {
                esperar.await();
            }
            reposar.add(p);
           
            arrayTxt.get(reposar.indexOf(p)).setText(p.getIdentificador());//Añado el paciente en los JtextField

        } catch (InterruptedException e) {
        } finally {
            lock.unlock();
        }
    }

    public void estaReposando(Paciente p) throws InterruptedException {
        Thread.sleep(10000);
        reaccion(p);
    }

    public void salir(Paciente p) throws InterruptedException {
        lock.lock();
        try {
            reposar.remove(p);

            esperar.signalAll();
        } finally {
            lock.unlock();
        }

    }

    public void sanitarioObserva(Sanitario s) throws InterruptedException {
        sanitarioObserva.acquire();

        System.out.println("El sanitario esta atendiendo al paciente");
        Thread.sleep((int) (Math.random() * (5000 - 2000 + 1) + 2000));
        pacienteReacciona.release();

    }

    public void reaccion(Paciente p) throws InterruptedException {

        int probabilidad = (int) ((Math.random() * (100 - 1 + 1) + 1));

        if (probabilidad > 0 && probabilidad < 5) {
            reaccion = true;
            System.out.println(p.getIdentificador() + " está sufriendo una reacción a la vacuna");
            pacienteReacciona.acquire();
            sanitarioObserva.release();
            arrayTxt.get(probabilidad).setText(sanitario.getIdentificador());
            System.out.println("el sanitario ha atendido al paciente" + p.getIdentificador());
            reaccion = false;
        }
    }

    public boolean isReaccion() {
        return reaccion;
    }

    public void setReaccion(boolean reaccion) {
        this.reaccion = reaccion;
    }

    public void puesto(int eleccion, String id) throws InterruptedException, BrokenBarrierException {

        switch (eleccion) {
            case 0:
                puesto1Txt.setText(puesto1Txt.getText() + " " + id);
                // jtextfield del puesto 0 y le hago un setText del id del paciente y del sanitario
                puesto1.await();
                
                puesto1Txt.setText(" ");
                break;
            case 1:
                puesto2Txt.setText(puesto2Txt.getText() + " " + id);
                puesto2.await();
                
                puesto2Txt.setText(" ");
                break;

            case 2:
                puesto3Txt.setText(puesto3Txt.getText() + " " + id);
                puesto3.await();
                
                puesto3Txt.setText("");
                break;
            case 3:
                puesto4Txt.setText(puesto4Txt.getText() + " " + id);
                puesto4.await();
                
                puesto4Txt.setText("");
                break;
            case 4:
                puesto5Txt.setText(puesto5Txt.getText() + " " + id);
                puesto5.await();
                
                puesto5Txt.setText("");
                break;
            case 5:
                puesto6Txt.setText(puesto6Txt.getText() + " " + id);
                puesto6.await();
                
                puesto6Txt.setText("");
                break;
            case 6:
                puesto7Txt.setText(puesto7Txt.getText() + " " + id);
                puesto7.await();
                
                puesto7Txt.setText("");
                break;
            case 7:
                puesto8Txt.setText(puesto8Txt.getText() + " " + id);
                puesto8.await();
                
                puesto8Txt.setText("");
                break;
            case 8:
                puesto9Txt.setText(puesto9Txt.getText() + " " + id);
                puesto9.await();
                
                puesto9Txt.setText("");
                break;
            case 9:
                puesto10Txt.setText(puesto10Txt.getText() + " " + id);
                puesto10.await();
                
                puesto10Txt.setText("");
                break;
            case 10:
                puesto11Txt.setText(puesto11Txt.getText() + " " + id);
                // jtextfield del puesto 0 y le hago un setText del id del paciente y del sanitario
                puesto1.await();
                
                puesto11Txt.setText(" ");
                break;
            case 11:
                puesto12Txt.setText(puesto12Txt.getText() + " " + id);
                puesto12.await();
                
                puesto12Txt.setText(" ");
                break;

            case 12:
                puesto13Txt.setText(puesto13Txt.getText() + " " + id);
                puesto13.await();
                
                puesto13Txt.setText("");
                break;
            case 13:
                puesto14Txt.setText(puesto14Txt.getText() + " " + id);
                puesto14.await();
                
                puesto14Txt.setText("");
                break;
            case 14:
                puesto15Txt.setText(puesto15Txt.getText() + " " + id);
                puesto15.await();
                
                puesto15Txt.setText("");
                break;
            case 15:
                puesto16Txt.setText(puesto16Txt.getText() + " " + id);
                puesto16.await();
                
                puesto16Txt.setText("");
                break;
            case 16:
                puesto17Txt.setText(puesto17Txt.getText() + " " + id);
                puesto17.await();
                
                puesto17Txt.setText("");
                break;
            case 17:
                puesto18Txt.setText(puesto18Txt.getText() + " " + id);
                puesto18.await();
                
                puesto18Txt.setText("");
                break;
            case 18:
                puesto19Txt.setText(puesto19Txt.getText() + " " + id);
                puesto19.await();
                
                puesto19Txt.setText("");
                break;
            case 19:
                puesto20Txt.setText(puesto20Txt.getText() + " " + id);
                puesto20.await();
                
                puesto20Txt.setText("");
                break;
        }
    }
}
