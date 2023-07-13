package Controller;

import Model.ServicosDAO;
import com.example.agendagora.AgendaApplication;
import Model.Servico;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;

import java.net.URL;
import java.sql.Date;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class AlteraDataServicoController implements Initializable {
    public static Servico servico;

    Servico servicoselecionado;
    @FXML
    public void salvar() throws SQLException {
        Servico novoServico = new Servico();
        if (!(datadoservicoField.getValue() ==null)) {
            novoServico.datadoservico = Date.valueOf(datadoservicoField.getValue());
            novoServico.codigo=servicoselecionado.codigo;


        }else{
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Informações");
            alert.setHeaderText(null);
            alert.setContentText("O campo da data deve ser preenchido");

            alert.showAndWait();
        }
        boolean tenvaga = new ServicosDAO().qtdvagaspordia(novoServico);

        if (datadoservicoField!=null && tenvaga){
            servico=novoServico;
            AgendaApplication.closeCurrentWindow();
        }else if(!tenvaga && novoServico.codigo == servicoselecionado.codigo && novoServico.datadoservico == servicoselecionado.datadoservico){
            servico=novoServico;
            AgendaApplication.closeCurrentWindow();
        }else {

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Informações");
            alert.setHeaderText(null);
            alert.setContentText(" Sem vagas disponíveis neste dia ");

            alert.showAndWait();
        }
    }


    @FXML
    DatePicker datadoservicoField;
    @FXML
    public void cancelar() {
        AgendaApplication.closeCurrentWindow();

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle){
            servicoselecionado = AlteraDataServicoController.servico;

            if (servicoselecionado != null) {

                datadoservicoField.setValue(servicoselecionado.datadoservico.toLocalDate());
            }

    }

}
