package com.example.monsgoblin.hifi_prototype;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Handler;
import android.os.SystemClock;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.animation.BounceInterpolator;
import android.view.animation.Interpolator;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
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
    private int b1state = 0;
    private boolean b2state = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);


        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        LayoutInflater inflater = this.getLayoutInflater();
        alertDialog.setView(inflater.inflate(R.layout.custom, null));

/* When positive (yes/ok) is clicked */
        alertDialog.setPositiveButton("SIGN IN", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog,int which) {
                dialog.cancel(); // Your custom code
            }
        });

/* When negative (No/cancel) button is clicked*/
        alertDialog.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                finish(); // Your custom code
            }
        });
        alertDialog.show();



        final Dialog haha = new Dialog(this);
        // Set GUI of login screen




        final Button jbutton1 = (Button) findViewById(R.id.button1);
        final Button jbutton2 = (Button) findViewById(R.id.button2);
        Button jbutton3 = (Button) findViewById(R.id.button3);
        Button jbutton4 = (Button) findViewById(R.id.button4);
        Button jbutton5 = (Button) findViewById(R.id.button5);
        Button jbutton6 = (Button) findViewById(R.id.button6);
        Button jbutton7 = (Button) findViewById(R.id.button7);

//vis
        jbutton1.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                b1state = (b1state+1)%3;
                if (b1state == 1)
                    jbutton1.setText("INVISIBLE TO ALL");
                else if (b1state == 2)
                    jbutton1.setText("VISIBLE TO SAME GENDER");
                else
                    jbutton1.setText("VISIBLE TO ALL");
            }
        });
        //gen
        jbutton2.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                b2state = !b2state;
                for (int i = 0; i < fakes.size(); i++) {
                    if (b2state) {
                        if (!fakes.get(i).gender.equals("female"))
                            fakes.get(i).temp.setVisible(false);
                        jbutton2.setText("SAME GENDER");
                    }
                    else {
                        if (!fakes.get(i).gender.equals("female"))
                            fakes.get(i).temp.setVisible(true);
                        jbutton2.setText("ALL GENDERS");
                    }
                }
            }
        });
        //dir
        jbutton3.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                haha.show();
            }
        });
        //req
        jbutton4.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                haha.show();
            }
        });
        //sched
        jbutton5.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                haha.show();
            }
        });

        //rep
        jbutton6.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                haha.show();
            }
        });
        //watch
        jbutton7.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                haha.show();
            }
        });


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
        int rainbows = 1000;

        for (int i = 0; i < rainbows; i++) {
            int gender = mizer.nextInt(21);
            float angle = 360*mizer.nextFloat();
            int ready = mizer.nextInt(3);
            Marker tempFake = mMap.addMarker(new MarkerOptions()
                    .position(new LatLng(37.870352 + mizer.nextInt(100 + 1)*.0001*(mizer.nextBoolean() ? -1 : 1), -122.259724 + mizer.nextInt(100 + 1)*.0001*(mizer.nextBoolean() ? -1 : 1)))
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
        /*
        LoginDialogFragment baby = new LoginDialogFragment();
        baby.getShowsDialog();
*/

    }

    public class MarkerWrapper {
        public Marker temp;
        public String gender;
    }

/*
    public static class LoginDialogFragment extends DialogFragment {
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the Builder class for convenient dialog construction
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setMessage(R.string.username)
                    .setPositiveButton(R.string.password, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            // FIRE ZE MISSILES!
                        }
                    })
                    .setNegativeButton(R.string.password, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            // User cancelled the dialog
                        }
                    });
            // Create the AlertDialog object and return it
            return builder.create();
        }
    }

    void showDialog() {

        // DialogFragment.show() will take care of adding the fragment
        // in a transaction.  We also want to remove any currently showing
        // dialog, so make our own transaction and take care of that here.
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        Fragment prev = getFragmentManager().findFragmentByTag("dialog");
        if (prev != null) {
            ft.remove(prev);
        }
        ft.addToBackStack(null);

        // Create and show the dialog.
        DialogFragment newFragment = new LoginDialogFragment();
        String yo = "yo";
        newFragment.show(getFragmentManager(), yo);
    }
*/
}
