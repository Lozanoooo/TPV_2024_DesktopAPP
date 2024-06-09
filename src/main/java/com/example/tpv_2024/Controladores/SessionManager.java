package com.example.tpv_2024.Controladores;

import com.example.tpv_2024.Modelos.Empleado;

public class SessionManager {
    private static SessionManager instance;
    private Empleado empleadoLogueado;

    private SessionManager() {
    }

    public static SessionManager getInstance() {
        if (instance == null) {
            instance = new SessionManager();
        }
        return instance;
    }

    public void setEmpleadoLogueado(Empleado empleado) {
        this.empleadoLogueado = empleado;
    }

    public String getNombreEmpleadoLogueado() {
        if (this.empleadoLogueado != null) {
            return this.empleadoLogueado.getNombre();
        }
        return null;
    }
    public int getIDEmpleadoLogueado() {
        if (this.empleadoLogueado != null) {
            return this.empleadoLogueado.getIdEmpleado();
        }
        return 1;
    }



}
