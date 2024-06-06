package com.example.tpv_2024.Controladores.Cliente;

import com.example.tpv_2024.Modelos.ProductoModelo;
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
import java.util.concurrent.CompletableFuture;
import java.util.function.UnaryOperator;
import java.util.regex.Pattern;

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
    @FXML
    private RadioButton rbt_efectivo;
    @FXML
    private RadioButton rbt_visa;
    @FXML
    private TextField tf_EnEfectivo;
    @FXML
    private Label l_cambios;

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

        // En javaFX, los radioButtons no tienen un grupo por defecto, por lo que se debe hacer manualmente
        ToggleGroup group = new ToggleGroup();
        this.rbt_efectivo.setToggleGroup(group);
        this.rbt_visa.setToggleGroup(group);

        // Seleccionar efectivo por defecto
        this.rbt_efectivo.setSelected(true);

        // Configurar TextFormatter para tf_EnEfectivo
        Pattern validEditingState = Pattern.compile("-?((\\d*)|(\\d+\\.\\d*))");
        UnaryOperator<TextFormatter.Change> filter = change -> {
            String text = change.getControlNewText();
            if (validEditingState.matcher(text).matches()) {
                return change;
            } else {
                return null;
            }
        };
        TextFormatter<Double> textFormatter = new TextFormatter<>(new DoubleStringConverter(), null, filter);
        tf_EnEfectivo.setTextFormatter(textFormatter);

        // Añadir evento para Enter
        tf_EnEfectivo.setOnAction(this::calcularCambios);

        // Añadir listener para el campo del código de barra
        TF_Cod_Barra.setOnAction(event -> comprobarProducto(TF_Cod_Barra.getText()));
    }

    private void calcularCambios(ActionEvent actionEvent) {
        try {
            double total = Double.parseDouble(this.totalLabel.getText().replace("€", "").trim());
            double enEfectivo = Double.parseDouble(this.tf_EnEfectivo.getText());
            double cambios = enEfectivo - total;
            this.l_cambios.setText(String.format("€ %.2f", cambios));
        } catch (NumberFormatException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Error al calcular cambios");
            alert.setContentText("Por favor, ingrese una cantidad válida");
            alert.showAndWait();
        }
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

    private void metodoPago(ActionEvent event) {
        if (this.rbt_efectivo.isSelected()) {

        } else if (this.rbt_visa.isSelected()) {

        }
    }

    private void comprobarProducto(String codigo) {
        // Crear una tarea asíncrona para llamar al servidor
        CompletableFuture.runAsync(() -> {
            try {
                Ventas producto = ProductoModelo.verificarProducto(codigo);
                // Actualizar UI en el hilo de JavaFX
                javafx.application.Platform.runLater(() -> {
                    TF_Nombre.setText(producto.getNombre());
                    TF_Precio.setText(String.valueOf(producto.getPrecio()));
                    TF_Cantidad.setText(String.valueOf(producto.getCantidad()));
                    TF_Cantidad.setText("1");
                });
            } catch (Exception e) {
                // Manejar cualquier excepción
                javafx.application.Platform.runLater(() -> {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error");
                    alert.setHeaderText("Producto no encontrado");
                    alert.setContentText("El producto con el código de barra " + codigo + " no se encontró en la base de datos.");
                    alert.showAndWait();
                });
                e.printStackTrace();
            }
        });
    }
}
