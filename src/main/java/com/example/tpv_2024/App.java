package com.example.tpv_2024;

import com.example.tpv_2024.Modelos.Modelo;
import javafx.application.Application;
import javafx.stage.Stage;

public class App extends Application {
    @Override
    public void start(Stage stage) {
//        ViewFactory viewFactory = new ViewFactory();
//        viewFactory.showLoginWindow();
        Modelo.getInstance().getViewFactory().showLoginWindow();
    }
}
