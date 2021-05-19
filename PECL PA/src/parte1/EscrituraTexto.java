package parte1;

import java.io.*;

public class EscrituraTexto {

    FileWriter fichero;
    PrintWriter pw;

    public EscrituraTexto() throws IOException {
       
    }

    public void auxiliarRegistra(Auxiliar a, Paciente p) throws IOException {
        try ( FileWriter fw = new FileWriter("FuncionamientoHospital.txt", true);  BufferedWriter bw = new BufferedWriter(fw);  PrintWriter out = new PrintWriter(bw)) {
            out.println("El auxiliar " + a.getIdentificador() + " ha registrado al paciente " + p.getIdentificador());

        } catch (IOException e) {
            //exception handling left as an exercise for the reader
        }
    }
    public void vacunacion(Paciente p, Sanitario s){
        try ( FileWriter fw = new FileWriter("FuncionamientoHospital.txt", true);  BufferedWriter bw = new BufferedWriter(fw);  PrintWriter out = new PrintWriter(bw)) {
            out.println("El sanitario " + s.getIdentificador() + " ha vacunado al paciente " + p.getIdentificador());

        } catch (IOException e) {
            //exception handling left as an exercise for the reader
        }        
    }
    
    public void inicioDescansoSanitario(Sanitario s){
        try ( FileWriter fw = new FileWriter("FuncionamientoHospital.txt", true);  BufferedWriter bw = new BufferedWriter(fw);  PrintWriter out = new PrintWriter(bw)) {
            out.println("El sanitario " + s.getIdentificador() + " ha comenzado su descanso");

        } catch (IOException e) {
            //exception handling left as an exercise for the reader
        }       
    }
    
    public void pasoPaciente(Paciente p, Auxiliar a, Sanitario s){
        System.out.println("El sanitario S ha vacunado al paciente P en el puesto Puesto");
        System.out.println("El paciente P ha acudido sin cita");
        System.out.println("El paciente P ha sufrido una reaccion y le ha atendido el sanitario S");
        System.out.println("A/S comienza descanso");
        System.out.println("A/S termina el descanso");
    }
}
