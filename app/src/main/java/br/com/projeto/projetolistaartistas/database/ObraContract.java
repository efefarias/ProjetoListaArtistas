package br.com.projeto.projetolistaartistas.database;

import android.provider.BaseColumns;

/**
 * Created by Felipe on 11/05/2016.
 */
public interface ObraContract extends BaseColumns{

    String TABLE_NAME = "obras";

    String NOME = "nome_obra";
    String IMG_OBRA = "img_obra";

}
