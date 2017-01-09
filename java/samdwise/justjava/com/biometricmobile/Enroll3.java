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
public class Enroll3 extends Activity {
    private EditText nChildren;
    private Spinner nDisability = null;
    private Spinner nschStatus = null;
    private Spinner neducation = null;
    private Spinner nhousehold = null;
    private Spinner nsizehousehold = null;



    private String sChildren ;
    private String sDisability = null;
    private String sSchStatus = null;
    private String sEducation = null;
    private String sHousehold = null;
    private String sSizeHousehold = null;



    private Button cont;
    public final static String EXTRA_MESSAGE = "com.cscsnigeriaplc.cscsbiometrics.MESSAGE";
    public final static String USER_ID = "com.cscsnigeriaplc.cscsbiometrics.USERID";

    private int _id;
    private DatabaseHandler _db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enroll3);

        nChildren = (EditText) findViewById(R.id.editTextNoChildren);
        nDisability = (Spinner) findViewById(R.id.spinnerDisability);
        nschStatus = (Spinner) findViewById(R.id.spinnerSchoolStatus);
        neducation = (Spinner) findViewById(R.id.spinnerEducation);
        nhousehold = (Spinner) findViewById(R.id.spinnerHousehold);
        nsizehousehold = (Spinner) findViewById(R.id.sizeofhousehold);

        cont = (Button) findViewById(R.id.btnContinue3);
        cont.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NextPage(v);
            }
        });

        Intent iin = getIntent();
        Bundle b = iin.getExtras();

        if (b != null) {
            _id = (Integer) b.get("com.cscsnigeriaplc.cscsbiometrics.USERID");

        }
        _db = new DatabaseHandler(this);
    }
    private void NextPage(View v) {

        sChildren = nChildren.getText().toString();
        String blank = "null";
        sDisability = nDisability.getSelectedItem().toString();
        sSchStatus = nschStatus.getSelectedItem().toString();
        sEducation = neducation.getSelectedItem().toString();
        sHousehold = nhousehold.getSelectedItem().toString();
        sSizeHousehold = nsizehousehold.getSelectedItem().toString();


        if (sChildren != null) {

           // if (sBankAccountNo != null && !sBankAccountNo.isEmpty()) {


                InvestorPpty pp = _db.getInvestor(_id);

                InvestorPpty ppNew = new InvestorPpty(pp.get_userId(),
                        pp.get_fname(), pp.get_mname(), pp.get_lname(),
                        pp.get_dob(), pp.get_homeAddress(), pp.get_gender(),pp.get_latitude(),pp.get_longitude(),pp.get_contactAddress(),
                        pp.get_phoneNumber(), pp.get_email(), pp.get_blood(),
                        pp.get_marital(),sChildren,sDisability,sSchStatus, sEducation, sHousehold, sSizeHousehold, blank,pp.get_dateAdded(), blank,blank);
                int rows = _db.updateContact(ppNew);
                showAlert("rows = " + rows);

                Intent intent = new Intent(this, BioExampleActivity.class);
                String message = "From Login";
                //intent.putExtra(EXTRA_MESSAGE, message);
                intent.putExtra(USER_ID, _id);
                startActivity(intent);

            }
            else {
               // showAlert("Bank Account Number cannot be empty");
            }
        }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.enroll3, menu);
        return true;
    }
    private void showAlert(String messg) {
        //
        Toast.makeText(getApplicationContext(), messg, Toast.LENGTH_LONG)
                .show();
    }

}
