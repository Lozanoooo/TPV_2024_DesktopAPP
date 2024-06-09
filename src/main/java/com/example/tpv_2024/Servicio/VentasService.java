package com.example.tpv_2024.Servicio;

/*
* Esta clase se encarga de registrar las ventas de los productos (Con PK: idEmpleado → empleado; idCliente → cliente; codigoBarra → productos)
* en la base de datos del servidor
*
* */

public class VentasService {
    private static final String BASE_URL = "http://localhost:8080/";
    public static void registrarVenta(String idEmpleado, String idCliente, String codigoBarra) {
        // Aquí se debe implementar la lógica para registrar la venta en la base de datos
        System.out.println("Registrando venta: idEmpleado=" + idEmpleado + ", idCliente=" + idCliente + ", codigoBarra=" + codigoBarra);
    }
}
