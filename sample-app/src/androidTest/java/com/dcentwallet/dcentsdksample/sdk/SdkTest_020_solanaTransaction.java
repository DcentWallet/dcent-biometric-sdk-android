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
import com.dcentwallet.manager.comm.SolanaTransaction;
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
public class SdkTest_020_solanaTransaction {

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
    public void Test_002_solanaTransaction() {
        initDongleConnect();

        if( !isConnected ){
            LOG.d("Dongle is not connected");
            fail();
        }

        try{
            String keyPath = "m/44'/501'/0'";
            SolanaTransaction solanaTransaction;
            String response;

            solanaTransaction = new SolanaTransaction.Builder()
                    .keyPath(Bip44KeyPath.valueOf(keyPath))
                    .sigHash("0100010350d8ec411526b2704c9e730e1b59a2fe128cd9b5b2939a5918c54a660612177d480cc32970400e125ed89752129793ab63d351326e0d5af0aed644233955e59c0000000000000000000000000000000000000000000000000000000000000000d87cf02a1cea0c26fe671ee2ca0cf8c1fa7db58f18ae158c5808eac974d855f601020200010c0200000000e1f50500000000")
                    .fee("0.0154367")
                    .decimals(9)
                    .symbol("SOL")
                    .build();

            response = dcentSdkManager.getDcentManager().getSolanaSignedTransaction(CoinType.SOLANA, solanaTransaction);

            assertNotNull(response);
        }catch (DcentException dex){
            LOG.d("DcentException Code : " + Integer.toHexString(dex.get_err_code()).toUpperCase());
            fail();
        }
    }

    @Test
    public void Test_003_splTokenTransaction() {
        initDongleConnect();

        if( !isConnected ){
            LOG.d("Dongle is not connected");
            fail();
        }

        try{
            String keyPath = "m/44'/501'/0'";
            SolanaTransaction solanaTransaction;
            String response;

            solanaTransaction = new SolanaTransaction.Builder()
                    .keyPath(Bip44KeyPath.valueOf(keyPath))
                    .sigHash("010002052d5b413c6540de150c9373144d5133ca4cb830ba0f756716acea0e50d79435e51e3cd6284380940e08624cb8338b77dc332575d15fa39a0f1df15ee08fb823ee69d4ed8cec4206311d580c84b4682382b3031c5ccebc9b34e8cccc11f7047dc10f1e6b1421c04a070431265c19c5bbee1992bae8afd1cd078ef8af7047dc11f706ddf6e1d765a193d9cbe146ceeb79ac1cb485ed5f5b37913a8cf5857eff00a90000000000000000000000000000000000000000000000000000000000000000010404010302000a0c220000000000000009")
                    .fee("0.0000891")
                    .decimals(9)
                    .symbol("DCT")
                    .optionParam("01") // spl-token transaction(01)
                    .build();

            response = dcentSdkManager.getDcentManager().getSolanaSignedTransaction(CoinType.SPL_TOKEN, solanaTransaction);

            assertNotNull(response);
        }catch (DcentException dex){
            LOG.d("DcentException Code : " + Integer.toHexString(dex.get_err_code()).toUpperCase());
            fail();
        }
    }

    @Test
    public void Test_004_splTokenTransactionAssociate() {
        initDongleConnect();

        if( !isConnected ){
            LOG.d("Dongle is not connected");
            fail();
        }

        try{
            String keyPath = "m/44'/501'/0'";
            SolanaTransaction solanaTransaction;
            String response;

            solanaTransaction = new SolanaTransaction.Builder()
                    .keyPath(Bip44KeyPath.valueOf(keyPath))
                    .sigHash("0100050750d8ec411526b2704c9e730e1b59a2fe128cd9b5b2939a5918c54a660612177d300fe0972facbb328e129f807ca6bc68c90bfe34c66026bb8ca2d1e9f4a2a162ce010e60afedb22717bd63192f54145a3f965a33bb82d2c7029eb2ce1e208264000000000000000000000000000000000000000000000000000000000000000006ddf6e1d765a193d9cbe146ceeb79ac1cb485ed5f5b37913a8cf5857eff00a906a7d517192c5c51218cc94c3d4af17f58daee089ba1fd44e3dbd98a000000008c97258f4e2489f1bb3d1029148e0d830b5a1399daff1084048e7bd8dbe9f8591c5bad98975c728c115f9a16a3693ac5e46bc5386b9093496a6852ccec4b7e990106070001000203040500")
                    .fee("0.0000506")
                    .decimals(10)
                    .symbol("SOL")
                    .optionParam("02") //create Associate(02)
                    .build();

            response = dcentSdkManager.getDcentManager().getSolanaSignedTransaction(CoinType.SPL_TOKEN, solanaTransaction);

            assertNotNull(response);
        }catch (DcentException dex){
            LOG.d("DcentException Code : " + Integer.toHexString(dex.get_err_code()).toUpperCase());
            fail();
        }
    }

