package com.example.tpv_2024.Modelos;

import com.example.tpv_2024.Vistas.ViewFactory;

public class Modelo {
    private static Modelo modelo;
    private final ViewFactory viewFactory;

    public Modelo() {
        this.viewFactory = new ViewFactory();
    }

    public static synchronized Modelo getInstance(){
        if (modelo == null){
            modelo = new Modelo();
        }
        return modelo;
    }

    public ViewFactory getViewFactory() {
        return viewFactory;
    }

}
