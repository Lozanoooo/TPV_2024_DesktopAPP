package com.example.tpv_2024.Controladores.Cliente;

import com.example.tpv_2024.Modelos.Modelo;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;

import java.net.URL;
import java.util.ResourceBundle;

public class ClientMenuController implements Initializable {


    public Button home_btn;
    public Button productos_btn;
    public Button ventas_btn;
    public Button clientes_btn;
    public Button logout_btn;
    public Button report_btn;
    public Button empleados_btn;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        addListeners();
    }

    //Funcionalidad a los botones para que cambie la vista central
    private void addListeners() {
        home_btn.setOnAction(event -> onHome());
        clientes_btn.setOnAction(event -> onClientes());
        ventas_btn.setOnAction(event -> onVentas());
        empleados_btn.setOnAction(event -> onEmpleados());
        productos_btn.setOnAction(event -> onProductos());
    }

    private void onClientes() {
        Modelo.getInstance().getViewFactory().getClienteVistaSeleccionada().set("Cuentas");
    }

    private void onHome() {
        Modelo.getInstance().getViewFactory().getClienteVistaSeleccionada().set("Home");
    }

    private void onVentas(){
        Modelo.getInstance().getViewFactory().getClienteVistaSeleccionada().set("Ventas");
    }

    private void onEmpleados(){
        Modelo.getInstance().getViewFactory().getClienteVistaSeleccionada().set("Empleados");
    }

    private void onProductos(){
        Modelo.getInstance().getViewFactory().getClienteVistaSeleccionada().set("Productos");
    }

    //Funcionalidad al botón de cerrar sesión
    public void onLogout() {

    }
}
