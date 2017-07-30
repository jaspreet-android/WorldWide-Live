package com.jaspreet.worldwidelive.mvp.presenter;

/**
 * Created by office on 30/07/17.
 */

public interface MainActivityPresenter extends Presenter {
    void onLoginClick(String code,String phoneNumber);

    void onVerifyCodeClick(String code, String generatedCode);
}
