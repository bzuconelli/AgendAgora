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
        if (!valortotalField.getText().equals("0.0") && !valorhoraField.getText().equals("0.0")&& !totaldehoras.getText().equals("0.0")){
            novoServico.codigo = Integer.parseInt(codigodaosField.getText());
            novoServico.cliente= new Cliente();
            novoServico.cliente.nome = (nomeclienteField.getText());
            novoServico.valorhora =  Double.parseDouble(valorhoraField.getText());
            novoServico.totaldehoras =  Double.parseDouble(totaldehoras.getText());
            novoServico.valorfinal =  Double.parseDouble(valortotalField.getText());
            finalizaros=novoServico;
            AgendaApplication.closeCurrentWindow();

        } else {

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Informações");
            alert.setHeaderText(null);
            alert.setContentText("Os campos com '*' devem ser preenchidos com valor maior que 0");

            alert.showAndWait();
        }
    }




    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Servico servicoselecionado = ServicoFinalizarController.finalizaros;
        if (servicoselecionado != null) {
            codigodaosField.setText(Integer.toString(servicoselecionado.codigo));
            nomeclienteField.setText(servicoselecionado.cliente.nome);
            totaldehoras.setText(Double.toString(servicoselecionado.totaldehoras));
            valorhoraField.setText(Double.toString(servicoselecionado.valorhora));
            valortotalField.setText(Double.toString(servicoselecionado.valorfinal));



        }


    }
}

