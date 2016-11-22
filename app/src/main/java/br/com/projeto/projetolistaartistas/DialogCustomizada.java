package br.com.projeto.projetolistaartistas;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Toast;

import com.google.gson.JsonObject;

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


public class DialogCustomizada extends DialogFragment {
    @Bind(R.id.btn_ok_avaliar)
    Button btnOk;
    @Bind(R.id.text_titulo)
    EditText txtTitulo;
    @Bind(R.id.text_avaliacao)
    EditText txtAvaliacao;
    @Bind(R.id.ratingbar_default)
    RatingBar ratingBar;
    RatePost ratePost;
    Long idArtista;
    int usu_id = 0;

    static DialogCustomizada newInstance(Long idArtista, int usu_id) {

        DialogCustomizada dialogCustomizada = new DialogCustomizada();


        Bundle args = new Bundle();
        args.putLong("num", idArtista);
        args.putInt("id", usu_id);
        dialogCustomizada.setArguments(args);

        return dialogCustomizada;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        idArtista = getArguments().getLong("num");
        usu_id = getArguments().getInt("id");
    }

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

        AvaliacaoDAO dao = new AvaliacaoDAO(getActivity());

        Avaliacao a = new Avaliacao();
        if (validarcampos()) {
            a.setAva_ativo("1");
            a.setAva_descricao(txtAvaliacao.getText().toString());
            a.setAva_titulo(txtTitulo.getText().toString());
            a.setAva_nota(ratingBar.getRating());
            a.setUsu_id_artista(idArtista);
            a.setUsu_id_artista(usu_id);

            dao.inserir(a);
            ratePost = new RatePost(a);
            if (ratePost.getStatus() != AsyncTask.Status.RUNNING) {
                ratePost.execute((Void) null);
            }
        } else {
            Toast.makeText(getActivity().getApplicationContext(), "Todos os Campos são obrigatorios", Toast.LENGTH_LONG).show();
        }

    }

    private boolean validarcampos() {
        if (txtAvaliacao.getText().toString().trim().equals("")) {
            return false;
        } else if (txtTitulo.getText().toString().trim().equals("")) {
            return false;
        } else return !txtTitulo.getText().toString().trim().equals("");
    }

    public class RatePost extends AsyncTask<Void, Void, Boolean> {
        Avaliacao avaliacao;

        RatePost(Avaliacao a) {
            this.avaliacao = a;
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            OkHttpClient client = new OkHttpClient();
            try {
                MediaType JSON = MediaType.parse("application/json; charset=utf-8");
                JsonObject json = new JsonObject();
                json.addProperty("ava_titulo", avaliacao.getAva_titulo());
                json.addProperty("ava_descricao", avaliacao.getAva_descricao());
                json.addProperty("ava_nota", avaliacao.getAva_nota());
                json.addProperty("ava_ativo", avaliacao.getAva_ativo());
                json.addProperty("usu_id_artista", avaliacao.getUsu_id_artista());
                json.addProperty("usu_id", avaliacao.getAva_id());
                RequestBody body = RequestBody.create(JSON, json.toString());
                Request post = new Request.Builder().url("http://www.doocati.com.br/tcc/webservice/avaliacoes/").method("POST", RequestBody.create(null, new byte[0])).post(body).build();
                Response responses = client.newCall(post).execute();
                Log.d("RESULTADO", responses.body().string());
                return true;
            } catch (Exception e) {
                Log.e("POST AVALIACAO", e.getMessage());
                return false;
            }
        }
    }
}