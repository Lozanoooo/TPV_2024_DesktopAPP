package com.example.tpv_2024.Controladores.Cliente;

import com.example.tpv_2024.Modelos.Localizacion;
import com.example.tpv_2024.Modelos.Producto;
import com.example.tpv_2024.Servicio.RestClientService;
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
import org.apache.hc.core5.http.ParseException;

import java.io.IOException;
import java.net.URL;
import java.util.List;
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
    private TableColumn<Producto, Double> precioSuministradorColumn;
    @FXML
    private TableColumn<Producto, String> suministradorColumn;
    @FXML
    private TableColumn<Producto, Double> gananciaColumn;
    @FXML
    private TableColumn<Producto, String> fechaStockColumn;
    @FXML
    private TableColumn<Producto, String> idLocalizacionColumn;
    @FXML
    private TableColumn<Producto, String> pasilloTiendaColumn;
    @FXML
    private TableColumn<Producto, String> estanteriaTiendaColumn;
    @FXML
    private TableColumn<Producto, String> pasilloAlmacenColumn;
    @FXML
    private TableColumn<Producto, String> estanteriaAlmacenColumn;

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
    private TextField precioSuministradorField;
    @FXML
    private TextField suministradorField;
    @FXML
    private TextField gananciaField;
    @FXML
    private TextField fechaStockField;
    @FXML
    private TextField idLocalizacionField;
    @FXML
    private TextField pasilloTiendaField;
    @FXML
    private TextField estanteriaTiendaField;
    @FXML
    private TextField pasilloAlmacenField;
    @FXML
    private TextField estanteriaAlmacenField;

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
        precioSuministradorColumn.setCellValueFactory(new PropertyValueFactory<>("precioSuministrador"));
        suministradorColumn.setCellValueFactory(new PropertyValueFactory<>("suministrador"));
        gananciaColumn.setCellValueFactory(new PropertyValueFactory<>("ganancia"));
        fechaStockColumn.setCellValueFactory(new PropertyValueFactory<>("fechaStock"));
        idLocalizacionColumn.setCellValueFactory(new PropertyValueFactory<>("localizacion.idLocalizacion"));
        pasilloTiendaColumn.setCellValueFactory(new PropertyValueFactory<>("localizacion.pasilloTienda"));
        estanteriaTiendaColumn.setCellValueFactory(new PropertyValueFactory<>("localizacion.estanteriaTienda"));
        pasilloAlmacenColumn.setCellValueFactory(new PropertyValueFactory<>("localizacion.pasilloAlmacen"));
        estanteriaAlmacenColumn.setCellValueFactory(new PropertyValueFactory<>("localizacion.estanteriaAlmacen"));

        productosTable.setItems(productos);

        // Set editable columns
        productosTable.setEditable(true);
        codigoBarraColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        nombreColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        precioVentaColumn.setCellFactory(TextFieldTableCell.forTableColumn(new DoubleStringConverter()));
        categoriaColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        stockColumn.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
        precioSuministradorColumn.setCellFactory(TextFieldTableCell.forTableColumn(new DoubleStringConverter()));
        suministradorColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        gananciaColumn.setCellFactory(TextFieldTableCell.forTableColumn(new DoubleStringConverter()));
        fechaStockColumn.setCellFactory(TextFieldTableCell.forTableColumn());

        addButton.setOnAction(this::onAdd);
        deleteButton.setOnAction(this::onDelete);

        // Cargar productos desde el servidor
        loadProductos();
    }

    private void loadProductos() {
        try {
            List<Producto> productosList = RestClientService.getProductos();
            productos.setAll(productosList);
        } catch (IOException | ParseException e) {
            showAlert("Error al cargar productos", "No se pudieron cargar los productos desde el servidor");
        }
    }

    @FXML
    private void onAdd(ActionEvent event) {
        try {
            String codigoBarra = codigoBarraField.getText();
            String nombre = nombreField.getText();
            Double precioVenta = parseDouble(precioVentaField.getText());
            String categoria = categoriaField.getText();
            Integer stock = parseInt(stockField.getText());
            Double precioSuministrador = parseDouble(precioSuministradorField.getText());
            String suministrador = suministradorField.getText();
            Double ganancia = parseDouble(gananciaField.getText());
            String fechaStock = fechaStockField.getText();
            Integer idLocalizacion = parseInt(idLocalizacionField.getText());
            String pasilloTienda = pasilloTiendaField.getText();
            String estanteriaTienda = estanteriaTiendaField.getText();
            String pasilloAlmacen = pasilloAlmacenField.getText();
            String estanteriaAlmacen = estanteriaAlmacenField.getText();

            Localizacion localizacion = new Localizacion(idLocalizacion, pasilloTienda, estanteriaTienda, pasilloAlmacen, estanteriaAlmacen);
            Producto producto = new Producto(codigoBarra, nombre, precioVenta, categoria, stock, precioSuministrador, suministrador, ganancia, fechaStock, localizacion);

            // Añadir el producto a la base de datos
            Producto nuevoProducto = RestClientService.createProducto(producto);

            // Añadir el nuevo producto a la tabla
            productos.add(nuevoProducto);
            clearFields();
        } catch (Exception e) {
            showAlert("Error al agregar producto", "Por favor, ingrese los datos correctamente");
        }
    }

    @FXML
    private void onDelete(ActionEvent event) {
        Producto selectedProduct = productosTable.getSelectionModel().getSelectedItem();
        if (selectedProduct != null) {
            try {
                System.out.println("Eliminando producto con código de barra: " + selectedProduct.getCodigoBarra());
                boolean success = RestClientService.deleteProducto(selectedProduct.getCodigoBarra());
                if (success) {
                    productos.remove(selectedProduct);
                    System.out.println("Producto eliminado correctamente");
                } else {
                    showAlert("Error al eliminar producto", "No se pudo eliminar el producto del servidor");
                }
            } catch (IOException | ParseException e) {
                showAlert("Error al eliminar producto", "Ocurrió un error al intentar eliminar el producto");
                e.printStackTrace();
            }
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
        precioSuministradorField.clear();
        suministradorField.clear();
        gananciaField.clear();
        fechaStockField.clear();
        idLocalizacionField.clear();
        pasilloTiendaField.clear();
        estanteriaTiendaField.clear();
        pasilloAlmacenField.clear();
        estanteriaAlmacenField.clear();
    }

    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setContentText(content);
        alert.showAndWait();
    }

    private Double parseDouble(String text) {
        try {
            return text == null || text.isEmpty() ? null : Double.parseDouble(text);
        } catch (NumberFormatException e) {
            return null;
        }
    }

    private Integer parseInt(String text) {
        try {
            return text == null || text.isEmpty() ? null : Integer.parseInt(text);
        } catch (NumberFormatException e) {
            return null;
        }
    }
}
