package com.dcentwallet.dcentsdksample.ui.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.dcentwallet.dcentsdksample.R;
import com.dcentwallet.dcentsdksample.databinding.FragmentDcentSdkTestBinding;
import com.dcentwallet.dcentsdksample.dcent.DcentSdkManager;
import com.dcentwallet.dcentsdksample.dcent.TestKlaytnTransaction;
import com.dcentwallet.dcentsdksample.ui.dialog.KlaytnTestFragment;
import com.dcentwallet.manager.DcentException;
import com.dcentwallet.manager.Util;
import com.dcentwallet.manager.comm.Account;
import com.dcentwallet.manager.comm.BinanceTransaction;
import com.dcentwallet.manager.comm.Bip44KeyPath;
import com.dcentwallet.manager.comm.BitCoinTransaction;
import com.dcentwallet.manager.comm.CoinType;
import com.dcentwallet.manager.comm.CosmosTransaction;
import com.dcentwallet.manager.comm.DeviceInfo;
import com.dcentwallet.manager.comm.EthMesageSignData;
import com.dcentwallet.manager.comm.EthereumTransanction;
import com.dcentwallet.manager.comm.HavahTransaction;
import com.dcentwallet.manager.comm.HederaTransaction;
import com.dcentwallet.manager.comm.NearTransaction;
import com.dcentwallet.manager.comm.PolkadotTransaction;
import com.dcentwallet.manager.comm.XrpTransaction;
import com.dcentwallet.manager.comm.StellarTransaction;
import com.dcentwallet.manager.comm.SyncAccount;
import com.dcentwallet.manager.comm.TezosTransaction;
import com.dcentwallet.manager.comm.TokenTransaction;
import com.dcentwallet.manager.comm.TransactionOutput;
import com.dcentwallet.manager.comm.TronTransaction;
import com.dcentwallet.manager.comm.UnionTransaction;
import com.dcentwallet.manager.comm.UnspentTransactionOutput;
import com.dcentwallet.manager.comm.VechainTransaction;
import com.dcentwallet.manager.comm.AlgorandTransaction;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

public class DcentSdkTestFragment extends Fragment {
    private static final String sLogTag = DcentSdkTestFragment.class.getName();

