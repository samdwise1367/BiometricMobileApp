package samdwise.justjava.com.biometricmobile;

/**
 * Created by samdwise on 3/27/16.
 */
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.Settings;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class LocationAddressHome extends Activity {

    Button btnGPSShowLocation;
    Button btnShowAddress;
    TextView tvAddress;
    private int _id;
    private DatabaseHandler _db;

    public final static String USER_ID = "com.cscsnigeriaplc.cscsbiometrics.USERID";
    public static double latitude;
    public static double longitude;

    AppLocationService appLocationService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my);
        tvAddress = (TextView) findViewById(R.id.tvAddress);
        appLocationService = new AppLocationService(
                LocationAddressHome.this);


        Intent iin = getIntent();
        Bundle b = iin.getExtras();

        if (b != null) {
            _id = (Integer) b.get("com.cscsnigeriaplc.cscsbiometrics.USERID");
            Toast.makeText(getApplicationContext(), String.valueOf("The id is " + _id), Toast.LENGTH_LONG)
                    .show();



        }

        _db = new DatabaseHandler(this);

        InvestorPpty invest = _db.getInvestor(_id);

        if(invest!= null){

            latitude = Double.parseDouble(invest._latitude);
            longitude = Double.parseDouble(invest._longitude);
        }


        //double latitude1 = 7.4447614;
        //double longitude1 = 3.8597119;

        //if (location != null) {
        //double latitude1 = location.getLatitude();
        //double longitude = location.getLongitude();
        LocationAddress locationAddress = new LocationAddress();
        locationAddress.getAddressFromLocation(latitude, longitude,
                getApplicationContext(), new GeocoderHandler());}



    public void showSettingsAlert() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(
                LocationAddressHome.this);
        alertDialog.setTitle("SETTINGS");
        alertDialog.setMessage("Enable Location Provider! Go to settings menu?");
        alertDialog.setPositiveButton("Settings",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(
                                Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                        LocationAddressHome.this.startActivity(intent);
                    }
                });
        alertDialog.setNegativeButton("Cancel",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
        alertDialog.show();
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
            tvAddress.setText(locationAddress);
        }
    }
}
