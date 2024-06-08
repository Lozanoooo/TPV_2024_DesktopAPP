package com.example.tpv_2024.Controladores.Cliente;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.ResourceBundle;

public class HomeController implements Initializable {

    @FXML
    private ImageView miImagen;

    @FXML
    private Text txt_bienvenida;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    //Método para dar la bienvenida al usuario con su nombre
    public void setTxt_bienvenida(String nombre){
        txt_bienvenida.setText("¡ Bienvenido, "+nombre+" !");
    }


}
