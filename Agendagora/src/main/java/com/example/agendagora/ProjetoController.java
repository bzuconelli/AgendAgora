package com.example.agendagora;

import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

public class ProjetoController implements Initializable {
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

    Cliente clientepequisado;

    public void initialize(URL url, ResourceBundle resourceBundle) {
        colunaCodigo.setCellValueFactory(new PropertyValueFactory<>("codigo"));
        colunaNome.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getCliente().getNome()));
        colunaData.setCellValueFactory(new PropertyValueFactory<>("datadoservico"));


    }

    @FXML
    public void Pesqisar() throws SQLException {
        Cliente pesquisacliente = new Cliente();

//        if (!telefoneField.getText().isBlank() && telefoneField!= null){


//}
    }
}