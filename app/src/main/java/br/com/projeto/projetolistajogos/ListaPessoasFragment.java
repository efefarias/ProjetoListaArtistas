package br.com.projeto.projetolistajogos;


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
import android.widget.ListView;
import android.widget.Toast;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import br.com.projeto.projetolistajogos.model.ListPessoas;
import br.com.projeto.projetolistajogos.model.Pessoa;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnItemClick;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


public class ListaPessoasFragment extends Fragment {

    @Bind(R.id.list_jogos)
    ListView mListView;
    @Bind(R.id.swipe_jogos)
    SwipeRefreshLayout swipeJogos;

    List<Pessoa> listPessoas;
    ArrayAdapter<Pessoa> adapterPessoas;
    PessoaTask pessoaTask;

    private void showProgress(){
        swipeJogos.post(new Runnable() {
            @Override
            public void run() {
                swipeJogos.setRefreshing(true);
            }
        });
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        listPessoas = new ArrayList<>();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View layout = inflater.inflate(R.layout.fragment_lista_pessoas, container, false);
        ButterKnife.bind(this, layout);

        adapterPessoas = new PessoasAdapter(getContext(), listPessoas);

        mListView.setEmptyView(layout.findViewById(R.id.empty));

        mListView.setAdapter(adapterPessoas);
        swipeJogos.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
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
            swipeJogos.setRefreshing(true);
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
            swipeJogos.setRefreshing(false);
            Toast.makeText(getActivity(), R.string.falha_conexao, Toast.LENGTH_SHORT).show();
        }
    }

    @OnItemClick(R.id.list_jogos)
    void onItemSelected(int p) {

        Pessoa pessoa = listPessoas.get(p);

        if(getActivity() instanceof CliqueiNaPessoaListener){
            CliqueiNaPessoaListener listener = (CliqueiNaPessoaListener)getActivity();
            listener.PessoaFoiClicada(pessoa);
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
            adapterPessoas.notifyDataSetChanged();

            if(getResources().getBoolean(R.bool.tablet)
                    && listPessoas.size() > 0){
                //onItemSelected(0);
            }

            swipeJogos.setRefreshing(false);
        }
    }
}
