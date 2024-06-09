package com.example.tpv_2024.Modelos;

public class Producto {
    private String codigoBarra;
    private String nombre;
    private String categoria;
    private double precioVenta;
    private int stock;
    private double precioSuministrador;
    private String suministrador;
    private double ganancia;
    private String localizacion_id;
    private String pasilloAlmacen;
    private String estanteriaAlmacen;
    private String pasilloTienda;
    private String estanteriaTienda;

    public Producto(String codigoBarra, String nombre, String categoria, double precioVenta, int stock,
                    double precioSuministrador, String suministrador, double ganancia, String localizacion_id,
                    String pasilloAlmacen, String estanteriaAlmacen, String pasilloTienda, String estanteriaTienda) {
        this.codigoBarra = codigoBarra;
        this.nombre = nombre;
        this.categoria = categoria;
        this.precioVenta = precioVenta;
        this.stock = stock;
        this.precioSuministrador = precioSuministrador;
        this.suministrador = suministrador;
        this.ganancia = ganancia;
        this.localizacion_id = localizacion_id;
        this.pasilloAlmacen = pasilloAlmacen;
        this.estanteriaAlmacen = estanteriaAlmacen;
        this.pasilloTienda = pasilloTienda;
        this.estanteriaTienda = estanteriaTienda;
    }

    public Producto() {
    }

    // Getters and setters

    public String getCodigoBarra() {
        return codigoBarra;
    }

    public void setCodigoBarra(String codigoBarra) {
        this.codigoBarra = codigoBarra;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public double getPrecioVenta() {
        return precioVenta;
    }

    public void setPrecioVenta(double precioVenta) {
        this.precioVenta = precioVenta;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public double getPrecioSuministrador() {
        return precioSuministrador;
    }

    public void setPrecioSuministrador(double precioSuministrador) {
        this.precioSuministrador = precioSuministrador;
    }

    public String getSuministrador() {
        return suministrador;
    }

    public void setSuministrador(String suministrador) {
        this.suministrador = suministrador;
    }

    public double getGanancia() {
        return ganancia;
    }

    public void setGanancia(double ganancia) {
        this.ganancia = ganancia;
    }

    public String getLocalizacion_id() {
        return localizacion_id;
    }

    public void setLocalizacion_id(String localizacion_id) {
        this.localizacion_id = localizacion_id;
    }

    public String getPasilloAlmacen() {
        return pasilloAlmacen;
    }

    public void setPasilloAlmacen(String pasilloAlmacen) {
        this.pasilloAlmacen = pasilloAlmacen;
    }

    public String getEstanteriaAlmacen() {
        return estanteriaAlmacen;
    }

    public void setEstanteriaAlmacen(String estanteriaAlmacen) {
        this.estanteriaAlmacen = estanteriaAlmacen;
    }

    public String getPasilloTienda() {
        return pasilloTienda;
    }

    public void setPasilloTienda(String pasilloTienda) {
        this.pasilloTienda = pasilloTienda;
    }

    public String getEstanteriaTienda() {
        return estanteriaTienda;
    }

    public void setEstanteriaTienda(String estanteriaTienda) {
        this.estanteriaTienda = estanteriaTienda;
    }
}