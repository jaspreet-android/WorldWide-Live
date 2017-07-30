package com.jaspreet.worldwidelive.mvp.views;

/**
 * Created by office on 30/07/17.
 */

public interface MainActivityMvpView extends MvpView {
    void onLoginSuccess(String Code);

    void onLoginFailure();

    void setPhoneError(String error);

    void setCodeError(String error);

    void resetError();
}
