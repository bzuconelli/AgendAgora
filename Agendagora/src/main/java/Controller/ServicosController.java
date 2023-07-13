package Controller;

import Model.Servico;
import Model.ServicosDAO;
import com.example.agendagora.*;
import javafx.beans.binding.BooleanBinding;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.CheckBox;


import java.awt.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
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
   @FXML
   Button cancelar;
   @FXML
   Button editar;
   @FXML
   Button whatsapp;
   @FXML
   Button finalizar;
   @FXML
   CheckBox servicosdodiacheck;





    public void initialize(URL url, ResourceBundle resourceBundle)  {



        colunaNome.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getCliente().getNome()));
        colunaTelefone.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getCliente().getTelefone()));
        colunaData.setCellValueFactory(new PropertyValueFactory<>("datadoservico"));
        colunaEndreco.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getCliente().getEndereco()));
        colunaTipodeservico.setCellValueFactory(new PropertyValueFactory<>("tipodoservico"));
        colunaCodigo.setCellValueFactory(new PropertyValueFactory<>("codigo"));

        ServicosDAO servicosDAO= new ServicosDAO();
        try {

            List<Servico> servicos = servicosDAO.getAll(false);

            tabelaServicos.getItems().addAll(servicos);
        } catch (SQLException e) {
            throw new RuntimeException(e);


        }
    }
    @FXML
    public void servicosdodia() throws SQLException {
        List<Servico> servicos = new ServicosDAO().getAll(servicosdodiacheck.isSelected());

        tabelaServicos.getItems().clear();
        tabelaServicos.getItems().addAll(servicos);
    }

    @FXML
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
            alert.setTitle("Excluir cliente");
            alert.setHeaderText(null);
            alert.setContentText("Deseja cancelar o serviço de " + " " + servicoselecionado.cliente.nome + " " + "?");

            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK) {
                tabelaServicos.getItems().remove(servicoselecionado);

                new ServicosDAO().delete(servicoselecionado);
            }
        }
    }
    @FXML
    public void alterardata() throws IOException, SQLException {
        Servico servicoselecionado = tabelaServicos.getSelectionModel().getSelectedItem();
        AlteraDataServicoController.servico = servicoselecionado;
        AgendaApplication.showModal("editar-dataservico-view");
        Servico servicoeditado= AlteraDataServicoController.servico;
        if(servicoselecionado!= null){
            servicoselecionado.datadoservico= servicoeditado.datadoservico;
            servicoeditado.codigo=servicoselecionado.codigo;
            new ServicosDAO().update(servicoeditado);
            tabelaServicos.refresh();


        }


    }
    @FXML
    public void finalizaros() throws IOException, SQLException {
        Servico servicoselecionado = tabelaServicos.getSelectionModel().getSelectedItem();
        ServicoFinalizarController.finalizaros=servicoselecionado;
        AgendaApplication.showModal("finalizaros-view");
        Servico servicofinalizado = ServicoFinalizarController.finalizaros;
        if (servicofinalizado.valorhora != 0.0 && servicofinalizado.totaldehoras !=0.0 && servicofinalizado.valorfinal!=0.0 ){
            servicoselecionado.valorfinal=servicofinalizado.valorfinal;
            servicoselecionado.totaldehoras=servicofinalizado.totaldehoras;
            servicoselecionado.valorhora=servicofinalizado.valorhora;
            new ServicosDAO().finalizaros(servicoselecionado);
            tabelaServicos.getItems().remove(servicoselecionado);
        }




    }
    @FXML
    public void voltar() throws IOException {

        AgendaApplication.setRoot("menu-principal-view");
    }
    @FXML
    public void whatsapp ()throws URISyntaxException, IOException {
        Servico servicotelefone =tabelaServicos.getSelectionModel().getSelectedItem();
        String telefone= servicotelefone.cliente.telefone.replace("(","");
        String telefonecorreto= telefone.replace(")","");
        URI link = new URI("https://wa.me/55"+telefonecorreto);
        Desktop.getDesktop().browse(link);
    }

    @FXML
    public void Habilitabotoes() {
        BooleanBinding algoSelecionado = tabelaServicos.getSelectionModel().selectedItemProperty().isNull();
        cancelar.disableProperty().bind(algoSelecionado);
        editar.disableProperty().bind(algoSelecionado);
        whatsapp.disableProperty().bind(algoSelecionado);
        finalizar.disableProperty().bind(algoSelecionado);

    }

}
