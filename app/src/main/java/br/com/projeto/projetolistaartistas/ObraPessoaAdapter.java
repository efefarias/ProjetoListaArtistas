package br.com.projeto.projetolistaartistas;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import br.com.projeto.projetolistaartistas.model.Obra;

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

        ImageView imgObra = (ImageView) convertView.findViewById(R.id.imgObra);
        TextView txtDescricaoObra = (TextView) convertView.findViewById(R.id.txt_obra_descricao);
        TextView txtCategoriaObra = (TextView) convertView.findViewById(R.id.txt_obra_categoria);

        if(!obra.getImagens().get(0).equals(null)) {
            Picasso.with(getContext()).load(obra.getImagens().get(0).getImg_url()).resize(450, 450).into(imgObra);
        }

        if (obra.getObr_descricao().length() > 15) {
            String desc = obra.getObr_descricao().substring(0, 14);
            txtDescricaoObra.setText(desc + "...");
        } else {
            txtDescricaoObra.setText(obra.getObr_descricao());
        }
        txtCategoriaObra.setText(obra.getCat_obra_descricao());

        return convertView;
    }
}
