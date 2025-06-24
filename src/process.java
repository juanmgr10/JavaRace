import java.util.Scanner;

public class process {

    // Arreglo tridimensional: [participante][categoria][datos]
    // categoria: 0=datosPersonales, 1=datosVehiculo, 2=patrocinador
    // datos: 0=nombre/marca, 1=edad/modelo, 2=numero/año
    private String[][][] participantesInfo;

    // Arreglo bidimensional: [participante][tiempos]
    // tiempos: 0=vuelta1, 1=vuelta2, 2=vuelta3, 3=tiempoTotal
    private double[][] tiemposCarrera;

    // Arreglos auxiliares para clasificación
    private int[] posicionesFinales;
    private boolean[] participanteActivo;

    private int totalParticipantes;
    private final int MAX_PARTICIPANTES = 50;

    public process() {
        inicializarArreglos();
    }

    private void inicializarArreglos() {
        // Inicialización del arreglo tridimensional
        participantesInfo = new String[MAX_PARTICIPANTES][3][3];
        for(int i = 0; i < MAX_PARTICIPANTES; i++) {
            for(int j = 0; j < 3; j++) {
                for(int k = 0; k < 3; k++) {
                    participantesInfo[i][j][k] = "";
                }
            }
        }

        // Inicialización del arreglo bidimensional
        tiemposCarrera = new double[MAX_PARTICIPANTES][4];
        for(int i = 0; i < MAX_PARTICIPANTES; i++) {
            for(int j = 0; j < 4; j++) {
                tiemposCarrera[i][j] = 0.0;
            }
        }

        // Inicialización de arreglos auxiliares
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
            // Datos personales
            String nombrePiloto = validate.validarTexto(scanner,
                    "Ingrese nombre del piloto: ");
            int edadPiloto = validate.validarEntero(scanner,
                    "Ingrese edad del piloto: ");
            int numeroParticipante = validate.validarNumeroParticipante(scanner,
                    "Ingrese número de participante: ", participantesInfo, totalParticipantes);

            // Datos del vehículo
            String marcaVehiculo = validate.validarTexto(scanner,
                    "Ingrese marca del vehículo: ");
            String modeloVehiculo = validate.validarTexto(scanner,
                    "Ingrese modelo del vehículo: ");
            int añoVehiculo = validate.validarAño(scanner,
                    "Ingrese año del vehículo: ");

            // Datos del patrocinador
            String nombrePatrocinador = validate.validarTexto(scanner,
                    "Ingrese nombre del patrocinador: ");

            // Guardar en arreglo tridimensional
            int indice = totalParticipantes;

            // Categoría 0: Datos personales
            participantesInfo[indice][0][0] = nombrePiloto;
            participantesInfo[indice][0][1] = String.valueOf(edadPiloto);
            participantesInfo[indice][0][2] = String.valueOf(numeroParticipante);

            // Categoría 1: Datos del vehículo
            participantesInfo[indice][1][0] = marcaVehiculo;
            participantesInfo[indice][1][1] = modeloVehiculo;
            participantesInfo[indice][1][2] = String.valueOf(añoVehiculo);

            // Categoría 2: Datos del patrocinador
            participantesInfo[indice][2][0] = nombrePatrocinador;
            participantesInfo[indice][2][1] = ""; // Campo vacío
            participantesInfo[indice][2][2] = ""; // Campo vacío

            participanteActivo[indice] = true;
            totalParticipantes++;

            System.out.println("¡Participante registrado exitosamente!");
            mostrarResumenParticipante(indice);

        } catch(Exception e) {
            System.out.println("Error al registrar participante: " + e.getMessage());
        }
    }

    public void registrarTiempo(Scanner scanner) {
        System.out.println("\n=== REGISTRO DE TIEMPOS ===");

        if(totalParticipantes == 0) {
            System.out.println("Error: No hay participantes registrados.");
            return;
        }

        mostrarParticipantesDisponibles();

        int numeroParticipante = validate.validarEntero(scanner,
                "Ingrese número de participante: ");

        int indiceParticipante = buscarParticipantePorNumero(numeroParticipante);

        if(indiceParticipante == -1) {
            System.out.println("Error: Participante no encontrado.");
            return;
        }

        try {
            System.out.println("Registrando tiempos para: " +
                    participantesInfo[indiceParticipante][0][0]);

            double vuelta1 = validate.validarTiempo(scanner, "Tiempo vuelta 1 (segundos): ");
            double vuelta2 = validate.validarTiempo(scanner, "Tiempo vuelta 2 (segundos): ");
            double vuelta3 = validate.validarTiempo(scanner, "Tiempo vuelta 3 (segundos): ");

            // Calcular tiempo total
            double tiempoTotal = Calculos.calcularTiempoTotal(vuelta1, vuelta2, vuelta3);

            // Guardar en arreglo bidimensional
            tiemposCarrera[indiceParticipante][0] = vuelta1;
            tiemposCarrera[indiceParticipante][1] = vuelta2;
            tiemposCarrera[indiceParticipante][2] = vuelta3;
            tiemposCarrera[indiceParticipante][3] = tiempoTotal;

            System.out.println("¡Tiempos registrados exitosamente!");
            System.out.printf("Tiempo total: %.2f segundos%n", tiempoTotal);

        } catch(Exception e) {
            System.out.println("Error al registrar tiempos: " + e.getMessage());
        }
    }

    public void calcularClasificacion() {
        System.out.println("\n=== CALCULANDO CLASIFICACIÓN FINAL ===");

        if(totalParticipantes == 0) {
            System.out.println("Error: No hay participantes registrados.");
            return;
        }

        try {
            // Crear arreglo de índices para ordenamiento
            int[] indices = new int[totalParticipantes];
            for(int i = 0; i < totalParticipantes; i++) {
                indices[i] = i;
            }

            // Ordenar por tiempo total usando algoritmo burbuja
            Calculos.ordenarPorTiempo(indices, tiemposCarrera, totalParticipantes);

            // Asignar posiciones finales
            for(int i = 0; i < totalParticipantes; i++) {
                posicionesFinales[indices[i]] = i + 1;
            }

            System.out.println("¡Clasificación calculada exitosamente!");
            mostrarClasificacionFinal();

        } catch(Exception e) {
            System.out.println("Error al calcular clasificación: " + e.getMessage());
        }
    }

    public void mostrarResultados(Scanner scanner) {
        System.out.println("\n=== MOSTRAR RESULTADOS ===");

        if(totalParticipantes == 0) {
            System.out.println("No hay datos para mostrar.");
            return;
        }

        System.out.println("1. Mostrar todos los participantes");
        System.out.println("2. Mostrar clasificación final");
        System.out.println("3. Mostrar por patrocinador");

        int opcion = validate.validarEntero(scanner, "Seleccione opción: ");

        switch(opcion) {
            case 1:
                mostrarTodosLosParticipantes();
                break;
            case 2:
                mostrarClasificacionFinal();
                break;
            case 3:
                mostrarPorPatrocinador(scanner);
                break;
            default:
                System.out.println("Opción inválida.");
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
        }
    }


    // Métodos auxiliares
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
        String patrocinador = validate.validarTexto(scanner,
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
