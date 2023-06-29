package com.example.agendagora;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class CadastroClienteController implements Initializable {


    @FXML
    TextField codigoField;

    @FXML
    TextField nomeField;

    @FXML
    TextField enderecoField;

    @FXML
    TextField telefoneField;

    public static Cliente cliente;

    @FXML
    public void salvar() {

        Cliente novoCliente = new Cliente();

        if (!codigoField.getText().isBlank()) {
                novoCliente.codigo = Integer.parseInt(codigoField.getText());
        }
        novoCliente.nome = nomeField.getText();
        novoCliente.endereco = enderecoField.getText();
        novoCliente.telefone = telefoneField.getText();
        if(!nomeField.getText().isBlank() &&!enderecoField.getText().isBlank() && !telefoneField.getText().isBlank()){
            cliente = novoCliente;
            AgendaApplication.closeCurrentWindow();
        }else{
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Iformações");
            alert.setHeaderText(null);
            alert.setContentText(" Um ou mais campos estão vazios. Todos os campos com * devem ser prenchidos" );

            alert.showAndWait();

        }
    }

    @FXML
    public void cancelar() {
        AgendaApplication.closeCurrentWindow();

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Cliente clienteselecionado = CadastroClienteController.cliente;

        if (clienteselecionado != null) {
            codigoField.setText(Integer.toString(clienteselecionado.codigo));
            nomeField.setText(clienteselecionado.nome);
            enderecoField.setText(clienteselecionado.endereco);
            telefoneField.setText(clienteselecionado.telefone);

        }
    }
}

