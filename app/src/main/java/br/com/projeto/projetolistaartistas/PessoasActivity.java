package br.com.projeto.projetolistaartistas;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;

import org.parceler.Parcels;

import br.com.projeto.projetolistaartistas.model.Pessoa;
import br.com.projeto.projetolistaartistas.model.Usuario;
import butterknife.ButterKnife;

public class PessoasActivity extends AppCompatActivity implements CliqueiNaPessoaListener, GoogleApiClient.OnConnectionFailedListener, NavigationView.OnNavigationItemSelectedListener {


    public final String USUARIO = "USUARIO";
    int usu_id = 0;
    DrawerLayout drawer;
    NavigationView navigationView;
    View headerview;
    private GoogleApiClient mGoogleApiClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Intent intent = getIntent();
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this /* FragmentActivity */, this /* OnConnectionFailedListener */)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();


        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        headerview = navigationView.inflateHeaderView(R.layout.nav_header_main);
        setupDrawerContent(navigationView);
        if (intent != null) {
            carregardadosUsuario(intent);
        }

    }

    private void carregardadosUsuario(Intent in) {
        try {
            ImageView imageView = (ImageView) headerview.findViewById(R.id.imageView);
            TextView nome = (TextView) headerview.findViewById(R.id.menu_nome);
            TextView email = (TextView) headerview.findViewById(R.id.menu_email);
            Usuario usuario = in.getExtras().getParcelable(USUARIO);
            Bitmap bitmap = in.getParcelableExtra("IMAGE");
            if (usuario != null) {
                nome.setText(usuario.getUsu_nome());
                email.setText(usuario.getUsu_email());
                usu_id = usuario.getUsu_id();
                imageView.setImageBitmap(bitmap);

            }
        } catch (Exception e) {
            Log.e("DADOS", "ERRO AO CARREGAR DADOS: " + e.getMessage());
        }
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
        Class fragmentClass = null;
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
            case R.id.sign_out:
                signOut();
                break;
            default:
                fragmentClass = MapaFragment.class;
        }

        try {
            assert fragmentClass != null;
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

    private void signOut() {
        Auth.GoogleSignInApi.signOut(mGoogleApiClient).setResultCallback(
                new ResultCallback<Status>() {
                    @Override
                    public void onResult(@NonNull Status status) {
                        // [START_EXCLUDE]
                        Intent i = new Intent(PessoasActivity.this, LoginActivity.class);
                        startActivity(i);
                        finish();
                        // [END_EXCLUDE]
                    }
                });
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

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

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
