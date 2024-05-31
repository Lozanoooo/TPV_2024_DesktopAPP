package com.example.tpv_2024.Vistas;

import com.example.tpv_2024.Controladores.Cliente.ClienteControlador;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public class ViewFactory {
    // Vistas
    private AnchorPane homeView;

    public ViewFactory(){}

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

    public void showLoginWindow(){
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/FXML/Login.fxml"));
        createStage(loader);
    }

    public void showClientWindow(){
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/FXML/Client/Client.fxml"));
        ClienteControlador clienteControlador = new ClienteControlador();
        loader.setController(clienteControlador);
        createStage(loader);
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

    public void CloseStage(Stage stage){
        stage.close();
    }


}
