package com.example.agendagora;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;


public class ServicoFinalizarController implements Initializable {

    @FXML
    TextField codigodaosField;
    @FXML
    TextField nomeclienteField;
    @FXML
    TextField valorhoraField;
    @FXML
    TextField valortotalField;
    @FXML
    TextField totaldehoras;

    public static Servico finalizaros;

    @FXML
    public void salvar() {
        Servico novoServico = new Servico();
        novoServico.codigo = Integer.parseInt(codigodaosField.getText());
        novoServico.cliente= new Cliente();
        novoServico.cliente.nome = (nomeclienteField.getText());
        novoServico.valorhora = Double.parseDouble(valorhoraField.getText());
        novoServico.totaldehoras = Double.parseDouble(totaldehoras.getText());
        novoServico.valorfinal = Double.parseDouble(valortotalField.getText());


        if (!valortotalField.getText().isBlank() && !totaldehoras.getText().isBlank() && !valorhoraField.getText().isBlank()) {
            finalizaros=novoServico;
            AgendaApplication.closeCurrentWindow();

        } else {

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Informações");
            alert.setHeaderText(null);
            alert.setContentText(" Um ou mais campos estão vazios. Todos os campos com * devem ser preenchidos");

            alert.showAndWait();
        }
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Servico servicoselecionado = ServicoFinalizarController.finalizaros;
        if (servicoselecionado != null) {
            codigodaosField.setText(Integer.toString(servicoselecionado.codigo));
            nomeclienteField.setText(servicoselecionado.cliente.nome);

        }


    }
}

