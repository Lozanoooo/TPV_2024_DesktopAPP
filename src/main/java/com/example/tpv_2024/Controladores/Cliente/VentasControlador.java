package com.example.tpv_2024.Controladores.Cliente;

import com.example.tpv_2024.Modelos.Ventas;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.util.converter.DoubleStringConverter;
import javafx.util.converter.IntegerStringConverter;

import java.net.URL;
import java.util.ResourceBundle;

public class VentasControlador implements Initializable {
    @FXML
    public TableView<Ventas> productTable;
    @FXML
    public TableColumn<Ventas, String> barcodeColumn;
    @FXML
    public TableColumn<Ventas, Integer> quantityColumn;
    @FXML
    public TableColumn<Ventas, Double> productPriceColumn;
    @FXML
    public TableColumn<Ventas, String> productNameColumn;
    @FXML
    public TableColumn<Ventas, Double> totalColumn;
    @FXML
    public Label totalQuantityLabel;
    @FXML
    public Label totalLabel;
    @FXML
    public TextField TF_Cantidad;
    @FXML
    public TextField TF_Cod_Barra;
    @FXML
    public TextField TF_Nombre;
    @FXML
    public TextField TF_Precio;
    @FXML
    public Button agregar_btn;
    @FXML
    public Button pagar_btn;

    public ObservableList<Ventas> productos;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        productos = FXCollections.observableArrayList();

        this.productNameColumn.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        this.barcodeColumn.setCellValueFactory(new PropertyValueFactory<>("codigo"));
        this.productPriceColumn.setCellValueFactory(new PropertyValueFactory<>("precio"));
        this.quantityColumn.setCellValueFactory(new PropertyValueFactory<>("cantidad"));
        this.totalColumn.setCellValueFactory(new PropertyValueFactory<>("total"));

        this.quantityColumn.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
        this.productPriceColumn.setCellFactory(TextFieldTableCell.forTableColumn(new DoubleStringConverter()));
        this.totalColumn.setCellFactory(TextFieldTableCell.forTableColumn(new DoubleStringConverter()));

        this.productTable.setItems(productos);
    }

    @FXML
    public void onAgregar(ActionEvent event) {
        try {
            System.out.println("Agregando producto");
            String nombre = this.TF_Nombre.getText();
            String codigo = this.TF_Cod_Barra.getText();
            double precio = Double.parseDouble(this.TF_Precio.getText());
            int cantidad = Integer.parseInt(this.TF_Cantidad.getText());
            double total = precio * cantidad;
            Ventas producto = new Ventas(nombre, codigo, precio, cantidad, total);
            productos.add(producto);
            this.productTable.setItems(productos);
            this.TF_Nombre.clear();
            this.TF_Cod_Barra.clear();
            this.TF_Precio.clear();
            this.TF_Cantidad.clear();
            updateTotal();
            updateCantidad();
        } catch (NumberFormatException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Error al agregar producto");
            alert.setContentText("Por favor, ingrese los datos correctamente");
            alert.showAndWait();
        }
    }

    public void onPagar(ActionEvent event) {
        System.out.println("Pagando");

    }

    public void updateTotal() {
        double total = 0;
        for (Ventas producto : productos) {
            total += producto.getTotal();
        }
        this.totalLabel.setText(String.format("€ %.2f",total));
    }

    public  void updateCantidad(){
        int cantidad = 0;
        for (Ventas producto : productos) {
            cantidad += producto.getCantidad();
        }
        this.totalQuantityLabel.setText(String.valueOf(cantidad));
    }

    private double calculateTotal() {
        double total = 0;
        for (Ventas producto : productos) {
            total += producto.getTotal();
        }
        return total;
    }
}
