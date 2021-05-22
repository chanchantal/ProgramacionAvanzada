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
 *El hilo ServidorHospital tiene como atributos la clase Hospital, un servidor , una conexion , una entrada y una salida.
 */
public class ServidorHospital extends Thread {

    private Hospital hospital;
    ServerSocket servidor;
    Socket conexion;
    DataOutputStream salida;
    DataInputStream entrada;

    public ServidorHospital(Hospital hospital) {
        this.hospital = hospital;

        try {
            servidor = new ServerSocket(30000); // creamos un ServerSocket con puerto 30000

        } catch (IOException ex) {
            Logger.getLogger(ServidorHospital.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    /*
  
    */
    public void run() {
        try {
            while (true) {
                conexion = servidor.accept(); //Esperamos una conexion
                entrada = new DataInputStream(conexion.getInputStream());// Abrimos canales de E/S
                salida = new DataOutputStream(conexion.getOutputStream());

                int numero = entrada.readInt();//Leemos el numero del switch.Cada numero corresponde a una de las salas pertenecientes al hospital. 
                switch (numero) {
                    //Recepcion
                    case 1:
                        salida.writeUTF(hospital.getRecepcion().getColaEspera());//Le respondemos 
                        salida.writeUTF(hospital.getRecepcion().getPacienteTxt());
                        salida.writeUTF(hospital.getRecepcion().getAuxiliarTxt());
                        break;
                    //Sala Descanso
                    case 2:
                        salida.writeUTF(hospital.getSalaDescanso().getColaDescansoTxtSan());
                        salida.writeUTF(hospital.getSalaDescanso().getColaDescansoTxtAux());
                        break;
                    //Sala Vacunacion
                    case 3:
                        salida.writeUTF(hospital.getSalaVacunacion().getAuxiliarTxt());
                        salida.writeInt(hospital.getSalaVacunacion().VacunasDisponibles());

                        salida.writeUTF(hospital.getSalaVacunacion().getPuesto1Txt());
                        salida.writeUTF(hospital.getSalaVacunacion().getPuesto2Txt());
                        salida.writeUTF(hospital.getSalaVacunacion().getPuesto3Txt());
                        salida.writeUTF(hospital.getSalaVacunacion().getPuesto4Txt());
                        salida.writeUTF(hospital.getSalaVacunacion().getPuesto5Txt());
                        salida.writeUTF(hospital.getSalaVacunacion().getPuesto6Txt());
                        salida.writeUTF(hospital.getSalaVacunacion().getPuesto7Txt());
                        salida.writeUTF(hospital.getSalaVacunacion().getPuesto8Txt());
                        salida.writeUTF(hospital.getSalaVacunacion().getPuesto9Txt());
                        salida.writeUTF(hospital.getSalaVacunacion().getPuesto10Txt());
                        break;
                     //Sala de Observacion
                    case 4:

                        salida.writeUTF(hospital.getSalaObservacion().getPuesto1Txt());
                        salida.writeUTF(hospital.getSalaObservacion().getPuesto2Txt());
                        salida.writeUTF(hospital.getSalaObservacion().getPuesto3Txt());
                        salida.writeUTF(hospital.getSalaObservacion().getPuesto4Txt());
                        salida.writeUTF(hospital.getSalaObservacion().getPuesto5Txt());
                        salida.writeUTF(hospital.getSalaObservacion().getPuesto6Txt());
                        salida.writeUTF(hospital.getSalaObservacion().getPuesto7Txt());
                        salida.writeUTF(hospital.getSalaObservacion().getPuesto8Txt());
                        salida.writeUTF(hospital.getSalaObservacion().getPuesto9Txt());
                        salida.writeUTF(hospital.getSalaObservacion().getPuesto10Txt());
                        salida.writeUTF(hospital.getSalaObservacion().getPuesto11Txt());
                        salida.writeUTF(hospital.getSalaObservacion().getPuesto12Txt());
                        salida.writeUTF(hospital.getSalaObservacion().getPuesto13Txt());
                        salida.writeUTF(hospital.getSalaObservacion().getPuesto14Txt());
                        salida.writeUTF(hospital.getSalaObservacion().getPuesto15Txt());
                        salida.writeUTF(hospital.getSalaObservacion().getPuesto16Txt());
                        salida.writeUTF(hospital.getSalaObservacion().getPuesto17Txt());
                        salida.writeUTF(hospital.getSalaObservacion().getPuesto18Txt());
                        salida.writeUTF(hospital.getSalaObservacion().getPuesto19Txt());
                        salida.writeUTF(hospital.getSalaObservacion().getPuesto20Txt());

                        break;

                }
                entrada.close();//cerramos los canales de E/S
                salida.close();
                conexion.close();// cierra la conexion
            }

        } catch (IOException ex) {
            Logger.getLogger(ServidorHospital.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
