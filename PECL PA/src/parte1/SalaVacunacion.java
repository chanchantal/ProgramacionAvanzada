package parte1;

import java.util.ArrayList;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import javax.swing.JTextField;

/**
 * En la clase SalaVacunacion el paciente entrará, irá a un puesto aleatorio, y
 * el Sanitario se encargará de vacunarle. Por otra parte, el Auxiliar 2 estará
 * en todo momento haciendo vacunas para los sanitarios.
 */
public class SalaVacunacion {

    private final EscrituraTexto et;
    private Paciente paciente;
    private boolean cerrado1 = false;
    private boolean cerrado2 = false;
    private boolean cerrado3 = false;
    private boolean cerrado4 = false;
    private boolean cerrado5 = false;
    private boolean cerrado6 = false;
    private boolean cerrado7 = false;
    private boolean cerrado8 = false;
    private boolean cerrado9 = false;
    private boolean cerrado10 = false;
    

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
    private final Paciente[] puestoPaciente = {null, null, null, null, null, null, null, null, null, null};
    private int pp = 0;

    private final Sanitario[] puestoSanitario = {null, null, null, null, null, null, null, null, null, null};
    private int ps = 0;

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

    /**
     * En el constructor inicializamos los JTextField necesarios para poder
     * poner en funcionamiento la interfaz, así como la clase de EscrituraTexto,
     * que se encargará de rellenar el log
     */
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

    /**
     * El método entrar hará que un paciente entre a un puesto en la sala de
     * Vacunación. Tendrá un lock para que pasen de uno en uno, se comprueba si
     * la sala está llena, y si tiene acceso elegirá un puesto aleatorio para
     * acceder a él.
     */
    public void entrar(Paciente p) throws BrokenBarrierException, InterruptedException { //entra el paciente a vacunarse
        lock.lock();
        int eleccion;
        try {
            while (pp >= 10) {
                esperaP.await();
            }

            do {
                eleccion = (int) (Math.random() * 10);
            } while (puestoPaciente[eleccion] != null);

            puestoPaciente[eleccion] = p;
            pp++;
            p.setPuesto(eleccion);

        } catch (InterruptedException e) {
        } finally {
            lock.unlock();
        }

    }

    /**
     * El método salir se encargará de sacar a un paciente de la sala de
     * Vacunación, así como del puesto en el que se encontraba presente.
     */
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

