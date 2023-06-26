package com.dcentwallet.dcentsdksample.sdk;


import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import android.content.Context;

import androidx.test.platform.app.InstrumentationRegistry;

import com.dcentwallet.dcentsdksample.dcent.DcentSdkManager;
import com.dcentwallet.manager.DcentException;
import com.dcentwallet.manager.DcentManager;
import com.dcentwallet.manager.comm.Bip44KeyPath;
import com.dcentwallet.manager.comm.CoinType;
import com.dcentwallet.manager.comm.XrpTransaction;
import com.dcentwallet.wam.LOG;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestName;
import org.junit.runners.MethodSorters;

import java.util.HashMap;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class SdkTest_009_xrp_Transaction {

    private static Context appContext;
    private static boolean isConnected = false;
    private static DcentConnectObserver dcentConnectObserver;
    private static DcentSdkManager dcentSdkManager;

    static class DcentConnectObserver implements DcentManager.Observer{

        @Override
        public void dcentDongleConnected(String deviceName) {
            LOG.i("dcentDongleConnected : " + deviceName);
            isConnected = true;
            dcentSdkManager.setConnectedDeviceName(deviceName);
        }

        @Override
        public void dcentDongleDisconnected(String deviceName) {
            LOG.i("dcentDongleDisconnected" );
            isConnected = false;
            dcentSdkManager.setConnectedDeviceName(null);
        }
    }

    @Rule
    public TestName name = new TestName();

    @BeforeClass
    public static void init() throws Exception {
        appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();

        dcentSdkManager = DcentSdkManager.getInstance();
        dcentSdkManager.initialize(appContext);

    }

    private void initDongleConnect(){

        long startTime;
        long endTime;
        long waitingTime;

        dcentConnectObserver = new DcentConnectObserver();
        dcentSdkManager.setDcentManagerSubscribe(dcentConnectObserver);
        dcentSdkManager.deviceInitialize();

        startTime = System.currentTimeMillis();
        while (true){
            if(isConnected){
                break;
            }

            endTime = System.currentTimeMillis();
            waitingTime = endTime - startTime;
            // 동글이 연결안된 상태에서 테스트하는 경우도 있으므로..20초만 동글연결을 기다림.
            // waiting
            if(waitingTime > 20000){
                break;
            }
        }
    }

    @Before
    public void setUp() throws Exception {
    }

    @AfterClass
    public static void tearDown() throws Exception {
        if( isConnected ){
            dcentSdkManager.setDcentManagerUnSubscribe(dcentConnectObserver);
            dcentSdkManager.disconnectDevice();
        }
    }

    @Test
    public void Test_001_initialize() {
        try {
            DcentSdkManager dcentSdkManager = DcentSdkManager.getInstance();
            assertNotNull(dcentSdkManager);
        } catch ( Exception ex ) {
            assertTrue(false) ;
        }
    }

    @Test
    public void Test_002_xrpTransaction() {
        initDongleConnect();

        if( !isConnected ){
            LOG.d("Dongle is not connected");
            fail();
        }

        try{
            String keyPath = "m/44'/144'/0'/0/0";
            XrpTransaction xrpTransaction;
            String response;

            String address = DcentSdkManager.getInstance().getDcentManager().getAddress(CoinType.XRP, Bip44KeyPath.valueOf(keyPath)).get("address");

            xrpTransaction = new XrpTransaction.Builder()
                    .keyPath(Bip44KeyPath.valueOf(keyPath))
                    .sourceAddress(address)
                    .destinationAddress("rsHXBk5vnswg5SZxUQCEPYVnmrd4PaZ7Ah")
                    .amountDrops(2)
                    .feeDrops(10)
                    .sequence(11)
                    .destinationTag(-1)
                    .build();

            response =dcentSdkManager.getDcentManager().getXrpSignedTransaction(CoinType.XRP, xrpTransaction);

            assertNotNull(response);
        }catch (DcentException dex){
            LOG.d("DcentException Code : " + Integer.toHexString(dex.get_err_code()).toUpperCase());
            fail();
        }
    }

    @Test
    public void Test_003_xrpTransactionr() {
        initDongleConnect();

        if( !isConnected ){
            LOG.d("Dongle is not connected");
            fail();
        }

        try{
            String keyPath = "m/44'/144'/0'/0/0";
            XrpTransaction rippleTransaction;
            HashMap<String, String> response;

            rippleTransaction = new XrpTransaction.Builder()
                    .keyPath(Bip44KeyPath.valueOf("m/44'/144'/0'/0/0"))
                    .unsignedTx("1200002280000000240238634E2E00000000201B023863E161400000000098968068400000000000000A8114FD970F4612987680F4008BA53ED6FD87BE0DAAF983141DEE2154B117FB47FCF4F19CD983D9FCBB894FF7")
                    .build();

            response = dcentSdkManager.getDcentManager().getXRPSignedTransactionWithUnsignedTx(CoinType.XRP, rippleTransaction);
            assertNotNull(response);
            assertNotNull(response.get("sign"));
            assertNotNull(response.get("pubkey"));
            assertNotNull(response.get("accountId"));
        }catch (DcentException dex){
            LOG.d("DcentException Code : " + Integer.toHexString(dex.get_err_code()).toUpperCase());
            fail();
        }
    }
}
