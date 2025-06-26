import java.util.Scanner;

public class Process {

    // arreglo tridimensional: [participante][categoria][datos]
    private String[][][] participantesInfo;

    // arreglo bidimensional: [participante][tiempos]
    private double[][] tiemposCarrera;

    // arreglos para clasificación
    private int[] posicionesFinales;
    private boolean[] participanteActivo;

    private int totalParticipantes;
    private final int MAX_PARTICIPANTES = 50;

    public Process() {
        inicializarArreglos();
    }

    private void inicializarArreglos() {
        // inicialización arr tri
        participantesInfo = new String[MAX_PARTICIPANTES][3][3];
        for(int i = 0; i < MAX_PARTICIPANTES; i++) {
            for(int j = 0; j < 3; j++) {
                for(int k = 0; k < 3; k++) {
                    participantesInfo[i][j][k] = "";
                }
            }
        }

        // inicialización arr bi
        tiemposCarrera = new double[MAX_PARTICIPANTES][4];
        for(int i = 0; i < MAX_PARTICIPANTES; i++) {
            for(int j = 0; j < 4; j++) {
                tiemposCarrera[i][j] = 0.0;
            }
        }

        // inicializacion arr clasificacion
        posicionesFinales = new int[MAX_PARTICIPANTES];
        participanteActivo = new boolean[MAX_PARTICIPANTES];

        for(int i = 0; i < MAX_PARTICIPANTES; i++) {
            posicionesFinales[i] = 0;
            participanteActivo[i] = false;
        }

        totalParticipantes = 0;
        System.out.println("Arreglos inicializados correctamente.");
    }

    public void registrarParticipante(Scanner scanner) {
        System.out.println("\n=== REGISTRO DE PARTICIPANTE ===");

        if(totalParticipantes >= MAX_PARTICIPANTES) {
            System.out.println("Error: Máximo de participantes alcanzado.");
            return;
        }

        try {
            // datos personales
            String nombrePiloto = Validate.validarNombre(scanner,
                    "Ingrese nombre del piloto: ");
            int edadPiloto = Validate.validarEdad(scanner,
                    "Ingrese edad del piloto: ");
            int numeroParticipante = Validate.validarNumeroParticipante(scanner,
                    "Ingrese número de participante: ", participantesInfo, totalParticipantes);

            // datos del vehículo
            String marcaVehiculo = Validate.validarTexto(scanner,
                    "Ingrese marca del vehículo: ");
            int añoVehiculo = Validate.validarAño(scanner,
                    "Ingrese año del vehículo: ");

            // datos del patrocinador
            String nombrePatrocinador = Validate.validarTexto(scanner,
                    "Ingrese nombre del patrocinador: ");

            int indice = totalParticipantes;

            participantesInfo[indice][0][0] = nombrePiloto;
            participantesInfo[indice][0][1] = String.valueOf(edadPiloto);
            participantesInfo[indice][0][2] = String.valueOf(numeroParticipante);

            participantesInfo[indice][1][0] = marcaVehiculo;
            participantesInfo[indice][1][1] = String.valueOf(añoVehiculo);

            participantesInfo[indice][2][0] = nombrePatrocinador;
            participantesInfo[indice][2][1] = "";
            participantesInfo[indice][2][2] = "";

            participanteActivo[indice] = true;
            totalParticipantes++;

            System.out.println("¡Participante registrado exitosamente!");
            mostrarResumenParticipante(indice);

        } catch(Exception e) {
            System.out.println("Error al registrar participante: " + e.getMessage());
            errorLog.logError("Error al registrar participante: " + e.getMessage());
        }
    }

    public void registrarTiempo(Scanner scanner) {
        System.out.println("\n=== REGISTRO DE TIEMPOS ===");

        if(totalParticipantes == 0) {
            System.out.println("Error: No hay participantes registrados.");
            return;
        }

        mostrarParticipantesDisponibles();

        int numeroParticipante = Validate.validarEntero(scanner,
                "Ingrese número de participante: ");

        int indiceParticipante = buscarParticipantePorNumero(numeroParticipante);

        if(indiceParticipante == -1) {
            System.out.println("Error: Participante no encontrado.");
            return;
        }

        try {
            System.out.println("Registrando tiempos para: " +
                    participantesInfo[indiceParticipante][0][0]);

            double vuelta1 = Validate.validarTiempo(scanner, "Tiempo vuelta 1 (segundos): ");
            double vuelta2 = Validate.validarTiempo(scanner, "Tiempo vuelta 2 (segundos): ");
            double vuelta3 = Validate.validarTiempo(scanner, "Tiempo vuelta 3 (segundos): ");

            // calculo tiempo total
            double tiempoTotal = Calculos.calcularTiempoTotal(vuelta1, vuelta2, vuelta3);

            tiemposCarrera[indiceParticipante][0] = vuelta1;
            tiemposCarrera[indiceParticipante][1] = vuelta2;
            tiemposCarrera[indiceParticipante][2] = vuelta3;
            tiemposCarrera[indiceParticipante][3] = tiempoTotal;

            System.out.println("¡Tiempos registrados exitosamente!");
            System.out.printf("Tiempo total: %.2f segundos%n", tiempoTotal);

        } catch(Exception e) {
            System.out.println("Error al registrar tiempos: " + e.getMessage());
            errorLog.logError("Error al registrar tiempos: " + e.getMessage());
        }
    }

