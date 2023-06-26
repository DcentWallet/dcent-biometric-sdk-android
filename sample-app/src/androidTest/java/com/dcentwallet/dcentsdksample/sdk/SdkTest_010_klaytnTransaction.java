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
import com.dcentwallet.manager.comm.HavahTransaction;
import com.dcentwallet.manager.comm.KlaytnTransaction;
import com.dcentwallet.manager.comm.KlaytnTxType;
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
public class SdkTest_010_klaytnTransaction {

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
    public void Test_002_klaytnTransaction() {
        initDongleConnect();

        if( !isConnected ){
            LOG.d("Dongle is not connected");
            fail();
        }

        try{
            String response;
            KlaytnTransaction klaytnTransanction;
            klaytnTransanction = new KlaytnTransaction.Builder()
                    .keyPath(Bip44KeyPath.valueOf("m/44'/8217'/0'/0/0"))
                    .nonce("0")
                    .toAddr("0x732E035fBdb9E5AB5Ef80c08f6aA081d029984dc")
                    .amount("1000000000000000000")
                    .gasLimit("25000")
                    .gasPrice("25000000000")
                    .data("")
                    .chainId(1001)
                    .build();

            response = dcentSdkManager.getDcentManager().getKlaytnSignedTransaction(CoinType.KLAYTN, klaytnTransanction);

            assertNotNull(response);
        }catch (DcentException dex){
            LOG.d("DcentException Code : " + Integer.toHexString(dex.get_err_code()).toUpperCase());
            fail();
        }
    }

    @Test
    public void Test_003_klaytnTransaction_valueTransfer() {
        initDongleConnect();

        if( !isConnected ){
            LOG.d("Dongle is not connected");
            fail();
        }

        try{
            String response;
            KlaytnTransaction klaytnTransanction;
            klaytnTransanction = new KlaytnTransaction.Builder()
                    .keyPath(Bip44KeyPath.valueOf("m/44'/8217'/1'/0/0"))
                    .nonce("0")
                    .toAddr("0x0036E93B6156A8562c33eB469Bf2248B7FC09088")
                    .amount("100000000000000000")
                    .gasLimit("25000")
                    .gasPrice("25000000000")
                    .data("0x")
                    .chainId(1001)
                    .setOptionAttribute(KlaytnTxType.TX_TYPE_VALUE_TRANSFER, "0x732E035fBdb9E5AB5Ef80c08f6aA081d029984dc", 0)
                    .build();

            response = dcentSdkManager.getDcentManager().getKlaytnSignedTransaction(CoinType.KLAYTN, klaytnTransanction);

            assertNotNull(response);
        }catch (DcentException dex){
            LOG.d("DcentException Code : " + Integer.toHexString(dex.get_err_code()).toUpperCase());
            fail();
        }
    }

    @Test
    public void Test_004_klaytnTransaction_feeDelegatedValueTransfer() {
        initDongleConnect();

        if( !isConnected ){
            LOG.d("Dongle is not connected");
            fail();
        }

        try{
            String response;
            KlaytnTransaction klaytnTransanction;
            klaytnTransanction = new KlaytnTransaction.Builder()
                    .keyPath(Bip44KeyPath.valueOf("m/44'/8217'/1'/0/0"))
                    .nonce("1")
                    .toAddr("0x008598fBAbbA5833950aA924AD87149309F9A0c4")
                    .amount("100000000000000000")
                    .gasLimit("125000")
                    .gasPrice("25000000000")
                    .data("0x")
                    .chainId(1001)
                    .setOptionAttribute(KlaytnTxType.TX_TYPE_FEEDELEGATED_VALUE_TRANSFER, "0x732E035fBdb9E5AB5Ef80c08f6aA081d029984dc", 0)
                    .build();

            response = dcentSdkManager.getDcentManager().getKlaytnSignedTransaction(CoinType.KLAYTN, klaytnTransanction);
            assertNotNull(response);

            klaytnTransanction = new KlaytnTransaction.Builder()
                    .keyPath(Bip44KeyPath.valueOf("m/44'/8217'/0'/0/0"))
                    .nonce("1")
                    .toAddr("")
                    .amount("")
                    .gasLimit("125000")
                    .gasPrice("25000000000")
                    .data(response)
                    .chainId(1001)
                    .setOptionAttribute(KlaytnTxType.TX_FEE_PAYER, "0x689dfa2C77335722f333EeA102ba8A5E13d2AD1a", 0)
                    .build();

            String response2 = dcentSdkManager.getDcentManager().getKlaytnSignedTransaction(CoinType.KLAYTN, klaytnTransanction);
            assertNotNull(response2);

        }catch (DcentException dex){
            LOG.d("DcentException Code : " + Integer.toHexString(dex.get_err_code()).toUpperCase());
            fail();
        }
    }

    @Test
    public void Test_005_klaytnTransaction_valueTransferMemo() {
        initDongleConnect();

        if( !isConnected ){
            LOG.d("Dongle is not connected");
            fail();
        }

        try{
            String response;
            KlaytnTransaction klaytnTransanction;
            klaytnTransanction = new KlaytnTransaction.Builder()
                    .keyPath(Bip44KeyPath.valueOf("m/44'/8217'/0'/0/1"))
                    .nonce("0")
                    .toAddr("0x689dfa2C77335722f333EeA102ba8A5E13d2AD1a")
                    .amount("10000000000000000")
                    .gasLimit("50000")
                    .gasPrice("25000000000")
                    .data("0x5445535420666f72206d656d6f205458") /*"TEST for memo TX"*/
                    .chainId(1001)
                    .setOptionAttribute(KlaytnTxType.TX_TYPE_VALUETRANSFER_MEMO, "0x0036E93B6156A8562c33eB469Bf2248B7FC09088", 0)
                    .build();

            response = dcentSdkManager.getDcentManager().getKlaytnSignedTransaction(CoinType.KLAYTN, klaytnTransanction);

            assertNotNull(response);
        }catch (DcentException dex){
            LOG.d("DcentException Code : " + Integer.toHexString(dex.get_err_code()).toUpperCase());
            fail();
        }
    }

    @Test
    public void Test_006_krc20Transaction_valueTransferMemo() {
        initDongleConnect();

        if( !isConnected ){
            LOG.d("Dongle is not connected");
            fail();
        }

        try{
            String response;
            KlaytnTransaction klaytnTransanction;
            klaytnTransanction = new KlaytnTransaction.Builder()
                    .keyPath(Bip44KeyPath.valueOf("m/44'/8217'/0'/0/0"))
                    .nonce("6")
                    .toAddr("0x92c1b39556d5322dac96e9e13d35a146297fcf43")
                    .amount("100000000")
                    .gasLimit("100000")
                    .gasPrice("25000000000")
                    .data("0xa9059cbb000000000000000000000000732E035fBdb9E5AB5Ef80c08f6aA081d029984dc")
                    .chainId(1001)
                    .setOptionAttribute(KlaytnTxType.TX_TYPE_SMARTCONTRACT_EXECUTION, "0x689dfa2C77335722f333EeA102ba8A5E13d2AD1a", 0)
                    .setTokenAttribute("BAOBABTOKEN", "BAO", "8")
                    .build();

            response = dcentSdkManager.getDcentManager().getKlaytnSignedTransaction(CoinType.KLAYTN_ERC20, klaytnTransanction);

            assertNotNull(response);
        }catch (DcentException dex){
            LOG.d("DcentException Code : " + Integer.toHexString(dex.get_err_code()).toUpperCase());
            fail();
        }
    }
}
