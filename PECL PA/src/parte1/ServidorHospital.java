
package parte1;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * El hilo ServidorHospital tiene como atributos la clase Hospital, un servidor
 * , una conexion , una entrada y una salida.
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

    /**
     * El método run de esta clase nos permitirá manejar los sockets implementados para 
     * llamar al caso necesario dependiendo del momento
     */
    @Override
    public void run() {
        try {
            while (true) {
                conexion = servidor.accept(); //Esperamos una conexion
                entrada = new DataInputStream(conexion.getInputStream());// Abrimos canales de E/S
                salida = new DataOutputStream(conexion.getOutputStream());

                int numero = entrada.readInt();//Leemos el numero del switch.Cada numero corresponde a una de las salas pertenecientes al hospital. 
                switch (numero) {
                    case 1 -> {
                        salida.writeUTF(hospital.getRecepcion().getColaEspera());//Le respondemos 
                        salida.writeUTF(hospital.getRecepcion().getPacienteTxt());
                        salida.writeUTF(hospital.getRecepcion().getAuxiliarTxt());
                    }
                    case 2 -> {
                        salida.writeUTF(hospital.getSalaDescanso().getColaDescansoTxtSan());
                        salida.writeUTF(hospital.getSalaDescanso().getColaDescansoTxtAux());
                    }
                    case 3 -> {
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
                    }
                    case 4 -> {
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
                    }
                    case 5 -> hospital.getSalaVacunacion().cerrarPuesto1();
                    case 6 -> hospital.getSalaVacunacion().cerrarPuesto2();
                    case 7 -> hospital.getSalaVacunacion().cerrarPuesto3();
                    case 8 -> hospital.getSalaVacunacion().cerrarPuesto4();
                    case 9 -> hospital.getSalaVacunacion().cerrarPuesto5();
                    case 10 -> hospital.getSalaVacunacion().cerrarPuesto6();
                    case 11 -> hospital.getSalaVacunacion().cerrarPuesto7();
                    case 12 -> hospital.getSalaVacunacion().cerrarPuesto8();
                    case 13 -> hospital.getSalaVacunacion().cerrarPuesto9();
                    case 14 -> hospital.getSalaVacunacion().cerrarPuesto10();

                }
                //Recepcion
                //Sala Descanso
                //Sala Vacunacion
                //Sala de Observacion
                entrada.close();//cerramos los canales de E/S
                salida.close();
                conexion.close();// cierra la conexion
            }

        } catch (IOException ex) {
            Logger.getLogger(ServidorHospital.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
