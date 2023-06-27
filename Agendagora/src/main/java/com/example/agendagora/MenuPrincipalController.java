package com.example.agendagora;

import javafx.fxml.FXML;

import java.io.IOException;

public class MenuPrincipalController {
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
        UsuarioSigleton.usuarioteste=null;
        AgendaApplication.setRoot("login-view");

    }

}
