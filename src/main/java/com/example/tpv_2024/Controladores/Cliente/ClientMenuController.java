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
    public Button perfil_btn;
    public Button logout_btn;
    public Button report_btn;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        addListeners();
    }

    //Funcionalidad a los botones para que cambie la vista central
    private void addListeners() {
        home_btn.setOnAction(event -> onHome());
        clientes_btn.setOnAction(event -> onCuentas());
        ventas_btn.setOnAction(event -> onVentas());
    }

    private void onCuentas() {
        Modelo.getInstance().getViewFactory().getClienteVistaSeleccionada().set("Cuentas");
    }

    private void onHome() {
        Modelo.getInstance().getViewFactory().getClienteVistaSeleccionada().set("Home");
    }

    private void onVentas(){
        Modelo.getInstance().getViewFactory().getClienteVistaSeleccionada().set("Ventas");
    }
}
