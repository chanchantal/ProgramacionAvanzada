package parte1;

import java.io.*;

public class EscrituraTexto {

    FileWriter fichero;
    PrintWriter pw;

    public EscrituraTexto() throws IOException {
        fichero = new FileWriter("prueba.txt");
        pw = new PrintWriter(fichero);
    }

    public void auxiliarRegistra(Auxiliar a, Paciente p) {
        pw.print("El auxiliar " + a.getIdentificador() + "ha registrado al paciente " + p.getIdentificador());
    }
    
}
