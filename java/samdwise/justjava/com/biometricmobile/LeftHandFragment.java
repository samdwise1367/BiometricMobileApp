package samdwise.justjava.com.biometricmobile;

import java.util.List;
import com.precisebiometrics.android.mtk.api.bio.PBBiometry;
import com.precisebiometrics.android.mtk.api.bio.PBBiometryException;
import com.precisebiometrics.android.mtk.biometrics.PBBiometryTrust;
import com.precisebiometrics.android.mtk.biometrics.PBBiometryVerifyConfig;
import com.precisebiometrics.android.mtk.biometrics.PBBiometryVerifyConfig.PBFalseAcceptRate;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by anochirionye.emilia on 3/9/2016.
 */
public class LeftHandFragment extends BiometricsFragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_left_hand,
                container, false);


        return rootView;
    }

}
