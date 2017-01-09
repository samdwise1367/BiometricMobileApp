package samdwise.justjava.com;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

import samdwise.justjava.com.biometricmobile.BioExampleVerify;
import samdwise.justjava.com.biometricmobile.CameraActivity2;
import samdwise.justjava.com.biometricmobile.DatabaseHandler;
import samdwise.justjava.com.biometricmobile.LocationAddressHome;
import samdwise.justjava.com.biometricmobile.R;
import samdwise.justjava.com.biometricmobile.VerifyInfoActivity;
import samdwise.justjava.com.biometricmobile.ViewUserDetails;
import samdwise.justjava.com.biometricmobile.homeActivity;

public class VerifyBiometricHome extends AppCompatActivity {

    public final static String USER_ID = "com.cscsnigeriaplc.cscsbiometrics.USERID";
    private int _id;
    private DatabaseHandler _db;

    private Button verify;
    private Button btnHome;

    String tempID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bio_example2);

        _db = new DatabaseHandler(this);

        verify = (Button) findViewById(R.id.btnVerify);
        btnHome = (Button) findViewById(R.id.btnHome);


        btnHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), homeActivity.class);
                // intent.putExtra(EXTRA_MESSAGE, _id);
                //intent.putExtra(USER_ID, _id);
                startActivity(intent);
            }
        });


        verify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //BioExampleVerify.class
                Intent intent = new Intent(getApplicationContext(),  BioExampleVerify.class);
                //intent.putExtra(EXTRA_MESSAGE, _id);
                intent.putExtra(USER_ID, _id);
                startActivity(intent);
            }
        });




    }



}
