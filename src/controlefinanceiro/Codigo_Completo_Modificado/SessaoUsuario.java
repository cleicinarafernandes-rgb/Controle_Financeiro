package controlefinanceiro.Codigo_Completo_Modificado;

public class SessaoUsuario {
    private static String usuarioLogado;

    public static void login(String usuario) {
        usuarioLogado = usuario;
    }

    public static String getUsuarioLogado() {
        return usuarioLogado;
    }
}