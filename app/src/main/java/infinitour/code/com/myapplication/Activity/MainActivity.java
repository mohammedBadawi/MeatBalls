package infinitour.code.com.myapplication.Activity;

import android.Manifest;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.spark.submitbutton.SubmitButton;

import java.util.ArrayList;

import infinitour.code.com.myapplication.Models.Country;
import infinitour.code.com.myapplication.R;

import static infinitour.code.com.myapplication.constants.constants.ERROR_DIALOG_REQUEST;
import static infinitour.code.com.myapplication.constants.constants.PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION;
import static infinitour.code.com.myapplication.constants.constants.PERMISSIONS_REQUEST_ENABLE_GPS;

public class MainActivity extends AppCompatActivity {
    SubmitButton gotomap;
    DatabaseReference myRef;
    double  longitude,latitude;
    public boolean mLocationPermissionGranted = false;
    private static final String TAG = "MainActivity";
    private FusedLocationProviderClient mfusedlocationclient;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();

        mfusedlocationclient = LocationServices.getFusedLocationProviderClient(this);
        myRef = firebaseDatabase.getReference("Country");
        gotomap = (SubmitButton) findViewById(R.id.btn_main_go_to_map);
        gotomap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, MapsActivity.class);

                intent.putExtra("long",longitude);
                intent.putExtra("lat",latitude);
                Thread timer = new Thread() {
                    public void run(){
                        try {
                            Thread.sleep(3100);
                            Intent intent = new Intent(MainActivity.this, MapsActivity.class);
                            intent.putExtra("long",longitude);
                            intent.putExtra("lat",latitude);
                            startActivity(intent);
                                finish();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }

                    }
                };
                timer.start();


                // show_dialog();
            }


        });

    }

    private void getlastlocation() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            return;
        }
        mfusedlocationclient.getLastLocation().addOnCompleteListener(new OnCompleteListener<Location>() {
            @Override
            public void onComplete(@NonNull Task<Location> task) {
                if(task.isSuccessful()){
                    Location location=task.getResult();
                     longitude=location.getLongitude();
                     latitude=location.getLatitude();


                }
            }
        });
    }

    private boolean checkMapServices() {
        if (isServicesOK()) {
            if (isMapsEnabled()) {
                return true;
            }
        }
        return false;
    }

    private void buildAlertMessageNoGps() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("This application requires GPS to work properly, do you want to enable it?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(@SuppressWarnings("unused") final DialogInterface dialog, @SuppressWarnings("unused") final int id) {
                        Intent enableGpsIntent = new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                        startActivityForResult(enableGpsIntent, PERMISSIONS_REQUEST_ENABLE_GPS);
                    }
                });
        final AlertDialog alert = builder.create();
        alert.show();
    }

    public boolean isMapsEnabled() {
        final LocationManager manager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        if (!manager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            buildAlertMessageNoGps();
            return false;
        }
        return true;
    }

    private void getLocationPermission() {
        /*
         * Request location permission, so that we can get the location of the
         * device. The result of the permission request is handled by a callback,
         * onRequestPermissionsResult.
         */
        if (ContextCompat.checkSelfPermission(this.getApplicationContext(),
                android.Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            mLocationPermissionGranted = true;
            getlastlocation();
            Toast.makeText(MainActivity.this, "Use the app", Toast.LENGTH_LONG).show();
            // getChatrooms();
        } else {
            ActivityCompat.requestPermissions(this,
                    new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                    PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);
        }
    }

    public boolean isServicesOK() {
        Log.d(TAG, "isServicesOK: checking google services version");

        int available = GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(MainActivity.this);

        if (available == ConnectionResult.SUCCESS) {
            //everything is fine and the user can make map requests
            Log.d(TAG, "isServicesOK: Google Play Services is working");
            return true;
        } else if (GoogleApiAvailability.getInstance().isUserResolvableError(available)) {
            //an error occured but we can resolve it
            Log.d(TAG, "isServicesOK: an error occured but we can fix it");
            Dialog dialog = GoogleApiAvailability.getInstance().getErrorDialog(MainActivity.this, available, ERROR_DIALOG_REQUEST);
            dialog.show();
        } else {
            Toast.makeText(this, "You can't make map requests", Toast.LENGTH_SHORT).show();
        }
        return false;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String permissions[],
                                           @NonNull int[] grantResults) {
        mLocationPermissionGranted = false;
        switch (requestCode) {
            case PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    mLocationPermissionGranted = true;
                    getlastlocation();

                }
            }
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d(TAG, "onActivityResult: called.");
        switch (requestCode) {
            case PERMISSIONS_REQUEST_ENABLE_GPS: {
                if(mLocationPermissionGranted){
                    Toast.makeText(MainActivity.this, "Use the app", Toast.LENGTH_LONG).show();
                    getlastlocation();

                }
                else{
                    getLocationPermission();
                }
            }
        }

    }


    void show_dialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        final View view = MainActivity.this.getLayoutInflater().inflate(R.layout.add_country_box, null);

        FloatingActionButton add_city = view.findViewById(R.id.fb_add_city);
        final ArrayList<String> cities = new ArrayList();
        add_city.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText city = (EditText) view.findViewById(R.id.et_city_name);
                String cityname = city.getText().toString();
                if (cityname.isEmpty()) {
                    city.setError("Enter city name");
                } else {
                    cities.add(cityname);
                    Toast.makeText(MainActivity.this, cityname + " is added", Toast.LENGTH_SHORT).show();
                    city.getText().clear();
                }
            }
        });
        Button add = view.findViewById(R.id.add_button);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText et_name = (EditText) view.findViewById(R.id.country_name);
                String name = et_name.getText().toString();
                EditText et_flag = (EditText) view.findViewById(R.id.et_flag_url);
                String flag_url = et_flag.getText().toString();
                if (name.isEmpty()) {
                    Toast.makeText(MainActivity.this, "Empty", Toast.LENGTH_LONG).show();
                } else {
                    String id = myRef.push().getKey();
                    Country new_country = new Country(id, name, flag_url);
                    myRef.child(id).setValue(new_country);
                    myRef.child(id).child("cities").setValue(cities);
                    Toast.makeText(MainActivity.this, name + " is added", Toast.LENGTH_SHORT).show();

                }
            }
        });


        builder.setView(view);
        builder.create();
        builder.show();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (checkMapServices()) {
            if (mLocationPermissionGranted) {
                Toast.makeText(MainActivity.this, "Use the app", Toast.LENGTH_LONG).show();
                getlastlocation();
                // getChatrooms();
            } else {
                getLocationPermission();
            }
        }
    }
}
