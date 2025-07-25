import java.util.Scanner;

public class Validate {

    public static String validarTexto(Scanner scanner, String mensaje) {
        String texto = "";
        boolean valido = false;

        while(!valido) {
            System.out.print(mensaje);
            texto = scanner.nextLine().trim();

            if(texto.isEmpty()) {
                System.out.println("Error: El texto no puede estar vacío.");
                continue;
            }

            if(!esTextoValido(texto)) {
                System.out.println("Error: El texto contiene caracteres inválidos.");
                continue;
            }

            valido = true;
        }

        return texto;
    }

    public static int validarEntero(Scanner scanner, String mensaje) {
        int numero = 0;
        boolean valido = false;

        while(!valido) {
            System.out.print(mensaje);
            try {
                String entrada = scanner.nextLine().trim();

                if(entrada.isEmpty()) {
                    System.out.println("Error: Debe ingresar un número.");
                    continue;
                }

                numero = Integer.parseInt(entrada);

                if(numero < 0) {
                    System.out.println("Error: El número debe ser positivo.");
                    continue;
                }

                valido = true;

            } catch(NumberFormatException e) {
                System.out.println("Error: Debe ingresar un número entero válido.");
                errorLog.logError("Error al ingresar numero valido: " + e.getMessage());

            }
        }

        return numero;
    }
    public static int validarEdad(Scanner scanner, String mensaje) {
        int numero = 0;
        boolean valido = false;

        while(!valido) {
            System.out.print(mensaje);
            try {
                String entrada = scanner.nextLine().trim();

                if(entrada.isEmpty()) {
                    System.out.println("Error: Debe ingresar un número.");
                    continue;
                }

                numero = Integer.parseInt(entrada);

                if(numero < 16 ) {
                    System.out.println("Error: la edad debe ser mayor de 16 años.");
                    continue;
                }
                if(numero > 80 ) {
                    System.out.println("Error: la edad debe ser menor de 80 años.");
                    continue;
                }

                valido = true;

            } catch(NumberFormatException e) {
                System.out.println("Error: Debe ingresar un número entero válido.");
                errorLog.logError("Error al ingresar numero valido: " + e.getMessage());

            }
        }

        return numero;
    }

    public static double validarTiempo(Scanner scanner, String mensaje) {
        double tiempo = 0.0;
        boolean valido = false;

        while(!valido) {
            System.out.print(mensaje);
            try {
                String entrada = scanner.nextLine().trim();

                if(entrada.isEmpty()) {
                    System.out.println("Error: Debe ingresar un tiempo.");
                    continue;
                }

                tiempo = Double.parseDouble(entrada);

                if(tiempo <= 0) {
                    System.out.println("Error: El tiempo debe ser mayor a 0.");
                    continue;
                }

                if(tiempo > 3600) {
                    System.out.println("Error: Tiempo demasiado alto (máximo 3600 segundos).");
                    continue;
                }

                valido = true;

            } catch(NumberFormatException e) {
                System.out.println("Error: Debe ingresar un número decimal válido.");
                errorLog.logError("Error: Debe ingresar un número decimal válido." + e.getMessage());
            }
        }

        return tiempo;
    }

    public static int validarNumeroParticipante(Scanner scanner, String mensaje,
                                                String[][][] participantes, int totalParticipantes) {
        int numero = 0;
        boolean valido = false;

        while(!valido) {
            numero = validarEntero(scanner, mensaje);

            if(numero <= 0 || numero > 25) {
                System.out.println("Error: El número debe estar entre 1 y 25.");
                continue;
            }

            if(numeroParticipanteExiste(numero, participantes, totalParticipantes)) {
                System.out.println("Error: Este número de participante ya está registrado.");
                continue;
            }

            valido = true;
        }

        return numero;
    }

    public static int validarAño(Scanner scanner, String mensaje) {
        int año = 0;
        boolean valido = false;

        while(!valido) {
            año = validarEntero(scanner, mensaje);

            if(año < 1950 || año > 2024) {
                System.out.println("Error: El año debe estar entre 1900 y 2024.");
                continue;
            }

            valido = true;
        }

        return año;
    }

    private static boolean esTextoValido(String texto) {
        return texto.matches("[a-zA-ZáéíóúÁÉÍÓÚñÑ0-9\\s.,\\-$]+");
    }

    private static boolean numeroParticipanteExiste(int numero, String[][][] participantes,
                                                    int totalParticipantes) {
        for(int i = 0; i < totalParticipantes; i++) {
            try {
                if(Integer.parseInt(participantes[i][0][2]) == numero) {
                    return true;
                }
            } catch(NumberFormatException e) {

            }
        }
        return false;
    }

    public static boolean validarRangoIndice(int indice, int maximo) {
        return indice >= 0 && indice < maximo;
    }

    public static boolean validarArregloNoNulo(Object[] arreglo) {
        return arreglo != null;
    }

    public static boolean validarStringNoVacio(String texto) {
        return texto != null && !texto.trim().isEmpty();
    }
    public static String validarNombre(Scanner scanner, String mensaje) {
        String texto = "";
        boolean valido = false;

        while(!valido) {
            System.out.print(mensaje);
            texto = scanner.nextLine().trim();

            if(texto.isEmpty()) {
                System.out.println("Error: El texto no puede estar vacío.");
                continue;
            }

            if(texto.matches(".\\d.")) {  // Verifica si hay algún dígito en el texto
                System.out.println("Error: El texto no puede contener números.");
                continue;
            }

            if(!esTextoValido(texto)) {
                System.out.println("Error: El texto contiene caracteres inválidos.");
                continue;
            }

            valido = true;
        }

        return texto;
    }
}
