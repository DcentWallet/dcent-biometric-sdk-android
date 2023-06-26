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
import com.dcentwallet.manager.comm.ConfluxCrc20Transaction;
import com.dcentwallet.manager.comm.ConfluxTransaction;
import com.dcentwallet.manager.comm.HavahTransaction;
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
public class SdkTest_022_confluxTransaction {

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
    public void Test_002_confluxTransaction() {
        initDongleConnect();

        if( !isConnected ){
            LOG.d("Dongle is not connected");
            fail();
        }

        try{
            String keyPath = "m/44'/60'/0'/0/0";
            ConfluxTransaction confluxTransaction;
            String response;

            confluxTransaction = new ConfluxTransaction.Builder()
                    .keyPath(Bip44KeyPath.valueOf(keyPath))
                    .nonce("0")
                    .gasPrice("1")
                    .gasLimit("53000")
                    .toAddr("cfx:aath39ukgygwae5jnhuj1z02vkkc68wsu62j5spdng")
                    .amount("499993640000000000000")
                    .storageLimit("0")
                    .epochHeight("57744640")
                    .chainId("1029")
                    .data("")
                    .build();

            response = dcentSdkManager.getDcentManager().getConfluxSignedTransaction(CoinType.CONFLUX, confluxTransaction);

            assertNotNull(response);
        }catch (DcentException dex){
            LOG.d("DcentException Code : " + Integer.toHexString(dex.get_err_code()).toUpperCase());
            fail();
        }
    }

    @Test
    public void Test_003_crc20Transaction() {
        initDongleConnect();

        if( !isConnected ){
            LOG.d("Dongle is not connected");
            fail();
        }

        try{
            String keyPath = "m/44'/60'/0'/0/0";
            ConfluxCrc20Transaction confluxCrc20Transaction;
            String response;

            confluxCrc20Transaction = new ConfluxCrc20Transaction.Builder()
                    .keyPath(Bip44KeyPath.valueOf(keyPath))
                    .nonce("0")
                    .gasPrice("1")
                    .gasLimit("53000")
                    .toAddr("cfx:acdeewzdr3cv7hvc12kdwp7gzysjaexz9jw7s1uaft")
                    .amount("0")
                    .storageLimit("0")
                    .epochHeight("58463079")
                    .chainId("1029")
                    .data("")
                    .name("IoTestCoin")
                    .contractAddress("cfx:acdeewzdr3cv7hvc12kdwp7gzysjaexz9jw7s1uaft")
                    .toAddressForCrc20("cfx:aath39ukgygwae5jnhuj1z02vkkc68wsu62j5spdng")
                    .decimals("18")
                    .value("5000000000000000000")
                    .symbol("ITC")
                    .build();

            response = dcentSdkManager.getDcentManager().getConfluxCrc20SignedTransaction(CoinType.CFX_CRC20, confluxCrc20Transaction);

            assertNotNull(response);
        }catch (DcentException dex){
            LOG.d("DcentException Code : " + Integer.toHexString(dex.get_err_code()).toUpperCase());
            fail();
        }
    }
}
