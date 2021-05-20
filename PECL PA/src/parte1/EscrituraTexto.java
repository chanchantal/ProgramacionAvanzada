package parte1;

import java.io.*;
import java.time.LocalDateTime;

public class EscrituraTexto {

    FileWriter fichero;
    PrintWriter pw;

    public EscrituraTexto() throws IOException {
        FileWriter fw = new FileWriter("FuncionamientoHospital.txt");
    }

    public void auxiliarRegistra(Auxiliar a, Paciente p) throws IOException {
        try ( FileWriter fw = new FileWriter("FuncionamientoHospital.txt", true);  BufferedWriter bw = new BufferedWriter(fw);  PrintWriter out = new PrintWriter(bw)) {
            out.println(LocalDateTime.now() + ": El auxiliar " + a.getIdentificador() + " ha registrado al paciente " + p.getIdentificador());
        } catch (IOException e) {
        }
    }

    public void vacunacion(Sanitario s, Paciente p, int puesto) {
        try ( FileWriter fw = new FileWriter("FuncionamientoHospital.txt", true);  BufferedWriter bw = new BufferedWriter(fw);  PrintWriter out = new PrintWriter(bw)) {
            out.println(LocalDateTime.now() + ": El sanitario " + s.getIdentificador() + " ha vacunado al paciente " + p.getIdentificador() + " en el puesto " + puesto);
        } catch (IOException e) {
        }
    }

    public void inicioDescansoS(Sanitario s) {
        try ( FileWriter fw = new FileWriter("FuncionamientoHospital.txt", true);  BufferedWriter bw = new BufferedWriter(fw);  PrintWriter out = new PrintWriter(bw)) {
            out.println(LocalDateTime.now() + ": El sanitario " + s.getIdentificador() + " ha iniciado su descanso");
        } catch (IOException e) {
        }
    }

    public void finDescansoS(Sanitario s) {
        try ( FileWriter fw = new FileWriter("FuncionamientoHospital.txt", true);  BufferedWriter bw = new BufferedWriter(fw);  PrintWriter out = new PrintWriter(bw)) {
            out.println(LocalDateTime.now() + ": El sanitario " + s.getIdentificador() + " ha acabado su descanso");
        } catch (IOException e) {
        }
    }

    public void inicioDescansoA(Auxiliar a) {
        try ( FileWriter fw = new FileWriter("FuncionamientoHospital.txt", true);  BufferedWriter bw = new BufferedWriter(fw);  PrintWriter out = new PrintWriter(bw)) {
            out.println(LocalDateTime.now() + ": El auxiliar " + a.getIdentificador() + " ha iniciado su descanso");
        } catch (IOException e) {
        }
    }

    public void finDescansoA(Auxiliar a) {
        try ( FileWriter fw = new FileWriter("FuncionamientoHospital.txt", true);  BufferedWriter bw = new BufferedWriter(fw);  PrintWriter out = new PrintWriter(bw)) {
            out.println(LocalDateTime.now() + ": El auxiliar " + a.getIdentificador() + " ha acabado su descanso");
        } catch (IOException e) {
        }
    }

    public void reaccionPaciente(Sanitario s, Paciente p) {
        try ( FileWriter fw = new FileWriter("FuncionamientoHospital.txt", true);  BufferedWriter bw = new BufferedWriter(fw);  PrintWriter out = new PrintWriter(bw)) {
            out.println(LocalDateTime.now() + ": El paciente " + p.getIdentificador() + " ha sufrido una reacción a la vacuna, y el sanitario " + s.getIdentificador() + " le ha atendido");
        } catch (IOException e) {
        }
    }
    
    public void pacienteSinCita(Paciente p) {
        try ( FileWriter fw = new FileWriter("FuncionamientoHospital.txt", true);  BufferedWriter bw = new BufferedWriter(fw);  PrintWriter out = new PrintWriter(bw)) {
            out.println(LocalDateTime.now() + " El paciente " + p.getIdentificador() + " ha acudido sin cita y se tiene que ir");
        } catch (IOException e) {
        }
    }
}
