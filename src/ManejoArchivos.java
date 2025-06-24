import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class ManejoArchivos {
    private static final String NOMBRE_ARCHIVO = "datos_carrera.txt";
    private static final String SEPARADOR = "|";

    public static void guardarDatos(String[][][] participantes, double[][] tiempos,
                                    int[] posiciones, int totalParticipantes) throws IOException {

        if (participantes == null || tiempos == null || posiciones == null) {
            throw new IllegalArgumentException("Los datos no pueden ser nulos");
        }

        FileWriter archivo = null;
        BufferedWriter escritor = null;

        try {
            archivo = new FileWriter(NOMBRE_ARCHIVO);
            escritor = new BufferedWriter(archivo);

            // Escribir encabezado
            escritor.write("DATOS_SISTEMA_CARRERAS");
            escritor.newLine();
            escritor.write("TOTAL_PARTICIPANTES:" + totalParticipantes);
            escritor.newLine();
            escritor.write("FECHA_GUARDADO:" + java.time.LocalDateTime.now());
            escritor.newLine();
            escritor.write("---INICIO_DATOS---");
            escritor.newLine();

            // Guardar datos de cada participante
            for (int i = 0; i < totalParticipantes; i++) {
                // Datos del participante (arreglo 3D)
                StringBuilder lineaParticipante = new StringBuilder();
                lineaParticipante.append("PARTICIPANTE:").append(i);

                // Datos personales
                lineaParticipante.append(SEPARADOR).append("PERSONAL:");
                for (int j = 0; j < 3; j++) {
                    lineaParticipante.append(participantes[i][0][j]);
                    if (j < 2) lineaParticipante.append(",");
                }

                // Datos del vehículo
                lineaParticipante.append(SEPARADOR).append("VEHICULO:");
                for (int j = 0; j < 3; j++) {
                    lineaParticipante.append(participantes[i][1][j]);
                    if (j < 2) lineaParticipante.append(",");
                }

                // Datos del patrocinador
                lineaParticipante.append(SEPARADOR).append("PATROCINADOR:");
                for (int j = 0; j < 3; j++) {
                    lineaParticipante.append(participantes[i][2][j]);
                    if (j < 2) lineaParticipante.append(",");
                }

                // Tiempos (arreglo 2D)
                lineaParticipante.append(SEPARADOR).append("TIEMPOS:");
                for (int j = 0; j < 4; j++) {
                    lineaParticipante.append(tiempos[i][j]);
                    if (j < 3) lineaParticipante.append(",");
                }

                // Posición final
                lineaParticipante.append(SEPARADOR).append("POSICION:").append(posiciones[i]);

                escritor.write(lineaParticipante.toString());
                escritor.newLine();
            }

            escritor.write("---FIN_DATOS---");
            escritor.newLine();

        } finally {
            if (escritor != null) {
                try {
                    escritor.close();
                } catch (IOException e) {
                    System.err.println("Error al cerrar el escritor: " + e.getMessage());
                }
            }
            if (archivo != null) {
                try {
                    archivo.close();
                } catch (IOException e) {
                    System.err.println("Error al cerrar el archivo: " + e.getMessage());
                }
            }
        }
    }
}
