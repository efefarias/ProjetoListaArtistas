package br.com.projeto.projetolistaartistas.model;

import java.util.List;

/**
 * Created by f.soares.de.farias on 10/1/2016.
 */
@org.parceler.Parcel
public class Obra {

    private String obr_id;
    private String obr_titulo;
    private String obr_descricao;
    private String cat_obra_descricao;
    private List<Imagens> imagens;

    public Obra(){}

    public Obra(String obr_id, String obr_titulo,String obr_descricao, String cat_obra_descricao, List<Imagens> imagens) {
        this.obr_id = obr_id;
        this.obr_titulo = obr_titulo;
        this.obr_descricao = obr_descricao;
        this.cat_obra_descricao = cat_obra_descricao;
        this.imagens = imagens;
    }

    public String getObr_id() {
        return obr_id;
    }

    public void setObr_id(String obr_id) {
        this.obr_id = obr_id;
    }

    public String getObr_descricao() {
        return obr_descricao;
    }

    public void setObr_descricao(String obr_descricao) {
        this.obr_descricao = obr_descricao;
    }

    public String getCat_obra_descricao() {
        return cat_obra_descricao;
    }

    public void setCat_obra_descricao(String cat_obra_descricao) {
        this.cat_obra_descricao = cat_obra_descricao;
    }

    public List<Imagens> getImagens() {
        return imagens;
    }

    public void setImagens(List<Imagens> imagens) {
        this.imagens = imagens;
    }

    public String getObr_titulo() {
        return obr_titulo;
    }

    public void setObr_titulo(String obr_titulo) {
        this.obr_titulo = obr_titulo;
    }
}
