package br.com.projeto.projetolistaartistas;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.RatingBar;
import android.widget.TextView;

import java.util.List;

import br.com.projeto.projetolistaartistas.model.Avaliacao;

/**
 * Created by carli on 11/25/2016.
 */

public class AvaliacaoPessoaAdapter extends ArrayAdapter<Avaliacao> {
    public AvaliacaoPessoaAdapter(Context context, List<Avaliacao> avaliacaos) {
        super(context, 0, avaliacaos);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Avaliacao avaliacao = getItem(position);
        Viewholder viewholder;
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(
                    R.layout.item_avaliacao, null);
            viewholder = new Viewholder();
            viewholder.title = (TextView) convertView.findViewById(R.id.text_titulo);
            viewholder.descricao = (TextView) convertView.findViewById(R.id.text_avaliacao);
            viewholder.ratingBar = (RatingBar) convertView.findViewById(R.id.ratingbar_default);
            convertView.setTag(viewholder);
        } else {
            viewholder = (Viewholder) convertView.getTag();
        }
        String title = (avaliacao.getAva_titulo() != null) ? avaliacao.getAva_titulo() : "Rating";
        viewholder.title.setText(title);
        if (avaliacao.getAva_descricao().length() > 15) {
            String desc = avaliacao.getAva_descricao().substring(0, 14);
            viewholder.descricao.setText(desc + "...");
        } else {
            viewholder.descricao.setText(avaliacao.getAva_descricao());
        }
        viewholder.descricao.setText(avaliacao.getAva_descricao());
        viewholder.ratingBar.setRating((float) avaliacao.getAva_nota());
        return convertView;
    }

    private static class Viewholder {
        TextView title;
        TextView descricao;
        RatingBar ratingBar;

    }
}
