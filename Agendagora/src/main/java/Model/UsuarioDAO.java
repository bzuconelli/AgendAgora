package Model;

import com.example.agendagora.AgendaApplication;
import com.example.agendagora.ConnectionSigleton;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UsuarioDAO {

    public List<Usuario> getAll() throws SQLException {

        try (Statement statement = ConnectionSigleton.getConnection().createStatement();
             ResultSet rs = statement.executeQuery("select * from usuario where ativook =1 order by nome asc")) {
            List<Usuario> usuarios = new ArrayList<>();
            while (rs.next()) {
                Usuario usuario = new Usuario();
                usuario.codigo = rs.getInt(1);
                usuario.usuario = rs.getString(2);
                usuario.senha = rs.getString(3);
                usuario.nome = rs.getString(4);
                usuario.endereco = rs.getString(5);
                usuario.cpfouCNPJ = rs.getString(6);
                usuario.ramodeatividade = rs.getString(7);
                usuario.telefone = rs.getString(8);
                usuario.qtdvagas = rs.getInt(9);

                usuarios.add(usuario);
            }

            return usuarios;
        }
    }

    public void insert(Usuario novousuario) throws SQLException {
        try (PreparedStatement preparedStatement = ConnectionSigleton.getConnection().prepareStatement("insert into usuario (login,senha,nome,ativook) value (?,?,?,'1' )", Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, novousuario.usuario);
            preparedStatement.setString(2, novousuario.senha);
            preparedStatement.setString(3, novousuario.nome);

            preparedStatement.execute();
            try (ResultSet rs = preparedStatement.getGeneratedKeys()) {
                rs.next();
                novousuario.codigo = rs.getInt(1);


            }

        }
    }

    public void delete(Usuario usuariexcluido) throws SQLException {

        try (PreparedStatement preparedStatement = ConnectionSigleton.getConnection().prepareStatement("update  usuario  set ativook = 0 where usuarioid = ?")) {
            preparedStatement.setInt(1, usuariexcluido.codigo);
            preparedStatement.execute();
        }
    }

    public void update(Usuario usuarioselecionado) throws SQLException {

        try (PreparedStatement preparedStatement = ConnectionSigleton.getConnection().prepareStatement("update usuario set nome = ?, login = ?,senha = ? where usuarioid = ?")) {
            preparedStatement.setString(1, usuarioselecionado.nome);
            preparedStatement.setString(2, usuarioselecionado.usuario);
            preparedStatement.setString(3, usuarioselecionado.senha);
            preparedStatement.setInt(4, usuarioselecionado.codigo);

            preparedStatement.execute();
        }
    }

    public void updateinfo(Usuario usuarioselecionado) throws SQLException {
        try (PreparedStatement preparedStatement = ConnectionSigleton.getConnection().prepareStatement("update usuario set endreco = ?, cpfCNPJ = ?, ramodeatividade = ?, telefone = ?, qtdvagas = ? where usuarioid = ?")) {

            preparedStatement.setString(1, usuarioselecionado.endereco);
            preparedStatement.setString(2, usuarioselecionado.cpfouCNPJ);
            preparedStatement.setString(3, usuarioselecionado.ramodeatividade);
            preparedStatement.setString(4, usuarioselecionado.telefone);
            preparedStatement.setInt(5, usuarioselecionado.qtdvagas);
            preparedStatement.setInt(6, usuarioselecionado.codigo);

            preparedStatement.execute();
        }

    }
    public Usuario usuariologadoinfo(Usuario usuariologado) throws SQLException {
        String sql = "select * from usuario  where login = ? and senha = ?";
        try (PreparedStatement preparedStatement = ConnectionSigleton.getConnection().prepareStatement(sql)) {
            preparedStatement.setString(1, usuariologado.usuario);
            preparedStatement.setString(2, usuariologado.senha);
            try (ResultSet resultado = preparedStatement.executeQuery()) {
                resultado.next();
                Usuario usuario = new Usuario();
                usuario.codigo = resultado.getInt(1);
                usuario.usuario = resultado.getString(2);
                usuario.senha = resultado.getString(3);
                usuario.nome = resultado.getString(4);
                usuario.endereco = resultado.getString(5);
                usuario.cpfouCNPJ = resultado.getString(6);
                usuario.ramodeatividade = resultado.getString(7);
                usuario.telefone = resultado.getString(8);
                usuario.qtdvagas = resultado.getInt(9);
                return usuario;
            }
        }

    }
    public boolean existe(Usuario usuario) throws SQLException {
        String sql = "select count(*) from usuario  where login = ? and senha = ? and ativook = 1";
        try (PreparedStatement preparedStatement = ConnectionSigleton.getConnection().prepareStatement(sql)) {
            preparedStatement.setString(1, usuario.usuario);
            preparedStatement.setString(2, usuario.senha);
            try (ResultSet resultado = preparedStatement.executeQuery()) {
                resultado.next();
                int quantidadedeusuarios = resultado.getInt(1);

                if (quantidadedeusuarios == 1) {
                    return true;
                } else {
                    return false;
                }
            }

        }
    }

    public boolean loginexiste(Usuario usuario) throws SQLException {
        String sql = "select count(*) from usuario  where login= ? and ativook = 1 ";
        try (PreparedStatement preparedStatement = ConnectionSigleton.getConnection().prepareStatement(sql)) {
            preparedStatement.setString(1, usuario.usuario);



            try (ResultSet resultado = preparedStatement.executeQuery()) {
                resultado.next();
                int quantidadedeusuarios = resultado.getInt(1);

                if (quantidadedeusuarios == 1) {
                    return true;
                } else {
                    return false;
                }
            }

        }
    }
//    public boolean qtdusuarios() throws SQLException {
//        String sql = "select count(*) from usuario where ativook= 1 ";
//        try( Statement statement = ConnectionSigleton.getConnection().createStatement();
//            ResultSet rs = statement.executeQuery(sql)){
//                rs.next();
//                int quantidadedeusuarios = rs.getInt(1);
//
//                if (quantidadedeusuarios > 1) {
//                    return true;
//                } else {
//                    return false;
//                }
//            }
//
//        }
    }

