package samdwise.justjava.com.biometricmobile;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.precisebiometrics.android.mtk.api.bio.PBBiometry;
import com.precisebiometrics.android.mtk.api.bio.PBBiometryException;
import com.precisebiometrics.android.mtk.biometrics.BiometryConstants;
import com.precisebiometrics.android.mtk.biometrics.PBBiometryEnrollConfig;
import com.precisebiometrics.android.mtk.biometrics.PBBiometryFinger;
import com.precisebiometrics.android.mtk.biometrics.PBBiometryUser;
import com.precisebiometrics.android.mtk.biometrics.PBBiometryVerifyConfig;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import samdwise.justjava.com.biometricmobile.BiometricsFragment;
import samdwise.justjava.com.biometricmobile.EnrollDialog;
import samdwise.justjava.com.biometricmobile.LocalDatabase;
import samdwise.justjava.com.biometricmobile.PracticeDialog;
import samdwise.justjava.com.biometricmobile.R;
import samdwise.justjava.com.biometricmobile.VerifyDialog;

public class VerifyFramentHome extends BiometricsFragment {
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

    private int id;
    /** Tells if the interaction widgets should be enabled or not. */
    private boolean widgetsEnabled = false;
    private DatabaseHandler _db;

    private static final int ACTION_TAKE_PHOTO_B = 1;

    // Button b;
    private static final String BITMAP_STORAGE_KEY = "viewbitmap";
    private static final String IMAGEVIEW_VISIBILITY_STORAGE_KEY = "imageviewvisibility";
    private ImageView mImageView;
    private TextView mTextView;
    // private TextView mTextView2;
    // private TextView mTextView3;
    private Bitmap mImageBitmap;
    private Button finish;

    private String mCurrentPhotoPath;

    private static final String JPEG_FILE_PREFIX = "IMG_";
    private static final String JPEG_FILE_SUFFIX = ".jpg";

    private AlbumStorageDirFactory mAlbumStorageDirFactory = null;

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

    //private EditText fName;
    //private EditText mName;
    //private EditText lName;
    private EditText homeAdd;
    //private Spinner gender;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.activity_view_user_details,
                container, false);

        verifyButton = (Button) rootView.findViewById(R.id.verifyButton);

        fName = (TextView) rootView.findViewById(R.id.TextFirstname);
        mName =  (TextView)rootView.findViewById(R.id.TextMiddlename);
        lName = (TextView)rootView.findViewById(R.id.Textlastname);
        //mothMaidenName = (TextView)rootView.findViewById(R.id.TextMother);
        gender = (TextView)rootView.findViewById(R.id.Textgender);
        phone = (TextView)rootView.findViewById(R.id.Textphone);
        email = (TextView)rootView.findViewById(R.id.Textemail);
        //nationality = (TextView)rootView.findViewById(R.id.Textnational);
        //state = (TextView)rootView.findViewById(R.id.Textstate);
        //chn1 = (TextView)rootView.findViewById(R.id.Textchn1);
        //chn2 = (TextView)rootView.findViewById(R.id.Textchn2);
        //bank = (TextView)rootView.findViewById(R.id.Textbnk);
        //bankSort = (TextView)rootView.findViewById(R.id.Textbnksort);
        //bankAccount = (TextView)rootView.findViewById(R.id.Textbnacc);
        dob = (TextView)rootView.findViewById(R.id.Textdob);
        btnBack = (Button)rootView.findViewById(R.id.btnBack);
        welcome = (TextView)rootView.findViewById(R.id.welcome);
        image = (TextView)rootView.findViewById(R.id.Textimagetext);
        homeAdd = (EditText) rootView.findViewById(R.id.EditTextHomeAdd);
        //gender = (Spinner) rootView.findViewById(R.id.spinner1);
        mImageView = (ImageView) rootView.findViewById(R.id.imageView1);

        verifyLocal(getView());

        /*verifyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(),String.valueOf(id)+" is the userId",Toast.LENGTH_LONG);
                verifyLocal(v);
            }
        });*/

      //  BioExampleVerify getBio = (BioExampleVerify)getActivity();
       // id = Integer.parseInt(getBio.getId());

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
            /*new Thread(new Runnable() {

                @Override
                public void run() {
                    PBBiometryVerifyConfig config = new PBBiometryVerifyConfig();
                    try {
                        LocalDatabase db = LocalDatabase.getInstance();

                        _db = new DatabaseHandler(getContext());


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



                            InvestorPpty invest = _db.getInvestor( verFinger.getUser().getId());
                            //String firstName = invest._fname;

                            fName.setText(invest._fname);
                            mName.setText(invest._mname);
                            lName.setText(invest._lname);
                            mothMaidenName.setText(invest._mothMaidenname);
                            gender.setText(invest._gender);
                            phone.setText(invest._phoneNumber);
                            email.setText(invest._email);
                            nationality.setText(invest._nationality);
                            state.setText(invest._stateOfOrigin);
                            chn1.setText(invest._pChn);
                            chn2.setText(invest._sChn);
                            bank.setText(invest._bankName);
                            bankSort.setText(invest._bankSortCode);
                            bankAccount.setText(invest._bankAccountNo);
                            dob.setText(invest._dob);

                            welcome.setText("Profile for "+invest._fname+" "+invest._lname);
                            image.setText(invest._imageUrl);


                            File f=new File(invest._imageUrl);
                            Bitmap bb = BitmapFactory.decodeStream(new FileInputStream(f));
                            // ImageView img=(ImageView)findViewById(R.id.imgPicker);
                            mImageView.setImageBitmap(bb);
                           // mImageView.setImageBitmap(bb);








                            *//*if (verFinger.getUser().getId() == id) {
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
                            }*//*
                        }
                    } catch (PBBiometryException e) {
                        publishError(e.getErrorDisplayName());
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    } finally {
                        if (dialog != null) {
                            dialog.dismiss();
                        }
                    }
                }
            }).start();*/
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
