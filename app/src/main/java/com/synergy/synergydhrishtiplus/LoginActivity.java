package com.synergy.synergydhrishtiplus;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.app.ActivityManager;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bugfender.sdk.Bugfender;
import com.github.ybq.android.spinkit.sprite.Sprite;
import com.github.ybq.android.spinkit.style.Circle;

import com.koushikdutta.async.AsyncServer;
import com.koushikdutta.async.AsyncSocket;
import com.koushikdutta.async.callback.CompletedCallback;
import com.synergy.synergydhrishtiplus.data_model.LoginResponse;
import com.synergy.synergydhrishtiplus.dialogs.ScannerDialogFragment;
import com.synergy.synergydhrishtiplus.network.ApiClient;
import com.synergy.synergydhrishtiplus.network.ApiInterface;
import com.synergy.synergydhrishtiplus.room.DatabaseClient;
import com.synergy.synergydhrishtiplus.socket.Server485;
import com.synergy.synergydhrishtiplus.utils.SharedPref;
import com.synergy.synergydhrishtiplus.utils.UtilsNetwork;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Executors;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.ContentValues.TAG;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    TextView login_btn;
    ProgressBar progressBar;
    String user,pass,LoginUserId;
    EditText userEmail,userPaswd;
    HashMap<Integer, AsyncSocket> socketStore;
    private static final String TAG = "LoginActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        init();


    }

    private void init() {
        login_btn = findViewById(R.id.login_btn);
        progressBar=findViewById(R.id.progress_circle);
        userEmail=findViewById(R.id.et_email);
        userPaswd=findViewById(R.id.et_pswd);
        userEmail.setText("test_franchisee@gmail.com");
        userPaswd.setText("1");

//        userEmail.setText("TV");
//        userPaswd.setText("1");
        Sprite circle = new Circle();
        progressBar.setIndeterminateDrawable(circle);

        login_btn.setOnClickListener(this);

    }



    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onClick(View v) {
        user = userEmail.getText().toString();
        pass = userPaswd.getText().toString();


        if(TextUtils.isEmpty(user)){
            Toast.makeText(LoginActivity.this, "Please enter your email", Toast.LENGTH_SHORT).show();
        }
        else if(TextUtils.isEmpty(pass)){
            Toast.makeText(LoginActivity.this, "Please enter your password", Toast.LENGTH_SHORT).show();
        }
        else{
            hitLoginApi(user,pass);
        }
    }
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void hitLoginApi(String user, String pass) {
        progressBar.setVisibility(View.VISIBLE);
        if (UtilsNetwork.isNetworkConnected(LoginActivity.this)) {
            HashMap<String, String> hashMap = new HashMap<>();
            hashMap.put("username", user);
            hashMap.put("password", pass);
            if (UtilsNetwork.isNetworkConnected(LoginActivity.this)) {
                ApiInterface apiInterface = ApiClient.getClientCI().create(ApiInterface.class);
                apiInterface.getLogin(hashMap).enqueue(new Callback<LoginResponse>() {
                    @Override
                    public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                        progressBar.setVisibility(View.GONE);

                        try {
                            if (response.isSuccessful() && response.body().getData().getSuccess()) {

                                Bugfender.d(TAG, response.body().getData().getMessage());
                                LoginUserId= response.body().getData().getData().getLoginId();
                                SharedPref.setLoginUserId(LoginUserId);
                                showScaningDialog();
                            } else {
                                try {
                                    Bugfender.d(TAG, response.body().getData().getMessage());
                                } catch (Exception e) {
                                    Bugfender.d(TAG,e.getMessage());
                                }
                            }
                        } catch (Exception e) {
                            Bugfender.d(TAG,""+ response.body().getData().getErrorMessages());
                            progressBar.setVisibility(View.GONE);
                            Toast.makeText(LoginActivity.this, "" + response.body().getData().getErrorMessages(), Toast.LENGTH_SHORT).show();
                        }
                    }
                    @Override
                    public void onFailure(Call<LoginResponse> call, Throwable t) {
                        Bugfender.d(TAG, t.getMessage());
                        progressBar.setVisibility(View.GONE);
                        Toast.makeText(LoginActivity.this, getResources().getString(R.string.server_problem), Toast.LENGTH_SHORT).show();
                    }
                });
            } else {
                Toast.makeText(LoginActivity.this, "Please check internet connection", Toast.LENGTH_LONG).show();
            }
        } else {
            progressBar.setVisibility(View.GONE);
            Toast.makeText(this, getResources().getString(R.string.no_internet), Toast.LENGTH_SHORT).show();
        }
    }


    private void showScaningDialog() {
        ScannerDialogFragment dialogFragment = new ScannerDialogFragment();
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        Fragment prev = getSupportFragmentManager().findFragmentByTag("scan_dialog");
        if (prev != null) {
            ft.remove(prev);
        }
        ft.addToBackStack(null);
        dialogFragment.show(ft, "scan_dialog");
    }
}