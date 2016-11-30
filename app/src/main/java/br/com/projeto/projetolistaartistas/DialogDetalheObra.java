package br.com.projeto.projetolistaartistas;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonObject;
import com.squareup.picasso.Picasso;

import br.com.projeto.projetolistaartistas.database.AvaliacaoDAO;
import br.com.projeto.projetolistaartistas.model.Avaliacao;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by f.soares.de.farias on 11/18/2016.
 */


public class DialogDetalheObra extends DialogFragment {
    @Bind(R.id.img_detalhe_obra)
    ImageView img_detalhe_obra;
    @Bind(R.id.txt_descricao_obra)
    TextView txtDescricaoObra;
    @Bind(R.id.txt_categoria_obra)
    TextView txtCategoriaObra;
    @Bind(R.id.btnOk_obra)
    Button btnOk;

    String url_img;
    String descricao_obra;
    String categoria_obra;


    static DialogDetalheObra newInstance(String url_img, String categoria_obra, String descricao_obra) {

        DialogDetalheObra dialogObra = new DialogDetalheObra();

        Bundle args = new Bundle();
        args.putString("url_img", url_img);
        args.putString("obra_desc", descricao_obra);
        args.putString("obra_cat", categoria_obra);

        dialogObra.setArguments(args);

        return dialogObra;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        url_img = getArguments().getString("url_img");
        descricao_obra = getArguments().getString("obra_desc");
        categoria_obra = getArguments().getString("obra_cat");

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.item_obra_full, container, false);

        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);

        ButterKnife.bind(this, v);

        Picasso.with(getContext()).load(url_img).resize(800, 1400).into(img_detalhe_obra);

        txtDescricaoObra.setText("Descrição da Obra: " + descricao_obra);
        txtCategoriaObra.setText("Categoria da Obra: " + categoria_obra);

        return v;
    }

    @OnClick(R.id.btnOk_obra)
    void onClick() {
        getDialog().dismiss();
    }
}