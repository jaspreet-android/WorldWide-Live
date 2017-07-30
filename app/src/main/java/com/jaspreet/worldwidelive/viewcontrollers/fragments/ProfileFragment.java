package com.jaspreet.worldwidelive.viewcontrollers.fragments;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.AppCompatImageView;
import android.view.View;
import android.widget.Toast;

import com.jaspreet.worldwidelive.R;
import com.jaspreet.worldwidelive.preferences.PreferenceConnector;
import com.jaspreet.worldwidelive.utils.Util;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

@EFragment(R.layout.fragment_profile)
public class ProfileFragment extends Fragment {
    private static final int MY_PERMISSIONS_REQUEST_CAMERA = 1;
    private static final int MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE = 2;
    private static final int REQUEST_CAMERA = 3;
    private static final int SELECT_FILE = 4;
    private String userChoosenTask;

    @ViewById
    AppCompatImageView profilePhoto;

    String path;

    @ViewById
    AppCompatEditText UserName;

    @ViewById
    AppCompatEditText boiText;


    @Click(R.id.profilePhoto)
    void onProfilePhotoClick(View view) {
        selectImage();
    }

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

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    public static boolean checkCameraPermission(final Context context) {
        int currentAPIVersion = Build.VERSION.SDK_INT;
        if (currentAPIVersion >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(context, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                if (ActivityCompat.shouldShowRequestPermissionRationale((Activity) context, Manifest.permission.CAMERA)) {
                    ActivityCompat.requestPermissions((Activity) context, new String[]{Manifest.permission.CAMERA}, MY_PERMISSIONS_REQUEST_CAMERA);
                } else {
                    ActivityCompat.requestPermissions((Activity) context, new String[]{Manifest.permission.CAMERA}, MY_PERMISSIONS_REQUEST_CAMERA);
                }
                return false;
            } else if (ContextCompat.checkSelfPermission(context, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                if (ActivityCompat.shouldShowRequestPermissionRationale((Activity) context, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                    ActivityCompat.requestPermissions((Activity) context, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, MY_PERMISSIONS_REQUEST_CAMERA);
                } else {
                    ActivityCompat.requestPermissions((Activity) context, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, MY_PERMISSIONS_REQUEST_CAMERA);
                }
                return false;
            } else {
                return true;
            }
        } else {
            return true;
        }
    }


    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    public static boolean checkPermissionExternalStorage(final Context context) {
        int currentAPIVersion = Build.VERSION.SDK_INT;
        if (currentAPIVersion >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(context, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                if (ActivityCompat.shouldShowRequestPermissionRationale((Activity) context, Manifest.permission.READ_EXTERNAL_STORAGE)) {
                    ActivityCompat.requestPermissions((Activity) context, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE);
                } else {
                    ActivityCompat.requestPermissions((Activity) context, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE);
                }
                return false;
            } else {
                return true;
            }
        } else {
            return true;
        }
    }


    private void selectImage() {
        final CharSequence[] items = {getString(R.string.take_photo), getString(R.string.choose_from_lib),
                getString(R.string.cancel)};
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(getString(R.string.add_photo));
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                if (items[item].equals(getString(R.string.take_photo))) {
                    boolean result = checkCameraPermission(getActivity());
                    userChoosenTask = getString(R.string.take_photo);
                    if (result)
                        cameraIntent();
                } else if (items[item].equals(getString(R.string.choose_from_lib))) {
                    userChoosenTask = getString(R.string.choose_from_lib);
                    boolean result = checkPermissionExternalStorage(getActivity());
                    if (result)
                        galleryIntent();
                } else if (items[item].equals(getString(R.string.cancel))) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }

    /***
     * Function to select image from camera
     */
    private void cameraIntent() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, REQUEST_CAMERA);
    }

    /***
     * Function to select image from gallery
     */
    private void galleryIntent() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);//
        startActivityForResult(Intent.createChooser(intent, "Select File"), SELECT_FILE);
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
            case MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if (userChoosenTask.equals("Choose from Library"))
                        galleryIntent();
                } else {
//code for deny
                }
                break;

            case MY_PERMISSIONS_REQUEST_CAMERA:

                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if (userChoosenTask.equals("Take Photo"))
                        cameraIntent();
                } else {
//code for deny
                }
                break;
        }
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data != null) {

            if (resultCode == Activity.RESULT_OK) {
                if (requestCode == SELECT_FILE)
                    onSelectFromGalleryResult(data);
                else if (requestCode == REQUEST_CAMERA) {
                    onCaptureImageResult(data);
                }
            }


        }
    }


    private void onSelectFromGalleryResult(Intent data) {
        Bitmap bm = null;
        if (data != null) {
            try {
                bm = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), data.getData());
                final File file = Util.saveImageToStorage(bm, "profile_photo.jpg");
                path = file.getAbsolutePath();
                profilePhoto.setImageBitmap(bm);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


    }


    private void onCaptureImageResult(Intent data) {
        Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
        final File file = Util.saveImageToStorage(thumbnail, "profile_photo.jpg");
        path = file.getAbsolutePath();
        profilePhoto.setImageBitmap(thumbnail);
        InputStream inputStream = null;
        try {
            inputStream = getActivity().getContentResolver().openInputStream(Uri.fromFile(file));
        } catch (Exception ignored) {

        }

    }
}
