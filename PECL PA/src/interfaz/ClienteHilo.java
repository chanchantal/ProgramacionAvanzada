/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package interfaz;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.util.concurrent.BrokenBarrierException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JTextField;
import parte1.Sanitario;

/**
 *
 * @author Usuario
 */
public class ClienteHilo extends Thread {
    public JTextField colaEspera;

    public ClienteHilo(JTextField colaEspera) {
        this.colaEspera = colaEspera;
    }
    
    
    @Override
    public void run() {
        System.out.println("che inisia");
        while(true){
            try {

                imprimirColaEspera();

                sleep(1000);
            } catch (InterruptedException ex) {
                Logger.getLogger(ClienteHilo.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    public void imprimirColaEspera(){
        Socket servi;
        DataInputStream entrada;
        DataOutputStream salida;
        String texto = " ";
        

        try {
            servi = new Socket(InetAddress.getLocalHost(), 30000);
            entrada = new DataInputStream(servi.getInputStream());
            salida = new DataOutputStream(servi.getOutputStream());

            salida.writeInt(1);

            texto = entrada.readUTF();
            System.out.println("entro bien");
            colaEspera.setText(texto);


            entrada.close(); //Cerramos los flujos de entrada y salida
            salida.close();
            servi.close(); //Cerramos la conexión

        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
    
}
