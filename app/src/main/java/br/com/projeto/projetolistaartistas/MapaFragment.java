package br.com.projeto.projetolistaartistas;

import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.*;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.maps.*;
import com.google.android.gms.maps.model.*;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import br.com.projeto.projetolistaartistas.Util.FuncoesGenericas;
import br.com.projeto.projetolistaartistas.model.ListPessoas;
import br.com.projeto.projetolistaartistas.model.Pessoa;
import butterknife.ButterKnife;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by f.soares.de.farias on 11/2/2016.
 */
public class MapaFragment extends Fragment implements OnMapReadyCallback, LocationListener {

    private GoogleMap map;
    private double latitude = -8.127990;
    private double longitude = -34.914597;
    FuncoesGenericas fg = new FuncoesGenericas();
    private View layout = null;

    public void onCreate(Bundle savedInstanceState) { super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        if(layout == null) {
            layout = inflater.inflate(R.layout.layout_mapa, container, false);
        }
        ButterKnife.bind(this, layout);
        getLocation();

        SupportMapFragment fragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        fragment.getMapAsync(this);
        loadArtistClouser();

        return layout;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

        map = googleMap;
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(latitude, longitude), 13));
        map.setMapType(GoogleMap.MAP_TYPE_TERRAIN);
        map.addMarker(new MarkerOptions()
                .title("Você está aqui!")
                .snippet("Recife")
                .position(new LatLng(latitude, longitude)))
                .showInfoWindow();

    }

    @Override
    public void onLocationChanged(Location location) {
        longitude = location.getLongitude();
        latitude = location.getLatitude();
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) { }

    @Override
    public void onProviderEnabled(String provider) { }

    @Override
    public void onProviderDisabled(String provider) { }

    private void getLocation(){

        LocationManager locationManager = null;
        String mprovider = null;
        locationManager = (LocationManager) getContext().getSystemService(getContext().LOCATION_SERVICE);
        Criteria criteria = new Criteria();
        mprovider = locationManager.getBestProvider(criteria, false);
        Location location = null;
        try{
            if (mprovider != null && !mprovider.equals("")) {

                location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER)   ;
                locationManager.requestLocationUpdates(mprovider, 25000, 1, this);

                if (location != null) {
                    onLocationChanged(location);
                    return;
                }

                location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                locationManager.requestLocationUpdates(mprovider, 25000, 1, this);

                if (location != null) {
                    onLocationChanged(location);
                    return;
                }

            }
        }catch (SecurityException e) {
            Log.v("exception", e.getMessage().toString());
        }

    }

    private void loadArtistClouser(){

        final List<Pessoa> listPessoas = new ArrayList<>();

        AsyncTask<Void, Void, ListPessoas> task = new AsyncTask<Void, Void, ListPessoas>(){

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

                if(pessoas != null){
                    listPessoas.clear();
                    listPessoas.addAll(pessoas.getPessoas());
                }

                double lat = latitude;
                double lng = longitude;
                for (final Pessoa pessoa : listPessoas) {

                    //lat += 0.005;
                    //lng += 0.005;
                    if(pessoa.getAtelie().size() != 0) {
                        lat = Double.parseDouble(pessoa.getAtelie().get(0).getAte_latitude());
                        lng = Double.parseDouble(pessoa.getAtelie().get(0).getAte_longitude());


                    map.setMapType(GoogleMap.MAP_TYPE_TERRAIN);
                    map.addMarker(new MarkerOptions()
                            .title( pessoa.getUsu_nome() )
                            .position(new LatLng(lat, lng))
                            .title(pessoa.getAtelie().get(0).getAte_endereco())
                            .snippet(pessoa.getAtelie().get(0).getAte_cidade()))
                            .showInfoWindow();
                    }
                }

            }

        };
        task.execute();
    }
}
