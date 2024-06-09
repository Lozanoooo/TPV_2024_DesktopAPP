module com.example.tpv_2024 {
    requires javafx.controls;
    requires javafx.fxml;
    requires de.jensd.fx.glyphs.fontawesome;
    requires java.sql;
    requires org.apache.httpcomponents.client5.httpclient5;
    requires org.apache.httpcomponents.core5.httpcore5;
    requires com.fasterxml.jackson.databind;
    requires org.slf4j;
    requires java.desktop;

    opens com.example.tpv_2024 to javafx.fxml;
    opens com.example.tpv_2024.Controladores.Cliente to javafx.fxml;

    exports com.example.tpv_2024;
    exports com.example.tpv_2024.Controladores;
    exports com.example.tpv_2024.Controladores.Cliente;
    exports com.example.tpv_2024.Modelos;
    exports com.example.tpv_2024.Vistas;
    exports com.example.tpv_2024.Servicio;
}
