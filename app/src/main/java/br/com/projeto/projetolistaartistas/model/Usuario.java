package br.com.projeto.projetolistaartistas.model;

/**
 * Created by carli on 11/14/2016.
 */

public class Usuario {
    private String usu_nome = "";
    private String usu_email = "";
    private String usu_imagem = "";
    private String usu_id_google = "";

    public String getUsu_nome() {
        return usu_nome;
    }

    public void setUsu_nome(String usu_nome) {
        this.usu_nome = usu_nome;
    }

    public String getUsu_email() {
        return usu_email;
    }

    public void setUsu_email(String usu_email) {
        this.usu_email = usu_email;
    }

    public String getUsu_imagem() {
        return usu_imagem;
    }

    public void setUsu_imagem(String usu_imagem) {
        this.usu_imagem = usu_imagem;
    }

    public String getUsu_id_google() {
        return usu_id_google;
    }

    public void setUsu_id_google(String usu_id_google) {
        this.usu_id_google = usu_id_google;
    }
}
