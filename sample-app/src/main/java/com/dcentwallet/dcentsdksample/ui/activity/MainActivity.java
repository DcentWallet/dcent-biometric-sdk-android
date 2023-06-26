package com.dcentwallet.dcentsdksample.ui.activity;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.dcentwallet.dcentsdksample.databinding.ActivityMainBinding;
import com.dcentwallet.dcentsdksample.dcent.DcentSdkManager;
import com.dcentwallet.manager.DcentManager;

@RequiresApi(api = Build.VERSION_CODES.S)
public class MainActivity extends AppCompatActivity implements DcentManager.Observer{
    private static final String LOGTAG = MainActivity.class.getName();

    private DcentSdkManager dcentsdkManager;

    private static final String[] PERMISSIONS_LOCATION = {
            Manifest.permission.BLUETOOTH_SCAN,
            Manifest.permission.BLUETOOTH_CONNECT,
            Manifest.permission.BLUETOOTH_PRIVILEGED
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.S){
            if (!hasPermissions(this)) {
                int REQUEST_ALL_PERMISSION = 2;
                requestPermissions(PERMISSIONS_LOCATION, REQUEST_ALL_PERMISSION);
            }
        }

        initDcentSdkManager();

        com.dcentwallet.dcentsdksample.databinding.ActivityMainBinding binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }

    private boolean hasPermissions(Context context) {
        for(int i = 0; i < MainActivity.PERMISSIONS_LOCATION.length; i++){
            if (ActivityCompat.checkSelfPermission(context, MainActivity.PERMISSIONS_LOCATION[i])
                    != PackageManager.PERMISSION_GRANTED
            ) {
                return false;
            }
        }
        return true;
    }

    private void initDcentSdkManager() {
        DcentSdkManager.getInstance().initialize(getBaseContext());
        dcentsdkManager = DcentSdkManager.getInstance();
        Log.d(LOGTAG,"setDcentManagerSubscribe");
        dcentsdkManager.setDcentManagerSubscribe(this);
        if(hasPermissions(this)) dcentsdkManager.deviceInitialize();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        dcentsdkManager.deviceInitialize();
    }

    @Override
    protected void onDestroy() {
        if (null != dcentsdkManager && null != dcentsdkManager.getDcentManager()) {
            dcentsdkManager.setDcentManagerUnSubscribe(this);
            Log.i(LOGTAG,"setDcentManagerUnSubscribe : %s");
            dcentsdkManager.getDcentManager().finalize();
        }
        super.onDestroy();
    }

    @Override
    public void dcentDongleConnected(String s) {
        Log.v(LOGTAG, "dcentDongleConnected : " + s);
        dcentsdkManager.setConnectedDeviceName(s);
        Toast.makeText(getApplicationContext(), "dcentDongleConnected : " + s, Toast.LENGTH_LONG).show();
    }

    @Override
    public void dcentDongleDisconnected(String s) {
        assert dcentsdkManager != null;
        dcentsdkManager.onlyDisconnectDevice();

        Log.v(LOGTAG, "dcentDongleDisconnected : " + s);
        Toast.makeText(getApplicationContext(), "dcentDongleDisconnected : " + s, Toast.LENGTH_LONG).show();
    }

}