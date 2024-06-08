package com.example.tpv_2024.Servicio;

import com.example.tpv_2024.Modelos.Empleado;
import com.example.tpv_2024.util.HttpUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
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

    public Empleado guardarEmpleado(Empleado empleado) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            String json = mapper.writeValueAsString(empleado);
            String response = HttpUtil.sendPostRequest(BASE_URL, json);
            return mapper.readValue(response, Empleado.class);
        } catch (IOException e) {
            logger.error("Error al guardar el empleado: {}", e.getMessage());
            throw new RuntimeException("Error al guardar el empleado: " + e.getMessage(), e);
        }
    }

    public void eliminarEmpleado(String idEmpleado) {
        String url = BASE_URL + "/" + idEmpleado;
        try {
            HttpUtil.sendDeleteRequest(url);
        } catch (IOException e) {
            logger.error("Error al eliminar el empleado: {}", e.getMessage());
            throw new RuntimeException("Error al eliminar el empleado: " + e.getMessage(), e);
        }
    }

    public List<Empleado> listarEmpleados() {
        try {
            String response = HttpUtil.sendGetRequest(BASE_URL);
            ObjectMapper mapper = new ObjectMapper();
            Empleado[] empleados = mapper.readValue(response, Empleado[].class);
            return Arrays.asList(empleados);
        } catch (IOException e) {
            logger.error("Error al obtener la lista de empleados: {}", e.getMessage());
            throw new RuntimeException("Error al obtener la lista de empleados: " + e.getMessage(), e);
        }
    }
}
