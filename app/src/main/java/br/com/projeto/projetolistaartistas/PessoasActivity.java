package br.com.projeto.projetolistaartistas;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Parcelable;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
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

public class PessoasActivity extends AppCompatActivity implements CliqueiNaPessoaListener, NavigationView.OnNavigationItemSelectedListener {



    DrawerLayout drawer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        setupDrawerContent(navigationView);
        /*viewPager.setAdapter(new JogoPager(getSupportFragmentManager()));
        tabLayout.setupWithViewPager(viewPager);*/

    }


    private void setupDrawerContent(NavigationView navigationView) {
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        selectDrawerItem(menuItem);
                        return true;
                    }
                });
    }
    public void selectDrawerItem(MenuItem menuItem) {
        // Create a new fragment and specify the fragment to show based on nav item clicked
        Fragment fragment = null;
        Class fragmentClass;
        switch(menuItem.getItemId()) {
            case R.id.map:
                fragmentClass = MapaFragment.class;
                break;
            case R.id.nav_artistas:
                fragmentClass = ListaPessoasFragment.class;
                break;
            case R.id.nav_favoritos:
                fragmentClass = ListaFavoritoFragment.class;
                break;
            default:
                fragmentClass = MapaFragment.class;
        }

        try {
            fragment = (Fragment) fragmentClass.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Insert the fragment by replacing any existing fragment
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.flContent, fragment).commit();

        // Highlight the selected item has been done by NavigationView
        menuItem.setChecked(true);
        // Set action bar title
        setTitle(menuItem.getTitle());
        // Close the navigation drawer
        drawer.closeDrawers();
    }


    @Override
    public void PessoaFoiClicada(Pessoa pessoa) {

        if (getResources().getBoolean(R.bool.tablet)) {
            DetalhePessoaFragment dpf = DetalhePessoaFragment.newInstance(pessoa);
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.detalhe_jogo, dpf, "detalhe")
                    .commit();
        } else {
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

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        return false;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.map:
                drawer.openDrawer(GravityCompat.START);
                return true;

        }
        return super.onOptionsItemSelected(item);
    }

    //FASF - Criando subclasse para paginação do viewPager de acordo com o fragment
    class JogoPager extends FragmentPagerAdapter{

        public JogoPager(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            if(position == 0){
                return new ListaPessoasFragment();
            }
            else if(position ==1) {
                return new ListaFavoritoFragment();
            }
            /*else if(position == 2){
                return new ListaAvaliadosFragment();
            }*/
            else if (position == 2) {
                return new MapaFragment();
            }
            return new ListaPessoasFragment();
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
            /*else if(position == 2) {
                return getString(R.string.aba_melhor_avaliados);
            }*/
            else if (position == 2) {
                return getString(R.string.clouser);
            }
            return getString(R.string.aba_pessoas);
        }
    }
}
