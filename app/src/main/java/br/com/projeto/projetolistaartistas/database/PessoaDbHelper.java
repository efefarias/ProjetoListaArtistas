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
                PessoaContract._ID                      +" INTEGER PRIMARY KEY AUTOINCREMENT, " +
                PessoaContract.USU_EMAIL                +" TEXT NOT NULL, " +
                PessoaContract.USU_CPF                  +" TEXT NOT NULL, " +
                PessoaContract.USU_GENERO               +" TEXT NOT NULL, " +
                PessoaContract.USU_NOME                 +" TEXT NOT NULL, " +
                PessoaContract.USU_DATA_NASCIMENTO      +" TEXT NOT NULL, " +
                PessoaContract.USU_IMAGEM               +" TEXT NOT NULL, " +
                PessoaContract.USU_TELEFONE             +" TEXT NOT NULL, " +
                PessoaContract.USU_CELULAR              +" TEXT NOT NULL)");

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






    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
