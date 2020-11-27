package www.runa.registrofbs;

public class UsuarioR {

    private String id;
    private String Usuario;
    private String Contraseña;


    public UsuarioR() {

    }

    public UsuarioR(String id, String usuario, String contraseña) {
        this.id = id;
        this.Usuario = usuario;
        this.Contraseña = contraseña;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsuario() {
        return Usuario;
    }

    public void setUsuario(String usuario) {
        Usuario = usuario;
    }

    public String getContraseña() {
        return Contraseña;
    }

    public void setContraseña(String contraseña) {
        Contraseña = contraseña;
    }
}