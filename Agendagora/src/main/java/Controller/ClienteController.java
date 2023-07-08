package Controller;

import com.example.agendagora.AgendaApplication;
import Model.Cliente;
import Model.ClienteDAO;
import javafx.beans.binding.BooleanBinding;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.*;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

public class ClienteController implements Initializable {
    @FXML
    TableView<Cliente> tabelaCliente;

    @FXML
    TableColumn<Cliente, Integer> colunaCodigo;

    @FXML
    TableColumn<Cliente, String> colunaNome;

    @FXML
    TableColumn<Cliente, String> colunaEndereco;

    @FXML
    TableColumn<Cliente, String> colunaTelefone;

    @FXML
    Button excluir;
    @FXML
    Button editar;






    //configura as colunas da na interface grafica

    public void initialize(URL url, ResourceBundle resourceBundle) {
        colunaCodigo.setCellValueFactory(new PropertyValueFactory<>("codigo"));
        colunaNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        colunaEndereco.setCellValueFactory(new PropertyValueFactory<>("endereco"));
        colunaTelefone.setCellValueFactory(new PropertyValueFactory<>("telefone"));

        ClienteDAO clienteDAO= new ClienteDAO();
        try {
            List<Cliente> clientes = clienteDAO.getAll();
            tabelaCliente.getItems().addAll(clientes);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
//      Cadastrar um cliente
    public void novo() throws IOException, SQLException {

        CadastroClienteController.cliente=null;
        AgendaApplication.showModal("cadastro-cliente-view");
        Cliente novocliente= CadastroClienteController.cliente;
//        verifica atraves do telefone se o cliente já está cadastrado

            boolean clienteexiste = new ClienteDAO().clienteexiste(novocliente);
            if (clienteexiste ) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("");
                alert.setHeaderText(null);
                alert.setContentText("Cliente já cadastrado");

                alert.showAndWait();

            } else {
                if (novocliente != null) {
                    new ClienteDAO().insert(novocliente);
                    tabelaCliente.getItems().add(CadastroClienteController.cliente);
                }
            }

        }


//        Edita o cliente selecionado
        public void editar() throws IOException, SQLException {
            Cliente clienteselecionado = tabelaCliente.getSelectionModel().getSelectedItem();

            CadastroClienteController.cliente = clienteselecionado;

            AgendaApplication.showModal("cadastro-cliente-view");

            Cliente clienteeditado = CadastroClienteController.cliente;

                if (clienteselecionado != null) {

                    clienteselecionado.codigo = clienteeditado.codigo;
                    clienteselecionado.nome = clienteeditado.nome;
                    clienteselecionado.endereco = clienteeditado.endereco;
                    clienteselecionado.telefone = clienteeditado.telefone;
                    new ClienteDAO().update(clienteeditado);
                    tabelaCliente.refresh();
                }
    }

//       Exclui o cliente que foi selecionado
    @FXML
    public void excluir() throws SQLException {
        Cliente clienteselecionado = tabelaCliente.getSelectionModel().getSelectedItem();
        if(clienteselecionado != null) {

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Excluir Cliente");
            alert.setHeaderText(null);
            alert.setContentText("Deseja excluir" + " " + clienteselecionado.nome + " " + "?");

            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK) {
                tabelaCliente.getItems().remove(clienteselecionado);
                new ClienteDAO().delete(clienteselecionado);
            }
        }
    }
//      Volta para o menu principal
    public void voltar() throws IOException {

        AgendaApplication.setRoot("menu-principal-view");
    }
    @FXML
    public void Habilitabotoes() {
        BooleanBinding algoSelecionado = tabelaCliente.getSelectionModel().selectedItemProperty().isNull();
        excluir.disableProperty().bind(algoSelecionado);
        editar.disableProperty().bind(algoSelecionado);

    }

}

