package br.com.projeto.projetolistaartistas;

import android.os.Bundle;
import android.support.v4.app.*;

import com.google.android.gms.common.*;
import com.google.android.gms.maps.*;
import com.google.android.gms.maps.model.*;

/**
 * Created by f.soares.de.farias on 11/2/2016.
 */
public class MapaActivity extends FragmentActivity {

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_mapa);

        SupportMapFragment fragment =
                (SupportMapFragment)getSupportFragmentManager()
                        .findFragmentById(R.id.map);
        GoogleMap map = fragment.getMap();

        LatLng latLng = new LatLng(-23.561706,-46.655981);
        map.addMarker(new MarkerOptions()
                .position(latLng)
                .icon(BitmapDescriptorFactory.fromResource(
                        R.drawable.common_ic_googleplayservices))
                .title("Av. Paulista")
                .snippet("São Paulo"));

        configuraPosicao(map, latLng);
    }

    private void configuraPosicao(
            GoogleMap map, LatLng latLng) {

        map.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
        map.animateCamera(
                CameraUpdateFactory.newLatLngZoom(latLng, 17.0f));
    }
}