package com.jaspreet.worldwidelive.utils;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Environment;
import android.telephony.TelephonyManager;
import android.text.TextUtils;

import com.google.i18n.phonenumbers.NumberParseException;
import com.google.i18n.phonenumbers.PhoneNumberUtil;
import com.google.i18n.phonenumbers.Phonenumber;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.Random;

/**
 * Created by office on 30/07/17.
 */

public class Util {
    public static String getCountryRegionFromPhone(Context paramContext) {
        TelephonyManager service = null;
        int res = paramContext.checkCallingOrSelfPermission("android.permission.READ_PHONE_STATE");
        if (res == PackageManager.PERMISSION_GRANTED) {
            service = (TelephonyManager) paramContext.getSystemService(Context.TELEPHONY_SERVICE);
        }
        String code = null;
        if (service != null) {
            String str = service.getLine1Number();
            if (!TextUtils.isEmpty(str) && !str.matches("^0*$")) {
                code = parseNumber(str);
            }
        }
        if (code == null) {
            if (service != null) {
                code = service.getNetworkCountryIso();
            }
            if (code == null) {
                code = paramContext.getResources().getConfiguration().locale.getCountry();
            }
        }
        if (code != null) {
            return code.toUpperCase();
        }
        return null;
    }

    public static String parseNumber(String paramString) {
        if (paramString == null) {
            return null;
        }
        PhoneNumberUtil numberUtil = PhoneNumberUtil.getInstance();
        String result;
        try {
            Phonenumber.PhoneNumber localPhoneNumber = numberUtil.parse(paramString, null);
            result = numberUtil.getRegionCodeForNumber(localPhoneNumber);
            if (result == null) {
                return null;
            }
        } catch (NumberParseException localNumberParseException) {
            return null;
        }
        return result;
    }


    public static String getCountryCodeFromPhoneNumber(String paramString) {
        if (paramString == null) {
            return null;
        }
        PhoneNumberUtil numberUtil = PhoneNumberUtil.getInstance();
        String result = "";
        try {
            Phonenumber.PhoneNumber localPhoneNumber = numberUtil.parse(paramString, null);
            result = numberUtil.getCountryCodeForRegion(numberUtil.getRegionCodeForNumber(localPhoneNumber)) + "";
            if (result == null) {
                return "";
            }
        } catch (NumberParseException localNumberParseException) {
            return "";
        }
        return result;
    }

    public static Country getMyCountry(String countryCode, Activity activity) {


        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new InputStreamReader(activity.getApplicationContext().getAssets().open("countries.dat"), "UTF-8"));

            // do reading, usually loop until end of file reading
            String line;
            int i = 0;
            while ((line = reader.readLine()) != null) {
                //process line
                Country c = new Country(activity, line, i);

                if (c.getCountryISO().equalsIgnoreCase(countryCode)) {
                    return c;
                }
                i++;
            }
        } catch (IOException e) {
            //log the exception
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    //log the exception
                }
            }
        }

        return null;

    }

    public static File saveImageToStorage(Bitmap mSelectedImage, String fileName) {
        Bitmap bitmap = mSelectedImage;
        OutputStream fout = null;
        File imageFile = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).toString());
        imageFile.mkdirs();

        if (!imageFile.exists()) {
            imageFile.mkdirs();
        }
        imageFile = new File(imageFile, fileName);
        try {
            fout = new FileOutputStream(imageFile);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 50, fout);
            fout.flush();
            fout.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
        return imageFile;
    }

    public static String getRandomNumber() {
        // Generate random id, for example 283952-V8M32
        char[] chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789".toCharArray();
        Random rnd = new Random();
        StringBuilder sb = new StringBuilder((100000 + rnd.nextInt(900000)) + "-");
        for (int i = 0; i < 5; i++)
            sb.append(chars[rnd.nextInt(chars.length)]);

        return sb.toString();
    }
}
