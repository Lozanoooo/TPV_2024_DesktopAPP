package com.example.tpv_2024.Servicio;

import com.example.tpv_2024.Modelos.Producto;
import com.example.tpv_2024.Modelos.Ventas;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.collections.ObservableList;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

/*
* Clase diseñada para interactuar con un servicio web que proporciona información sobre productos.
* Esta clase contiene un único método estático llamado verificarProducto que se utiliza para obtener
* la información de un producto basado en su código de barras.
* */
public class ProductoService {

    public static Ventas verificarProducto(String codigo) throws Exception {
        System.out.println("Verificando producto con código de barras: " + codigo);
        String urlString = "http://localhost:8080/productos/" +codigo;
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
        } else {
            throw new Exception("Producto no encontrado");
        }
    }

    public static Producto verificarProducto2(String codigo) throws Exception {
        System.out.println("Verificando producto con código de barras: " + codigo);
        String urlString = "http://localhost:8080/productos/" +codigo;
        URL url = new URL(urlString);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");

        int responseCode = conn.getResponseCode();
        if (responseCode == 200) { // 200 OK
            System.out.println("Producto encontrado");
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
            Producto producto = new Producto();
            producto.setCodigoBarra(jsonNode.get("codigoBarra").asText());
            producto.setNombre(jsonNode.get("nombre").asText());
            producto.setPrecioVenta(jsonNode.get("precioVenta").asDouble());
            producto.setStock(jsonNode.get("stock").asInt());
            producto.setSuministrador(jsonNode.get("suministrador").asText());
            producto.setPrecioSuministrador(jsonNode.get("precioSuministrador").asDouble());
            producto.setGanancia(jsonNode.get("ganancia").asDouble());
            producto.setCategoria(jsonNode.get("categoria").asText());
            // Obtener la información de localización del objeto "localizacion"
            JsonNode localizacionNode = jsonNode.get("localizacion");
            if (localizacionNode.get("pasilloAlmacen")== null) {
                producto.setPasilloAlmacen("null");
                producto.setEstanteriaAlmacen("null");
                producto.setPasilloTienda("null");
                producto.setEstanteriaTienda("null");
            }else {
                producto.setPasilloAlmacen(localizacionNode.get("pasilloAlmacen").asText());
                producto.setEstanteriaAlmacen(localizacionNode.get("estanteriaAlmacen").asText());
                producto.setPasilloTienda(localizacionNode.get("pasilloTienda").asText());
                producto.setEstanteriaTienda(localizacionNode.get("estanteriaTienda").asText());
            }
            return producto;
        } else {
            throw new Exception("Producto no encontrado");
        }
    }

    public static void actualizarProductos(ObservableList<Producto> productos) {
        for (Producto producto : productos) {
            String codigoBarra = producto.getCodigoBarra();
            String urlString = "http://localhost:8080/productos/" + codigoBarra;

            try {
                URL url = new URL(urlString);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();

                // Verificar si el producto existe
                conn.setRequestMethod("GET");
                int responseCode = conn.getResponseCode();

                if (responseCode == HttpURLConnection.HTTP_OK) {
                    // El producto existe, realizar una solicitud PUT para actualizar
                    conn = (HttpURLConnection) url.openConnection();
                    conn.setRequestMethod("PUT");
                    conn.setRequestProperty("Content-Type", "application/json");
                    conn.setDoOutput(true);

                    // Crear un objeto Map para representar la estructura JSON
                    Map<String, Object> jsonData = new HashMap<>();
                    jsonData.put("codigoBarra", producto.getCodigoBarra());
                    jsonData.put("nombre", producto.getNombre());
                    jsonData.put("precioVenta", producto.getPrecioVenta());
                    jsonData.put("categoria", producto.getCategoria());
                    jsonData.put("stock", producto.getStock());
                    jsonData.put("precioSuministrador", producto.getPrecioSuministrador());
                    jsonData.put("suministrador", producto.getSuministrador());

                    // Crear un objeto Map para la localización
                    Map<String, Object> localizacionData = new HashMap<>();
                    localizacionData.put("pasilloTienda", producto.getPasilloTienda());
                    localizacionData.put("estanteriaTienda", producto.getEstanteriaTienda());
                    localizacionData.put("pasilloAlmacen", producto.getPasilloAlmacen());
                    localizacionData.put("estanteriaAlmacen", producto.getEstanteriaAlmacen());

                    jsonData.put("localizacion", localizacionData);

                    // Convertir el objeto Map a JSON
                    ObjectMapper objectMapper = new ObjectMapper();
                    String json = objectMapper.writeValueAsString(jsonData);
                    System.out.println(json);

                    // Enviar el JSON en el cuerpo de la solicitud
                    conn.getOutputStream().write(json.getBytes());

                    responseCode = conn.getResponseCode();
                    if (responseCode == HttpURLConnection.HTTP_OK) {
                        System.out.println("Producto actualizado: " + codigoBarra);
                    } else {
                        System.out.println("Error al actualizar el producto: " + codigoBarra);
                        System.out.println("Response code: " + responseCode);
                    }
                } else if (responseCode == HttpURLConnection.HTTP_NOT_FOUND) {
                    // El producto no existe, realizar una solicitud POST para crear
                    urlString = "http://localhost:8080/productos";
                    url = new URL(urlString);
                    conn = (HttpURLConnection) url.openConnection();
                    conn.setRequestMethod("POST");
                    conn.setRequestProperty("Content-Type", "application/json");
                    conn.setDoOutput(true);

                    // Crear un objeto Map para representar la estructura JSON
                    Map<String, Object> jsonData = new HashMap<>();
                    jsonData.put("codigoBarra", producto.getCodigoBarra());
                    jsonData.put("nombre", producto.getNombre());
                    jsonData.put("precioVenta", producto.getPrecioVenta());
                    jsonData.put("categoria", producto.getCategoria());
                    jsonData.put("stock", producto.getStock());
                    jsonData.put("precioSuministrador", producto.getPrecioSuministrador());
                    jsonData.put("suministrador", producto.getSuministrador());

                    // Crear un objeto Map para la localización
                    Map<String, Object> localizacionData = new HashMap<>();
                    localizacionData.put("pasilloTienda", producto.getPasilloTienda());
                    localizacionData.put("estanteriaTienda", producto.getEstanteriaTienda());
                    localizacionData.put("pasilloAlmacen", producto.getPasilloAlmacen());
                    localizacionData.put("estanteriaAlmacen", producto.getEstanteriaAlmacen());

                    jsonData.put("localizacion", localizacionData);

                    // Convertir el objeto Map a JSON
                    ObjectMapper objectMapper = new ObjectMapper();
                    String json = objectMapper.writeValueAsString(jsonData);
                    System.out.println(json);

                    // Enviar el JSON en el cuerpo de la solicitud
                    conn.getOutputStream().write(json.getBytes());

                    responseCode = conn.getResponseCode();
                    if (responseCode == HttpURLConnection.HTTP_CREATED) {
                        System.out.println("Producto creado: " + codigoBarra);
                    } else {
                        System.out.println("Error al crear el producto: " + codigoBarra);
                    }
                } else {
                    System.out.println("Error al verificar el producto: " + codigoBarra);
                    System.out.println("Response code: " + responseCode);
                }

                conn.disconnect();
            } catch (Exception e) {
                System.out.println("Error al procesar el producto: " + codigoBarra);
                e.printStackTrace();
            }
        }
    }
}
