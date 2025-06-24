import java.util.Scanner;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        System.out.println("Java Race Timer ");
        process sistema = new process();
        mostrarMenuPrincipal();



        Scanner scanner = new Scanner(System.in);

        int opcion = -1;
        while(opcion != 0) {
            opcion = validate.validarEntero(scanner, "Seleccione una opción: ");

            switch(opcion) {
                case 1:
                    sistema.registrarParticipante(scanner);
                    break;
                case 2:
                    sistema.registrarTiempo(scanner);
                    break;
                case 3:
                    sistema.calcularClasificacion();
                    break;
                case 4:
                    sistema.mostrarResultados(scanner);
                    break;
                case 5:
                    sistema.guardarEnArchivo();
                    break;
                case 0:
                    System.out.println("¡Gracias por usar el sistema!");
                    break;
                default:
                    System.out.println("Opción inválida. Intente nuevamente.");
            }

            if(opcion != 0) {
                System.out.println("\nPresione Enter para continuar...");
                scanner.nextLine();
            }
        }

        scanner.close();
    }
    private static void mostrarMenuPrincipal() {
        System.out.println("\n" + "=".repeat(50));
        System.out.println("           MENÚ PRINCIPAL");
        System.out.println("=".repeat(50));
        System.out.println("1. Registrar Participante");
        System.out.println("2. Registrar Tiempo de Carrera");
        System.out.println("3. Calcular Clasificación Final");
        System.out.println("4. Mostrar Resultados");
        System.out.println("5. Guardar Datos en Archivo");
        System.out.println("0. Salir del Sistema");
        System.out.println("=".repeat(50));
    }
}