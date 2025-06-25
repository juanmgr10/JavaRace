import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class errorLog {
    private static final String LOG_FILE = "errores.log";

    // guardar en .log
    public static void logError(String errorMessage) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(LOG_FILE, true))) {
            // Formato: [Fecha y Hora] ERROR: Mensaje de error
            String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
            writer.printf("[%s] ERROR: %s%n", timestamp, errorMessage);
        } catch (IOException e) {
            System.err.println("No se pudo escribir en el archivo de log: " + e.getMessage());
        }
    }
}
