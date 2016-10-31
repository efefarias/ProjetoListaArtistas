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
public class PessoasAdapter extends ArrayAdapter<Pessoa> {


    public PessoasAdapter(Context context, List<Pessoa> pessoas) {
        super(context, 0, pessoas);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        Pessoa pessoa = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(
                    R.layout.item_pessoa, null);
        }

        double qtdVotos = 0;
        double somaVotos = 0;
        double mediaVotos = 0;

        ImageView imgCapa               = (ImageView) convertView.findViewById(R.id.img_capa);
        TextView txtNome                = (TextView) convertView.findViewById(R.id.txt_nome);
        TextView txtEmail               = (TextView) convertView.findViewById(R.id.txt_estado_cidade);
        TextView txtTelefone            = (TextView) convertView.findViewById(R.id.txt_bio);
        TextView txtResumo              = (TextView) convertView.findViewById(R.id.txt_resumo);
        TextView txtMediaNota           = (TextView) convertView.findViewById(R.id.txt_media_votos);
        TextView txt_nota               = (TextView) convertView.findViewById(R.id.txt_nota);

        Calendar c = Calendar.getInstance();

        if (pessoa.getAvaliacao() != null) {
            qtdVotos = pessoa.getAvaliacao().size();

            for (int i = 0; i < qtdVotos; i++) {
                somaVotos = somaVotos + pessoa.getAvaliacao().get(i).getAva_nota();
                if (pessoa.getAvaliacao().get(i).getAva_nota() != 0) {
                    if (pessoa.getAvaliacao().get(i).getAva_nota() != 0)
                        //if (pessoa.getAvaliacao().get(i).getFlag_nota_usuario() == 'S' &&
                        //   (pessoa.getAvaliacoes().get(i).getDataVoto().before(c.getTime())))
                            txt_nota.setText(String.format("Sua nota: " + "%.1f", pessoa.getAvaliacao().get(i).getAva_nota()));
                }
                if (i == (qtdVotos - 1)) {
                    mediaVotos = somaVotos / qtdVotos;
                }
            }
        }


        txtNome.setText(pessoa.getUsu_nome());
        txtEmail.setText(pessoa.getUsu_email());
        txtTelefone.setText("(81) " + pessoa.getUsu_telefone());
        //txtResumo.setText("      " + pessoa.getBio_pessoa());
        txtResumo.setText("");
        if (mediaVotos != 0) {
            txtMediaNota.setText(String.format("%.1f", mediaVotos) + "/10");
        }

        //img do artista
        Glide.with(getContext()).load(pessoa.getUsu_imagem()).into(imgCapa);

        return convertView;
    }
}
