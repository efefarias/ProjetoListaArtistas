package br.com.projeto.projetolistaartistas.model;

/**
 * Created by f.soares.de.farias on 10/1/2016.
 */
@org.parceler.Parcel
public class Obra {

    private String obr_id;
    private String obr_descricao;
    private String cat_obra_descricao;
    private String img_url;

    public Obra(){}

    public Obra(String obr_id, String obr_descricao, String cat_obra_descricao, String img_url) {
        this.obr_id = obr_id;
        this.obr_descricao = obr_descricao;
        this.cat_obra_descricao = cat_obra_descricao;
        this.img_url = img_url;
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

    public String getImg_url() {
        return img_url;
    }

    public void setImg_url(String img_url) {
        this.img_url = img_url;
    }
}
