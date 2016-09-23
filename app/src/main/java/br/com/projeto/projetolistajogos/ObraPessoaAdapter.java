package br.com.projeto.projetolistajogos;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import br.com.projeto.projetolistajogos.model.Pessoa;

/**
 * Created by f.soares.de.farias on 9/22/2016.
 */
public class ObraPessoaAdapter extends ArrayAdapter<Pessoa> {

    public ObraPessoaAdapter(Context context, List<Pessoa> pessoas) {
        super(context, 0, pessoas);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        Pessoa pessoa = getItem(position);

        if(convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(
                    R.layout.item_obra, null);
        }

        ImageView imgObra = (ImageView)convertView.findViewById(R.id.imgObra);

        Glide.with(getContext()).load(pessoa.getImg_pessoa()).into(imgObra);

        return convertView;
    }


}
