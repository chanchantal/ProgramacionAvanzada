package parte1;

import java.util.ArrayList;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import javax.swing.JTextField;


/**
* La clase SalaObservación recogerá a los pacientes que acaban de ser vacunados, 
* para tenerlos en observación un tiempo y comprobar si sufren alguna reacción
*/
public class SalaObservacion {

    private final EscrituraTexto et;
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

    private final Paciente[] puestoPaciente = {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null};
    private int pp = 0;

    private ArrayList<JTextField> arrayTxt = new ArrayList<>();
    private ArrayList<Integer> puestos = new ArrayList<>();
    private final Semaphore sanitarioObserva = new Semaphore(0);
    private final Semaphore pacienteReacciona = new Semaphore(0);

    private final Condition esperar = lock.newCondition();


    /**
    * En el constructor inicializamos los JTextField necesarios para poder poner 
    * en funcionamiento la interfaz, así como la clase de EscrituraTexto, que
    * se encargará de rellenar el log
     */
    public SalaObservacion(JTextField puesto1Txt, JTextField puesto2Txt, JTextField puesto3Txt, JTextField puesto4Txt, JTextField puesto5Txt, JTextField puesto6Txt, JTextField puesto7Txt, JTextField puesto8Txt, JTextField puesto9Txt, JTextField puesto10Txt, JTextField puesto11Txt, JTextField puesto12Txt, JTextField puesto13Txt, JTextField puesto14Txt, JTextField puesto15Txt, JTextField puesto16Txt, JTextField puesto17Txt, JTextField puesto18Txt, JTextField puesto19Txt, JTextField puesto20Txt, EscrituraTexto et) {
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
        this.et=et;
    }

