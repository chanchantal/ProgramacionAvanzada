
package interfaz;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JTextField;


/**
 * La clase ClienteHilo nos permitirá manejarnos como clientes en función del servidor
 */
public class ClienteHilo extends Thread {

    public JTextField colaEspera;
    public JTextField pacienteTxt;
    public JTextField auxiliarTxt1;
    public JTextField colaDescansoTxtSan;
    public JTextField colaDescansoTxtAux;
    public JTextField auxiliarTxt2;
    public JTextField vacunasDisponibles;
    public JTextField puesto1V;
    public JTextField puesto2V;
    public JTextField puesto3V;
    public JTextField puesto4V;
    public JTextField puesto5V;
    public JTextField puesto6V;
    public JTextField puesto7V;
    public JTextField puesto8V;
    public JTextField puesto9V;
    public JTextField puesto10V;

    public JTextField puesto1O;
    public JTextField puesto2O;
    public JTextField puesto3O;
    public JTextField puesto4O;
    public JTextField puesto5O;
    public JTextField puesto6O;
    public JTextField puesto7O;
    public JTextField puesto8O;
    public JTextField puesto9O;
    public JTextField puesto10O;
    public JTextField puesto11O;
    public JTextField puesto12O;
    public JTextField puesto13O;
    public JTextField puesto14O;
    public JTextField puesto15O;
    public JTextField puesto16O;
    public JTextField puesto17O;
    public JTextField puesto18O;
    public JTextField puesto19O;
    public JTextField puesto20O;

    public ClienteHilo(JTextField colaEspera, JTextField pacienteTxt, JTextField auxiliarTxt1, JTextField colaDescansoTxtSan, JTextField colaDescansoTxtAux, JTextField auxiliarTxt2, JTextField vacunasDisponibles, JTextField puesto1V, JTextField puesto2V, JTextField puesto3V, JTextField puesto4V, JTextField puesto5V, JTextField puesto6V, JTextField puesto7V, JTextField puesto8V, JTextField puesto9V, JTextField puesto10V, JTextField puesto1O, JTextField puesto2O, JTextField puesto3O, JTextField puesto4O, JTextField puesto5O, JTextField puesto6O, JTextField puesto7O, JTextField puesto8O, JTextField puesto9O, JTextField puesto10O, JTextField puesto11O, JTextField puesto12O, JTextField puesto13O, JTextField puesto14O, JTextField puesto15O, JTextField puesto16O, JTextField puesto17O, JTextField puesto18O, JTextField puesto19O, JTextField puesto20O) {
        this.colaEspera = colaEspera;
        this.pacienteTxt = pacienteTxt;
        this.auxiliarTxt1 = auxiliarTxt1;
        this.colaDescansoTxtSan = colaDescansoTxtSan;
        this.colaDescansoTxtAux = colaDescansoTxtAux;
        this.auxiliarTxt2 = auxiliarTxt2;
        this.vacunasDisponibles = vacunasDisponibles;
        this.puesto1V = puesto1V;
        this.puesto2V = puesto2V;
        this.puesto3V = puesto3V;
        this.puesto4V = puesto4V;
        this.puesto5V = puesto5V;
        this.puesto6V = puesto6V;
        this.puesto7V = puesto7V;
        this.puesto8V = puesto8V;
        this.puesto9V = puesto9V;
        this.puesto10V = puesto10V;
        this.puesto1O = puesto1O;
        this.puesto2O = puesto2O;
        this.puesto3O = puesto3O;
        this.puesto4O = puesto4O;
        this.puesto5O = puesto5O;
        this.puesto6O = puesto6O;
        this.puesto7O = puesto7O;
        this.puesto8O = puesto8O;
        this.puesto9O = puesto9O;
        this.puesto10O = puesto10O;
        this.puesto11O = puesto11O;
        this.puesto12O = puesto12O;
        this.puesto13O = puesto13O;
        this.puesto14O = puesto14O;
        this.puesto15O = puesto15O;
        this.puesto16O = puesto16O;
        this.puesto17O = puesto17O;
        this.puesto18O = puesto18O;
        this.puesto19O = puesto19O;
        this.puesto20O = puesto20O;
    }

