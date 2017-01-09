package samdwise.justjava.com.biometricmobile;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Created by samdwise on 3/26/16.
 */
public class VerificationActivity extends Activity {

    public final static String USER_ID = "com.cscsnigeriaplc.cscsbiometrics.USERID";
    private EditText Id;
    private DatabaseHandler _db;
    private Button cont;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verification);

        Id = (EditText) findViewById(R.id.editId);
        cont = (Button) findViewById(R.id.btnContinue);

        cont.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NextPage(v);
            }
        });
    }

    private void NextPage(View v) {
        _db = new DatabaseHandler(this);

        InvestorPpty invest = _db.getInvestor(Integer.parseInt(Id.getText().toString()));

        if(invest!= null){

            Toast.makeText(VerificationActivity.this, invest._fname +" registered with this ID " +Id.getText().toString(), Toast.LENGTH_SHORT).show();
            Intent intent =new Intent(this, VerificationHome.class);
            String message = "From Login";

            //intent.putExtra(EXTRA_MESSAGE, message);
            intent.putExtra(USER_ID, Integer.parseInt(Id.getText().toString()));
            startActivity(intent);

        } else{
            Toast.makeText(VerificationActivity.this, "It is empty big time", Toast.LENGTH_SHORT).show();
        }

    }




    }
