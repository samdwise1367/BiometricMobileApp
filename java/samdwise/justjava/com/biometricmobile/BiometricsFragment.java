package samdwise.justjava.com.biometricmobile;

import com.precisebiometrics.android.mtk.api.bio.PBBiometry;

import android.app.AlertDialog;
import android.app.Dialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;


import com.precisebiometrics.android.mtk.api.bio.PBBiometry;

/**
 * Created by anochirionye.emilia on 3/9/2016.
 */
public abstract class BiometricsFragment extends Fragment implements Constants{
    protected PBBiometry bio;

    /**
     * Override this function if your fragment need to perform any specific
     * action when the biometrics is activated/initialized.
     *
     * @param bio
     *            The biometrics object used to performed biometric operations.
     */
    public void biometricsActivated(PBBiometry bio) {
    }

    /**
     * Override this function if your fragment need to perform any specific
     * action when the biometrics is deactivated/uninitialized.
     */
    public void biometricsDeactivated() {
    }

    /**
     * Publish an error to the user.
     *
     * @param error
     *            The error message to publish.
     */
    protected void publishError(final String error) {
        showInfoDialog(error, R.string.error);
        Log.e(Constants.LOG_TAG, error);
    }

    /**
     * Show an information dialog to the user.
     *
     * @param messageResId
     *            String resource Id for message to display.
     * @param titleResId
     *            String resource Id for the title of the dialog.
     */
    protected void showInfoDialog(int messageResId, int titleResId) {
        showInfoDialog(getString(messageResId), titleResId);
    }

    /**
     * Show an information dialog to the user. If possible it is recommended to
     * use {@link #showInfoDialog(int, int)} in favor of this function.
     *
     * @param message
     *            Message to display.
     * @param titleResId
     *            String resource Id for the title of the dialog.
     */
    protected void showInfoDialog(final String message, final int titleResId) {
        if (getActivity() != null) {
            getActivity().runOnUiThread(new Runnable() {

                @Override
                public void run() {
                    AlertDialog.Builder builder = new AlertDialog.Builder(
                            getActivity());
                    builder.setMessage(message).setTitle(titleResId)
                            .setPositiveButton(R.string.ok, null);
                    builder.create().show();
                }
            });
        }

    }
    private DatabaseHandler _db;
    InvestorPpty _pp ;
    protected void showCustomizedDialog(final String message, final int userId) {
        if (getActivity() != null) {
            _db = new DatabaseHandler(getActivity());
            _pp = _db.getInvestor(userId);
            String msg="Successfully verified User with ";


            getActivity().runOnUiThread(new Runnable() {

                @Override
                public void run() {
                    final Dialog dialog = new Dialog(getActivity());
                    dialog.setContentView(R.layout.custom_dialog);
                    dialog.setTitle("Successful Verification");

                    TextView textFullNames = (TextView) dialog.findViewById(R.id.textViewVerifyFullnames);
                    String tempText=textFullNames.getText().toString();
                    textFullNames.setText(tempText+ _pp.get_fname()+" "+
                            _pp.get_lname()+" "+_pp.get_mname());

                    TextView textTel = (TextView) dialog.findViewById(R.id.textViewVerifyTelephone);
                    tempText=textTel.getText().toString();
                    textTel.setText(tempText+": "+ _pp.get_phoneNumber());



                    Bitmap bmp = BitmapFactory.decodeFile(_pp.get_imageUrl());
                    ImageView img=(ImageView) dialog.findViewById(R.id.imageViewVeifyImg);
                    img.setImageBitmap(bmp);


                    Button dialogButton = (Button) dialog.findViewById(R.id.buttonVerifyDetails);
                    // if button is clicked, close the custom dialog
                    dialogButton.setOnClickListener(new OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog.dismiss();
                        }
                    });


                    dialog.show();


//					AlertDialog.Builder builder = new AlertDialog.Builder(
//							getActivity());
//					builder.setMessage(message).setTitle(titleResId)
//							.setPositiveButton(R.string.ok, null);
//					builder.create().show();
                }
            });
        }
    }
}
