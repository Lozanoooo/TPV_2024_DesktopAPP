package com.example.tpv_2024.Controladores.Cliente;

import com.example.tpv_2024.Modelos.Modelo;
import javafx.fxml.Initializable;
import javafx.scene.layout.BorderPane;

import java.net.URL;
import java.util.ResourceBundle;

public class ClientControlador implements Initializable{

    public BorderPane padre;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Modelo.getInstance().getViewFactory().getClienteVistaSeleccionada().addListener((observableValue, valorViejo, valorNuevo) -> {
            switch (valorNuevo) {
                //Metodo para cambiar las vistas del centro cambiandose el valor de StringProperty (Que se va cambiar desde el CleinteMenuCOntrolador)
                case "Cuentas" -> padre.setCenter(Modelo.getInstance().getViewFactory().getClientesView());
                case "Ventas" -> padre.setCenter(Modelo.getInstance().getViewFactory().getVentasView());
                case "Empleados" -> padre.setCenter(Modelo.getInstance().getViewFactory().getEmpleadosView());
                case "Productos" -> padre.setCenter(Modelo.getInstance().getViewFactory().getProductosView());
                case "Home" -> padre.setCenter(Modelo.getInstance().getViewFactory().getHomeView());
                default -> padre.setCenter(Modelo.getInstance().getViewFactory().getHomeView());
            }
        });
    }
}