    /**
     * El método sanitarioVacuna se encargará de proporcionar la vacuna a los
     * pacientes Tiene un procedimiento parecido al de entrar.Se echa un lock,
 se comprueba si tiene espacio en la sala para acceder, se le deriva a un
 puesto de vacunación libre y pone la vacuna.
     * @param s
     */
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
            puestoSanitario[eleccion] = s;
            ps++;
            s.setPuesto(eleccion);

        } catch (InterruptedException e) {
        } finally {
            lock.unlock();
        }

    }

    /**
     * El método salirSan se encarga de sacar al Sanitario para que deje libre
     * su puesto.
     */
    public void salirSan(Sanitario s) {
        lock.lock();
        try {

            puestoSanitario[s.getPuesto()] = null;
            ps--;
            esperaS.signal();
        } finally {
            lock.unlock();
        }
    }

    /**
     * En el método haciendoVacunas el auxiliar 2 se encargará de hacer las
     * vacunas para proporcionarlas a los sanitarios.
     * @param a
     */
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

    /**
     * El método cogeVacuna se encarga de liberar el semáforo de Vacuna que
     * cierra el auxiliar a la hora de crear una vacuna, y luego se encarga de
     * retirar una de las vacunas de la cola en la que se encuentra. Este método
     * se le pasa a sanitarioVacuna para que cumpla su procedimiento.
     */
    public void cogeVacuna() throws InterruptedException {

        semaforoVacuna.acquire();
        colaVacunas.remove(0);
    }

    /**
     * El método puestoPaciente se encarga de interactuar con puestoSanitario
     * para conseguir que ambos, paciente y sanitario, acaben ocupando el mismo
     * puesto para poder poner la vacuna.Es un juego de semáforos que conecta
 el puesto del paciente con el del sanitario.
     * @param p
     */
    public void puestoPaciente(Paciente p) throws InterruptedException, BrokenBarrierException {

        int eleccion = p.getPuesto();
        switch (eleccion) {
            case 0 -> {
                puesto1Txt.setText(puesto1Txt.getText() + " " + p.getIdentificador());

                puesto1s.release();

                puesto1.acquire();
            }
            case 1 -> {
                puesto2Txt.setText(puesto2Txt.getText() + " " + p.getIdentificador());

                puesto2s.release();
                puesto2.acquire();
            }

            case 2 -> {
                puesto3Txt.setText(puesto3Txt.getText() + " " + p.getIdentificador());

                puesto3s.release();
                puesto3.acquire();
            }
            case 3 -> {
                puesto4Txt.setText(puesto4Txt.getText() + " " + p.getIdentificador());

                puesto4s.release();
                puesto4.acquire();
            }
            case 4 -> {
                puesto5Txt.setText(puesto5Txt.getText() + " " + p.getIdentificador());

                puesto5s.release();
                puesto5.acquire();
            }
            case 5 -> {
                puesto6Txt.setText(puesto6Txt.getText() + " " + p.getIdentificador());

                puesto6s.release();
                puesto6.acquire();
            }
            case 6 -> {
                puesto7Txt.setText(puesto7Txt.getText() + " " + p.getIdentificador());

                puesto7s.release();
                puesto7.acquire();
            }
            case 7 -> {
                puesto8Txt.setText(puesto8Txt.getText() + " " + p.getIdentificador());

                puesto8s.release();
                puesto8.acquire();
            }
            case 8 -> {
                puesto9Txt.setText(puesto9Txt.getText() + " " + p.getIdentificador());

                puesto9s.release();
                puesto9.acquire();
            }
            case 9 -> {
                puesto10Txt.setText(puesto10Txt.getText() + " " + p.getIdentificador());

                puesto10s.release();
                puesto10.acquire();
            }

        }

    }

    /**
     * Método complementario a puestoPaciente. En este, a parte, nos encargamos
     * de imprimir imprimir qué paciente ha sido vacunado por qué sanitario y en
     * qué puesto.
     */
    public void puestoSanitario(Sanitario s) throws InterruptedException, BrokenBarrierException {

        int eleccion = s.getPuesto();

        switch (eleccion) {
            case 0 -> {
                puesto1Txt.setText(puesto1Txt.getText() + " " + s.getIdentificador());

                puesto1s.acquire();
                cogeVacuna();
                Thread.sleep((int) (Math.random() * ((5000 - 3000 + 1) + 3000)));
                System.out.println("Paciente " + puestoPaciente[s.getPuesto()].getIdentificador()
                        + " ha sido vacunado por el Sanitario " + s.getIdentificador() + " en el puesto 1");
                et.vacunacion(s, puestoPaciente[0], 1);
                puesto1Txt.setText("");
                puesto1.release();
                if (cerrado1 == true) {
                    s.setDescansoForzado(true);
                    cerrado1 = false;
                }
            }
            case 1 -> {
                puesto2Txt.setText(puesto2Txt.getText() + " " + s.getIdentificador());

                puesto2s.acquire();
                cogeVacuna();
                Thread.sleep((int) (Math.random() * ((5000 - 3000 + 1) + 3000)));
                System.out.println("Paciente " + puestoPaciente[s.getPuesto()].getIdentificador()
                        + " ha sido vacunado por el Sanitario " + s.getIdentificador() + " en el puesto 2");
                et.vacunacion(s, puestoPaciente[1], 2);
                puesto2Txt.setText("");
                puesto2.release();
                if (cerrado2 == true) {
                    s.setDescansoForzado(true);
                    cerrado2 = false;
                }
            }

            case 2 -> {
                puesto3Txt.setText(puesto3Txt.getText() + " " + s.getIdentificador());

                puesto3s.acquire();
                cogeVacuna();
                Thread.sleep((int) (Math.random() * ((5000 - 3000 + 1) + 3000)));
                System.out.println("Paciente " + puestoPaciente[s.getPuesto()].getIdentificador()
                        + " ha sido vacunado por el Sanitario " + s.getIdentificador() + " en el puesto 3");
                et.vacunacion(s, puestoPaciente[2], 3);
                puesto3Txt.setText("");
                puesto3.release();
                if (cerrado3 == true) {
                    s.setDescansoForzado(true);
                    cerrado3 = false;
                }
            }
            case 3 -> {
                puesto4Txt.setText(puesto4Txt.getText() + " " + s.getIdentificador());

                puesto4s.acquire();
                cogeVacuna();
                Thread.sleep((int) (Math.random() * ((5000 - 3000 + 1) + 3000)));
                System.out.println("Paciente " + puestoPaciente[s.getPuesto()].getIdentificador()
                        + " ha sido vacunado por el Sanitario " + s.getIdentificador() + " en el puesto 4");
                et.vacunacion(s, puestoPaciente[3], 4);
                puesto4Txt.setText("");
                puesto4.release();
                if (cerrado4 == true) {
                    s.setDescansoForzado(true);
                    cerrado4 = false;
                }
            }
            case 4 -> {
                puesto5Txt.setText(puesto5Txt.getText() + " " + s.getIdentificador());

                puesto5s.acquire();
                cogeVacuna();
                Thread.sleep((int) (Math.random() * ((5000 - 3000 + 1) + 3000)));
                System.out.println("Paciente " + puestoPaciente[s.getPuesto()].getIdentificador()
                        + " ha sido vacunado por el Sanitario " + s.getIdentificador() + " en el puesto 5");
                et.vacunacion(s, puestoPaciente[4], 5);
                puesto5Txt.setText("");
                puesto5.release();
                if (cerrado5 == true) {
                    s.setDescansoForzado(true);
                    cerrado5 = false;
                }
            }
            case 5 -> {
                puesto6Txt.setText(puesto6Txt.getText() + " " + s.getIdentificador());

                puesto6s.acquire();
                cogeVacuna();
                Thread.sleep((int) (Math.random() * ((5000 - 3000 + 1) + 3000)));
                System.out.println("Paciente " + puestoPaciente[s.getPuesto()].getIdentificador()
                        + " ha sido vacunado por el Sanitario " + s.getIdentificador() + " en el puesto 6");
                et.vacunacion(s, puestoPaciente[5], 6);
                puesto6Txt.setText("");
                puesto6.release();
                if (cerrado6 == true) {
                    s.setDescansoForzado(true);
                    cerrado6 = false;
                }
            }
            case 6 -> {
                puesto7Txt.setText(puesto7Txt.getText() + " " + s.getIdentificador());

                puesto7s.acquire();
                cogeVacuna();
                Thread.sleep((int) (Math.random() * ((5000 - 3000 + 1) + 3000)));
                System.out.println("Paciente " + puestoPaciente[s.getPuesto()].getIdentificador()
                        + " ha sido vacunado por el Sanitario " + s.getIdentificador() + " en el puesto 7");
                et.vacunacion(s, puestoPaciente[6], 7);
                puesto7Txt.setText("");
                puesto7.release();
                if (cerrado7 == true) {
                    s.setDescansoForzado(true);
                    cerrado7 = false;
                }
            }
            case 7 -> {
                puesto8Txt.setText(puesto8Txt.getText() + " " + s.getIdentificador());

                puesto8s.acquire();
                cogeVacuna();
                Thread.sleep((int) (Math.random() * ((5000 - 3000 + 1) + 3000)));
                System.out.println("Paciente " + puestoPaciente[s.getPuesto()].getIdentificador()
                        + " ha sido vacunado por el Sanitario " + s.getIdentificador() + " en el puesto 8");
                et.vacunacion(s, puestoPaciente[7], 8);
                puesto8Txt.setText("");
                puesto8.release();
                if (cerrado8 == true) {
                    s.setDescansoForzado(true);
                    cerrado8 = false;
                }
            }
            case 8 -> {
                puesto9Txt.setText(puesto9Txt.getText() + " " + s.getIdentificador());

                puesto9s.acquire();
                cogeVacuna();
                Thread.sleep((int) (Math.random() * ((5000 - 3000 + 1) + 3000)));
                System.out.println("Paciente " + puestoPaciente[s.getPuesto()].getIdentificador()
                        + " ha sido vacunado por el Sanitario " + s.getIdentificador() + " en el puesto 9");
                et.vacunacion(s, puestoPaciente[8], 9);
                puesto9Txt.setText("");
                puesto9.release();
                if (cerrado9 == true) {
                    s.setDescansoForzado(true);
                    cerrado9 = false;
                }
            }
            case 9 -> {
                puesto10Txt.setText(puesto10Txt.getText() + " " + s.getIdentificador());
                puesto10s.acquire();
                cogeVacuna();
                Thread.sleep((int) (Math.random() * ((5000 - 3000 + 1) + 3000)));
                System.out.println("Paciente " + puestoPaciente[s.getPuesto()].getIdentificador()
                        + " ha sido vacunado por el Sanitario " + s.getIdentificador() + " en el puesto 10");
                et.vacunacion(s, puestoPaciente[9], 10);
                puesto10Txt.setText("");
                puesto10.release();;
                if (cerrado10 == true) {
                    s.setDescansoForzado(true);
                    cerrado10 = false;
                }
            }
        }
    }

    /**
     * cerrarPuesto1 establecerá la variable cerrado del puesto que le corresponda
     * a true, para establecer que ese puesto está cerrado. Los 9 siguientes métodos
     * para el resto de puestos cumplen la misma función que este
     */
    public void cerrarPuesto1() {
        cerrado1 = true;
    }
    
    public void cerrarPuesto2() {
        cerrado2 = true;
    }
    
    public void cerrarPuesto3() {
        cerrado3 = true;
    }
    
    public void cerrarPuesto4() {
        cerrado4 = true;
    }
    
    public void cerrarPuesto5() {
        cerrado5 = true;
    }
    
    public void cerrarPuesto6() {
        cerrado6 = true;
    }
    
    public void cerrarPuesto7() {
        cerrado7 = true;
    }
    
    public void cerrarPuesto8() {
        cerrado8 = true;
    }
    
    public void cerrarPuesto9() {
        cerrado9 = true;
    }
    
    public void cerrarPuesto10() {
        cerrado10 = true;
    }

    public String getPuesto1Txt() {
        return puesto1Txt.getText();
    }

    public String getPuesto2Txt() {
        return puesto2Txt.getText();
    }

    public String getPuesto3Txt() {
        return puesto3Txt.getText();
    }

    public String getPuesto4Txt() {
        return puesto4Txt.getText();
    }

    public String getPuesto5Txt() {
        return puesto5Txt.getText();
    }

    public String getPuesto6Txt() {
        return puesto6Txt.getText();
    }

    public String getPuesto7Txt() {
        return puesto7Txt.getText();
    }

    public String getPuesto8Txt() {
        return puesto8Txt.getText();
    }

    public String getPuesto9Txt() {
        return puesto9Txt.getText();
    }

    public String getPuesto10Txt() {
        return puesto10Txt.getText();
    }

    public String getAuxiliarTxt() {
        return auxiliarTxt.getText();
    }

    public int VacunasDisponibles() {
        return colaVacunas.size();
    }

}
