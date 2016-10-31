package br.com.projeto.projetolistaartistas;

import android.content.Context;
import android.graphics.Color;
import android.support.design.widget.FloatingActionButton;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.Calendar;
import java.util.List;

import br.com.projeto.projetolistaartistas.model.Pessoa;

/**
 * Created by Felipe on 04/05/2016.
 */
public class PessoaFavoritoAdapter extends ArrayAdapter<Pessoa> {


    public PessoaFavoritoAdapter(Context context, List<Pessoa> pessoas) {
        super(context, 0, pessoas);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        Pessoa pessoa = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(
                    R.layout.item_pessoa_favorito, null);
        }

        ImageView imgCapa               = (ImageView) convertView.findViewById(R.id.img_capa);
        TextView txtNome                = (TextView) convertView.findViewById(R.id.txt_nome_artista_favorito);

        txtNome.setText(pessoa.getUsu_nome());

        //img do artista
        Glide.with(getContext()).load(pessoa.getUsu_imagem()).into(imgCapa);

        return convertView;
    }
}
