module com.example.tpv_2024 {
    requires javafx.controls;
    requires javafx.fxml;
    requires de.jensd.fx.glyphs.fontawesome;
    requires java.sql;


    opens com.example.tpv_2024 to javafx.fxml;
    exports com.example.tpv_2024;
    exports com.example.tpv_2024.Controladores;
    exports com.example.tpv_2024.Controladores.Admin;
    exports com.example.tpv_2024.Controladores.Cliente;
    exports com.example.tpv_2024.Modelos;
    exports com.example.tpv_2024.Vistas;
}