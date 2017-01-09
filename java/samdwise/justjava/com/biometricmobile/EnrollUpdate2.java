package samdwise.justjava.com.biometricmobile;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

/**
 * Created by anochirionye.emilia on 3/9/2016.
 */
public class EnrollUpdate2 extends Activity {

    private String scontAdd;
    private String sTel;
    private String sEmail;
    private String sblood;
    private String smarital = null;

    private EditText contAdd;
    private EditText tel;
    private EditText email;
    private EditText blood;
    private Spinner marital = null;

    public final static String EXTRA_MESSAGE = "com.cscsnigeriaplc.cscsbiometrics.MESSAGE2";
    public final static String USER_ID = "com.cscsnigeriaplc.cscsbiometrics.USERID";
    private Button cont;
    private int _id;
    private int userEnrollmentId;

    private DatabaseHandler _db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_enroll2);

        Bundle b = getIntent().getExtras();
        userEnrollmentId = b.getInt("enrollmentId");

        contAdd = (EditText) findViewById(R.id.EditTextcontactAdd);
        tel = (EditText) findViewById(R.id.editTextTelephone);
        email = (EditText) findViewById(R.id.editTextEmail);
        email = (EditText) findViewById(R.id.editTextEmail);
        blood = (EditText) findViewById(R.id.editTextBlood);
        marital = (Spinner) findViewById(R.id.SpinnerMarital);


        cont = (Button) findViewById(R.id.btnCont2);
        cont.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NextPage(v);
                // showAlert("Passed id="+_id);
            }
        });

       /* Intent iin = getIntent();
        Bundle b = iin.getExtras();
*/
       /* if (b != null) {
            _id = (Integer) b.get("com.cscsnigeriaplc.cscsbiometrics.USERID");

        }*/
        _db = new DatabaseHandler(this);

        InvestorPpty invest = _db.getInvestor(userEnrollmentId);
        //fName.setText(invest.get_fname());
        // mName.setText(invest.get_mname());
        //lName.setText(invest.get_lname());
        //mothMaidenName.setText(invest.get_mothMaidenname());
        tel.setText(invest.get_phoneNumber());
        email.setText(invest.get_email());

    }

    private void NextPage(View v) {
        scontAdd = contAdd.getText().toString();
        sTel = tel.getText().toString();
        String blank = "null";
        sEmail = email.getText().toString();
        sblood = blood.getText().toString();
        smarital = marital.getSelectedItem().toString().toString();

        if (sTel != null && !sTel.isEmpty()) {

            InvestorPpty pp = _db.getInvestor(_id);
            //InvestorPpty pp = _db.getInvestor(7);

            InvestorPpty ppNew = new InvestorPpty(pp.get_userId(),pp.get_fname(), pp.get_mname(), pp.get_lname(),
                    pp.get_dob(), pp.get_homeAddress(), pp.get_gender(),pp.get_latitude(),pp.get_longitude(),scontAdd,
                    sTel, sEmail, sblood, smarital, pp.get_children(), pp.get_disablity(),
                    pp.get_schoolStatus(), pp.get_education(), pp.get_houseHoldType(), pp.get_sizeHousehold(),
                    pp._imageUrl, pp._dateAdded,pp._uniqueId,pp._accountType);

            int rows = _db.updateContact(ppNew);
            showAlert("rows = " + rows);
            Intent intent = new Intent(this, Enroll3.class);
            //String message = "From Login";
            intent.putExtra(USER_ID, _id);
            startActivity(intent);

        } else {
            showAlert("Date of Birth cannot be empty");
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.enroll2, menu);
        return true;
    }

    private void showAlert(String messg) {
        //
        Toast.makeText(getApplicationContext(), messg, Toast.LENGTH_LONG)
                .show();
    }

}
