package com.example.tpv_2024.Servicio;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.hc.core5.http.ParseException;
import com.example.tpv_2024.Modelos.Producto;
import com.example.tpv_2024.util.HttpUtil;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public class RestClientService {

    private static final String BASE_URL = "http://localhost:8080/";

    public static boolean authenticate(String idEmpleado, String contrasena) throws IOException, ParseException {
        String url = BASE_URL + "login";
        String jsonInputString = String.format("{\"idEmpleado\":\"%s\", \"contrasena\":\"%s\"}", idEmpleado, contrasena);
        String response = HttpUtil.sendPostRequest(url, jsonInputString);
        return response.equals("success");
    }

    public static Producto createProducto(Producto producto) throws IOException, ParseException {
        String url = BASE_URL + "productos";
        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(producto);
        String response = HttpUtil.sendPostRequest(url, json);
        return mapper.readValue(response, Producto.class);
    }

    public static List<Producto> getProductos() throws IOException, ParseException {
        String url = BASE_URL + "productos";
        String response = HttpUtil.sendGetRequest(url);
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(response, new TypeReference<List<Producto>>() {});
    }

    public static String post(String endpoint, Map<String, String> data) throws Exception {
        String url = BASE_URL + endpoint;
        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(data);
        return HttpUtil.sendPostRequest(url, json);
    }

    public static <T> T get(String endpoint, Class<T> responseType) throws IOException, ParseException {
        String url = BASE_URL + endpoint;
        String response = HttpUtil.sendGetRequest(url);
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(response, responseType);
    }

    public static <T> T put(String endpoint, T object) throws IOException, ParseException {
        String url = BASE_URL + endpoint;
        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(object);
        String response = HttpUtil.sendPutRequest(url, json);
        return mapper.readValue(response, (Class<T>) object.getClass());
    }

    public static boolean delete(String endpoint) throws IOException, ParseException {
        String url = BASE_URL + endpoint;
        String response = HttpUtil.sendDeleteRequest(url);
        return response.equals("success");
    }
}