    /**
     * El método run llama a los demás métodos implementados en este cliente y le 
     * asigna el tiempo de 1 segundo que se pide en el enunciado
     */
    @Override
    public void run() {
        while (true) {
            try {

                imprimirColaEspera();
                imprimirColaDescanso();
                imprimirPuestosVacunacion();
                imprimirPuestosObservacion();
                sleep(1000);
            } catch (InterruptedException ex) {
                Logger.getLogger(ClienteHilo.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(ClienteHilo.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    /**
     * imprimirColaEspera será el método encargado de imprimir todo lo relacionado 
     * con la recepción del programa. 
     */
    public void imprimirColaEspera() {
        Socket servi;
        DataInputStream entrada;
        DataOutputStream salida;
        String textoCola = " ";
        String textoPaciente = " ";
        String textoAuxiliar = " ";

        try {
            servi = new Socket(InetAddress.getLocalHost(), 30000);
            entrada = new DataInputStream(servi.getInputStream());
            salida = new DataOutputStream(servi.getOutputStream());

            salida.writeInt(1);

            textoCola = entrada.readUTF();
           
            colaEspera.setText(textoCola);
            textoPaciente = entrada.readUTF();
            pacienteTxt.setText(textoPaciente);
            textoAuxiliar = entrada.readUTF();
            auxiliarTxt1.setText(textoAuxiliar);

            entrada.close(); //Cerramos los flujos de entrada y salida
            salida.close();
            servi.close(); //Cerramos la conexión

        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    /**
     * imprimirColaDescanso será el método encargado de imprimir todo lo relacionado
     * con la clase SalaDescanso
     * @throws IOException 
     */
    private void imprimirColaDescanso() throws IOException {
        Socket servi;
        DataInputStream entrada;
        DataOutputStream salida;
        String textoColaDesAux = " ";
        String textoColaDesSan = " ";
        try {
            servi = new Socket(InetAddress.getLocalHost(), 30000);
            entrada = new DataInputStream(servi.getInputStream());
            salida = new DataOutputStream(servi.getOutputStream());
            salida.writeInt(2);

            textoColaDesAux = entrada.readUTF();
            colaDescansoTxtAux.setText(textoColaDesAux);
            textoColaDesSan = entrada.readUTF();
            colaDescansoTxtSan.setText(textoColaDesSan);

            entrada.close(); //Cerramos los flujos de entrada y salida
            salida.close();
            servi.close(); //Cerramos la conexión

        } catch (UnknownHostException ex) {
            Logger.getLogger(ClienteHilo.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * imprimirPuestosVacunación será el método encargado de imprimir todo lo
     * relacionado con la clase SalaVacunación
     * @throws IOException 
     */
    private void imprimirPuestosVacunacion() throws IOException {
        Socket servi;
        DataInputStream entrada;
        DataOutputStream salida;
        String textoAuxiliar2 = " ";
        int vacunas;
        String puesto1 = " ";
        String puesto2 = " ";
        String puesto3 = " ";
        String puesto4 = " ";
        String puesto5 = " ";
        String puesto6 = " ";
        String puesto7 = " ";
        String puesto8 = " ";
        String puesto9 = " ";
        String puesto10 = " ";

        try {
            servi = new Socket(InetAddress.getLocalHost(), 30000);
            entrada = new DataInputStream(servi.getInputStream());
            salida = new DataOutputStream(servi.getOutputStream());
            salida.writeInt(3);

            textoAuxiliar2 = entrada.readUTF();
            auxiliarTxt2.setText(textoAuxiliar2);
            vacunas = entrada.readInt();
            vacunasDisponibles.setText(" " + vacunas);

            puesto1 = entrada.readUTF();
            puesto1V.setText(puesto1);
            puesto2 = entrada.readUTF();
            puesto2V.setText(puesto2);
            puesto3 = entrada.readUTF();
            puesto3V.setText(puesto3);
            puesto4 = entrada.readUTF();
            puesto4V.setText(puesto4);
            puesto5 = entrada.readUTF();
            puesto5V.setText(puesto5);
            puesto6 = entrada.readUTF();
            puesto6V.setText(puesto6);
            puesto7 = entrada.readUTF();
            puesto7V.setText(puesto7);
            puesto8 = entrada.readUTF();
            puesto8V.setText(puesto8);
            puesto9 = entrada.readUTF();
            puesto9V.setText(puesto9);
            puesto10 = entrada.readUTF();
            puesto10V.setText(puesto10);
            entrada.close(); //Cerramos los flujos de entrada y salida
            salida.close();
            servi.close(); //Cerramos la conexión

        } catch (UnknownHostException ex) {
            Logger.getLogger(ClienteHilo.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * imprimirPuestosObservacion será el método encargado de imprimir todo lo
     * relacionado con la clase SalaObservación
     * @throws IOException 
     */
    private void imprimirPuestosObservacion() throws IOException {
        Socket servi;
        DataInputStream entrada;
        DataOutputStream salida;

        String puesto1 = " ";
        String puesto2 = " ";
        String puesto3 = " ";
        String puesto4 = " ";
        String puesto5 = " ";
        String puesto6 = " ";
        String puesto7 = " ";
        String puesto8 = " ";
        String puesto9 = " ";
        String puesto10 = " ";
        String puesto11 = " ";
        String puesto12 = " ";
        String puesto13 = " ";
        String puesto14 = " ";
        String puesto15 = " ";
        String puesto16 = " ";
        String puesto17 = " ";
        String puesto18 = " ";
        String puesto19 = " ";
        String puesto20 = " ";

        try {
            servi = new Socket(InetAddress.getLocalHost(), 30000);
            entrada = new DataInputStream(servi.getInputStream());
            salida = new DataOutputStream(servi.getOutputStream());
            salida.writeInt(4);

            puesto1 = entrada.readUTF();
            puesto1O.setText(puesto1);
            puesto2 = entrada.readUTF();
            puesto2O.setText(puesto2);
            puesto3 = entrada.readUTF();
            puesto3O.setText(puesto3);
            puesto4 = entrada.readUTF();
            puesto4O.setText(puesto4);
            puesto5 = entrada.readUTF();
            puesto5O.setText(puesto5);
            puesto6 = entrada.readUTF();
            puesto6O.setText(puesto6);
            puesto7 = entrada.readUTF();
            puesto7O.setText(puesto7);
            puesto8 = entrada.readUTF();
            puesto8O.setText(puesto8);
            puesto9 = entrada.readUTF();
            puesto9O.setText(puesto9);
            puesto10 = entrada.readUTF();
            puesto10O.setText(puesto10);

            puesto11 = entrada.readUTF();
            puesto11O.setText(puesto11);
            puesto12 = entrada.readUTF();
            puesto12O.setText(puesto12);
            puesto13 = entrada.readUTF();
            puesto13O.setText(puesto13);
            puesto14 = entrada.readUTF();
            puesto14O.setText(puesto14);
            puesto15 = entrada.readUTF();
            puesto15O.setText(puesto15);
            puesto16 = entrada.readUTF();
            puesto16O.setText(puesto16);
            puesto17 = entrada.readUTF();
            puesto17O.setText(puesto17);
            puesto18 = entrada.readUTF();
            puesto18O.setText(puesto18);
            puesto19 = entrada.readUTF();
            puesto19O.setText(puesto19);
            puesto20 = entrada.readUTF();
            puesto20O.setText(puesto20);
            entrada.close(); //Cerramos los flujos de entrada y salida
            salida.close();
            servi.close(); //Cerramos la conexión

        } catch (UnknownHostException ex) {
            Logger.getLogger(ClienteHilo.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
