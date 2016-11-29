package br.com.projeto.projetolistaartistas.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import br.com.projeto.projetolistaartistas.model.Obra;
import br.com.projeto.projetolistaartistas.model.Pessoa;

/**
 * Created by Felipe on 15/09/2016.
 */
public class PessoaDAO {

    private Context contexto;

    public PessoaDAO(Context context){
        this.contexto = context;
    }

    public long inserir(Pessoa pessoa){

        PessoaDbHelper helper = new PessoaDbHelper(contexto);
        SQLiteDatabase db = helper.getWritableDatabase();

        ContentValues values = valuesFromPessoa(pessoa);

        long id = db.insert(PessoaContract.TABLE_NAME, null, values);

        pessoa.setUsu_id(id);
        db.close();

        return id;
    }

    public int atualizar(Pessoa pessoa){

        PessoaDbHelper helper = new PessoaDbHelper(contexto);
        SQLiteDatabase db = helper.getWritableDatabase();

        ContentValues values = valuesFromPessoa(pessoa);

        int rowsAffected = db.update(PessoaContract.TABLE_NAME, values,
                PessoaContract._ID + " = ?",
                new String[]{String.valueOf(pessoa.getUsu_id())});

        db.close();

        return rowsAffected;
    }

    public int excluir(Pessoa pessoa){

        PessoaDbHelper helper = new PessoaDbHelper(contexto);
        SQLiteDatabase db = helper.getWritableDatabase();

        int rowsAffected = db.delete(PessoaContract.TABLE_NAME,
                PessoaContract.USU_NOME + " = ?",
                new String[]{pessoa.getUsu_nome()});

        db.close();

        return rowsAffected;
    }

    public List<Pessoa> listar(){

        PessoaDbHelper helper = new PessoaDbHelper(contexto);
        SQLiteDatabase db = helper.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM " + PessoaContract.TABLE_NAME, null);

        List<Pessoa> pessoas = new ArrayList<>();

        if(cursor.getCount() > 0) {

            int indexId             = cursor.getColumnIndex(PessoaContract._ID);
            int indexEmail           = cursor.getColumnIndex(PessoaContract.USU_EMAIL);
            int indexCPF           = cursor.getColumnIndex(PessoaContract.USU_CPF);
            int indexGenero           = cursor.getColumnIndex(PessoaContract.USU_GENERO);
            int indexNome           = cursor.getColumnIndex(PessoaContract.USU_NOME);
            int indexDataNascimento           = cursor.getColumnIndex(PessoaContract.USU_DATA_NASCIMENTO);
            int indexImagem           = cursor.getColumnIndex(PessoaContract.USU_IMAGEM);
            int indexTelefone           = cursor.getColumnIndex(PessoaContract.USU_TELEFONE);
            int indexCelular           = cursor.getColumnIndex(PessoaContract.USU_CELULAR);
            int indexDescricao           = cursor.getColumnIndex(PessoaContract.USU_DESCRICAO);


            while (cursor.moveToNext()) {
                Pessoa pessoa = new Pessoa();

                pessoa.setUsu_id(cursor.getLong(indexId));
                pessoa.setUsu_email(cursor.getString(indexEmail));
                pessoa.setUsu_cpf(cursor.getString(indexCPF));
                pessoa.setUsu_genero(cursor.getString(indexGenero));
                pessoa.setUsu_nome(cursor.getString(indexNome));
                pessoa.setUsu_data_nascimento(cursor.getString(indexDataNascimento));
                pessoa.setUsu_imagem(cursor.getString(indexImagem));
                pessoa.setUsu_telefone(cursor.getString(indexTelefone));
                pessoa.setUsu_celular(cursor.getString(indexCelular));
                pessoa.setUsu_desc(cursor.getString(indexDescricao));

                pessoas.add(pessoa);
            }
        }

        cursor.close();

        db.close();

        return pessoas;
    }

