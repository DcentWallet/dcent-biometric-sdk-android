package com.dcentwallet.dcentsdksample.dcent;

import android.util.Log;

import com.dcentwallet.manager.DcentException;
import com.dcentwallet.manager.DcentManager;
import com.dcentwallet.manager.comm.Bip44KeyPath;
import com.dcentwallet.manager.comm.CoinType;
import com.dcentwallet.manager.comm.KlaytnTransaction;
import com.dcentwallet.manager.comm.KlaytnTxType;

import java.util.ArrayList;
import java.util.List;

public class TestKlaytnTransaction {
    private static final String cName = "TestKlaytnTransaction";

    private static void checkResponse(List<String> result, String req1, String rep2){
        Log.v(cName, "\n[req][" + req1 +"]\n");
        Log.v(cName, "[Result][" + rep2 +"]\n");

        if (req1.equalsIgnoreCase(rep2))
        {
            result.add("<font color=\"#4169E1\">" + "[Assert] Success"+"</font><br>");
        }
        else
        {
            result.add("<font color=\"#FF4500\">" + "[Assert] Fail"+"</font><br>");
        }
    }

    public static List<String> testLegacyTransaction(DcentManager mDcentmanager) throws DcentException {
        Log.i(cName, "Enter [testLegacyTransaction]");
        List<String> responseList = new ArrayList<>() ;
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

        //klaytnTransanction.setOptionAttribute(KlaytnTransaction.TX_TYPE_LEGACY, "0x732E035fBdb9E5AB5Ef80c08f6aA081d029984dc", 0);

        response = mDcentmanager.getKlaytnSignedTransaction(CoinType.KLAYTN, klaytnTransanction);

        /*String assert_return ="0xf86e808505d21dba008261a894732e035fbdb9e5ab5ef80c08f6aa081d029984dc880de0b6b3a7640000808207f6a073b3d5ea210fc7e3bc38e2b5345d139d4f1bef1a77ab22f5aca53a46d515aca2a03ce71913741044a8923ae3fa63509fd3dc785805eb92d5f9a9f2a5ca27d039e2";

        checkResponse(responseList, assert_return, response);*/

        responseList.add("response : " + response + "\n");

        Log.i(cName, "Leave [testLegacyTransaction]");

        return responseList;
    }

    ///////////////////////////////////////////////////////////////////////////////
    //                               value
    ///////////////////////////////////////////////////////////////////////////////
    public static List<String> testValueTransfer(DcentManager mDcentmanager) throws DcentException {
        Log.i(cName, "Enter [testValueTransfer]");
        List<String> responseList = new ArrayList<>() ;
        String response;

        KlaytnTransaction klaytnTransanction;
        //(Bip44KeyPath hdKeyPath, String nonce, String toAddr, String amount, String gasLimit, String gasPrice, String data, int chainId)
        /*klaytnTransanction = new KlaytnTransaction(Bip44KeyPath.valueOf("m/44'/8217'/1'/0/0"), "0",
                "0x0036E93B6156A8562c33eB469Bf2248B7FC09088", "100000000000000000", "25000", "25000000000", "0x", 1001);

        klaytnTransanction.setOptionAttribute(KlaytnTransaction.TX_TYPE_VALUE_TRANSFER, "0x732E035fBdb9E5AB5Ef80c08f6aA081d029984dc", 0);*/

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

        response = mDcentmanager.getKlaytnSignedTransaction(CoinType.KLAYTN, klaytnTransanction);

       /* String assert_return ="0x08f886808505d21dba008261a8940036e93b6156a8562c33eb469bf2248b7fc0908888016345785d8a000094732e035fbdb9e5ab5ef80c08f6aa081d029984dcf847f8458207f5a0ef0d2f0e8ada71122c023c4edf31d6946269a718db14ad71d820c48b0da34987a0641ee2ea774236ecb81f7750f0930fce33650f0a438350699c5cc8e3687e2f31";

        checkResponse(responseList, assert_return, response);*/
        responseList.add("response : " + response + "\n");


        Log.i(cName, "Leave [testValueTransfer]");

        return responseList;
    }



    public static List<String> testDelegatedValueTransfer(DcentManager mDcentmanager) throws DcentException {
        Log.i(cName, "Enter [testDelegatedValueTransfer]");
        List<String> responseList = new ArrayList<>() ;
        String response;

        KlaytnTransaction klaytnTransanction;
        //(Bip44KeyPath hdKeyPath, String nonce, String toAddr, String amount, String gasLimit, String gasPrice, String data, int chainId)
        Log.i(cName, "[Sender Transaction]");

        /*klaytnTransanction = new KlaytnTransaction(Bip44KeyPath.valueOf("m/44'/8217'/1'/0/0"), "1",
                "0x008598fBAbbA5833950aA924AD87149309F9A0c4", "100000000000000000", "125000", "25000000000", "0x", 1001);

        klaytnTransanction.setOptionAttribute(KlaytnTransaction.TX_TYPE_FEEDELEGATED_VALUE_TRANSFER, "0x732E035fBdb9E5AB5Ef80c08f6aA081d029984dc", 0);*/

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

        response = mDcentmanager.getKlaytnSignedTransaction(CoinType.KLAYTN, klaytnTransanction);


        /*String assert_return ="0x09f887018505d21dba008301e84894008598fbabba5833950aa924ad87149309f9a0c488016345785d8a000094732e035fbdb9e5ab5ef80c08f6aa081d029984dcf847f8458207f6a02bd7a47bf55c148588d6020fe0a0251186594b7b7f9eff8e9c730bba658a96f7a070b2005cba2fe1694b9ab3d0e9a6177dcc0a1c7b7061fc9d587f13eb72a281cf";

        checkResponse(responseList, assert_return, response);*/
        responseList.add("response : " + response + "\n");

        //
        Log.i(cName, "[Fee Payer Transaction]");

       /* klaytnTransanction = new KlaytnTransaction(Bip44KeyPath.valueOf("m/44'/8217'/0'/0/0"), "1",
                "", "", "125000", "25000000000", response, 1001);

        klaytnTransanction.setOptionAttribute(KlaytnTransaction.TX_FEE_PAYER, "0x689dfa2C77335722f333EeA102ba8A5E13d2AD1a", 0);*/

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

        response = mDcentmanager.getKlaytnSignedTransaction(CoinType.KLAYTN, klaytnTransanction);

       /* assert_return ="0x09f8e5018505d21dba008301e84894008598fbabba5833950aa924ad87149309f9a0c488016345785d8a000094732e035fbdb9e5ab5ef80c08f6aa081d029984dcf847f8458207f6a02bd7a47bf55c148588d6020fe0a0251186594b7b7f9eff8e9c730bba658a96f7a070b2005cba2fe1694b9ab3d0e9a6177dcc0a1c7b7061fc9d587f13eb72a281cf94689dfa2c77335722f333eea102ba8a5e13d2ad1af847f8458207f6a09a15f57270c7076519ddfb5a1017c9ac2e707cec0589124ac6f80912817ba17ba0434ea7e774e8f3493978c5192daee15ac5e3e9076b4b2291a9b2c70ef73135dc";

        checkResponse(responseList, assert_return, response);*/
        responseList.add("response : " + response + "\n");

        Log.i(cName, "Leave [testDelegatedValueTransfer]");

        return responseList;
    }

