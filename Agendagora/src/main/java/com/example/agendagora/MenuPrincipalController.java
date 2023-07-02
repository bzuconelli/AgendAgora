package com.example.agendagora;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class MenuPrincipalController  {
    @FXML
    Label diaatual;
    @FXML
    Label diaatual1;
    @FXML
    Label diaatual2;
    @FXML
    Label diaatual3;
    @FXML
    Label diaatual4;

    @FXML
    public void abrirtelaclientes() throws IOException {
        AgendaApplication.setRoot("cliente-view");
    }

    @FXML
    public void abrirtelaconfiguracoes() throws IOException {
        AgendaApplication.setRoot("configuracoes-view");
    }

    @FXML
    public void abrirtelaservicos() throws IOException {
        AgendaApplication.setRoot("servicos-view");
    }

    @FXML
    public void voltar() throws IOException {
        UsuarioSigleton.usuarioteste = null;
        AgendaApplication.setRoot("login-view");

    }

}
