package com.example.agendagora;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class UsuarioSigleton {
    public static Usuario usuarioteste ;

    public static Usuario getUsuario(Usuario usuariologado) {

        if (usuarioteste == null) {
            usuarioteste = usuariologado;


        }
        return usuarioteste;

    }
}
