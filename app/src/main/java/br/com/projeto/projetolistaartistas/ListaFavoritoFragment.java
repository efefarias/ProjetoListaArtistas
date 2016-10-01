package br.com.projeto.projetolistaartistas;


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

import br.com.projeto.projetolistaartistas.database.PessoaDAO;
import br.com.projeto.projetolistaartistas.model.Pessoa;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnItemClick;


public class ListaFavoritoFragment extends Fragment {

    @Bind(R.id.list_pessoas)
    ListView mListView;

    List<Pessoa> listPessoa;
    ArrayAdapter<Pessoa> adapterPessoa;

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

    @OnItemClick(R.id.list_pessoas)
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

    @Override public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @Subscribe
    public void atualizar(Pessoa pessoa){
        listPessoa.clear();
        //todo
        listPessoa.addAll(dao.listar());
        adapterPessoa.notifyDataSetChanged();
    }
}
