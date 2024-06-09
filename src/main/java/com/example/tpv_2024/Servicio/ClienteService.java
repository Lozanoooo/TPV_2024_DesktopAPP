package com.example.tpv_2024.Servicio;

import com.example.tpv_2024.Modelos.Cliente;
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
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ClienteService {

    private static final Logger logger = LoggerFactory.getLogger(EmpleadoService.class);
    private static final String BASE_URL = "http://localhost:8080/empleados";

    public static Cliente buscarCliente(String idCliente) throws Exception {
        System.out.println("Buscando cliente con ID: " + idCliente);
        String urlString = "http://localhost:8080/clientes/" + idCliente;
        URL url = new URL(urlString);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");

        int responseCode = conn.getResponseCode();
        if (responseCode == 200) { // 200 OK
            System.out.println("Cliente encontrado");
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

            // Crear un objeto Cliente con los datos obtenidos
            Cliente cliente = new Cliente();
            cliente.setIdCliente(jsonNode.get("idCliente").asLong());
            cliente.setNombre(jsonNode.get("nombre").asText());
            cliente.setApellidos(jsonNode.get("apellidos").asText());
            cliente.setDireccion(jsonNode.get("direccion").asText());
            cliente.setEmail(jsonNode.get("email").asText());
            cliente.setTelefono(jsonNode.get("telefono").asText());
            cliente.setCif(jsonNode.get("cif").asText());

            return cliente;
        } else {
            throw new Exception("Cliente no encontrado");
        }
    }

    public static void actualizarClientes(ObservableList<Cliente> clientes) {
        for (Cliente cliente : clientes) {
            Long idCliente = cliente.getIdCliente();
            String urlString = "http://localhost:8080/clientes/" + idCliente;

            try {
                URL url = new URL(urlString);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();

                // Verificar si el cliente existe
                conn.setRequestMethod("GET");
                int responseCode = conn.getResponseCode();

                if (responseCode == HttpURLConnection.HTTP_OK) {
                    // El cliente existe, realizar una solicitud PUT para actualizar
                    conn = (HttpURLConnection) url.openConnection();
                    conn.setRequestMethod("PUT");
                    conn.setRequestProperty("Content-Type", "application/json");
                    conn.setDoOutput(true);

                    // Crear un objeto Map para representar la estructura JSON
                    Map<String, Object> jsonData = new HashMap<>();
                    jsonData.put("idCliente", cliente.getIdCliente());
                    jsonData.put("nombre", cliente.getNombre());
                    jsonData.put("apellidos", cliente.getApellidos());
                    jsonData.put("direccion", cliente.getDireccion());
                    jsonData.put("email", cliente.getEmail());
                    jsonData.put("telefono", cliente.getTelefono());
                    jsonData.put("cif", cliente.getCif());

                    // Convertir el objeto Map a JSON
                    ObjectMapper objectMapper = new ObjectMapper();
                    String json = objectMapper.writeValueAsString(jsonData);

                    // Enviar el JSON en el cuerpo de la solicitud
                    conn.getOutputStream().write(json.getBytes());

                    responseCode = conn.getResponseCode();
                    if (responseCode == HttpURLConnection.HTTP_OK) {
                        System.out.println("Cliente actualizado: " + idCliente);
                    } else {
                        System.out.println("Error al actualizar el cliente: " + idCliente);
                        System.out.println("Response code: " + responseCode);
                    }
                } else if (responseCode == HttpURLConnection.HTTP_NOT_FOUND) {
                    // El cliente no existe, realizar una solicitud POST para crear
                    urlString = "http://localhost:8080/clientes";
                    url = new URL(urlString);
                    conn = (HttpURLConnection) url.openConnection();
                    conn.setRequestMethod("POST");
                    conn.setRequestProperty("Content-Type", "application/json");
                    conn.setDoOutput(true);

                    // Crear un objeto Map para representar la estructura JSON
                    Map<String, Object> jsonData = new HashMap<>();
                    jsonData.put("nombre", cliente.getNombre());
                    jsonData.put("apellidos", cliente.getApellidos());
                    jsonData.put("direccion", cliente.getDireccion());
                    jsonData.put("email", cliente.getEmail());
                    jsonData.put("telefono", cliente.getTelefono());
                    jsonData.put("cif", cliente.getCif());

                    // Convertir el objeto Map a JSON
                    ObjectMapper objectMapper = new ObjectMapper();
                    String json = objectMapper.writeValueAsString(jsonData);

                    // Enviar el JSON en el cuerpo de la solicitud
                    conn.getOutputStream().write(json.getBytes());

                    responseCode = conn.getResponseCode();
                    System.out.println("Cliente creado: " + idCliente);
                } else {
                    System.out.println("Error al verificar el cliente: " + idCliente);
                    System.out.println("Response code: " + responseCode);
                }

                conn.disconnect();
            } catch (Exception e) {
                System.out.println("Error al procesar el cliente: " + idCliente);
                e.printStackTrace();
            }
        }
    }

    public static List<Cliente> obtenerTodosLosClientes() throws IOException {
        String url = BASE_URL;
        try {
            String response = HttpUtil.sendGetRequest(url);
            ObjectMapper mapper = new ObjectMapper();
            logger.debug("Response: {}", response);
            return Arrays.asList(mapper.readValue(response, Cliente[].class));
        } catch (IOException e) {
            logger.error("Error al obtener los clientes: {}", e.getMessage());
            throw new RuntimeException("Error al obtener los clientes: " + e.getMessage(), e);
        }
    }
}