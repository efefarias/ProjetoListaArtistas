package br.com.projeto.projetolistaartistas;

import android.content.Intent;
import android.os.Parcelable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import org.parceler.Parcels;

import br.com.projeto.projetolistaartistas.model.Pessoa;
import butterknife.Bind;
import butterknife.ButterKnife;

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
            return 2;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            if(position == 0) return getString(R.string.aba_jogos);
            return getString(R.string.aba_favoritos);
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
            Intent it = new Intent(this, DetalhePessoaActivity.class);
            Parcelable p = Parcels.wrap(pessoa);
            it.putExtra(DetalhePessoaActivity.EXTRA_PESSOA, p);
            startActivity(it);
        }
    }
}
