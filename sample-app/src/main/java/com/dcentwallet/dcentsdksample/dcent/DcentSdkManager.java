package com.dcentwallet.dcentsdksample.dcent;

import android.content.Context;

import com.dcentwallet.manager.DcentException;
import com.dcentwallet.manager.DcentManager;
import com.dcentwallet.manager.comm.DeviceInfo;

public class DcentSdkManager {
    private Context _context;
    private DcentManager _dcentManager = null;
    private DeviceInfo connectedDevice;
    private String connectedDeviceName ;

    private static DcentSdkManager _instance = null;

    public static DcentSdkManager getInstance() {
        if(null == _instance){
            _instance = new DcentSdkManager();
        }
        return _instance;
    }

    public void initialize(Context context)
    {
        this._context = context.getApplicationContext();
        this._dcentManager = new DcentManager( this._context);
    }

    public DcentManager getDcentManager()
    {
        return this._dcentManager;
    }

    public void setDcentManagerSubscribe(DcentManager.Observer observer)
    {
        if ( this._context==null ) {
            throw new IllegalStateException("Context of DcentManagerModel is not set");
        }
        if ( this._dcentManager==null ) {
            throw new IllegalStateException("DcentManager Object of DcentManagerModel is not set");
        }
        this._dcentManager.subscribe(observer);
    }

    public void setDcentManagerUnSubscribe(DcentManager.Observer observer)
    {
        if ( this._context==null ) {
            throw new IllegalStateException("Context of DcentManagerModel is not set");
        }
        if ( this._dcentManager==null ) {
            throw new IllegalStateException("DcentManager Object of DcentManagerModel is not set");
        }
        this._dcentManager.unsubscribe(observer);
    }
    public void setConnectedDeviceName(String deviceName)
    {
        this.connectedDeviceName = deviceName ;
    }

    public String getConnectedDeviceName() {
        return this.connectedDeviceName ;
    }

    public boolean isDongleConnected(){
        if (  this._dcentManager==null ) {
            return false;
        }
        return this._dcentManager.isConnected();
    }

    private void setConnectedDevice( DeviceInfo deviceInfo ) {
        this.connectedDevice = deviceInfo ;
    }

    public DeviceInfo getConnectedDevice()
    {
        return this.connectedDevice ;
    }


    public void deviceInitialize() {
        if ( _dcentManager!=null && !_dcentManager.isConnected()) {
            _dcentManager.initialize();
        }
    }

    public void disconnectDevice()
    {
        if (_dcentManager != null) {
            _dcentManager.finalize();
        }
        connectedDevice = null ;
        connectedDeviceName = null;
    }

    public void onlyDisconnectDevice()
    {
        connectedDevice = null ;
        connectedDeviceName = null;
    }

    public DeviceInfo getInfo() throws DcentException {
        if (this.connectedDevice == null) {
            DeviceInfo deviceInfo = _dcentManager.getDeviceInfo();
            if(deviceInfo == null){
                return null;
            }
            setConnectedDevice(deviceInfo);
        }
        return getConnectedDevice();
    }

}
