package Controller;

import com.example.agendagora.AgendaApplication;
import Model.ServicosDAO;
import Model.UsuarioSigleton;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

import static java.time.LocalDate.now;

public class MenuPrincipalController  implements Initializable  {
//
    @FXML
    Label diaatual;
    @FXML
    Label diaatual1;
    @FXML
    Label diaatual2;
    @FXML
    Label diaatual3;
    @FXML
    Label diaatual4;
    @FXML
    Label dia1field;
    @FXML
    Label dia2field;
    @FXML
    Label dia3field;
    @FXML
    Label dia4field;
    @FXML
    Label dia5field;

    int dia1;
    int dia2;
    int dia3;
    int dia4;
    int dia5;

    @FXML
    public void abrirtelaclientes() throws IOException {
        AgendaApplication.setRoot("cliente-view");
    }

    @FXML
    public void abrirtelaconfiguracoes() throws IOException {
        AgendaApplication.setRoot("configuracoes-view");
    }

    @FXML
    public void abrirtelaservicos() throws IOException {
        AgendaApplication.setRoot("servicos-view");
    }
    @FXML
    public void abrirtelaprojetos() throws IOException {
        AgendaApplication.setRoot("projetos-view");
    }

    @FXML
    public void voltar() throws IOException {
        UsuarioSigleton.usuarioteste = null;
        AgendaApplication.setRoot("login-view");

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
       try {
         dia1 = new ServicosDAO().dataatual();
         dia2=new ServicosDAO().dataatual1();
         dia3=new ServicosDAO().dataatual2();
         dia4=new ServicosDAO().dataatual3();
         dia5=new ServicosDAO().dataatual4();
      } catch (SQLException e) {
           throw new RuntimeException(e);
      }
      diaatual.setText(Integer.toString(dia1));
      diaatual1.setText(Integer.toString(dia2));
      diaatual2.setText(Integer.toString(dia3));
      diaatual3.setText(Integer.toString(dia4));
      diaatual4.setText(Integer.toString(dia5));

      LocalDate dataatual = now();
      LocalDate dataatual1 = now().plusDays(1);
      LocalDate dataatual2 = now().plusDays(2);
      LocalDate dataatual3 = now().plusDays(3);
      LocalDate dataatual4 = now().plusDays(4);
      
      DateTimeFormatter fmt =DateTimeFormatter.ofPattern("dd/MM");

      dia1field.setText(dataatual.format(fmt));
      dia2field.setText(dataatual1.format(fmt));
      dia3field.setText(dataatual2.format(fmt));
      dia4field.setText(dataatual3.format(fmt));
      dia5field.setText(dataatual4.format(fmt));




    }


}
