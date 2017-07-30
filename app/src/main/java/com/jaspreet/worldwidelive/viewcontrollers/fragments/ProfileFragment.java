package com.jaspreet.worldwidelive.viewcontrollers.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.AppCompatImageView;
import android.view.View;
import android.widget.Toast;

import com.jaspreet.worldwidelive.R;
import com.jaspreet.worldwidelive.preferences.PreferenceConnector;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

@EFragment(R.layout.fragment_profile)
public class ProfileFragment extends Fragment {

    @ViewById
    AppCompatImageView profilePhoto;

    String path;

    @ViewById
    AppCompatEditText UserName;

    @ViewById
    AppCompatEditText boiText;

    @Click(R.id.mLogoutButton)
    void onLogoutButtonClick(View view) {
        getActivity().finish();
    }

    @Click(R.id.mSaveButton)
    void onSaveButtonClick(View view) {
        PreferenceConnector.writeString(PreferenceConnector.PREF_USER_NAME, UserName.getText().toString(), getActivity());
        PreferenceConnector.writeString(PreferenceConnector.PREF_BIO, boiText.getText().toString(), getActivity());
        PreferenceConnector.writeString(PreferenceConnector.PREF_PIC, path, getActivity());
        Toast.makeText(getActivity(), getResources().getString(R.string.profile_saved), Toast.LENGTH_LONG).show();
        getActivity().finish();
    }

    @Click(R.id.mCancelButton)
    void onCancelButtonClick(View view) {
        getActivity().finish();
    }

    public ProfileFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @AfterViews
    void InitViews() {
    }


}
