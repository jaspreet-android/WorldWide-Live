package com.jaspreet.worldwidelive.mvp.presenter.imp;

import android.text.TextUtils;

import com.jaspreet.worldwidelive.R;
import com.jaspreet.worldwidelive.mvp.presenter.MainActivityPresenter;
import com.jaspreet.worldwidelive.mvp.views.MainActivityMvpView;
import com.jaspreet.worldwidelive.utils.Util;


/**
 * Created by office on 30/07/17.
 */

public class MainActivityPresenterImp implements MainActivityPresenter {
    private static MainActivityPresenter instance;
    private MainActivityMvpView mainActivityMvpView;

    private MainActivityPresenterImp(MainActivityMvpView mainActivityMvpView) {
        this.mainActivityMvpView = mainActivityMvpView;
    }

    @Override
    public void onAttachView() {

    }

    @Override
    public void onDetachView() {
    mainActivityMvpView=null;
    instance=null;
    }

    public MainActivityMvpView getMainActivityMvpView() {
        return mainActivityMvpView;
    }


    public static MainActivityPresenter getInstance(MainActivityMvpView mainActivityMvpView) {
        instance = new MainActivityPresenterImp(mainActivityMvpView);
        return instance;
    }


    @Override
    public void onLoginClick(String countryCode,String phoneNumber) {
        if (!TextUtils.isEmpty(phoneNumber)) {
            if (phoneNumber.length() < 7) {
                mainActivityMvpView.setPhoneError(mainActivityMvpView.getContext().getString(R.string.error_invalid_number));
                return;
            }

            if (countryCode.length() < 1) {
                mainActivityMvpView.setCodeError(mainActivityMvpView.getContext().getString(R.string.error_invalid_number));
                return;
            }
            String code = Util.getRandomNumber();
            mainActivityMvpView.onLoginSuccess(code);
        } else {
            mainActivityMvpView.setPhoneError(mainActivityMvpView.getContext().getString(R.string.error_field_required));
        }
    }

    @Override
    public void onVerifyCodeClick(String Code, String generatedCode) {
        if (!TextUtils.isEmpty(Code)) {
            if (Code.length() < 6) {
                mainActivityMvpView.setPhoneError(mainActivityMvpView.getContext().getString(R.string.error_invalid_code));
                return;
            }
            if ("123456".equals(Code))
                mainActivityMvpView.onLoginSuccess(Code);
            else
                mainActivityMvpView.setCodeError(mainActivityMvpView.getContext().getString(R.string.error_code_not_match));
        } else {
            mainActivityMvpView.setCodeError(mainActivityMvpView.getContext().getString(R.string.error_field_required));
        }
    }
}
