package br.com.projeto.projetolistaartistas.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Felipe on 11/05/2016.
 */
public class PessoaDbHelper extends SQLiteOpenHelper {

    public static final int DB_VERSION = 1;
    public static final String DB_NAME = "pessoas_db";


    public PessoaDbHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL("CREATE TABLE "+ PessoaContract.TABLE_NAME +" (" +
                PessoaContract._ID              +" INTEGER PRIMARY KEY AUTOINCREMENT, " +
                PessoaContract.BIO              +" TEXT NOT NULL, " +
                PessoaContract.NOME             +" TEXT NOT NULL, " +
                PessoaContract.CPF              +" TEXT NOT NULL, " +
                PessoaContract.SEXO             +" TEXT NOT NULL, " +
                PessoaContract.DDD              +" TEXT NOT NULL, " +
                PessoaContract.TELEFONE         +" TEXT NOT NULL, " +
                PessoaContract.FAVORITO         +" TEXT NOT NULL, " +
                PessoaContract.EMAIL            +" TEXT NOT NULL, " +
                PessoaContract.ID_ENDERECO      +" TEXT NOT NULL, " +
                PessoaContract.ID_TIPO_PESSOA   +" TEXT NOT NULL, " +
                PessoaContract.IMAGEM           +" TEXT NOT NULL)");

        db.execSQL("CREATE TABLE "+ ObraContract.TABLE_NAME +" (" +
                ObraContract._ID                +" INTEGER PRIMARY KEY AUTOINCREMENT, " +
                ObraContract.NOME               +" TEXT NOT NULL, " +
                ObraContract.IMG_OBRA           +" TEXT NOT NULL)");

        String query = "CREATE TABLE "+ PessoaObraContract.TABLE_NAME +" (" +
                PessoaObraContract._ID                   +" INTEGER PRIMARY KEY AUTOINCREMENT, " +
                PessoaObraContract.ID_OBRA   + " INTEGER, " +
                PessoaObraContract.ID_PESSOA + " INTEGER, " +
                " FOREIGN KEY (" + PessoaObraContract.ID_OBRA + ") REFERENCES " + ObraContract.TABLE_NAME + " (" + ObraContract._ID +"), " +
                " FOREIGN KEY (" + PessoaObraContract.ID_PESSOA + ") REFERENCES " + PessoaContract.TABLE_NAME + " (" + PessoaContract._ID +"))";


        db.execSQL("CREATE TABLE "+ PessoaObraContract.TABLE_NAME +" (" +
                PessoaObraContract._ID                   +" INTEGER PRIMARY KEY AUTOINCREMENT, " +
                PessoaObraContract.ID_OBRA   + " INTEGER, " +
                PessoaObraContract.ID_PESSOA + " INTEGER, " +
                " FOREIGN KEY (" + PessoaObraContract.ID_OBRA + ") REFERENCES " + ObraContract.TABLE_NAME + " (" + ObraContract._ID +"), " +
                " FOREIGN KEY (" + PessoaObraContract.ID_PESSOA + ") REFERENCES " + PessoaContract.TABLE_NAME + " (" + PessoaContract._ID +"))");




                //PessoaObraContract.ID_OBRA                  +" TEXT NOT NULL, " +
                //PessoaObraContract.ID_PESSOA                +" TEXT NOT NULL");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
