package samdwise.justjava.com.biometricmobile;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Akinrinde on 3/21/2016.
 */
public class EnrollmentCreate extends Activity implements OnClickListener {
    Button getButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.enrollment_home);

        int recordCount = new DatabaseHandler(this).getContactsCount();
        TextView textViewRecordCount = (TextView) findViewById(R.id.textViewRecordCount);
        textViewRecordCount.setText(recordCount + " records found.");

        getButton = (Button)findViewById(R.id.buttonNewEnrollment);
        readRecords();

        final Intent intent = new Intent(this, Enroll1.class);
        getButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                String message = "From Login";
                //intent.putExtra(EXTRA_MESSAGE, message);
                startActivity(intent);

            }
        });


    }

    public void readRecords() {

        LinearLayout linearLayoutRecords = (LinearLayout) findViewById(R.id.linearLayoutRecords);
        linearLayoutRecords.removeAllViews();

        List<InvestorPpty> enrollMember = new DatabaseHandler(this).getAllContacts();

        if (enrollMember.size() > 0) {

            for (InvestorPpty obj : enrollMember) {


                int id = obj._userId;
                String firstName = obj._fname;
                String lastName = obj._lname;


                String textViewContents = String.valueOf(id)+" - "+firstName + " - " + lastName;

                TextView textViewEnrollmentItem= new TextView(this);
                textViewEnrollmentItem.setPadding(0, 10, 0, 10);
                textViewEnrollmentItem.setText(textViewContents);
                textViewEnrollmentItem.setTag(Integer.toString(id));

               // textViewEnrollmentItem.setOnLongClickListener(new OnLongClickListenerEnrollmentRecord());

                linearLayoutRecords.addView(textViewEnrollmentItem);
            }

        }

        else {

            TextView locationItem = new TextView(this);
            locationItem.setPadding(8, 8, 8, 8);
            locationItem.setText("No records yet.");

            linearLayoutRecords.addView(locationItem);
        }

    }

    @Override
    public void onClick(View v) {

    }

}