    public void calcularClasificacion() {
        System.out.println("\n=== CALCULANDO CLASIFICACIÓN FINAL ===");

        if(totalParticipantes == 0) {
            System.out.println("Error: No hay participantes registrados.");
            return;
        }

        try {
            int[] indices = new int[totalParticipantes];
            for(int i = 0; i < totalParticipantes; i++) {
                indices[i] = i;
            }

            Calculos.ordenarPorTiempo(indices, tiemposCarrera, totalParticipantes);

            for(int i = 0; i < totalParticipantes; i++) {
                posicionesFinales[indices[i]] = i + 1;
            }

            System.out.println("¡Clasificación calculada exitosamente!");
            mostrarClasificacionFinal();

        } catch(Exception e) {
            System.out.println("Error al calcular clasificación: " + e.getMessage());
            errorLog.logError("Error al calcular clasificacion: " + e.getMessage());
        }
    }

    public void guardarEnArchivo() {
        System.out.println("\n=== GUARDANDO DATOS EN ARCHIVO ===");

        if(totalParticipantes == 0) {
            System.out.println("No hay datos para guardar.");
            return;
        }

        try {
            ManejoArchivos.guardarDatos(participantesInfo, tiemposCarrera,
                    posicionesFinales, totalParticipantes);
            System.out.println("¡Datos guardados exitosamente!");

        } catch(Exception e) {
            System.out.println("Error al guardar archivo: " + e.getMessage());
            errorLog.logError("Error al guardar archivo: " + e.getMessage());
        }
    }

    private void mostrarResumenParticipante(int indice) {
        System.out.println("\n--- RESUMEN DEL PARTICIPANTE ---");
        System.out.println("Piloto: " + participantesInfo[indice][0][0]);
        System.out.println("Edad: " + participantesInfo[indice][0][1]);
        System.out.println("Número: " + participantesInfo[indice][0][2]);
        System.out.println("Vehículo: " + participantesInfo[indice][1][0] +
                " " + participantesInfo[indice][1][1] + " (" + participantesInfo[indice][1][2] + ")");
        System.out.println("Patrocinador: " + participantesInfo[indice][2][0]);
    }

    private void mostrarParticipantesDisponibles() {
        System.out.println("\n--- PARTICIPANTES REGISTRADOS ---");
        for(int i = 0; i < totalParticipantes; i++) {
            System.out.printf("Nº %s - %s%n",
                    participantesInfo[i][0][2], participantesInfo[i][0][0]);
        }
    }

    private int buscarParticipantePorNumero(int numero) {
        for(int i = 0; i < totalParticipantes; i++) {
            if(Integer.parseInt(participantesInfo[i][0][2]) == numero) {
                return i;
            }
        }
        return -1;
    }

    private void mostrarTodosLosParticipantes() {
        System.out.println("\n=== TODOS LOS PARTICIPANTES ===");
        for(int i = 0; i < totalParticipantes; i++) {
            System.out.println("\n--- PARTICIPANTE " + (i + 1) + " ---");
            mostrarResumenParticipante(i);
            if(tiemposCarrera[i][3] > 0) {
                System.out.printf("Tiempo Total: %.2f segundos%n", tiemposCarrera[i][3]);
            } else {
                System.out.println("Sin tiempos registrados");
            }
        }
    }

    private void mostrarClasificacionFinal() {
        System.out.println("\n=== CLASIFICACIÓN FINAL ===");
        System.out.println("POS | PILOTO | VEHÍCULO | TIEMPO | PATROCINADOR");
        System.out.println("-".repeat(70));

        // Crear arreglo ordenado por posición
        int[] indicesOrdenados = new int[totalParticipantes];
        for(int i = 0; i < totalParticipantes; i++) {
            for(int j = 0; j < totalParticipantes; j++) {
                if(posicionesFinales[j] == i + 1) {
                    indicesOrdenados[i] = j;
                    break;
                }
            }
        }

        for(int i = 0; i < totalParticipantes; i++) {
            int idx = indicesOrdenados[i];
            if(tiemposCarrera[idx][3] > 0) {
                System.out.printf("%2d  | %-10s | %-8s | %s | %-12s%n",
                        posicionesFinales[idx],
                        participantesInfo[idx][0][0],
                        participantesInfo[idx][1][0],
                        Calculos.formatearTiempo(tiemposCarrera[idx][3]),
                        participantesInfo[idx][2][0]);
            }
        }
    }

    private void mostrarPorPatrocinador(Scanner scanner) {
        String patrocinador = Validate.validarTexto(scanner,
                "Ingrese nombre del patrocinador: ");

        System.out.println("\n=== PARTICIPANTES DE " + patrocinador.toUpperCase() + " ===");
        boolean encontrado = false;

        for(int i = 0; i < totalParticipantes; i++) {
            if(participantesInfo[i][2][0].equalsIgnoreCase(patrocinador)) {
                mostrarResumenParticipante(i);
                if(tiemposCarrera[i][3] > 0) {
                    System.out.printf("Tiempo: %s - Posición: %d%n",
                            Calculos.formatearTiempo(tiemposCarrera[i][3]), posicionesFinales[i]);
                }
                System.out.println();
                encontrado = true;
            }
        }

        if(!encontrado) {
            System.out.println("No se encontraron participantes de ese patrocinador.");
        }
    }
}
