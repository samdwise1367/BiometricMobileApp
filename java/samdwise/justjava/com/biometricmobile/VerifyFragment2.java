package samdwise.justjava.com.biometricmobile;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.precisebiometrics.android.mtk.api.bio.PBBiometry;
import com.precisebiometrics.android.mtk.api.bio.PBBiometryException;
import com.precisebiometrics.android.mtk.biometrics.BiometryConstants;
import com.precisebiometrics.android.mtk.biometrics.PBBiometryEnrollConfig;
import com.precisebiometrics.android.mtk.biometrics.PBBiometryFinger;
import com.precisebiometrics.android.mtk.biometrics.PBBiometryUser;
import com.precisebiometrics.android.mtk.biometrics.PBBiometryVerifyConfig;

import samdwise.justjava.com.biometricmobile.BiometricsFragment;
import samdwise.justjava.com.biometricmobile.EnrollDialog;
import samdwise.justjava.com.biometricmobile.LocalDatabase;
import samdwise.justjava.com.biometricmobile.PracticeDialog;
import samdwise.justjava.com.biometricmobile.R;
import samdwise.justjava.com.biometricmobile.VerifyDialog;

public class VerifyFragment2 extends BiometricsFragment {
    /** Holds the button "Ver Finger" */
    private Button enrollFingerButton;
    /** Holds the button "Delete Finger" */
    private Button deleteFingerButton;
    /** Holds the button "Verify" */
    private Button verifyButton;
    /** Holds the button "Practice" */
    private Button practiceButton;
    /** Holds the radio group for finger selection */
    private RadioGroup radioGroup;

    public final static String USER_ID = "com.cscsnigeriaplc.cscsbiometrics.USERID";

    private int id;
    /** Tells if the interaction widgets should be enabled or not. */
    private boolean widgetsEnabled = false;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_verify2,
                container, false);

        verifyButton = (Button) rootView.findViewById(R.id.verifyButton);

        verifyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(),String.valueOf(id)+" is the userId",Toast.LENGTH_LONG);
                verifyLocal(v);
            }
        });

        BioExampleVerify getBio = (BioExampleVerify)getActivity();
        id = Integer.parseInt(getBio.getId());

       // Toast.makeText(this, String.valueOf(id), Toast.LENGTH_LONG).show();


        return rootView;
    }

    @Override
    public void biometricsActivated(PBBiometry bio) {
        this.bio = bio;
        widgetsEnabled = true;
        setWidgetsEnabled();
    }

    @Override
    public void biometricsDeactivated() {
        bio = null;
        widgetsEnabled = false;
        setWidgetsEnabled();
    }

    /**
     * Called when the delete button is pressed in the Local Database section.
     * Deletes the template for the finger that is selected in the GUI. If
     * that finger is not enrolled an error will be displayed.
     *
     * @param view The button that was pressed.
     */
    public void deleteLocal(View view) {
        if (bio != null) {
            try {
                LocalDatabase.getInstance().deleteTemplate(getSelectedFinger());
                showInfoDialog(R.string.delete_successful, R.string.info);
            } catch (PBBiometryException e) {
                publishError(e.getErrorDisplayName());
            }
        }
    }

    /**
     * Called when the enroll button is pressed in the Local Database section.
     * Performs a one finger image enrollment of the finger that is selected
     * in the GUI. Enrolled template is stored in the locally implemented
     * database.
     *
     * @param view The button that was pressed.
     */

    /**
     * Called when the verify button is pressed in the Local Database section.
     * Performs a verification against all the templates that are enrolled in
     * the locally implemented database.
     *
     * @param view The button that was pressed.
     */
    public void verifyLocal(View view) {
        if (bio != null) {
            final VerifyDialog dialog = new VerifyDialog(getActivity(),
                    bio);
            dialog.show();
            new Thread(new Runnable() {

                @Override
                public void run() {
                    PBBiometryVerifyConfig config = new PBBiometryVerifyConfig();
                    try {
                        LocalDatabase db = LocalDatabase.getInstance();

                        if(db.getEnrolledFingers() == null || db.getEnrolledFingers().isEmpty())
                            return;

                        // Perform verification and receive the verified finger
                        PBBiometryFinger verFinger =
                                bio.verifyFingers(
                                        // We wants to verify against all
                                        // enrolled fingers
                                        db.getEnrolledFingers(),
                                        // Use the local database for
                                        // template storage
                                        db,
                                        // The GUI functionality is
                                        // handled by the verify dialog.
                                        dialog,
                                        // We just standard verifier which
                                        // is most common
                                        null,
                                        // Default configuration is used
                                        config,
                                        // We use our own GUI so GUI
                                        // configuration is useless here
                                        null);

                        // No exception, so operation was successful
                        // But if the returned finger was null it means that
                        // the finger did not match.
                        if (verFinger == null) {
                            showInfoDialog(R.string.no_match,
                                    R.string.info);
                        } else {
                           /* if (verFinger.getUser().getId() == id) {
                                int fingerNum = 1;
                                if (verFinger.getPosition() !=
                                        PBBiometryFinger.PBFingerPosition.PBFingerPositionLeftIndex) {
                                    fingerNum = 2;
                                }
                                showInfoDialog(getString(R.string.finger_verified) +
                                                " " + fingerNum + " for user " + verFinger.getUser().getId(),
                                        R.string.info);
                            }else{
                                showInfoDialog(R.string.no_match,
                                        R.string.info);
                            }*/

                            showInfoDialog(getString(R.string.finger_verified),
                                    R.string.info);

                            Intent intent = new Intent(getContext(), ViewUserDetails.class);
                            // intent.putExtra(EXTRA_MESSAGE, _id);
                            intent.putExtra(USER_ID, verFinger.getUser().getId());
                            startActivity(intent);


                        }
                    } catch (PBBiometryException e) {
                        publishError(e.getErrorDisplayName());
                    } finally {
                        if (dialog != null) {
                            dialog.dismiss();
                        }
                    }
                }
            }).start();
        }else{
            showInfoDialog("You have not registered yet",
                    R.string.info);

        }
    }

    /**
     * Get the finger that is selected in the GUI (in LocalDatabaseFragment).
     *
     * Note! that we here has chosen to represent the restricted two finger use
     * in the example app with exactly the left index and left middle fingers.
     *
     * @return The selected finger.
     */
    private PBBiometryFinger getSelectedFinger() {
        int id = radioGroup.getCheckedRadioButtonId();
        // The finger positions here are simply randomly chosen for the example
        // app only.
        if (id == R.id.finger1) {
            return new PBBiometryFinger(PBBiometryFinger.PBFingerPosition.PBFingerPositionLeftIndex,
                    new PBBiometryUser(EXAMPLE_USER_ID));
        } else {
            return new PBBiometryFinger(PBBiometryFinger.PBFingerPosition.PBFingerPositionLeftMiddle,
                    new PBBiometryUser(EXAMPLE_USER_ID));
        }
    }

    /**
     * Enables/disables the widgets in this fragment.
     * It is enabled/disabled based on the value of the {@link #widgetsEnabled}
     * variable.
     */
    private void setWidgetsEnabled() {

        if (verifyButton != null) {
            verifyButton.setEnabled(widgetsEnabled);
        }

    }
}
