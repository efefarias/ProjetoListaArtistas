package br.com.projeto.projetolistaartistas.Util;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;

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

}
