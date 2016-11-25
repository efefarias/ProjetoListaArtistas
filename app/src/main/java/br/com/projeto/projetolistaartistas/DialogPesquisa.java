package br.com.projeto.projetolistaartistas;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.List;

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


public class DialogPesquisa extends DialogFragment {
    @Bind(R.id.btn_ok_pesquisa)
    Button btnOk;
    @Bind(R.id.spn_pesquisa)
    Spinner spnBusca;
    @Bind(R.id.edt_pesquisa)
    EditText edtPesquisa;

    static String EDIT_TEXT_BUNDLE_KEY = "1";
    static int REQUEST_CODE = 1;

    static DialogPesquisa newInstance() {

        DialogPesquisa dialogPesquisa = new DialogPesquisa();

        Bundle args = new Bundle();

        dialogPesquisa.setArguments(args);

        return dialogPesquisa;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.layout_dialog_pesquisa, container, false);

        ButterKnife.bind(this, v);

        List<String> spinnerArray =  new ArrayList<String>();
        spinnerArray.add("Selecione");
        spinnerArray.add("Nome");
        spinnerArray.add("Categoria");
        spinnerArray.add("Obra");
        spinnerArray.add("Avaliação");

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, spinnerArray);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spnBusca.setAdapter(adapter);

        return v;
    }

    @OnClick(R.id.btn_ok_pesquisa)
    public void EnviarDados(){
        sendResult(1);
    }

    private boolean validarcampos() {

        if(edtPesquisa.getText().toString().equals("")) {
            Toast.makeText(getActivity(), "Preencha algum campo para refinar a pesquisa.", Toast.LENGTH_SHORT).show();
            return false;
        }
        if(spnBusca.getSelectedItem().toString() == "Selecione") {
            Toast.makeText(getActivity(), "Preencha algum filtro para refinar a pesquisa.", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    public void sendResult(int REQUEST_CODE) {

        if(validarcampos()) {
            Intent intent = new Intent();
            //Passando a campo a ser pesquisado
            intent.putExtra("pesquisa", edtPesquisa.getText().toString());
            //Passando o filtro a ser feita a pesquisa
            intent.putExtra("filtro", spnBusca.getSelectedItem().toString());

            getTargetFragment().onActivityResult(getTargetRequestCode(), REQUEST_CODE, intent);
            getDialog().dismiss();
        }

    }
}