    public static List<String> testPFDelegatedValueTransfer(DcentManager mDcentmanager) throws DcentException {
        Log.i(cName, "Enter [testPFDelegatedValueTransfer]");
        List<String> responseList = new ArrayList<>() ;
        String response;

        KlaytnTransaction klaytnTransanction;
        //(Bip44KeyPath hdKeyPath, String nonce, String toAddr, String amount, String gasLimit, String gasPrice, String data, int chainId)
        Log.i(cName, "[Sender Transaction]");

      /*  klaytnTransanction = new KlaytnTransaction(Bip44KeyPath.valueOf("m/44'/8217'/0'/0/0"), "1",
                "0x732E035fBdb9E5AB5Ef80c08f6aA081d029984dc", "100000000000000000", "50000", "25000000000", null, 1001);

        klaytnTransanction.setOptionAttribute(KlaytnTransaction.TX_TYPE_FEEDELEGATED_VALUETRANSFER_WITH_RATIO, "0x689dfa2C77335722f333EeA102ba8A5E13d2AD1a", 70);
*/
        klaytnTransanction = new KlaytnTransaction.Builder()
                .keyPath(Bip44KeyPath.valueOf("m/44'/8217'/0'/0/0"))
                .nonce("1")
                .toAddr("0x732E035fBdb9E5AB5Ef80c08f6aA081d029984dc")
                .amount("100000000000000000")
                .gasLimit("50000")
                .gasPrice("25000000000")
                .chainId(1001)
                .setOptionAttribute(KlaytnTxType.TX_TYPE_FEEDELEGATED_VALUETRANSFER_WITH_RATIO, "0x689dfa2C77335722f333EeA102ba8A5E13d2AD1a", 70)
                .build();


        response = mDcentmanager.getKlaytnSignedTransaction(CoinType.KLAYTN, klaytnTransanction);


        /*String assert_return ="0x0af887018505d21dba0082c35094732e035fbdb9e5ab5ef80c08f6aa081d029984dc88016345785d8a000094689dfa2c77335722f333eea102ba8a5e13d2ad1a46f847f8458207f5a0a3a7e745e2113fdff58ae2e2e1e3c54ef017f8327920634a0aa1a07581893344a061bdfa8edbc91f997b60f7a1e224303d81f65ddebd968fc7b650b2ea0ba189e0";

        checkResponse(responseList, assert_return, response);*/
        responseList.add("response : " + response + "\n");

        //
        Log.i(cName, "[Fee Payer Transaction]");

        /*klaytnTransanction = new KlaytnTransaction(Bip44KeyPath.valueOf("m/44'/8217'/1'/0/0"), "0",
                "0x", null, "0", "0", response, 1001);

        klaytnTransanction.setOptionAttribute(KlaytnTransaction.TX_FEE_PAYER, "0x732E035fBdb9E5AB5Ef80c08f6aA081d029984dc", 0);
*/
        klaytnTransanction = new KlaytnTransaction.Builder()
                .keyPath(Bip44KeyPath.valueOf("m/44'/8217'/1'/0/0"))
                .nonce("0")
                .toAddr("0x")
                .gasLimit("0")
                .gasPrice("0")
                .data(response)
                .chainId(1001)
                .setOptionAttribute(KlaytnTxType.TX_FEE_PAYER, "0x732E035fBdb9E5AB5Ef80c08f6aA081d029984dc", 0)
                .build();

        response = mDcentmanager.getKlaytnSignedTransaction(CoinType.KLAYTN, klaytnTransanction);

        /*assert_return ="0x0af8e5018505d21dba0082c35094732e035fbdb9e5ab5ef80c08f6aa081d029984dc88016345785d8a000094689dfa2c77335722f333eea102ba8a5e13d2ad1a46f847f8458207f5a0a3a7e745e2113fdff58ae2e2e1e3c54ef017f8327920634a0aa1a07581893344a061bdfa8edbc91f997b60f7a1e224303d81f65ddebd968fc7b650b2ea0ba189e094732e035fbdb9e5ab5ef80c08f6aa081d029984dcf847f8458207f6a0b6349f79a6a0d781bf317e62ef848daad1f74bea25db9b971d9bdf09fb551870a04ca1df0feb240fc60d3ed57396395085d12ebc94c003626e4b9e6b0547cd7889";

        checkResponse(responseList, assert_return, response);*/
        responseList.add("response : " + response + "\n");

        Log.i(cName, "Leave [testPFDelegatedValueTransfer]");

        return responseList;
    }


