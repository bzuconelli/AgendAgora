package com.example.agendagora;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class ServicosDAO {

    public List<Servico> getAll() throws SQLException {
        String sql =  "select os.datadoservico,os.tipodeservico,c.nomecliente,c.enderecocliente,c.fonecliente,ordendeservicoid from ordendeservico as os inner join cliente as c on os.cliente_clienteid = c.clienteid where os.estadodaordem = 'aberto' and os.usuario_usuarioid = ? and c.ativook= 1";
        try (PreparedStatement preparedStatement = ConnectionSigleton.getConnection().prepareStatement(sql)) {
            preparedStatement.setInt(1, UsuarioSigleton.usuarioteste.codigo);
            try (ResultSet rs = preparedStatement.executeQuery()) {

                List<Servico> servicos = new ArrayList<>();
                while (rs.next()) {
                    Servico servico1 = new Servico();
                    servico1.datadoservico = rs.getDate(1);
                    servico1.tipodoservico = rs.getString(2);
                    servico1.cliente = new Cliente();
                    servico1.cliente.nome = rs.getString(3);
                    servico1.cliente.endereco = rs.getString(4);
                    servico1.cliente.telefone = rs.getString(5);
                    servico1.codigo= rs.getInt(6);

                    servicos.add(servico1);
                }

                return servicos;
            }

        }
    }
    public void insert(Servico novoservico) throws SQLException {
        String sql = "insert into ordendeservico(usuario_usuarioid,cliente_clienteid,datadoservico,estadodaordem,tipodeservico) values ( ?, ?,?,?,?)";
        try (PreparedStatement preparedStatement = ConnectionSigleton.getConnection().prepareStatement(sql,Statement.RETURN_GENERATED_KEYS)){

            novoservico.usuario= UsuarioSigleton.usuarioteste;
            preparedStatement.setInt(1, novoservico.usuario.codigo);
            preparedStatement.setInt(2, novoservico.cliente.codigo);
            preparedStatement.setDate(3, novoservico.datadoservico);
            preparedStatement.setString(4, AgendaApplication.aberto);
            preparedStatement.setString(5, novoservico.tipodoservico);

            preparedStatement.execute();
            try (ResultSet rs = preparedStatement.getGeneratedKeys()) {
                rs.next();
                novoservico.codigo = rs.getInt(1);
            }

        }


    }

    public void delete(Servico servicocancelado) throws SQLException {

        try (PreparedStatement preparedStatement = ConnectionSigleton.getConnection().prepareStatement("update ordendeservico set estadodaordem = 'cancelada' where ordendeservicoid = ? ")) {
            preparedStatement.setInt(1, servicocancelado.codigo);
            preparedStatement.execute();
        }
    }

    public void update(Servico servicoselecionado) throws SQLException {

        try (PreparedStatement preparedStatement = ConnectionSigleton.getConnection().prepareStatement("update ordendeservico set datadoservico = ? where ordendeservicoid = ? ")){
            preparedStatement.setDate(1,servicoselecionado.datadoservico);
            preparedStatement.setInt(2,servicoselecionado.codigo);

            preparedStatement.execute();

        }
    }
    public int dataatual() throws SQLException {
        String sql= "select count(*) from ordendeservico where datadoservico=curdate()and estadodaordem = ? and usuario_usuarioid =?";
        try (PreparedStatement preparedStatement = ConnectionSigleton.getConnection().prepareStatement(sql)) {
            preparedStatement.setString(1, AgendaApplication.aberto);
            preparedStatement.setInt(2, UsuarioSigleton.usuarioteste.codigo);
            try (ResultSet rs = preparedStatement.executeQuery()) {
                int qtd =rs.getInt(1);
                return qtd;

            }
        }


    }

}
