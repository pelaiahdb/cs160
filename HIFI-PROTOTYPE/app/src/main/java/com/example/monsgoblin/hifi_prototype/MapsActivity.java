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
import android.widget.ImageButton;
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
import java.util.Timer;
import java.util.TimerTask;

import static android.R.id.*;
import static com.example.monsgoblin.hifi_prototype.R.id.map;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, GoogleMap.OnMapLongClickListener {

    private GoogleMap mMap;
    private Marker meYou;
    private ArrayList<MarkerWrapper> fakes;
    private boolean b1state = false;
    private boolean b2state = false;
    private boolean b3state = false;
    private LatLng myLocation = new LatLng(37.870352, -122.259724);
    private boolean b4state = false;
    private boolean b5state = false;
    private boolean b7state = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(map);
        mapFragment.getMapAsync(this);


        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        LayoutInflater inflater = this.getLayoutInflater();
        alertDialog.setView(inflater.inflate(R.layout.custom, null));

/* When positive (yes/ok) is clicked */
        alertDialog.setPositiveButton("SIGN IN", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog,int which) {
                askDest(); // Your custom code
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




        final ImageView jbutton1 = (ImageView) findViewById(R.id.button1);
        final ImageView jbutton2 = (ImageView) findViewById(R.id.button2);
        final ImageView jbutton3 = (ImageView) findViewById(R.id.button3);
        ImageView jbutton4 = (ImageView) findViewById(R.id.button4);
        ImageView jbutton5 = (ImageView) findViewById(R.id.button5);
        ImageView jbutton6 = (ImageView) findViewById(R.id.button6);
        ImageView jbutton7 = (ImageView) findViewById(R.id.button7);

//vis
        jbutton1.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                b1state = !b1state;
                if(b1state)
                    jbutton1.setImageResource(R.drawable.invtoall);
                else
                    jbutton1.setImageResource(R.drawable.vistoall);
            }
        });
        //gen
        jbutton2.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                b2state = !b2state;
                if(b2state) {
                    jbutton2.setImageResource(R.drawable.samegen);
                    for (int i = 0; i < fakes.size(); i++) {
                        if (!fakes.get(i).gender.equals("female"))
                            fakes.get(i).temp.setVisible(false);
                    }
                }
                else {
                    jbutton2.setImageResource(R.drawable.allgen);
                    for (int i = 0; i < fakes.size(); i++) {
                        if (!fakes.get(i).gender.equals("female"))
                            fakes.get(i).temp.setVisible(true);
                    }
                }
            }
        });
        //dir
        jbutton3.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                b3state = !b3state;
                if(b3state) {
                    jbutton3.setImageResource(R.drawable.samedir);
                    for (int i = 0; i < fakes.size(); i++) {
                        fakes.get(i).temp.setVisible(false);
                    }
                }

                else {
                    jbutton3.setImageResource(R.drawable.showall);
                    for (int i = 0; i < fakes.size(); i++) {
                        fakes.get(i).temp.setVisible(true);
                    }
                }
            }
        });
        jbutton4.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(myLocation, 17.5f));
                b4state = !b4state;
                final RippleBackground rippleBackground=(RippleBackground)findViewById(R.id.content1);
                if(b4state) {
                    rippleBackground.startRippleAnimation();
                }
                else
                    rippleBackground.stopRippleAnimation();
            }
        });
        //sched
        jbutton5.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(myLocation, 17.5f));
                b4state = !b4state;
                final RippleBackground rippleBackground1=(RippleBackground)findViewById(R.id.content2);
                if(b4state) {
                    rippleBackground1.startRippleAnimation();
                }
                else
                    rippleBackground1.stopRippleAnimation();
            }
        });

        //rep
        jbutton6.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(myLocation, 17.5f));
                b4state = !b4state;
                final RippleBackground rippleBackground2=(RippleBackground)findViewById(R.id.content3);
                if(b4state) {
                    rippleBackground2.startRippleAnimation();
                    askAlert();
                }
                else
                    rippleBackground2.stopRippleAnimation();
            }
        });
        //watch
        jbutton7.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(myLocation, 17.5f));
                b4state = !b4state;
                final RippleBackground rippleBackground3=(RippleBackground)findViewById(R.id.content4);
                if(b4state) {
                    rippleBackground3.startRippleAnimation();
                }
                else
                    rippleBackground3.stopRippleAnimation();
            }
        });


    }

    public void askDest() {
        AlertDialog.Builder alertDialog2 = new AlertDialog.Builder(this);
        LayoutInflater inflater2 = this.getLayoutInflater();
        alertDialog2.setView(inflater2.inflate(R.layout.custom2, null));

/* When positive (yes/ok) is clicked */
        alertDialog2.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog,int which) {
                dialog.cancel(); // Your custom code
            }
        });

/* When negative (No/cancel) button is clicked*/
        alertDialog2.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel(); // Your custom code
            }
        });
        alertDialog2.show();
    }

    public void askAlert() {
        AlertDialog.Builder alertDialog3 = new AlertDialog.Builder(this);
        LayoutInflater inflater3 = this.getLayoutInflater();
        alertDialog3.setView(inflater3.inflate(R.layout.custom3, null));

/* When positive (yes/ok) is clicked */
        alertDialog3.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog,int which) {
                dialog.cancel(); // Your custom code
            }
        });

/* When negative (No/cancel) button is clicked*/
        alertDialog3.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel(); // Your custom code
            }
        });
        alertDialog3.show();
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
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(myLocation, 17.5f));
        meYou = mMap.addMarker(new MarkerOptions()

                    .position(myLocation).title("You")
                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.meyou)));
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
        mMap.setOnMapLongClickListener(this);
        /*
        LoginDialogFragment baby = new LoginDialogFragment();
        baby.getShowsDialog();
*/

        //new Thread(p).start();

        final Handler h = new Handler();
        final int delay = 100; //milliseconds

        h.postDelayed(new Runnable(){
            public void run(){
                //do something
                Random mizer = new Random();
                for (int i = 0; i < fakes.size(); i++) {
                    LatLng temp = (LatLng) fakes.get(i).temp.getPosition();
                    LatLng temp2 = new LatLng(temp.latitude+.00001*(mizer.nextBoolean() ? -1 : 1), temp.longitude+.00001*(mizer.nextBoolean() ? -1 : 1));
                    fakes.get(i).temp.setPosition(temp2);
                }
                h.postDelayed(this, delay);
            }
        }, delay);


    }

    public class MarkerWrapper {
        public Marker temp;
        public String gender;
    }


    @Override
    public void onMapLongClick(LatLng point) {
        mMap.addMarker(new MarkerOptions()
                .position(point)
                .title("Alert")
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.genericalert)));
        askAlert();
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
