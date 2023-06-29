package com.example.agendagora;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;

import java.sql.Date;
import java.sql.SQLException;


public class CadastroServicoController {
    @FXML
    TextField telefoneField;
    @FXML
    TextField nomeField;

    @FXML
    TextField enderecoField;
    @FXML
    DatePicker datadoservicoField;
    @FXML
    TextField statusdoservicoField;
    @FXML
    TextField tipodeservicoField;


    public static Servico servico;

    Cliente clientepequisado;
    @FXML
    public void salvar() {

        Servico novoServico = new Servico();
        novoServico.cliente= new Cliente();


        novoServico.cliente.nome=nomeField.getText();
        novoServico.cliente.endereco=enderecoField.getText();
        novoServico.cliente.telefone=telefoneField.getText();
        novoServico.datadoservico= Date.valueOf(datadoservicoField.getValue());
        if (Date.valueOf(datadoservicoField.getValue()) == null){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Iformações");
            alert.setHeaderText(null);
            alert.setContentText(" Um ou mais campos estão vazios. Todos os campos com * devem ser preenchidos" );

            alert.showAndWait();

        }
        novoServico.estadodoservico= statusdoservicoField.getText();
        novoServico.tipodoservico= tipodeservicoField.getText();
        novoServico.cliente.codigo=clientepequisado.codigo;
        if(!nomeField.getText().isBlank() &&!enderecoField.getText().isBlank() && !telefoneField.getText().isBlank() && !statusdoservicoField.getText().isBlank() && !tipodeservicoField.getText().isBlank()){
            servico=novoServico;
            AgendaApplication.closeCurrentWindow();

        }else{
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Iformações");
            alert.setHeaderText(null);
            alert.setContentText(" Um ou mais campos estão vazios. Todos os campos com * devem ser preenchidos" );

            alert.showAndWait();

        }


    }

    @FXML
    public void Pesqisar() throws  SQLException {
        Cliente pesquisacliente =new Cliente();

        if (!telefoneField.getText().isBlank()){

            pesquisacliente.telefone=telefoneField.getText();

            boolean clienteexiste = new ClienteDAO().clienteexiste(pesquisacliente);
            if (clienteexiste){
                Cliente clientestacadastrado= new ClienteDAO().clientecadastrado(pesquisacliente);

                nomeField.setText(clientestacadastrado.nome);
                enderecoField.setText(clientestacadastrado.endereco);
                clientepequisado=clientestacadastrado;

           }else{
               Alert alert = new Alert(Alert.AlertType.WARNING);
               alert.setTitle("");
               alert.setHeaderText(null);
               alert.setContentText("Cliente não cadastrado");

               alert.showAndWait();
           }



        }

    }
}
