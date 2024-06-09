package com.example.tpv_2024.Servicio;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/*
 * Esta clase se encarga de registrar las ventas de los productos (Con PK: idEmpleado → empleado; idCliente → cliente; codigoBarra → productos)
 * en la base de datos del servidor
 *
 * */

public class VentasService {
    private static final String BASE_URL = "http://localhost:8080/";

    public static void registrarVenta(String idEmpleado, String idCliente, List<String> codigosBarras) {
        System.out.println("Registrando venta: idEmpleado=" + idEmpleado + ", idCliente=" + idCliente + ", codigosBarras=" + codigosBarras);

        // Crear el objeto JSON para el cuerpo de la solicitud
        Map<String, Object> ventaData = new HashMap<>();
        ventaData.put("empleado", createEmpleadoData(idEmpleado));
        ventaData.put("cliente", createClienteData(idCliente));
        ventaData.put("productos", createProductosData(codigosBarras));

        // Convertir el objeto JSON a una cadena
        ObjectMapper objectMapper = new ObjectMapper();
        String jsonBody;
        try {
            jsonBody = objectMapper.writeValueAsString(ventaData);
        } catch (IOException e) {
            System.out.println("Error al convertir el objeto JSON a una cadena: " + e.getMessage());
            return;
        }

        // Crear la solicitud POST
        HttpPost httpPost = new HttpPost(BASE_URL + "ventas");
        StringEntity entity = new StringEntity(jsonBody, "UTF-8");
        httpPost.setEntity(entity);
        httpPost.setHeader("Content-type", "application/json");

        // Enviar la solicitud POST
        try (CloseableHttpClient httpClient = HttpClients.createDefault();
             CloseableHttpResponse response = httpClient.execute(httpPost)) {

            // Verificar el código de respuesta
            int statusCode = response.getStatusLine().getStatusCode();
            if (statusCode == 200) {
                HttpEntity responseEntity = response.getEntity();
                String responseBody = EntityUtils.toString(responseEntity);
                System.out.println("Venta registrada exitosamente. Respuesta del servidor: " + responseBody);
            } else {
                System.out.println("Error al registrar la venta. Código de respuesta: " + statusCode);
            }
        } catch (IOException e) {
            System.out.println("Error al enviar la solicitud POST: " + e.getMessage());
        }
    }

    private static Map<String, Object> createEmpleadoData(String idEmpleado) {
        Map<String, Object> empleadoData = new HashMap<>();
        empleadoData.put("idEmpleado", Integer.parseInt(idEmpleado));
        return empleadoData;
    }

    private static Map<String, Object> createClienteData(String idCliente) {
        Map<String, Object> clienteData = new HashMap<>();
        if (!idCliente.isEmpty()) {
            clienteData.put("idCliente", Integer.parseInt(idCliente));
        }
        return clienteData;
    }

    private static List<Map<String, String>> createProductosData(List<String> codigosBarras) {
        List<Map<String, String>> productosData = new ArrayList<>();
        for (String codigoBarra : codigosBarras) {
            Map<String, String> productoData = new HashMap<>();
            productoData.put("codigoBarra", codigoBarra);
            productosData.add(productoData);
        }
        return productosData;
    }
}