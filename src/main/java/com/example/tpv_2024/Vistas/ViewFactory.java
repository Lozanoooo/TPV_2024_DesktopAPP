package com.example.tpv_2024.Vistas;

import com.example.tpv_2024.Controladores.Cliente.ClientControlador;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;

public class ViewFactory {
    // Vistas
    //Cada vez que se cambia el valor de abajo el listener de ClientControlador va ponerse en función
    private final StringProperty clienteVistaSeleccionada;

    private AnchorPane homeView;
    private VBox clientesView;

    private VBox ventasView;
    private VBox empleadosView;
    private VBox productosView;


    public ViewFactory(){
        this.clienteVistaSeleccionada = new SimpleStringProperty("");
    }

    /*
    * Vistas del Administrador
    * */

    //Una vez los objetos de vistas se hayan creado no se tienen que crear más por eso vista seleccionada:
    public StringProperty getClienteVistaSeleccionada() {
        return clienteVistaSeleccionada;
    }

    public AnchorPane getHomeView() {
        if (homeView == null) {
            try {
                homeView = new FXMLLoader(getClass().getResource("/FXML/Client/Home.fxml")).load();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return homeView;
    }

    public VBox getClientesView(){
        if(clientesView == null) {
            try {
                clientesView = new FXMLLoader(getClass().getResource("/FXML/Client/Cuentas.fxml")).load();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return clientesView;
    }

    public VBox getVentasView() {
        if (ventasView == null) {
            try {
                ventasView = new FXMLLoader(getClass().getResource("/FXML/Client/ventas.fxml")).load();
                // a ventana completa
                ventasView.setPrefWidth(1920);
                ventasView.setPrefHeight(1080);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return ventasView;
    }

    public VBox getEmpleadosView() {
        if (empleadosView == null) {
            try {
                empleadosView = new FXMLLoader(getClass().getResource("/FXML/Client/Empleados.fxml")).load();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return empleadosView;
    }

    public VBox getProductosView() {
        if (productosView == null) {
            try {
                productosView = new FXMLLoader(getClass().getResource("/FXML/Client/prdcts.fxml")).load();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return productosView;
    }

    public void showLoginWindow(){
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/FXML/Login.fxml"));
        createStage(loader);
    }

    public void showClientWindow(){
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/FXML/Client/Client.fxml"));
        ClientControlador clientControlador = new ClientControlador();
        loader.setController(clientControlador);
        createStage2(loader);
    }

    private void createStage(FXMLLoader loader) {
        Scene scene=null;
        try {
            scene = new Scene(loader.load());
        } catch (Exception e) {
            e.printStackTrace();
        }
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setTitle("TPV");
        stage.show();
    }

    private void createStage2(FXMLLoader loader) {
        Scene scene=null;
        try {
            scene = new Scene(loader.load());
        } catch (Exception e) {
            e.printStackTrace();
        }
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setTitle("TPV");
        stage.setFullScreen(true);
        stage.show();
    }

    public void CloseStage(Stage stage){
        stage.close();
    }


}
