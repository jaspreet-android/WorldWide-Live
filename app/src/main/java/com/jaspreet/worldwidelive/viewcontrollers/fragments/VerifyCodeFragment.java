package com.jaspreet.worldwidelive.viewcontrollers.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatEditText;
import android.view.View;
import android.widget.ProgressBar;

import com.jaspreet.worldwidelive.R;
import com.jaspreet.worldwidelive.mvp.presenter.MainActivityPresenter;
import com.jaspreet.worldwidelive.mvp.presenter.imp.MainActivityPresenterImp;
import com.jaspreet.worldwidelive.mvp.views.MainActivityMvpView;
import com.jaspreet.worldwidelive.preferences.PreferenceConnector;
import com.jaspreet.worldwidelive.viewcontrollers.HomeActivity;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

@EFragment(R.layout.fragment_code)
public class VerifyCodeFragment extends Fragment implements MainActivityMvpView {
    private static final int MY_PERMISSIONS_REQUEST_READ_PHONE_STATE = 1;
    String VerificationCode;
    private MainActivityPresenter presenter;

    @ViewById
    AppCompatEditText confirmationCodeText;

    @ViewById
    ProgressBar progressView;

    @Click(R.id.mVerifyButton)
    void onVerifyButtonClick(View view) {
        progressView.setVisibility(View.VISIBLE);
        String code = confirmationCodeText.getText().toString();
        presenter.onVerifyCodeClick(code, VerificationCode);
    }

    @Click(R.id.mNotReceivedButton)
    void onNotReceivedButtonClick(View view) {
        progressView.setVisibility(View.VISIBLE);
        ((HomeActivity) getActivity()).onLoginFailure();
    }

    public VerifyCodeFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        VerificationCode = getArguments().getString(PreferenceConnector.PREF_CODE);
    }

    @AfterViews
    void InitViews() {
        presenter = MainActivityPresenterImp.getInstance(this);
    }


    @Override
    public void onLoginSuccess(String Code) {
        progressView.setVisibility(View.GONE);
    }

    @Override
    public void onLoginFailure() {
        progressView.setVisibility(View.GONE);
    }

    @Override
    public void setPhoneError(String error) {
        progressView.setVisibility(View.GONE);
        confirmationCodeText.setError(error);
    }

    @Override
    public void setCodeError(String error) {
        progressView.setVisibility(View.GONE);
        confirmationCodeText.setError(error);
    }

    @Override
    public void resetError() {
        confirmationCodeText.setError(null);
    }
}
