package br.com.projeto.projetolistaartistas;

import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.*;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import br.com.projeto.projetolistaartistas.database.AvaliacaoDAO;
import br.com.projeto.projetolistaartistas.model.Avaliacao;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by f.soares.de.farias on 11/18/2016.
 */


public class DialogCustomizada extends DialogFragment {
    static DialogCustomizada newInstance() {
        return new DialogCustomizada();
    }

    @Bind(R.id.btn_ok_avaliar)
    Button btnOk;
    @Bind(R.id.text_titulo)
    EditText txtTitulo;
    @Bind(R.id.text_avaliacao)
    EditText txtAvaliacao;
    @Bind(R.id.ratingbar_default)
    RatingBar ratingBar;

    //AQUI
    AvaliacaoDAO dao = new AvaliacaoDAO(getContext());

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.layout_dialog_customizada, container, false);

        ButterKnife.bind(this, v);

        txtAvaliacao.setHint("Preencha um comentário");
        txtTitulo.setHint("Título");

        return v;
    }

    @OnClick(R.id.btn_ok_avaliar)
    public void Avaliar(){

        Avaliacao a = new Avaliacao();

        a.setAva_ativo("1");
        a.setAva_descricao(txtAvaliacao.getText().toString());
        a.setAva_titulo(txtTitulo.getText().toString());
        a.setAva_nota(ratingBar.getRating());

        dao.inserir(a);

        List<Avaliacao> avaliacoes = dao.listar();

        Toast.makeText(getActivity(), avaliacoes.get(0).getAva_titulo().toString() , Toast.LENGTH_SHORT).show();
    }
}