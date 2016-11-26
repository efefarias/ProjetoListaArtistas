package br.com.projeto.projetolistaartistas.Util;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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

        //Pegando a primeira pessoa
        for(i = 0; i < listPessoa.size(); i++){

            Double somaVotosP1 = 0.0;
            Double mediaP1 = 0.0;
            Double qtdVotosP1 = 0.0;

            //Somando as notas e verificando a média da primeira pessoa
            for(int k = 0; k < listPessoa.get(i).getAvaliacao().size(); k++){
                somaVotosP1 = somaVotosP1 + listPessoa.get(i).getAvaliacao().get(k).getAva_nota();
                qtdVotosP1 = qtdVotosP1 + 1;
            }

            if(somaVotosP1 != 0.0 && qtdVotosP1 != 0.0) {
                mediaP1 = somaVotosP1 / qtdVotosP1;
            }

            for(j = 0; j < listPessoa.size(); j++){

                Double somaVotosP2 = 0.0;
                Double mediaP2 = 0.0;
                Double qtdVotosP2 = 0.0;

                //Somando as notas e verificando a média da segunda pessoa
                for(int k = 0; k < listPessoa.get(j).getAvaliacao().size(); k++){
                    somaVotosP2 = somaVotosP2 + listPessoa.get(j).getAvaliacao().get(k).getAva_nota();
                    qtdVotosP2 = qtdVotosP2 + 1;
                }

                if(somaVotosP2 != 0.0 && qtdVotosP2 != 0.0) {
                    mediaP2 = somaVotosP2 / qtdVotosP2;
                }

            if(mediaP1 != mediaP2) {
                if (mediaP1 > mediaP2) {
                    listaAvaliados.add(listPessoa.get(i));
                } else if (mediaP2 > mediaP1) {
                    listaAvaliados.add(listPessoa.get(j));
                }
            }

            }
        }

        return listaAvaliados;
    }

    public List<Pessoa> removeDuplicados(List<Pessoa> listPessoa){

        List<Pessoa> listPessoaVerificar = listPessoa;

        Set<Pessoa> hs = new HashSet<>();
        hs.addAll(listPessoaVerificar);
        listPessoaVerificar.clear();
        listPessoaVerificar.addAll(hs);

        return listPessoaVerificar;
    }
}