    ///////////////////////////////////////////////////////////////////////////////
    //                               value with memo
    ///////////////////////////////////////////////////////////////////////////////
    public static List<String> testValueMemoTx(DcentManager mDcentmanager) throws DcentException {
        Log.i(cName, "Enter [testValueMemoTx]");
        List<String> responseList = new ArrayList<>() ;
        String response;

        KlaytnTransaction klaytnTransanction;
        //(Bip44KeyPath hdKeyPath, String nonce, String toAddr, String amount, String gasLimit, String gasPrice, String data, int chainId)
       /* klaytnTransanction = new KlaytnTransaction(Bip44KeyPath.valueOf("m/44'/8217'/0'/0/1"), "0",
                "0x689dfa2C77335722f333EeA102ba8A5E13d2AD1a", "10000000000000000", "50000", "25000000000",
                "0x5445535420666f72206d656d6f205458"*//*"TEST for memo TX"*//*, 1001);

        klaytnTransanction.setOptionAttribute(KlaytnTransaction.TX_TYPE_VALUETRANSFER_MEMO, "0x0036E93B6156A8562c33eB469Bf2248B7FC09088", 0);*/

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


        response = mDcentmanager.getKlaytnSignedTransaction(CoinType.KLAYTN, klaytnTransanction);


        /*String assert_return ="0x10f896808505d21dba0082c35094689dfa2c77335722f333eea102ba8a5e13d2ad1a872386f26fc10000940036e93b6156a8562c33eb469bf2248b7fc09088905445535420666f72206d656d6f205458f847f8458207f6a0612b2e38bdb1b8f8f3f5654f453f502c105f4200108339c29ce0b732e6267759a0276d20964fd1d5a7ea1c819fcab9584d7c5f563c645af3fb139fd3a1536b71d3";

        checkResponse(responseList, assert_return, response);*/
        responseList.add("response : " + response + "\n");


        Log.i(cName, "Leave [testValueMemoTx]");

        return responseList;
    }


    public static List<String> testDelegatedValueMemoTx(DcentManager mDcentmanager) throws DcentException {
        Log.i(cName, "Enter [testValueTransfer]");
        List<String> responseList = new ArrayList<>() ;
        String response;

        KlaytnTransaction klaytnTransanction;
        //(Bip44KeyPath hdKeyPath, String nonce, String toAddr, String amount, String gasLimit, String gasPrice, String data, int chainId)
        Log.i(cName, "[Sender Transaction]");

     /*   klaytnTransanction = new KlaytnTransaction(Bip44KeyPath.valueOf("m/44'/8217'/0'/0/1"), "1",
                "0x689dfa2C77335722f333EeA102ba8A5E13d2AD1a", "10000000000000000", "50000", "25000000000", "FEE DELEGATE With MEMO!!", 1001);

        klaytnTransanction.setOptionAttribute(KlaytnTransaction.TX_TYPE_FEEDELEGATED_VALUETRANSFER_MEMO, "0x0036E93B6156A8562c33eB469Bf2248B7FC09088", 0);*/

        klaytnTransanction = new KlaytnTransaction.Builder()
                .keyPath(Bip44KeyPath.valueOf("m/44'/8217'/0'/0/1"))
                .nonce("1")
                .toAddr("0x689dfa2C77335722f333EeA102ba8A5E13d2AD1a")
                .amount("10000000000000000")
                .gasLimit("50000")
                .gasPrice("25000000000")
                .data("FEE DELEGATE With MEMO!!")
                .chainId(1001)
                .setOptionAttribute(KlaytnTxType.TX_TYPE_FEEDELEGATED_VALUETRANSFER_MEMO, "0x0036E93B6156A8562c33eB469Bf2248B7FC09088", 0)
                .build();

        response = mDcentmanager.getKlaytnSignedTransaction(CoinType.KLAYTN, klaytnTransanction);

        /*String assert_return ="0x11f89e018505d21dba0082c35094689dfa2c77335722f333eea102ba8a5e13d2ad1a872386f26fc10000940036e93b6156a8562c33eb469bf2248b7fc09088984645452044454c45474154452057697468204d454d4f2121f847f8458207f5a0ef20b1f0467de4d3f78ed702f380f2c16aae88e4c74a256dfb9889c70c6a2acaa01948bb825237269e7fef5265c33a66624825e2d1d071887d24a48c01554860f6";

        checkResponse(responseList, assert_return, response);*/
        responseList.add("response : " + response + "\n");

        //
        Log.i(cName, "[Fee Payer Transaction]");

        /*klaytnTransanction = new KlaytnTransaction(Bip44KeyPath.valueOf("m/44'/8217'/1'/0/0"), "1",
                "0x", null, "0", "0", response, 1001);

        klaytnTransanction.setOptionAttribute(KlaytnTransaction.TX_FEE_PAYER, "0x732E035fBdb9E5AB5Ef80c08f6aA081d029984dc", 0);*/

        klaytnTransanction = new KlaytnTransaction.Builder()
                .keyPath(Bip44KeyPath.valueOf("m/44'/8217'/1'/0/0"))
                .nonce("1")
                .toAddr("0x")
                .gasLimit("0")
                .gasPrice("0")
                .data(response)
                .chainId(1001)
                .setOptionAttribute(KlaytnTxType.TX_FEE_PAYER, "0x732E035fBdb9E5AB5Ef80c08f6aA081d029984dc", 0)
                .build();


        response = mDcentmanager.getKlaytnSignedTransaction(CoinType.KLAYTN, klaytnTransanction);

        /*assert_return ="0x11f8fc018505d21dba0082c35094689dfa2c77335722f333eea102ba8a5e13d2ad1a872386f26fc10000940036e93b6156a8562c33eb469bf2248b7fc09088984645452044454c45474154452057697468204d454d4f2121f847f8458207f5a0ef20b1f0467de4d3f78ed702f380f2c16aae88e4c74a256dfb9889c70c6a2acaa01948bb825237269e7fef5265c33a66624825e2d1d071887d24a48c01554860f694732e035fbdb9e5ab5ef80c08f6aa081d029984dcf847f8458207f5a04a1fcbce04802a264cfc1ed055529fc89685010ab8692424d007450189fde2d3a06f3499e03121bb3adb94a190c4e5b086442347f4dbeb68b6323986a7e2e3e768";

        checkResponse(responseList, assert_return, response);*/
        responseList.add("response : " + response + "\n");

        Log.i(cName, "Leave [testValueTransfer]");

        return responseList;
    }

