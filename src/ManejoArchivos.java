import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ManejoArchivos {
    private static final String NOMBRE_ARCHIVO = "resultados_carrera.txt";

    public static void guardarDatos(String[][][] participantes, double[][] tiempos,
                                    int[] posiciones, int totalParticipantes) throws IOException {

        if (participantes == null || tiempos == null || posiciones == null) {
            throw new IllegalArgumentException("Los datos no pueden ser nulos");
        }

        FileWriter archivo = null;
        BufferedWriter escritor = null;

        try {
            String nombreArchivo = generarNombreArchivo();
            archivo = new FileWriter(nombreArchivo);
            escritor = new BufferedWriter(archivo);
            escritor.write("                   |             Inicio del programa                 |");
            escritor.newLine();
            escritor.write("Posición           |              Piloto/Tiempo/Estado                    |    Vehículo/Patrocinador");
            escritor.newLine();

            int[] indicesOrdenados = new int[totalParticipantes];
            for(int i = 0; i < totalParticipantes; i++) {
                for(int j = 0; j < totalParticipantes; j++) {
                    if(posiciones[j] == i + 1) {
                        indicesOrdenados[i] = j;
                        break;
                    }
                }
            }

            for(int i = 0; i < totalParticipantes; i++) {
                int idx = indicesOrdenados[i];

                if(tiempos[idx][3] > 0) {
                    String posicion = String.format("%dº lugar        ", posiciones[idx]);
                    String piloto = participantes[idx][0][0];
                    double tiempoTotal = tiempos[idx][3];
                    String estado = (tiempoTotal < 180.0) ? "Excelente" : (tiempoTotal < 240.0) ? "Bueno" : "Regular";
                    String vehiculo = participantes[idx][1][0];
                    String patrocinador = participantes[idx][2][0];

                    String linea = String.format("%s      %10s/%.2f/%s     >>%10s/%s",
                            posicion, piloto, tiempoTotal, estado, vehiculo, patrocinador);

                    escritor.write(linea);
                    escritor.newLine();
                }
            }

            escritor.write("                   |             Fin del programa                 |");
            escritor.newLine();

            System.out.println("Archivo guardado como: " + nombreArchivo);

        } finally {
            if (escritor != null) {
                try {
                    escritor.close();
                } catch (IOException e) {
                    System.err.println("Error al cerrar el escritor: " + e.getMessage());
                    errorLog.logError("Error al cerrar el escritor: " + e.getMessage());
                }
            }
            if (archivo != null) {
                try {
                    archivo.close();
                } catch (IOException e) {
                    System.err.println("Error al cerrar el archivo: " + e.getMessage());
                    errorLog.logError("Error al cerrar el archivo: " + e.getMessage());
                }
            }
        }
    }

    private static String generarNombreArchivo() {
        LocalDateTime fechaActual = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH_mm_ss.SSSSSS");
        int numeroSerial = (int) (Math.random() * 100) + 1;

        return String.format("resultadosCarrera_%s_Serial%d.txt",
                fechaActual.format(formatter), numeroSerial);
    }
}
