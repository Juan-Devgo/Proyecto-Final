package co.edu.uniquindio.proyectofinal.Model;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;
import org.json.JSONArray;
import org.json.JSONObject;

public class ServicioGeolocalizacion {
    private static final String GOOGLE_GEOCODING_URL = "https://maps.googleapis.com/maps/api/geocode/json?address=%s&key=AIzaSyDnchbnb9_uuiSCd2wEhOfpzbjyeri6JZc";

    public double[] obtenerCoordenadas(String direccion) throws IOException, URISyntaxException {
        String encodedDireccion = URLEncoder.encode(direccion, StandardCharsets.UTF_8.toString());
        String urlStr = String.format(GOOGLE_GEOCODING_URL, encodedDireccion);
        URI uri = new URI(urlStr);
        URL url = uri.toURL();
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");

        if (conn.getResponseCode() != 200) {
            InputStream errorStream = conn.getErrorStream();
            Scanner errorScanner = new Scanner(errorStream, StandardCharsets.UTF_8.name());
            StringBuilder errorResponse = new StringBuilder();
            while (errorScanner.hasNext()) {
                errorResponse.append(errorScanner.nextLine());
            }
            errorScanner.close();
            throw new IOException("Error en la respuesta del servidor: " + conn.getResponseCode() + " - " + errorResponse.toString());
        }

        InputStream responseStream = conn.getInputStream();
        Scanner scanner = new Scanner(responseStream, StandardCharsets.UTF_8.name());
        StringBuilder responseStr = new StringBuilder();
        while (scanner.hasNext()) {
            responseStr.append(scanner.nextLine());
        }
        scanner.close();

        String jsonResponse = responseStr.toString().trim();
        JSONObject jsonObject = new JSONObject(jsonResponse);
        if (jsonObject.has("results")) {
            JSONArray results = jsonObject.getJSONArray("results");
            if (results.length() > 0) {
                JSONObject location = results.getJSONObject(0).getJSONObject("geometry").getJSONObject("location");
                double lat = location.getDouble("lat");
                double lon = location.getDouble("lng");
                return new double[]{lat, lon};
            } else {
                throw new IOException("No se encontraron coordenadas para la dirección dada.");
            }
        } else {
            throw new IOException("La respuesta del servidor no contiene resultados: " + jsonResponse);
        }
    }
}
