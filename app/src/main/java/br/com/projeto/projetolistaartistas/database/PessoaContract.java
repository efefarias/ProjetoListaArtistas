package br.com.projeto.projetolistaartistas.database;

import android.provider.BaseColumns;

/**
 * Created by Felipe on 11/05/2016.
 */
public interface PessoaContract extends BaseColumns{

    String TABLE_NAME = "pessoas";

    String USU_EMAIL = "usu_email";
    String USU_CPF = "usu_cpf";
    String USU_GENERO = "usu_genero";
    String USU_NOME = "usu_nome";
    String USU_DATA_NASCIMENTO = "usu_data_nascimento";
    String USU_IMAGEM = "usu_imagem";
    String USU_TELEFONE = "usu_telefone";
    String USU_CELULAR = "usu_celular";
    String USU_DESCRICAO = "usu_descricao";

}
