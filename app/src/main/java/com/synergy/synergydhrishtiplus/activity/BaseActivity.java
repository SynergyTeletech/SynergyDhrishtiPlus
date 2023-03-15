package com.synergy.synergydhrishtiplus.activity;

import static android.Manifest.permission.ACCESS_FINE_LOCATION;
import static android.Manifest.permission.READ_SMS;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.widget.Toast;
import com.synergy.synergydhrishtiplus.R;
public class BaseActivity extends AppCompatActivity {
    private FragmentManager fm;
    private FragmentTransaction ft;
    private ProgressDialog progressDialog;
    public static final int PERMISSION_READ_SMS_CODE = 101;
    public static final int PERMISSION_LOCATION_ACCESS_CODE = 102;
    public void replaceFragment(Activity mContext, int layout, Fragment fragment, Bundle bundle, Boolean addTobackStack) {
        fm = getSupportFragmentManager();
        ft = fm.beginTransaction();
        fragment.setArguments(bundle);
        ft.replace(layout, fragment, fragment.getClass().getSimpleName());

        if (addTobackStack) {
            ft.addToBackStack(fragment.getClass().getSimpleName());
        }
        ft.commit();
    }

    public void showProgressDialog(Context mContext) {
        progressDialog = new ProgressDialog(mContext);
        progressDialog.setCancelable(false);
        progressDialog.setMessage(getResources().getString(R.string.please_wait));
        progressDialog.show();
    }

    public void hideProgressDialog(Context mContext) {

        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.hide();
        }

    }

    /*
    method to show toast message to user
     */
    public void showToast(String str) {
        Toast.makeText(this, "" + str, Toast.LENGTH_SHORT).show();
    }



    public void checkIfPermissionIsAvailable(Context mContext, int permission) {
        // checkPermission(mContext, permissions);
        switch (permission) {
            case PERMISSION_LOCATION_ACCESS_CODE:

                if (checkPermissionAvaiable(mContext, permission)) {
                    Toast.makeText(mContext, "Permission Granted", Toast.LENGTH_SHORT).show();
                } else {
                    requestPermission(permission, mContext);
                }

                break;
            case PERMISSION_READ_SMS_CODE:
                if (checkPermissionAvaiable(mContext, permission)) {
                    Toast.makeText(mContext, "SMS permission granted", Toast.LENGTH_SHORT).show();
                } else {
                    requestPermission(permission, mContext);
                }
        }
    }
    private void requestPermission(int permission, Context mContext) {
        switch (permission) {
            case PERMISSION_LOCATION_ACCESS_CODE:
                ActivityCompat.requestPermissions((Activity) mContext, new String[]{ACCESS_FINE_LOCATION}, PERMISSION_LOCATION_ACCESS_CODE);
                break;
            case PERMISSION_READ_SMS_CODE:
                ActivityCompat.requestPermissions((Activity) mContext, new String[]{READ_SMS}, PERMISSION_READ_SMS_CODE);
                break;
        }
    }

    private boolean checkPermissionAvaiable(Context mContext, int permission) {

        switch (permission) {
            case PERMISSION_LOCATION_ACCESS_CODE:
                int resultLocation = ContextCompat.checkSelfPermission(mContext, ACCESS_FINE_LOCATION);
                return resultLocation == PackageManager.PERMISSION_GRANTED;
            case PERMISSION_READ_SMS_CODE:
                int resultSMS = ContextCompat.checkSelfPermission(mContext, READ_SMS);
                return resultSMS == PackageManager.PERMISSION_GRANTED;
        }
        return false;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case PERMISSION_LOCATION_ACCESS_CODE:
                if (grantResults.length > 0) {
                    boolean locationGranted = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                    if (!locationGranted) {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                            showMessageOKCancel("You need to allow location access permission",
                                    new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialogInterface, int i) {
                                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                                requestPermissions(new String[]{ACCESS_FINE_LOCATION},
                                                        PERMISSION_LOCATION_ACCESS_CODE);
                                            }
                                        }
                                    });
                        }
                    }
                }
                break;
            case PERMISSION_READ_SMS_CODE:
                if (grantResults.length > 0) {
                    boolean smsGranted = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                    if (!smsGranted) {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                            showMessageOKCancel("You need to allow SMS read options",
                                    new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialogInterface, int i) {
                                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                                requestPermissions(new String[]{READ_SMS},
                                                        PERMISSION_READ_SMS_CODE);
                                            }
                                        }
                                    });
                        }
                    }
                }
        }
    }
    private void showMessageOKCancel(String message, DialogInterface.OnClickListener okListener) {
        new AlertDialog.Builder(this)
                .setMessage(message)
                .setPositiveButton("OK", okListener)
                .setNegativeButton("Cancel", null)
                .create()
                .show();
    }
}