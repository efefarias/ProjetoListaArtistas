package br.com.projeto.projetolistaartistas.model;

/**
 * Created by f.soares.de.farias on 10/27/2016.
 */
@org.parceler.Parcel
public class Atelie {

    private String ate_id;
    private String ate_nome;
    private String ate_endereco;
    private String ate_cep;
    private String ate_complemento;
    private String ate_latitude;
    private String ate_longitude;
    private String ate_cidade;
    private String ate_estado;
    private String ate_meso_regiao;

    public Atelie(){

    }

    public Atelie(String ate_id, String ate_nome, String ate_endereco, String ate_cep, String ate_complemento, String ate_latitude, String ate_longitude, String ate_cidade, String ate_estado, String ate_meso_regiao) {
        this.ate_id = ate_id;
        this.ate_nome = ate_nome;
        this.ate_endereco = ate_endereco;
        this.ate_cep = ate_cep;
        this.ate_complemento = ate_complemento;
        this.ate_latitude = ate_latitude;
        this.ate_longitude = ate_longitude;
        this.ate_cidade = ate_cidade;
        this.ate_estado = ate_estado;
        this.ate_meso_regiao = ate_meso_regiao;
    }

    public String getAte_id() {
        return ate_id;
    }

    public void setAte_id(String ate_id) {
        this.ate_id = ate_id;
    }

    public String getAte_nome() {
        return ate_nome;
    }

    public void setAte_nome(String ate_nome) {
        this.ate_nome = ate_nome;
    }

    public String getAte_endereco() {
        return ate_endereco;
    }

    public void setAte_endereco(String ate_endereco) {
        this.ate_endereco = ate_endereco;
    }

    public String getAte_cep() {
        return ate_cep;
    }

    public void setAte_cep(String ate_cep) {
        this.ate_cep = ate_cep;
    }

    public String getAte_complemento() {
        return ate_complemento;
    }

    public void setAte_complemento(String ate_complemento) {
        this.ate_complemento = ate_complemento;
    }

    public String getAte_latitude() {
        return ate_latitude;
    }

    public void setAte_latitude(String ate_latitude) {
        this.ate_latitude = ate_latitude;
    }

    public String getAte_longitude() {
        return ate_longitude;
    }

    public void setAte_longitude(String ate_longitude) {
        this.ate_longitude = ate_longitude;
    }

    public String getAte_cidade() {
        return ate_cidade;
    }

    public void setAte_cidade(String ate_cidade) {
        this.ate_cidade = ate_cidade;
    }

    public String getAte_estado() {
        return ate_estado;
    }

    public void setAte_estado(String ate_estado) {
        this.ate_estado = ate_estado;
    }

    public String getAte_meso_regiao() {
        return ate_meso_regiao;
    }

    public void setAte_meso_regiao(String ate_meso_regiao) {
        this.ate_meso_regiao = ate_meso_regiao;
    }
}
