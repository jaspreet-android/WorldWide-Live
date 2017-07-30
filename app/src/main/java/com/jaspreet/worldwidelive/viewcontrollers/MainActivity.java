package com.jaspreet.worldwidelive.viewcontrollers;

import android.content.Intent;
import android.support.v7.widget.AppCompatImageView;
import android.view.View;

import com.jaspreet.worldwidelive.R;
import com.jaspreet.worldwidelive.preferences.PreferenceConnector;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

/**
 * A login screen that offers login via email/password.
 */
@EActivity(R.layout.activity_main)
public class MainActivity extends BaseActivity {
    @ViewById
    AppCompatImageView logOutBtn;

    @Click(R.id.logOutBtn)
    void onLogOutBtnClick(View view) {

    }

    @Click(R.id.mReportNewsButton)
    void onReportNewsButtonClick(View view) {
        if (!PreferenceConnector.readBoolean(PreferenceConnector.PREF_IS_USER_LOGGEDIN, false, this)) {
            startActivity(new Intent(MainActivity.this, HomeActivity_.class));
        }
    }

    @Click(R.id.mWatchNewsButton)
    void onWatchNewsButtonClick(View view) {

    }

    @AfterViews
    public void InitViews() {

    }
}

