package br.com.projeto.projetolistajogos;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import org.greenrobot.eventbus.Subscribe;

import java.util.List;

import br.com.projeto.projetolistajogos.database.PessoaDAO;
import br.com.projeto.projetolistajogos.model.Pessoa;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnItemClick;


public class ListaFavoritoFragment extends Fragment {

    @Bind(R.id.list_jogos)
    ListView mListView;

    List<Pessoa> listPessoa;
    ArrayAdapter<Pessoa> adapterPessoa;
    //ArrayAdapter<Jogo> adapterJogos;
    PessoaDAO dao;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        dao = new PessoaDAO(getActivity());
        //todo
        listPessoa = dao.listar();

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

    @OnItemClick(R.id.list_jogos)
    void onItemSelected(int p) {

        Pessoa pessoa = listPessoa.get(p);

        if(getActivity() instanceof CliqueiNaPessoaListener){
            CliqueiNaPessoaListener listener = (CliqueiNaPessoaListener)getActivity();
            listener.PessoaFoiClicada(pessoa);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        ((PessoaApp)getActivity().getApplication()).getEventBus().unregister(this);
    }

    @Subscribe
    public void atualizar(Pessoa pessoa){
        listPessoa.clear();
        //todo
        listPessoa.addAll(dao.listar());
        adapterPessoa.notifyDataSetChanged();
    }
}
