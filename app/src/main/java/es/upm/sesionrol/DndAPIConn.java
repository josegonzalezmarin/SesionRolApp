package es.upm.sesionrol;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class DndAPIConn {
    private String hacerRequest(String url) throws IOException {
        StringBuilder response = new StringBuilder();

        // Crea la URL de la API utilizando la dirección raíz y la ruta específica
        URL apiUrl = new URL(url);

        // Abre una conexión HTTP
        HttpURLConnection connection = (HttpURLConnection) apiUrl.openConnection();

        // Establece el método de solicitud y otros parámetros necesarios
        connection.setRequestMethod("GET");
        connection.setReadTimeout(5000); // Tiempo de espera de lectura de 5 segundos
        connection.setConnectTimeout(5000); // Tiempo de espera de conexión de 5 segundos

        // Realiza la conexión y verifica el código de respuesta
        int responseCode = connection.getResponseCode();
        if (responseCode == HttpURLConnection.HTTP_OK) {
            // Lee la respuesta de la API
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String line;
            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
            reader.close();
        } else {
            // La solicitud no fue exitosa, manejar el error según sea necesario
            throw new IOException("Error en la solicitud HTTP. Código de respuesta: " + responseCode);
        }

        // Cierra la conexión
        connection.disconnect();

        // Devuelve la respuesta como una cadena de texto
        return response.toString();
    }

}
