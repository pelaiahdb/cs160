package com.example.monsgoblin.hifi_prototype;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.widget.Button;


import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.Marker;
import com.skyfishjy.library.RippleBackground;

import java.util.Random;
import java.util.ArrayList;
import java.util.List;

import static android.R.id.*;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private Marker meYou;
    private ArrayList<MarkerWrapper> fakes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        Button onlyButton1 = (Button) findViewById(button1);
        Button onlyButton2 = (Button) findViewById(button2);
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        fakes = new ArrayList<>();

        mMap = googleMap;

        MapStyleOptions style;
        style = MapStyleOptions.loadRawResourceStyle(this, R.raw.style_json);

        mMap.setMapStyle(style);

        // Add a marker for You and move the camera

        LatLng myLocation = new LatLng(37.870352, -122.259724);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(myLocation, 17.5f));
        final RippleBackground rippleBackground=(RippleBackground)findViewById(R.id.content);
        rippleBackground.startRippleAnimation();
        meYou = mMap.addMarker(new MarkerOptions().position(myLocation).title("You"));
        meYou.showInfoWindow();

        Random mizer = new Random();
        int rainbows = 100;

        for (int i = 0; i < rainbows; i++) {
            int gender = mizer.nextInt(21);
            float angle = 360*mizer.nextFloat();
            int ready = mizer.nextInt(3);
            Marker tempFake = mMap.addMarker(new MarkerOptions()
                    .position(new LatLng(37.870352 + mizer.nextInt(20 + 1)*.0001*(mizer.nextBoolean() ? -1 : 1), -122.259724 + mizer.nextInt(20 + 1)*.0001*(mizer.nextBoolean() ? -1 : 1)))
                    .title("TO: Somewhere")
                    .icon(BitmapDescriptorFactory.defaultMarker(angle))
                    .snippet("Hi nice to meet u"));
            MarkerWrapper fakeWrapper = new MarkerWrapper();
            fakeWrapper.temp = tempFake;
            if (gender == 0)
                fakeWrapper.gender = "other";
            else if (gender < 11)
                fakeWrapper.gender = "female";
            else
                fakeWrapper.gender = "male";
            fakes.add(fakeWrapper);
            tempFake.setRotation(angle);

        }




    }

    public class MarkerWrapper {
        public Marker temp;
        public String gender;
    }
/*
    public class FireMissilesDialogFragment extends DialogFragment {
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the Builder class for convenient dialog construction
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setMessage(R.string.dialog_fire_missiles)
                    .setPositiveButton(R.string.fire, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            // FIRE ZE MISSILES!
                        }
                    })
                    .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            // User cancelled the dialog
                        }
                    });
            // Create the AlertDialog object and return it
            return builder.create();
        }
    }
*/
}
