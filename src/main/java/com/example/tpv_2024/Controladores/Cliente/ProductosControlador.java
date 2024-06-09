package com.example.tpv_2024.Controladores.Cliente;

import com.example.tpv_2024.Modelos.Producto;
import com.example.tpv_2024.Servicio.ProductoService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.concurrent.CompletableFuture;

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
    private TableColumn<Producto, Integer> stockColumn;
    @FXML
    private TableColumn<Producto, Double> precioSuministradorColumn;
    @FXML
    private TableColumn<Producto, String> suministradorColumn;
    @FXML

    private TableColumn<Producto, String> categoriaColumn;
    @FXML
    private TableColumn<Producto, String> Column_pasilloAlmacen;
    @FXML
    private TableColumn<Producto, String> Column_estanteriaAlmacen;
    @FXML
    private TableColumn<Producto, String> Column_pasilloTienda;
    @FXML
    private TableColumn<Producto, String> Column_estanteriaTienda;
    @FXML
    private TextField codigoBarraField;
    @FXML
    private TextField nombreField;
    @FXML
    private TextField precioVentaField;
    @FXML
    private TextField stockField;
    @FXML
    private TextField precioSuministradorField;
    @FXML
    private TextField suministradorField;
    @FXML

    private TextField categoriaField;
    @FXML
    private TextField PasiAlmField;
    @FXML
    private TextField EstAlmField;
    @FXML
    private TextField PasiTieField;
    @FXML
    private TextField EstTieField;
    @FXML
    private Button addButton;
    @FXML
    private Button updateButton;
    @FXML
    private Button deleteButton;

    private ObservableList<Producto> productos;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        productos = FXCollections.observableArrayList();


        this.codigoBarraColumn.setCellValueFactory(new PropertyValueFactory<>("codigoBarra"));
        this.nombreColumn.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        this.precioVentaColumn.setCellValueFactory(new PropertyValueFactory<>("precioVenta"));
        this.precioVentaColumn.setCellFactory(tc -> new TableCell<Producto, Double>() {
            @Override
            protected void updateItem(Double precio, boolean empty) {
                super.updateItem(precio, empty);
                if (empty) {
                    setText(null);
                } else {
                    setText(String.format("%.2f", precio));
                }
            }
        });
        this.stockColumn.setCellValueFactory(new PropertyValueFactory<>("stock"));
        this.stockColumn.setCellFactory(tc -> new TableCell<Producto, Integer>() {
            @Override
            protected void updateItem(Integer stock, boolean empty) {
                super.updateItem(stock, empty);
                if (empty) {
                    setText(null);
                } else {
                    setText(String.valueOf(stock));
                }
            }
        });
        this.precioSuministradorColumn.setCellValueFactory(new PropertyValueFactory<>("precioSuministrador"));
        this.precioSuministradorColumn.setCellFactory(tc -> new TableCell<Producto, Double>() {
            @Override
            protected void updateItem(Double precio, boolean empty) {
                super.updateItem(precio, empty);
                if (empty) {
                    setText(null);
                } else {
                    setText(String.format("%.2f", precio));
                }
            }
        });
        this.suministradorColumn.setCellValueFactory(new PropertyValueFactory<>("suministrador"));
        this.categoriaColumn.setCellValueFactory(new PropertyValueFactory<>("categoria"));
        this.Column_pasilloAlmacen.setCellValueFactory(new PropertyValueFactory<>("pasilloAlmacen"));
        this.Column_estanteriaAlmacen.setCellValueFactory(new PropertyValueFactory<>("estanteriaAlmacen"));
        this.Column_pasilloTienda.setCellValueFactory(new PropertyValueFactory<>("pasilloTienda"));
        this.Column_estanteriaTienda.setCellValueFactory(new PropertyValueFactory<>("estanteriaTienda"));

        this.productosTable.setItems(productos);

        // Añadir listener para el campo del código de barra
        codigoBarraField.setOnAction(event -> comprobarProducto(codigoBarraField.getText()));
    }

    private void comprobarProducto(String codigo) {
        // Crear una tarea asíncrona para llamar al servidor
        CompletableFuture.runAsync(() -> {
            try {
                Producto producto = ProductoService.verificarProducto2(codigo);
                // Actualizar UI en el hilo de JavaFX
                javafx.application.Platform.runLater(() -> {
                    // Actualizar los campos de texto con los datos del producto
                    nombreField.setText(producto.getNombre());
                    precioVentaField.setText(String.valueOf(producto.getPrecioVenta()));
                    stockField.setText(String.valueOf(producto.getStock()));
                    precioSuministradorField.setText(String.valueOf(producto.getPrecioSuministrador()));
                    suministradorField.setText(producto.getSuministrador());
                    categoriaField.setText(producto.getCategoria());
                    PasiAlmField.setText(producto.getPasilloAlmacen());
                    EstAlmField.setText(producto.getEstanteriaAlmacen());
                    PasiTieField.setText(producto.getPasilloTienda());
                    EstTieField.setText(producto.getEstanteriaTienda());
                });
            } catch (Exception e) {
                // Manejar cualquier excepción
                javafx.application.Platform.runLater(() -> {
                    // Salir primero de la pantalla completa
                    Stage stage = (Stage) this.codigoBarraField.getScene().getWindow();
                    stage.setFullScreen(false);
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error");
                    alert.setHeaderText("Producto no encontrado");
                    alert.setContentText("El producto con el código de barra " + codigo + " no se encontró en la base de datos.");
                    alert.showAndWait();
                    stage.setFullScreen(true);
                });
                e.printStackTrace();
            }
        });
    }

    @FXML
    public void onAgregar(ActionEvent event) {
        try {
            System.out.println("Agregando producto en Gestión de Productos");
            String codigo = this.codigoBarraField.getText();
            String nombre = this.nombreField.getText();
            String categoria = this.categoriaField.getText();
            double precioVenta = Double.parseDouble(this.precioVentaField.getText());
            int stock = Integer.parseInt(this.stockField.getText());
            double precioSuministrador = Double.parseDouble(this.precioSuministradorField.getText());
            String suministrador = this.suministradorField.getText();
            double ganancia = precioVenta - precioSuministrador;
            String localizacion_id = ""; // Debes obtener el valor de localizacion_id de algún campo o generarlo automáticamente
            String pasilloAlmacen = this.PasiAlmField.getText();
            String estanteriaAlmacen = this.EstAlmField.getText();
            String pasilloTienda = this.PasiTieField.getText();
            String estanteriaTienda = this.EstTieField.getText();

            // Verificar si ya existe un producto con el mismo código de barra en la tabla
            Producto productoExistente = buscarProductoEnTabla(codigo);
            if (productoExistente != null) {
                // Mostrar un mensaje de error
                Stage stage = (Stage) this.addButton.getScene().getWindow();
                stage.setFullScreen(false);
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("Producto ya existe");
                alert.setContentText("Ya existe un producto con el código de barra " + codigo + " en la tabla.");
                alert.showAndWait();
                stage.setFullScreen(true);
                return;
            }

            // Si no existe, crear un nuevo producto y agregarlo a la tabla
            Producto producto = new Producto(codigo, nombre, categoria, precioVenta, stock, precioSuministrador, suministrador, ganancia, localizacion_id, pasilloAlmacen, estanteriaAlmacen, pasilloTienda, estanteriaTienda);
            productos.add(producto);

            // Limpiar los campos de entrada después de agregar el producto
            this.codigoBarraField.clear();
            this.nombreField.clear();
            this.categoriaField.clear();
            this.precioVentaField.clear();
            this.stockField.clear();
            this.precioSuministradorField.clear();
            this.suministradorField.clear();
            this.PasiAlmField.clear();
            this.EstAlmField.clear();
            this.PasiTieField.clear();
            this.EstTieField.clear();

            System.out.println("Producto agregado en Gestión de Productos");
        } catch (NumberFormatException e) {
            // Mostrar un mensaje de error si los campos numéricos no tienen un formato válido
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Formato inválido");
            alert.setContentText("Por favor, ingrese valores numéricos válidos para los campos de precio y stock.");
            alert.showAndWait();
        }
    }

    @FXML
    public void onBorrar(ActionEvent event) {
        Producto producto = productosTable.getSelectionModel().getSelectedItem();
        productos.remove(producto);
        // Limpiar los campos de entrada
        this.codigoBarraField.clear();
        this.nombreField.clear();
        this.categoriaField.clear();
        this.precioVentaField.clear();
        this.stockField.clear();
        this.precioSuministradorField.clear();
        this.suministradorField.clear();
        this.PasiAlmField.clear();
        this.EstAlmField.clear();
        this.PasiTieField.clear();
        this.EstTieField.clear();

    }

    private Producto buscarProductoEnTabla(String codigo) {
        for (Producto producto : productosTable.getItems()) {
            if (producto.getCodigoBarra().equals(codigo)) {
                return producto;
            }
        }
        return null;
    }

    @FXML
    public void onActualizar(ActionEvent event) {
        // Hace una petición PUT al servidor para actualizar todos los productos existentes en la tabla;
        // petición POST si el producto no existe, luego limpia la tabla
        try {
            ProductoService.actualizarProductos(productos);
            // Limpiar la tabla después de actualizar los productos
            productos.clear();
            // Mostrar un mensaje de éxito
            Stage stage = (Stage) this.updateButton.getScene().getWindow();
            stage.setFullScreen(false);
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Éxito");
            alert.setHeaderText("Productos actualizados");
            alert.setContentText("Los productos se han actualizado correctamente en el servidor.");
            alert.showAndWait();
            stage.setFullScreen(true);
        } catch (Exception e) {
            // Mostrar un mensaje de error si ocurre alguna excepción
            Stage stage = (Stage) this.updateButton.getScene().getWindow();
            stage.setFullScreen(false);
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Error al actualizar productos");
            alert.setContentText("Ha ocurrido un error al actualizar los productos en el servidor.");
            alert.showAndWait();
            stage.setFullScreen(true);
            e.printStackTrace();

        }
    }
}

