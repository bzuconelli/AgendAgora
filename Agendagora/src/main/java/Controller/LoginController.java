package Controller;

import com.example.agendagora.AgendaApplication;
import Model.Usuario;
import Model.UsuarioDAO;
import Model.UsuarioSigleton;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.sql.SQLException;

public class LoginController {
    @FXML
    TextField usuarioField;
    @FXML
    PasswordField senhaField;


    @FXML
    public void entrar () throws IOException, SQLException {
        Usuario usariologin=new Usuario();
        usariologin.usuario= usuarioField.getText();
        usariologin.senha= senhaField.getText();



        boolean usuarioexiste= new UsuarioDAO().existe(usariologin);

        if (usuarioexiste){
           Usuario usuarioteste = new UsuarioDAO().usuariologadoinfo(usariologin);
           UsuarioSigleton.getUsuario(usuarioteste);
           AgendaApplication.setRoot("menu-principal-view");
        }else{
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle(null);
            alert.setHeaderText(null);
            alert.setContentText("Usuario ou senha incorretos!!");
            alert.showAndWait();

        }
    }


}