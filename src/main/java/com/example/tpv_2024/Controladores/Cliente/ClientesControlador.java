package com.example.tpv_2024.Controladores.Cliente;

import com.example.tpv_2024.Modelos.Cliente;
import com.example.tpv_2024.Servicio.ClienteService;
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

public class ClientesControlador implements Initializable {

    @FXML
    private Button BT_actualizar;
    @FXML
    private Button BT_buscar;
    @FXML
    private Button BT_eliminar;
    @FXML
    private Button BT_agregar;
    @FXML
    private TextField TF_buscarPorID;
    @FXML
    private TableView<Cliente> clientesTable;
    @FXML
    private TableColumn<Cliente, Long> idClienteColumn;
    @FXML
    private TableColumn<Cliente, String> nombreColumn;
    @FXML
    private TableColumn<Cliente, String> apellidosColumn;
    @FXML
    private TableColumn<Cliente, String> direccionColumn;
    @FXML
    private TableColumn<Cliente, String> emailColumn;
    @FXML
    private TableColumn<Cliente, String> telefonoColumn;
    @FXML
    private TableColumn<Cliente, String> cifColumn;
    @FXML
    private TextField nombreField;
    @FXML
    private TextField apellidosField;
    @FXML
    private TextField direccionField;
    @FXML
    private TextField emailField;
    @FXML
    private TextField telefonoField;
    @FXML
    private TextField cifField;

    private ObservableList<Cliente> clientes;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        clientes = FXCollections.observableArrayList();

        this.idClienteColumn.setCellValueFactory(new PropertyValueFactory<>("idCliente"));
        this.nombreColumn.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        this.apellidosColumn.setCellValueFactory(new PropertyValueFactory<>("apellidos"));
        this.direccionColumn.setCellValueFactory(new PropertyValueFactory<>("direccion"));
        this.emailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));
        this.telefonoColumn.setCellValueFactory(new PropertyValueFactory<>("telefono"));
        this.cifColumn.setCellValueFactory(new PropertyValueFactory<>("cif"));

        this.clientesTable.setItems(clientes);

        // Añadir listener para el campo de búsqueda por ID
        TF_buscarPorID.setOnAction(event -> buscarCliente(TF_buscarPorID.getText()));
    }

    private void buscarCliente(String idCliente) {
        // Crear una tarea asíncrona para llamar al servidor
        CompletableFuture.runAsync(() -> {
            try {
                Cliente cliente = ClienteService.buscarCliente(idCliente);
                // Actualizar UI en el hilo de JavaFX
                javafx.application.Platform.runLater(() -> {
                    // Actualizar los campos de texto con los datos del cliente
                    nombreField.setText(cliente.getNombre());
                    apellidosField.setText(cliente.getApellidos());
                    direccionField.setText(cliente.getDireccion());
                    emailField.setText(cliente.getEmail());
                    telefonoField.setText(cliente.getTelefono());
                    cifField.setText(cliente.getCif());
                });
            } catch (Exception e) {
                // Manejar cualquier excepción
                javafx.application.Platform.runLater(() -> {
                    // Salir primero de la pantalla completa
                    Stage stage = (Stage) this.TF_buscarPorID.getScene().getWindow();
                    stage.setFullScreen(false);
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error");
                    alert.setHeaderText("Cliente no encontrado");
                    alert.setContentText("El cliente con el ID " + idCliente + " no se encontró en la base de datos.");
                    alert.showAndWait();
                    stage.setFullScreen(true);
                });
                e.printStackTrace();
            }
        });
    }

    @FXML
    public void onActualizar(ActionEvent event) {
        // Hace una petición PUT al servidor para actualizar todos los clientes existentes en la tabla;
        // petición POST si el cliente no existe, luego limpia la tabla
        try {
            ClienteService.actualizarClientes(clientes);
            // Limpiar la tabla después de actualizar los clientes
            clientes.clear();
            // Mostrar un mensaje de éxito
            Stage stage = (Stage) this.BT_actualizar.getScene().getWindow();
            stage.setFullScreen(false);
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Éxito");
            alert.setHeaderText("Clientes actualizados");
            alert.setContentText("Los clientes se han actualizado correctamente en el servidor.");
            alert.showAndWait();
            stage.setFullScreen(true);
        } catch (Exception e) {
            // Mostrar un mensaje de error si ocurre alguna excepción
            Stage stage = (Stage) this.BT_actualizar.getScene().getWindow();
            stage.setFullScreen(false);
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Error al actualizar clientes");
            alert.setContentText("Ha ocurrido un error al actualizar los clientes en el servidor.");
            alert.showAndWait();
            stage.setFullScreen(true);
            e.printStackTrace();
        }
    }

    @FXML
    public void onAdd(ActionEvent event) {
        try {
            System.out.println("Agregando cliente");
            String ID = TF_buscarPorID.getText();
            String nombre = this.nombreField.getText();
            String apellidos = this.apellidosField.getText();
            String direccion = this.direccionField.getText();
            String email = this.emailField.getText();
            String telefono = this.telefonoField.getText();
            String cif = this.cifField.getText();

            // Crear un nuevo cliente
            Cliente cliente = new Cliente();
            cliente.setIdCliente(Long.parseLong(ID));
            cliente.setNombre(nombre);
            cliente.setApellidos(apellidos);
            cliente.setDireccion(direccion);
            cliente.setEmail(email);
            cliente.setTelefono(telefono);
            cliente.setCif(cif);

            // Agregar el cliente a la lista observable
            clientes.add(cliente);

            // Limpiar los campos de entrada después de agregar el cliente
            this.TF_buscarPorID.clear();
            this.nombreField.clear();
            this.apellidosField.clear();
            this.direccionField.clear();
            this.emailField.clear();
            this.telefonoField.clear();
            this.cifField.clear();

            System.out.println("Cliente agregado");
        } catch (Exception e) {
            // Mostrar un mensaje de error si ocurre alguna excepción
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Error al agregar cliente");
            alert.setContentText("Ha ocurrido un error al agregar el cliente.");
            alert.showAndWait();
            e.printStackTrace();
        }
    }

    @FXML
    public void onDelete(ActionEvent event) {
        Cliente clienteSeleccionado = clientesTable.getSelectionModel().getSelectedItem();
        // Limpiar los campos de entrada
        this.TF_buscarPorID.clear();
        this.nombreField.clear();
        this.apellidosField.clear();
        this.direccionField.clear();
        this.emailField.clear();
        this.telefonoField.clear();
        this.cifField.clear();
        if (clienteSeleccionado != null) {
            // Eliminar el cliente de la lista observable
            clientes.remove(clienteSeleccionado);

            System.out.println("Cliente eliminado");

        }
    }
}