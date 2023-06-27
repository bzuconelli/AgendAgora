package com.example.agendagora;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class UsuarioInfomacoesController implements Initializable {


    @FXML
    TextField qtdvagasField;

    @FXML
    TextField telefoneField;

    @FXML
    TextField enderecoField;

    @FXML
    TextField ramodeatividadeField;
    @FXML
    TextField CPFouCPJField;
    @FXML
    Label qtdvasgas;

    public static Usuario usuarioinfo;

    @FXML
    public void salvar() {

        Usuario novoUsuario = new Usuario();


        novoUsuario.qtdvagas = Integer.parseInt(qtdvagasField.getText());
        novoUsuario.telefone = telefoneField.getText();
        novoUsuario.endereco = enderecoField.getText();
        novoUsuario.cpfouCNPJ = CPFouCPJField.getText();
        novoUsuario.ramodeatividade=ramodeatividadeField.getText();

        if ( !isEmpty(telefoneField) && !isEmpty(enderecoField) && !isEmpty(CPFouCPJField) && !isEmpty(ramodeatividadeField)&& !isEmpty(qtdvagasField) &&!qtdvagasField.getText().equals("0")) {
            usuarioinfo = novoUsuario;
            AgendaApplication.closeCurrentWindow();
        } else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Informações");
            alert.setHeaderText(null);
            alert.setContentText(" Um ou mais campos estão vazios. Todos os campos com * devem ser preenchidos");

            alert.showAndWait();
        }
    }

    private boolean isEmpty(TextField textField) {
        return textField.getText() != null && textField.getText().isBlank();
    }

    @FXML
    public void cancelar() {
        AgendaApplication.closeCurrentWindow();

    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Usuario usuarioselecionado = UsuarioInfomacoesController.usuarioinfo;

        if (usuarioselecionado != null) {
            qtdvagasField.setText(Integer.toString(usuarioselecionado.qtdvagas));
            telefoneField.setText(usuarioselecionado.telefone);
            enderecoField.setText(usuarioselecionado.endereco);
            ramodeatividadeField.setText(usuarioselecionado.ramodeatividade);
            CPFouCPJField.setText(usuarioselecionado.cpfouCNPJ);
        }
        if (qtdvagasField.getText().equals("0")){
            qtdvasgas.setText("A quantidade de atendimentos deve ser mairo que 0");


        }
    }
}


