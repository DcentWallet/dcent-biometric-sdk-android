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
import com.dcentwallet.manager.comm.StacksTransaction;
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
public class SdkTest_019_stacksTransaction {

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
    public void Test_002_stacksTransaction() {
        initDongleConnect();

        if( !isConnected ){
            LOG.d("Dongle is not connected");
            fail();
        }

        try{
            String keyPath = "m/44'/5757'/0'/0/0";
            StacksTransaction stacksTransaction;
            String response;

            stacksTransaction = new StacksTransaction.Builder()
                    .keyPath(Bip44KeyPath.valueOf(keyPath))
                    .sigHash("00000000010400b8d4c2dbab9a59837ca0892080d9395199b3fa9d000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000302000000000005166c717c6701374d0db48dac262d1a85f906ae2d1e000000001ad2748031303638343134333900000000000000000000000000000000000000000000000000")
                    .fee("0.072")
                    .nonce("000000000000006c")
                    .decimals(6)
                    .authType(4)
                    .symbol("STX")
                    .build();

            response = dcentSdkManager.getDcentManager().getStacksSignedTransaction(CoinType.STACKS, stacksTransaction);

            assertNotNull(response);
        }catch (DcentException dex){
            LOG.d("DcentException Code : " + Integer.toHexString(dex.get_err_code()).toUpperCase());
            fail();
        }
    }

    @Test
    public void Test_003_stacksAssertTransaction() {
        initDongleConnect();

        if( !isConnected ){
            LOG.d("Dongle is not connected");
            fail();
        }

        try{
            String keyPath = "m/44'/5757'/0'/0/0";
            StacksTransaction stacksTransaction;
            String response;

            stacksTransaction = new StacksTransaction.Builder()
                    .keyPath(Bip44KeyPath.valueOf(keyPath))
                    .sigHash("0000000001040027b586cbbbd0902773c2faafb2c511b130c3610800000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000030200000000021608633eac058f2e6ab41613a0a537c7ea1a79cdd20f6d69616d69636f696e2d746f6b656e087472616e736665720000000401000000000000000000000000000f4240051627b586cbbbd0902773c2faafb2c511b130c361080516e3d94a92b80d0aabe8ef1b50de84449cd61ad6370a0200000006313130343239")
                    .fee("0.0139")
                    .nonce("0000000000000029")
                    .decimals(6)
                    .authType(4)
                    .symbol("MIAMI")
                    .optionParam("01") // assert transfer
                    .build();

            response = dcentSdkManager.getDcentManager().getStacksSignedTransaction(CoinType.SIP010, stacksTransaction);

            assertNotNull(response);
        }catch (DcentException dex){
            LOG.d("DcentException Code : " + Integer.toHexString(dex.get_err_code()).toUpperCase());
            fail();
        }
    }

    @Test
    public void Test_004_stacksTokenStakeTransaction() {
        initDongleConnect();

        if( !isConnected ){
            LOG.d("Dongle is not connected");
            fail();
        }

        try{
            String keyPath = "m/44'/5757'/0'/0/0";
            StacksTransaction stacksTransaction;
            String response;

            stacksTransaction = new StacksTransaction.Builder()
                    .keyPath(Bip44KeyPath.valueOf(keyPath))
                    .sigHash("0000000001040080bef4b34cdb1c8f05d79db1d608c18321bc7cfb000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000302000000000216000000000000000000000000000000000000000003706f780c64656c65676174652d737478000000040100000000000000000000000058390f000516f4d9fbd8d79ee18aa14910440d1c7484587480f80909")
                    .fee("0.001518")
                    .nonce("0000000000000000")
                    .decimals(6)
                    .authType(4)
                    .symbol("STX")
                    .optionParam("02") // token stake
                    .build();

            response = dcentSdkManager.getDcentManager().getStacksSignedTransaction(CoinType.STACKS, stacksTransaction);

            assertNotNull(response);
        }catch (DcentException dex){
            LOG.d("DcentException Code : " + Integer.toHexString(dex.get_err_code()).toUpperCase());
            fail();
        }
    }

    @Test
    public void Test_005_stacksTokenRevokeTransaction() {
        initDongleConnect();

        if( !isConnected ){
            LOG.d("Dongle is not connected");
            fail();
        }

        try{
            String keyPath = "m/44'/5757'/0'/0/0";
            StacksTransaction stacksTransaction;
            String response;

            stacksTransaction = new StacksTransaction.Builder()
                    .keyPath(Bip44KeyPath.valueOf(keyPath))
                    .sigHash("0000000001040026519da021fe218c8d5ef6f9c6814c0c8d332daa000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000302000000000216000000000000000000000000000000000000000003706f78137265766f6b652d64656c65676174652d73747800000000")
                    .fee("0.05015")
                    .nonce("0000000000000001")
                    .decimals(6)
                    .authType(4)
                    .symbol("STX")
                    .optionParam("030000000058390f00") // token revoke(03) + amount(0000000058390f00)
                    .build();

            response = dcentSdkManager.getDcentManager().getStacksSignedTransaction(CoinType.STACKS, stacksTransaction);

            assertNotNull(response);
        }catch (DcentException dex){
            LOG.d("DcentException Code : " + Integer.toHexString(dex.get_err_code()).toUpperCase());
            fail();
        }
    }

    @Test
    public void Test_006_stacksDappContractTransaction() {
        initDongleConnect();

        if( !isConnected ){
            LOG.d("Dongle is not connected");
            fail();
        }

        try{
            String keyPath = "m/44'/5757'/0'/0/0";
            StacksTransaction stacksTransaction;
            String response;

            stacksTransaction = new StacksTransaction.Builder()
                    .keyPath(Bip44KeyPath.valueOf(keyPath))
                    .sigHash("8080000000040080aa1803e945bac855fbf72734e9345ffb6fbef400000000000000370000000000011170000144ab678418bef61790c6b9395ac8c8b19577af21878ff030429b3388a2fa20e8245b8589c7d704f1bbd64054a9496f64aafd5e201d1dd81d1d7c5fdd148148d0030200000000021af220600d53d50d3c9406cafd39ff8fc27d0736aa0b626f6172642d7374616b6515636c61696d2d70656e64696e672d7265776172647300000000")
                    //.fee("0000000000016c10")
                    .fee("0.000093")
                    .nonce("0000000000000007")
                    .decimals(6)
                    .authType(4)
                    .symbol("STX")
                    .optionParam("04") // dapp contract
                    .build();

            response = dcentSdkManager.getDcentManager().getStacksSignedTransaction(CoinType.STACKS, stacksTransaction);

            assertNotNull(response);
        }catch (DcentException dex){
            LOG.d("DcentException Code : " + Integer.toHexString(dex.get_err_code()).toUpperCase());
            fail();
        }
    }
}
