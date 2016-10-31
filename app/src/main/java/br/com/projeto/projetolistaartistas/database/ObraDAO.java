package br.com.projeto.projetolistaartistas.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import br.com.projeto.projetolistaartistas.model.Obra;
import br.com.projeto.projetolistaartistas.model.Pessoa;
import br.com.projeto.projetolistaartistas.database.*;

/**
 * Created by Felipe on 15/09/2016.
 */
public class ObraDAO {

    private Context contexto;

    public ObraDAO(Context context){
        this.contexto = context;
    }

    public long inserir(Obra obra){

        PessoaDbHelper helper = new PessoaDbHelper(contexto);
        SQLiteDatabase db = helper.getWritableDatabase();

        ContentValues values = valuesFromObra(obra);

        long id = db.insert(ObraContract.TABLE_NAME, null, values);

        //obra.setId_obra(id);
        db.close();

        return id;
    }

    public List<Obra> listar(){

        PessoaDbHelper helper = new PessoaDbHelper(contexto);
        SQLiteDatabase db = helper.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM " + ObraContract.TABLE_NAME, null);

        List<Obra> obras = new ArrayList<>();

        if(cursor.getCount() > 0) {

            int indexId             = cursor.getColumnIndex(ObraContract._ID);
            int indexNome            = cursor.getColumnIndex(ObraContract.NOME);
            int indexImg           = cursor.getColumnIndex(ObraContract.IMG_OBRA);

            while (cursor.moveToNext()) {
                Obra obra = new Obra();

                //TODO
                //obra.setId_obra(cursor.getLong(indexId));
                //obra.setNome_obra(cursor.getString(indexNome));
                //obra.setImg_obra(cursor.getString(indexImg));

                obras.add(obra);
            }
        }

        cursor.close();

        db.close();

        return obras;
    }

    private ContentValues valuesFromObra(Obra obra){
        ContentValues values = new ContentValues();

        //TODO
        //values.put(ObraContract.NOME,             obra.getNome_obra());
        //values.put(ObraContract.IMG_OBRA, obra.getImg_obra());
        //values.put(ObraContract._ID,              obra.getId_obra());

        return values;
    }
}
