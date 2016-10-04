package br.com.projeto.projetolistaartistas;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import br.com.projeto.projetolistaartistas.model.Pessoa;

/**
 * Created by Felipe on 04/05/2016.
 */
public class PessoasAdapter extends ArrayAdapter<Pessoa> {


    public PessoasAdapter(Context context, List<Pessoa> pessoas) {
        super(context, 0, pessoas);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        Pessoa pessoa = getItem(position);

        if(convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(
                    R.layout.item_pessoa, null);
        }

        ImageView imgCapa    = (ImageView)convertView.findViewById(R.id.img_capa);
        TextView txtNome     = (TextView)convertView.findViewById(R.id.txt_nome);
        TextView txtEmail    = (TextView)convertView.findViewById(R.id.txt_estado_cidade);
        TextView txtTelefone = (TextView)convertView.findViewById(R.id.txt_bio);
        TextView txtResumo   = (TextView)convertView.findViewById(R.id.txt_resumo);

        txtNome.setText(pessoa.getNome_pessoa());
        txtEmail.setText(pessoa.getEmail_pessoa());
        txtTelefone.setText("("+pessoa.getDdd_pessoa()+") "+pessoa.getTelefone_pessoa());
        txtResumo.setText("      "+pessoa.getBio_pessoa());
        Glide.with(getContext()).load(pessoa.getImg_pessoa()).into(imgCapa);

        return convertView;
    }
}
