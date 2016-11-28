package br.com.projeto.projetolistaartistas;

/**
 * Created by f.soares.de.farias on 11/7/2016.
 */

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.FragmentActivity;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.gson.Gson;

import org.parceler.Parcels;

import br.com.projeto.projetolistaartistas.Util.FuncoesGenericas;
import br.com.projeto.projetolistaartistas.model.Coordenadas;
import br.com.projeto.projetolistaartistas.model.ListPessoas;
import br.com.projeto.projetolistaartistas.model.Pessoa;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MapaActivity extends FragmentActivity {
    FuncoesGenericas fg = new FuncoesGenericas();
    PessoaTask pessoaTask;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        LatLng latLng;

        Intent i = getIntent();
        Coordenadas c = i.getParcelableExtra("coordenadas");
        latLng  = new LatLng(Double.parseDouble(c.getLatitude()), Double.parseDouble(c.getLongitude()));


        setContentView(R.layout.layout_mapa);

        SupportMapFragment fragment =
                (SupportMapFragment)getSupportFragmentManager()
                        .findFragmentById(R.id.map);
        GoogleMap map = fragment.getMap();

        map.addMarker(new MarkerOptions()
                .position(latLng)
                .icon(BitmapDescriptorFactory.fromResource(
                        R.mipmap.ic_marker))
                .title(c.getRua())
                .snippet(c.getCidade()));

        configuraPosicao(map, latLng);

        map.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                Double lat = marker.getPosition().latitude;
                Double longe = marker.getPosition().longitude;
                pessoaTask = new PessoaTask(lat, longe);

                if (pessoaTask.getStatus() == AsyncTask.Status.RUNNING) {
                    pessoaTask.execute((Void) null);
                }
                return true;
            }
        });
    }

    private void configuraPosicao(
            GoogleMap map, LatLng latLng) {

        map.setMapType(GoogleMap.MAP_TYPE_TERRAIN);
        map.animateCamera(
                CameraUpdateFactory.newLatLngZoom(latLng, 17.0f));
    }

    class PessoaTask extends AsyncTask<Void, Void, ListPessoas> {

        Double lat;
        Double longe;

        public PessoaTask(Double lat, Double longe) {
            this.lat = lat;
            this.longe = longe;
        }

        @Override
        protected ListPessoas doInBackground(Void... params) {
            OkHttpClient client = new OkHttpClient();

            ListPessoas pessoas = null;

            Request request = new Request.Builder()
                    //.url("https://dl.dropboxusercontent.com/s/7nkzh4zqyc0upe6/pessoasfinal.json?dl=0")
                    .url("https://www.doocati.com.br/tcc/webservice/mobile/detalharartistas/" + lat + "/" + longe)
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
        protected void onPostExecute(ListPessoas listPessoas) {
            super.onPostExecute(listPessoas);
            if (listPessoas != null) {
                Pessoa pessoa = listPessoas.getPessoas().get(0);
                Intent i = new Intent(getApplicationContext(), DetalhePessoaActivity.class);
                Parcelable parcelable = Parcels.wrap(pessoa);
                i.putExtra("pessoa", parcelable);
                startActivity(i);
            }
        }
    }
}
