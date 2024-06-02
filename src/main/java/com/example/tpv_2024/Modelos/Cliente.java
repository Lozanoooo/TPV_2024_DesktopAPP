package com.example.tpv_2024.Modelos;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.hc.client5.http.classic.methods.HttpDelete;
import org.apache.hc.client5.http.classic.methods.HttpPost;
import org.apache.hc.client5.http.classic.methods.HttpPut;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.core5.http.io.entity.StringEntity;

public class Cliente {

    private Long idCliente;
    private String nombre;
    private String apellidos;
    private String telefono;
    private String direccion;
    private String email;
    private String cif;

    // Getters and Setters
    public Long getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(Long idCliente) {
        this.idCliente = idCliente;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCif() {
        return cif;
    }

    public void setCif(String cif) {
        this.cif = cif;
    }

    // Método para crear un cliente en el servidor
    public static Cliente createCliente(Cliente cliente) throws IOException {
        String url = "http://localhost:8080/clientes";
        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(cliente);

        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            HttpPost request = new HttpPost(url);
            StringEntity entity = new StringEntity(json);
            request.setEntity(entity);
            request.setHeader("Content-type", "application/json");

            try (CloseableHttpResponse response = httpClient.execute(request)) {
                String responseBody = new String(response.getEntity().getContent().readAllBytes(), StandardCharsets.UTF_8);
                response.getEntity().getContent().close();
                return mapper.readValue(responseBody, Cliente.class);
            }
        }
    }

    // Método para obtener un cliente por ID
    public static Cliente getClienteById(Long id) throws IOException {
        String url = "http://localhost:8080/clientes/" + id;
        ObjectMapper mapper = new ObjectMapper();

        HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
        connection.setRequestMethod("GET");
        connection.setRequestProperty("Accept", "application/json");

        try (Scanner scanner = new Scanner(connection.getInputStream(), StandardCharsets.UTF_8.name())) {
            String jsonResponse = scanner.useDelimiter("\\A").next();
            return mapper.readValue(jsonResponse, Cliente.class);
        }
    }

    // Método para actualizar un cliente
    public static Cliente updateCliente(Long id, Cliente cliente) throws IOException {
        String url = "http://localhost:8080/clientes/" + id;
        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(cliente);

        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            HttpPut request = new HttpPut(url);
            StringEntity entity = new StringEntity(json);
            request.setEntity(entity);
            request.setHeader("Content-type", "application/json");

            try (CloseableHttpResponse response = httpClient.execute(request)) {
                String responseBody = new String(response.getEntity().getContent().readAllBytes(), StandardCharsets.UTF_8);
                response.getEntity().getContent().close();
                return mapper.readValue(responseBody, Cliente.class);
            }
        }
    }

    // Método para eliminar un cliente
    public static void deleteCliente(Long id) throws IOException {
        String url = "http://localhost:8080/clientes/" + id;

        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            HttpDelete request = new HttpDelete(url);

            try (CloseableHttpResponse response = httpClient.execute(request)) {
                response.getEntity().getContent().close();
            }
        }
    }
}