    public static List<String> testPFDelegatedValueMemoTx(DcentManager mDcentmanager) throws DcentException {
        Log.i(cName, "Enter [testValueTransfer]");
        List<String> responseList = new ArrayList<>() ;
        String response;

        KlaytnTransaction klaytnTransanction;
        //(Bip44KeyPath hdKeyPath, String nonce, String toAddr, String amount, String gasLimit, String gasPrice, String data, int chainId)
        Log.i(cName, "[Sender Transaction]");

        /*klaytnTransanction = new KlaytnTransaction(Bip44KeyPath.valueOf("m/44'/8217'/0'/0/1"), "2",
                "0x689dfa2C77335722f333EeA102ba8A5E13d2AD1a", "10000000000000000", "70000", "25000000000", "Partial FEE DELEGATE With MEMO!!", 1001);

        klaytnTransanction.setOptionAttribute(KlaytnTransaction.TX_TYPE_FEEDELEGATED_VALUETRANSFER_MEMO_WITH_RATIO, "0x0036E93B6156A8562c33eB469Bf2248B7FC09088", 70);*/

        klaytnTransanction = new KlaytnTransaction.Builder()
                .keyPath(Bip44KeyPath.valueOf("m/44'/8217'/0'/0/1"))
                .nonce("2")
                .toAddr("0x689dfa2C77335722f333EeA102ba8A5E13d2AD1a")
                .amount("10000000000000000")
                .gasLimit("70000")
                .gasPrice("25000000000")
                .data("Partial FEE DELEGATE With MEMO!!")
                .chainId(1001)
                .setOptionAttribute(KlaytnTxType.TX_TYPE_FEEDELEGATED_VALUETRANSFER_MEMO_WITH_RATIO, "0x0036E93B6156A8562c33eB469Bf2248B7FC09088", 70)
                .build();

        response = mDcentmanager.getKlaytnSignedTransaction(CoinType.KLAYTN, klaytnTransanction);

        /*String assert_return ="0x12f8a8028505d21dba008301117094689dfa2c77335722f333eea102ba8a5e13d2ad1a872386f26fc10000940036e93b6156a8562c33eb469bf2248b7fc09088a05061727469616c204645452044454c45474154452057697468204d454d4f212146f847f8458207f5a0c097e9f28090653ed4cd00d1d330227f2f85ddf0453145c6cd2f789546d00aa0a068bd4da21696db210387297748ec1439ac7a3290d09151842b846654e6a33b28";

        checkResponse(responseList, assert_return, response);*/
        responseList.add("response : " + response + "\n");

        //
        Log.i(cName, "[Fee Payer Transaction]");

       /* klaytnTransanction = new KlaytnTransaction(Bip44KeyPath.valueOf("m/44'/8217'/1'/0/0"), "0",
                "0x", "0x", "0", "0", response, 1001);

        klaytnTransanction.setOptionAttribute(KlaytnTransaction.TX_FEE_PAYER, "0x732E035fBdb9E5AB5Ef80c08f6aA081d029984dc", 70);*/

        klaytnTransanction = new KlaytnTransaction.Builder()
                .keyPath(Bip44KeyPath.valueOf("m/44'/8217'/1'/0/0"))
                .nonce("0")
                .toAddr("0x")
                .amount("0x")
                .gasLimit("0")
                .gasPrice("0")
                .data(response)
                .chainId(1001)
                .setOptionAttribute(KlaytnTxType.TX_FEE_PAYER, "0x732E035fBdb9E5AB5Ef80c08f6aA081d029984dc", 70)
                .build();

        response = mDcentmanager.getKlaytnSignedTransaction(CoinType.KLAYTN, klaytnTransanction);

        /*assert_return ="0x12f90106028505d21dba008301117094689dfa2c77335722f333eea102ba8a5e13d2ad1a872386f26fc10000940036e93b6156a8562c33eb469bf2248b7fc09088a05061727469616c204645452044454c45474154452057697468204d454d4f212146f847f8458207f5a0c097e9f28090653ed4cd00d1d330227f2f85ddf0453145c6cd2f789546d00aa0a068bd4da21696db210387297748ec1439ac7a3290d09151842b846654e6a33b2894732e035fbdb9e5ab5ef80c08f6aa081d029984dcf847f8458207f6a0b648499120283b7c83febaf8eba93426d02649a92f7b8c6a4596f658cb21a585a045d0bbcde4d4e37fbc9a8f0c2bac1bdfbd7279b5c023fc9260a6a31300d742d1";

        checkResponse(responseList, assert_return, response);*/
        responseList.add("response : " + response + "\n");

        Log.i(cName, "Leave [testValueTransfer]");

        return responseList;
    }

