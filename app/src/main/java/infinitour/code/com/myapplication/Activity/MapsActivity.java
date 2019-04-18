package infinitour.code.com.myapplication.Activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Point;
import android.location.Location;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import java.util.*;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polygon;
import com.google.android.gms.maps.model.PolygonOptions;
import com.google.android.gms.maps.model.PolylineOptions;

import java.lang.reflect.Array;
import java.util.ArrayList;

import infinitour.code.com.myapplication.R;

import static infinitour.code.com.myapplication.constants.constants.MAPVIEW_BUNDLE_KEY;

public class MapsActivity extends AppCompatActivity implements OnMapReadyCallback {
    private MapView mMapView;
    private GoogleMap mMap;
    double longit,latit;
    private static final String TAG = "MapsActivity";
    ArrayList<LatLng>points_of_markers;
    Button removemarkers;
    Button draw;
    Button gotoinfo;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        Bundle mapViewBundle = null;
        if (savedInstanceState != null) {
            mapViewBundle = savedInstanceState.getBundle(MAPVIEW_BUNDLE_KEY);
        }
        points_of_markers=new ArrayList<>();
        mMapView = (MapView) findViewById(R.id.map);
        mMapView.onCreate(mapViewBundle);
        mMapView.getMapAsync(this);
        longit= getIntent().getDoubleExtra("long",0.0);
        latit= getIntent().getDoubleExtra("lat",0.0);
        Log.d(TAG,"YOOOOOOOOOOOOOOOOOOur lond is"+longit);
        Log.d(TAG,"YOOOOOOOOOOOOOOOOOOur lat is"+longit);
        removemarkers=(Button)findViewById(R.id.btn_map_remove_markers);
        removemarkers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mMap.clear();
                setmylocation(mMap);
                points_of_markers.clear();
            }
        });
        draw=(Button)findViewById(R.id.btn_map_draw);
        draw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            draw_polygon(points_of_markers);

            }
        });
        gotoinfo=(Button)findViewById(R.id.btn_map_goto);
        gotoinfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MapsActivity.this,info_Activity.class);
                intent.putExtra("area",get_area(find_sides(points_of_markers)));
                startActivity(intent);
            }
        });

    }

    public double[] find_sides( ArrayList<LatLng>points_of_markers){
        double[]sides=new double[5];
        for(int i=0;i<3;i++) {
            float[] results = new float[1];
            Location.distanceBetween(points_of_markers.get(i).latitude, points_of_markers.get(i).longitude, points_of_markers.get(i+1).latitude, points_of_markers.get(i+1).longitude, results);
       sides[i]=results[0];
        }
        float[] results = new float[1];
        Location.distanceBetween(points_of_markers.get(3).latitude, points_of_markers.get(3).longitude, points_of_markers.get(1).latitude, points_of_markers.get(1).longitude, results);
        sides[3]=results[0];

        Location.distanceBetween(points_of_markers.get(0).latitude, points_of_markers.get(0).longitude, points_of_markers.get(2).latitude, points_of_markers.get(2).longitude, results);
        sides[4]=results[0];
        return sides;
    }

    public double get_area(double[]sides){
      double area=0;
        double a=sides[0];double b=sides[1];double c=sides[2];double d=sides[3];double e=sides[4];
        double x= Math.pow(a,2)+Math.pow(d,2)+Math.pow(e,2);
        double y= Math.pow(a,4)+Math.pow(d,4)+Math.pow(e,4);
        double w= Math.pow(b,2)+Math.pow(c,2)+Math.pow(e,2);
        double z= Math.pow(b,4)+Math.pow(c,4)+Math.pow(e,4);
        area=(Math.pow(Math.pow(x,2)-2*y,0.5)+Math.pow(Math.pow(w,2)-2*z,0.5))/4;
    return area;}




    public void draw_polygon(ArrayList<LatLng>points){
        PolygonOptions options=new PolygonOptions().fillColor(Color.argb(100, 49, 101, 187))
                .strokeColor(Color.argb(255, 49, 101, 187));
        for(int i=0;i<points.size();i++){
            options.add(points.get(i));
        }
        mMap.addPolygon(options);





    }
    public void setmylocation(GoogleMap map){
        map.addMarker(new MarkerOptions().position(new LatLng(getIntent().getDoubleExtra("lat",0.0),getIntent().getDoubleExtra("long",0.0)))
                .title("Your Location"));
    }

    public void setmarkers(LatLng latLng){
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(latLng);
        markerOptions.title(latLng.latitude + " : " + latLng.longitude);
        mMap.addMarker(markerOptions);
        points_of_markers.add(latLng);


    }




    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        Bundle mapViewBundle = outState.getBundle(MAPVIEW_BUNDLE_KEY);
        if (mapViewBundle == null) {
            mapViewBundle = new Bundle();
            outState.putBundle(MAPVIEW_BUNDLE_KEY, mapViewBundle);
        }

        mMapView.onSaveInstanceState(mapViewBundle);
    }


    @Override
    protected void onResume() {
        super.onResume();
        mMapView.onResume();
    }

    @Override
    protected void onStart() {
        super.onStart();
        mMapView.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mMapView.onStop();
    }

    @Override
    public void onMapReady(GoogleMap map) {
        mMap=map;
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(
                new LatLng(getIntent().getDoubleExtra("lat",0.0),
                        getIntent().getDoubleExtra("long",0.0)),17));
        setmylocation(map);

       // map.addMarker(new MarkerOptions().position(new LatLng(getIntent().getDoubleExtra("lat",0.0),getIntent().getDoubleExtra("long",0.0)))
         //       .title("Your Location"));
        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
               setmarkers(latLng);
            }
        });


    }

    @Override
    protected void onPause() {
        mMapView.onPause();
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        mMapView.onDestroy();
        super.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mMapView.onLowMemory();
    }


}
