package samdwise.justjava.com.biometricmobile;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

/**
 * Created by Akinrinde on 5/25/2016.
 */
public class ViewLocation extends Activity implements View.OnClickListener {
    Button getButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.location_home);

        int recordCount = new DatabaseHandler(this).getLocationCount();
        TextView textViewRecordCount = (TextView) findViewById(R.id.textViewRecordCount);
        textViewRecordCount.setText(recordCount + " records found.");

       // getButton = (Button)findViewById(R.id.buttonNewEnrollment);
        readRecords();



    }

    public void readRecords() {

        LinearLayout linearLayoutRecords = (LinearLayout) findViewById(R.id.linearLayoutRecords);
        linearLayoutRecords.removeAllViews();

        List<SaveLocation> enrollMember = new DatabaseHandler(this).getAllAddress();
        String addressGet[] = new String[enrollMember.size()];
        if (enrollMember.size() > 0) {
            int id = 1;
            for (SaveLocation obj : enrollMember) {

                String address = obj.get_address();
                String first[] = address.split(" ");


                Toast.makeText(getBaseContext(), address,
                        Toast.LENGTH_SHORT).show();
                if(first[4].contains("null")){
                    first[4] = first[4].replace("null","");

                }

                String textViewContents = String.valueOf(id)+" - "+first[0]+" "+first[1]+" "+first[2]+" "+first[3]+" "+first[4];

                TextView textViewEnrollmentItem= new TextView(this);
                textViewEnrollmentItem.setPadding(0, 10, 0, 10);
                textViewEnrollmentItem.setText(textViewContents);
                textViewEnrollmentItem.setTag(Integer.toString(id));

                // textViewEnrollmentItem.setOnLongClickListener(new OnLongClickListenerEnrollmentRecord());

                linearLayoutRecords.addView(textViewEnrollmentItem);
                id++;
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
