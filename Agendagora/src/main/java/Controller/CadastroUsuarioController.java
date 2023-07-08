package Controller;

import com.example.agendagora.AgendaApplication;
import Model.Usuario;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class CadastroUsuarioController implements Initializable {


    @FXML
    TextField codigoField;

    @FXML
    TextField nomeField;

    @FXML
    TextField usuarioField;

    @FXML
    PasswordField senhaField;

    public static Usuario usuario;

    @FXML
    public void salvar() throws SQLException {

        Usuario novoUsuario = new Usuario();

        if (!codigoField.getText().isBlank()) {
            novoUsuario.codigo = Integer.parseInt(codigoField.getText());
        }
        novoUsuario.nome = nomeField.getText();
        novoUsuario.usuario = usuarioField.getText();
        novoUsuario.senha = senhaField.getText();
        if (!nomeField.getText().isEmpty() && !senhaField.getText().isEmpty() && !usuarioField.getText().isEmpty()) {
            usuario = novoUsuario;
            AgendaApplication.closeCurrentWindow();

        } else {

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Imformações");
            alert.setHeaderText(null);
            alert.setContentText(" Um ou mais campos estão vazios. Todos os campos com * devem ser preenchidos");

            alert.showAndWait();
        }
    }



    @FXML
    public void cancelar() {
        AgendaApplication.closeCurrentWindow();

    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Usuario usuarioselecionado = CadastroUsuarioController.usuario;

        if (usuarioselecionado != null) {
            codigoField.setText(Integer.toString(usuarioselecionado.codigo));
            nomeField.setText(usuarioselecionado.nome);
            senhaField.setText(usuarioselecionado.senha);
            usuarioField.setText(usuarioselecionado.usuario);

        }
    }
}


