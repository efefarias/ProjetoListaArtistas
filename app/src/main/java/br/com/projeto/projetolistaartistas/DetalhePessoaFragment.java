package br.com.projeto.projetolistaartistas;


import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.ShareActionProvider;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import br.com.projeto.projetolistaartistas.AvaliacaoActivity;
import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import org.parceler.Parcels;


import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import br.com.projeto.projetolistaartistas.Util.FuncoesGenericas;
import br.com.projeto.projetolistaartistas.database.PessoaDAO;
import br.com.projeto.projetolistaartistas.model.Avaliacao;
import br.com.projeto.projetolistaartistas.model.ListPessoas;
import br.com.projeto.projetolistaartistas.model.Obra;
import br.com.projeto.projetolistaartistas.model.Pessoa;
import br.com.projeto.projetolistaartistas.Util.SimpleDialog;


import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnItemClick;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class DetalhePessoaFragment extends Fragment {

    private static final String EXTRA_PESSOA = "param1";

    static final int PICK_CONTACT_REQUEST = 1;  // The request code

    @Bind(R.id.text_detalhes_pessoa)
    TextView txtDetalhesPessoa;
    @Bind(R.id.img_capa)
    ImageView imgView;
    @Bind(R.id.fab_favorito)
    FloatingActionButton fabFavorito;
    @Bind(R.id.fab_favorito2)
    FloatingActionButton fabFavorito2;
    @Bind(R.id.list_obras)
    ListView mlistObras;
    @Bind(R.id.swipe_pessoas)
    SwipeRefreshLayout swipePessoas;
    @Bind(R.id.img_full)
    ImageView imgFullObra;
    @Bind(R.id.txt_nome_obra)
    TextView txtNomeObra;
    @Bind(R.id.layout_conteudo)
    FrameLayout frameConteudo;
    @Bind(R.id.fab_Mapa)
    FloatingActionButton fab_mapa;

    private ShareActionProvider mShareActionProvider;
    List<Obra> listObras;
    PessoaDAO pessoaDAO;
    ArrayAdapter<Obra> adapterObras;
    private Pessoa pessoa;
    //PessoaTask pessoaTask;
    PessoaTask pessoaTask = new PessoaTask();
    FuncoesGenericas fg = new FuncoesGenericas();

    int j = 0;
    int k = 0;
    boolean entrou = false;

    private void showProgress(){
        swipePessoas.post(new Runnable() {
            @Override
            public void run() {
                if (!(swipePessoas == null))
                    swipePessoas.setRefreshing(true);
            }
        });
    }

    public static DetalhePessoaFragment newInstance(Pessoa pessoa) {
        DetalhePessoaFragment fragment = new DetalhePessoaFragment();
        Bundle args = new Bundle();
        Parcelable p = Parcels.wrap(pessoa);
        args.putParcelable(EXTRA_PESSOA, p);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        pessoaDAO = new PessoaDAO(getActivity());
        if (getArguments() != null) {
            Parcelable p = getArguments().getParcelable(EXTRA_PESSOA);
            pessoa = Parcels.unwrap(p);
        }

        listObras = new ArrayList<>();
        imgFullObra = new ImageView(getActivity());
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_detalhe, menu);

        MenuItem item = menu.findItem(R.id.menu_item_share);

        mShareActionProvider = (ShareActionProvider) MenuItemCompat.getActionProvider(item);

        Intent it = new Intent(Intent.ACTION_SEND);
        it.putExtra(Intent.EXTRA_TEXT, pessoa.getUsu_nome());
        it.setType("text/plain");
        mShareActionProvider.setShareIntent(it);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if(listObras.size() == 0 || listObras == null) {
            baixarJson();
        }else if(listObras != null && pessoaTask.getStatus() == AsyncTask.Status.RUNNING){
            swipePessoas.setRefreshing(true);
            showProgress();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_detalhe_pessoa, container, false);

        ButterKnife.bind(this, view);

        txtDetalhesPessoa.setText("           " + pessoa.getUsu_nome() + ", clique nas obras para obter detalhes. "  );

        //Foto do artista
        Glide.with(getActivity()).load(pessoa.getUsu_imagem()).into(imgView);

        //Obras
        //adapterObras = new ObraPessoaAdapter(getContext(), listObras);
        if(pessoa.getObra() != null) {
            adapterObras = new ObraPessoaAdapter(getContext(), pessoa.getObra());
            adapterObras.notifyDataSetChanged();
        }else{
            baixarJson();
        }
        mlistObras.setEmptyView(view.findViewById(R.id.empty));
        mlistObras.setAdapter(adapterObras);

        swipePessoas.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                //baixarJson();
            }
        });
        imgFullObra.setClickable(false);
        alteraFavorito();

        return view;
    }

    //FASF - 20/09/2016 - Método que verifica a imagem que será atribuida ao botão, dependendo se a pessoa é favorita
    private void alteraFavorito(){
        Boolean favorito = pessoaDAO.isfavorito(pessoa);

        fabFavorito.setImageResource(favorito ? R.drawable.ic_remove : R.drawable.ic_add);
    }

    @Override
    public void onDestroy() {
        //Cancelando requisições
        if(pessoaTask != null){
            pessoaTask.cancel(true);
        }

        //int i = 1;
        //Fragment fragment = new Fragment();
        //Bundle bundle = new Bundle();
        //bundle.putInt("1", i);
        //fragment.setArguments(bundle);

        if(!(swipePessoas == null))
            swipePessoas.setRefreshing(false);

        ((PessoaApp)getActivity().getApplication()).getEventBus().unregister(this);

        super.onDestroy();
    }

    @Override public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);

        //Cancelando requisições
        //if(pessoaTask.isCancelled()==false)
        //    pessoaTask.cancel(true);

        if(!(swipePessoas == null))
            swipePessoas.setRefreshing(false);
    }

    @Override
    public void onStop() {
        super.onStop();

        //Cancelando requisições
        //if(pessoaTask.isCancelled()==false)
        //pessoaTask.cancel(true);

        if(!(swipePessoas == null))
            swipePessoas.setRefreshing(false);
    }

    @OnClick(R.id.fab_favorito)
    public void favoritoClick(){
        if(pessoaDAO.isfavorito(pessoa)){
            //remove
            pessoaDAO.excluir(pessoa);
        }else{
            //add
            pessoaDAO.inserir(pessoa);
        }

        fabFavorito.animate()
                .scaleX(0)
                .scaleY(0)
                .setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        super.onAnimationEnd(animation);
                        alteraFavorito();
                        fabFavorito.animate()
                                .scaleX(1)
                                .scaleY(1)
                                .setListener(null);
                    }
                });

        alteraFavorito();



        //FASF 15/05/2016 - Enviando um post, e assim definindo ao portador da escuta, que atualize a página
        ((PessoaApp)getActivity().getApplication()).getEventBus().post(pessoa);
    }

    @OnClick(R.id.fab_favorito2)
    public void abrirAvaliacao() {
        Intent intent = new Intent(getActivity(), AvaliacaoActivity.class);
        startActivityForResult(intent, PICK_CONTACT_REQUEST);
    }

    @OnClick(R.id.fab_Mapa)
    public void abrirMapa() {
        Intent intent = new Intent(getActivity(), MapaActivity.class);
        startActivityForResult(intent, PICK_CONTACT_REQUEST);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == 1) {
            if(resultCode == Activity.RESULT_OK){
                String result = data.getStringExtra("result");
                Avaliacao a = new Avaliacao();

                Calendar c = Calendar.getInstance();

                //TODO
                //a.setDataVoto(c.getTime());
                //a.setFlag_nota_usuario('S');
                //a.setId_nota("15");
                //a.setNota(Double.valueOf(result));

                //pessoa.getAvaliacoes().add(a);

                ((PessoaApp)getActivity().getApplication()).getEventBus().post(pessoa);

                //getActivity().onBackPressed(pessoa);
            }
            if (resultCode == Activity.RESULT_CANCELED) {
                //Write your code if there's no result
            }
        }
    }

    //Baixando dados das pessoas(Obras)
    public void baixarJson(){
        ConnectivityManager cm = (ConnectivityManager)getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = cm.getActiveNetworkInfo();
        if(info != null && info.isConnected() && pessoaTask.getStatus().toString() != "RUNNING") {
            //PessoaTask pessoaTask = new PessoaTask();
            //new PessoaTask().execute();
            pessoaTask.execute();
        }else{
            swipePessoas.setRefreshing(false);
            //Toast.makeText(getActivity(), R.string.falha_conexao, Toast.LENGTH_SHORT).show();
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

            }catch(Exception e){
                e.printStackTrace();
            }
            return null;
        }

        @Override
        public void onPostExecute(ListPessoas pessoas) {
            super.onPostExecute(pessoas);



                //Obras
                for (j = 0; j < pessoas.getPessoas().size(); j++) {
                    if (pessoa.getUsu_nome().equals(pessoas.getPessoas().get(j).getUsu_nome())) {
                        pessoa.setObra(pessoas.getPessoas().get(j).getObra());//pessoa.setObras(pessoas.getPessoas().get(i).getObras());
                    }
                }


                //Avaliações
                for (k = 0; k < pessoas.getPessoas().size(); k++) {
                    if (pessoa.getUsu_nome().equals(pessoas.getPessoas().get(k).getUsu_nome())) {
                        pessoa.setAvaliacao(pessoas.getPessoas().get(k).getAvaliacao());//pessoa.setObras(pessoas.getPessoas().get(i).getObras());
                    }
                }

                if (entrou == false) {
                    adapterObras = new ObraPessoaAdapter(getContext(), pessoa.getObra());
                    adapterObras.notifyDataSetChanged();
                    entrou = true;
                }

                mlistObras.setAdapter(adapterObras);

                if (!(swipePessoas == null))
                    swipePessoas.setRefreshing(false);

        }
    }

    @OnItemClick(R.id.list_obras)
    void onItemSelected(int p) {

        //Fade out no layout de fundo
        frameConteudo.animate().alpha((float) 0.15);

        //Nome da obra sendo expandida
        txtNomeObra.setText(pessoa.getObra().get(p).getObr_descricao());
        //txtNomeObra.setShadowLayer((float)0.8,(float)0.8,(float)0.8, Color.BLACK);
        txtNomeObra.animate().scaleX(1).scaleY(1);

        //Imagem da Obra sendo expandida
        //Picasso.with(getContext()).load(pessoa.getObra().get(p).getImg_url()).into(imgFullObra);
        Picasso.with(getContext()).load(pessoa.getObra().get(p).getImagens().get(0).getImg_url()).into(imgFullObra);
        imgFullObra.animate().scaleX(1).scaleY(1);
        imgFullObra.setClickable(true);

    }

    @OnClick(R.id.img_full)
    void onClick(){
        if(imgFullObra.isClickable())
        {
            //Fade in no layout de fundo
            frameConteudo.animate().alpha((float) 1);

            //Nome da obra sendo recolhido
            txtNomeObra.animate().scaleX(0).scaleY(0);

            //Imagem da obra sendo recolhida
            imgFullObra.animate().scaleX(0).scaleY(0);

            imgFullObra.setClickable(false);
        }

    }
}
