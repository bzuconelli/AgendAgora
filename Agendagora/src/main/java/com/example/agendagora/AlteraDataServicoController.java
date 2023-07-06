package com.example.agendagora;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;

import java.net.URL;
import java.sql.Date;
import java.util.ResourceBundle;

public class AlteraDataServicoController implements Initializable {
    public static Servico servico;
    @FXML
    public void salvar() {
        Servico novoServico = new Servico();
        if (!(datadoservicoField.getValue() ==null)) {
            novoServico.datadoservico = Date.valueOf(datadoservicoField.getValue());
        }else{
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Informações");
            alert.setHeaderText(null);
            alert.setContentText("O campo da data deve ser preenchido");

            alert.showAndWait();
        }

        if (datadoservicoField!=null){
            servico=novoServico;
            AgendaApplication.closeCurrentWindow();
        }
    }


    @FXML
    DatePicker datadoservicoField;
    @FXML
    public void cancelar() {
        AgendaApplication.closeCurrentWindow();

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle){
            Servico servicoselecionado = AlteraDataServicoController.servico;

            if (servicoselecionado != null) {

                datadoservicoField.setValue(servicoselecionado.datadoservico.toLocalDate());
            }

    }

}
