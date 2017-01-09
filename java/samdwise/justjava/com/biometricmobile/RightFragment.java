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
import com.precisebiometrics.android.mtk.biometrics.PBBiometryTemplate;
import com.precisebiometrics.android.mtk.biometrics.PBBiometryUser;

import org.kobjects.base64.Base64;

import java.util.List;

/**
 * Created by anochirionye.emilia on 3/9/2016.
 */
public class RightFragment extends BiometricsFragment {

    private Button enrollRightFinger;
    private Button cont;
    public final static String EXTRA_MESSAGE = "com.cscsnigeriaplc.cscsbiometrics.MESSAGE";
    public final static String USER_ID = "com.cscsnigeriaplc.cscsbiometrics.USERID";
    private boolean widgetsEnabled = false;
    private RadioGroup radioGroup;
    private int _id;
    private DatabaseHandler _db;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.new_fragment_local_db,
                container, false);
        radioGroup = (RadioGroup) rootView.findViewById(R.id.radioGroupR);
        enrollRightFinger = (Button) rootView
                .findViewById(R.id.enrollButtonRight);
        enrollRightFinger.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                enrollLocal(v);
                //	deleteLocal();

            }
        });
        cont = (Button) rootView.findViewById(R.id.ButtonContinueRight);
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

    private void deleteLocal(){
        LocalDatabase ldb=LocalDatabase.getInstance();
        List<PBBiometryFinger>  fingerPosList=	ldb.getEnrolledFingers();
        for (int i = 0; i < fingerPosList.size(); i++) {
            PBBiometryFinger fingerPos=fingerPosList.get(i);
            try {
                ldb.deleteTemplate(fingerPos);
            } catch (PBBiometryException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

        }
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
        if (enrollRightFinger != null) {
            enrollRightFinger.setEnabled(widgetsEnabled);
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
                    PBBiometryEnrollConfig config = new PBBiometryEnrollConfig();
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
        PBBiometryFinger fing = null;
        if (id == R.id.fingerR1) {
            fing = new PBBiometryFinger(
                    PBFingerPosition.PBFingerPositionRightIndex,
                    new PBBiometryUser(_id));
        } else if (id == R.id.fingerR2) {
            fing = new PBBiometryFinger(
                    PBFingerPosition.PBFingerPositionRightLittle,
                    new PBBiometryUser(_id));
        } else if (id == R.id.fingerR3) {
            fing = new PBBiometryFinger(
                    PBFingerPosition.PBFingerPositionRightMiddle,
                    new PBBiometryUser(_id));
        } else if (id == R.id.fingeR4) {
            fing = new PBBiometryFinger(
                    PBFingerPosition.PBFingerPositionRightRing,
                    new PBBiometryUser(_id));
        } else if (id == R.id.fingerR5) {
            fing = new PBBiometryFinger(
                    PBFingerPosition.PBFingerPositionRightThumb,
                    new PBBiometryUser(_id));
        }
        return fing;
    }
    String base64S = "";
    private void GetTemplates() {

        LocalDatabase ldb=	LocalDatabase.getInstance();
        PBBiometryTemplate temp=null;
        List<PBBiometryFinger> enrolledFingers=ldb.getEnrolledFingers();




        for (int i = 0; i < enrolledFingers.size(); i++) {
            try {
                temp=ldb.getTemplate(enrolledFingers.get(i));
                byte[] _arr=temp.getData();
                // Bitmap bitmap = BitmapFactory.decodeByteArray(_arr , 0, _arr .length);





                String strBase64 = Base64.encode(_arr);
                base64S = strBase64;
                //base64S=WebService.invokeHelloWorldWS(base64S,"HelloWorld");
                // t.doInBackground(arr);
            } catch (PBBiometryException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

        }
    }
}
