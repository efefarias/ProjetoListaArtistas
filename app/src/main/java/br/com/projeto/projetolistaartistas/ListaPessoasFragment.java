package br.com.projeto.projetolistaartistas;


import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import br.com.projeto.projetolistaartistas.database.PessoaDAO;
import br.com.projeto.projetolistaartistas.model.ListPessoas;
import br.com.projeto.projetolistaartistas.model.Pessoa;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnItemClick;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


public class ListaPessoasFragment extends Fragment {

    @Bind(R.id.list_pessoas)
    ListView mListView;
    @Bind(R.id.swipe_pessoas)
    SwipeRefreshLayout swipePessoas;
    @Bind(R.id.btn_busca_artista)
    Button btnBuscaArtista;
    @Bind(R.id.edt_nome_artista)
    EditText edtNomeArtista;

    List<Pessoa> listPessoas;
    List<Pessoa> listPessoasFiltro;
    ArrayAdapter<Pessoa> adapterPessoas;
    ArrayAdapter<Pessoa> adapterPessoasFiltro;
    PessoaTask pessoaTask;
    PessoaDAO dao;
    Boolean foiFiltrado = false;

    private void showProgress(){
        swipePessoas.post(new Runnable() {
            @Override
            public void run() {
                swipePessoas.setRefreshing(true);
            }
        });
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        listPessoasFiltro = new ArrayList<>();
        listPessoas = new ArrayList<>();
        dao = new PessoaDAO(getActivity());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View layout = inflater.inflate(R.layout.fragment_lista_pessoas, container, false);
        ButterKnife.bind(this, layout);

        adapterPessoas = new PessoasAdapter(getContext(), listPessoas);

        mListView.setEmptyView(layout.findViewById(R.id.empty));

        mListView.setAdapter(adapterPessoas);
        swipePessoas.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                baixarJson();
            }
        });
        return layout;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if(listPessoas.size() == 0 || listPessoas == null) {
            baixarJson();
        }else if(listPessoas != null && pessoaTask.getStatus() == AsyncTask.Status.RUNNING){
            swipePessoas.setRefreshing(true);
            //showProgress();
        }
    }

    public void baixarJson(){
        ConnectivityManager cm = (ConnectivityManager)getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = cm.getActiveNetworkInfo();
        if(info != null && info.isConnected()) {
            pessoaTask = new PessoaTask();
            new PessoaTask().execute();
        }else{
            swipePessoas.setRefreshing(false);
            Toast.makeText(getActivity(), R.string.falha_conexao, Toast.LENGTH_SHORT).show();
        }
    }

    @OnItemClick(R.id.list_pessoas)
    void onItemSelected(int p) {

        Pessoa pessoa = listPessoas.get(p);

        if(getActivity() instanceof CliqueiNaPessoaListener){
            CliqueiNaPessoaListener listener = (CliqueiNaPessoaListener)getActivity();
            listener.PessoaFoiClicada(pessoa);
        }
    }

    @OnClick(R.id.btn_busca_artista)
    public void buscarArtista(){

        String nome = edtNomeArtista.getText().toString();

        if(nome.toString().equals("")){
            Toast.makeText(getActivity(), "Preencha um nome para pesquisar",
                    Toast.LENGTH_LONG).show();
        }else{

            for(int i = 0; i < listPessoas.size(); i++){
                if(nome.toString().equals(listPessoas.get(i).getNome_pessoa())){
                    listPessoasFiltro.add(listPessoas.get(i));
                }
            }

            if(listPessoasFiltro.size() != 0) {
                adapterPessoasFiltro = new PessoasAdapter(getContext(), listPessoasFiltro);
                mListView.setAdapter(adapterPessoasFiltro);
                adapterPessoas.notifyDataSetChanged();
                foiFiltrado = true;
            }else{
                Toast.makeText(getActivity(), "Artista não localizado",
                        Toast.LENGTH_LONG).show();
                 }
        }
    }


     class PessoaTask extends AsyncTask<Void, Void, ListPessoas> {

        @Override
        public void onPreExecute() {
            super.onPreExecute();
            showProgress();
        }


        @Override
        public ListPessoas doInBackground(Void... params) {
            OkHttpClient client = new OkHttpClient();

            ListPessoas pessoas = null;

            Request request = new Request.Builder()
                    .url("https://dl.dropboxusercontent.com/s/tic0mahjasbsij0/testepessoas2.json?dl=0")
                    .build();

            try {
                Response response = client.newCall(request).execute();
                String jsonString = response.body().string();

                Gson gson = new Gson();
                pessoas = gson.fromJson(jsonString, ListPessoas.class);
                return pessoas;

            }catch(Exception e){
                e.printStackTrace();
            }
            return null;
        }

        @Override
        public void onPostExecute(ListPessoas pessoas) {
            super.onPostExecute(pessoas);

            if(pessoas != null){
                listPessoas.clear();
                listPessoas.addAll(pessoas.getPessoas());
            }
            mListView.setAdapter(adapterPessoas);
            adapterPessoas.notifyDataSetChanged();

            if(getResources().getBoolean(R.bool.tablet)
                    && listPessoas.size() > 0){
                //onItemSelected(0);
            }

            swipePessoas.setRefreshing(false);
        }
    }
}