    ///////////////////////////////////////////////////////////////////////////////
    //                               Cancel
    ///////////////////////////////////////////////////////////////////////////////
    public static List<String> testCancelTx(DcentManager mDcentmanager) throws DcentException {
        Log.i(cName, "Enter [testValueMemoTx]");
        List<String> responseList = new ArrayList<>() ;
        String response;

        KlaytnTransaction klaytnTransanction;
        //(Bip44KeyPath hdKeyPath, String nonce, String toAddr, String amount, String gasLimit, String gasPrice, String data, int chainId)
       /* klaytnTransanction = new KlaytnTransaction(Bip44KeyPath.valueOf("m/44'/8217'/0'/0/0"), "2",
                null, null, "50000", "25000000000",
                null, 1001);

        klaytnTransanction.setOptionAttribute(KlaytnTransaction.TX_TYPE_CANCEL, "0x689dfa2C77335722f333EeA102ba8A5E13d2AD1a", 0);*/

        klaytnTransanction = new KlaytnTransaction.Builder()
                .keyPath(Bip44KeyPath.valueOf("\"m/44'/8217'/0'/0/0"))
                .nonce("2")
                .gasLimit("50000")
                .gasPrice("25000000000")
                .chainId(1001)
                .setOptionAttribute(KlaytnTxType.TX_TYPE_CANCEL, "0x689dfa2C77335722f333EeA102ba8A5E13d2AD1a", 0)
                .build();

        response = mDcentmanager.getKlaytnSignedTransaction(CoinType.KLAYTN, klaytnTransanction);

        /*String assert_return ="0x38f868028505d21dba0082c35094689dfa2c77335722f333eea102ba8a5e13d2ad1af847f8458207f6a076341da963370dffc304c222db9e368b04612ccac8baee0f797ca6e8fc28386aa035f4738ad71135bb904daf463c93cf0d9c7e7a27cd101331a614284d0611bac0";

        checkResponse(responseList, assert_return, response);*/
        responseList.add("response : " + response + "\n");


        Log.i(cName, "Leave [testValueMemoTx]");

        return responseList;
    }


    public static List<String> testDelegatedCancelTx(DcentManager mDcentmanager) throws DcentException {
        Log.i(cName, "Enter [testValueTransfer]");
        List<String> responseList = new ArrayList<>() ;
        String response;

        KlaytnTransaction klaytnTransanction;
        //(Bip44KeyPath hdKeyPath, String nonce, String toAddr, String amount, String gasLimit, String gasPrice, String data, int chainId)
        Log.i(cName, "[Sender Transaction]");

       /* klaytnTransanction = new KlaytnTransaction(Bip44KeyPath.valueOf("m/44'/8217'/0'/0/0"), "3",
                "0x", "10000000000000000", "50000", "25000000000", "0x", 1001);

        klaytnTransanction.setOptionAttribute(KlaytnTransaction.TX_TYPE_FEEDELEGATED_CANCEL, "0x689dfa2C77335722f333EeA102ba8A5E13d2AD1a", 70);*/

        klaytnTransanction = new KlaytnTransaction.Builder()
                .keyPath(Bip44KeyPath.valueOf("m/44'/8217'/0'/0/0"))
                .nonce("3")
                .toAddr("0x")
                .amount("10000000000000000")
                .gasLimit("50000")
                .gasPrice("25000000000")
                .data("0x")
                .chainId(1001)
                .setOptionAttribute(KlaytnTxType.TX_TYPE_FEEDELEGATED_CANCEL, "0x689dfa2C77335722f333EeA102ba8A5E13d2AD1a", 70)
                .build();

        response = mDcentmanager.getKlaytnSignedTransaction(CoinType.KLAYTN, klaytnTransanction);

        /*String assert_return ="0x39f868038505d21dba0082c35094689dfa2c77335722f333eea102ba8a5e13d2ad1af847f8458207f5a05bb8b1eabc60804dee1929b70e62bf5a18c31c65b8f635e7bd26db5759f88b81a01581d8be2948675e3056fe9ded9898aee19144936223f7904b2c1a8d86d35d2c";

        checkResponse(responseList, assert_return, response);*/
        responseList.add("response : " + response + "\n");

        //
        Log.i(cName, "[Fee Payer Transaction]");

        /*klaytnTransanction = new KlaytnTransaction(Bip44KeyPath.valueOf("m/44'/8217'/1'/0/0"), "1",
                "0x", null, "0", "0", response, 1001);

        klaytnTransanction.setOptionAttribute(KlaytnTransaction.TX_FEE_PAYER, "0x732E035fBdb9E5AB5Ef80c08f6aA081d029984dc", 70);*/

        klaytnTransanction = new KlaytnTransaction.Builder()
                .keyPath(Bip44KeyPath.valueOf("m/44'/8217'/1'/0/0"))
                .nonce("1")
                .toAddr("0x")
                .gasLimit("0")
                .gasPrice("0")
                .data(response)
                .chainId(1001)
                .setOptionAttribute(KlaytnTxType.TX_FEE_PAYER, "0x732E035fBdb9E5AB5Ef80c08f6aA081d029984dc", 70)
                .build();

        response = mDcentmanager.getKlaytnSignedTransaction(CoinType.KLAYTN, klaytnTransanction);

        /*assert_return ="0x39f8c6038505d21dba0082c35094689dfa2c77335722f333eea102ba8a5e13d2ad1af847f8458207f5a05bb8b1eabc60804dee1929b70e62bf5a18c31c65b8f635e7bd26db5759f88b81a01581d8be2948675e3056fe9ded9898aee19144936223f7904b2c1a8d86d35d2c94732e035fbdb9e5ab5ef80c08f6aa081d029984dcf847f8458207f5a0a2d07bf5597421b0b636e999896a66645c76572d567cb851bb4f3f6b171b3d9ba0544f1a9d170b074251bc0e8384cb08ecb925bfb1271402ca5082e122aaef423c";

        checkResponse(responseList, assert_return, response);*/
        responseList.add("response : " + response + "\n");

        Log.i(cName, "Leave [testValueTransfer]");

        return responseList;
    }

