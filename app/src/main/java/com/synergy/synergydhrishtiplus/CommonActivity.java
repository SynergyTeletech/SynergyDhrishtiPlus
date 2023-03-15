package com.synergy.synergydhrishtiplus;
import static com.synergy.synergydhrishtiplus.utils.Utils.hexStringToBytes;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.res.ResourcesCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.annotation.SuppressLint;
import android.app.ActivityManager;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.navigation.NavigationView;
import com.google.gson.Gson;
import com.koushikdutta.async.AsyncServer;
import com.koushikdutta.async.AsyncSocket;
import com.koushikdutta.async.ByteBufferList;
import com.koushikdutta.async.DataEmitter;
import com.koushikdutta.async.Util;
import com.koushikdutta.async.callback.CompletedCallback;
import com.koushikdutta.async.callback.DataCallback;
import com.synergy.synergydhrishtiplus.activity.BaseActivity;
import com.synergy.synergydhrishtiplus.data_model.MapWrapper;
import com.synergy.synergydhrishtiplus.data_model.PumpHardwareInfoResponse;
import com.synergy.synergydhrishtiplus.dialogs.ScannerDialogFragment;
import com.synergy.synergydhrishtiplus.fragments.PumpListFragment;
import com.synergy.synergydhrishtiplus.fragments.StatusAndStockFragment;
import com.synergy.synergydhrishtiplus.socket.Server485;
import com.synergy.synergydhrishtiplus.utils.DataConversion;
import com.synergy.synergydhrishtiplus.utils.SharedPref;
import com.synergy.synergydhrishtiplus.viewmodel.HardwareResponse;

import java.util.HashMap;
import java.util.Map;

public class CommonActivity extends BaseActivity implements View.OnClickListener {
    FrameLayout container;
    private DrawerLayout mDrawer;
    private NavigationView nvDrawer;
    private ImageView drawerIcon;
    MenuItem menuItem;
    String hardwaredetail;
    String hardwareStatus;
    PumpHardwareInfoResponse.Data data;
    HardwareResponse hardwareViewModel;
    MapWrapper mapWrapper;
    HashMap<Integer, AsyncSocket> socketStore;
    FragmentManager fragmentManager;
    private ConstraintLayout topBarLayout;
    private TextView toolBarHeader;
    private ImageView toolBarMessageImage, toolBarLogoImage;

    int[] ports=new int[20];
    AsyncSocket socket[]=new AsyncSocket[20];



    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setStatusBarColor(ResourcesCompat.getColor(getResources(), R.color.top_bar_color, null));
        setContentView(R.layout.activity_common);
        hardwareViewModel= ViewModelProviders.of(this).get(HardwareResponse.class);
        Fragment prev = getSupportFragmentManager().findFragmentByTag("scan_dialog");
        if (prev != null) {
            ScannerDialogFragment df = (ScannerDialogFragment) prev;
            df.dismiss();
        }
        socketStore=((SynergyApplicationClass)getApplication()).getSocketHashMap();
//        int i=0;
//        for (Map.Entry<Integer,AsyncSocket> entry : socketStore.entrySet()){
//            if(entry.getValue()!=null){
//                 ports[i] = entry.getKey();
//                 socket[i]=entry.getValue();
//                i++;
//            }
//        }
        Gson gson =new Gson();
        hardwaredetail= SharedPref.getHardwareDetail();
        hardwareStatus=  SharedPref.getSocketConnnectionDetail();
        data=gson.fromJson(hardwaredetail, PumpHardwareInfoResponse.Data.class);
        hardwareViewModel.getDataObserver().observe(this, new Observer<PumpHardwareInfoResponse.Data>() {
            @Override
            public void onChanged(PumpHardwareInfoResponse.Data datas) {
                Log.d("mutatable",""+datas);
                data=datas;

            }
        });
        mapWrapper=gson.fromJson(hardwareStatus, MapWrapper.class);
        init();

