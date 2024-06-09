package com.example.tpv_2024.Controladores.Cliente;

import com.example.tpv_2024.Modelos.Modelo;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;

import java.net.URISyntaxException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

import java.awt.Desktop;
import java.net.URI;
import java.io.IOException;

public class ClientMenuController implements Initializable {


    public Button home_btn;
    public Button productos_btn;
    public Button ventas_btn;
    public Button clientes_btn;
    public Button logout_btn;
    public Button report_btn;
    public Button empleados_btn;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        addListeners();
    }

    //Funcionalidad a los botones para que cambie la vista central
    private void addListeners() {
        home_btn.setOnAction(event -> onHome());
        clientes_btn.setOnAction(event -> onClientes());
        ventas_btn.setOnAction(event -> onVentas());
        empleados_btn.setOnAction(event -> onEmpleados());
        productos_btn.setOnAction(event -> onProductos());
    }

    private void onClientes() {
        Modelo.getInstance().getViewFactory().getClienteVistaSeleccionada().set("Cuentas");
    }

    private void onHome() {
        Modelo.getInstance().getViewFactory().getClienteVistaSeleccionada().set("Home");
    }

    private void onVentas(){
        Modelo.getInstance().getViewFactory().getClienteVistaSeleccionada().set("Ventas");
    }

    private void onEmpleados(){
        Modelo.getInstance().getViewFactory().getClienteVistaSeleccionada().set("Empleados");
    }

    private void onProductos(){
        Modelo.getInstance().getViewFactory().getClienteVistaSeleccionada().set("Productos");
    }

    //Funcionalidad al botón de cerrar sesión
    public void onLogout() {
        // Obtener la ventana principal y salir de la ventana completa
        Stage stage = (Stage) logout_btn.getScene().getWindow();
        stage.setFullScreen(false);
        // Crear el pop-up de confirmación
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmación");
        alert.setHeaderText("¿Estás seguro de que deseas cerrar la aplicación?");

        // Mostrar el pop-up y esperar la respuesta del usuario
        Optional<ButtonType> result = alert.showAndWait();

        // Verificar la respuesta del usuario
        if (result.isPresent() && result.get() == ButtonType.OK) {
            // El usuario confirmó el cierre de sesión, salir de la aplicación
            System.exit(0);
        }else {
            stage.setFullScreen(true);
        }
    }

    public void onReport(){
        // salir de la ventana completa y abrir el correo para enviar el reporte
        Stage stage = (Stage) report_btn.getScene().getWindow();
        stage.setFullScreen(false);
        try {
            String recipient = "binaryglobalsl@gmail.com";
            String subject = "ReporteTPV2024_ID:001";
            openMailClient(recipient, subject);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void openMailClient(String recipient, String subject) {
        if (Desktop.isDesktopSupported() && Desktop.getDesktop().isSupported(Desktop.Action.MAIL)) {
            try {
                URI mailto = new URI("mailto:" + recipient + "?subject=" + subject);

                Desktop.getDesktop().mail(mailto);
            } catch (IOException | URISyntaxException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("Desktop mail not supported");
        }
    }
}
