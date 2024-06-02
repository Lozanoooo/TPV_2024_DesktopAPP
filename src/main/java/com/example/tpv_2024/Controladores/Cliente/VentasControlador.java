package com.example.tpv_2024.Controladores.Cliente;

import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.net.URL;
import java.util.ResourceBundle;

public class VentasControlador implements Initializable {
    public TableView productTable;
    public TableColumn barcodeColumn;
    public TableColumn quantityColumn;
    public TableColumn productPriceColumn;
    public TableColumn productNameColumn;
    public TableColumn totalColumn;
    public Label totalQuantityLabel;
    public Label totalLabel;
    public Button agregar_btn;
    public Button pagar_btn;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
