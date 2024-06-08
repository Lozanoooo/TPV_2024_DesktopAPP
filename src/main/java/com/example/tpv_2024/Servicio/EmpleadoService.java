package com.example.tpv_2024.Servicio;

import com.example.tpv_2024.Modelos.Empleado;
import com.example.tpv_2024.util.HttpUtil;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class EmpleadoService {

    private static final String BASE_URL = "http://localhost:8080/empleados";

    public Empleado getEmpleadoPorId(String idEmpleado) {
        String url = BASE_URL + "/" + idEmpleado;
        try {
            String response = HttpUtil.sendGetRequest(url);
            ObjectMapper mapper = new ObjectMapper();
            System.out.println(response);
            return mapper.readValue(response, Empleado.class);
        } catch (IOException e) {
            throw new RuntimeException("Error al obtener el empleado: " + e.getMessage());
        }
    }

    public Empleado guardarEmpleado(Empleado empleado) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            String json = mapper.writeValueAsString(empleado);
            String response = HttpUtil.sendPostRequest(BASE_URL, json);
            return mapper.readValue(response, Empleado.class);
        } catch (IOException e) {
            throw new RuntimeException("Error al guardar el empleado: " + e.getMessage());
        }
    }

    public void eliminarEmpleado(String idEmpleado) {
        String url = BASE_URL + "/" + idEmpleado;
        try {
            HttpUtil.sendDeleteRequest(url);
        } catch (IOException e) {
            throw new RuntimeException("Error al eliminar el empleado: " + e.getMessage());
        }
    }

    public List<Empleado> listarEmpleados() {
        try {
            String response = HttpUtil.sendGetRequest(BASE_URL);
            ObjectMapper mapper = new ObjectMapper();
            Empleado[] empleados = mapper.readValue(response, Empleado[].class);
            return Arrays.asList(empleados);
        } catch (IOException e) {
            throw new RuntimeException("Error al obtener la lista de empleados: " + e.getMessage());
        }
    }
}