package com.example.agendagora;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ServicosDAO {

    public List<Servico> getAll() throws SQLException {
        String sql =  "select os.datadoservico,os.tipodeservico,c.nomecliente,c.enderecocliente,c.fonecliente from ordendeservico as os inner join cliente as c on os.cliente_clienteid = c.clienteid where os.estadodaordem = 'aberto' and os.usuario_usuarioid = ?";
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

                    servicos.add(servico1);
                }

                return servicos;
            }

        }
    }
    public void insert(Servico novoservico) throws SQLException {
//        try (PreparedStatement preparedStatement = ConnectionSigleton.getConnection().prepareStatement())


    }

}
