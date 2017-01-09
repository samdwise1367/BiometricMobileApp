package samdwise.justjava.com.biometricmobile;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
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

import samdwise.justjava.com.VerifyBiometricHome;


public class homeActivity extends Activity implements View.OnClickListener, LocationListener {

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
                homeActivity.this);
        // Show location button click listener
        listView = (ListView) findViewById(R.id.list);
        _db = new DatabaseHandler(this);
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
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


        String[] values = new String[]{"Enroll Investor","Verification via User Id", "Verification via Biometrics","Logout"};

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

                    //Intent intent = new Intent(view.getContext(), Enroll1.class);

                    Intent intent = new Intent(view.getContext(), EnrollmentCreate.class);


                    // new Intent(this, HomeActivity.class);
                    String message = "From Login";
                    //intent.putExtra(EXTRA_MESSAGE, message);
                    startActivity(intent);



                } else if (itemPosition == 1) {
                    Intent intent = new Intent(view.getContext(), VerificationActivity.class);
                    startActivity(intent);
                } else if (itemPosition == 2) {



                    Intent intent = new Intent(view.getContext(), VerifyBiometricHome.class);
                    String message = "From Login";
                    //intent.putExtra(EXTRA_MESSAGE, message);
                    startActivity(intent);

                /*} else if (itemPosition == 3) {
                    LocationAddress locationAddress = new LocationAddress();
                    locationAddress.getAddressFromLocation(latitude, longitude,
                            getApplicationContext(), new GeocoderHandler());

                    Toast.makeText(getBaseContext(), "Location Saved with ID "+response,
                            Toast.LENGTH_SHORT).show();

                }
                else if (itemPosition == 4) {

                    Intent intent = new Intent(view.getContext(), ViewLocation.class);
                    startActivity(intent);*/

                    // finish();

                }else if (itemPosition == 3) {

                    Intent intent = new Intent(view.getContext(), LoginActivity.class);
                    startActivity(intent);


                }

            }

        });
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
                SaveLocation saveLocation = new SaveLocation(locationAddress);
                response =  _db.addAddress(saveLocation);

                Toast.makeText(getBaseContext(), String.valueOf(response),
                        Toast.LENGTH_SHORT).show();

                //Toast.makeText(getBaseContext(), saveLocation.get_address(),
                       // Toast.LENGTH_SHORT).show();
            }


           // tvAddress.setText(locationAddress);
        }
    }
}
    /**
     * Method to display the location on UI
     * */


