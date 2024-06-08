package com.example.tpv_2024.Modelos;

public class Localizacion {
    private int idLocalizacion;
    private String pasilloTienda;
    private String estanteriaTienda;
    private String pasilloAlmacen;
    private String estanteriaAlmacen;

    public Localizacion() {
        // Constructor por defecto
    }

    public Localizacion(int idLocalizacion, String pasilloTienda, String estanteriaTienda, String pasilloAlmacen, String estanteriaAlmacen) {
        this.idLocalizacion = idLocalizacion;
        this.pasilloTienda = pasilloTienda;
        this.estanteriaTienda = estanteriaTienda;
        this.pasilloAlmacen = pasilloAlmacen;
        this.estanteriaAlmacen = estanteriaAlmacen;
    }

    // Getters and Setters

    public int getIdLocalizacion() { return idLocalizacion; }
    public void setIdLocalizacion(int idLocalizacion) { this.idLocalizacion = idLocalizacion; }

    public String getPasilloTienda() { return pasilloTienda; }
    public void setPasilloTienda(String pasilloTienda) { this.pasilloTienda = pasilloTienda; }

    public String getEstanteriaTienda() { return estanteriaTienda; }
    public void setEstanteriaTienda(String estanteriaTienda) { this.estanteriaTienda = estanteriaTienda; }

    public String getPasilloAlmacen() { return pasilloAlmacen; }
    public void setPasilloAlmacen(String pasilloAlmacen) { this.pasilloAlmacen = pasilloAlmacen; }

    public String getEstanteriaAlmacen() { return estanteriaAlmacen; }
    public void setEstanteriaAlmacen(String estanteriaAlmacen) { this.estanteriaAlmacen = estanteriaAlmacen; }
}
