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
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import samdwise.justjava.com.VerifyBiometricHome;


public class AdminApp extends Activity implements View.OnClickListener, LocationListener {

    ListView listView;
    private static double latitude;
    private static double longitude;
    LocationManager locationManager;
    Location location;
    AppLocationService appLocationService;
    private DatabaseHandler _db;
    private static final String TAG = "LocationActivity";
    int response;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        appLocationService = new AppLocationService(
                AdminApp.this);
        // Show location button click listener
        listView = (ListView) findViewById(R.id.list);
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

                Toast.makeText(this, text, Toast.LENGTH_SHORT).show();


            } else {

                Toast.makeText(this, "GPS not detected", Toast.LENGTH_SHORT).show();
            }
        }else{
            Toast.makeText(this, "Location Manager is null itself", Toast.LENGTH_SHORT).show();
        }


        String[] values = new String[]{"Track Location","View Tracked Location","Back to Home"};

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, android.R.id.text1, values);

        // Assign adapter to ListView
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {

                // ListView Clicked item index
                int itemPosition = position;

                // ListView Clicked item value
                String itemValue = (String) listView
                        .getItemAtPosition(position);

               if (itemPosition == 0) {

                   Geocoder geocoder;
                   List<Address> addresses;
                   geocoder = new Geocoder(getApplicationContext(), Locale.getDefault());
                   try {
                       addresses = geocoder.getFromLocation(latitude, longitude, 1);
                       String address = addresses.get(0).getAddressLine(0); // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()
                       String city = addresses.get(0).getLocality();
                       String state = addresses.get(0).getAdminArea();
                       String country = addresses.get(0).getCountryName();
                       String completeAddress = address+" "+city+" "+state+" "+country;
                       int i;
                       i = readRecords(completeAddress.replace("null","").trim());
                       if(i>0){
                           Toast.makeText(getBaseContext(), "Location cannot be save more than once, Already saved",
                                   Toast.LENGTH_SHORT).show();
                       }else{
                           Toast.makeText(getBaseContext(), "Location is "+address+" "+city+" "+state+" "+country,
                                   Toast.LENGTH_SHORT).show();

                           SaveLocation saveLocation = new SaveLocation(completeAddress);
                           response =  _db.addAddress(saveLocation);

                           Toast.makeText(getBaseContext(), "Location tracked, ID is "+String.valueOf(response),
                                   Toast.LENGTH_SHORT).show();

                       }


                   } catch (IOException e) {
                       e.printStackTrace();
                   }

                }
                else if (itemPosition == 1) {

                    Intent intent = new Intent(view.getContext(), ViewLocation.class);
                    startActivity(intent);

                    // finish();

                }else if (itemPosition == 2) {

                    Intent intent = new Intent(view.getContext(), startApp.class);
                    startActivity(intent);

                    // finish();

                }

            }

        });
    }

    public int readRecords(String addressSearch) {
        int count = 0;
        List<SaveLocation> enrollMember = new DatabaseHandler(this).getAllAddress();

        if (enrollMember.size() > 0) {
            int id = 1;

            for (SaveLocation obj : enrollMember) {
                String address = obj.get_address();
                if(addressSearch.equals(address)){
                    count++;
                }


                id++;
            }


        }
        return count;
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


}
/**
 * Method to display the location on UI
 * */