    /**
    * El método entrar funciona igual que en la SalaVacunación. El paciente entra
    * controlado por un lock, comprueba que hay puestos disponibles y accede a uno
    * de manera aleatoria. 
    */
    public void entrar(Paciente p) throws BrokenBarrierException {
        lock.lock();
        int eleccion;
        try {
            while (pp >= 20) {
                esperar.await();
            }

            do {
                eleccion = (int) (Math.random() * 20);
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
    * El método salir se encarga de sacar a los pacientes del array de pacientes, 
    * y también de dejar el puesto vacío. 
    */
    public void salir(Paciente p) throws InterruptedException {
        lock.lock();
        try {
            puestoPaciente[p.getPuesto()] = null;
            pp--;
            esperar.signalAll();
        } finally {
            lock.unlock();
        }
    }

    /**
    * sanitarioObserva entra en funcionamiento cuando un paciente experimenta una reacción
    * a la vacuna. Se entrelaza con el método reacción mediante un semáforo. Atiende
    * al paciente y finalmente liberan ambos el puesto. 
    */
    public void sanitarioObserva(Sanitario s) throws InterruptedException {
        sanitarioObserva.acquire();
        s.setPuesto(puestos.get(0));
        puestos.remove(0);
        reaccion = false;
        Thread.sleep((int) (Math.random() * (5000 - 2000 + 1) + 2000));
        et.reaccionPaciente(s, puestoPaciente[s.getPuesto()]);
        System.out.println("El sanitario " + s.getIdentificador() + " esta atendiendo al paciente " + puestoPaciente[s.getPuesto()].getIdentificador());
        pacienteReacciona.release();
        
    }

    /**
    * El método reacción cumple dos funciones: por una parte ejecuta el descanso del
    * paciente que tiene que hacer obligatoriamente en la sala de descanso, y por 
    * otra parte saca una probabilidad de que al paciente le de una reacción a la vacuna,
    * llamándo así a un sanitario para que le atienda. 
    */
    public void reaccion(Paciente p) throws InterruptedException {

        Thread.sleep(10000);
        int probabilidad = (int) ((Math.random() * (100 - 1 + 1) + 1));

        if (probabilidad > 0 && probabilidad < 90) {
            reaccion = true;
            System.out.println("El paciente " + p.getIdentificador() + " está sufriendo una reacción a la vacuna");
            puestos.add(p.getPuesto());
            sanitarioObserva.release();
            pacienteReacciona.acquire();
            
            
        }
    }

    /**
    * Devuelve el boolean reaccion, el cual indica si el paciente sufre o no reacción. 
    */
    public boolean isReaccion() {
        return reaccion;
    }

    /**
    * Establece el boolean reaccion, el cual indica si el paciente sufre o no reacción. 
    */
    public void setReaccion(boolean reaccion) {
        this.reaccion = reaccion;
    }

    /**
    * El método puestoPaciente se encarga de introducir a los pacientes en un puesto 
    * de los disponibles. A todos se les pasa el método reacción para que hagan el 
    * descanso y compruebe si tiene o no reacción. 
    */
    public void puesto(Paciente p) throws InterruptedException, BrokenBarrierException {
        int eleccion = p.getPuesto();
        switch (eleccion) {

            case 0:
                puesto1Txt.setText(puesto1Txt.getText() + " " + p.getIdentificador());
                reaccion(p);

                puesto1Txt.setText(" ");
                break;
            case 1:
                puesto2Txt.setText(puesto2Txt.getText() + " " + p.getIdentificador());
                reaccion(p);

                puesto2Txt.setText(" ");
                break;

            case 2:
                puesto3Txt.setText(puesto3Txt.getText() + " " + p.getIdentificador());
                reaccion(p);

                puesto3Txt.setText("");
                break;
            case 3:
                puesto4Txt.setText(puesto4Txt.getText() + " " + p.getIdentificador());
                reaccion(p);
                puesto4Txt.setText("");
                break;
            case 4:
                puesto5Txt.setText(puesto5Txt.getText() + " " + p.getIdentificador());
                reaccion(p);

                puesto5Txt.setText("");
                break;
            case 5:
                puesto6Txt.setText(puesto6Txt.getText() + " " + p.getIdentificador());
                reaccion(p);

                puesto6Txt.setText("");
                break;
            case 6:
                puesto7Txt.setText(puesto7Txt.getText() + " " + p.getIdentificador());
                reaccion(p);

                puesto7Txt.setText("");
                break;
            case 7:
                puesto8Txt.setText(puesto8Txt.getText() + " " + p.getIdentificador());
                reaccion(p);

                puesto8Txt.setText("");
                break;
            case 8:
                puesto9Txt.setText(puesto9Txt.getText() + " " + p.getIdentificador());
                reaccion(p);

                puesto9Txt.setText("");
                break;
            case 9:
                puesto10Txt.setText(puesto10Txt.getText() + " " + p.getIdentificador());
                reaccion(p);

                puesto10Txt.setText("");
                break;
            case 10:
                puesto11Txt.setText(puesto11Txt.getText() + " " + p.getIdentificador());

                reaccion(p);

                puesto11Txt.setText(" ");
                break;
            case 11:
                puesto12Txt.setText(puesto12Txt.getText() + " " + p.getIdentificador());
                reaccion(p);

                puesto12Txt.setText(" ");
                break;

            case 12:
                puesto13Txt.setText(puesto13Txt.getText() + " " + p.getIdentificador());
                reaccion(p);

                puesto13Txt.setText("");
                break;
            case 13:
                puesto14Txt.setText(puesto14Txt.getText() + " " + p.getIdentificador());
                reaccion(p);

                puesto14Txt.setText("");
                break;
            case 14:
                puesto15Txt.setText(puesto15Txt.getText() + " " + p.getIdentificador());
                reaccion(p);

                puesto15Txt.setText("");
                break;
            case 15:
                puesto16Txt.setText(puesto16Txt.getText() + " " + p.getIdentificador());
                reaccion(p);

                puesto16Txt.setText("");
                break;
            case 16:
                puesto17Txt.setText(puesto17Txt.getText() + " " + p.getIdentificador());
                reaccion(p);

                puesto17Txt.setText("");
                break;
            case 17:
                puesto18Txt.setText(puesto18Txt.getText() + " " + p.getIdentificador());
                reaccion(p);

                puesto18Txt.setText("");
                break;
            case 18:
                puesto19Txt.setText(puesto19Txt.getText() + " " + p.getIdentificador());
                reaccion(p);

                puesto19Txt.setText("");
                break;
            case 19:
                puesto20Txt.setText(puesto20Txt.getText() + " " + p.getIdentificador());
                reaccion(p);

                puesto20Txt.setText("");
                break;
        }
    }
}
