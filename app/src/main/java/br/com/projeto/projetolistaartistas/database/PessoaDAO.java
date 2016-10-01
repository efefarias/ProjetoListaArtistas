package br.com.projeto.projetolistaartistas.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

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

        pessoa.setId_pessoa(id);
        db.close();

        return id;
    }

    public int atualizar(Pessoa pessoa){

        PessoaDbHelper helper = new PessoaDbHelper(contexto);
        SQLiteDatabase db = helper.getWritableDatabase();

        ContentValues values = valuesFromPessoa(pessoa);

        int rowsAffected = db.update(PessoaContract.TABLE_NAME, values,
                PessoaContract._ID + " = ?",
                new String[]{String.valueOf(pessoa.getId_pessoa())});

        db.close();

        return rowsAffected;
    }

    public int excluir(Pessoa pessoa){

        PessoaDbHelper helper = new PessoaDbHelper(contexto);
        SQLiteDatabase db = helper.getWritableDatabase();

        int rowsAffected = db.delete(PessoaContract.TABLE_NAME,
                PessoaContract.NOME + " = ?",
                new String[]{pessoa.getNome_pessoa()});

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
            int indexBio            = cursor.getColumnIndex(PessoaContract.BIO);
            int indexNome           = cursor.getColumnIndex(PessoaContract.NOME);
            int indexCpf            = cursor.getColumnIndex(PessoaContract.CPF);
            int indexSexo           = cursor.getColumnIndex(PessoaContract.SEXO);
            int indexDDD            = cursor.getColumnIndex(PessoaContract.DDD);
            int indexTelefone       = cursor.getColumnIndex(PessoaContract.TELEFONE);
            int indexFavorito       = cursor.getColumnIndex(PessoaContract.FAVORITO);
            int indexEmail          = cursor.getColumnIndex(PessoaContract.EMAIL);
            int indexId_Endereco    = cursor.getColumnIndex(PessoaContract.ID_ENDERECO);
            int indexId_tipo_pessoa = cursor.getColumnIndex(PessoaContract.ID_TIPO_PESSOA);
            int indexImagem         = cursor.getColumnIndex(PessoaContract.IMAGEM);


            while (cursor.moveToNext()) {
                Pessoa pessoa = new Pessoa();

                pessoa.setId_pessoa(cursor.getLong(indexId));     //setId(cursor.getLong(indexId));
                pessoa.setBio_pessoa(cursor.getString(indexBio));     //setId(cursor.getLong(indexId));
                pessoa.setNome_pessoa(cursor.getString(indexNome));
                pessoa.setCpf_pessoa(cursor.getString(indexCpf));
                pessoa.setSexo_pessoa(cursor.getString(indexSexo));
                pessoa.setDdd_pessoa(cursor.getString(indexDDD));
                pessoa.setTelefone_pessoa(cursor.getString(indexTelefone));
                pessoa.setFavorito_pessoa(cursor.getString(indexFavorito));
                pessoa.setEmail_pessoa(cursor.getString(indexEmail));
                pessoa.setId_endereco_pessoa(cursor.getString(indexId_Endereco));
                pessoa.setId_tipo_pessoa(cursor.getString(indexId_tipo_pessoa));
                pessoa.setImg_pessoa(cursor.getString(indexImagem));

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

        String query = "SELECT * FROM " + PessoaContract.TABLE_NAME + " WHERE " + PessoaContract.NOME + " = '" + nomeArtista + "'";

        Cursor cursor = db.rawQuery(query, null);

        List<Pessoa> pessoas = new ArrayList<>();

        if(cursor.getCount() > 0) {

            int indexId             = cursor.getColumnIndex(PessoaContract._ID);
            int indexBio            = cursor.getColumnIndex(PessoaContract.BIO);
            int indexNome           = cursor.getColumnIndex(PessoaContract.NOME);
            int indexCpf            = cursor.getColumnIndex(PessoaContract.CPF);
            int indexSexo           = cursor.getColumnIndex(PessoaContract.SEXO);
            int indexDDD            = cursor.getColumnIndex(PessoaContract.DDD);
            int indexTelefone       = cursor.getColumnIndex(PessoaContract.TELEFONE);
            int indexFavorito       = cursor.getColumnIndex(PessoaContract.FAVORITO);
            int indexEmail          = cursor.getColumnIndex(PessoaContract.EMAIL);
            int indexId_Endereco    = cursor.getColumnIndex(PessoaContract.ID_ENDERECO);
            int indexId_tipo_pessoa = cursor.getColumnIndex(PessoaContract.ID_TIPO_PESSOA);
            int indexImagem         = cursor.getColumnIndex(PessoaContract.IMAGEM);


            while (cursor.moveToNext()) {
                Pessoa pessoa = new Pessoa();

                pessoa.setId_pessoa(cursor.getLong(indexId));     //setId(cursor.getLong(indexId));
                pessoa.setBio_pessoa(cursor.getString(indexBio));     //setId(cursor.getLong(indexId));
                pessoa.setNome_pessoa(cursor.getString(indexNome));
                pessoa.setCpf_pessoa(cursor.getString(indexCpf));
                pessoa.setSexo_pessoa(cursor.getString(indexSexo));
                pessoa.setDdd_pessoa(cursor.getString(indexDDD));
                pessoa.setTelefone_pessoa(cursor.getString(indexTelefone));
                pessoa.setFavorito_pessoa(cursor.getString(indexFavorito));
                pessoa.setEmail_pessoa(cursor.getString(indexEmail));
                pessoa.setId_endereco_pessoa(cursor.getString(indexId_Endereco));
                pessoa.setId_tipo_pessoa(cursor.getString(indexId_tipo_pessoa));
                pessoa.setImg_pessoa(cursor.getString(indexImagem));

                pessoas.add(pessoa);
            }
        }

        cursor.close();

        db.close();

        return pessoas;
    }

    private ContentValues valuesFromPessoa(Pessoa pessoa){
        ContentValues values = new ContentValues();

        //values.put(JogoContract.NOME,             jogo.getNome());
        values.put(PessoaContract.NOME,             pessoa.getNome_pessoa());
        values.put(PessoaContract.BIO,              pessoa.getBio_pessoa());
        values.put(PessoaContract.CPF,              pessoa.getCpf_pessoa());
        values.put(PessoaContract.SEXO,             pessoa.getSexo_pessoa());
        values.put(PessoaContract.DDD,              pessoa.getDdd_pessoa());
        values.put(PessoaContract.TELEFONE,         pessoa.getTelefone_pessoa());
        values.put(PessoaContract.FAVORITO,         pessoa.getFavorito_pessoa());
        values.put(PessoaContract.EMAIL,            pessoa.getEmail_pessoa());
        values.put(PessoaContract.ID_ENDERECO,      pessoa.getId_endereco_pessoa());
        values.put(PessoaContract.ID_TIPO_PESSOA,   pessoa.getId_tipo_pessoa());
        values.put(PessoaContract.IMAGEM,           pessoa.getImg_pessoa());

        return values;
    }

    public Boolean isfavorito(Pessoa pessoa){
        PessoaDbHelper helper = new PessoaDbHelper(contexto);
        SQLiteDatabase db = helper.getReadableDatabase();

        //FASF - Ao invés de trazer tudo pode ser retornado apenas o ID, para um leve ganho de performance
        Cursor cursor = db.rawQuery("SELECT * FROM " + PessoaContract.TABLE_NAME
                + " WHERE "
                + PessoaContract.NOME
                + " = ?", new String[]{pessoa.getNome_pessoa()});

        boolean existe = false;
        if (cursor != null) {
            existe = cursor.getCount() > 0;
            cursor.close();
        }
        db.close();
        return existe;
    }

}
