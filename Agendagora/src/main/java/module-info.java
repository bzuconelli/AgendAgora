module com.example.agendagora {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires java.desktop;





    opens com.example.agendagora to javafx.fxml;
    exports com.example.agendagora;
    exports Controller;
    opens Controller to javafx.fxml;
    exports Model;
    opens Model to javafx.fxml;
}