package samdwise.justjava.com.biometricmobile;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class ViewUserDetails extends AppCompatActivity {

    public final static String USER_ID = "com.cscsnigeriaplc.cscsbiometrics.USERID";
    private TextView fName;
    private TextView mName;
    private TextView lName;
    private TextView mothMaidenName;
    private TextView gender;

    private TextView dob;
    private TextView phone;
    private TextView email;
    private TextView nationality;
    private TextView state;
    private TextView chn1;
    private TextView welcome;
    private TextView chn2;
    private TextView bank;
    private TextView bankSort;
    private TextView bankAccount;
    private TextView image;
    private Button btnBack;
    private Button print;
    private ImageView mImageView;
    private TextView homeAdd;
    private TextView contAdd;
    private TextView marital;
    private TextView children;
    private TextView disability;
    private TextView schoolStatus;
    private TextView imageUrl;
    private String fileName;
    private String firstName;
    private String secondName;
    private String surName;


    private int _id;

    private DatabaseHandler _db;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_user_details);

        fName = (TextView) findViewById(R.id.TextFirstname);
        mName = (TextView) findViewById(R.id.TextMiddlename);
        lName = (TextView) findViewById(R.id.Textlastname);
        dob = (TextView) findViewById(R.id.Textdob);
        gender = (TextView) findViewById(R.id.Textgender);
        phone = (TextView) findViewById(R.id.Textphone);
        email = (TextView) findViewById(R.id.Textemail);
        homeAdd = (TextView) findViewById(R.id.TextHome);
        contAdd = (TextView) findViewById(R.id.TextContactAdd);
        //mothMaidenName = (TextView)findViewById(R.id.TextMother);

        marital = (TextView) findViewById(R.id.TextMarital);
        children = (TextView) findViewById(R.id.TextNoChildren);
        disability = (TextView) findViewById(R.id.TextDisabillity);
        schoolStatus = (TextView) findViewById(R.id.TextSchholStatus);
        imageUrl = (TextView) findViewById(R.id.Textimagetext);


        //btnBack = (Button) findViewById(R.id.btnBack);
        print = (Button) findViewById(R.id.print);

        welcome = (TextView) findViewById(R.id.welcome);
        image = (TextView) findViewById(R.id.Textimagetext);
        mImageView = (ImageView) findViewById(R.id.imageView1);

        Intent iin = getIntent();
        Bundle b = iin.getExtras();

        if (b != null) {
            _id = (Integer) b.get("com.cscsnigeriaplc.cscsbiometrics.USERID");
            Toast.makeText(getApplicationContext(), String.valueOf("The id is " + _id), Toast.LENGTH_LONG)
                    .show();


        }

        _db = new DatabaseHandler(this);

        InvestorPpty invest = _db.getInvestor(_id);

        if (invest != null) {

            Toast.makeText(ViewUserDetails.this, invest._fname + " registered with this ID " + _id, Toast.LENGTH_SHORT).show();
            fName.setText(invest._fname);
            mName.setText(invest._mname);
            lName.setText(invest._lname);

            dob.setText(invest._dob);

            gender.setText(invest._gender);
            phone.setText(invest._phoneNumber);
            email.setText(invest._email);
            homeAdd.setText(invest._homeAddress);
            contAdd.setText(invest._contactAddress);
            //mothMaidenName = (TextView)findViewById(R.id.TextMother);

            marital.setText(invest._marital);
            children.setText(invest._children);
            disability.setText(invest._disablity);
            schoolStatus.setText(invest._schoolStatus);
            imageUrl.setText(invest._imageUrl);


            welcome.setText("Profile for " + invest._fname + " " + invest._lname);
            image.setText(invest._imageUrl);
            firstName = invest._fname;
            secondName = invest._mname;
            surName = invest._lname;
            //fileName
            File f = new File(invest._imageUrl);
            Bitmap bb = null;
            try {
                bb = BitmapFactory.decodeStream(new FileInputStream(f));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            // ImageView img=(ImageView)findViewById(R.id.imgPicker);
            mImageView.setImageBitmap(bb);

            Toast.makeText(ViewUserDetails.this, invest._imageUrl + " is the image URL " + _id, Toast.LENGTH_SHORT).show();

        } else {
            Toast.makeText(ViewUserDetails.this, "It is empty big time", Toast.LENGTH_SHORT).show();
        }

        print.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // Intent intent = new Intent(getApplicationContext(), VerificationHome.class);
                // intent.putExtra(EXTRA_MESSAGE, _id);
                //intent.putExtra(USER_ID, _id);
                //startActivity(intent);
                saveBitmap(takeScreenshot());
            }
        });

    }
    public Bitmap takeScreenshot() {
        View rootView = findViewById(android.R.id.content).getRootView();
        rootView.setDrawingCacheEnabled(true);
        return rootView.getDrawingCache();
    }

    public void saveBitmap(Bitmap bitmap) {

        File imagePath = new File(Environment.getExternalStorageDirectory() +"/Pictures/User"+_id+".png");
        FileOutputStream fos;
        try {
            fos = new FileOutputStream(imagePath);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            fos.flush();
            fos.close();
            Toast.makeText(ViewUserDetails.this, "Printed to your phone", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(getApplicationContext(), VerificationHome.class);
            //intent.putExtra(EXTRA_MESSAGE, _id);
            intent.putExtra(USER_ID, _id);
            startActivity(intent);
        } catch (FileNotFoundException e) {
            Log.e("GREC", e.getMessage(), e);
        } catch (IOException e) {
            Log.e("GREC", e.getMessage(), e);
        }}






}