    private FragmentDcentSdkTestBinding binding;
    private List<Account> mAccountlist = null;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentDcentSdkTestBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        String[] list = new String[]{
                getResources().getString(R.string.list_opt_showConnectingDevice),
                getResources().getString(R.string.list_opt_getDeviceInfo),
                getResources().getString(R.string.list_opt_setDeviceLabel),
                getResources().getString(R.string.list_opt_addAccount_bitcoin),
                getResources().getString(R.string.list_opt_addAccount_ethereum),
                getResources().getString(R.string.list_opt_getAccountInfo),
                getResources().getString(R.string.list_opt_syncAccount),
                getResources().getString(R.string.list_opt_getAddress_bitcoin),
                getResources().getString(R.string.list_opt_getAddress_tezos),
                getResources().getString(R.string.list_opt_getAddress_coreum),
                getResources().getString(R.string.list_opt_bitcoinTransaction),
                getResources().getString(R.string.list_opt_xrpTransaction),
                getResources().getString(R.string.list_opt_ethereumTransaction),
                getResources().getString(R.string.list_opt_erc20Transaction),
                getResources().getString(R.string.list_opt_klaytnTransaction),
                getResources().getString(R.string.list_opt_rskTransaction),
                getResources().getString(R.string.list_opt_rrc20Transaction),
                getResources().getString(R.string.list_opt_monacoinTransaction),
                getResources().getString(R.string.list_opt_binanceTransaction),
                getResources().getString(R.string.list_opt_stellarTransaction),
                getResources().getString(R.string.list_opt_tronTransaction),
                getResources().getString(R.string.list_opt_hederaTransaction),
                getResources().getString(R.string.list_opt_polkadotTransaction),
                getResources().getString(R.string.list_opt_cosmosTransaction),
                getResources().getString(R.string.list_opt_coreumTransaction),
                getResources().getString(R.string.list_opt_tezosTransaction),
                getResources().getString(R.string.list_opt_vechainTransaction),
                getResources().getString(R.string.list_opt_nearTransaction),
                getResources().getString(R.string.list_opt_nearTokenTransaction),
                getResources().getString(R.string.list_opt_havahTransaction),
                getResources().getString(R.string.list_opt_hsp20Transaction),
                getResources().getString(R.string.list_opt_algorandTransaction),
                getResources().getString(R.string.list_opt_algorandAssetTransaction),
                getResources().getString(R.string.list_opt_algorandAppTransaction),
                getResources().getString(R.string.list_opt_ethMsgSign),
                getResources().getString(R.string.list_opt_xrpUnsignedTransaction),
                getResources().getString(R.string.list_opt_getXPUB)};
        final ArrayAdapter<String> adapter = new ArrayAdapter<>(this.getContext(), android.R.layout.simple_list_item_1, list);
        binding.listView.setAdapter(adapter);
        binding.listView.setItemsCanFocus(true);
        binding.listView.setChoiceMode
                (ListView.CHOICE_MODE_SINGLE);
        binding.listView.setOnItemClickListener(OnItemClickListener);
    }

    private final AdapterView.OnItemClickListener OnItemClickListener = (parent, view, position, id) -> {
        String operationItem = (String)parent.getItemAtPosition(position);
        try {
            dcnetSDkthread(operationItem);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    };

    private Thread thread;
    private boolean isProcessing = false;
    /**
     * All method of DcentManager instance should be called from a background thread because it can take a long time!
     */
    @SuppressLint("SetTextI18n")
    private void dcnetSDkthread(String operationItem) throws InterruptedException {
        if(!DcentSdkManager.getInstance().isDongleConnected()){
            //binding.resInfo.setText("D'Cent is not connected\n");
            Toast.makeText(getContext(), "D'Cent is not connected", Toast.LENGTH_LONG).show();
            return;
        }

        getActivity().getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        if( thread != null && thread.isAlive()){
            if(isProcessing){
                Toast.makeText(getContext(), "Test processing...", Toast.LENGTH_LONG).show();
                return;
            }
            thread.interrupt();
        }
        if  (!Objects.equals(operationItem, getResources().getString(R.string.list_opt_klaytnTransaction))) {
            binding.reqInfo.setText(operationItem);
            binding.resInfo.setText("processing...");
        }
        thread = new Thread(() -> {
            String response = null;
            try {
                if (getResources().getString(R.string.list_opt_showConnectingDevice).equals(operationItem)) {
                    Log.d(sLogTag, "list_opt_showConnectingDevice");
                    response = ShowConnectingDevice();
                } else if (Objects.equals(operationItem, getResources().getString(R.string.list_opt_getDeviceInfo)))
                {
                    Log.d(sLogTag, "list_opt_getDeviceInfo");
                    response = GetDeviceInfo();
                } else if (Objects.equals(operationItem, getResources().getString(R.string.list_opt_setDeviceLabel)))
                {
                    Log.d(sLogTag, "list_opt_setDeviceLabel");
                    response = SetDeviceLabel();
                }
                else if (Objects.equals(operationItem, getResources().getString(R.string.list_opt_addAccount_bitcoin)))
                {
                    Log.d(sLogTag, "list_opt_addAccount");
                    response = AddAccount_bitcoin();
                }
                else if (Objects.equals(operationItem, getResources().getString(R.string.list_opt_addAccount_ethereum)))
                {
                    Log.d(sLogTag, "list_opt_addAccount");
                    response = AddAccount_ethereum();
                }
                else if (Objects.equals(operationItem, getResources().getString(R.string.list_opt_getAccountInfo)))
                {
                    Log.d(sLogTag, "list_opt_getAccountInfo");
                    response = GetAccountInfo();
                }
                else if (Objects.equals(operationItem, getResources().getString(R.string.list_opt_syncAccount)))
                {
                    Log.d(sLogTag, "list_opt_syncAccount");
                    response = SyncAccount();
                }
                else if (Objects.equals(operationItem, getResources().getString(R.string.list_opt_getAddress_bitcoin)))
                {
                    Log.d(sLogTag, "list_opt_getAddress_bitcoin");
                    response = GetAddressBitcoin();
                }
                else if (Objects.equals(operationItem, getResources().getString(R.string.list_opt_getAddress_tezos)))
                {
                    Log.d(sLogTag, "list_opt_getAddress_tezos");
                    response = GetAddressTezos();
                }
                else if (Objects.equals(operationItem, getResources().getString(R.string.list_opt_getAddress_coreum)))
                {
                    Log.d(sLogTag, "list_opt_getAddress_coreum");
                    response = GetAddressCoreum();
                }
                else if (Objects.equals(operationItem, getResources().getString(R.string.list_opt_bitcoinTransaction)))
                {
                    Log.d(sLogTag, "list_opt_bitcoinTransaction");
                    response = BitCoinTransaction();
                }
                else if (Objects.equals(operationItem, getResources().getString(R.string.list_opt_xrpTransaction)))
                {
                    Log.d(sLogTag, "list_opt_rippleTransaction");
                    response = XrpTransaction();

                }
                else if (Objects.equals(operationItem, getResources().getString(R.string.list_opt_ethereumTransaction)))
                {
                    Log.d(sLogTag, "list_opt_ethereumTransaction");
                    response = EthereumTransaction();
                }
                else if (Objects.equals(operationItem, getResources().getString(R.string.list_opt_erc20Transaction)))
                {
                    Log.d(sLogTag, "list_opt_erc20Transaction");
                    response = ERC20Transaction();
                }
                else if (Objects.equals(operationItem, getResources().getString(R.string.list_opt_rskTransaction)))
                {
                    Log.d(sLogTag, "list_opt_rskTransaction");
                    response = RSKTransaction();
                }
                else if (Objects.equals(operationItem, getResources().getString(R.string.list_opt_rrc20Transaction)))
                {
                    Log.d(sLogTag, "list_opt_rrc20Transaction");
                    response = RRC20Transaction();
                }
                else if (Objects.equals(operationItem, getResources().getString(R.string.list_opt_monacoinTransaction)))
                {
                    Log.d(sLogTag, "list_opt_monacoinTransaction");
                    response = MonaCoinTransaction();
                }
                else if (Objects.equals(operationItem, getResources().getString(R.string.list_opt_getXPUB)))
                {
                    Log.d(sLogTag, "list_opt_getXPUB");
                    response = GetXPUB();
                }
                else if  (Objects.equals(operationItem, getResources().getString(R.string.list_opt_klaytnTransaction)))
                {
                    Log.d(sLogTag, "list_opt_klaytnTransaction");
                    KlaytnTransaction();
                }
                else if  (Objects.equals(operationItem, getResources().getString(R.string.list_opt_binanceTransaction)))
                {
                    Log.d(sLogTag, "list_opt_binanceTransaction");
                    response = BinanceTransaction();
                }
                else if  (Objects.equals(operationItem, getResources().getString(R.string.list_opt_stellarTransaction)))
                {
                    Log.d(sLogTag, "list_opt_stellarTransaction");
                    response = StellarTransaction();
                }
                else if  (Objects.equals(operationItem, getResources().getString(R.string.list_opt_tronTransaction)))
                {
                    Log.d(sLogTag, "list_opt_tronTransaction");
                    response = TronTransaction();
                }
                else if  (Objects.equals(operationItem, getResources().getString(R.string.list_opt_hederaTransaction)))
                {
                    Log.d(sLogTag, "list_opt_hederaTransaction");
                    response = HederaTransaction();
                }
                else if  (Objects.equals(operationItem, getResources().getString(R.string.list_opt_polkadotTransaction)))
                {
                    Log.d(sLogTag, "list_opt_polkadotTransaction");
                    response = PolkadotTransaction();
                }
                else if  (Objects.equals(operationItem, getResources().getString(R.string.list_opt_cosmosTransaction)))
                {
                    Log.d(sLogTag, "list_opt_cosmosTransaction");
                    response = CosmosTransaction();
                }
                else if  (Objects.equals(operationItem, getResources().getString(R.string.list_opt_coreumTransaction)))
                {
                    Log.d(sLogTag, "list_opt_coreumTransaction");
                    response = CoreumTransaction();
                }
                else if  (Objects.equals(operationItem, getResources().getString(R.string.list_opt_tezosTransaction)))
                {
                    Log.d(sLogTag, "list_opt_tezosTransaction");
                    response = TezosTransaction();
                }
                else if  (Objects.equals(operationItem, getResources().getString(R.string.list_opt_vechainTransaction)))
                {
                    Log.d(sLogTag, "list_opt_vechainTransaction");
                    response = VechainTransaction();
                }
                else if  (Objects.equals(operationItem, getResources().getString(R.string.list_opt_nearTransaction)))
                {
                    Log.d(sLogTag, "list_opt_nearTransaction");
                    response = NearTransaction();
                }
                else if  (Objects.equals(operationItem, getResources().getString(R.string.list_opt_nearTokenTransaction)))
                {
                    Log.d(sLogTag, "list_opt_nearTokenTransaction");
                    response = NearTokenTransaction();
                }
                else if  (Objects.equals(operationItem, getResources().getString(R.string.list_opt_havahTransaction)))
                {
                    Log.d(sLogTag, "list_opt_havahTransaction");
                    response = HavahTransaction();
                }
                else if  (Objects.equals(operationItem, getResources().getString(R.string.list_opt_hsp20Transaction)))
                {
                    Log.d(sLogTag, "list_opt_hsp20Transaction");
                    response = HSP20Transaction();
                }
                else if  (Objects.equals(operationItem, getResources().getString(R.string.list_opt_algorandTransaction)))
                {
                    Log.d(sLogTag, "list_opt_algorandTransaction");
                    response = AlgorandTransaction();
                }
                else if  (Objects.equals(operationItem, getResources().getString(R.string.list_opt_algorandAssetTransaction)))
                {
                    Log.d(sLogTag, "list_opt_algorandAssetTransaction");
                    response = AlgorandAssetTransaction();
                }
                else if  (Objects.equals(operationItem, getResources().getString(R.string.list_opt_algorandAppTransaction)))
                {
                    Log.d(sLogTag, "list_opt_algorandAppTransaction");
                    response = AlgorandAppTransaction();
                }
                else if  (Objects.equals(operationItem, getResources().getString(R.string.list_opt_ethMsgSign)))
                {
                    Log.d(sLogTag, "list_opt_ethMsgSign");
                    response = EthMessageSignTransaction();
                }
                else if  (Objects.equals(operationItem, getResources().getString(R.string.list_opt_xrpUnsignedTransaction)))
                {
                    Log.d(sLogTag, "list_opt_xrpUnsignedTransaction");
                    response = XrpUnSignedTransaction();
                }
                else {
                    binding.resInfo.setText("Not Reached!!");
                }

            } catch (DcentException e) {
                binding.resInfo.post(() -> {
                    binding.resInfo.setText("[" + operationItem + "] Exception msg: " + e.get_err_msg());
                });
                e.printStackTrace();
                isProcessing = false;
                return;
            }
            if  (!Objects.equals(operationItem, getResources().getString(R.string.list_opt_klaytnTransaction))) {
                String finalResponse = response;
                getActivity().runOnUiThread(() -> {
                    binding.resInfo.setText("[" + operationItem + "]  SUCCESS\n");
                    binding.resInfo.append(finalResponse);
                    Log.i(sLogTag, "Leave" + "[" + operationItem + "]");
                    getActivity().getWindow().clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
                });
            }
            isProcessing = false;
        });
        thread.start();
    }

    private String ShowConnectingDevice(){

        Log.i(sLogTag, "Enter [ShowConnectingDevice]");
        //binding.reqInfo.setText("ShowConnectingDevice");

        return DcentSdkManager.getInstance().getConnectedDeviceName() ;
    }

    private String GetDeviceInfo() throws DcentException {

        Log.i(sLogTag, "Enter [GetDeviceInfo]");
        String response;

        isProcessing = true;

        DeviceInfo mDeviceInfo;

        mDeviceInfo = DcentSdkManager.getInstance().getInfo();

        response = "Label : " + mDeviceInfo.getLabel() + "\n";
        response += "Device ID : " + mDeviceInfo.getDeviceId() + "\n";
        response += "FW Version : " + mDeviceInfo.getFwVersion() + "\n";
        response += "KSM Version : " + mDeviceInfo.getKsmVersion() + "\n";
        response += "State : " + mDeviceInfo.getState() + "\n";
        response += "CoinList : " + mDeviceInfo.getCoinList() + "\n";
        return response;
    }

    private int getRandomNum(int n1, int n2) {
        return (int) (Math.random() * (n2 - n1 + 1)) + n1;
    }

    private String SetDeviceLabel() throws DcentException {

        Log.i(sLogTag, "Enter [SetDeviceLabel]");
        isProcessing = true;
        String response;

        List<String> testLabel = new ArrayList<>();
        testLabel.add("test_1");
        testLabel.add("test_2");
        testLabel.add("test_3");
        testLabel.add("test_4");
        testLabel.add("test_5");


        int idx = getRandomNum(1, 5);
        String label = testLabel.get(idx -1);
        Log.i(sLogTag, "[Test Label] : " + label);


        DcentSdkManager.getInstance().getDcentManager().setDeviceLabel(label);

        String getlabel;
        getlabel = DcentSdkManager.getInstance().getDcentManager().getDeviceInfo().getLabel();

        if(!(label.equals(getlabel))){
            response = "[SetDeviceLabel] FAIL : " + "[setLabel] : " + label + "[getLabel]" + getlabel;
            return response;
        }

        Log.i(sLogTag, "Leave [SetDeviceLabel]");

        response = "Device Label : " + getlabel + "\n";
        return response;
    }

    private String AddAccount_bitcoin() throws DcentException {

        Log.i(sLogTag, "Enter [AddAccount]");
        isProcessing = true;
        String keyPath = "m/44'/0'/0'/0/0";

        SyncAccount syncAccount = new SyncAccount(CoinType.BITCOIN.getCoinGroup(), CoinType.BITCOIN.name(), "bitcoin_1", "0 BTC", keyPath);

        DcentSdkManager.getInstance().getDcentManager().syncAccount(syncAccount);

        String response = "[AddAccount] SUCCESS\n";
        Log.i(sLogTag, "Leave [AddAccount]");
        return response;
    }

    private String AddAccount_ethereum() throws DcentException {

        Log.i(sLogTag, "Enter [AddAccount]");
        isProcessing = true;
        String keyPath = "m/44'/60'/0'/0/0";

        SyncAccount syncAccount = new SyncAccount(CoinType.ETHEREUM.getCoinGroup(), CoinType.ETHEREUM.name(), "ethereum_1", "0 ETH", keyPath);

        DcentSdkManager.getInstance().getDcentManager().syncAccount(syncAccount);

        String response = "[AddAccount] SUCCESS\n";
        Log.i(sLogTag, "Leave [AddAccount]");
        return response;
    }

    private String GetAccountInfo() throws DcentException {

        Log.i(sLogTag, "Enter [GetAccountInfo]");
        isProcessing = true;
        mAccountlist = DcentSdkManager.getInstance().getDcentManager().getAccountInfo();
        if(mAccountlist == null){
            return "[GetAccountInfo] FAIL : accountlist is null\n";
        }

       // binding.resInfo.append("[GetAccountInfo] SUCCESS\n");
        StringBuilder response = new StringBuilder();
        for(int idx = 0; idx < mAccountlist.size(); idx++ ){
            response.append(mAccountlist.get(idx).toString()).append("\n");
        }

        Log.i(sLogTag, "Leave [GetAccountInfo]");
        return response.toString();
    }

    private String SyncAccount() throws DcentException {

        Log.i(sLogTag, "Enter [SyncAccount]");
        isProcessing = true;
        if(mAccountlist == null || mAccountlist.size() == 0){
            GetAccountInfo();
        }
        Log.i(sLogTag, "Enter [SyncAccount] mAccountlist: " + mAccountlist);

        String balance = "120";
        SyncAccount syncAccount = new SyncAccount(mAccountlist.get(0).getCoinGroup(), mAccountlist.get(0).getCoinName(), mAccountlist.get(0).getLabel(), balance, mAccountlist.get(0).getReceivingAddressPath());
        DcentSdkManager.getInstance().getDcentManager().syncAccount(syncAccount);

        Log.i(sLogTag, "Leave [SyncAccount]");
        return "";
    }

    private String GetAddressBitcoin() throws DcentException {

        Log.i(sLogTag, "Enter [GetAddressBitcoin]");
        isProcessing = true;
        String keyPath = "m/44'/0'/0'/0/0";
        String address;
        HashMap<String, String> response;
        response = DcentSdkManager.getInstance().getDcentManager().getAddress(CoinType.BITCOIN, Bip44KeyPath.valueOf(keyPath));
        address = response.get("address");
        Log.i(sLogTag, "Leave [GetAddressBitcoin]");
        return "Address : " + address + "\n";
    }

    private String GetAddressTezos() throws DcentException {

        Log.i(sLogTag, "Enter [GetAddressTezos]");
        isProcessing = true;
        String keyPath = "m/44'/1729'/0'/0'";
        HashMap<String, String> response;
        response = DcentSdkManager.getInstance().getDcentManager().getAddress(CoinType.TEZOS, Bip44KeyPath.valueOf(keyPath));
        String address = response.get("address");
        String pubkey = response.get("pubkey");
        Log.i(sLogTag, "Leave [GetAddressTezos]");
        return "Address : " + address + "\n" + "pubkey : " + pubkey + "\n";
    }

    private String GetAddressCoreum() throws DcentException {

        Log.i(sLogTag, "Enter [GetAddressCoreum]");
        isProcessing = true;
        String keyPath = "m/44'/990'/0'/0/0";
        HashMap<String, String> response;

        response = DcentSdkManager.getInstance().getDcentManager().getAddress(CoinType.COREUM, Bip44KeyPath.valueOf(keyPath));
        String address = response.get("address");
        Log.i(sLogTag, "Leave [GetAddressCoreum]");
        return "Address : " + address + "\n";
    }

    private String BitCoinTransaction() throws DcentException {

        Log.i(sLogTag, "Enter [BitcoinTransaction]");
        isProcessing = true;
        String response;

        // UnspentTransactionOutput utxo = new UnspentTransactionOutput("01000000013163cadbb482e229c652a09d14a5eba146581b6779181b639c6ae74c684eb4a7010000006b48304502210094b94c7694b5325d62da5d225b0be813aa0fa461658a94d25b4e8db2e90af858022031dff44a0ac5e9315ead8bd9dae125e37d369eb3af92f3104536039ea2bbebdd012103e118eaec58bc80840c234745fd27c1c37a64a035e099e1481118920575844ba5ffffffff0220a10700000000001976a914e17b0853135b205d784ad0d83a63d5b29e2b0ade88acdb230400000000001976a9148ebb5bde87ed7444b8d3e1929d12ebfbb4ba672c88ac00000000", 1, BitCoinTransaction.TxType.p2pkh, Bip44KeyPath.valueOf("m/44'/0'/0'/1/3")) ;
        UnspentTransactionOutput utxo = new UnspentTransactionOutput("01000000012b09bd990adc6792588225486c336fb2090890341fcbc6ae92c440c3bd266b98010000006a47304402201df6bd2294f9d51496c1be7ea09431fcfee4b0ca9359712c2c381aff9b2d6f070220595a5bb4e9f0f0d1f5fb9a800224c01ac99058d9b491cd7e6a60145bbd26ddca0121028cbb73e589f81937784eaf728cd14ad27984e5415766c04408211af8d9e30ee7ffffffff0127810000000000001976a9141c7254fac600ef7371664a613f0323c6c641cbd288ac00000000", 0, BitCoinTransaction.TxType.p2pkh, Bip44KeyPath.valueOf("m/44'/0'/0'/0/0")) ;

        List<UnspentTransactionOutput> input = new ArrayList<>();
        input.add(utxo) ;

        TransactionOutput txo = new TransactionOutput(10000, Arrays.asList(new String[]{"1Ckii7MpiquSxcmo2ch1UTfQMConz31rpB"}), BitCoinTransaction.TxType.p2pkh  ) ;
        List<TransactionOutput> output = new ArrayList<>();
        output.add(txo) ;

        //BitCoinTransaction bitCoinTransaction = new BitCoinTransaction(1, input, output, 0) ;
        BitCoinTransaction bitCoinTransaction = new BitCoinTransaction.Builder()
                .version(1)
                .input(input)
                .output(output)
                .locktime(0)
                .build();

        response = DcentSdkManager.getInstance().getDcentManager().getBitcoinSignedTransaction(CoinType.BITCOIN, bitCoinTransaction);

        return "response : " + response + "\n";
    }

    // ToDo : implement
    private String XrpTransaction() throws DcentException {

        Log.i(sLogTag, "Enter [XrpTransaction]");
        isProcessing = true;
        XrpTransaction xrpTransaction;
        String response;

        String address = DcentSdkManager.getInstance().getDcentManager().getAddress(CoinType.XRP, Bip44KeyPath.valueOf("m/44'/144'/0'/0/0")).get("address");

        xrpTransaction = new XrpTransaction.Builder()
                .keyPath(Bip44KeyPath.valueOf("m/44'/144'/0'/0/0"))
                .sourceAddress(address)
                .destinationAddress("rsHXBk5vnswg5SZxUQCEPYVnmrd4PaZ7Ah")
                .amountDrops(2)
                .feeDrops(10)
                .sequence(11)
                .destinationTag(-1)
                .build();

        response = DcentSdkManager.getInstance().getDcentManager().getXrpSignedTransaction(CoinType.XRP, xrpTransaction);

        Log.i(sLogTag, "Leave [XrpTransaction]");
        return "response : " + response + "\n";
    }


    private String EthereumTransaction() throws DcentException {

        Log.i(sLogTag, "Enter [EthereumTransaction]");
        isProcessing = true;
        EthereumTransanction ethereumTransanction;

        /*ethereumTransanction = new EthereumTransanction(Bip44KeyPath.valueOf("m/44'/60'/0'/0/0"), "14",  "0xe5c23dAa6480e45141647E5AeB321832150a28D4",
                "500000000000000", "21000", "6000000000", "0x", "1", "ETH");*/

        ethereumTransanction = new EthereumTransanction.Builder()
                .keyPath(Bip44KeyPath.valueOf("m/44'/60'/0'/0/0"))
                .nonce("14")
                .toAddr("0xe5c23dAa6480e45141647E5AeB321832150a28D4")
                .amount("500000000000000")
                .gasLimit("21000")
                .gasPrice("6000000000")
                .data("0x")
                .chainId("1")
                .symbol("ETH")
                .build();

        String response;
        response = DcentSdkManager.getInstance().getDcentManager().getEthereumSignedTransaction(CoinType.ETHEREUM, ethereumTransanction);

        Log.i(sLogTag, "Leave [EthereumTransaction]");
        return "response : " + response + "\n";

    }

    private String EthMessageSignTransaction() throws DcentException {

        Log.i(sLogTag, "Enter [EthMessageSignTransaction]");
        isProcessing = true;
        EthMesageSignData ethMesageSignData;
        HashMap<String, String> response;

        //ethMesageSignData = new EthMesageSignData(Bip44KeyPath.valueOf("m/44'/60'/0'/0/0"),"Message Sign TEST");

        ethMesageSignData = new EthMesageSignData.Builder()
                .keyPath(Bip44KeyPath.valueOf("m/44'/60'/0'/0/0"))
                .data("Message Sign TEST")
                .build();

        response = DcentSdkManager.getInstance().getDcentManager().getEthereumMessageSigned(CoinType.ETHEREUM, ethMesageSignData, "msg_sign");

        Log.i(sLogTag, "Leave [EthMessageSignTransaction]");
        return "address : " + response.get("address") + "\n" + "sign    : " + response.get("sign") + "\n";
    }

    private String XrpUnSignedTransaction() throws DcentException {
        Log.i(sLogTag, "Enter [XrpUnSignedTransaction]");
        isProcessing = true;
        XrpTransaction xrpTransaction;
        HashMap<String, String> response;

        //Transaction Type: Payment
        // Account: rQficL...tKjo4p
        // Destination Tag: 0
        // Amount(XRP): 10
        // Fee(XRP) : 0.00001
        // To: rxjE19...vqYRas
        xrpTransaction = new XrpTransaction.Builder()
                .keyPath(Bip44KeyPath.valueOf("m/44'/144'/0'/0/0"))
                .unsignedTx("1200002280000000240238634E2E00000000201B023863E161400000000098968068400000000000000A8114FD970F4612987680F4008BA53ED6FD87BE0DAAF983141DEE2154B117FB47FCF4F19CD983D9FCBB894FF7")
                .build();

        response = DcentSdkManager.getInstance().getDcentManager().getXRPSignedTransactionWithUnsignedTx(CoinType.XRP, xrpTransaction);

        Log.i(sLogTag, "Leave [XrpUnSignedTransaction]");
        return "sign : " + response.get("sign") + "\n" + "pubkey    : " + response.get("pubkey") + "\n" + "accountId    : " + response.get("accountId") + "\n";
    }

    private String ERC20Transaction() throws DcentException {

        Log.i(sLogTag, "Enter [ERC20Transaction]");
        isProcessing = true;
        TokenTransaction erc20transaction;
        String response;

        erc20transaction = new TokenTransaction.Builder()
                .keyPath(Bip44KeyPath.valueOf("m/44'/60'/0'/0/0"))
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

        response = DcentSdkManager.getInstance().getDcentManager().getTokenSignedTransaction(CoinType.ERC20, erc20transaction);

        Log.i(sLogTag, "Leave [ERC20Transaction]");
        return "response : " + response + "\n";
    }

    private String RSKTransaction() throws DcentException {

        Log.i(sLogTag, "Enter [RSKTransaction]");
        isProcessing = true;
        EthereumTransanction rskTransanction;
        String response;

        // rskTransanction = new EthereumTransanction(Bip44KeyPath.valueOf("m/44'/137'/0'/0/0"), "4",  "0x31f9eac21857d55efCebaE8234BE08B4940D3954", "49000000000", "21000", "65164000", null, "30", "RBTC");

        rskTransanction = new EthereumTransanction.Builder()
                .keyPath(Bip44KeyPath.valueOf("m/44'/137'/0'/0/0"))
                .nonce("4")
                .toAddr("0x31f9eac21857d55efCebaE8234BE08B4940D3954")
                .amount("49000000000")
                .gasLimit("21000")
                .gasPrice("65164000")
                .data(null)
                .chainId("30")
                .symbol("RBTC")
                .build();

        response = DcentSdkManager.getInstance().getDcentManager().getEthereumSignedTransaction(CoinType.RSK, rskTransanction);

        Log.i(sLogTag, "Leave [RSKTransaction]");
        return "response : " + response + "\n";
    }

    private String BinanceTransaction() throws DcentException {
        Log.i(sLogTag, "Enter [BinanceTransaction]");
        isProcessing = true;
        BinanceTransaction binanceTransaction;
        String response;

        binanceTransaction = new BinanceTransaction.Builder()
                .keyPath(Bip44KeyPath.valueOf("m/44'/714'/0'/0/0"))
                .signBytes("7b226163636f756e745f6e756d626572223a22343834303237222c22636861696e5f6964223a2242696e616e63652d436861696e2d546967726973222c2264617461223a6e756c6c2c226d656d6f223a22222c226d736773223a5b7b22696e70757473223a5b7b2261646472657373223a22626e62317a7a34647634767235687a3076647161686334707371797a39676a303974703775646d673273222c22636f696e73223a5b7b22616d6f756e74223a313030303030302c2264656e6f6d223a22424e42227d5d7d5d2c226f757470757473223a5b7b2261646472657373223a22626e6231796d33377164616e76716b72686139636b70686a6b3067336d6630367068656d33787a393437222c22636f696e73223a5b7b22616d6f756e74223a31303030303030303030302c2264656e6f6d223a22424e42227d5d7d5d7d5d2c2273657175656e6365223a2230222c22736f75726365223a2230227d")
                .feeValue("375000")
                .build();

        response = DcentSdkManager.getInstance().getDcentManager().getBinanceSignedTransaction(CoinType.BINANCE, binanceTransaction);

        Log.i(sLogTag, "Leave [BinanceTransaction]");
        return "response : " + response + "\n";
    }

    private String StellarTransaction() throws DcentException {
        Log.i(sLogTag, "Enter [StellarTransaction]");
        isProcessing = true;
        StellarTransaction stellarTransaction;
        String response;

        stellarTransaction = new StellarTransaction.Builder()
                .keyPath(Bip44KeyPath.valueOf("m/44'/148'/0'"))
                .signBytes("7ac33997544e3175d266bd022439b22cdb16508c01163f26e5cb2a3e1045a9790000000200000000d147b20efbeb51a8f8eea50f4ce1ad549796a509236c3cad056e22cf3e3e6f0b000000640005821d00000004000000010000000000000000000000005ec76b4a00000000000000010000000000000000000000005e3deafcf4bee3bf40a85e4f93bdf7d94e62a05e811ed787f9d04bc983ec207b0000000049504f8000000000")
                .build();

        response = DcentSdkManager.getInstance().getDcentManager().getStellarSignedTransaction(CoinType.STELLAR, stellarTransaction);

        Log.i(sLogTag, "Leave [StellarTransaction]");
        return "response : " + response + "\n";
    }

    private String TronTransaction() throws DcentException {
        Log.i(sLogTag, "Enter [TronTransaction]");
        isProcessing = true;
        TronTransaction tronTransaction;
        String response;

        tronTransaction = new TronTransaction.Builder()
                .keyPath(Bip44KeyPath.valueOf("m/44'/195'/0'/0/0"))
                .signBytes("0a02610a220838507457b79561a740e8dd8fefaf2e5a65080112610a2d747970652e676f6f676c65617069732e636f6d2f70726f746f636f6c2e5472616e73666572436f6e747261637412300a15418f2d2dfaa81af60f5a3ac4ca5597e795aff7abae121541c27dcd7d914fd6aa8fec3c8a41cb2e90883bc6f0187f70dd978cefaf2e")
                .feeValue("0.001")
                .build();

        response = DcentSdkManager.getInstance().getDcentManager().getTronSignedTransaction(CoinType.TRON, tronTransaction);

        Log.i(sLogTag, "Leave [TronTransaction]");
        return "response : " + response + "\n";
    }

    private String HederaTransaction() throws DcentException {
        Log.i(sLogTag, "Enter [HederaTransaction]");
        isProcessing = true;
        HederaTransaction hederaTransaction;
        String response;

        hederaTransaction = new HederaTransaction.Builder()
                .keyPath(Bip44KeyPath.valueOf("m/44'/3030'/0'"))
                .unsigned("0a1a0a0b0893b888880610cdfab9741209080010001895cb84011800120608001000180318c0843d22020878320072280a260a110a09080010001895cb840110ff87debe010a110a090800100018c1cb8401108088debe01")
                .decimals(8)
                .symbol("HBAR")
                .build();

        response = DcentSdkManager.getInstance().getDcentManager().getHederaSignedTransaction(CoinType.HEDERA, hederaTransaction);

        Log.i(sLogTag, "Leave [HederaTransaction]");
        return "response : " + response + "\n";
    }

    private String PolkadotTransaction() throws DcentException {
        Log.i(sLogTag, "Enter [PolkadotTransaction]");
        isProcessing = true;
        PolkadotTransaction polkadotTransaction;
        String response;

        polkadotTransaction = new PolkadotTransaction.Builder()
                .keyPath(Bip44KeyPath.valueOf("m/44'/354'/0'/0/0"))
                .sigHash("040000163a5ee36b1243ce5241c0a45010dd1717869e9918c040bf5d305be4a5af9e7a0b00407a10f35a003400a223000007000000e143f23803ac50e8f6f8e62695d1ce9e4e1d68aa36c1cd2cfd15340213f3423ee143f23803ac50e8f6f8e62695d1ce9e4e1d68aa36c1cd2cfd15340213f3423e")
                .fee("0.0000000005")
                .decimals(12)
                .symbol("DOT")
                .build();

        response = DcentSdkManager.getInstance().getDcentManager().getPolkadotSignedTransaction(CoinType.POLKADOT, polkadotTransaction);

        Log.i(sLogTag, "Leave [PolkadotTransaction]");
        return "response : " + response + "\n";
    }

    private String CosmosTransaction() throws DcentException {
        Log.i(sLogTag, "Enter [CosmosTransaction]");
        isProcessing = true;
        CosmosTransaction cosmosTransaction;
        String response;

        cosmosTransaction = new CosmosTransaction.Builder()
                .keyPath(Bip44KeyPath.valueOf("m/44'/118'/0'/0/0"))
                .sigHash("0a94010a8f010a1c2f636f736d6f732e62616e6b2e763162657461312e4d736753656e64126f0a2d636f736d6f73317235763573726461377866746833686e327332367478767263726e746c646a756d74386d686c122d636f736d6f733138766864637a6a7574343467707379383034637266686e64356e713030336e7a306e663230761a0f0a057561746f6d1206313030303030120012670a500a460a1f2f636f736d6f732e63727970746f2e736563703235366b312e5075624b657912230a21ffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffff12040a020801180a12130a0d0a057561746f6d12043530303010c09a0c1a0b636f736d6f736875622d34208f3a")
                .fee("0.00025")
                .decimals(6)
                .symbol("ATOM")
                .build();

        response = DcentSdkManager.getInstance().getDcentManager().getCosmosSignedTransaction(CoinType.COSMOS, cosmosTransaction);

        Log.i(sLogTag, "Leave [CosmosTransaction]");
        return "response : " + response + "\n sign: " + response.substring(0, 128) + "\n pubkey: " + response.substring(128) + "\n" ;
    }

    private String CoreumTransaction() throws DcentException {
        Log.i(sLogTag, "Enter [CoreumTransaction]");
        isProcessing = true;
        CosmosTransaction coreumTransaction;
        String response;

        coreumTransaction = new CosmosTransaction.Builder()
                .keyPath(Bip44KeyPath.valueOf("m/44'/990'/0'/0/0"))
                .sigHash("0a90010a8b010a1c2f636f736d6f732e62616e6b2e763162657461312e4d736753656e64126b0a2b636f7265317432656d347a6b77346161716d7139373939656e756b66366538343577656671776e63667a78122b636f726531666c343876736e6d73647a637638357135643271347a35616a646861387975337834333537761a0f0a057561746f6d1206313030303030120012670a500a460a1f2f636f736d6f732e63727970746f2e736563703235366b312e5075624b657912230a21ffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffff12040a020801180012130a0d0a057561746f6d12043530303010c09a0c1a0b636f736d6f736875622d342000")
                .fee("0.000251")
                .decimals(6)
                .symbol("CORE")
                .build();

        response = DcentSdkManager.getInstance().getDcentManager().getCosmosSignedTransaction(CoinType.COREUM, coreumTransaction);

        Log.i(sLogTag, "Leave [CosmosTransaction]");
        return "response : " + response + "\n sign: " + response.substring(0, 128) + "\n pubkey: " + response.substring(128) + "\n" ;
    }

    private String TezosTransaction() throws DcentException {
        Log.i(sLogTag, "Enter [TezosTransaction]");
        isProcessing = true;
        TezosTransaction tezosTransaction;
        String response;

        tezosTransaction = new TezosTransaction.Builder()
                .keyPath(Bip44KeyPath.valueOf("m/44'/1729'/0'/0/0"))
                .sigHash("032923211dc76b05a644c88df7507c6f2fd5100cb6ed11c236a270d97dbd53937c6c0021298384724bff62370492fbb56f408bf6f77bcfb905b8d6f804f51219a0e7010000678a5cb8807767a9d900311890526ad77bffbb3900")
                .fee("0.000697")
                .decimals(6)
                .symbol("XTZ")
                .build();

        response = DcentSdkManager.getInstance().getDcentManager().getTezosSignedTransaction(CoinType.TEZOS, tezosTransaction);

        Log.i(sLogTag, "Leave [TezosTransaction]");
        return "response : " + response + "\n";
    }

    private String NearTransaction() throws DcentException {
        Log.i(sLogTag, "Enter [NearTransaction]");
        isProcessing = true;
        NearTransaction nearTransaction;
        String response;

        nearTransaction = new NearTransaction.Builder()
                .keyPath(Bip44KeyPath.valueOf("m/44'/397'/0'"))
                .sigHash("4000000033666164666339326631633631643261303138626166333738383566376633363331313439616331356163303438613263303137316566316661356139633366003fadfc92f1c61d2a018baf37885f7f3631149ac15ac048a2c0171ef1fa5a9c3f41b15753844300004000000033666164666339326631633631643261303138626166333738383566376633363331313439616331356163303438613263303137316566316661356139633366d5e91d9515257370e4763c0da089ca544c1292bd188ad3fee466e17024e941f40100000003000000a1edccce1bc2d3000000000000")
                .fee("0.000860039223625")
                .decimals(24)
                .symbol("NEAR")
                .build();


        response = DcentSdkManager.getInstance().getDcentManager().getNearSignedTransaction(CoinType.NEAR, nearTransaction);

        Log.i(sLogTag, "Leave [NearTransaction]");
        return "response : " + response + "\n";
    }

    private String NearTokenTransaction() throws DcentException {
        Log.i(sLogTag, "Enter [NearTokenTransaction]");
        isProcessing = true;
        NearTransaction nearTokenTransaction;
        String response;

        nearTokenTransaction = new NearTransaction.Builder()
                .keyPath(Bip44KeyPath.valueOf("m/44'/397'/0'"))
                .sigHash("400000006364663837313939386530343164333037383432383063636530313362666435633764653339643264333236373262623239666163636530363333373066333200cdf871998e041d30784280cce013bfd5c7de39d2d32672bb29facce063370f32c25215a29572000014000000757364632e7370696e2d66692e746573746e6574a37a00484ecbde6e6c436dece0ce845369657857deff857a2acb645e0a20799e02000000020f00000073746f726167655f6465706f7369746a0000007b226163636f756e745f6964223a2230626238343733363130336633336265623330356432316262333235396464653666646437313764616432656562336366356137666232323730666362353866222c22726567697374726174696f6e5f6f6e6c79223a747275657d00e057eb481b00000000485637193cc34300000000000000020b00000066745f7472616e73666572610000007b2272656365697665725f6964223a2230626238343733363130336633336265623330356432316262333235396464653666646437313764616432656562336366356137666232323730666362353866222c22616d6f756e74223a22313530227d00f0ab75a40d000001000000000000000000000000000000")
                .fee("0.123")
                .decimals(2)
                .symbol("USDC")
                .optionParam("02") // function call
                .build();

        response = DcentSdkManager.getInstance().getDcentManager().getNearSignedTransaction(CoinType.NEAR_TOKEN, nearTokenTransaction);

        Log.i(sLogTag, "Leave [NearTokenTransaction]");
        return "response : " + response + "\n";
    }

    private String VechainTransaction() throws DcentException {
        Log.i(sLogTag, "Enter [VechainTransaction]");
        isProcessing = true;
        VechainTransaction vechainTransaction;
        String response;

        vechainTransaction = new VechainTransaction.Builder()
                .keyPath(Bip44KeyPath.valueOf("m/44'/818'/0'/0/0"))
                .sigHash("f83b2787c6143a04c08fe18202d0e1e094a57105e43efa47e787d84bb6dfedb19bdcaa8a908908e3f50b173c100001808082520880860152671166bdc0")
                .fee("0.21")
                .decimals(18)
                .symbol("VET")
                .build();

        response = DcentSdkManager.getInstance().getDcentManager().getVechainSignedTransaction(CoinType.VECHAIN, vechainTransaction);

        Log.i(sLogTag, "Leave [PolkadotTransaction]");
        return "response : " + response + "\n";
    }

    private String HavahTransaction() throws DcentException {
        Log.i(sLogTag, "Enter [HavahTransaction]");
        isProcessing = true;
        HavahTransaction havahTransaction;
        String response;

        havahTransaction = new HavahTransaction.Builder()
                .keyPath(Bip44KeyPath.valueOf("m/44'/858'/0'/0/0"))
                .sigHash("6963785f73656e645472616e73616374696f6e2e66726f6d2e6878316531333433353935303532383335613064396137643064396533353839633433323831623262642e6e69642e30783130302e6e6f6e63652e3078312e737465704c696d69742e307831616462302e74696d657374616d702e3078356661316631343633666161302e746f2e6878353833323164313731633833393465613434303638376562623462353832623037353739356663352e76616c75652e307833636235396163376237353734652e76657273696f6e2e307833")
                .fee("0.001375")
                .decimals(18)
                .symbol("HVH")
                .build();

        response = DcentSdkManager.getInstance().getDcentManager().getHavahSignedTransaction(CoinType.HAVAH, havahTransaction);

        Log.i(sLogTag, "Leave [HavahTransaction]");
        return "response : " + response + "\n";
    }

    private String HSP20Transaction() throws DcentException {
        Log.i(sLogTag, "Enter [HSP20Transaction]");
        isProcessing = true;
        HavahTransaction hsp20Transaction;
        String response;

        hsp20Transaction = new HavahTransaction.Builder()
                .keyPath(Bip44KeyPath.valueOf("m/44'/858'/0'/0/0"))
                .sigHash("6963785f73656e645472616e73616374696f6e2e646174612e7b6d6574686f642e7472616e736665722e706172616d732e7b5f746f2e6878656337623030666563623033393132333037306334633330383130636438636439666465623662392e5f76616c75652e30786465306236623361373634303030307d7d2e64617461547970652e63616c6c2e66726f6d2e6878653165396634626466623461316562363332323266366535653338396237386461663533323639612e6e69642e30783130312e6e6f6e63652e3078312e737465704c696d69742e30786534653163302e74696d657374616d702e3078356638663564303562623763382e746f2e6378333235313963366331316234373166663632396138656661333665633764303439393630616639382e76657273696f6e2e307833")
                .fee("0.24")
                .decimals(18)
                .symbol("HVH")
                .optionParam("01")
                .build();

        response = DcentSdkManager.getInstance().getDcentManager().getHavahSignedTransaction(CoinType.HAVAH_HSP20, hsp20Transaction);

        Log.i(sLogTag, "Leave [HSP20Transaction]");
        return "response : " + response + "\n";
    }

    private String AlgorandTransaction() throws DcentException {
        Log.i(sLogTag, "Enter [AlgorandTransaction]");
        isProcessing = true;
        AlgorandTransaction algorandTransaction;
        String response;

        algorandTransaction = new AlgorandTransaction.Builder()
                .keyPath(Bip44KeyPath.valueOf("m/44'/283'/0'/0/0"))
                .sigHash("54588aa3616d74cf000000174876e800a3666565cd03e8a26676ce01f60f1ca367656eac746573746e65742d76312e30a26768c4204863b518a4b3c84ec810f22d4f1081cb0f71f059a7ac20dec62f7f70e5093a22a26c76ce01f61304a46e6f7465c4084669727374205478a3726376c420568d5f7efc21a0928e50234dfa58764a84128d1c127971f6a26f350500d0ce24a3736e64c420302be92b2e5fb14e540554f3b652c0350fcc77ea53488fed81c97555179040c8a474797065a3706179")
                .fee("0.001")
                .decimals(6)
                .symbol("ALGO")
                .optionParam("00")
                .build();

        response = DcentSdkManager.getInstance().getDcentManager().getAlgorandSignedTransaction(CoinType.ALGORAND, algorandTransaction);

        Log.i(sLogTag, "Leave [AlgorandTransaction]");
        return "response : " + response + "\n";
    }

    private String AlgorandAssetTransaction() throws DcentException {
        Log.i(sLogTag, "Enter [AlgorandAssetTransaction]");
        isProcessing = true;
        AlgorandTransaction algorandAssetTransaction;
        String response;

        algorandAssetTransaction = new AlgorandTransaction.Builder()
                .keyPath(Bip44KeyPath.valueOf("m/44'/283'/0'/0/0"))
                .sigHash("54588aa461616d7464a461726376c420568d5f7efc21a0928e50234dfa58764a84128d1c127971f6a26f350500d0ce24a3666565cd03e8a26676ce01f618f6a367656eac746573746e65742d76312e30a26768c4204863b518a4b3c84ec810f22d4f1081cb0f71f059a7ac20dec62f7f70e5093a22a26c76ce01f61cdea3736e64c420302be92b2e5fb14e540554f3b652c0350fcc77ea53488fed81c97555179040c8a474797065a56178666572a478616964ce11fb87c5")
                .fee("0.001")
                .decimals(2)
                .symbol("DTN")
                .optionParam("01")
                .build();

        response = DcentSdkManager.getInstance().getDcentManager().getAlgorandSignedTransaction(CoinType.ALGORAND_ASSET, algorandAssetTransaction);

        Log.i(sLogTag, "Leave [AlgorandAssetTransaction]");
        return "response : " + response + "\n";
    }

    private String AlgorandAppTransaction() throws DcentException {
        Log.i(sLogTag, "Enter [AlgorandAppTransaction]");
        isProcessing = true;
        AlgorandTransaction algorandAppTransaction;
        String response;

        algorandAppTransaction = new AlgorandTransaction.Builder()
                .keyPath(Bip44KeyPath.valueOf("m/44'/283'/0'/0/0"))
                .sigHash("545889a46170616191c43a5475652053657020313220323032332031353a34303a343220474d542b303930302028eb8c80ed959cebafbceab5ad20ed919ceca480ec8b9c29a461706964ce068fee9aa3666565cd03e8a26676ce01f6204fa367656eac746573746e65742d76312e30a26768c4204863b518a4b3c84ec810f22d4f1081cb0f71f059a7ac20dec62f7f70e5093a22a26c76ce01f62437a3736e64c420568d5f7efc21a0928e50234dfa58764a84128d1c127971f6a26f350500d0ce24a474797065a46170706c")
                .fee("0.001")
                .decimals(2)
                .symbol("DTN")
                .optionParam("03")
                .build();

        response = DcentSdkManager.getInstance().getDcentManager().getAlgorandSignedTransaction(CoinType.ALGORAND_APP, algorandAppTransaction);

        Log.i(sLogTag, "Leave [AlgorandAppTransaction]");
        return "response : " + response + "\n";
    }

    private String RRC20Transaction() throws DcentException {

        Log.i(sLogTag, "Enter [RRC20Transaction]");
        isProcessing = true;
        TokenTransaction rrc20transaction;
        String response;

       /* rrc20transaction = new TokenTransaction(Bip44KeyPath.valueOf("m/44'/137'/0'/0/0"), "4", "0x31f9eac21857d55efCebaE8234BE08B4940D3954", "49000000000", "70000", "65164000",
                "RIF", "0x2acc95758f8b5f583470ba265eb685a8f45fc9d5", "18", "RIF", "30", "RBTC");
*/
        rrc20transaction = new TokenTransaction.Builder()
                .keyPath(Bip44KeyPath.valueOf("m/44'/137'/0'/0/0"))
                .nonce("4")
                .toAddr("0x31f9eac21857d55efCebaE8234BE08B4940D3954")
                .amount("49000000000")
                .gasLimit("70000")
                .gasPrice("65164000")
                .tokenName("RIF")
                .contractAddress("0x2acc95758f8b5f583470ba265eb685a8f45fc9d5")
                .decimals("18")
                .symbol("RIF")
                .chainId("30")
                .feeSymbol("RBTC")
                .build();

        response = DcentSdkManager.getInstance().getDcentManager().getTokenSignedTransaction(CoinType.RRC20, rrc20transaction);

        Log.i(sLogTag, "Leave [RRC20Transaction]");
        return "response : " + response + "\n";
    }

    private String MonaCoinTransaction() throws DcentException {

        Log.i(sLogTag, "Enter [MonaCoinTransaction]");
        isProcessing = true;
        String response;

        UnspentTransactionOutput utxo = new UnspentTransactionOutput("0100000001ac9aa66bb2a2298c0551d0abce64c87011478e314a3cd02c14ec3d10d9a7fdef000000006a473044022002c0774d92a510c03894d3b719830c2c5152bcdb0aed44ae1ade8601c2dddf0802201d4e9a3c7f20be0950c6824516b0bb585553dcdca9b0d4505c392c475f8d09f60121032323ea47022a2f079e1515c05bca65c01e2f319eff88ba6de157d0471360e206ffffffff02c09ee605000000001976a914adf0f8b303265bce9cf06e0dd6865de2e6fc7c0a88ac68cb4a36000000001976a91457765ae98c002d6ae2e27e275f0353c2af0602cc88ac00000000", 1, BitCoinTransaction.TxType.p2pkh, Bip44KeyPath.valueOf("m/44'/22'/0'/1/2")) ;
        List<UnspentTransactionOutput> input = new ArrayList<>();
        input.add(utxo) ;

        TransactionOutput txo = new TransactionOutput(500000000, Arrays.asList(new String[]{"MFjJjkwpsXdtYwpuwtqhuvEAYkSpGJcJsN"}), BitCoinTransaction.TxType.p2pkh) ;
        List<TransactionOutput> output = new ArrayList<>();
        output.add(txo) ;
        txo = new TransactionOutput(410836251, Arrays.asList(new String[]{"m/44'/22'/0'/1/1"}), BitCoinTransaction.TxType.change  ) ;
        output.add(txo) ;

        //BitCoinTransaction monaCoinTransaction = new BitCoinTransaction(1, input, output, 0) ;
        BitCoinTransaction monaCoinTransaction = new BitCoinTransaction.Builder()
                .version(1)
                .input(input)
                .output(output)
                .locktime(0)
                .build();

        response = DcentSdkManager.getInstance().getDcentManager().getBitcoinSignedTransaction(CoinType.MONACOIN, monaCoinTransaction);

        Log.i(sLogTag, "Leave [MonaCoinTransaction]");
        return "response : " + response + "\n";
    }

    private String GetXPUB() throws DcentException {
        Log.i(sLogTag, "Enter [GetXPUB]");
        isProcessing = true;

        String keyPath = "m/44'/0'/0'";
        String xpub;

        xpub = DcentSdkManager.getInstance().getDcentManager().getXPUB(keyPath, null);

        Log.i(sLogTag, "Leave [GetXPUB]");
        return "XPUB : " + xpub + "\n";
    }

    KlaytnTestFragment klaytnTestFragment;
    private void KlaytnTransaction() {
        Log.i(sLogTag, "Enter [Klaytn]");
        klaytnTestFragment = KlaytnTestFragment.newInstance();
        klaytnTestFragment.setFragmentInterfacer(operationItem -> {

            binding.reqInfo.setText(getResources().getString(R.string.list_opt_klaytnTransaction) + " - " + operationItem);
            binding.resInfo.setText("processing...");
            thread = new Thread(() -> {
                List<String> klaytnResponse;
                Log.d(sLogTag, operationItem);
                try {
                    isProcessing = true;
                    if (Objects.equals(operationItem, getResources().getString(R.string.list_opt_Klaytn_LegacyTransaction))) {
                        klaytnResponse = TestKlaytnTransaction.testLegacyTransaction(DcentSdkManager.getInstance().getDcentManager());
                    } else if (Objects.equals(operationItem, getResources().getString(R.string.list_opt_Klaytn_ValueTransaction))) {
                        klaytnResponse = TestKlaytnTransaction.testValueTransfer(DcentSdkManager.getInstance().getDcentManager());
                    } else if (Objects.equals(operationItem, getResources().getString(R.string.list_opt_Klaytn_ValueTransaction_D))) {
                        klaytnResponse = TestKlaytnTransaction.testDelegatedValueTransfer(DcentSdkManager.getInstance().getDcentManager());
                    } else if (Objects.equals(operationItem, getResources().getString(R.string.list_opt_Klaytn_ValueTransaction_PD))) {
                        klaytnResponse = TestKlaytnTransaction.testPFDelegatedValueTransfer(DcentSdkManager.getInstance().getDcentManager());
                    } else if (Objects.equals(operationItem, getResources().getString(R.string.list_opt_Klaytn_ValueMemoTx))) {
                        klaytnResponse = TestKlaytnTransaction.testValueMemoTx(DcentSdkManager.getInstance().getDcentManager());
                    } else if (Objects.equals(operationItem, getResources().getString(R.string.list_opt_Klaytn_ValueMemoTx_D))) {
                        klaytnResponse = TestKlaytnTransaction.testDelegatedValueMemoTx(DcentSdkManager.getInstance().getDcentManager());
                    } else if (Objects.equals(operationItem, getResources().getString(R.string.list_opt_Klaytn_ValueMemoTx_PD))) {
                        klaytnResponse = TestKlaytnTransaction.testPFDelegatedValueMemoTx(DcentSdkManager.getInstance().getDcentManager());
                    } else if (Objects.equals(operationItem, getResources().getString(R.string.list_opt_Klaytn_CancelTx))) {
                        klaytnResponse = TestKlaytnTransaction.testCancelTx(DcentSdkManager.getInstance().getDcentManager());
                    } else if (Objects.equals(operationItem, getResources().getString(R.string.list_opt_Klaytn_CancelTx_D))) {
                        klaytnResponse = TestKlaytnTransaction.testDelegatedCancelTx(DcentSdkManager.getInstance().getDcentManager());
                    } else if (Objects.equals(operationItem, getResources().getString(R.string.list_opt_Klaytn_CancelTx_PD))) {
                        klaytnResponse = TestKlaytnTransaction.testPFDelegatedCancelTx(DcentSdkManager.getInstance().getDcentManager());
                    } else if (Objects.equals(operationItem, getResources().getString(R.string.list_opt_Klaytn_KRC20Tx))) {
                        klaytnResponse = TestKlaytnTransaction.testKRC20Tx(DcentSdkManager.getInstance().getDcentManager());
                    } else if (Objects.equals(operationItem, getResources().getString(R.string.list_opt_Klaytn_KRC20Tx_D))) {
                        klaytnResponse = TestKlaytnTransaction.testDelegatedKRC20Tx(DcentSdkManager.getInstance().getDcentManager());
                    } else if (Objects.equals(operationItem, getResources().getString(R.string.list_opt_Klaytn_KRC20Tx_PD))) {
                        klaytnResponse = TestKlaytnTransaction.testPFDelegatedKRC20Tx(DcentSdkManager.getInstance().getDcentManager());
                    } else {
                        String finalResponse = "Not Reached!!";
                        binding.resInfo.post(() -> {
                            binding.resInfo.setText("[" + operationItem + "]  Failed\n");
                            binding.resInfo.append(finalResponse);
                            Log.i(sLogTag, "Leave" + "[" + operationItem + "]");
                            getActivity().getWindow().clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
                        });
                        return;
                    }
                } catch (DcentException e) {
                    e.printStackTrace();
                    binding.resInfo.post(() -> {
                        binding.resInfo.setText("[" + operationItem + "]  Failed\n");
                        binding.resInfo.append("[KlaytnTransaction] Exception msg: " + e.get_err_msg());
                        Log.i(sLogTag, "Leave" + "[" + operationItem + "]");
                        getActivity().getWindow().clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
                    });

                    return;
                }


                //Display Result
                List<String> finalKlaytnResponse = klaytnResponse;
                binding.resInfo.post(() -> {
                    binding.resInfo.setText("[" + operationItem + "]  Success\n");
                    for (String response : finalKlaytnResponse) {
                        if (android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.N) {
                            // noinspection deprecation
                            binding.resInfo.append(android.text.Html.fromHtml(response + "<br>\n"));
                        } else {
                            binding.resInfo.append(android.text.Html.fromHtml(response + "<br>\n", Html.FROM_HTML_MODE_LEGACY));
                        }
                    }
                    Log.i(sLogTag, "Leave" + "[" + operationItem + "]");
                    getActivity().getWindow().clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

                    isProcessing = false;
                });

            });
            thread.start();
        });

        klaytnTestFragment.show(((FragmentActivity)getContext()).getSupportFragmentManager(), null);
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        klaytnTestFragment.dismiss();
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
        if(thread != null && thread.isAlive())
            thread.interrupt();
        thread = null;
    }
}