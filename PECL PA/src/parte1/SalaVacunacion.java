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
    private final  javax.swing.JTextField puesto1Txt;
    private final  javax.swing.JTextField puesto2Txt;
    private final  javax.swing.JTextField puesto3Txt;
    private final  javax.swing.JTextField puesto4Txt;
    private final  javax.swing.JTextField puesto5Txt;
    private final  javax.swing.JTextField puesto6Txt;
    private final  javax.swing.JTextField puesto7Txt;
    private final  javax.swing.JTextField puesto8Txt;
    private final  javax.swing.JTextField puesto9Txt;
    private final  javax.swing.JTextField puesto10Txt;
    private final javax.swing.JTextField auxiliarTxt;
    
    private  Paciente paciente;
    private  Sanitario sanitario;
    
    private final Lock lock = new ReentrantLock();
    private final Lock hayVacunas = new ReentrantLock();

    private final ArrayList<Sanitario> puestoSanitario = new ArrayList<>();
    private final ArrayList<Paciente> puestoPaciente = new ArrayList<>(10);
    private final ArrayList<Integer> colaVacunas = new ArrayList<>();
    private ArrayList<JTextField> arrayTxt = new ArrayList<>();

    private final Condition esperaS = lock.newCondition();
    private final Condition esperaP = lock.newCondition();
    private final Condition vacuna = lock.newCondition();

    private final Semaphore pacienteEspera = new Semaphore(0, true);
    private final Semaphore sanitarioVacuna = new Semaphore(0, true);

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

    public SalaVacunacion(JTextField puesto1Txt, JTextField puesto2Txt, JTextField puesto3Txt, JTextField puesto4Txt, JTextField puesto5Txt, JTextField puesto6Txt, JTextField puesto7Txt, JTextField puesto8Txt, JTextField puesto9Txt, JTextField puesto10Txt, JTextField auxiliarTxt) {
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

   }

    public void entrar(Paciente p) throws BrokenBarrierException { //entra el paciente a vacunarse
        int eleccion;
        lock.lock();
        System.out.println("El paciente entra a la sala de vacunación");

        try {
            while (puestoPaciente.size() > 10) {
                esperaP.await();
            }
            do {
                eleccion = (int) (Math.random() * 10);
            } while (puestoPaciente.get(eleccion) != null);
            puestoPaciente.add(eleccion, p);
            arrayTxt.get(eleccion).setText(paciente.getIdentificador());
            arrayTxt.get(eleccion).setText(sanitario.getIdentificador());
            puesto(eleccion);
            

        } catch (InterruptedException e) {
        } finally {
            lock.unlock();
        }
    }

    public void salir(Paciente p) {
        lock.lock();
        try {
            puestoPaciente.remove(p);
            esperaP.signalAll();
            System.out.println("El paciente ha recibido la vacuna y va a sala de observación");
        } finally {
            lock.unlock();
        }
    }

    public void haciendoVacunas(Auxiliar a) throws InterruptedException {
        
        for (int i = 0; i < 20; i++) {
            colaVacunas.add(i);
            auxiliarTxt.setText(a.getIdentificador());
            Thread.sleep((int) (Math.random() * ((1000 - 500 + 1) + 500)));
            vacuna.signalAll();
        }
    }

    public void sanitarioVacuna(Sanitario s) throws InterruptedException, BrokenBarrierException { //aquí pongo la vacuna
        for (int i = 0; i < 15; i++) {
            int eleccion;
            lock.lock();    
            try {
                while (puestoSanitario.size() > 10) {
                    esperaS.await();
                }
                do {
                    eleccion = (int) (Math.random() * 10);
                } while (puestoSanitario.get(eleccion) != null);
                puestoSanitario.add(eleccion, s);
                puesto(eleccion);
                colaVacunas.remove(0);

            } catch (InterruptedException e) {
            } finally {
                lock.unlock();
            }
        }

    }

    public void puesto(int eleccion) throws InterruptedException, BrokenBarrierException {
        switch (eleccion) {
            case 0:
                puesto1Txt.setText(paciente.getIdentificador()+ sanitario.getIdentificador());
                // jtextfield del puesto 0 y le hago un setText del id del paciente y del sanitario
                puesto1.await();
                Thread.sleep((int) (Math.random() * ((5000 - 3000 + 1) + 3000)));
                puesto1Txt.getText().isEmpty();
                break;
            case 1:
                puesto2Txt.setText(paciente.getIdentificador()+ sanitario.getIdentificador());
                puesto2.await();
                Thread.sleep((int) (Math.random() * ((5000 - 3000 + 1) + 3000)));
                puesto2Txt.getText().isEmpty();
                break;

            case 2:
                puesto3Txt.setText(paciente.getIdentificador()+ sanitario.getIdentificador());
                puesto3.await();
                Thread.sleep((int) (Math.random() * ((5000 - 3000 + 1) + 3000)));
                puesto3Txt.getText().isEmpty();
                break;
            case 3:
                puesto4Txt.setText(paciente.getIdentificador()+ sanitario.getIdentificador());
                puesto4.await();
                Thread.sleep((int) (Math.random() * ((5000 - 3000 + 1) + 3000)));
                puesto4Txt.getText().isEmpty();
                break;
            case 4:
                puesto5Txt.setText(paciente.getIdentificador()+ sanitario.getIdentificador());
                puesto5.await();
                Thread.sleep((int) (Math.random() * ((5000 - 3000 + 1) + 3000)));
                puesto5Txt.getText().isEmpty();
                break;
            case 5:
                puesto6Txt.setText(paciente.getIdentificador()+ sanitario.getIdentificador());
                puesto6.await();
                Thread.sleep((int) (Math.random() * ((5000 - 3000 + 1) + 3000)));
                puesto6Txt.getText().isEmpty();
                break;
            case 6:
                puesto7Txt.setText(paciente.getIdentificador()+ sanitario.getIdentificador());
                puesto7.await();
                Thread.sleep((int) (Math.random() * ((5000 - 3000 + 1) + 3000)));
                puesto7Txt.getText().isEmpty();
                break;
            case 7:
                puesto8Txt.setText(paciente.getIdentificador()+ sanitario.getIdentificador());
                puesto8.await();
                Thread.sleep((int) (Math.random() * ((5000 - 3000 + 1) + 3000)));
                puesto8Txt.getText().isEmpty();
                break;
            case 8:
                puesto9Txt.setText(paciente.getIdentificador()+ sanitario.getIdentificador());
                puesto9.await();
                Thread.sleep((int) (Math.random() * ((5000 - 3000 + 1) + 3000)));
                puesto9Txt.getText().isEmpty();
                break;
            case 9:
                puesto10Txt.setText(paciente.getIdentificador()+ sanitario.getIdentificador());
                puesto10.await();
                Thread.sleep((int) (Math.random() * ((5000 - 3000 + 1) + 3000)));
                puesto10Txt.getText().isEmpty();
                break;

        }
    }   
}
