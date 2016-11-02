package br.com.projeto.projetolistaartistas.Util;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.List;

import br.com.projeto.projetolistaartistas.model.Pessoa;

/**
 * Created by f.soares.de.farias on 10/2/2016.
 */
public class FuncoesGenericas {

    public boolean verificarNome(String nomeArtista, String nomeFiltro){

        boolean contemNome = false;

        if(nomeArtista.contains(nomeFiltro))
            contemNome = true;

            return contemNome;
    }

    public String formataJson(String json){

        for(int i = 0; i < json.length(); i++){
            if(json.substring(0, json.length()).contains("\"" + i + "\":")){
                json = json.replace("\"" + i + "\":", "");
            }
        }

        return json;
    }

    public List<Pessoa> maisAvaliados(List<Pessoa> listPessoa){

        int i = 0;
        int j = 0;

        List<Pessoa> listaAvaliados = new ArrayList<Pessoa>();

        //TODO
        //for(i = 0; i < listPessoa.size(); i++){
        //    for(j = 0; j < listPessoa.size(); j++){
        //        if(listPessoa.get(i).getAvaliacao().get(i) != null
        //                && !listPessoa.get(j).getAvaliacao().get(j).equals(null))
        //            if((Integer) listPessoa.get(i).getAvaliacao().get(i).getAva_nota() >  (Integer) listPessoa.get(j).getAvaliacao().get(j).getAva_nota()){
        //                listaAvaliados.add(listPessoa.get(i));
        //            }
        //    }
        //}

        return listaAvaliados;

    }
}
