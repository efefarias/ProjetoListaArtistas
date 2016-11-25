package br.com.projeto.projetolistaartistas;


import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.gson.Gson;

import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import br.com.projeto.projetolistaartistas.Util.FuncoesGenericas;
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

    private static final String EXTRA_PESSOA = "param1";
    static String EDIT_TEXT_BUNDLE_KEY = "1";
    static int REQUEST_CODE = 1;
    @Bind(R.id.list_pessoas)
    ListView mListView;
    @Bind(R.id.swipe_pessoas)
    SwipeRefreshLayout swipePessoas;
    @Bind(R.id.fab_pesquisa)
    FloatingActionButton fabPesquisa;
    List<Pessoa> listPessoas;
    List<Pessoa> listPessoasFiltro;
    ArrayAdapter<Pessoa> adapterPessoas;
    ArrayAdapter<Pessoa> adapterPessoasFiltro;
    PessoaTask pessoaTask;
    PessoaDAO dao;
    Boolean foiFiltrado;
    FuncoesGenericas fg = new FuncoesGenericas();

    int usu_id = 0;

    public static ListaPessoasFragment newInstance(int id) {
        ListaPessoasFragment fragment = new ListaPessoasFragment();
        Bundle args = new Bundle();
        args.putInt(EXTRA_PESSOA, id);
        fragment.setArguments(args);
        return fragment;
    }

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

        Bundle bundle = this.getArguments();
        if (bundle != null) {
            usu_id = getArguments().getInt(EXTRA_PESSOA);
        }

        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        listPessoasFiltro = new ArrayList<>();
        listPessoas = new ArrayList<>();
        dao = new PessoaDAO(getActivity());
        foiFiltrado = false;
        ((PessoaApp)getActivity().getApplication()).getEventBus().register(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View layout = inflater.inflate(R.layout.fragment_lista_pessoas, container, false);
        ButterKnife.bind(this, layout);

        foiFiltrado = false;

        adapterPessoas = new PessoasAdapter(getContext(), listPessoas);

        mListView.setEmptyView(layout.findViewById(R.id.empty));

        mListView.setAdapter(adapterPessoas);
        swipePessoas.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                foiFiltrado = false;
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

    @Override
    public void onStart() {
        super.onStart();

        Bundle bundle = this.getArguments();
        if (bundle != null) {
            int myInt = bundle.getInt("1");
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

        Pessoa pessoa;

        if(foiFiltrado)
        {
            pessoa = listPessoasFiltro.get(p);
        }
        else
        {
            pessoa = listPessoas.get(p);
        }
        if(getActivity() instanceof CliqueiNaPessoaListener){
            CliqueiNaPessoaListener listener = (CliqueiNaPessoaListener)getActivity();
            listener.PessoaFoiClicada(pessoa, usu_id);
            //getActivity().onBackPressed();
        }
    }

    @Override
    public void onDestroy() {
        //foiFiltrado = false;
        super.onDestroy();
        ((PessoaApp)getActivity().getApplication()).getEventBus().unregister(this);
    }
    @Subscribe
    public void atualizar(Pessoa pessoa){
        //listPessoas.clear();
        //todo
        //listPessoas.addAll(dao.listar());
        //listPessoas.get(0).setAvaliacoes(pessoa.getAvaliacoes());

        for(int i = 0; i < listPessoas.size(); i++){
            if(listPessoas.get(i).getUsu_id() == pessoa.getUsu_id()){
                listPessoas.get(i).setAvaliacao(pessoa.getAvaliacao());
            }
        }

        adapterPessoas.notifyDataSetChanged();
    }
    @Override
    public void onDestroyView() {
        //foiFiltrado = false;
        super.onDestroyView();
    }

    //Funções de pesquisa
    @OnClick(R.id.fab_pesquisa)
    public void buscarArtista(){
        //FragmentManager fm = getActivity().getSupportFragmentManager();
        DialogFragment dialogFragment = DialogPesquisa.newInstance();
        dialogFragment.setTargetFragment(this, REQUEST_CODE);
        dialogFragment.show(getFragmentManager(), "dialog");
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == DialogPesquisa.REQUEST_CODE) {
            //recebendo o campo a ser pesquisado
            String campoPesquisa = data.getStringExtra("pesquisa");
            //recebendo o filtro a ser feita a busca
            String filtroPesquisa = data.getStringExtra("filtro");

            if(filtroPesquisa == "Nome"){
                buscarArtistaNome(campoPesquisa);
            }
            if(filtroPesquisa == "Categoria"){
                buscarArtistaCategoria(campoPesquisa);
            }
            if(filtroPesquisa == "Obra"){
                buscarArtistaObra(campoPesquisa);
            }
            if(filtroPesquisa == "Avaliação"){
                buscarArtistaAvaliacao(campoPesquisa);
            }
        }
    }

    public void buscarArtistaNome(String nome){

        listPessoasFiltro.clear();

        for(int i = 0; i < listPessoas.size(); i++){
            if(fg.verificarNome(listPessoas.get(i).getUsu_nome().toLowerCase(), nome.toLowerCase())) {
                listPessoasFiltro.add(listPessoas.get(i));
            }
        }

        listPessoasFiltro = fg.removeDuplicados(listPessoasFiltro);

        if(listPessoasFiltro.size() != 0) {
            adapterPessoasFiltro = new PessoasAdapter(getContext(), listPessoasFiltro);
            mListView.setAdapter(adapterPessoasFiltro);
            adapterPessoas.notifyDataSetChanged();
            foiFiltrado = true;
        }else{
            Toast.makeText(getActivity(), "Artista não localizado", Toast.LENGTH_LONG).show();
        }
    }

    public void buscarArtistaCategoria(String nome){

        listPessoasFiltro.clear();

        for(int i = 0; i < listPessoas.size(); i++){
            for(int j = 0; j < listPessoas.get(i).getObra().size(); j++){
                //if(listPessoas.get(i).getObra().get(j).getCat_obra_descricao().toString().equals(nome))
                if(fg.verificarNome(listPessoas.get(i).getObra().get(j).getCat_obra_descricao().toString().toLowerCase(), nome.toLowerCase()))
                    listPessoasFiltro.add(listPessoas.get(i));
            }
        }

        listPessoasFiltro = fg.removeDuplicados(listPessoasFiltro);

        if(listPessoasFiltro.size() != 0) {
            adapterPessoasFiltro = new PessoasAdapter(getContext(), listPessoasFiltro);
            mListView.setAdapter(adapterPessoasFiltro);
            adapterPessoas.notifyDataSetChanged();
            foiFiltrado = true;
        }else{
            Toast.makeText(getActivity(), "Categoria não localizada", Toast.LENGTH_LONG).show();
        }
    }

    public void buscarArtistaObra(String nome){

        listPessoasFiltro.clear();

        for(int i = 0; i < listPessoas.size(); i++){
            for(int j = 0; j < listPessoas.get(i).getObra().size(); j++){
                //if(listPessoas.get(i).getObra().get(j).getObr_descricao().toString().equals(nome))
                if(fg.verificarNome(listPessoas.get(i).getObra().get(j).getObr_descricao().toString().toLowerCase(), nome.toLowerCase()))
                    listPessoasFiltro.add(listPessoas.get(i));
            }
        }

        listPessoasFiltro = fg.removeDuplicados(listPessoasFiltro);

        if(listPessoasFiltro.size() != 0) {
            adapterPessoasFiltro = new PessoasAdapter(getContext(), listPessoasFiltro);
            mListView.setAdapter(adapterPessoasFiltro);
            adapterPessoas.notifyDataSetChanged();
            foiFiltrado = true;
        }else{
            Toast.makeText(getActivity(), "Obra não localizada", Toast.LENGTH_LONG).show();
        }
    }

    public void buscarArtistaAvaliacao(String nome){

        listPessoasFiltro.clear();

        for(int i = 0; i < listPessoas.size(); i++){
            for(int j = 0; j < listPessoas.get(i).getAvaliacao().size(); j++){
                if(fg.verificarNome(listPessoas.get(i).getAvaliacao().get(j).getAva_descricao().toString().toLowerCase(), nome.toLowerCase()))
                    listPessoasFiltro.add(listPessoas.get(i));
            }
        }

        listPessoasFiltro = fg.removeDuplicados(listPessoasFiltro);

        if(listPessoasFiltro.size() != 0) {
            adapterPessoasFiltro = new PessoasAdapter(getContext(), listPessoasFiltro);
            mListView.setAdapter(adapterPessoasFiltro);
            adapterPessoas.notifyDataSetChanged();
            foiFiltrado = true;
        }else{
            Toast.makeText(getActivity(), "Avaliação não localizada", Toast.LENGTH_LONG).show();
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
                    //.url("https://dl.dropboxusercontent.com/s/7nkzh4zqyc0upe6/pessoasfinal.json?dl=0")
                    .url("https://www.doocati.com.br/tcc/webservice/mobile/detalharartista")
                    .build();

            try {
                Response response = client.newCall(request).execute();
                String jsonString = response.body().string();

                jsonString = jsonString.replace(getResources().getString(R.string.json_find), getResources().getString(R.string.json_replace));
                jsonString = jsonString.replace("}}}", "}]}");

                String jsonFormatada = fg.formataJson(jsonString);

                Gson gson = new Gson();
                //pessoas = gson.fromJson(jsonString, ListPessoas.class);
                pessoas = gson.fromJson(jsonFormatada, ListPessoas.class);
                return pessoas;

            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        public void onPostExecute(ListPessoas pessoas) {
            super.onPostExecute(pessoas);

            if (pessoas != null) {
                listPessoas.clear();
                listPessoas.addAll(pessoas.getPessoas());
                Collections.sort(listPessoas, new Comparator<Pessoa>() {
                    public int compare(Pessoa p1, Pessoa p2) {
                        //Ordem decrescente por média de rating
                        if (p1.getAvaliacao().size() != 0 && p2.getAvaliacao().size() != 0) {

                            Double somaVotosP1 = 0.0;
                            Double somaVotosP2 = 0.0;
                            Double mediaP1 = 0.0;
                            Double mediaP2 = 0.0;
                            Double qtdVotosP1 = 0.0;
                            Double qtdVotosP2 = 0.0;

                            //Somando as notas e verificando a média da primeira pessoa
                            for (int i = 0; i < p1.getAvaliacao().size(); i++) {
                                somaVotosP1 = somaVotosP1 + p1.getAvaliacao().get(i).getAva_nota();
                                qtdVotosP1 = qtdVotosP1 + 1;
                            }

                            if (somaVotosP1 != 0.0 && qtdVotosP1 != 0.0) {
                                mediaP1 = somaVotosP1 / qtdVotosP1;
                            }

                            //Somando as notas e verificando a média da segunda pessoa
                            for (int i = 0; i < p2.getAvaliacao().size(); i++) {
                                somaVotosP2 = somaVotosP2 + p2.getAvaliacao().get(i).getAva_nota();
                                qtdVotosP2 = qtdVotosP2 + 1;
                            }

                            if (somaVotosP2 != 0.0 && qtdVotosP2 != 0.0) {
                                mediaP2 = somaVotosP2 / qtdVotosP2;
                            }

                            //return Double.valueOf(p1.getAvaliacao().get(0).getAva_nota()).compareTo(p2.getAvaliacao().get(0).getAva_nota());
                            Double valor = mediaP2 - mediaP1;
                            return valor.intValue();
                        }
                        return 1;
                    }
                });
            }
            mListView.setAdapter(adapterPessoas);
            adapterPessoas.notifyDataSetChanged();

            //if(getResources().getBoolean(R.bool.tablet)
            //        && listPessoas.size() > 0){
            //onItemSelected(0);
            //}

            swipePessoas.setRefreshing(false);
        }
    }

}
