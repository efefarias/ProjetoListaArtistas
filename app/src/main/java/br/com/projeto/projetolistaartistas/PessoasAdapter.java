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

        float qtdVotos = 0;
        float somaVotos = 0;
        float mediaVotos = 0;

        ImageView imgCapa     = (ImageView)convertView.findViewById(R.id.img_capa);
        TextView txtNome      = (TextView)convertView.findViewById(R.id.txt_nome);
        TextView txtEmail     = (TextView)convertView.findViewById(R.id.txt_estado_cidade);
        TextView txtTelefone  = (TextView)convertView.findViewById(R.id.txt_bio);
        TextView txtResumo    = (TextView)convertView.findViewById(R.id.txt_resumo);
        TextView txtMediaNota = (TextView)convertView.findViewById(R.id.txt_media_votos);

        if(pessoa.getAvaliacoes() != null) {
            qtdVotos = pessoa.getAvaliacoes().size();

            for (int i = 0; i < qtdVotos; i++) {
                somaVotos = somaVotos + pessoa.getAvaliacoes().get(i).getNota();

                if (i == (qtdVotos - 1)) {
                    mediaVotos = somaVotos / qtdVotos;
                }
            }
        }


        txtNome.setText(pessoa.getNome_pessoa());
        txtEmail.setText(pessoa.getEmail_pessoa());
        txtTelefone.setText("("+pessoa.getDdd_pessoa()+") "+pessoa.getTelefone_pessoa());
        txtResumo.setText("      "+pessoa.getBio_pessoa());
        if(mediaVotos != 0) {
            txtMediaNota.setText(String.format("%.1f", mediaVotos) + "/10");
        }
        //img do artista
        Glide.with(getContext()).load(pessoa.getImg_pessoa()).into(imgCapa);


        return convertView;
    }
}
