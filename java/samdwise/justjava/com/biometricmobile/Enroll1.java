package samdwise.justjava.com.biometricmobile;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;
import java.util.List;


public class Enroll1 extends Activity implements OnClickListener, LocationListener {

    public final static String EXTRA_MESSAGE = "MESSAGE";
    public final static String USER_ID = "com.cscsnigeriaplc.cscsbiometrics.USERID";
    private ImageButton ib;

    private Button cont;
    private Calendar cal;
    private int day;
    private int month;
    private int year;
    private EditText et;
    private DatabaseHandler _db;

    private EditText fName;
    private EditText mName;
    private EditText lName;
    private EditText homeAdd;
    private Spinner gender;

    private TextView par;

    private String sfName;
    private String smName;
    private String slName;
    private String shomeAdd;
    private String sgender;
    private String sdob;

    private int _id;
    private int userEnrollmentId;

    LocationManager locationManager;
    Location location;
    //<2>
    //Geocoder geocoder; //<3>
    TextView locationText;
    private static double latitude;
    private static double longitude;


    private static final String TAG = "LocationActivity";

    //MapView map;
    //MapController mapController; //<4>

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enroll1);

        //locationManager = (LocationManager) this.getSystemService(LOCATION_SERVICE); //<2>

        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 20000, 1, this);


        //geocoder = new Geocoder(this); //<3>

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


        ib = (ImageButton) findViewById(R.id.imageButton1);
        cont = (Button) findViewById(R.id.btnContinue);
        cal = Calendar.getInstance();
        day = cal.get(Calendar.DAY_OF_MONTH);
        month = cal.get(Calendar.MONTH);
        year = cal.get(Calendar.YEAR);
        et = (EditText) findViewById(R.id.editText);
        par = (TextView) findViewById(R.id.textView1);

        fName = (EditText) findViewById(R.id.editTextFirstname);
        mName = (EditText) findViewById(R.id.editTextMiddlename);
        lName = (EditText) findViewById(R.id.editTextLastname);
        homeAdd = (EditText) findViewById(R.id.EditTextHomeAdd);
        gender = (Spinner) findViewById(R.id.spinner1);

        ib.setOnClickListener(this);

        _db = new DatabaseHandler(this);

        cont.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NextPage(v);
            }
        });

        _id = _db.getLastId();
        _contact = _db.getContactsCount();

        ++_id;


    }

    int _contact;

    private void NextPage(View v) {
        sfName = fName.getText().toString();
        String blank = "null";
        smName = mName.getText().toString();
        slName = lName.getText().toString();
        shomeAdd = homeAdd.getText().toString();
        sgender = gender.getSelectedItem().toString();
        sdob = et.getText().toString();
        if (sfName != null && !sfName.isEmpty()) {

            if (slName != null && !slName.isEmpty()) {

                if (!sdob.trim().equalsIgnoreCase("Date of Birth")) {


                        int d = cal.get(Calendar.DAY_OF_MONTH);
                        int m = cal.get(Calendar.MONTH);
                        int y = cal.get(Calendar.YEAR);
                        int h = cal.get(Calendar.HOUR);
                        int min = cal.get(Calendar.MINUTE);
                        int s = cal.get(Calendar.SECOND);
                        int milli = cal.get(Calendar.MILLISECOND);

                        showAlert("Max Id = " + _id);
                        showAlert("conatct count = " + _contact);
                        String date = AddZero(y) + "-" + AddZero(m) + "-" + AddZero(d) + " "
                                + AddZero(h) + ":" + AddZero(min)
                                + ":" + AddZero(s) + "." + AddZero(milli);

                        InvestorPpty pp = new InvestorPpty(sfName, smName, slName,
                                sdob, shomeAdd, sgender,String.valueOf(latitude),String.valueOf(longitude), blank, blank,
                                blank, blank,blank, blank, blank, blank, blank, blank,
                                blank,blank,date, blank, blank);
                        int userId = _db.addContact(pp);
                        showAlert("userId is  = " + userId);
                        List<InvestorPpty> contacts = _db.getAllContacts();


                        List<FingerprintPpty> f = _db.getAllFingers();


                        Intent intent = new Intent(this, Enroll2.class);
                        // intent.putExtra(EXTRA_MESSAGE, _id);
                        intent.putExtra(USER_ID, userId);
                        startActivity(intent);



                } else {
                    showAlert("Date of Birth cannot be empty");
                }

            } else {
                showAlert("Last Name cannot be empty");
            }

        } else {
            showAlert("First Name cannot be empty");
        }

        // par.setText(" sfName= " + sfName + " smName= " + smName + " slName= "
        // + slName + " smothMaidenName= " + smothMaidenName
        // + " sgender= " + sgender + " sdob= " + sdob);

    }

    private String AddZero(int num) {
        String ret = "";
        if (num < 10) {
            ret = "0" + num;
        } else {
            ret ="" + num;
        }

        return ret;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.enroll1, menu);
        return true;
    }

    @Override
    public void onClick(View arg0) {
        showDialog(0);

    }

    @Override
    @Deprecated
    protected Dialog onCreateDialog(int id) {
        return new DatePickerDialog(this, datePickerListener, year, month, day);
    }

    private DatePickerDialog.OnDateSetListener datePickerListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int selectedYear,
                              int selectedMonth, int selectedDay) {
            et.setText(selectedDay + " / " + (selectedMonth + 1) + " / "
                    + selectedYear);
        }
    };

    private void showAlert(String messg) {
        //
        Toast.makeText(getApplicationContext(), messg, Toast.LENGTH_LONG)
                .show();
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
}
