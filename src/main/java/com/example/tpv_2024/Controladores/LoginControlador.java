package com.example.tpv_2024.Controladores;

import com.example.tpv_2024.Modelos.Modelo;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class LoginControlador implements Initializable {


    public Label usuario_lbl;
    public TextField usuario_fld;
    public Label pwd_lbl;
    public TextField pwd_fld;
    public Button login_btn;
    public Label error_lbl;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        login_btn.setOnAction(e -> onLogin());
    }

    private void onLogin(){
        Stage stage = (Stage) error_lbl.getScene().getWindow();
        Modelo.getInstance().getViewFactory().CloseStage(stage);
        Modelo.getInstance().getViewFactory().showClientWindow();
    }
}
