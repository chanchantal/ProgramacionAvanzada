package parte1;

import java.util.ArrayList;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import javax.swing.JTextField;

public class SalaVacunacion {
    private EscrituraTexto et;
    private Paciente paciente;
    
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
    private final javax.swing.JTextField auxiliarTxt;
    private final javax.swing.JTextField vacunasDisponibles;

    private final Lock lock = new ReentrantLock();
    private final  Paciente[] puestoPaciente = {null,null,null,null,null,null,null,null,null,null};
    private int pp =0; 
    
    private final  Sanitario[] puestoSanitario = {null,null,null,null,null,null,null,null,null,null};
    private int ps =0;

   
    private final ArrayList<Integer> colaVacunas = new ArrayList<>();
    private ArrayList<JTextField> arrayTxt = new ArrayList<>();

    private final Condition esperaS = lock.newCondition();
    private final Condition esperaP = lock.newCondition();

    private final Semaphore semaforoVacuna = new Semaphore(0);

    private final Semaphore puesto1 = new Semaphore(0);
    private final Semaphore puesto2 = new Semaphore(0);
    private final Semaphore puesto3 = new Semaphore(0);
    private final Semaphore puesto4 = new Semaphore(0);
    private final Semaphore puesto5 = new Semaphore(0);
    private final Semaphore puesto6 = new Semaphore(0);
    private final Semaphore puesto7 = new Semaphore(0);
    private final Semaphore puesto8 = new Semaphore(0);
    private final Semaphore puesto9 = new Semaphore(0);
    private final Semaphore puesto10 = new Semaphore(0);
    
    private final Semaphore puesto1s = new Semaphore(0);
    private final Semaphore puesto2s = new Semaphore(0);
    private final Semaphore puesto3s = new Semaphore(0);
    private final Semaphore puesto4s = new Semaphore(0);
    private final Semaphore puesto5s = new Semaphore(0);
    private final Semaphore puesto6s = new Semaphore(0);
    private final Semaphore puesto7s = new Semaphore(0);
    private final Semaphore puesto8s = new Semaphore(0);
    private final Semaphore puesto9s = new Semaphore(0);
    private final Semaphore puesto10s = new Semaphore(0);

    public SalaVacunacion(JTextField puesto1Txt, JTextField puesto2Txt, JTextField puesto3Txt, JTextField puesto4Txt, JTextField puesto5Txt, JTextField puesto6Txt, JTextField puesto7Txt, JTextField puesto8Txt, JTextField puesto9Txt, JTextField puesto10Txt, JTextField auxiliarTxt, JTextField vacunasDisponibles, EscrituraTexto et) {
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
        this.auxiliarTxt = auxiliarTxt;
        this.vacunasDisponibles = vacunasDisponibles;
        this.et = et;
    }

    public void entrar(Paciente p) throws BrokenBarrierException, InterruptedException { //entra el paciente a vacunarse
        lock.lock();
        int eleccion;
        try {
            while (pp >= 10) {
                esperaP.await();
            }
            
            do {
                    eleccion = (int) (Math.random() * 10);
            } while ( puestoPaciente[eleccion] != null);
            
            puestoPaciente[eleccion]= p;
            pp++;
            p.setPuesto(eleccion);

        } catch (InterruptedException e) {
        } finally {
            lock.unlock();
        }
        
    }

    public void salir(Paciente p) {
        lock.lock();
        try {
            puestoPaciente[p.getPuesto()] = null;
            pp--;
            esperaP.signalAll();
        } finally {
            lock.unlock();
        }
    }

    public void sanitarioVacuna(Sanitario s) throws InterruptedException, BrokenBarrierException { //aquí pongo la vacuna
        lock.lock();
        int eleccion;
        try {
            while (ps >= 10) {
                esperaS.await();
            }
            do {
                    eleccion = (int) (Math.random() * 10);
            } while (puestoSanitario[eleccion] != null);
            puestoSanitario[eleccion]= s;
            ps++;
            s.setPuesto(eleccion);
           
        } catch (InterruptedException e) {
        } finally {
            lock.unlock();
        }
        

    }

    public void salirSan(Sanitario s) {
        lock.lock();
        try {
            
            puestoSanitario[s.getPuesto()]=null;
            ps--;
            esperaS.signal();
        } finally {
            lock.unlock();
        }
    }

    public void haciendoVacunas(Auxiliar a) throws InterruptedException {

        for (int i = 0; i < 21; i++) {
            colaVacunas.add(i);
            auxiliarTxt.setText(a.getIdentificador());
            Thread.sleep((int) (Math.random() * ((1000 - 500 + 1) + 500)));
            vacunasDisponibles.setText("" + colaVacunas.size());
            semaforoVacuna.release();
        }
        auxiliarTxt.setText("");
    }

    public void cogeVacuna() throws InterruptedException {

        semaforoVacuna.acquire();
        colaVacunas.remove(0);
    }          
    
