package br.com.projeto.projetolistaartistas.database;

import android.provider.BaseColumns;

/**
 * Created by Felipe on 11/05/2016.
 */
public interface PessoaObraContract extends BaseColumns{

    String TABLE_NAME = "pessoa_obras";

    String ID_PESSOA = "id_pessoa";
    String ID_OBRA = "id_obra";

}