    public static List<String> testPFDelegatedCancelTx(DcentManager mDcentmanager) throws DcentException {
        Log.i(cName, "Enter [testValueTransfer]");
        List<String> responseList = new ArrayList<>() ;
        String response;

        KlaytnTransaction klaytnTransanction;
        //(Bip44KeyPath hdKeyPath, String nonce, String toAddr, String amount, String gasLimit, String gasPrice, String data, int chainId)
        Log.i(cName, "[Sender Transaction]");

        /*klaytnTransanction = new KlaytnTransaction(Bip44KeyPath.valueOf("m/44'/8217'/0'/0/0"), "4",
                "", "0", "50000", "25000000000", "", 1001);

        klaytnTransanction.setOptionAttribute(KlaytnTransaction.TX_TYPE_FEEDELEGATED_CANCEL_WITH_RATIO, "0x689dfa2C77335722f333EeA102ba8A5E13d2AD1a", 70);*/

        klaytnTransanction = new KlaytnTransaction.Builder()
                .keyPath(Bip44KeyPath.valueOf("m/44'/8217'/0'/0/0"))
                .nonce("4")
                .toAddr("")
                .amount("0")
                .gasLimit("50000")
                .gasPrice("25000000000")
                .data("")
                .chainId(1001)
                .setOptionAttribute(KlaytnTxType.TX_TYPE_FEEDELEGATED_CANCEL_WITH_RATIO, "0x689dfa2C77335722f333EeA102ba8A5E13d2AD1a", 70)
                .build();

        response = mDcentmanager.getKlaytnSignedTransaction(CoinType.KLAYTN, klaytnTransanction);

       /* String assert_return ="0x3af869048505d21dba0082c35094689dfa2c77335722f333eea102ba8a5e13d2ad1a46f847f8458207f6a0693ce8ed29a1616cb2bafde77c13b1be06901c0aa082aadc07ce725975eecf78a064a13a4bb6a8033c95adbb83a2d425fa98c8e446d7648d89e9c1e20f76a5721e";

        checkResponse(responseList, assert_return, response);*/
        responseList.add("response : " + response + "\n");

        //
        Log.i(cName, "[Fee Payer Transaction]");

        /*klaytnTransanction = new KlaytnTransaction(Bip44KeyPath.valueOf("m/44'/8217'/1'/0/0"), "0",
                "0x", "0x", "0", "0", response, 1001);

        klaytnTransanction.setOptionAttribute(KlaytnTransaction.TX_FEE_PAYER, "0x732E035fBdb9E5AB5Ef80c08f6aA081d029984dc", 70);*/

        klaytnTransanction = new KlaytnTransaction.Builder()
                .keyPath(Bip44KeyPath.valueOf("m/44'/8217'/1'/0/0"))
                .nonce("0")
                .toAddr("0x")
                .amount("0x")
                .gasLimit("0")
                .gasPrice("0")
                .data(response)
                .chainId(1001)
                .setOptionAttribute(KlaytnTxType.TX_FEE_PAYER, "0x732E035fBdb9E5AB5Ef80c08f6aA081d029984dc", 70)
                .build();

        response = mDcentmanager.getKlaytnSignedTransaction(CoinType.KLAYTN, klaytnTransanction);

        /*assert_return ="0x3af8c7048505d21dba0082c35094689dfa2c77335722f333eea102ba8a5e13d2ad1a46f847f8458207f6a0693ce8ed29a1616cb2bafde77c13b1be06901c0aa082aadc07ce725975eecf78a064a13a4bb6a8033c95adbb83a2d425fa98c8e446d7648d89e9c1e20f76a5721e94732e035fbdb9e5ab5ef80c08f6aa081d029984dcf847f8458207f6a085294e1eb35ac4c12cdcd3a2e14e35d14d2a5768922bdd7ab9c9d324b3c35543a01cc8fcadd0e0a61e997cd11c4652a8b91df1bc3f1d874e153baf8bef6bdca476";

        checkResponse(responseList, assert_return, response);*/
        responseList.add("response : " + response + "\n");

        Log.i(cName, "Leave [testValueTransfer]");

        return responseList;
    }