    public void puestoPaciente(Paciente p) throws InterruptedException, BrokenBarrierException {
        
            int eleccion = p.getPuesto();
            switch (eleccion) {
                case 0:
                    puesto1Txt.setText(puesto1Txt.getText() + " " + p.getIdentificador());

                    puesto1s.release();

                    puesto1.acquire();
                    break;
                case 1:
                    puesto2Txt.setText(puesto2Txt.getText() + " " + p.getIdentificador());

                    puesto2s.release();
                    puesto2.acquire();
                    break;

                case 2:
                    puesto3Txt.setText(puesto3Txt.getText() + " " + p.getIdentificador());

                    puesto3s.release();
                    puesto3.acquire();
                    break;
                case 3:
                    puesto4Txt.setText(puesto4Txt.getText() + " " + p.getIdentificador());

                    puesto4s.release();
                    puesto4.acquire();
                    break;
                case 4:
                    puesto5Txt.setText(puesto5Txt.getText() + " " + p.getIdentificador());

                    puesto5s.release();
                    puesto5.acquire();
                    break;
                case 5:
                    puesto6Txt.setText(puesto6Txt.getText() + " " + p.getIdentificador());

                    puesto6s.release();
                    puesto6.acquire();
                    break;
                case 6:
                    puesto7Txt.setText(puesto7Txt.getText() + " " + p.getIdentificador());

                    puesto7s.release();
                    puesto7.acquire();
                    break;
                case 7:
                    puesto8Txt.setText(puesto8Txt.getText() + " " + p.getIdentificador());

                    puesto8s.release();
                    puesto8.acquire();
                    break;
                case 8:
                    puesto9Txt.setText(puesto9Txt.getText() + " " + p.getIdentificador());

                    puesto9s.release();
                    puesto9.acquire();
                    break;
                case 9:
                    puesto10Txt.setText(puesto10Txt.getText() + " " + p.getIdentificador());

                    puesto10s.release();
                    puesto10.acquire();
                    break;

            }
        
    }
    
    public void puestoSanitario(Sanitario s) throws InterruptedException, BrokenBarrierException {
        
            
            int eleccion =s.getPuesto();
         
            switch (eleccion) {
                case 0 -> {
                    puesto1Txt.setText(puesto1Txt.getText() + " " + s.getIdentificador());

                    puesto1s.acquire();
                    cogeVacuna();
                    Thread.sleep((int) (Math.random() * ((5000 - 3000 + 1) + 3000)));
                    puesto1Txt.setText("");
                    puesto1.release();
            }
                case 1 -> {
                    puesto2Txt.setText(puesto2Txt.getText() + " " + s.getIdentificador());

                    puesto2s.acquire();
                    cogeVacuna();
                    Thread.sleep((int) (Math.random() * ((5000 - 3000 + 1) + 3000)));
                    puesto2Txt.setText("");
                    puesto2.release();
            }

                case 2 -> {
                    puesto3Txt.setText(puesto3Txt.getText() + " " + s.getIdentificador());

                    puesto3s.acquire();
                    cogeVacuna();
                    Thread.sleep((int) (Math.random() * ((5000 - 3000 + 1) + 3000)));
                    puesto3Txt.setText("");
                    puesto3.release();
            }
                case 3 -> {
                    puesto4Txt.setText(puesto4Txt.getText() + " " + s.getIdentificador());

                    puesto4s.acquire();
                    cogeVacuna();
                    Thread.sleep((int) (Math.random() * ((5000 - 3000 + 1) + 3000)));
                    puesto4Txt.setText("");
                    puesto4.release();
            }
                case 4 -> {
                    puesto5Txt.setText(puesto5Txt.getText() + " " + s.getIdentificador());

                    puesto5s.acquire();
                    cogeVacuna();
                    Thread.sleep((int) (Math.random() * ((5000 - 3000 + 1) + 3000)));
                    puesto5Txt.setText("");
                    puesto5.release();
            }
                case 5 -> {
                    puesto6Txt.setText(puesto6Txt.getText() + " " + s.getIdentificador());

                    puesto6s.acquire();
                    cogeVacuna();
                    Thread.sleep((int) (Math.random() * ((5000 - 3000 + 1) + 3000)));
                    puesto6Txt.setText("");
                    puesto6.release();
            }
                case 6 -> {
                    puesto7Txt.setText(puesto7Txt.getText() + " " + s.getIdentificador());

                    puesto7s.acquire();
                    cogeVacuna();
                    Thread.sleep((int) (Math.random() * ((5000 - 3000 + 1) + 3000)));
                    puesto7Txt.setText("");
                    puesto7.release();
            }
                case 7 -> {
                    puesto8Txt.setText(puesto8Txt.getText() + " " + s.getIdentificador());

                    puesto8s.acquire();
                    cogeVacuna();
                    Thread.sleep((int) (Math.random() * ((5000 - 3000 + 1) + 3000)));
                    puesto8Txt.setText("");
                    puesto8.release();
            }
                case 8 -> {
                    puesto9Txt.setText(puesto9Txt.getText() + " " + s.getIdentificador());

                    puesto9s.acquire();
                    cogeVacuna();
                    Thread.sleep((int) (Math.random() * ((5000 - 3000 + 1) + 3000)));
                    puesto9Txt.setText("");
                    puesto9.release();
            }
                case 9 -> {
                    puesto10Txt.setText(puesto10Txt.getText() + " " + s.getIdentificador());

                    puesto10s.acquire();
                    cogeVacuna();
                    Thread.sleep((int) (Math.random() * ((5000 - 3000 + 1) + 3000)));
                    puesto10Txt.setText("");
                    puesto10.release();;
            }

            }
        
    }
}
