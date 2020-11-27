package www.runa.registrofbs;

public class FichaPokemon {

    String NombrePKM;
    String Ataque;
    String Vida;
    String Defensa;

    public FichaPokemon(){

    }

    public FichaPokemon(String nombrepkm,String ataque, String vida, String defensa) {
        this.NombrePKM = nombrepkm;
        this.Ataque = ataque;
        this.Vida = vida;
        this.Defensa = defensa;
    }

    public String getNombrePKM() {
        return NombrePKM;
    }

    public void setNombrePKM(String nombrePKM) {
        NombrePKM = nombrePKM;
    }

    public String getAtaque() {
        return Ataque;
    }

    public void setAtaque(String ataque) {
        Ataque = ataque;
    }

    public String getVida() {
        return Vida;
    }

    public void setVida(String vida) {
        Vida = vida;
    }

    public String getDefensa() {
        return Defensa;
    }

    public void setDefensa(String defensa) {
        Defensa = defensa;
    }
}