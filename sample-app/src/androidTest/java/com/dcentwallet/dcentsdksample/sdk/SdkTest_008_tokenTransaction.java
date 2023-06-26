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
import com.dcentwallet.manager.comm.EthereumTransanction;
import com.dcentwallet.manager.comm.TokenTransaction;
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
public class SdkTest_008_tokenTransaction {

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
    public void Test_002_erc20Transaction() {
        initDongleConnect();

        if( !isConnected ){
            LOG.d("Dongle is not connected");
            fail();
        }

        try{
            String keyPath = "m/44'/60'/0'/0/0";
            TokenTransaction erc20transaction;
            String response;

            erc20transaction = new TokenTransaction.Builder()
                    .keyPath(Bip44KeyPath.valueOf(keyPath))
                    .nonce("14")
                    .toAddr("0xe5c23dAa6480e45141647E5AeB321832150a28D4")
                    .amount("60000000000000000")
                    .gasLimit("70000")
                    .gasPrice("3000000000")
                    .tokenName("OmiseGO")
                    .contractAddress("0xd26114cd6ee289accf82350c8d8487fedb8a0c07")
                    .decimals("18")
                    .symbol("OMG")
                    .chainId("1")
                    .feeSymbol("ETH")
                    .build();

            response = dcentSdkManager.getDcentManager().getTokenSignedTransaction(CoinType.ERC20, erc20transaction);
            assertNotNull(response);
        }catch (DcentException dex){
            LOG.d("DcentException Code : " + Integer.toHexString(dex.get_err_code()).toUpperCase());
            fail();
        }
    }

    @Test
    public void Test_003_rrc20Transactionr() {
        initDongleConnect();

        if( !isConnected ){
            LOG.d("Dongle is not connected");
            fail();
        }

        try{
            String keyPath = "m/44'/137'/0'/0/0";
            TokenTransaction rrc20Transanction;
            String response;

            rrc20Transanction = new TokenTransaction.Builder()
                    .keyPath(Bip44KeyPath.valueOf(keyPath))
                    .nonce("4")
                    .toAddr("0x31f9eac21857d55efCebaE8234BE08B4940D3954")
                    .amount("46000000000000000")
                    .gasLimit("70000")
                    .gasPrice("65164000")
                    .tokenName("RIF")
                    .contractAddress("0x2acc95758f8b5f583470ba265eb685a8f45fc9d5")
                    .decimals("18")
                    .symbol("RIF")
                    .chainId("30")
                    .feeSymbol("RBTC")
                    .build();

            response = dcentSdkManager.getDcentManager().getTokenSignedTransaction(CoinType.RRC20, rrc20Transanction);
            assertNotNull(response);
        }catch (DcentException dex){
            LOG.d("DcentException Code : " + Integer.toHexString(dex.get_err_code()).toUpperCase());
            fail();
        }
    }
}