    ///////////////////////////////////////////////////////////////////////////////
    //                               KRC20
    ///////////////////////////////////////////////////////////////////////////////
    public static List<String> testKRC20Tx(DcentManager mDcentmanager) throws DcentException {
        Log.i(cName, "Enter [testValueMemoTx]");
        List<String> responseList = new ArrayList<>() ;
        String response;

        KlaytnTransaction klaytnTransanction;
        //(Bip44KeyPath hdKeyPath, String nonce, String toAddr, String amount, String gasLimit, String gasPrice, String data, int chainId)
        /*klaytnTransanction = new KlaytnTransaction(Bip44KeyPath.valueOf("m/44'/8217'/0'/0/0"), "6",
                "0x92c1b39556d5322dac96e9e13d35a146297fcf43", "100000000", "100000", "25000000000",
                "0xa9059cbb000000000000000000000000732E035fBdb9E5AB5Ef80c08f6aA081d029984dc", 1001);
        //

        klaytnTransanction.setOptionAttribute(KlaytnTransaction.TX_TYPE_SMARTCONTRACT_EXECUTION, "0x689dfa2C77335722f333EeA102ba8A5E13d2AD1a", 0);
        klaytnTransanction.setTokenAttribute("BAOBABTOKEN", "BAO", "8");*/

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

        response = mDcentmanager.getKlaytnSignedTransaction(CoinType.KLAYTN_ERC20, klaytnTransanction);

        /*String assert_return ="0x30f8c5068505d21dba00830186a09492c1b39556d5322dac96e9e13d35a146297fcf438094689dfa2c77335722f333eea102ba8a5e13d2ad1ab844a9059cbb000000000000000000000000732e035fbdb9e5ab5ef80c08f6aa081d029984dc0000000000000000000000000000000000000000000000000000000005f5e100f847f8458207f6a0c3fec7252c1ebdbd8475fbce004f781537e36fdb58c6e204f4f75240821a58c6a0729f79da900fce7fea0d9fd6d28801ca495b0e14f676b25ece7c53a3e4457a4c";

        checkResponse(responseList, assert_return, response);*/
        responseList.add("response : " + response + "\n");


        Log.i(cName, "Leave [testValueMemoTx]");

        return responseList;
    }


    public static List<String> testDelegatedKRC20Tx(DcentManager mDcentmanager) throws DcentException {
        Log.i(cName, "Enter [testValueTransfer]");
        List<String> responseList = new ArrayList<>() ;
        String response;

        KlaytnTransaction klaytnTransanction;
        //(Bip44KeyPath hdKeyPath, String nonce, String toAddr, String amount, String gasLimit, String gasPrice, String data, int chainId)
        Log.i(cName, "[Sender Transaction]");

        /*klaytnTransanction = new KlaytnTransaction(Bip44KeyPath.valueOf("m/44'/8217'/0'/0/0"), "7",
                "0x92c1b39556d5322dac96e9e13d35a146297fcf43", "100000000", "100000", "25000000000", "0xa9059cbb000000000000000000000000732E035fBdb9E5AB5Ef80c08f6aA081d029984dc", 1001);

        klaytnTransanction.setOptionAttribute(KlaytnTransaction.TX_TYPE_FEEDELEGATED_SMARTCONTRACT_EXECUTION, "0x689dfa2C77335722f333EeA102ba8A5E13d2AD1a", 70);
        klaytnTransanction.setTokenAttribute("BAOBABTOKEN", "BAO", "8");*/

        klaytnTransanction = new KlaytnTransaction.Builder()
                .keyPath(Bip44KeyPath.valueOf("m/44'/8217'/0'/0/0"))
                .nonce("7")
                .toAddr("0x92c1b39556d5322dac96e9e13d35a146297fcf43")
                .amount("100000000")
                .gasLimit("100000")
                .gasPrice("25000000000")
                .data("0xa9059cbb000000000000000000000000732E035fBdb9E5AB5Ef80c08f6aA081d029984dc")
                .chainId(1001)
                .setOptionAttribute(KlaytnTxType.TX_TYPE_FEEDELEGATED_SMARTCONTRACT_EXECUTION, "0x689dfa2C77335722f333EeA102ba8A5E13d2AD1a", 70)
                .setTokenAttribute("BAOBABTOKEN", "BAO", "8")
                .build();

        response = mDcentmanager.getKlaytnSignedTransaction(CoinType.KLAYTN_ERC20, klaytnTransanction);

        /*String assert_return ="0x31f8c5078505d21dba00830186a09492c1b39556d5322dac96e9e13d35a146297fcf438094689dfa2c77335722f333eea102ba8a5e13d2ad1ab844a9059cbb000000000000000000000000732e035fbdb9e5ab5ef80c08f6aa081d029984dc0000000000000000000000000000000000000000000000000000000005f5e100f847f8458207f5a0c75dadb184e4cf60aad49d9ed58547c85a0690bab6fe07ee9408fbcdcc13264ea0142ef4401d3bf899b30b5b33de343613185bcb59196b7b8f0bed0ad9a2e35853";

        checkResponse(responseList, assert_return, response);*/
        responseList.add("response : " + response + "\n");

        //
        Log.i(cName, "[Fee Payer Transaction]");

        /*klaytnTransanction = new KlaytnTransaction(Bip44KeyPath.valueOf("m/44'/8217'/1'/0/0"), "0",
                "0x", null, "0", "0", response, 1001);

        klaytnTransanction.setOptionAttribute(KlaytnTransaction.TX_FEE_PAYER, "0x732E035fBdb9E5AB5Ef80c08f6aA081d029984dc", 70);*/

        klaytnTransanction = new KlaytnTransaction.Builder()
                .keyPath(Bip44KeyPath.valueOf("m/44'/8217'/1'/0/0"))
                .nonce("0")
                .toAddr("0x")
                .gasLimit("0")
                .gasPrice("0")
                .data(response)
                .chainId(1001)
                .setOptionAttribute(KlaytnTxType.TX_FEE_PAYER, "0x732E035fBdb9E5AB5Ef80c08f6aA081d029984dc", 70)
                .build();

        response = mDcentmanager.getKlaytnSignedTransaction(CoinType.KLAYTN, klaytnTransanction);

       /* assert_return ="0x31f90123078505d21dba00830186a09492c1b39556d5322dac96e9e13d35a146297fcf438094689dfa2c77335722f333eea102ba8a5e13d2ad1ab844a9059cbb000000000000000000000000732e035fbdb9e5ab5ef80c08f6aa081d029984dc0000000000000000000000000000000000000000000000000000000005f5e100f847f8458207f5a0c75dadb184e4cf60aad49d9ed58547c85a0690bab6fe07ee9408fbcdcc13264ea0142ef4401d3bf899b30b5b33de343613185bcb59196b7b8f0bed0ad9a2e3585394732e035fbdb9e5ab5ef80c08f6aa081d029984dcf847f8458207f5a093bf98930874e8b71eb8f8a7a6de5c1ad870bac2fffa401a746dc4c1e338df55a056035f9ea1be100c5b94f0b5c53217d0c126dd458d3281611fd253146bd43dd5";

        checkResponse(responseList, assert_return, response);*/
        responseList.add("response : " + response + "\n");

        Log.i(cName, "Leave [testValueTransfer]");

        return responseList;
    }

