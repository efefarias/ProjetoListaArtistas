package br.com.projeto.projetolistaartistas.model;

import java.util.Date;

/**
 * Created by f.soares.de.farias on 10/20/2016.
 */
@org.parceler.Parcel
public class Avaliacao {

    private String id_nota;
    private Double nota;
    private char flag_nota_usuario;
    private Date dataVoto;

    public Avaliacao(){

    }

    public Avaliacao(String id_nota, Double nota, char flag_nota_usuario, Date dataVoto) {
        this.id_nota = id_nota;
        this.nota = nota;
        this.flag_nota_usuario = flag_nota_usuario;
        this.dataVoto = dataVoto;
    }


    public String getId_nota() {
        return id_nota;
    }

    public void setId_nota(String id_nota) {
        this.id_nota = id_nota;
    }

    public Double getNota() {
        return nota;
    }

    public void setNota(Double nota) {
        this.nota = nota;
    }

    public char getFlag_nota_usuario() {
        return flag_nota_usuario;
    }

    public void setFlag_nota_usuario(char flag_nota_usuario) {
        this.flag_nota_usuario = flag_nota_usuario;
    }

    public Date getDataVoto() {
        return dataVoto;
    }

    public void setDataVoto(Date dataVoto) {
        this.dataVoto = dataVoto;
    }
}
