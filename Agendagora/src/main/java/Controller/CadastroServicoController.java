package Controller;

import Model.*;
import com.example.agendagora.*;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;

import java.net.URL;
import java.sql.Date;
import java.sql.SQLException;
import java.util.ResourceBundle;


public class CadastroServicoController implements Initializable {
    @FXML
    TextField telefoneField;
    @FXML
    TextField nomeField;

    @FXML
    TextField enderecoField;
    @FXML
    DatePicker datadoservicoField;

    @FXML
    TextField tipodeservicoField;


    public static Servico servico;



    Cliente clientepequisado;
    @FXML
    public void salvar() throws SQLException {

        Servico novoServico = new Servico();
        novoServico.cliente= new Cliente();
        novoServico.cliente.nome=nomeField.getText();
        novoServico.cliente.endereco=enderecoField.getText();
        novoServico.cliente.telefone=telefoneField.getText();
        if (!(datadoservicoField.getValue() ==null)) {
            novoServico.datadoservico = Date.valueOf(datadoservicoField.getValue());
        }

        if (clientepequisado!= null){
            novoServico.tipodoservico= tipodeservicoField.getText();
            novoServico.cliente.codigo=clientepequisado.codigo;
        }

        if(!nomeField.getText().isBlank() &&!enderecoField.getText().isBlank() && !telefoneField.getText().isBlank() &&  !tipodeservicoField.getText().isBlank() && datadoservicoField.getValue()!=null){

            boolean tenvaga = new ServicosDAO().qtdvagaspordia(novoServico);
            if(tenvaga && UsuarioSigleton.usuarioteste.qtdvagas>0) {
                servico = novoServico;
                AgendaApplication.closeCurrentWindow();
            }else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Informações");
                alert.setHeaderText(null);
                alert.setContentText(" Sem vagas disponíveis neste dia! " );

                alert.showAndWait();

            }

        }else{
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Informações");
            alert.setHeaderText(null);
            alert.setContentText(" Um ou mais campos estão vazios. Todos os campos com '*' devem ser preenchidos" );

            alert.showAndWait();

        }
    }

    @FXML
    public void Pesqisar() throws  SQLException {
        Cliente pesquisacliente =new Cliente();

        if (!telefoneField.getText().isBlank() && telefoneField!= null){

            pesquisacliente.telefone=telefoneField.getText();

            boolean clienteexiste = new ClienteDAO().clienteexiste(pesquisacliente);
            if (clienteexiste){
                if(telefoneField.getText().matches("^\\(?[1-9]{2}\\)? ?(?:[2-8]|9[1-9])[0-9]{3}\\-?[0-9]{4}$")) {
                    Cliente clientestacadastrado = new ClienteDAO().clientecadastrado(pesquisacliente);

                    nomeField.setText(clientestacadastrado.nome);
                    enderecoField.setText(clientestacadastrado.endereco);
                    clientepequisado = clientestacadastrado;
                }else{
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Informações");
                    alert.setHeaderText(null);
                    alert.setContentText("O campo telefone deve ser prenchido no formato (DDD)999999999" );

                    alert.showAndWait();
                }

           }else{
               Alert alert = new Alert(Alert.AlertType.WARNING);
               alert.setTitle("");
               alert.setHeaderText(null);
               alert.setContentText("Cliente não cadastrado");

               alert.showAndWait();
           }
        }
    }
    @FXML
    public void cancelar() {
        AgendaApplication.closeCurrentWindow();

    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Servico servicoselecionado = CadastroServicoController.servico;


        if (servicoselecionado != null) {

            nomeField.setText(servicoselecionado.cliente.nome);
            enderecoField.setText(servicoselecionado.cliente.endereco);
            telefoneField.setText(servicoselecionado.cliente.telefone);
            tipodeservicoField.setText(servicoselecionado.tipodoservico);
            datadoservicoField.setValue(servicoselecionado.datadoservico.toLocalDate());
        }
        tipodeservicoField.textProperty().addListener((o,oldValue, newValue)->{
            tipodeservicoField.setText(newValue.replaceAll("(\\d+)|",""));

        });

    }
}
