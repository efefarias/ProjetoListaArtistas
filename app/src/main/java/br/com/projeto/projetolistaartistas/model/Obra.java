package br.com.projeto.projetolistaartistas.model;

/**
 * Created by f.soares.de.farias on 10/1/2016.
 */
@org.parceler.Parcel
public class Obra {

    private long id_obra;
    private String nome_obra;
    private String img_obra;

    public Obra(long id_obra, String nome_obra, String img_obra) {
        this.id_obra = id_obra;
        this.nome_obra = nome_obra;
        this.img_obra = img_obra;
    }

    public Obra() {
    }

    public long getId_obra() {
        return id_obra;
    }

    public void setId_obra(long id_obra) {
        this.id_obra = id_obra;
    }

    public String getNome_obra() {
        return nome_obra;
    }

    public void setNome_obra(String nome_obra) {
        this.nome_obra = nome_obra;
    }

    public String getImg_obra() {
        return img_obra;
    }

    public void setImg_obra(String img_obra) {
        this.img_obra = img_obra;
    }
}
