package samdwise.justjava.com.biometricmobile;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.View;

import android.os.Bundle;
import android.app.Activity;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

/**
 * Created by Akinrinde on 3/21/2016.
 */
public class OnLongClickListenerEnrollmentRecord implements View.OnLongClickListener {
    Context context;
    String id;

    @Override
    public boolean onLongClick(final View v) {
        context = v.getContext();
        id = v.getTag().toString();

        final CharSequence[] items = { "Edit", "Delete" };

        //Intent intent = new Intent(, Enroll1.class);

        new AlertDialog.Builder(context).setTitle("Enrollment Record")
                .setItems(items, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int item) {
                        if (item == 0) {
                           //editRecord(Integer.parseInt(id));
                            Intent intent = new Intent(context, EnrollUpdate1.class);
                            intent.putExtra("enrollmentId", Integer.parseInt(id));
                            //final boolean b = context.bindService(intent);
                            context.startActivity(intent);

                        }

                        dialog.dismiss();


                    }
                }).show();
        return true;
    }

}
