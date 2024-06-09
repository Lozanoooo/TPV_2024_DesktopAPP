package com.example.tpv_2024.Servicio;

import com.example.tpv_2024.Modelos.Empleado;
import com.example.tpv_2024.util.HttpUtil;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.collections.ObservableList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import java.util.Arrays;
import java.util.List;


public class EmpleadoService {

    private static final Logger logger = LoggerFactory.getLogger(EmpleadoService.class);
    private static final String BASE_URL = "http://localhost:8080/empleados";

    public Empleado getEmpleadoPorId(String idEmpleado) {
        String url = BASE_URL + "/" + idEmpleado;
        try {
            String response = HttpUtil.sendGetRequest(url);
            ObjectMapper mapper = new ObjectMapper();
            logger.debug("Response: {}", response);
            return mapper.readValue(response, Empleado.class);
        } catch (IOException e) {
            logger.error("Error al obtener el empleado: {}", e.getMessage());
            throw new RuntimeException("Error al obtener el empleado: " + e.getMessage(), e);
        }
    }

    public static Empleado buscarEmpleado(String idEmpleado) throws Exception {
        System.out.println("Buscando empleado con ID: " + idEmpleado);
        String urlString = "http://localhost:8080/empleados/" + idEmpleado;
        URL url = new URL(urlString);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");

        int responseCode = conn.getResponseCode();
        if (responseCode == 200) { // 200 OK
            System.out.println("Empleado encontrado");
            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String inputLine;
            StringBuilder response = new StringBuilder();
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

            // Parsear la respuesta JSON
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonNode = objectMapper.readTree(response.toString());

            // Crear un objeto Empleado con los datos obtenidos
            Empleado empleado = new Empleado();
            empleado.setIdEmpleado((int) jsonNode.get("idEmpleado").asLong());
            empleado.setNombre(jsonNode.get("nombre").asText());
            empleado.setApellidos(jsonNode.get("apellidos").asText());
            empleado.setPuesto(jsonNode.get("puesto").asText());

            return empleado;
        } else {
            throw new Exception("Empleado no encontrado");
        }
    }

    public static void actualizarEmpleados(ObservableList<Empleado> empleados) {
        for (Empleado empleado : empleados) {
            Long idEmpleado = (long) empleado.getIdEmpleado();
            String urlString = "http://localhost:8080/empleados/" + idEmpleado;

            try {
                URL url = new URL(urlString);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();

                // Verificar si el empleado existe
                conn.setRequestMethod("GET");
                int responseCode = conn.getResponseCode();

                if (responseCode == HttpURLConnection.HTTP_OK) {
                    // El empleado existe, realizar una solicitud PUT para actualizar
                    conn = (HttpURLConnection) url.openConnection();
                    conn.setRequestMethod("PUT");
                    conn.setRequestProperty("Content-Type", "application/json");
                    conn.setDoOutput(true);

                    // Crear un objeto Map para representar la estructura JSON
                    Map<String, Object> jsonData = new HashMap<>();
                    jsonData.put("idEmpleado", empleado.getIdEmpleado());
                    jsonData.put("nombre", empleado.getNombre());
                    jsonData.put("apellidos", empleado.getApellidos());
                    jsonData.put("puesto", empleado.getPuesto());

                    // Convertir el objeto Map a JSON
                    ObjectMapper objectMapper = new ObjectMapper();
                    String json = objectMapper.writeValueAsString(jsonData);

                    // Enviar el JSON en el cuerpo de la solicitud
                    conn.getOutputStream().write(json.getBytes());

                    responseCode = conn.getResponseCode();
                    System.out.println("Empleado actualizado: " + idEmpleado);
                } else if (responseCode == HttpURLConnection.HTTP_NOT_FOUND) {
                    // El empleado no existe, realizar una solicitud POST para crear
                    urlString = "http://localhost:8080/empleados";
                    url = new URL(urlString);
                    conn = (HttpURLConnection) url.openConnection();
                    conn.setRequestMethod("POST");
                    conn.setRequestProperty("Content-Type", "application/json");
                    conn.setDoOutput(true);

                    // Crear un objeto Map para representar la estructura JSON
                    Map<String, Object> jsonData = new HashMap<>();
                    jsonData.put("nombre", empleado.getNombre());
                    jsonData.put("apellidos", empleado.getApellidos());
                    jsonData.put("puesto", empleado.getPuesto());

                    // Convertir el objeto Map a JSON
                    ObjectMapper objectMapper = new ObjectMapper();
                    String json = objectMapper.writeValueAsString(jsonData);

                    // Enviar el JSON en el cuerpo de la solicitud
                    conn.getOutputStream().write(json.getBytes());

                    responseCode = conn.getResponseCode();
                    System.out.println("Empleado creado: " + idEmpleado);
                } else {
                    System.out.println("Error al verificar el empleado: " + idEmpleado);
                    System.out.println("Response code: " + responseCode);
                }

                conn.disconnect();
            } catch (Exception e) {
                System.out.println("Error al procesar el empleado: " + idEmpleado);
                e.printStackTrace();
            }
        }
    }

    public static List<Empleado> obtenerTodosLosEmpleados() throws IOException {
        String url = BASE_URL;
        try {
            String response = HttpUtil.sendGetRequest(url);
            ObjectMapper mapper = new ObjectMapper();
            logger.debug("Response: {}", response);
            return Arrays.asList(mapper.readValue(response, Empleado[].class));
        } catch (IOException e) {
            logger.error("Error al obtener los empleados: {}", e.getMessage());
            throw new RuntimeException("Error al obtener los empleados: " + e.getMessage(), e);
        }
    }
}

