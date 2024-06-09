package com.example.tpv_2024.Servicio;

import com.example.tpv_2024.Modelos.Ventas;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

// Excepción personalizada
class ProductoNoEncontradoException extends Exception {
    public ProductoNoEncontradoException(String message) {
        super(message);
    }
}

public class ProductoService {

    public static Ventas verificarProducto(String codigo) throws Exception {
        System.out.println("Verificando producto con código de barras: " + codigo);
        String urlString = "http://localhost:8080/productos/" + codigo;
        URL url = new URL(urlString);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");

        int responseCode = conn.getResponseCode();
        if (responseCode == 200) { // 200 OK
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

            // Crear un objeto Producto con los datos obtenidos
            Ventas producto = new Ventas();
            producto.setCodigo(jsonNode.get("codigoBarra").asText());
            producto.setNombre(jsonNode.get("nombre").asText());
            producto.setPrecio(jsonNode.get("precioVenta").asDouble());

            return producto;
        } else if (responseCode == 404) {
            throw new ProductoNoEncontradoException("Producto con código de barras " + codigo + " no encontrado");
        } else {
            throw new Exception("Error en la solicitud: Código de respuesta " + responseCode);
        }
    }
}
