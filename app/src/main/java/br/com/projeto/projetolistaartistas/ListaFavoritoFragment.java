package br.com.projeto.projetolistaartistas;


import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.gson.Gson;

import org.greenrobot.eventbus.Subscribe;

import java.util.List;

import br.com.projeto.projetolistaartistas.database.ObraDAO;
import br.com.projeto.projetolistaartistas.database.PessoaDAO;
import br.com.projeto.projetolistaartistas.model.ListPessoas;
import br.com.projeto.projetolistaartistas.model.Obra;
import br.com.projeto.projetolistaartistas.model.Pessoa;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnItemClick;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


public class ListaFavoritoFragment extends Fragment {

    @Bind(R.id.list_pessoas)
    ListView mListView;

    List<Pessoa> listPessoa;
    List<Obra> listObra;
    ArrayAdapter<Pessoa> adapterPessoa;

    Pessoa pessoa = new Pessoa();

    PessoaDAO daoPessoa;
    ObraDAO daoObra;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        daoPessoa = new PessoaDAO(getActivity());
        daoObra = new ObraDAO(getActivity());
        //todo
        listPessoa = daoPessoa.listar();
        listObra = daoObra.listar();

        //Verificando se as imagens estão carregadas para cada Artista
        for(int i = 0; i < listPessoa.size(); i++)
        {
            if(listPessoa.get(i).getObras() == null)
            {
                baixarJson();
            }
        }



        ((PessoaApp)getActivity().getApplication()).getEventBus().register(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View layout = inflater.inflate(R.layout.fragment_lista_favorito, container, false);
        ButterKnife.bind(this, layout);

        adapterPessoa = new PessoasAdapter(getContext(), listPessoa);

        mListView.setEmptyView(layout.findViewById(R.id.empty));

        mListView.setAdapter(adapterPessoa);

        return layout;
    }

    //@OnItemClick(R.id.list_pessoas)
    //void onItemSelected(int p) {
    //
    //    pessoa = listPessoa.get(p);
    //
    //    if(getActivity() instanceof CliqueiNaPessoaListener){
    //        CliqueiNaPessoaListener listener = (CliqueiNaPessoaListener)getActivity();
    //        listener.PessoaFoiClicada(pessoa);
    //    }
    //}

    @Override
    public void onDestroy() {
        super.onDestroy();
        ((PessoaApp)getActivity().getApplication()).getEventBus().unregister(this);
    }

    @Override public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @Subscribe
    public void atualizar(Pessoa pessoa){
        listPessoa.clear();
        //todo
        listPessoa.addAll(daoPessoa.listar());
        adapterPessoa.notifyDataSetChanged();
    }

    //Baixando dados das pessoas(Obras)
    public void baixarJson(){
        ConnectivityManager cm = (ConnectivityManager)getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = cm.getActiveNetworkInfo();
        if(info != null && info.isConnected()) {
            PessoaTask pessoaTask = new PessoaTask();
            new PessoaTask().execute();
        }else{
            Toast.makeText(getActivity(), R.string.falha_conexao, Toast.LENGTH_SHORT).show();
        }
    }

    class PessoaTask extends AsyncTask<Void, Void, ListPessoas> {

        @Override
        public void onPreExecute() {
            super.onPreExecute();
        }


        @Override
        public ListPessoas doInBackground(Void... params) {
            OkHttpClient client = new OkHttpClient();

            ListPessoas pessoas = null;

            Request request = new Request.Builder()
                    .url("https://dl.dropboxusercontent.com/s/f7qtu7vfqkvezpa/pessoasfinal.json?dl=0")
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

            for(int i = 0; i < listPessoa.size(); i++) {
                if(listPessoa.get(i).getId_pessoa() == pessoas.getPessoas().get(i).getId_pessoa()){
                    listPessoa.get(i).setObras(pessoas.getPessoas().get(i).getObras());//pessoa.setObras(pessoas.getPessoas().get(i).getObras());
                }
            }
        }
    }
}
