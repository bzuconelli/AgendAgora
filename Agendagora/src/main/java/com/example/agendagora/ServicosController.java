package com.example.agendagora;

import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

public class ServicosController implements Initializable {
    @FXML
    TableView<Servico> tabelaServicos;
    @FXML
    TableColumn<Servico, String> colunaNome;

    @FXML
    TableColumn<Servico, String> colunaEndreco;

    @FXML
    TableColumn<Servico, String> colunaTelefone;

    @FXML
    TableColumn<Servico, Date> colunaData;
    @FXML
    TableColumn<Servico, String> colunaTipodeservico;
   @FXML
   TableColumn<Servico,Integer> colunaCodigo;
    public void initialize(URL url, ResourceBundle resourceBundle)  {


        colunaNome.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getCliente().getNome()));
        colunaTelefone.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getCliente().getTelefone()));
        colunaData.setCellValueFactory(new PropertyValueFactory<>("datadoservico"));
        colunaEndreco.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getCliente().getEndereco()));
        colunaTipodeservico.setCellValueFactory(new PropertyValueFactory<>("tipodoservico"));
        colunaCodigo.setCellValueFactory(new PropertyValueFactory<>("codigo"));

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
        if (novoservico != null) {
            new ServicosDAO().insert(novoservico);
            tabelaServicos.getItems().add(CadastroServicoController.servico);
        }
    }

    @FXML
    public void excluir() throws SQLException {
        Servico servicoselecionado = tabelaServicos.getSelectionModel().getSelectedItem();
        if(servicoselecionado != null) {

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Excluir Cliente");
            alert.setHeaderText(null);
            alert.setContentText("Deseja cancelar o servico de " + " " + servicoselecionado.cliente.nome + " " + "?");

            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK) {
                tabelaServicos.getItems().remove(servicoselecionado);

                new ServicosDAO().delete(servicoselecionado);
            }
        }
    }
    public void voltar() throws IOException {

        AgendaApplication.setRoot("menu-principal-view");
    }

}
