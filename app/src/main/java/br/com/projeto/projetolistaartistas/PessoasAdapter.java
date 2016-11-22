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

import br.com.projeto.projetolistaartistas.database.AvaliacaoDAO;
import br.com.projeto.projetolistaartistas.model.Avaliacao;
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

        AvaliacaoDAO dao = new AvaliacaoDAO(getContext());
        Pessoa pessoa = getItem(position);
        ViewHolder viewHolder;
        double qtdVotos = 0;
        double somaVotos = 0;
        double mediaVotos = 0;
        List<Avaliacao> listaAvaliacoes = dao.listar();

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(
                    R.layout.item_pessoa, null);
            viewHolder = new ViewHolder();
            viewHolder.imgCapa = (ImageView) convertView.findViewById(R.id.img_capa);
            viewHolder.txtNome = (TextView) convertView.findViewById(R.id.txt_nome);
            viewHolder.txtEmail = (TextView) convertView.findViewById(R.id.txt_estado_cidade);
            viewHolder.txtTelefone = (TextView) convertView.findViewById(R.id.txt_bio);
            viewHolder.txtResumo = (TextView) convertView.findViewById(R.id.txt_resumo);
            viewHolder.txtMediaNota = (TextView) convertView.findViewById(R.id.txt_media_votos);
            viewHolder.txt_nota = (TextView) convertView.findViewById(R.id.txt_nota);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        /*if (pessoa.getAvaliacao() != null) {
            qtdVotos = pessoa.getAvaliacao().size();

            for (int i = 0; i < qtdVotos; i++) {
                somaVotos = somaVotos + pessoa.getAvaliacao().get(i).getAva_nota();
                if (pessoa.getAvaliacao().get(i).getAva_nota() != 0) {
                    if (pessoa.getAvaliacao().get(i).getAva_nota() != 0)
                        //if (pessoa.getAvaliacao().get(i).getAva_id() == "33")
                            txt_nota.setText(String.format("Sua nota: " + "%.1f", pessoa.getAvaliacao().get(i).getAva_nota()));
                }
                if (i == (qtdVotos - 1)) {
                    mediaVotos = somaVotos / qtdVotos;
                }
            }
        }*/
        //Aplica a nota do artista
        for (int i = 0; i < listaAvaliacoes.size(); i++) {
            if (listaAvaliacoes.get(i).getUsu_id_artista() == pessoa.getUsu_id()) {
                if(listaAvaliacoes.size() != 0) {
                    viewHolder.txt_nota.setText(String.format("Sua nota: " + "%.1f", listaAvaliacoes.get(i).getAva_nota()));
                    somaVotos = somaVotos + listaAvaliacoes.get(i).getAva_nota();
                    qtdVotos = qtdVotos + 1;
                }
            }
        }
        viewHolder.txtNome.setText(pessoa.getUsu_nome());
        if (pessoa.getAvaliacao().size() != 0) {
            viewHolder.txtNome.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_avaliado, 0);
        }

        viewHolder.txtEmail.setText(pessoa.getUsu_email());
        viewHolder.txtTelefone.setText(pessoa.getUsu_telefone());
        viewHolder.txtResumo.setText("");

        if (mediaVotos != 0) {
            viewHolder.txtMediaNota.setText(String.format("%.1f", mediaVotos) + "/10");
        }

        //img do artista
        Glide.with(getContext()).load(pessoa.getUsu_imagem()).into(viewHolder.imgCapa);

        return convertView;
    }

    private static class ViewHolder {
        ImageView imgCapa;
        TextView txtNome;
        TextView txtEmail;
        TextView txtTelefone;
        TextView txtResumo;
        TextView txtMediaNota;
        TextView txt_nota;
    }
}
