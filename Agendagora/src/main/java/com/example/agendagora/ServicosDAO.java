package com.example.agendagora;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class ServicosDAO {

    public List<Servico> getAll(boolean apenasDiaAtual) throws SQLException {
        String sql = "select os.datadoservico,os.tipodeservico,c.nomecliente,c.enderecocliente,c.fonecliente,ordendeservicoid " + //
                "from ordendeservico as os " + //
                "inner join cliente as c " + //
                "on os.cliente_clienteid = c.clienteid " + //
                "where os.estadodaordem = 'aberto' and os.usuario_usuarioid = ? and c.ativook= 1";
        if (apenasDiaAtual) {
            sql += " and os.datadoservico = curdate()";
        }

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
                    servico1.codigo = rs.getInt(6);

                    servicos.add(servico1);
                }

                return servicos;
            }

        }
    }


    public void insert(Servico novoservico) throws SQLException {
        String sql = "insert into ordendeservico(usuario_usuarioid,cliente_clienteid,datadoservico,estadodaordem,tipodeservico) values ( ?, ?,?,'aberto',?)";
        try (PreparedStatement preparedStatement = ConnectionSigleton.getConnection().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            novoservico.usuario = UsuarioSigleton.usuarioteste;
            preparedStatement.setInt(1, novoservico.usuario.codigo);
            preparedStatement.setInt(2, novoservico.cliente.codigo);
            preparedStatement.setDate(3, novoservico.datadoservico);

            preparedStatement.setString(4, novoservico.tipodoservico);

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

        try (PreparedStatement preparedStatement = ConnectionSigleton.getConnection().prepareStatement("update ordendeservico set datadoservico = ? where ordendeservicoid = ? ")) {
            preparedStatement.setDate(1, servicoselecionado.datadoservico);
            preparedStatement.setInt(2, servicoselecionado.codigo);

            preparedStatement.execute();

        }
    }

    public boolean qtdvagaspordia(Servico servico) throws SQLException {
        String sql = "select count(*) from ordendeservico where datadoservico = ? and estadodaordem = ? and usuario_usuarioid =?";
        try (PreparedStatement preparedStatement = ConnectionSigleton.getConnection().prepareStatement(sql)) {
            preparedStatement.setDate(1, servico.datadoservico);
            preparedStatement.setString(2, AgendaApplication.aberto);
            preparedStatement.setInt(3, UsuarioSigleton.usuarioteste.codigo);
            try (ResultSet rs = preparedStatement.executeQuery()) {
                rs.next();
                int qtd;
                qtd = rs.getInt(1);
                if (qtd < UsuarioSigleton.usuarioteste.qtdvagas || qtd == 0) {
                    return true;
                } else {
                    return false;
                }
            }
        }
    }
    public void finalizaros(Servico servicoselecionado) throws SQLException {
        try (PreparedStatement preparedStatement = ConnectionSigleton.getConnection().prepareStatement("update ordendeservico set estadodaordem = 'finalizada', hora = ?, valorhora = ?, valorfinal = ? where ordendeservicoid = ? ")){
            preparedStatement.setDouble(1, servicoselecionado.totaldehoras);
            preparedStatement.setDouble(2, servicoselecionado.valorhora);
            preparedStatement.setDouble(3, servicoselecionado.valorfinal);
            preparedStatement.setInt(4,servicoselecionado.codigo);

            preparedStatement.execute();
        }
    }

    public int dataatual() throws SQLException {
        String sql = "select count(*) from ordendeservico where datadoservico = curdate()and estadodaordem =  'aberto' and usuario_usuarioid =?";
        try (PreparedStatement preparedStatement = ConnectionSigleton.getConnection().prepareStatement(sql)) {
            preparedStatement.setInt(1, UsuarioSigleton.usuarioteste.codigo);
            try (ResultSet rs = preparedStatement.executeQuery()) {
                rs.next();
                int qtd;
                qtd = rs.getInt(1);
                if (qtd > 0) {
                    int qdtfinal = UsuarioSigleton.usuarioteste.qtdvagas - qtd;
                    return qdtfinal;

                } else {
                    return UsuarioSigleton.usuarioteste.qtdvagas;
                }

            }
        }


    }

    public int dataatual1() throws SQLException {
        String sql = "select count(*) from ordendeservico where datadoservico = curdate() + INTERVAL 1 DAY and estadodaordem = 'aberto' and usuario_usuarioid = ? ";
        try (PreparedStatement preparedStatement = ConnectionSigleton.getConnection().prepareStatement(sql)) {
            preparedStatement.setInt(1, UsuarioSigleton.usuarioteste.codigo);
            try (ResultSet rs = preparedStatement.executeQuery()) {
                rs.next();
                int qtd;
                qtd = rs.getInt(1);
                if (qtd > 0) {
                    int qdtfinal = UsuarioSigleton.usuarioteste.qtdvagas - qtd;
                    return qdtfinal;

                } else {
                    return UsuarioSigleton.usuarioteste.qtdvagas;
                }

            }
        }
    }

    public int dataatual2() throws SQLException {
        String sql = "select count(*) from ordendeservico where datadoservico = curdate() + INTERVAL 2 DAY and estadodaordem =  'aberto' and usuario_usuarioid = ? ";
        try (PreparedStatement preparedStatement = ConnectionSigleton.getConnection().prepareStatement(sql)) {
            preparedStatement.setInt(1, UsuarioSigleton.usuarioteste.codigo);
            try (ResultSet rs = preparedStatement.executeQuery()) {
                rs.next();
                int qtd;
                qtd = rs.getInt(1);
                if (qtd > 0) {
                    int qdtfinal = UsuarioSigleton.usuarioteste.qtdvagas - qtd;
                    return qdtfinal;

                } else {
                    return UsuarioSigleton.usuarioteste.qtdvagas;
                }

            }
        }
    }
    public int dataatual3() throws SQLException {
        String sql = "select count(*) from ordendeservico where datadoservico = curdate() + INTERVAL 3 DAY and estadodaordem =  'aberto' and usuario_usuarioid = ? ";
        try (PreparedStatement preparedStatement = ConnectionSigleton.getConnection().prepareStatement(sql)) {
            preparedStatement.setInt(1, UsuarioSigleton.usuarioteste.codigo);
            try (ResultSet rs = preparedStatement.executeQuery()) {
                rs.next();
                int qtd;
                qtd = rs.getInt(1);
                if (qtd > 0) {
                    int qdtfinal = UsuarioSigleton.usuarioteste.qtdvagas - qtd;
                    return qdtfinal;

                } else {
                    return UsuarioSigleton.usuarioteste.qtdvagas;
                }

            }
        }
    }
    public int dataatual4() throws SQLException {
        String sql = "select count(*) from ordendeservico where datadoservico = curdate() + INTERVAL 4 DAY and estadodaordem =  'aberto' and usuario_usuarioid = ? ";
        try (PreparedStatement preparedStatement = ConnectionSigleton.getConnection().prepareStatement(sql)) {
            preparedStatement.setInt(1, UsuarioSigleton.usuarioteste.codigo);
            try (ResultSet rs = preparedStatement.executeQuery()) {
                rs.next();
                int qtd;
                qtd = rs.getInt(1);
                if (qtd > 0) {
                    int qdtfinal = UsuarioSigleton.usuarioteste.qtdvagas - qtd;
                    return qdtfinal;

                } else {
                    return UsuarioSigleton.usuarioteste.qtdvagas;
                }

            }
        }
    }

}


