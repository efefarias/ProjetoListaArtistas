package br.com.projeto.projetolistaartistas.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import br.com.projeto.projetolistaartistas.model.Avaliacao;

/**
 * Created by f.soares.de.farias on 11/21/2016.
 */

public class AvaliacaoDAO {

    private Context contexto;

    public AvaliacaoDAO(Context context){
        this.contexto = context;
    }

    private ContentValues valuesFromAvaliacao(Avaliacao avaliacao){

        ContentValues values = new ContentValues();

        values.put(AvaliacaoContract.AVA_EMAIL,                 avaliacao.getAva_email());
        values.put(AvaliacaoContract.AVA_TITULO,                avaliacao.getAva_titulo());
        values.put(AvaliacaoContract.AVA_DESCRICAO,             avaliacao.getAva_descricao());
        values.put(AvaliacaoContract.AVA_NOME,                  avaliacao.getAva_nome());
        values.put(AvaliacaoContract.AVA_NOTA,                  avaliacao.getAva_nota());
        values.put(AvaliacaoContract.AVA_ATIVO,                 avaliacao.getAva_ativo());
        values.put(AvaliacaoContract.USU_ID_ARTISTA,            avaliacao.getUsu_id_artista());

        return values;
    }

    public long inserir(Avaliacao avaliacao){

        PessoaDbHelper helper = new PessoaDbHelper(contexto);
        SQLiteDatabase db = helper.getWritableDatabase();

        ContentValues values = valuesFromAvaliacao(avaliacao);

        long id = db.insert(AvaliacaoContract.TABLE_NAME, null, values);

        avaliacao.setAva_id(id);
        db.close();

        return id;
    }

    public List<Avaliacao> listar(){

        PessoaDbHelper helper = new PessoaDbHelper(contexto);
        SQLiteDatabase db = helper.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM " + AvaliacaoContract.TABLE_NAME, null);

        List<Avaliacao> avaliacoes = new ArrayList<>();

        if(cursor.getCount() > 0) {

            int indexId                = cursor.getColumnIndex(AvaliacaoContract._ID);
            int indexEmail             = cursor.getColumnIndex(AvaliacaoContract.AVA_EMAIL);
            int indexTitulo            = cursor.getColumnIndex(AvaliacaoContract.AVA_TITULO);
            int indexDescricao         = cursor.getColumnIndex(AvaliacaoContract.AVA_DESCRICAO);
            int indexNome              = cursor.getColumnIndex(AvaliacaoContract.AVA_NOME);
            int indexNota              = cursor.getColumnIndex(AvaliacaoContract.AVA_NOTA);
            int indexAtivo             = cursor.getColumnIndex(AvaliacaoContract.AVA_ATIVO);
            int indexIdArtistas        = cursor.getColumnIndex(AvaliacaoContract.USU_ID_ARTISTA);

            while (cursor.moveToNext()) {
                Avaliacao a = new Avaliacao();

                a.setAva_id(cursor.getLong(indexId));
                a.setAva_email(cursor.getString(indexEmail));
                a.setAva_titulo(cursor.getString(indexTitulo));
                a.setAva_descricao(cursor.getString(indexDescricao));
                a.setAva_nome(cursor.getString(indexNome));
                a.setAva_nota(cursor.getLong(indexNota));
                a.setAva_ativo(cursor.getString(indexAtivo));
                a.setUsu_id_artista(cursor.getString(indexIdArtistas));

                avaliacoes.add(a);
            }
        }

        cursor.close();

        db.close();

        return avaliacoes;
    }



}
