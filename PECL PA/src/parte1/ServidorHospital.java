/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package parte1;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author ctati
 */
public class ServidorHospital extends Thread {
    private Hospital hospital;
    private Paciente paciente;
    private Sanitario sanitario;
    ServerSocket servidor;
    Socket conexion;
    DataOutputStream salida;
    DataInputStream entrada;

    public ServidorHospital(Hospital hospital){
        this.hospital = hospital;

        try{
            servidor = new ServerSocket(30000);
        
    }   catch (IOException ex) {
            Logger.getLogger(ServidorHospital.class.getName()).log(Level.SEVERE, null, ex);
    }
 }
    public void run (){
        try{
            while(true){
                conexion = servidor.accept(); // Esperamos una conexion
                entrada = new DataInputStream(conexion.getInputStream());
                salida = new DataOutputStream(conexion.getOutputStream());
                
                int numero = entrada.readInt();
                switch(numero){
                    // No esta completo
                    case 1:
                        salida.writeUTF(paciente.getIdentificador());
                        salida.writeUTF(sanitario.getIdentificador());
                    case 2:
                        salida.writeUTF(paciente.getIdentificador());
                        salida.writeUTF(sanitario.getIdentificador());
                    case 3:
                        salida.writeUTF(paciente.getIdentificador());
                        salida.writeUTF(sanitario.getIdentificador());
                    case 4:
                        salida.writeUTF(paciente.getIdentificador());
                        salida.writeUTF(sanitario.getIdentificador());
                    case 5:
                        salida.writeUTF(paciente.getIdentificador());
                        salida.writeUTF(sanitario.getIdentificador());
                    case 6:
                        salida.writeUTF(paciente.getIdentificador());
                        salida.writeUTF(sanitario.getIdentificador());
                    case 7:
                        salida.writeUTF(paciente.getIdentificador());
                        salida.writeUTF(sanitario.getIdentificador());
                    case 8:
                        salida.writeUTF(paciente.getIdentificador());
                        salida.writeUTF(sanitario.getIdentificador());
                    case 9:
                        salida.writeUTF(paciente.getIdentificador());
                        salida.writeUTF(sanitario.getIdentificador());
                    case 10:
                        salida.writeUTF(paciente.getIdentificador());
                        salida.writeUTF(sanitario.getIdentificador());
                }
                entrada.close();
                salida.close();
                conexion.close();
            }
            
        } catch (IOException ex) {
            Logger.getLogger(ServidorHospital.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
 
    
    
    
}