    public List<Pessoa> listarComFiltro(String nomeArtista){

        PessoaDbHelper helper = new PessoaDbHelper(contexto);
        SQLiteDatabase db = helper.getReadableDatabase();

        //Cursor cursor = db.rawQuery("SELECT * FROM " + PessoaContract.TABLE_NAME, null );

        //Cursor cursor = db.rawQuery("SELECT * FROM pessoas WHERE nome=?", new String[] { nomeArtista + "%" });

        String query = "SELECT * FROM " + PessoaContract.TABLE_NAME + " WHERE " + PessoaContract.USU_NOME + " = '" + nomeArtista + "'";

        Cursor cursor = db.rawQuery(query, null);

        List<Pessoa> pessoas = new ArrayList<>();

        if(cursor.getCount() > 0) {

            int indexId             = cursor.getColumnIndex(PessoaContract._ID);
            int indexEmail           = cursor.getColumnIndex(PessoaContract.USU_EMAIL);
            int indexCPF           = cursor.getColumnIndex(PessoaContract.USU_CPF);
            int indexGenero           = cursor.getColumnIndex(PessoaContract.USU_GENERO);
            int indexNome           = cursor.getColumnIndex(PessoaContract.USU_NOME);
            int indexDataNascimento           = cursor.getColumnIndex(PessoaContract.USU_DATA_NASCIMENTO);
            int indexImagem           = cursor.getColumnIndex(PessoaContract.USU_IMAGEM);
            int indexTelefone           = cursor.getColumnIndex(PessoaContract.USU_TELEFONE);
            int indexCelular           = cursor.getColumnIndex(PessoaContract.USU_CELULAR);
            int indexDescricao           = cursor.getColumnIndex(PessoaContract.USU_DESCRICAO);

            while (cursor.moveToNext()) {
                Pessoa pessoa = new Pessoa();

                pessoa.setUsu_id(cursor.getLong(indexId));     //setId(cursor.getLong(indexId));
                pessoa.setUsu_email(cursor.getString(indexEmail));     //setId(cursor.getLong(indexId));
                pessoa.setUsu_cpf(cursor.getString(indexCPF));
                pessoa.setUsu_genero(cursor.getString(indexGenero));
                pessoa.setUsu_nome(cursor.getString(indexNome));
                pessoa.setUsu_data_nascimento(cursor.getString(indexDataNascimento));
                pessoa.setUsu_imagem(cursor.getString(indexImagem));
                pessoa.setUsu_telefone(cursor.getString(indexTelefone));
                pessoa.setUsu_celular(cursor.getString(indexCelular));
                pessoa.setUsu_desc(cursor.getString(indexDescricao));

                pessoas.add(pessoa);
            }
        }

        cursor.close();

        db.close();

        return pessoas;
    }

    private ContentValues valuesFromPessoa(Pessoa pessoa){
        ContentValues values = new ContentValues();

        values.put(PessoaContract.USU_EMAIL,             pessoa.getUsu_email());
        values.put(PessoaContract.USU_CPF,              pessoa.getUsu_cpf());
        values.put(PessoaContract.USU_GENERO,              pessoa.getUsu_genero());
        values.put(PessoaContract.USU_NOME,             pessoa.getUsu_nome());
        values.put(PessoaContract.USU_DATA_NASCIMENTO,              pessoa.getUsu_data_nascimento());
        values.put(PessoaContract.USU_IMAGEM,         pessoa.getUsu_imagem());
        values.put(PessoaContract.USU_TELEFONE,         pessoa.getUsu_telefone());
        values.put(PessoaContract.USU_CELULAR,            pessoa.getUsu_celular());
        values.put(PessoaContract.USU_DESCRICAO,            pessoa.getUsu_desc());

        return values;
    }

    public Boolean isfavorito(Pessoa pessoa){
        PessoaDbHelper helper = new PessoaDbHelper(contexto);
        SQLiteDatabase db = helper.getReadableDatabase();

        //FASF - Ao invés de trazer tudo pode ser retornado apenas o ID, para um leve ganho de performance
        Cursor cursor = db.rawQuery("SELECT * FROM " + PessoaContract.TABLE_NAME
                + " WHERE "
                + PessoaContract.USU_NOME
                + " = ?", new String[]{pessoa.getUsu_nome()});

        boolean existe = false;
        if (cursor != null) {
            existe = cursor.getCount() > 0;
            cursor.close();
        }
        db.close();
        return existe;
    }

}
