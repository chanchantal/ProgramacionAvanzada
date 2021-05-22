package parte1;

import java.io.*;
import java.time.LocalDateTime;
/*
*En la clase EscrituratTexto crearemos el fichero log en donde guardaremos todos los eventos que van teniendo lugar al ejecutar la 
*aplicacion.
*/
public class EscrituraTexto {

    FileWriter fichero;
    PrintWriter pw;

    public EscrituraTexto() throws IOException {
        FileWriter fw = new FileWriter("FuncionamientoHospital.txt");// creamos el fichero txt
    }
    /*
    *El metodo auxiliarRegistra,añade en el txt al auxiliar que ha registrado al paciente, cada uno de ellos con su correpondiente
    *identificadores junto con la fecha en la que se realizo dicho registro.   
    */
    public void auxiliarRegistra(Auxiliar a, Paciente p) throws IOException {
        try ( FileWriter fw = new FileWriter("FuncionamientoHospital.txt", true);  BufferedWriter bw = new BufferedWriter(fw);  PrintWriter out = new PrintWriter(bw)) {
            out.println(LocalDateTime.now() + ": El auxiliar " + a.getIdentificador() + " ha registrado al paciente " + p.getIdentificador());
        } catch (IOException e) {
        }
    }
    /*
    *El metodo vacunacion añade al txt al sanitario que ha vacunado a un paciente y el puesto en el que se realizo dicha accion.
    *
    */
    public void vacunacion(Sanitario s, Paciente p, int puesto) {
        try ( FileWriter fw = new FileWriter("FuncionamientoHospital.txt", true);  BufferedWriter bw = new BufferedWriter(fw);  PrintWriter out = new PrintWriter(bw)) {
            out.println(LocalDateTime.now() + ": El sanitario " + s.getIdentificador() + " ha vacunado al paciente " + p.getIdentificador() + " en el puesto " + puesto);
        } catch (IOException e) {
        }
    }
    /*
    *El metodo inicioDescansoS añade al txt al sanitario que esta empezando su descanso.
    */
    public void inicioDescansoS(Sanitario s) {
        try ( FileWriter fw = new FileWriter("FuncionamientoHospital.txt", true);  BufferedWriter bw = new BufferedWriter(fw);  PrintWriter out = new PrintWriter(bw)) {
            out.println(LocalDateTime.now() + ": El sanitario " + s.getIdentificador() + " ha iniciado su descanso");
        } catch (IOException e) {
        }
    }
    /*
    *El metodo finDescansoS añade el txt al sanitario que acaba su descanso.
    */
    public void finDescansoS(Sanitario s) {
        try ( FileWriter fw = new FileWriter("FuncionamientoHospital.txt", true);  BufferedWriter bw = new BufferedWriter(fw);  PrintWriter out = new PrintWriter(bw)) {
            out.println(LocalDateTime.now() + ": El sanitario " + s.getIdentificador() + " ha acabado su descanso");
        } catch (IOException e) {
        }
    }
    /*
    *El metodo inicioDescansoA añade al txt al auxiliar que esta empezando su descanso.
    */
    public void inicioDescansoA(Auxiliar a) {
        try ( FileWriter fw = new FileWriter("FuncionamientoHospital.txt", true);  BufferedWriter bw = new BufferedWriter(fw);  PrintWriter out = new PrintWriter(bw)) {
            out.println(LocalDateTime.now() + ": El auxiliar " + a.getIdentificador() + " ha iniciado su descanso");
        } catch (IOException e) {
        }
    }
    /*
    *El metodo finDescansoA añade el txt al auxiliar que acaba su descanso.
    */
    public void finDescansoA(Auxiliar a) {
        try ( FileWriter fw = new FileWriter("FuncionamientoHospital.txt", true);  BufferedWriter bw = new BufferedWriter(fw);  PrintWriter out = new PrintWriter(bw)) {
            out.println(LocalDateTime.now() + ": El auxiliar " + a.getIdentificador() + " ha acabado su descanso");
        } catch (IOException e) {
        }
    }
    /*
    *El metodo reaccionPaciente guarda en el txt al paciente que ha sufrido una reaccion a la vacuna y al sanitario que atendera a 
    *dicho paciente.
    */
    public void reaccionPaciente(Sanitario s, Paciente p) {
        try ( FileWriter fw = new FileWriter("FuncionamientoHospital.txt", true);  BufferedWriter bw = new BufferedWriter(fw);  PrintWriter out = new PrintWriter(bw)) {
            out.println(LocalDateTime.now() + ": El paciente " + p.getIdentificador() + " ha sufrido una reacción a la vacuna, y el sanitario " + s.getIdentificador() + " le ha atendido");
        } catch (IOException e) {
        }
    }
    /*
    *El metodo pacienteSinCita añade al txt al paciente que acudio al hospital sin cita.
    */
    public void pacienteSinCita(Paciente p) {
        try ( FileWriter fw = new FileWriter("FuncionamientoHospital.txt", true);  BufferedWriter bw = new BufferedWriter(fw);  PrintWriter out = new PrintWriter(bw)) {
            out.println(LocalDateTime.now() + " El paciente " + p.getIdentificador() + " ha acudido sin cita y se tiene que ir");
        } catch (IOException e) {
        }
    }
}
