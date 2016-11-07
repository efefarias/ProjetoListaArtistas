package br.com.projeto.projetolistaartistas;

/**
 * Created by f.soares.de.farias on 11/7/2016.
 */
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.*;

import com.google.android.gms.common.*;
import com.google.android.gms.maps.*;
import com.google.android.gms.maps.model.*;

import br.com.projeto.projetolistaartistas.model.Coordenadas;

public class MapaActivity extends FragmentActivity {

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
    }

    private void configuraPosicao(
            GoogleMap map, LatLng latLng) {

        map.setMapType(GoogleMap.MAP_TYPE_TERRAIN);
        map.animateCamera(
                CameraUpdateFactory.newLatLngZoom(latLng, 17.0f));
    }
}
