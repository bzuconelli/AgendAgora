package Model;

public class UsuarioSigleton {
    public static Usuario usuarioteste ;

    public static Usuario getUsuario(Usuario usuariologado) {

        if (usuarioteste == null) {
            usuarioteste = usuariologado;


        }
        return usuarioteste;

    }
}
