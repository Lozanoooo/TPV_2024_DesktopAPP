package com.example.tpv_2024.Modelos;

public class Producto {
    private String codigoBarra;
    private String nombre;
    private double precioVenta;
    private String categoria;
    private int stock;
    private double precioSuministrador;
    private String suministrador;
    private double ganancia;
    private String fechaStock;
    private Localizacion localizacion;

    public Producto() {
        // Constructor por defecto
    }

    public Producto(String codigoBarra, String nombre, Double precioVenta, String categoria, Integer stock, Double precioSuministrador, String suministrador, Double ganancia, String fechaStock, Localizacion localizacion) {
        this.codigoBarra = codigoBarra;
        this.nombre = nombre;
        this.precioVenta = precioVenta;
        this.categoria = categoria;
        this.stock = stock;
        this.precioSuministrador = precioSuministrador;
        this.suministrador = suministrador;
        this.ganancia = ganancia;
        this.fechaStock = fechaStock;
        this.localizacion = localizacion;
    }

    // Getters and Setters

    public String getCodigoBarra() { return codigoBarra; }
    public void setCodigoBarra(String codigoBarra) { this.codigoBarra = codigoBarra; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public double getPrecioVenta() { return precioVenta; }
    public void setPrecioVenta(double precioVenta) { this.precioVenta = precioVenta; }

    public String getCategoria() { return categoria; }
    public void setCategoria(String categoria) { this.categoria = categoria; }

    public int getStock() { return stock; }
    public void setStock(int stock) { this.stock = stock; }

    public double getPrecioSuministrador() { return precioSuministrador; }
    public void setPrecioSuministrador(double precioSuministrador) { this.precioSuministrador = precioSuministrador; }

    public String getSuministrador() { return suministrador; }
    public void setSuministrador(String suministrador) { this.suministrador = suministrador; }

    public double getGanancia() { return ganancia; }
    public void setGanancia(double ganancia) { this.ganancia = ganancia; }

    public String getFechaStock() { return fechaStock; }
    public void setFechaStock(String fechaStock) { this.fechaStock = fechaStock; }

    public Localizacion getLocalizacion() { return localizacion; }
    public void setLocalizacion(Localizacion localizacion) { this.localizacion = localizacion; }
}
