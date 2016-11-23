package br.com.projeto.projetolistaartistas.database;

import android.provider.BaseColumns;

/**
 * Created by f.soares.de.farias on 11/21/2016.
 */

public interface AvaliacaoContract extends BaseColumns {

    String TABLE_NAME = "avaliacoes";

    String AVA_TITULO = "ava_titulo";
    String AVA_DESCRICAO = "ava_descricao";
    String AVA_NOTA = "ava_nota";
    String AVA_ATIVO = "ava_ativo";
    String USU_ID_ARTISTA = "usu_id_artista";
    String USU_ID = "usu_id";
}
