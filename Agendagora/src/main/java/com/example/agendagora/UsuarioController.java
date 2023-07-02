package com.example.agendagora;

import javafx.beans.binding.BooleanBinding;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

public class UsuarioController implements Initializable {
    @FXML
    TableView<Usuario> tabelaUsuario;
    @FXML
    TableColumn<Usuario, Integer> colunaCodigo;

    @FXML
    TableColumn<Usuario, String> colunaNome;

    @FXML
    TableColumn<Usuario, Integer> colunaqtdvagas;

    @FXML
    TableColumn<Usuario, String> colunaendereco;
    @FXML
    TableColumn<Usuario, String> colunausuario;

    @FXML
   Button loginesenha;
    @FXML
    Button excluir;
    @FXML
    Button informacoes;

    //configura as colunas da na interface grafica

    public void initialize(URL url, ResourceBundle resourceBundle) {
        colunaCodigo.setCellValueFactory(new PropertyValueFactory<>("codigo"));
        colunaNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        colunausuario.setCellValueFactory(new PropertyValueFactory<>("usuario"));
        colunaqtdvagas.setCellValueFactory(new PropertyValueFactory<>("qtdvagas"));
        colunaendereco.setCellValueFactory(new PropertyValueFactory<>("endereco"));

        UsuarioDAO usuarioDAO = new UsuarioDAO();
        try {

            List<Usuario> usuarios = usuarioDAO.getAll();
            tabelaUsuario.getItems().addAll(usuarios);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public void novo() throws IOException, SQLException {
        CadastroUsuarioController.usuario=null;
        AgendaApplication.showModal("cadastro-login-view");
        Usuario novousuario= CadastroUsuarioController.usuario;
        boolean loginexiste = new UsuarioDAO().loginexiste(novousuario);
        if (!loginexiste ) {
            new UsuarioDAO().insert(novousuario);
            tabelaUsuario.getItems().add(CadastroUsuarioController.usuario);

        } else {

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Imformações");
            alert.setHeaderText(null);
            alert.setContentText("O login Ja existe digite outro ");
            alert.showAndWait();

        }

    }

    @FXML
    public void excluir() throws SQLException, IOException {
        Usuario usuarioselecionado = tabelaUsuario.getSelectionModel().getSelectedItem();
        if(usuarioselecionado.codigo ==UsuarioSigleton.usuarioteste.codigo) {
            boolean existeoutrousuario= new UsuarioDAO().qtdusuarios();
            if (existeoutrousuario) {

                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Excluir Cliente");
                alert.setHeaderText(null);
                alert.setContentText("Deseja excluir" + " " + usuarioselecionado.nome + " " + "?");

                Optional<ButtonType> result = alert.showAndWait();
                if (result.get() == ButtonType.OK) {
                    AgendaApplication.setRoot("login-view");
                    tabelaUsuario.getItems().remove(usuarioselecionado);
                    new UsuarioDAO().delete(usuarioselecionado);
                    UsuarioSigleton.usuarioteste=null;

                }
            }else{
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Imformações");
                alert.setHeaderText(null);
                alert.setContentText(" Não ha outro usuario criado para poder excluir crie outro usuario primeiro");
                alert.showAndWait();
            }
        }else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Imformações");
            alert.setHeaderText(null);
            alert.setContentText(" Voce so pode excluir  o seu  usuario");
            alert.showAndWait();
        }

    }
    public void editarloginousenha() throws IOException, SQLException {
        Usuario usuarioselecionado = tabelaUsuario.getSelectionModel().getSelectedItem();

        CadastroUsuarioController.usuario = usuarioselecionado;
        if(usuarioselecionado.codigo ==UsuarioSigleton.usuarioteste.codigo) {

        AgendaApplication.showModal("cadastro-login-view");


        Usuario usuarioeditado = CadastroUsuarioController.usuario;

        if ( !usuarioselecionado.usuario.equals(usuarioeditado.usuario) || !usuarioselecionado.senha.equals(usuarioeditado.senha)) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Troca de login ou senha");
            alert.setHeaderText(null);
            alert.setContentText("Ao trocar a senha ou login você sera direcionado tela de login");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK) {
                usuarioselecionado.codigo = usuarioeditado.codigo;
                usuarioselecionado.nome = usuarioeditado.nome;
                usuarioselecionado.usuario = usuarioeditado.usuario;
                usuarioselecionado.senha = usuarioeditado.senha;
                new UsuarioDAO().update(usuarioeditado);
                tabelaUsuario.refresh();
                UsuarioSigleton.usuarioteste=null;
                AgendaApplication.setRoot("login-view");

            }


        }else if(!usuarioselecionado.nome.equals(usuarioeditado.nome)){
                usuarioselecionado.nome = usuarioeditado.nome;
                new UsuarioDAO().update(usuarioeditado);
                tabelaUsuario.refresh();

        }

        }else {

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Imformações");
            alert.setHeaderText(null);
            alert.setContentText(" Voce so pode alterar as informações do seu usuario");
            alert.showAndWait();

        }
    }

    public void editarinformacoes() throws IOException, SQLException {
        Usuario usuarioselecionado = tabelaUsuario.getSelectionModel().getSelectedItem();

        UsuarioInfomacoesController.usuarioinfo = usuarioselecionado;
        if (usuarioselecionado.codigo == UsuarioSigleton.usuarioteste.codigo) {
            AgendaApplication.showModal("informacoesusuario-view");
            Usuario usuarioeditado = UsuarioInfomacoesController.usuarioinfo;
            if (usuarioselecionado != null) {

                usuarioselecionado.qtdvagas = usuarioeditado.qtdvagas;
                usuarioselecionado.endereco = usuarioeditado.endereco;
                usuarioselecionado.telefone = usuarioeditado.telefone;
                usuarioselecionado.cpfouCNPJ = usuarioeditado.cpfouCNPJ;
                usuarioselecionado.ramodeatividade = usuarioeditado.ramodeatividade;
                new UsuarioDAO().updateinfo(usuarioselecionado);
                tabelaUsuario.refresh();


            }
        } else {

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Imformações");
            alert.setHeaderText(null);
            alert.setContentText(" Você so pode alterar as informações do seu usuario");
            alert.showAndWait();

        }
    }


    public void voltar() throws IOException {
        AgendaApplication.setRoot("menu-principal-view");
    }

   @FXML
    public void Habilitabotoes() {
       BooleanBinding algoSelecionado = tabelaUsuario.getSelectionModel().selectedItemProperty().isNull();

        excluir.disableProperty().bind(algoSelecionado);
        loginesenha.disableProperty().bind(algoSelecionado);
        informacoes.disableProperty().bind(algoSelecionado);
    }
}

