package samdwise.justjava.com.biometricmobile;

import android.app.Activity;
import android.os.Bundle;
import android.content.Intent;
import android.graphics.Color;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by anochirionye.emilia on 3/8/2016.
 */
public class LoginActivity extends Activity {


    private EditText username = null;
    public final static String EXTRA_MESSAGE = "MESSAGE";
    private EditText password = null;

    //private TextView attempts;
    private Button login;
    int counter = 3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        username = (EditText) findViewById(R.id.editTextUsename);
        password = (EditText) findViewById(R.id.editTextPassword);

        //attempts = (TextView) findViewById(R.id.textView4);
        //attempts.setText(Integer.toString(counter));
        login = (Button) findViewById(R.id.btnLogin);//btnLogin

        login.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                login(v);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.login, menu);
        return true;
    }

    public void login(View view) {
        if (username.getText().toString().equals("a")
                && password.getText().toString().equals("a")) {
            Toast.makeText(getApplicationContext(), "Redirecting...",
                    Toast.LENGTH_SHORT).show();
            //Intent intent = new Intent(this, BioExampleActivity.class);
            Intent intent = new Intent(this, homeActivity.class);
            String message = "From Login";
            intent.putExtra(EXTRA_MESSAGE, message);
            startActivity(intent);
        } else {
            Toast.makeText(getApplicationContext(), "Wrong Credentials",
                    Toast.LENGTH_SHORT).show();
            //	attempts.setBackgroundColor(Color.RED);
            counter--;
            //attempts.setText(Integer.toString(counter));
            if (counter == 0) {
                login.setEnabled(false);
            }

        }

    }
}
