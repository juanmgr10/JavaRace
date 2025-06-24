public class Calculos {

    public static double calcularTiempoTotal(double vuelta1, double vuelta2, double vuelta3) {
        if(vuelta1 < 0 || vuelta2 < 0 || vuelta3 < 0) {
            throw new IllegalArgumentException("Los tiempos no pueden ser negativos");
        }

        return vuelta1 + vuelta2 + vuelta3;
    }

    public static double calcularTiempoPromedio(double tiempoTotal, int numeroVueltas) {
        if(numeroVueltas <= 0) {
            throw new IllegalArgumentException("El número de vueltas debe ser mayor a 0");
        }

        return tiempoTotal / numeroVueltas;
    }

    public static void ordenarPorTiempo(int[] indices, double[][] tiempos, int totalParticipantes) {
        if(indices == null || tiempos == null) {
            throw new IllegalArgumentException("Los arreglos no pueden ser nulos");
        }

        // Algoritmo de ordenamiento burbuja mejorado
        for(int i = 0; i < totalParticipantes - 1; i++) {
            boolean intercambio = false;

            for(int j = 0; j < totalParticipantes - 1 - i; j++) {
                // Comparar tiempos totales (columna 3)
                double tiempo1 = tiempos[indices[j]][3];
                double tiempo2 = tiempos[indices[j + 1]][3];

                // Si el tiempo es 0, se considera como el peor tiempo
                if(tiempo1 == 0.0) tiempo1 = Double.MAX_VALUE;
                if(tiempo2 == 0.0) tiempo2 = Double.MAX_VALUE;

                if(tiempo1 > tiempo2) {
                    // Intercambiar índices
                    int temp = indices[j];
                    indices[j] = indices[j + 1];
                    indices[j + 1] = temp;
                    intercambio = true;
                }
            }

            // Si no hubo intercambios, el arreglo ya está ordenado
            if(!intercambio) {
                break;
            }
        }
    }

    public static double encontrarMejorTiempo(double[][] tiempos, int totalParticipantes) {
        if(tiempos == null || totalParticipantes <= 0) {
            return 0.0;
        }

        double mejorTiempo = Double.MAX_VALUE;
        boolean hayTiempos = false;

        for(int i = 0; i < totalParticipantes; i++) {
            if(tiempos[i][3] > 0 && tiempos[i][3] < mejorTiempo) {
                mejorTiempo = tiempos[i][3];
                hayTiempos = true;
            }
        }

        return hayTiempos ? mejorTiempo : 0.0;
    }

    public static double encontrarPeorTiempo(double[][] tiempos, int totalParticipantes) {
        if(tiempos == null || totalParticipantes <= 0) {
            return 0.0;
        }

        double peorTiempo = 0.0;

        for(int i = 0; i < totalParticipantes; i++) {
            if(tiempos[i][3] > peorTiempo) {
                peorTiempo = tiempos[i][3];
            }
        }

        return peorTiempo;
    }

    public static double calcularTiempoPromedioGeneral(double[][] tiempos, int totalParticipantes) {
        if(tiempos == null || totalParticipantes <= 0) {
            return 0.0;
        }

        double sumaTotal = 0.0;
        int participantesConTiempo = 0;

        for(int i = 0; i < totalParticipantes; i++) {
            if(tiempos[i][3] > 0) {
                sumaTotal += tiempos[i][3];
                participantesConTiempo++;
            }
        }

        return participantesConTiempo > 0 ? sumaTotal / participantesConTiempo : 0.0;
    }

    public static int contarParticipantesConTiempo(double[][] tiempos, int totalParticipantes) {
        if(tiempos == null) {
            return 0;
        }

        int contador = 0;
        for(int i = 0; i < totalParticipantes; i++) {
            if(tiempos[i][3] > 0) {
                contador++;
            }
        }

        return contador;
    }

    public static double calcularDiferenciaTiempo(double tiempo1, double tiempo2) {
        return Math.abs(tiempo1 - tiempo2);
    }

    public static String formatearTiempo(double segundos) {
        if(segundos <= 0) {
            return "00:00.00";
        }

        int minutos = (int) (segundos / 60);
        double segundosRestantes = segundos % 60;

        return String.format("%02d:%05.2f", minutos, segundosRestantes);
    }
}