module com.example.agendagora {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens com.example.agendagora to javafx.fxml;
    exports com.example.agendagora;
}