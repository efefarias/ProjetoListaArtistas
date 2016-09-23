package br.com.projeto.projetolistajogos.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Felipe on 18/04/2016.
 */

public class ListPessoas {

    private List<Pessoa> pessoas;

    public List<Pessoa> getPessoas() {
        return pessoas;
    }

    public void setPessoas(List<Pessoa> pessoas) {
        this.pessoas = pessoas;
    }
}
