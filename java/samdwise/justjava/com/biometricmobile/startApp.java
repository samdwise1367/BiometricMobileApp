package samdwise.justjava.com.biometricmobile;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import samdwise.justjava.com.biometricmobile.R;
import samdwise.justjava.com.biometricmobile.homeActivity;

public class startApp extends Activity implements View.OnClickListener, LocationListener {
    ListView listView;
    private static double latitude;
    private static double longitude;
    LocationManager locationManager;
    Location location;
    AppLocationService appLocationService;
    private DatabaseHandler _db;
    private static final String TAG = "LocationActivity";
    int response;
    String locationAddress1;
    String completeAddress;
    private EditText username = null;
    public final static String EXTRA_MESSAGE = "MESSAGE";
    private EditText password = null;

    //private TextView attempts;
    private Button admin;
    private Button launch;
    int id,count;
    //int counter = 3;

    public String getLocationAddress1() {
        return locationAddress1;
    }

    public void setLocationAddress1(String locationAddress1) {
        this.locationAddress1 = locationAddress1;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.startlayout);

        _db = new DatabaseHandler(this);
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            return;
        }
        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 20000, 1, this);
        if (locationManager != null) {

            location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER); //<5>

            if (location != null) {
                Log.d(TAG, location.toString());
                //this.onLocationChanged(location); //<6>
                latitude = location.getLatitude();
                longitude = location.getLongitude();


                Log.d(TAG, "onLocationChanged with location " + location.toString());
                String text = String.format("Lat:\t %f\nLong:\t %f\nAlt:\t %f\nBearing:\t %f", location.getLatitude(),
                        location.getLongitude(), location.getAltitude(), location.getBearing());

               // Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
                Geocoder geocoder;
                List<Address> addresses;
                geocoder = new Geocoder(this, Locale.getDefault());
                try {
                    addresses = geocoder.getFromLocation(latitude, longitude, 1);
                    String address = addresses.get(0).getAddressLine(0); // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()
                    String city = addresses.get(0).getLocality();
                    String state = addresses.get(0).getAdminArea();
                    String country = addresses.get(0).getCountryName();
                    completeAddress = address+" "+city+" "+state+" "+country;

                   /* Toast.makeText(getBaseContext(), "Location is "+address+" "+city+" "+state+" "+country,
                            Toast.LENGTH_SHORT).show();*/

                } catch (IOException e) {
                    e.printStackTrace();
                }


                LocationAddress locationAddress = new LocationAddress();
                //locationAddress.getAddressFromLocation(latitude, longitude,
                       // getApplicationContext(), new GeocoderHandler());




            } else {

                Toast.makeText(this, "GPS not detected", Toast.LENGTH_SHORT).show();
            }
        }else{
            Toast.makeText(this, "Location Manager is null itself", Toast.LENGTH_SHORT).show();
        }



        admin = (Button) findViewById(R.id.btnAdmin);//btnLogin
        launch = (Button) findViewById(R.id.btnApp);

        admin.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), AdminLoginActivity.class);
                startActivity(intent);
            }
        });

        launch.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "Your location is "+completeAddress.replace("null","").trim()+",Kindly wait while i check if you can use this application in ths location"
                        , Toast.LENGTH_SHORT).show();
                readRecords(completeAddress.replace("null","").trim());



            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.login, menu);
        return true;
    }

    public void readRecords(String addressSearch) {

        List<SaveLocation> enrollMember = new DatabaseHandler(this).getAllAddress();
        String addressGet[] = new String[enrollMember.size()];
        if (enrollMember.size() > 0) {
            id = 1;
            count = 0;
            for (SaveLocation obj : enrollMember) {
                String address = obj.get_address();
                if(addressSearch.equals(address)){
                    count++;
                    Toast.makeText(getBaseContext(), "Address Has been find",
                            Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                    startActivity(intent);
                }else if((id == enrollMember.size()-1) && !addressSearch.equals(address) ){
                    Toast.makeText(getApplicationContext(), "Sorry you cannot use this application in this location, Contact Administrator", Toast.LENGTH_SHORT).show();
                }


                id++;

            }




        }else{
            Toast.makeText(getApplicationContext(), "Sorry you cannot use this application in this location, Contact Administrator", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onLocationChanged(Location location) {

    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

        Toast.makeText(getBaseContext(), "Gps is turned on!! ",
                Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onProviderDisabled(String provider) {
        Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
        startActivity(intent);
        Toast.makeText(getBaseContext(), "Gps is turned off!! ",
                Toast.LENGTH_SHORT).show();

    }


    @Override
    public void onClick(View v) {

    }

    private class GeocoderHandler extends Handler {
        @Override
        public void handleMessage(Message message) {
            String locationAddress;
            switch (message.what) {
                case 1:
                    Bundle bundle = message.getData();
                    locationAddress = bundle.getString("address");
                    break;
                default:
                    locationAddress = null;
            }
            //_db = new DatabaseHandler(this);
            if(locationAddress.equals(null)){
                Toast.makeText(getBaseContext(), "Location services not available",
                        Toast.LENGTH_SHORT).show();

            }else{
                //locationAddress1.
                //locationAddress1  = locationAddress;
                setLocationAddress1(locationAddress);

            }

        }
    }
}

