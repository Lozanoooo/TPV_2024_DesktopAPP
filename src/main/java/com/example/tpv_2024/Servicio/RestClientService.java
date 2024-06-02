package com.example.tpv_2024.Servicio;


import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.hc.core5.http.ParseException;
import com.example.tpv_2024.Modelos.Cliente;
import com.example.tpv_2024.util.HttpUtil;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Map;

public class RestClientService {

    private static final String BASE_URL = "http://localhost:8080/";

    public static boolean authenticate(String idEmpleado, String contrasena) throws IOException, ParseException {
        String url = BASE_URL + "/login";
        String jsonInputString = String.format("{\"idEmpleado\":\"%s\", \"contrasena\":\"%s\"}", idEmpleado, contrasena);
        String response = HttpUtil.sendPostRequest(url, jsonInputString);
        return response.equals("success");
    }

    public static Cliente createCliente(Cliente cliente) throws IOException, ParseException {
        String url = BASE_URL + "/clientes";
        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(cliente);
        String response = HttpUtil.sendPostRequest(url, json);
        return mapper.readValue(response, Cliente.class);
    }

    public static String post(String endpoint, Map<String, String> data) throws Exception {
        String url = BASE_URL + endpoint;
        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(data);
        return HttpUtil.sendPostRequest(url, json);
    }

    // Otros métodos para GET, PUT, DELETE
}
