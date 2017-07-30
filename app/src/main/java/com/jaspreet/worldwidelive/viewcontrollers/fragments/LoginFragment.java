package com.jaspreet.worldwidelive.viewcontrollers.fragments;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.AppCompatEditText;
import android.telephony.TelephonyManager;
import android.view.View;
import android.widget.ProgressBar;

import com.jaspreet.worldwidelive.R;
import com.jaspreet.worldwidelive.mvp.presenter.MainActivityPresenter;
import com.jaspreet.worldwidelive.mvp.presenter.imp.MainActivityPresenterImp;
import com.jaspreet.worldwidelive.mvp.views.MainActivityMvpView;
import com.jaspreet.worldwidelive.preferences.PreferenceConnector;
import com.jaspreet.worldwidelive.utils.Country;
import com.jaspreet.worldwidelive.utils.Util;
import com.jaspreet.worldwidelive.viewcontrollers.HomeActivity;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

@EFragment(R.layout.fragment_login)
public class LoginFragment extends Fragment implements MainActivityMvpView {
    private static final int MY_PERMISSIONS_REQUEST_READ_PHONE_STATE = 1;

    private MainActivityPresenter presenter;

    @ViewById
    AppCompatEditText countryCodeText;

    @ViewById
    AppCompatEditText phoneNumberText;

    @ViewById
    ProgressBar progressView;

    @Click(R.id.mGetCodeButton)
    void onGetCodeButtonClick(View view) {
        progressView.setVisibility(View.VISIBLE);
        String code = countryCodeText.getText().toString();
        String number = phoneNumberText.getText().toString();
        PreferenceConnector.writeString(PreferenceConnector.PREF_USER_PHONE_NUMBER, code + number, getActivity());
        presenter.onLoginClick(code + number);
    }

    public LoginFragment() {
    }

    @AfterViews
    void InitViews() {
        setCountryCode();
        presenter = MainActivityPresenterImp.getInstance(this);
    }

    public static boolean checkPhoneStatePermission(final Context context) {
        int currentAPIVersion = Build.VERSION.SDK_INT;
        if (currentAPIVersion >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(context, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
                if (ActivityCompat.shouldShowRequestPermissionRationale((Activity) context, Manifest.permission.READ_PHONE_STATE)) {
                    ActivityCompat.requestPermissions((Activity) context, new String[]{Manifest.permission.READ_PHONE_STATE}, MY_PERMISSIONS_REQUEST_READ_PHONE_STATE);
                } else {
                    ActivityCompat.requestPermissions((Activity) context, new String[]{Manifest.permission.READ_PHONE_STATE}, MY_PERMISSIONS_REQUEST_READ_PHONE_STATE);
                }
                return false;
            } else {
                return true;
            }
        } else {
            return true;
        }
    }

    /***
     *  Function to get result for permission
     *
     * @param requestCode
     * @param permissions
     * @param grantResults
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_READ_PHONE_STATE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    fillCountryCode();
                } else {
                    //code for deny
                    countryCodeText.setText("1");
                }
                break;
        }
    }

    private void fillCountryCode() {
        TelephonyManager tm = (TelephonyManager) getActivity().getSystemService(Activity.TELEPHONY_SERVICE);
        String phonenumber = tm.getLine1Number();
        String region = Util.getCountryRegionFromPhone(getActivity());

        Country country = Util.getMyCountry(region, getActivity());
        if (country != null) {
            countryCodeText.setText("");
            countryCodeText.append("" + country.getCountryCode());
        }
    }

    private void setCountryCode() {
        if (checkPhoneStatePermission(getActivity())) {
            fillCountryCode();
        } else {
            countryCodeText.setText("1");
        }
    }

    @Override
    public void onLoginSuccess(String Code) {
        progressView.setVisibility(View.GONE);
        ((HomeActivity) getActivity()).onLoginSuccess(Code);
    }

    @Override
    public void onLoginFailure() {
        progressView.setVisibility(View.GONE);
    }

    @Override
    public void setPhoneError(String error) {
        progressView.setVisibility(View.GONE);
        phoneNumberText.setError(error);
    }

    @Override
    public void setCodeError(String error) {
        progressView.setVisibility(View.GONE);
    }

    @Override
    public void resetError() {
        phoneNumberText.setError(null);
    }
}
