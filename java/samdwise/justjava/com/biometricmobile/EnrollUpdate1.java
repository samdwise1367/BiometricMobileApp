package samdwise.justjava.com.biometricmobile;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
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


public class EnrollUpdate1 extends Activity implements OnClickListener {
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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enroll1);

        Bundle b = getIntent().getExtras();
        userEnrollmentId = b.getInt("enrollmentId");

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

       // if(enrollmentId > 0){
            //fName.setText("Hello");
            InvestorPpty invest = _db.getInvestor(userEnrollmentId);
            fName.setText(invest.get_fname());
            mName.setText(invest.get_mname());
            lName.setText(invest.get_lname());
            //mothMaidenName.setText(invest.get_mothMaidenname());
            //shomeAdd


        //}
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


                        InvestorPpty old  = _db.getInvestor(userEnrollmentId);

                        InvestorPpty ppUpdate = new InvestorPpty(sfName, smName, slName,
                                sdob, shomeAdd, sgender,old.get_latitude(),old.get_longitude(), old.get_contactAddress(),
                                old.get_phoneNumber(), old.get_email(), old.get_blood(),old.get_marital(),old.get_children(),
                                old.get_disablity(),old.get_schoolStatus(),old.get_education(),old.get_houseHoldType(),old.get_sizeHousehold(),
                                old._imageUrl,old._dateAdded,old._uniqueId,old._accountType);
                        int id =  _db.updateContact(ppUpdate);
                        Intent intent = new Intent(this, EnrollUpdate2.class);
                        // intent.putExtra(EXTRA_MESSAGE, _id);
                        intent.putExtra(USER_ID, userEnrollmentId);
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

}
