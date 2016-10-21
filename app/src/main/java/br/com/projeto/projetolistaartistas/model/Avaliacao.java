package br.com.projeto.projetolistaartistas.model;

/**
 * Created by f.soares.de.farias on 10/20/2016.
 */
@org.parceler.Parcel
public class Avaliacao {

    private String id_nota;
    private Integer nota;
    private char flag_nota_usuario;

    public Avaliacao(){

    }

    public Avaliacao(String id_nota, Integer nota, char flag_nota_usuario) {
        this.id_nota = id_nota;
        this.nota = nota;
        this.flag_nota_usuario = flag_nota_usuario;
    }

    public String getId_nota() {
        return id_nota;
    }

    public void setId_nota(String id_nota) {
        this.id_nota = id_nota;
    }

    public Integer getNota() {
        return nota;
    }

    public void setNota(Integer nota) {
        this.nota = nota;
    }

    public char getFlag_nota_usuario() {
        return flag_nota_usuario;
    }

    public void setFlag_nota_usuario(char flag_nota_usuario) {
        this.flag_nota_usuario = flag_nota_usuario;
    }
}