        fragmentManager = getSupportFragmentManager();
        FragmentTransaction tx = getSupportFragmentManager().beginTransaction();
        tx.replace(R.id.container, new StatusAndStockFragment(), "initial");
        tx.commit();
        setupDrawerContent(nvDrawer);
    }
    private String  hexToAscii(String recive) {
        int n = recive.length();
        StringBuilder sb = new StringBuilder(n / 2);
        for (int i = 0; i < n; i += 2) {
            char a = recive.charAt(i);
            char b = recive.charAt(i + 1);
            sb.append((char) ((hexToInt(a) << 4) | hexToInt(b)));
        }
        return sb.toString();

    }
    private int hexToInt(char ch) {
        if ('a' <= ch && ch <= 'f') { return ch - 'a' + 10; }
        if ('A' <= ch && ch <= 'F') { return ch - 'A' + 10; }
        if ('0' <= ch && ch <= '9') { return ch - '0'; }
        throw new IllegalArgumentException(String.valueOf(ch));
    }
    private void setupDrawerContent(NavigationView navigationView) {
        navigationView.setNavigationItemSelectedListener(menuItem -> {
            this.menuItem = menuItem;
            selectDrawerItem(menuItem);
            return true;
        });
    }

    @SuppressLint("NonConstantResourceId")
    public void selectDrawerItem(MenuItem menuItem) {

        Fragment fragment = null;
        Class fragmentClass;
        switch (menuItem.getItemId()) {
            case R.id.nav_dispenser:
                fragmentClass = PumpListFragment.class;
                changeToolbar(false);
                break;
            default:
//                fragmentClass = StatusAndStockFragment.class;
//                changeToolbar(true);
        }
//        try {
//            fragment = (Fragment) fragmentClass.newInstance();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
        // Insert the fragment by replacing any existing fragment
//        fragmentManager.beginTransaction().replace(R.id.container, fragment).addToBackStack(fragmentClass.getName()).commit();
        mDrawer.closeDrawers();
    }

    private void init() {
        container = findViewById(R.id.container);
        mDrawer = findViewById(R.id.drawer_layout);
        nvDrawer = findViewById(R.id.nvView);
        drawerIcon = findViewById(R.id.drawerIcon);
        topBarLayout = findViewById(R.id.topBarLayout);
        toolBarHeader = findViewById(R.id.toolBarHeader);
        toolBarMessageImage = findViewById(R.id.toolBarMessageImage);
        toolBarLogoImage = findViewById(R.id.toolBarLogoImage);

        drawerIcon.setOnClickListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();

    }
    @Override
    public void onClick(View v) {
        mDrawer.openDrawer(GravityCompat.START);
    }
    @Override
    public void onBackPressed() {
        if (mDrawer.isDrawerOpen(nvDrawer)) {
            mDrawer.closeDrawers();
        }
        if(fragmentManager.getBackStackEntryCount() > 0) {
            changeToolbar(true);
            fragmentManager.popBackStack();
        } else {
            Intent setIntent = new Intent(Intent.ACTION_MAIN);
            setIntent.addCategory(Intent.CATEGORY_HOME);
            setIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(setIntent);

//           super.onBackPressed();
        }
    }

    private void clearAppData() {

            try {
                // clearing app data
                if (Build.VERSION_CODES.KITKAT <= Build.VERSION.SDK_INT) {
                    ((ActivityManager)getSystemService(ACTIVITY_SERVICE)).clearApplicationUserData(); // note: it has a return value!
                } else {
                    String packageName = getApplicationContext().getPackageName();
                    Runtime runtime = Runtime.getRuntime();
                    runtime.exec("pm clear "+packageName);
                }

            } catch (Exception e) {
                e.printStackTrace();
            }




    }

    private void changeToolbar(boolean isDefault){
        if(!isDefault) {
            topBarLayout.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.tool_bar_bg, null));
            toolBarHeader.setVisibility(View.GONE);
            toolBarLogoImage.setVisibility(View.VISIBLE);
            toolBarMessageImage.setVisibility(View.GONE);
        } else {
            topBarLayout.setBackgroundColor(ResourcesCompat.getColor(getResources(), R.color.top_bar_color, null));
            toolBarHeader.setVisibility(View.VISIBLE);
            toolBarLogoImage.setVisibility(View.GONE);
            toolBarMessageImage.setVisibility(View.VISIBLE);
        }
    }
}