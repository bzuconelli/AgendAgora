package com.example.agendagora;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

public class ServicosController implements Initializable {
    @FXML
    TableView<Servico> tabelaServicos;
    @FXML
    TableColumn<Servico, String> ColunaNome;

    @FXML
    TableColumn<Servico, String> colunaEndreco;

    @FXML
    TableColumn<Servico, String> colunaTelefone;

    @FXML
    TableColumn<Servico, Date> colunaData;
    @FXML
    TableColumn<Servico, String> colunaTipodeservico;
    public void initialize(URL url, ResourceBundle resourceBundle) {

        ColunaNome.setCellValueFactory(new PropertyValueFactory<>("cliente"));
        colunaTelefone.setCellValueFactory(new PropertyValueFactory<>("cliente"));
        colunaData.setCellValueFactory(new PropertyValueFactory<>("datadoservico"));
        colunaEndreco.setCellValueFactory(new PropertyValueFactory<>("cliente"));
        colunaTipodeservico.setCellValueFactory(new PropertyValueFactory<>("tipodoservico"));

        ServicosDAO servicosDAO= new ServicosDAO();
        try {

            List<Servico> servicos = servicosDAO.getAll();
            tabelaServicos.getItems().addAll(servicos);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


    }
    public void novo() throws IOException, SQLException {
        CadastroServicoController.servico=null;
        AgendaApplication.showModal("cadastro-servico-view");
        Servico novoservico= CadastroServicoController.servico;


    }
    public void voltar() throws IOException {

        AgendaApplication.setRoot("menu-principal-view");
    }

}
