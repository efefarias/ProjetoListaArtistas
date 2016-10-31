package br.com.projeto.projetolistaartistas;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.squareup.picasso.Picasso;

import java.util.List;

import br.com.projeto.projetolistaartistas.model.Obra;
import br.com.projeto.projetolistaartistas.Util.FuncoesGenericas;

/**
 * Created by f.soares.de.farias on 9/22/2016.
 */
public class ObraPessoaAdapter extends ArrayAdapter<Obra> {

    public ObraPessoaAdapter(Context context, List<Obra> obras) {
        super(context, 0, obras);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        Obra obra = getItem(position);

        if(convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(
                    R.layout.item_obra, null);
        }

        ImageView imgObra = (ImageView)convertView.findViewById(R.id.imgObra);

        Picasso.with(getContext()).load(obra.getImg_url()).resize(450, 450).into(imgObra);

        //Glide.with(getContext()).load(obra.getImg_obra()).into(imgObra);
        //Glide.with(getContext()).load(imgObra).into(imgObra);

        return convertView;
    }


}
