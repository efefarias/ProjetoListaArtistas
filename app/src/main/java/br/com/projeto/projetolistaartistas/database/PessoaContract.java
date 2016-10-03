package br.com.projeto.projetolistaartistas.database;

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
    //
    String OBRA1 = "obra1";
    String OBRA2 = "obra2";
    String OBRA3 = "obra3";
    String OBRA4 = "obra4";
    String OBRA5 = "obra5";
    String OBRA6 = "obra6";
    String OBRA7 = "obra7";
    String OBRA8 = "obra8";
    String OBRA9 = "obra9";
}
