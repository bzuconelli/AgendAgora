package Controller;

import com.example.agendagora.AgendaApplication;
import Model.Cliente;
import Model.Servico;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;


public class ServicoFinalizarController implements Initializable {

    @FXML
    TextField codigodaosField;
    @FXML
    TextField nomeclienteField;
    @FXML
    TextField valorhoraField;
    @FXML
    TextField valortotalField;
    @FXML
    TextField totaldehoras;
    @FXML
    Button Finalizar;

    public static Servico finalizaros;
    double valortotal;

    @FXML
    public void salvar() {
        Servico novoServico = new Servico();
        if (!valortotalField.getText().equals("0.0") && !valorhoraField.getText().equals("0.0")&& !totaldehoras.getText().equals("0.0")){
            novoServico.codigo = Integer.parseInt(codigodaosField.getText());
            novoServico.cliente= new Cliente();
            novoServico.cliente.nome = (nomeclienteField.getText());
            novoServico.valorhora =  Double.parseDouble(valorhoraField.getText());
            novoServico.totaldehoras =  Double.parseDouble(totaldehoras.getText());
            novoServico.valorfinal = valortotal;
            finalizaros=novoServico;
            AgendaApplication.closeCurrentWindow();

        } else {

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Informações");
            alert.setHeaderText(null);
            alert.setContentText("Os campos com '*' devem ser preenchidos com valor maior que 0");

            alert.showAndWait();
        }
    }
    @FXML
    public void calcular(){
        valortotal = Double.parseDouble(valorhoraField.getText()) * Double.parseDouble(totaldehoras.getText());
        valortotalField.setText(Double.toString(valortotal));
        Finalizar.setDisable(false);

    }
    @FXML
    public void voltar() throws IOException {
        AgendaApplication.closeCurrentWindow();
    }




    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Servico servicoselecionado = ServicoFinalizarController.finalizaros;
        if (servicoselecionado != null) {
            codigodaosField.setText(Integer.toString(servicoselecionado.codigo));
            nomeclienteField.setText(servicoselecionado.cliente.nome);
            totaldehoras.setText(Double.toString(servicoselecionado.totaldehoras));
            valorhoraField.setText(Double.toString(servicoselecionado.valorhora));
            valortotalField.setText(Double.toString(servicoselecionado.valorfinal));

        }
        Finalizar.setDisable(true);


    }
}

