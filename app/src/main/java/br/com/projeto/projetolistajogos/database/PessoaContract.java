package br.com.projeto.projetolistajogos.database;

import android.provider.BaseColumns;

/**
 * Created by Felipe on 11/05/2016.
 */
public interface PessoaContract extends BaseColumns{

    String TABLE_NAME = "pessoas";

    String NOME = "nome";
    String BIO = "bio";
    String CPF = "cpf";
    String SEXO = "sexo";
    String DDD = "ddd";
    String TELEFONE = "telefone";
    String FAVORITO = "favorito";
    String EMAIL = "email";
    String ID_ENDERECO = "id_endereco";
    String ID_TIPO_PESSOA = "id_tipo_pessoa";
    String IMAGEM = "imagem";
}
