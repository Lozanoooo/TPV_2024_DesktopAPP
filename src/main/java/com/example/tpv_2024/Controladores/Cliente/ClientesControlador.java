package com.example.tpv_2024.Controladores.Cliente;

import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.net.URL;
import java.util.ResourceBundle;

public class ClientesControlador implements Initializable {
    public ListView clientes_tabla;
    public TableView clientesTable;
    public TableColumn idClienteColumn;
    public TableColumn apellidosColumn;
    public TableColumn telefonoColumn;
    public TableColumn direccionColumn;
    public TableColumn emailColumn;
    public TableColumn cifColumn;
    public TableColumn nombreColumn;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
