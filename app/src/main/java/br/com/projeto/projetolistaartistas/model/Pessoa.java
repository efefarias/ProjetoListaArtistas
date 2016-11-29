package br.com.projeto.projetolistaartistas.model;

import java.util.List;
import br.com.projeto.projetolistaartistas.model.Obra;

/**
 * Created by Felipe on 12/04/2016.
 */
@org.parceler.Parcel
public class Pessoa{

    private long usu_id;
    private String usu_email;
    private String usu_cpf;
    private String usu_genero;
    private String usu_nome;
    private String usu_data_nascimento;
    private String usu_imagem;
    private String usu_celular;
    private String usu_telefone;
    private String usu_descricao;
    private List<Obra> obra;
    private List<Avaliacao> avaliacao;
    private List<Atelie> atelie;

    public Pessoa(){
    }

    public Pessoa(long usu_id, String usu_email, String usu_cpf, String usu_genero, String usu_nome, String usu_data_nascimento, String usu_imagem, String usu_celular, String usu_telefone, String usu_descricao,List<Obra> obra, List<Avaliacao> avaliacao, List<Atelie> atelie) {
        this.usu_id = usu_id;
        this.usu_email = usu_email;
        this.usu_cpf = usu_cpf;
        this.usu_genero = usu_genero;
        this.usu_nome = usu_nome;
        this.usu_data_nascimento = usu_data_nascimento;
        this.usu_imagem = usu_imagem;
        this.usu_celular = usu_celular;
        this.usu_telefone = usu_telefone;
        this.usu_descricao = usu_descricao;
        this.obra = obra;
        this.avaliacao = avaliacao;
        this.atelie = atelie;
    }

    public long getUsu_id() {
        return usu_id;
    }

    public void setUsu_id(long usu_id) {
        this.usu_id = usu_id;
    }

    public String getUsu_email() {
        return usu_email;
    }

    public void setUsu_email(String usu_email) {
        this.usu_email = usu_email;
    }

    public String getUsu_cpf() {
        return usu_cpf;
    }

    public void setUsu_cpf(String usu_cpf) {
        this.usu_cpf = usu_cpf;
    }

    public String getUsu_genero() {
        return usu_genero;
    }

    public void setUsu_genero(String usu_genero) {
        this.usu_genero = usu_genero;
    }

    public String getUsu_nome() {
        return usu_nome;
    }

    public void setUsu_nome(String usu_nome) {
        this.usu_nome = usu_nome;
    }

    public String getUsu_data_nascimento() {
        return usu_data_nascimento;
    }

    public void setUsu_data_nascimento(String usu_data_nascimento) {
        this.usu_data_nascimento = usu_data_nascimento;
    }

    public String getUsu_imagem() {
        return usu_imagem;
    }

    public void setUsu_imagem(String usu_imagem) {
        this.usu_imagem = usu_imagem;
    }

    public String getUsu_celular() {
        return usu_celular;
    }

    public void setUsu_celular(String usu_celular) {
        this.usu_celular = usu_celular;
    }

    public String getUsu_telefone() {
        return usu_telefone;
    }

    public void setUsu_telefone(String usu_telefone) {
        this.usu_telefone = usu_telefone;
    }

    public List<Obra> getObra() {
        return obra;
    }

    public void setObra(List<Obra> obra) {
        this.obra = obra;
    }

    public List<Avaliacao> getAvaliacao() {
        return avaliacao;
    }

    public void setAvaliacao(List<Avaliacao> avaliacao) {
        this.avaliacao = avaliacao;
    }

    public List<Atelie> getAtelie() {
        return atelie;
    }

    public void setAtelie(List<Atelie> atelie) {
        this.atelie = atelie;
    }

    public String getUsu_desc() {
        return usu_descricao;
    }

    public void setUsu_desc(String usu_desc) {
        this.usu_descricao = usu_desc;
    }
}
