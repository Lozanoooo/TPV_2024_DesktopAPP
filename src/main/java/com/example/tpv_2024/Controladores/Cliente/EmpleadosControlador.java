package com.example.tpv_2024.Controladores.Cliente;

import com.example.tpv_2024.Modelos.Empleado;
import com.example.tpv_2024.Servicio.EmpleadoService;
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

public class EmpleadosControlador implements Initializable {
    @FXML
    private TableView<Empleado> empleadosTable;
    @FXML
    private TableColumn<Empleado, Long> idEmpleadoColumn;
    @FXML
    private TableColumn<Empleado, String> nombreColumn;
    @FXML
    private TableColumn<Empleado, String> apellidosColumn;
    @FXML
    private TableColumn<Empleado, String> puestoColumn;
    @FXML
    private TextField idEmpleadoField;
    @FXML
    private TextField nombreField;
    @FXML
    private TextField apellidosField;
    @FXML
    private TextField puestoField;
    @FXML
    private Button addButton;
    @FXML
    private Button updateButton;
    @FXML
    private Button deleteButton;

    private ObservableList<Empleado> empleados;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        empleados = FXCollections.observableArrayList();

        this.idEmpleadoColumn.setCellValueFactory(new PropertyValueFactory<>("idEmpleado"));
        this.nombreColumn.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        this.apellidosColumn.setCellValueFactory(new PropertyValueFactory<>("apellidos"));
        this.puestoColumn.setCellValueFactory(new PropertyValueFactory<>("puesto"));

        this.empleadosTable.setItems(empleados);

        // Añadir listener para el campo de búsqueda por ID
        idEmpleadoField.setOnAction(event -> buscarEmpleado(idEmpleadoField.getText()));
    }

    private void buscarEmpleado(String idEmpleado) {
        // Crear una tarea asíncrona para llamar al servidor
        CompletableFuture.runAsync(() -> {
            try {
                Empleado empleado = EmpleadoService.buscarEmpleado(idEmpleado);
                // Actualizar UI en el hilo de JavaFX
                javafx.application.Platform.runLater(() -> {
                    // Actualizar los campos de texto con los datos del empleado
                    nombreField.setText(empleado.getNombre());
                    apellidosField.setText(empleado.getApellidos());
                    puestoField.setText(empleado.getPuesto());
                });
            } catch (Exception e) {
                // Manejar cualquier excepción
                javafx.application.Platform.runLater(() -> {
                    // Salir primero de la pantalla completa
                    Stage stage = (Stage) this.idEmpleadoField.getScene().getWindow();
                    stage.setFullScreen(false);
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error");
                    alert.setHeaderText("Empleado no encontrado");
                    alert.setContentText("El empleado con el ID " + idEmpleado + " no se encontró en la base de datos.");
                    alert.showAndWait();
                    stage.setFullScreen(true);
                });
                e.printStackTrace();
            }
        });
    }

    @FXML
    public void onAdd(ActionEvent event) {
        try {
            System.out.println("Agregando empleado");
            String idEmpleado = this.idEmpleadoField.getText();
            String nombre = this.nombreField.getText();
            String apellidos = this.apellidosField.getText();
            String puesto = this.puestoField.getText();

            // Crear un nuevo empleado
            Empleado empleado = new Empleado();
            empleado.setIdEmpleado(Integer.parseInt(idEmpleado));
            empleado.setNombre(nombre);
            empleado.setApellidos(apellidos);
            empleado.setPuesto(puesto);

            // Agregar el empleado a la lista observable
            empleados.add(empleado);

            // Limpiar los campos de entrada después de agregar el empleado
            this.idEmpleadoField.clear();
            this.nombreField.clear();
            this.apellidosField.clear();
            this.puestoField.clear();

            System.out.println("Empleado agregado");
        } catch (Exception e) {
            // Mostrar un mensaje de error si ocurre alguna excepción
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Error al agregar empleado");
            alert.setContentText("Ha ocurrido un error al agregar el empleado.");
            alert.showAndWait();
            e.printStackTrace();
        }
    }

    @FXML
    public void onDelete(ActionEvent event) {
        Empleado empleadoSeleccionado = empleadosTable.getSelectionModel().getSelectedItem();
        if (empleadoSeleccionado != null) {
            // Eliminar el empleado de la lista observable
            empleados.remove(empleadoSeleccionado);

            // Limpiar los campos de entrada
            this.idEmpleadoField.clear();
            this.nombreField.clear();
            this.apellidosField.clear();
            this.puestoField.clear();

            System.out.println("Empleado eliminado");
        }
    }

    @FXML
    public void onUpdate(ActionEvent event) {
        // Hace una petición PUT al servidor para actualizar todos los empleados existentes en la tabla;
        // petición POST si el empleado no existe, luego limpia la tabla
        try {
            EmpleadoService.actualizarEmpleados(empleados);
            // Limpiar la tabla después de actualizar los empleados
            empleados.clear();
            // Mostrar un mensaje de éxito
            Stage stage = (Stage) this.updateButton.getScene().getWindow();
            stage.setFullScreen(false);
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Éxito");
            alert.setHeaderText("Empleados actualizados");
            alert.setContentText("Los empleados se han actualizado correctamente en el servidor.");
            alert.showAndWait();
            stage.setFullScreen(true);
        } catch (Exception e) {
            // Mostrar un mensaje de error si ocurre alguna excepción
            Stage stage = (Stage) this.updateButton.getScene().getWindow();
            stage.setFullScreen(false);
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Error al actualizar empleados");
            alert.setContentText("Ha ocurrido un error al actualizar los empleados en el servidor.");
            alert.showAndWait();
            stage.setFullScreen(true);
            e.printStackTrace();
        }
    }
}