    @Test
    public void Test_005_splTokenTransactionForDapp() {
        initDongleConnect();

        if( !isConnected ){
            LOG.d("Dongle is not connected");
            fail();
        }

        try{
            String keyPath = "m/44'/501'/0'";
            SolanaTransaction solanaTransaction;
            String response;

            solanaTransaction = new SolanaTransaction.Builder()
                    .keyPath(Bip44KeyPath.valueOf(keyPath))
                    .sigHash("010500137842adebf146d55498bf1e9c59cb1252338d79b9f9598eebd7fb2f76940b411814a9cc38c2da6f91700622eb3b8ddff7707a7e6bfcbb4301b9c442990b92dc8cc40a4939977b82afe23c3c6bf859cc74c8757a688e46c9633bb8051138b81b76eebcaf41d63734864c18aa510ca43c89f38eb80d40c1ad95d8f34a93b8364c8894b15d072786475a09b4944c100d830a7bd843bbab2955290bfbba9bb4650ae71314562a9db4410932a5510cbccf45294213ea9c9b237775d189801be4edde0504d42854e9bad4cef8bc8d53ec576586258bdf1f37ba15e7e253487bb4ee952659e944ad9682b34349abe72246ef5b999b273ae987903504e02e6f82c635eb5a2aa9d58c2c76ade93cb3a212a0a5ba5d75a03ce1dedb219ba48fc9d7d972eb44b3ae006f4a26f61d3ed71088b47d4a01a21b78f52344ab7276d784968f3b73b3174ef9f7ecee2165c5b31505e0d1755e5725829a4a760a6b61628bb098da935f73eb0c866fb2830342292ab39aed6bd16844507bcfcc34dc3e80be29f3066bc28f140d143f584f237abf346a017098929cb8dc686ef1905a9ab9469bfe4e3bf4272e2356580b6175fda5e189259dad62bb45308cf466e8cabd1083fa25b4d51706ddf6e1d765a193d9cbe146ceeb79ac1cb485ed5f5b37913a8cf5857eff00a94157b0580f31c5fce44a62582dbcf9d78ee75943a084a393b350368d22899308850f2d6e02a47af824d09ab69dc42d70cb28cbfa249fb7ee57b9d256c12762ef444718f31bf2796cbdb84c8d1e5e2b23913531c84d40fcacc7c349ee58b214c14bd949c43602c33f207790ed16a3524ca1b9975cf121a2a90cffec7df8b68acdd4e1e0977eeb6eea0dddf7d99524fd7748c2b13dcb08146e4bda783811815e000112120e010f0203040510060708090a0b110c0d000930a1b10400000000f125b10d00000000")
                    .fee("0.00024")
                    .decimals(10)
                    .symbol("SOL")
                    .optionParam("030000000000000500") // dapp(03) + amount(0000000000000500) wai unit hex
                    .build();

            response = dcentSdkManager.getDcentManager().getSolanaSignedTransaction(CoinType.SPL_TOKEN, solanaTransaction);

            assertNotNull(response);
        }catch (DcentException dex){
            LOG.d("DcentException Code : " + Integer.toHexString(dex.get_err_code()).toUpperCase());
            fail();
        }
    }
}
