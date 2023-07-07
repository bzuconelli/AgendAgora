package com.example.agendagora;

import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

import javax.swing.border.EmptyBorder;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

public class ProjetoController  {
    @FXML
    TableView<Servico> tabelaservicos;

    @FXML
    TableColumn<Servico, String> colunaCodigo;

    @FXML
    TableColumn<Servico, String> colunaNome;

    @FXML
    TableColumn<Servico, Date> colunaData;
    @FXML
    TableColumn<Servico, Double> colunaTempodoservico;
    @FXML
    TableColumn<Servico, Double> colunaValortotal;
    @FXML
    TextField telefoneField;




    @FXML
    public void Pesqisar() throws SQLException {
        Cliente pesquisacliente = new Cliente();
        Cliente clientepequisadoservico = new Cliente();

        if (!telefoneField.getText().isBlank() && telefoneField != null) {
            pesquisacliente.telefone = telefoneField.getText();
            boolean clienteexiste = new ClienteDAO().clienteexiste(pesquisacliente);
            if (clienteexiste){
                clientepequisadoservico = new ClienteDAO().clientecadastrado(pesquisacliente);
                new ServicosDAO().historicodeservico(clientepequisadoservico);
            }else{
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("");
                alert.setHeaderText(null);
                alert.setContentText("Cliente não cadastrado");

                alert.showAndWait();
            }
        }

        colunaCodigo.setCellValueFactory(new PropertyValueFactory<>("codigo"));
        colunaNome.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getCliente().getNome()));
        colunaData.setCellValueFactory(new PropertyValueFactory<>("datadoservico"));
        colunaTempodoservico.setCellValueFactory(new PropertyValueFactory<>("totaldehoras"));
        colunaValortotal.setCellValueFactory(new PropertyValueFactory<>("valorfinal"));
        ServicosDAO servicosDAO = new ServicosDAO();
        try {
            List<Servico> servicos = servicosDAO.historicodeservico(clientepequisadoservico);
            tabelaservicos.getItems().addAll(servicos);

        } catch (SQLException e) {
            throw new RuntimeException(e);


        }


    }

    @FXML
    public void voltar() throws IOException {

        AgendaApplication.setRoot("menu-principal-view");
    }


}

