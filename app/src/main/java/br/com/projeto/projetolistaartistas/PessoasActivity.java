package br.com.projeto.projetolistaartistas;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Parcelable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SearchView;
import android.widget.TextView;

import org.parceler.Parcels;

import java.util.List;

import br.com.projeto.projetolistaartistas.model.Pessoa;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class PessoasActivity extends AppCompatActivity implements CliqueiNaPessoaListener {

    @Bind(R.id.viewPager)
    ViewPager viewPager;
    @Bind(R.id.tabLayout)
    TabLayout tabLayout;
    @Bind(R.id.toolbar)
    android.support.v7.widget.Toolbar toolBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pessoas);
        ButterKnife.bind(this);

        setSupportActionBar(toolBar);

        viewPager.setAdapter(new JogoPager(getSupportFragmentManager()));
        tabLayout.setupWithViewPager(viewPager);

    }


    /*//FASF - Criando subclasse para paginação do viewPager de acordo com o fragment
    class JogoPager extends FragmentPagerAdapter{

        public JogoPager(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            if(position == 0) return new ListaPessoasFragment();
            return new ListaFavoritoFragment();
        }

        @Override
        public int getCount() {
            return 2;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            if(position == 0) return getString(R.string.aba_pessoas);
            return getString(R.string.aba_favoritos);
        }
    }*/

    //FASF - Criando subclasse para paginação do viewPager de acordo com o fragment
    class JogoPager extends FragmentPagerAdapter{

        public JogoPager(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            if(position == 0) return new ListaPessoasFragment();
            return new ListaFavoritoFragment();
        }

        @Override
        public int getCount() {
            return 3;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            if(position == 0) {
                return getString(R.string.aba_pessoas);
            }
            else if(position == 1) {
                return getString(R.string.aba_favoritos);
            }
            else if(position == 2) {
                return getString(R.string.aba_melhor_avaliados);
            }
            return getString(R.string.aba_melhor_avaliados);
        }
    }



    @Override
    public void PessoaFoiClicada(Pessoa pessoa) {

        if(getResources().getBoolean(R.bool.tablet)){
            DetalhePessoaFragment dpf = DetalhePessoaFragment.newInstance(pessoa);
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.detalhe_jogo, dpf, "detalhe")
                    .commit();
        }else {
            //Intent it = new Intent(this, DetalhePessoaActivity.class);
            //Parcelable p = Parcels.wrap(pessoa);
            //it.putExtra(DetalhePessoaActivity.EXTRA_PESSOA, p);
            //startActivity(it);
            Intent it = new Intent(this, DetalhePessoaActivity.class);
            Parcelable p = Parcels.wrap(pessoa);
            it.putExtra(DetalhePessoaActivity.EXTRA_PESSOA, p);
            startActivityForResult(it, 1);
        }
    }
}
