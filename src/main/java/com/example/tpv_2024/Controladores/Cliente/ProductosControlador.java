package com.example.tpv_2024.Controladores.Cliente;

import com.example.tpv_2024.Modelos.Producto;
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

public class ProductosControlador implements Initializable {
    @FXML
    private TableView<Producto> productosTable;
    @FXML
    private TableColumn<Producto, String> codigoBarraColumn;
    @FXML
    private TableColumn<Producto, String> nombreColumn;
    @FXML
    private TableColumn<Producto, Double> precioVentaColumn;
    @FXML
    private TableColumn<Producto, String> categoriaColumn;
    @FXML
    private TableColumn<Producto, Integer> stockColumn;
    @FXML
    private TableColumn<Producto, String> idLocalizacionColumn;
    @FXML
    private TableColumn<Producto, Double> precioSuministradorColumn;
    @FXML
    private TableColumn<Producto, String> suministradorColumn;
    @FXML
    private TableColumn<Producto, Double> gananciaColumn;
    @FXML
    private TableColumn<Producto, String> fechaStockColumn;

    @FXML
    private TextField codigoBarraField;
    @FXML
    private TextField nombreField;
    @FXML
    private TextField precioVentaField;
    @FXML
    private TextField categoriaField;
    @FXML
    private TextField stockField;
    @FXML
    private TextField idLocalizacionField;
    @FXML
    private TextField precioSuministradorField;
    @FXML
    private TextField suministradorField;
    @FXML
    private TextField gananciaField;
    @FXML
    private TextField fechaStockField;

    @FXML
    private Button addButton;
    @FXML
    private Button deleteButton;

    private ObservableList<Producto> productos;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        productos = FXCollections.observableArrayList();

        codigoBarraColumn.setCellValueFactory(new PropertyValueFactory<>("codigoBarra"));
        nombreColumn.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        precioVentaColumn.setCellValueFactory(new PropertyValueFactory<>("precioVenta"));
        categoriaColumn.setCellValueFactory(new PropertyValueFactory<>("categoria"));
        stockColumn.setCellValueFactory(new PropertyValueFactory<>("stock"));
        idLocalizacionColumn.setCellValueFactory(new PropertyValueFactory<>("idLocalizacion"));
        precioSuministradorColumn.setCellValueFactory(new PropertyValueFactory<>("precioSuministrador"));
        suministradorColumn.setCellValueFactory(new PropertyValueFactory<>("suministrador"));
        gananciaColumn.setCellValueFactory(new PropertyValueFactory<>("ganancia"));
        fechaStockColumn.setCellValueFactory(new PropertyValueFactory<>("fechaStock"));

        productosTable.setItems(productos);

        // Set editable columns
        productosTable.setEditable(true);
        codigoBarraColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        nombreColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        precioVentaColumn.setCellFactory(TextFieldTableCell.forTableColumn(new DoubleStringConverter()));
        categoriaColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        stockColumn.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
        idLocalizacionColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        precioSuministradorColumn.setCellFactory(TextFieldTableCell.forTableColumn(new DoubleStringConverter()));
        suministradorColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        gananciaColumn.setCellFactory(TextFieldTableCell.forTableColumn(new DoubleStringConverter()));
        fechaStockColumn.setCellFactory(TextFieldTableCell.forTableColumn());

        addButton.setOnAction(this::onAdd);
        deleteButton.setOnAction(this::onDelete);
    }

    @FXML
    private void onAdd(ActionEvent event) {
        try {
            String codigoBarra = codigoBarraField.getText();
            String nombre = nombreField.getText();
            double precioVenta = Double.parseDouble(precioVentaField.getText());
            String categoria = categoriaField.getText();
            int stock = Integer.parseInt(stockField.getText());
            String idLocalizacion = idLocalizacionField.getText();
            double precioSuministrador = Double.parseDouble(precioSuministradorField.getText());
            String suministrador = suministradorField.getText();
            double ganancia = Double.parseDouble(gananciaField.getText());
            String fechaStock = fechaStockField.getText();

            Producto producto = new Producto(codigoBarra, nombre, precioVenta, categoria, stock, idLocalizacion, precioSuministrador, suministrador, ganancia, fechaStock);
            productos.add(producto);
            clearFields();
        } catch (NumberFormatException e) {
            showAlert("Error al agregar producto", "Por favor, ingrese los datos correctamente");
        }
    }

    @FXML
    private void onDelete(ActionEvent event) {
        Producto selectedProduct = productosTable.getSelectionModel().getSelectedItem();
        if (selectedProduct != null) {
            productos.remove(selectedProduct);
        } else {
            showAlert("Error al eliminar producto", "Por favor, seleccione un producto para eliminar");
        }
    }

    private void clearFields() {
        codigoBarraField.clear();
        nombreField.clear();
        precioVentaField.clear();
        categoriaField.clear();
        stockField.clear();
        idLocalizacionField.clear();
        precioSuministradorField.clear();
        suministradorField.clear();
        gananciaField.clear();
        fechaStockField.clear();
    }

    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
