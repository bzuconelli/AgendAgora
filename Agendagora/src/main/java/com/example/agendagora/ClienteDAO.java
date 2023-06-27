package com.example.agendagora;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ClienteDAO {
    public List<Cliente> getAll() throws SQLException {

        try (Statement statement = ConnectionSigleton.getConnection().createStatement();
             ResultSet rs = statement.executeQuery("select * from cliente ORDER BY nomecliente asc")) {
            List<Cliente> clientes = new ArrayList<>();
            while (rs.next()) {
                Cliente cliente = new Cliente();
                cliente.codigo = rs.getInt(1);
                cliente.nome = rs.getString(2);
                cliente.endereco = rs.getString(3);
                cliente.telefone = rs.getString(4);
                clientes.add(cliente);
            }
            return clientes;
        }
    }

    public void insert(Cliente novocliente) throws SQLException {

        try (PreparedStatement preparedStatement = ConnectionSigleton.getConnection().prepareStatement("insert into cliente(nomecliente,enderecocliente,fonecliente) values (?,?,?)", Statement.RETURN_GENERATED_KEYS)) {

            preparedStatement.setString(1, novocliente.nome);
            preparedStatement.setString(2, novocliente.endereco);
            preparedStatement.setString(3, novocliente.telefone);

            preparedStatement.execute();
            try (ResultSet rs = preparedStatement.getGeneratedKeys()) {
                rs.next();
                novocliente.codigo = rs.getInt(1);


            }

        }
    }


    public void delete(Cliente clienteexcluido) throws SQLException {

        try (PreparedStatement preparedStatement = ConnectionSigleton.getConnection().prepareStatement("delete from cliente  where clienteid = ?")) {
            preparedStatement.setInt(1, clienteexcluido.codigo);
            preparedStatement.execute();
        }
    }

    public void update(Cliente clienteselecionado) throws SQLException {

        try (PreparedStatement preparedStatement = ConnectionSigleton.getConnection().prepareStatement("update cliente set nomecliente = ?, enderecocliente =?,fonecliente= ? where clienteid = ?")) {
            preparedStatement.setString(1, clienteselecionado.nome);
            preparedStatement.setString(2, clienteselecionado.endereco);
            preparedStatement.setString(3, clienteselecionado.telefone);
            preparedStatement.setInt(4, clienteselecionado.codigo);

            preparedStatement.execute();

        }
    }

    public boolean clienteexiste(Cliente cliente) throws SQLException {

        if (cliente != null) {
            String sql = "select count(*)from cliente where fonecliente = ?";
            try (PreparedStatement preparedStatement = ConnectionSigleton.getConnection().prepareStatement(sql)) {
                preparedStatement.setString(1, cliente.telefone);
                try (ResultSet resultado = preparedStatement.executeQuery()) {
                    resultado.next();
                    int clienteexiste = resultado.getInt(1);
                    if (clienteexiste > 0) {
                        return true;
                    } else {
                        return false;
                    }
                }
            }
        }
        return false;
    }

    public Cliente clientecadastrado(Cliente clientepesquisa) throws SQLException {
        String sql = "select * from cliente  where fonecliente = ? ";
        try (PreparedStatement preparedStatement = ConnectionSigleton.getConnection().prepareStatement(sql)) {
            preparedStatement.setString(1, clientepesquisa.telefone);

            try (ResultSet resultado = preparedStatement.executeQuery()) {
                resultado.next();
                Cliente cliente = new Cliente();


                cliente.codigo = resultado.getInt(1);
                cliente.nome = resultado.getString(2);
                cliente.endereco = resultado.getString(3);
                cliente.telefone = resultado.getString(4);
                return cliente;

            }

        }
    }
}






