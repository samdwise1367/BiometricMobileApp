package samdwise.justjava.com.biometricmobile;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

public class VerificationHome extends AppCompatActivity {

    public final static String USER_ID = "com.cscsnigeriaplc.cscsbiometrics.USERID";
    private int _id;
    private DatabaseHandler _db;

    private Button verify;
    private Button locate;
    private Button picture;
    private Button details;
    private Button btnHome;

    String tempID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verification_home);

        Intent iin = getIntent();
        Bundle b = iin.getExtras();

        if (b != null) {
            _id =  (Integer)b.get(USER_ID);

        }
        _db = new DatabaseHandler(this);

        verify = (Button) findViewById(R.id.btnVerify);
        locate = (Button) findViewById(R.id.btnShowLocation);
        picture = (Button) findViewById(R.id.btnPicture);
        details = (Button) findViewById(R.id.btnDetails);
        btnHome = (Button) findViewById(R.id.btnHome);

        details.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ViewDetailPage(v);
                // showAlert("Passed id="+_id);
            }
        });

        btnHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), homeActivity.class);
                // intent.putExtra(EXTRA_MESSAGE, _id);
                //intent.putExtra(USER_ID, _id);
                startActivity(intent);
            }
        });

        locate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), LocationAddressHome.class);
                //intent.putExtra(EXTRA_MESSAGE, _id);
                intent.putExtra(USER_ID, _id);
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

        picture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), CameraActivity2.class);
                //intent.putExtra(EXTRA_MESSAGE, _id);
                intent.putExtra(USER_ID, _id);
                startActivity(intent);
            }
        });



    }

    private void ViewDetailPage(View v) {
        Intent intent =new Intent(this, ViewUserDetails.class);

        intent.putExtra(USER_ID,_id);
        startActivity(intent);


    }

}
