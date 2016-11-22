package br.com.projeto.projetolistaartistas.model;

/**
 * Created by f.soares.de.farias on 10/20/2016.
 */
@org.parceler.Parcel
public class Avaliacao {

    private long ava_id;
    private String ava_titulo;
    private String ava_descricao;
    private double ava_nota;
    private String ava_ativo;
    private long usu_id_artista;

    public Avaliacao(){}

    public Avaliacao(long ava_id, String ava_email, String ava_titulo, String ava_descricao, String ava_nome, double ava_nota, String ava_ativo, long usu_id_artista) {
        this.ava_id = ava_id;
        this.ava_titulo = ava_titulo;
        this.ava_descricao = ava_descricao;
        this.ava_nota = ava_nota;
        this.ava_ativo = ava_ativo;
        this.usu_id_artista = usu_id_artista;
    }

    public long getAva_id() {
        return ava_id;
    }

    public void setAva_id(long ava_id) {
        this.ava_id = ava_id;
    }


    public String getAva_titulo() {
        return ava_titulo;
    }

    public void setAva_titulo(String ava_titulo) {
        this.ava_titulo = ava_titulo;
    }

    public String getAva_descricao() {
        return ava_descricao;
    }

    public void setAva_descricao(String ava_descricao) {
        this.ava_descricao = ava_descricao;
    }


    public double getAva_nota() {
        return ava_nota;
    }

    public void setAva_nota(double ava_nota) {
        this.ava_nota = ava_nota;
    }

    public String getAva_ativo() {
        return ava_ativo;
    }

    public void setAva_ativo(String ava_ativo) {
        this.ava_ativo = ava_ativo;
    }

    public long getUsu_id_artista() {
        return usu_id_artista;
    }

    public void setUsu_id_artista(long usu_id_artista) {
        this.usu_id_artista = usu_id_artista;
    }
}