    public static List<String> testPFDelegatedKRC20Tx(DcentManager mDcentmanager) throws DcentException {
        Log.i(cName, "Enter [testValueTransfer]");
        List<String> responseList = new ArrayList<>() ;
        String response;

        KlaytnTransaction klaytnTransanction;
        //(Bip44KeyPath hdKeyPath, String nonce, String toAddr, String amount, String gasLimit, String gasPrice, String data, int chainId)
        Log.i(cName, "[Sender Transaction]");

        /*klaytnTransanction = new KlaytnTransaction(Bip44KeyPath.valueOf("m/44'/8217'/0'/0/0"), "8",
                "0x92c1b39556d5322dac96e9e13d35a146297fcf43", "100000000", "100000", "25000000000", "0xa9059cbb000000000000000000000000732E035fBdb9E5AB5Ef80c08f6aA081d029984dc", 1001);

        klaytnTransanction.setOptionAttribute(KlaytnTransaction.TX_TYPE_FEEDELEGATED_SMARTCONTRACT_EXECUTION_WITH_RATIO, "0x689dfa2C77335722f333EeA102ba8A5E13d2AD1a", 70);
        klaytnTransanction.setTokenAttribute("BAOBABTOKEN", "BAO", "8");*/

        klaytnTransanction = new KlaytnTransaction.Builder()
                .keyPath(Bip44KeyPath.valueOf("m/44'/8217'/0'/0/0"))
                .nonce("8")
                .toAddr("0x92c1b39556d5322dac96e9e13d35a146297fcf43")
                .amount("100000000")
                .gasLimit("100000")
                .gasPrice("25000000000")
                .data("0xa9059cbb000000000000000000000000732E035fBdb9E5AB5Ef80c08f6aA081d029984dc")
                .chainId(1001)
                .setOptionAttribute(KlaytnTxType.TX_TYPE_FEEDELEGATED_SMARTCONTRACT_EXECUTION_WITH_RATIO, "0x689dfa2C77335722f333EeA102ba8A5E13d2AD1a", 70)
                .setTokenAttribute("BAOBABTOKEN", "BAO", "8")
                .build();


        response = mDcentmanager.getKlaytnSignedTransaction(CoinType.KLAYTN_ERC20, klaytnTransanction);

        /*String assert_return ="0x32f8c6088505d21dba00830186a09492c1b39556d5322dac96e9e13d35a146297fcf438094689dfa2c77335722f333eea102ba8a5e13d2ad1ab844a9059cbb000000000000000000000000732e035fbdb9e5ab5ef80c08f6aa081d029984dc0000000000000000000000000000000000000000000000000000000005f5e10046f847f8458207f5a040f1c433c0bcf3aa4e158a7c71ce8709cc8735532b5801ebc229d40969df8294a008a2bfd29cfc3a47cd29a27050e7546f84dc9322917e1a60e65b2d2b49c499db";

        checkResponse(responseList, assert_return, response);*/
        responseList.add("response : " + response + "\n");

        //
        Log.i(cName, "[Fee Payer Transaction]");

        /*klaytnTransanction = new KlaytnTransaction(Bip44KeyPath.valueOf("m/44'/8217'/1'/0/0"), "0",
                "0x", "0x", "0", "0", response, 1001);

        klaytnTransanction.setOptionAttribute(KlaytnTransaction.TX_FEE_PAYER, "0x732E035fBdb9E5AB5Ef80c08f6aA081d029984dc", 70);*/

        klaytnTransanction = new KlaytnTransaction.Builder()
                .keyPath(Bip44KeyPath.valueOf("m/44'/8217'/1'/0/0"))
                .nonce("0")
                .toAddr("0x")
                .amount("0x")
                .gasLimit("0")
                .gasPrice("0")
                .data(response)
                .chainId(1001)
                .setOptionAttribute(KlaytnTxType.TX_FEE_PAYER, "0x732E035fBdb9E5AB5Ef80c08f6aA081d029984dc", 70)
                .build();

        response = mDcentmanager.getKlaytnSignedTransaction(CoinType.KLAYTN, klaytnTransanction);

        /*assert_return ="0x32f90124088505d21dba00830186a09492c1b39556d5322dac96e9e13d35a146297fcf438094689dfa2c77335722f333eea102ba8a5e13d2ad1ab844a9059cbb000000000000000000000000732e035fbdb9e5ab5ef80c08f6aa081d029984dc0000000000000000000000000000000000000000000000000000000005f5e10046f847f8458207f5a040f1c433c0bcf3aa4e158a7c71ce8709cc8735532b5801ebc229d40969df8294a008a2bfd29cfc3a47cd29a27050e7546f84dc9322917e1a60e65b2d2b49c499db94732e035fbdb9e5ab5ef80c08f6aa081d029984dcf847f8458207f6a0c2b899ff100904808f861e9034556b138388d797ad50f74e5c3fd2a36cd07a56a0100f2387a4a459e417d87611a774223a68763dd67fe4c562e3453709be9ef7e1";

        checkResponse(responseList, assert_return, response);*/
        responseList.add("response : " + response + "\n");

        Log.i(cName, "Leave [testValueTransfer]");

        return responseList;
    }

}
