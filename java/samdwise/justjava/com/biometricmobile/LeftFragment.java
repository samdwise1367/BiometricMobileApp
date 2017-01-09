package samdwise.justjava.com.biometricmobile;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioGroup;

import com.precisebiometrics.android.mtk.api.bio.PBBiometry;
import com.precisebiometrics.android.mtk.api.bio.PBBiometryException;
import com.precisebiometrics.android.mtk.biometrics.PBBiometryEnrollConfig;
import com.precisebiometrics.android.mtk.biometrics.PBBiometryFinger;
import com.precisebiometrics.android.mtk.biometrics.PBBiometryFinger.PBFingerPosition;
import com.precisebiometrics.android.mtk.biometrics.PBBiometryUser;


/**
 * Created by anochirionye.emilia on 3/9/2016.
 */
public class LeftFragment extends BiometricsFragment {
    private Button enrollLeftFinger;
    private Button cont;
    public final static String EXTRA_MESSAGE = "com.cscsnigeriaplc.cscsbiometrics.MESSAGE";
    private boolean widgetsEnabled = false;
    private RadioGroup radioGroup;
    private int _id;
    private DatabaseHandler _db;
    public final static String USER_ID = "com.cscsnigeriaplc.cscsbiometrics.USERID";

    private int _fingerCounter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.new_fragment_local_dbl,
                container, false);
        radioGroup = (RadioGroup) rootView.findViewById(R.id.radioGroupL);
        enrollLeftFinger = (Button) rootView.findViewById(R.id.enrollButtonLeft);
        enrollLeftFinger.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                enrollLocal(v);

            }
        });
        cont = (Button) rootView.findViewById(R.id.ButtonContinueLeft);
        cont.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NextPage(v);
            }
        });
        Intent iin = getActivity().getIntent();
        Bundle b = iin.getExtras();

        if (b != null) {
            _id = (Integer) b.get("com.cscsnigeriaplc.cscsbiometrics.USERID");

        }
        _db = new DatabaseHandler(getActivity());

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
    private void setWidgetsEnabled() {
        if (enrollLeftFinger != null) {
            enrollLeftFinger.setEnabled(widgetsEnabled);
        }

    }
    private void NextPage(View v) {

        //GetTemplates();
        Intent intent = new Intent(v.getContext(), CameraActivity.class);
        intent.putExtra(USER_ID, _id);
        startActivity(intent);

    }
    public void enrollLocal(View view) {
        if (bio != null) {
            final EnrollDialog dialog = new EnrollDialog(getActivity(), bio);
            dialog.show();
            new Thread(new Runnable() {

                @Override
                public void run() {
                    PBBiometryEnrollConfig config =
                            new PBBiometryEnrollConfig();
                    try {
                        bio.enrollFinger(
                                // Enroll the finger selected in the
                                // GUI
                                getSelectedFinger(),
                                // Use the local database for
                                // template storage
                                LocalDatabase.getInstance(),
                                // The GUI functionality is
                                // handled by the enroll dialog.
                                dialog,
                                // We use the recommended 3 sample
                                // enrollment. The GUI is also created
                                // to show the 3 samples, see
                                // EnrollDialog
                                3,
                                // Default configuration is used
                                config,
                                // We use our own GUI so GUI
                                // configuration is useless here
                                null);
                        showInfoDialog(R.string.enroll_successful,
                                R.string.info);
                        //    ++ _fingerCounter;
                    } catch (PBBiometryException e) {
                        publishError(e.getErrorDisplayName());
                    } finally {
                        if (dialog != null) {
                            dialog.dismiss();
                        }
                    }
                }
            }).start();
        }
    }
    private PBBiometryFinger getSelectedFinger() {
        int id = radioGroup.getCheckedRadioButtonId();
        PBBiometryFinger fing=null;
        if (id == R.id.fingerL1) {
            fing= new PBBiometryFinger(
                    PBFingerPosition.PBFingerPositionLeftIndex,
                    new PBBiometryUser(_id));
        }
        else if (id == R.id.fingerL2) {
            fing= new PBBiometryFinger(
                    PBFingerPosition.PBFingerPositionLeftLittle,
                    new PBBiometryUser(_id));
        }
        else if (id == R.id.fingerL3) {
            fing=new PBBiometryFinger(
                    PBFingerPosition.PBFingerPositionLeftMiddle,
                    new PBBiometryUser(_id));
        }
        else if (id == R.id.fingeL4) {
            fing= new PBBiometryFinger(
                    PBFingerPosition.PBFingerPositionLeftRing,
                    new PBBiometryUser(_id));
        }
        else if (id == R.id.fingerL5) {
            fing= new PBBiometryFinger(
                    PBFingerPosition.PBFingerPositionLeftThumb,
                    new PBBiometryUser(_id));
        }
        return fing;
    }

}
