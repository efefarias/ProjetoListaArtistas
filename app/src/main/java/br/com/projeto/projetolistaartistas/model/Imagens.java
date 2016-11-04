package br.com.projeto.projetolistaartistas.model;

/**
 * Created by f.soares.de.farias on 11/4/2016.
 */
@org.parceler.Parcel
public class Imagens {

    String img_id;
    String img_url;

    public Imagens(){
    }

    public Imagens(String img_url, String img_id) {
        this.img_url = img_url;
        this.img_id = img_id;
    }

    public String getImg_id() {
        return img_id;
    }

    public void setImg_id(String img_id) {
        this.img_id = img_id;
    }

    public String getImg_url() {
        return img_url;
    }

    public void setImg_url(String img_url) {
        this.img_url = img_url;
    }
}
