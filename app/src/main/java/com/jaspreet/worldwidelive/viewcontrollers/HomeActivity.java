package com.jaspreet.worldwidelive.viewcontrollers;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;

import com.jaspreet.worldwidelive.R;
import com.jaspreet.worldwidelive.mvp.presenter.MainActivityPresenter;
import com.jaspreet.worldwidelive.mvp.presenter.imp.MainActivityPresenterImp;
import com.jaspreet.worldwidelive.mvp.views.MainActivityMvpView;
import com.jaspreet.worldwidelive.preferences.PreferenceConnector;
import com.jaspreet.worldwidelive.viewcontrollers.fragments.LoginFragment_;
import com.jaspreet.worldwidelive.viewcontrollers.fragments.VerifyCodeFragment_;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;

/**
 * Created by office on 30/07/17.
 */

@EActivity(R.layout.activity_home)
public class HomeActivity extends BaseActivity implements MainActivityMvpView {
    private MainActivityPresenter presenter;

    @AfterViews
    public void InitViews() {
        presenter = MainActivityPresenterImp.getInstance(this);

        //add login fragment
        addFragment(LoginFragment_.builder().build());
    }

    private void addFragment(Fragment fragment) {
        final FragmentManager supportFragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = supportFragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.container, fragment);
        fragmentTransaction.commitAllowingStateLoss();
    }

    @Override
    public void onLoginSuccess(String Code) {
        Log.d("Code", Code);
        addFragment(VerifyCodeFragment_.builder()
                .arg(PreferenceConnector.PREF_CODE, Code)
                .build());
    }

    @Override
    public void onLoginFailure() {
//add login fragment
        addFragment(LoginFragment_.builder().build());
    }

    @Override
    public void setPhoneError(String error) {

    }

    @Override
    public void setCodeError(String error) {

    }

    @Override
    public void resetError() {

    }

    @Override
    public Context getContext() {
        return getApplicationContext();
    }
}
