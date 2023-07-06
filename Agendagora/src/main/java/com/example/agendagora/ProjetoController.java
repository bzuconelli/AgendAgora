package com.example.agendagora;

import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class ProjetoController {
    @FXML
    TableView<Servico> tabelaservicos;

    @FXML
    TableColumn<Cliente, Integer> colunaCodigo;

    @FXML
    TableColumn<Cliente, String> colunaNome;

    @FXML
    TableColumn<Cliente, String> colunaEndereco;

    @FXML
    TableColumn<Cliente, String> colunaTelefone;





}
