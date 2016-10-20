package br.com.projeto.projetolistaartistas.model;

import java.util.List;
import br.com.projeto.projetolistaartistas.model.Obra;

/**
 * Created by Felipe on 12/04/2016.
 */
@org.parceler.Parcel
public class Pessoa{

    private long id_pessoa;
    private String nome_pessoa;
    private String cpf_pessoa;
    private String sexo_pessoa;
    private String ddd_pessoa;
    private String telefone_pessoa;
    private String favorito_pessoa;
    private String email_pessoa;
    private String id_endereco_pessoa;
    private String id_tipo_pessoa;
    private String bio_pessoa;
    private String img_pessoa;
    private List<Obra> obras;
    private List<Avaliacao> avaliacoes;

    public Pessoa() {
    }

    public Pessoa(long id_pessoa, String nome_pessoa, String cpf_pessoa, String sexo_pessoa, String ddd_pessoa, String telefone_pessoa, String favorito_pessoa, String email_pessoa, String id_endereco_pessoa, String id_tipo_pessoa, String bio_pessoa, String img_pessoa, List<Obra> obras, List<Avaliacao> avaliacoes) {
        this.id_pessoa = id_pessoa;
        this.nome_pessoa = nome_pessoa;
        this.cpf_pessoa = cpf_pessoa;
        this.sexo_pessoa = sexo_pessoa;
        this.ddd_pessoa = ddd_pessoa;
        this.telefone_pessoa = telefone_pessoa;
        this.favorito_pessoa = favorito_pessoa;
        this.email_pessoa = email_pessoa;
        this.id_endereco_pessoa = id_endereco_pessoa;
        this.id_tipo_pessoa = id_tipo_pessoa;
        this.bio_pessoa = bio_pessoa;
        this.img_pessoa = img_pessoa;
        this.obras = obras;
        this.avaliacoes = avaliacoes;
    }

    public long getId_pessoa() {
        return id_pessoa;
    }

    public void setId_pessoa(long id_pessoa) {
        this.id_pessoa = id_pessoa;
    }

    public String getNome_pessoa() {
        return nome_pessoa;
    }

    public void setNome_pessoa(String nome_pessoa) {
        this.nome_pessoa = nome_pessoa;
    }

    public String getCpf_pessoa() {
        return cpf_pessoa;
    }

    public void setCpf_pessoa(String cpf_pessoa) {
        this.cpf_pessoa = cpf_pessoa;
    }

    public String getSexo_pessoa() {
        return sexo_pessoa;
    }

    public void setSexo_pessoa(String sexo_pessoa) {
        this.sexo_pessoa = sexo_pessoa;
    }

    public String getDdd_pessoa() {
        return ddd_pessoa;
    }

    public void setDdd_pessoa(String ddd_pessoa) {
        this.ddd_pessoa = ddd_pessoa;
    }

    public String getTelefone_pessoa() {
        return telefone_pessoa;
    }

    public void setTelefone_pessoa(String telefone_pessoa) {
        this.telefone_pessoa = telefone_pessoa;
    }

    public String getFavorito_pessoa() {
        return favorito_pessoa;
    }

    public void setFavorito_pessoa(String favorito_pessoa) {
        this.favorito_pessoa = favorito_pessoa;
    }

    public String getEmail_pessoa() {
        return email_pessoa;
    }

    public void setEmail_pessoa(String email_pessoa) {
        this.email_pessoa = email_pessoa;
    }

    public String getId_endereco_pessoa() {
        return id_endereco_pessoa;
    }

    public void setId_endereco_pessoa(String id_endereco_pessoa) {
        this.id_endereco_pessoa = id_endereco_pessoa;
    }

    public String getId_tipo_pessoa() {
        return id_tipo_pessoa;
    }

    public void setId_tipo_pessoa(String id_tipo_pessoa) {
        this.id_tipo_pessoa = id_tipo_pessoa;
    }

    public String getBio_pessoa() {
        return bio_pessoa;
    }

    public void setBio_pessoa(String bio_pessoa) {
        this.bio_pessoa = bio_pessoa;
    }

    public String getImg_pessoa() {
        return img_pessoa;
    }

    public void setImg_pessoa(String img_pessoa) {
        this.img_pessoa = img_pessoa;
    }

    public List<Obra> getObras() {
        return obras;
    }

    public void setObras(List<Obra> obras) {
        this.obras = obras;
    }

    public List<Avaliacao> getAvaliacoes() {
        return avaliacoes;
    }

    public void setAvaliacoes(List<Avaliacao> avaliacoes) {
        this.avaliacoes = avaliacoes;
    }
}
