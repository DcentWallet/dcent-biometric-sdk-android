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
import com.dcentwallet.manager.comm.AlgorandTransaction;
import com.dcentwallet.wam.LOG;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestName;
import org.junit.runners.MethodSorters;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class SdkTest_029_algorandTransaction {

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
    public void Test_002_algorandTransaction() {
        initDongleConnect();

        if( !isConnected ){
            LOG.d("Dongle is not connected");
            fail();
        }

        try{
            String keyPath = "m/44'/283'/0'/0/0";
            AlgorandTransaction algorandTransaction;
            String response;

            algorandTransaction = new AlgorandTransaction.Builder()
                    .keyPath(Bip44KeyPath.valueOf(keyPath))
                    .sigHash("54588aa3616d74cf000000174876e800a3666565cd03e8a26676ce01f60f1ca367656eac746573746e65742d76312e30a26768c4204863b518a4b3c84ec810f22d4f1081cb0f71f059a7ac20dec62f7f70e5093a22a26c76ce01f61304a46e6f7465c4084669727374205478a3726376c420568d5f7efc21a0928e50234dfa58764a84128d1c127971f6a26f350500d0ce24a3736e64c420302be92b2e5fb14e540554f3b652c0350fcc77ea53488fed81c97555179040c8a474797065a3706179")
                    .fee("0.001")
                    .decimals(6)
                    .symbol("ALGO")
                    .optionParam("00")
                    .build();

            response = dcentSdkManager.getDcentManager().getAlgorandSignedTransaction(CoinType.ALGORAND, algorandTransaction);

            assertNotNull(response);
        }catch (DcentException dex){
            LOG.d("DcentException Code : " + Integer.toHexString(dex.get_err_code()).toUpperCase());
            fail();
        }
    }

    @Test
    public void Test_003_algorandAssetTransaction() {
        initDongleConnect();

        if( !isConnected ){
            LOG.d("Dongle is not connected");
            fail();
        }

        try{
            String keyPath = "m/44'/283'/0'/0/0";
            AlgorandTransaction algorandAssetTransaction;
            String response;

            algorandAssetTransaction = new AlgorandTransaction.Builder()
                    .keyPath(Bip44KeyPath.valueOf(keyPath))
                    .sigHash("54588aa461616d7464a461726376c420568d5f7efc21a0928e50234dfa58764a84128d1c127971f6a26f350500d0ce24a3666565cd03e8a26676ce01f618f6a367656eac746573746e65742d76312e30a26768c4204863b518a4b3c84ec810f22d4f1081cb0f71f059a7ac20dec62f7f70e5093a22a26c76ce01f61cdea3736e64c420302be92b2e5fb14e540554f3b652c0350fcc77ea53488fed81c97555179040c8a474797065a56178666572a478616964ce11fb87c5")
                    .fee("0.001")
                    .decimals(2)
                    .symbol("DTN")
                    .optionParam("01")
                    .build();

            response = dcentSdkManager.getDcentManager().getAlgorandSignedTransaction(CoinType.ALGORAND_ASSET, algorandAssetTransaction);

            assertNotNull(response);
        }catch (DcentException dex){
            LOG.d("DcentException Code : " + Integer.toHexString(dex.get_err_code()).toUpperCase());
            fail();
        }
    }

    @Test
    public void Test_004_algorandAppTransaction() {
        initDongleConnect();

        if( !isConnected ){
            LOG.d("Dongle is not connected");
            fail();
        }

        try{
            String keyPath = "m/44'/283'/0'/0/0";
            AlgorandTransaction algorandAppTransaction;
            String response;

            algorandAppTransaction = new AlgorandTransaction.Builder()
                    .keyPath(Bip44KeyPath.valueOf(keyPath))
                    .sigHash("545889a46170616191c43a5475652053657020313220323032332031353a34303a343220474d542b303930302028eb8c80ed959cebafbceab5ad20ed919ceca480ec8b9c29a461706964ce068fee9aa3666565cd03e8a26676ce01f6204fa367656eac746573746e65742d76312e30a26768c4204863b518a4b3c84ec810f22d4f1081cb0f71f059a7ac20dec62f7f70e5093a22a26c76ce01f62437a3736e64c420568d5f7efc21a0928e50234dfa58764a84128d1c127971f6a26f350500d0ce24a474797065a46170706c")
                    .fee("0.001")
                    .decimals(2)
                    .symbol("DTN")
                    .optionParam("03")
                    .build();

            response = dcentSdkManager.getDcentManager().getAlgorandSignedTransaction(CoinType.ALGORAND_APP, algorandAppTransaction);

            assertNotNull(response);
        }catch (DcentException dex){
            LOG.d("DcentException Code : " + Integer.toHexString(dex.get_err_code()).toUpperCase());
            fail();
        }
    }
}