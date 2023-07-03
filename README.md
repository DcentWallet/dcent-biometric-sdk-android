# dcent-biometric-sdk-android

This is D'CENT Biometric Android SDK. This SDK allows your application to quickly create an wallet application using D'CENT biometric wallet.

# User Start Guide

## Setup

Developers can develop wallet application using our andoird sdk. The .aar file in output directory is our sdk library file.

Copy ``dcent-biometric-sdk-android-v.X.X.aar`` file to ``libs`` folder in your android application project.

![image](https://user-images.githubusercontent.com/96401185/233911728-750925b6-a01b-4314-93a4-928d0260dbec.png)

Add the following to your **build.gradle** and **manifest** file:

### build.glade(:app)

```java
dependencies {
    // # add dcent-biometric-sdk-android-vX.X.aar file
    implementation files('libs/dcent-biometric-sdk-android-vX.X.aar')

    // # required to use dcent-biometric-sdk-android
    implementation 'com.google.protobuf:protobuf-java:3.5.1'
    implementation 'org.web3j:core:3.3.1-android'
    implementation 'com.klaytn.caver:core:1.0.0'
}
```

### AndroidManifest.xml

```xml
<uses-permission android:name="android.permission.BLUETOOTH" android:maxSdkVersion="30" />
<uses-permission android:name="android.permission.BLUETOOTH_ADMIN" android:maxSdkVersion="30" />

<!-- Bluetooth permissions: Android API >= 31 (Android 12)-->
<uses-permission android:name="android.permission.BLUETOOTH_CONNECT"/>
<uses-permission android:name="android.permission.BLUETOOTH_SCAN" android:usesPermissionFlags="neverForLocation" tools:targetApi="s" />
```

## Usage

Refer to the [`sample-app`](/sample-app) directory. There is an android project using our android sdk.

> **All instance method should be called from a background thread because it can take a long time!**

> The D'CENT biometric wallet device is turned off after 5 minutes of inactivity. It is recommended that you regularly use `sendDummyCommand()` provided by the SDK to maintain the connection of the device.

### DcentManager initialize

The entry point for interacting with the D'CENT biometric wallet device is the `DcentManager`, so you must create and initialize a `DcentManager` instance.

And  find out if the D'CENT wallet is connected, `DcentManager.Observer` interface should be implemented.

**sample**

```java
public class MainActivity extends Activity implements DcentManager.Observer {
	// DcentManager Object
	DcentManager mDcentmanager;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mDcentmanager = new DcentManager(this); // Create DcentManager Object
		mDcentmanager.subscribe(this); // Subscribe DCENT event using DcentManager.Observer
		mDcentmanager.initialize(); // Initialize dcentmanager
	}
	@Override
	protected void onDestroy() {
		//Unsubscribe DCENT event using DcentManager.Observer
		mDcentmanager.setDcentManagerUnSubscribe(this);
		super.onDestroy();
	}
	///// override DcentManager.Observer interface
	@Override
	public void dcentDongleConnected() {
		//TODO: Add your code
	}
	@Override
	public void dcentDongleDisconnected() {
		//TODO: Add your code
	}
		/////
}
```

### getDeviceInfo

Get the information about the current connected D'CENT biometric wallet device.

**Parameters**

None

**Returns**

[`DeviceInfo`](./doc/DeviceInfo.md) - the current connected D'CENT biometric wallet device Information

**Example**

```java
DeviceInfo mDeviceInfo = mDcentmanager.getDeviceInfo();
```

### setDeviceLabel

Set the label name to the D'CENT biometric wallet.(If you reboot your D'CENT, you can see the label name.)

**Parameters**

| Parameter | Type       | Description                                |
| --------- | ---------- | ------------------------------------------ |
| label     | `String` | the label name of D'CENT biometric wallet. |

**Returns**

None

**Example**

```java
mDcentmanager.setDeviceLavel("newLabel");
```

### getAccountInfo

Get the list of current accounts in the D'CENT biometric wallet.

**Parameters**

None

**Returns**

[`List<Account>`](./doc/Account.md) - current account list in D'CENT biometric wallet.

**Example**

```java
List<Account> accountList = mDcentmanager.getAccountInfo()
```

### syncAccount

Create or update accounts on the network.

If the account of the specified key path is already exist, the `syncAccount()` method do not create account just sync the account information. For example, if you want to change the label of account or modify the balance, you can use the `syncAccount()` method.

**(※ As of firmware version 2.9.2 or later, the D'CENT biometric wallet device does not support updating the balance.)**

**Parameters**

| Parameter | Type                                     | Description                           |
| --------- | ---------------------------------------- | ------------------------------------- |
| accounts  | `List<SyncAccount>` \| `SyncAccount` | account list or account to be synced. |

**Returns**

`boolean` - sync result. true if you sync completely otherwise false.

**Example**

```java
  String labelOfAccount = "bitcoin_1" ; // account label
  String balanceOfAccount = "0 BTC" ; // balance of account. This string will be displayed on device.
  String keyPath = "m/44'/0'/0'/0/0/"; // key path of the account

  /* 
  Bitcoin account will be created.
  if bitcoin account is already created, the bitcoin account label and balance will be just modified.
  */
  SyncAccount syncAccount = new SyncAccount(CoinType.BITCOIN.getCoinGroup(),
  CoinType.BITCOIN.name(),
  labelOfAccount,
  balanceOfAccount,
  keyPath);
  mDcentmanager.syncAccount(syncAccount);
  
```

### getAddress

Get the address of `CoinType` and BIP44 Key Path.

**Parameters**

| Parameter | Type                           | Description              |
| --------- | ------------------------------ | ------------------------ |
| coinType  | `CoinType`                   | coin type.               |
| keyPath   | `Bip44KeyPath` \| `String` | key path to get address. |

**Returns**

`HashMap<String, String>` - address{address, pubkey}. (pubkey exists only when the coinType is `CoinType.TEZOS`, `CoinType.XTZ_FA`, `CoinType.TEZOS_TESTNET` and `CoinType.XTZ_FA_TESTNET`.)

**Example(bitcoin)**

```java
  String keyPath = "m/44'/0'/0'/0/0/"; // key path of the account
  HashMap<String, String> response = mDcentmanager.getAddress(CoinType.BITCOIN, keyPath);
  String address = response.get("address");
```

### getXPUB

Get a xpub key of BIP44 Key Path or BIP0032 master key

**Example**

```java
  String keyPath = "m/44'/0'"; // key path of the account
  String address = mDcentmanager.getXPUB(keyPath, null);
```

### getEthereumMessageSigned

Get the sign value of "EVM" message sign(personal_sign & signTypedData).

**This function for :**

* `ETHEREUM` (ETH) - ethereum
* `ETHEREUM_KOVAN` (ETHt) - etheruem kovan
* `ETHEREUM_ROPSTEN` (ETHt) - etheruem ropsten
* `ETHEREUM_GOERLI` (ETHt) - etheruem goerli
* `ETHEREUM_RINKEBY` (ETHt) - etheruem rinkeby
* `RSK` (RBTC)- rsk smart bitcoin
* `RSK_TESTNET` (RBTCt) - rsk smart bitcoin testnet
* `XDC` (XDC) - xdc network
* `XDC_APOTHEM` (XDCt) - xdc apothem
* `BSC` (BNB) - binance smart chain
* `BSC_TESTNET` (BNBt) - binance smart chain testnet
* `POLYGON` (MATIC) - polygon
* `POLYGON_TESTNET` (MATICt) - polygon testnet
* `ETH_CHAIN` - other evm networks
* `KLAYTN` (KLAY) - klaytn
* `KLAY_BAOBAB` (KLAYt) - klaytn baobab

**Parameters**

| Parameter   | Type                                                                                                                | Description                                                                                                                                                                   |
| ----------- | ------------------------------------------------------------------------------------------------------------------- | ----------------------------------------------------------------------------------------------------------------------------------------------------------------------------- |
| coinType    | `CoinType`                                                                                                        | coin type.                                                                                                                                                                    |
| transaction | [EthMesageSignData](./doc/EthMesageSignData.md) | ethereum message sign data parameters.                                                                                                                                        |
| command     | `String`                                                                                                          | data sign command.(`"msg_sign"` \| `"sign_data"`) <br /> - `"msg_sign"` for personal_sign or 'eth_sign' of EVM<br />- `"sign_data"` for signTypedData(EIP-721) of EVM |

**Returns**

`HashMap<String, String>` - signedData{address,sign}

**Requirements**

* D'CENT Biometric Wallet version 1.3.0. or higher is required.
  * (for EIP-721) version 2.11.1. or higher is required.

**Example(ethereum personal_sign)**

```java
EthMesageSignData ethMesageSignData;
ethMesageSignData = new EthMesageSignData.Builder()
                    	.keyPath(Bip44KeyPath.valueOf("m/44'/60'/0'/0/0"))
                    	.data("Message Sign TEST")
                    	.build();
HashMap<String, String> response = mDcentmanager.getEthereumMessageSigned(CoinType.ETHEREUM, ethMesageSignData, "msg_sign");
String address = response.get("address");
String sign = response.get("sign");
```

### SignedTransaction

Depending on the coin type, the function for signing transaction and the transaction parameter used are different.

#### getBitcoinSignedTransaction

**This function for :**

* `BITCOIN` (BTC) - bitcoin
* `BITCOIN_SV` (BSV) - bitcoin sv
* `BTC_SEGWIT` (BTC) - bitcoin segwit
* `MONACOIN` (MONA) - monacoin
* `LITECOIN` (LTC) - litecoin
* `LTC_SEGWIT` (LTC) - litecoin segwit
* `ZCASH` (ZEC) - zcash
* `HORIZEN` (ZEN) - horizen
* `BITCOINCASH` (BCH) - bitcoin cash
* `DOGECOIN` (DOGE) - dogecoin
* `BITCOINABC` (BCHA) - bitcoin cash abc
* `ECASH` (XEC) - ecash
* `DASH` (DASH) - dash
* `BITCOIN_GOLD` (BTG) - bitcoin gold
* `DIGIBYTE` (DGB) - digibyte
* `DGB_SEGWIT` (DGB)- digibyte segwit
* `RAVENCOIN` (RVN) - ravencoin
* `BITCOIN_TESTNET` (BTCt) - bitcoin testnset
* `BTC_SEGWIT_TESTNET` (BTCt) - bitcoin segwit testnet
* `MONACOIN_TESTNET` (MONAt) - monacoin testnet
* `LITE_TESTNET` (tLTC) - litecoin testnet
* `LTC_SEGWIT_TESTNET` (tLTC) - litecoin segwit testnet
* `ZCASH_TESTNET` (TAZ) - zcash testnet
* `BCH_TESTNET` (tBCH)- bitocin cash testnet
* `DASH_TESTENET` (tDASH) - dash testnet
* `BTG_TESTNET` (tBTG) - bitcoin gold testnet
* `DIGIBYTE_TESTNET` (tDGB) - digibyte testnet
* `DGB_SEGWIT_TESTNET` (tDGB) - digibyte segwit testnet
* `RVN_TESTNET` (tRVN) - ravencoin testnet

**Parameters**

| Parameter   | Type                                                                                                                                     | Description                     |
| :---------- | ---------------------------------------------------------------------------------------------------------------------------------------- | ------------------------------- |
| coinType    | `CoinType`                                                                                                                             | bitcoin coin type.              |
| transaction | [BitCoinTransaction](./doc/BitcoinTransaction.md#bitcointransaction) | bitcoin transaction parameters. |

**Returns**

`String` - signed transaction.

**Requirements**

* Refer to "[D`CENT Firmware Update History](https://dcentwallet.com/support/FirmwareUpdate)" to determine which D'CENT Biometric Wallet versions are supported on each network.

**Example**

```java
UnspentTransactionOutput utxo = new UnspentTransactionOutput("01000000012b09bd990adc6792588225486c336fb2090890341fcbc6ae92c440c3bd266b98010000006a47304402201df6bd2294f9d51496c1be7ea09431fcfee4b0ca9359712c2c381aff9b2d6f070220595a5bb4e9f0f0d1f5fb9a800224c01ac99058d9b491cd7e6a60145bbd26ddca0121028cbb73e589f81937784eaf728cd14ad27984e5415766c04408211af8d9e30ee7ffffffff0127810000000000001976a9141c7254fac600ef7371664a613f0323c6c641cbd288ac00000000", 0, BitCoinTransaction.TxType.p2pkh, Bip44KeyPath.valueOf("m/44'/0'/0'/0/0")) ;

List<UnspentTransactionOutput> input = new ArrayList<>();
input.add(utxo) ;

TransactionOutput txo = new TransactionOutput(10000, Arrays.asList(new String[]{"1Ckii7MpiquSxcmo2ch1UTfQMConz31rpB"}), BitCoinTransaction.TxType.p2pkh  ) ;
List<TransactionOutput> output = new ArrayList<>();
output.add(txo) ;

BitCoinTransaction bitCoinTransaction = new BitCoinTransaction.Builder()
                .version(1)
                .input(input)
                .output(output)
                .locktime(0)
                .build();

String response = mDcentManager.getBitcoinSignedTransaction(CoinType.BITCOIN, bitCoinTransaction);
```

#### getEthereumSignedTransaction

**This function for :**

* `ETHEREUM` (ETH) - ethereum
* `ETH_CLASSIC` (ETC) - ethereum classic
* `ETHEREUM_KOVAN` (ETHt) - etheruem kovan
* `ETHEREUM_ROPSTEN` (ETHt) - etheruem ropsten
* `ETHEREUM_GOERLI` (ETHt) - etheruem goerli
* `ETHEREUM_RINKEBY` (ETHt) - etheruem rinkeby
* `FLARE_COSTON` (CFLR) - flare network coston
* `RSK` (RBTC)- rsk smart bitcoin
* `RSK_TESTNET` (RBTCt) - rsk smart bitcoin testnet
* `XDC` (XDC) - xdc network
* `XDC_APOTHEM` (XDCt) - xdc apothem
* `BSC` (BNB) - binance smart chain
* `BSC_TESTNET` (BNBt) - binance smart chain testnet
* `POLYGON` (MATIC) - polygon
* `POLYGON_TESTNET` (MATICt) - polygon testnet
* `ETH_CHAIN` - other evm networks

**Parameters**

| Parameter   | Type                                                                                                                    | Description                      |
| :---------- | ----------------------------------------------------------------------------------------------------------------------- | -------------------------------- |
| coinType    | `CoinType`                                                                                                            | ethereum coin type.              |
| transaction | [EtheruemTransaction](./doc/EthereumTransaction.md) | ethereum transaction parameters. |

**Returns**

`String` - signed transaction.

**Requirements**

* Refer to "[D`CENT Firmware Update History](https://dcentwallet.com/support/FirmwareUpdate)" to determine which D'CENT Biometric Wallet versions are supported on each network.

**Example**

```java
EthereumTransanction ethereumTransanction;
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
String response = mDcentmanager.getEthereumSignedTransaction(CoinType.ETHEREUM, ethereumTransanction);
```

#### getKalytnSignedTransaction

**This function for :**

* `KLAYTN` (KLAY)
* `KLAYTN_ERC20` - klaytn token
* `KLAY_BAOBAB` (KLAYt)
* `KRC20_BAOBAB`

**Parameters**

| Parameter   | Type                                                                                                                | Description                     |
| :---------- | ------------------------------------------------------------------------------------------------------------------- | ------------------------------- |
| coinType    | `CoinType`                                                                                                        | klaytn coin type.               |
| transaction | [KlaytnTransaction](./doc/KlaytnTransaction.md) | klaytn transaction parameters. |

**Returns**

`String` - signed transaction.

**Example**

```java
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

String response = mDcentmanager.getKlaytnSignedTransaction(CoinType.KLAYTN, klaytnTransanction);
```

#### getTokenSignedTransaction

**This function for :**

* `ERC20` - ethereum erc20 token
* `ETC_ERC20` - ethereum classic erc20 token
* `ERC20_KOVAN` - etheruem kovan erc20 token
* `ERC20_ROPSTEN` - etheruem ropsten erc20 token
* `ERC20_GOERLI` - etheruem goerli erc20 token
* `ERC20_RINKEBY` - etheruem rinkeby erc20 token
* `FRC20_COSTON` - flare network coston token
* `RRC20` - rsk smart bitcoin token
* `RRC20_TESTNET` - rsk smart bitcoin testnet token
* `XRC20` - xdc xrc20 token
* `XRC20_APOTHEM` - xdc apothem xrc20 token
* `BSC_BEP20` - binance smart chain bep20 token
* `BSC_BEP20_TESTNET` - binance smart chain bep20 testnet token
* `POLYGON_ERC20` - polygon erc20 token
* `POLYGON_ERC20_TESTNET` - polygon erc20 testnet token
* `ECH_ERC20` - other evm erc20 token

**Parameters**

| Parameter   | Type                                                                                                              | Description                   |
| :---------- | ----------------------------------------------------------------------------------------------------------------- | ----------------------------- |
| coinType    | `CoinType`                                                                                                      | erc20 type.                   |
| transaction | [TokenTransaction](./doc/TokenTransaction.md) | token transaction parameters. |

**Returns**

`String` - signed transaction.

**Requirements**

* Refer to "[D`CENT Firmware Update History](https://dcentwallet.com/support/FirmwareUpdate)" to determine which D'CENT Biometric Wallet versions are supported on each network.

**Example**

```java
TokenTransaction erc20transaction;
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

String response = mDcentmanager.getTokenSignedTransaction(CoinType.ERC20, erc20transaction)
```

#### getXrpSignedTransaction

**This function for :**

* `XRP` (XRP) - xrpl
* `XRP_TA` - xrp ta
* `XRP_TESTNET` (XRPt)
* `XRP_TA_TESTNET`

**Parameters**

| Parameter   | Type                                                                                                          | Description                 |
| :---------- | ------------------------------------------------------------------------------------------------------------- | --------------------------- |
| coinType    | `CoinType`                                                                                                  | xrp coin type.              |
| transaction | [XrpTransaction](./doc/XrpTransaction.md) | xrp transaction parameters. |

**Returns**

`String` - signed transaction.

**Example**

```java
XrpTransaction xrpTransaction;

String address = mDcentManager.getAddress(CoinType.RIPPLE, Bip44KeyPath.valueOf("m/44'/144'/0'/0/0")).get("address");
xrpTransaction = new XrpTransaction.Builder()
                .keyPath(Bip44KeyPath.valueOf("m/44'/144'/0'/0/0"))
                .sourceAddress(address)
                .destinationAddress("rsHXBk5vnswg5SZxUQCEPYVnmrd4PaZ7Ah")
                .amountDrops(2)
                .feeDrops(10)
                .sequence(11)
                .destinationTag(-1)
                .build();

String response = mDcentManager.getXrpSignedTransaction(CoinType.XRP, xrpTransaction);
```

#### getXRPSignedTransactionWithUnsignedTx

**This function for :**

* `XRP` (XRP) - xrpl
* `XRP_TA` - xrp ta
* `XRP_TESTNET` (XRPt)
* `XRP_TA_TESTNET`

**Parameters**

| Parameter   | Type                                                                                                          | Description                 |
| :---------- | ------------------------------------------------------------------------------------------------------------- | --------------------------- |
| coinType    | `CoinType`                                                                                                  | xrp coin type.              |
| transaction | [XrpTransaction](./doc/XrpTransaction.md) | xrp transaction parameters. |

**Returns**

`HashMap<String, String>` -  signed transaction{sign, pubkey, accountId}.

**Example**

```java
XrpTransaction xrpTransaction;
xrpTransaction= new XrpTransaction .Builder()
                .keyPath(Bip44KeyPath.valueOf("m/44'/144'/0'/0/0"))
                .unsignedTx("1200002280000000240238634E2E00000000201B023863E161400000000098968068400000000000000A8114FD970F4612987680F4008BA53ED6FD87BE0DAAF983141DEE2154B117FB47FCF4F19CD983D9FCBB894FF7")
                .build();
HashMap<String, String> response = mDcentmanager.getXRPSignedTransactionWithUnsignedTx(CoinType.XRP, xrpTransaction);

String resSign = response.get("sign");
String resPubKey = response.get("pubkey");
String res_accountId = response.get("accountId");
```

#### getBinanceSignedTransaction

**This function for :**

* `BINANCE` (BNB)
* `BEP2` - Binance token
* `BNB_TESTNET` (BNB)
* `BEP2_TESTNET`

**Parameters**

| Parameter   | Type                                                                                                                 | Description                    |
| :---------- | -------------------------------------------------------------------------------------------------------------------- | ------------------------------ |
| coinType    | `CoinType`                                                                                                         | binance coin type.             |
| transaction | [BinaceTransaction](./doc/BinanceTransaction.md) | binace transaction parameters. |

**Returns**

`String` - signed transaction.

**Requirements**

* D'CENT Biometric Wallet version 1.9.2. or higher is required.

**Example**

```java
String keyPath = "m/44'/714'/0'/0/0";
BinanceTransaction binanceTransaction;

binanceTransaction = new BinanceTransaction.Builder()
                    .keyPath(Bip44KeyPath.valueOf("m/44'/714'/0'/0/0"))
                    .signBytes("7b226163636f756e745f6e756d626572223a22343834303237222c22636861696e5f6964223a2242696e616e63652d436861696e2d546967726973222c2264617461223a6e756c6c2c226d656d6f223a22222c226d736773223a5b7b22696e70757473223a5b7b2261646472657373223a22626e62317a7a34647634767235687a3076647161686334707371797a39676a303974703775646d673273222c22636f696e73223a5b7b22616d6f756e74223a313030303030302c2264656e6f6d223a22424e42227d5d7d5d2c226f757470757473223a5b7b2261646472657373223a22626e6231796d33377164616e76716b72686139636b70686a6b3067336d6630367068656d33787a393437222c22636f696e73223a5b7b22616d6f756e74223a31303030303030303030302c2264656e6f6d223a22424e42227d5d7d5d7d5d2c2273657175656e6365223a2230222c22736f75726365223a2230227d")
                    .feeValue("375000")
                    .build();

String response = mDcentmanager.getDcentManager().getBinanceSignedTransaction(CoinType.BINANCE, binanceTransaction);
```

#### getStellarSignedTransaction

**This function for :**

* `STELLAR`(XLM)
* `STELLAR_TA`
* `XLM_TESTNET`(XLMt)
* `XLM_TA_TESTNET`

**Parameters**

| Parameter   | Type                                                                                                                  | Description                     |
| :---------- | --------------------------------------------------------------------------------------------------------------------- | ------------------------------- |
| coinType    | `CoinType`                                                                                                          | stellar coin type.              |
| transaction | [StellarTransaction](./doc/StellarTransaction.md) | stellar transaction parameters. |

**Returns**

`String` - signed transaction.

**Requirements**

* D'CENT Biometric Wallet version 2.1.0. or higher is required.

**Example**

```java
String keyPath = "m/44'/148'/0'";
StellarTransaction stellarTransaction;

stellarTransaction = new StellarTransaction.Builder()
                    .keyPath(Bip44KeyPath.valueOf(keyPath))
                    .signBytes("7ac33997544e3175d266bd022439b22cdb16508c01163f26e5cb2a3e1045a9790000000200000000d147b20efbeb51a8f8eea50f4ce1ad549796a509236c3cad056e22cf3e3e6f0b000000640005821d00000004000000010000000000000000000000005ec76b4a00000000000000010000000000000000000000005e3deafcf4bee3bf40a85e4f93bdf7d94e62a05e811ed787f9d04bc983ec207b0000000049504f8000000000")
                    .build();

String response = mDcentmanager.getStellarSignedTransaction(CoinType.STELLAR, stellarTransaction);
```

#### getTronSignedTransaction

**This function for :**

* `TRON` (TRX)
* `TRC_TOKEN` - TRON Token
* `TRX_TESTNET` (tTRX) - TRON  Testnet
* `TRC_TESTNET`

**Parameters**

| Parameter   | Type                                                                                                            | Description                  |
| :---------- | --------------------------------------------------------------------------------------------------------------- | ---------------------------- |
| coinType    | `CoinType`                                                                                                    | tron coin type.              |
| transaction | [TronTransaction](./doc/TronTransaction.md) | tron transaction parameters. |

**Returns**

`String` - signed transaction.

**Requirements**

* D'CENT Biometric Wallet version 2.2.2. or higher is required.

**Example**

```java
String keyPath = "m/44'/195'/0'/0/0";
TronTransaction tronTransaction;

tronTransaction = new TronTransaction.Builder()
                    .keyPath(Bip44KeyPath.valueOf(keyPath))
                    .signBytes("0a02610a220838507457b79561a740e8dd8fefaf2e5a65080112610a2d747970652e676f6f676c65617069732e636f6d2f70726f746f636f6c2e5472616e73666572436f6e747261637412300a15418f2d2dfaa81af60f5a3ac4ca5597e795aff7abae121541c27dcd7d914fd6aa8fec3c8a41cb2e90883bc6f0187f70dd978cefaf2e")
                    .feeValue("0.262")
                    .build();

String response = mDcentmanager.getTronSignedTransaction(CoinType.TRON, tronTransaction);
```

#### getCardanoSignedTransaction

**This function for :**

* `CARDANO` (ADA)
* `CARDANO_TESTNET` (ADAt)

**Parameters**

| Parameter   | Type                                                                                                                 | Description                  |
| :---------- | -------------------------------------------------------------------------------------------------------------------- | ---------------------------- |
| coinType    | `CoinType`                                                                                                         | cardano coin type.           |
| transaction | [CardaonTransaction](./doc/CardanoTransacion.md) | tron transaction parameters. |

**Returns**

`String` - signed transaction.

**Requirements**

* D'CENT Biometric Wallet version 2.7.0. or higher is required.

**Example**

```java
String keyPath = "m/44'/1815'/0'/0/0";
CardanoTransacion cardanoTransacion;

cardanoTransacion = new CardanoTransacion.Builder()
                    .keyPath(Bip44KeyPath.valueOf(keyPath))
                    .unsignedTx("a400818258209087b68d8f59dca2a29a3f5c03db7b162ba2607fa930f95996d12b1a570a84ef01018182583901b552fcf04820629ec73d54cd8cac3fccb4902d81f7801b65c654a490f122908efcb7fc3b457a83c4a1a50a7e7e919694fdc195d55c1a1b961a00297398021a00028911031a0185c2df")
                    .build();

String response = mDcentmanager.getCardanoSignedTransaction(CoinType.CARDANO, cardanoTransacion);
```

#### getHederaSignedTransaction

**This function for :**

* `HEDERA` (HBAR)
* `HEDERA_HTS` - hedera token
* `HEDERA_TESTNET` (HBARt)
* `HTS_TESTNET`

**Parameters**

| Parameter   | Type                                                                                                                | Description                    |
| :---------- | ------------------------------------------------------------------------------------------------------------------- | ------------------------------ |
| coinType    | `CoinType`                                                                                                        | hedera coin type.              |
| transaction | [HederaTransaction](./doc/HederaTransaction.md) | hedera transaction parameters. |

**Returns**

`String` - signed transaction.

**Requirements**

* D'CENT Biometric Wallet version 2.13.0 or higher is required.

**Example**

```java
String keyPath = "m/44'/3030'/0'";
HederaTransaction hederaTransaction;

hederaTransaction = new HederaTransaction.Builder()
                    .keyPath(Bip44KeyPath.valueOf(keyPath))
                    .decimals(8)
                    .unsigned("0a1a0a0b0893b888880610cdfab9741209080010001895cb84011800120608001000180318c0843d22020878320072280a260a110a09080010001895cb840110ff87debe010a110a090800100018c1cb8401108088debe01")
                    .symbol("HBAR")
                    .build();

String response = mDcentmanager.getHederaSignedTransaction(CoinType.HEDERA, hederaTransaction);
```

#### getStacksSignedTransaction

**This function for :**

* `STACKS `(STX)
* `SIP010` - stacks token
* `STACKS_TESTNET` (STXt)
* `SIP010_TESTNET`

**Parameters**

| Parameter   | Type                                                                                                                | Description                    |
| :---------- | ------------------------------------------------------------------------------------------------------------------- | ------------------------------ |
| coinType    | `CoinType`                                                                                                        | stacks coin type.              |
| transaction | [StacksTransaction](./doc/StacksTransaction.md) | stacks transaction parameters. |

**Returns**

`String` - signed transaction.

**Requirements**

* D'CENT Biometric Wallet version 2.14.1 or higher is required.
  * (for SIP010): 2.16.7 or higher is required.

**Example**

```java
String keyPath = "m/44'/5757'/0'/0/0";
StacksTransaction stacksTransaction;

stacksTransaction = new StacksTransaction.Builder()
                    .keyPath(Bip44KeyPath.valueOf(keyPath))
                    .sigHash("0000000001040027b586cbbbd0902773c2faafb2c511b130c3610800000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000030200000000021608633eac058f2e6ab41613a0a537c7ea1a79cdd20f6d69616d69636f696e2d746f6b656e087472616e736665720000000401000000000000000000000000000f4240051627b586cbbbd0902773c2faafb2c511b130c361080516e3d94a92b80d0aabe8ef1b50de84449cd61ad6370a0200000006313130343239")
                    .fee("0.12")
                    .nonce("0000000000000029")
                    .decimals(6)
                    .authType(4)
                    .symbol("MIAMI")
                    .optionParam("01") // assert transfer
                    .build();

String response = mDcentmanager.getStacksSignedTransaction(CoinType.SRC20, stacksTransaction);
```

#### getSolanaSignedTransaction

**This function for :**

* `SOLANA` (SOL)
* `SPL_TOKEN` - solana token

**Parameters**

| Parameter   | Type                                                                                                                | Description                    |
| :---------- | ------------------------------------------------------------------------------------------------------------------- | ------------------------------ |
| coinType    | `CoinType`                                                                                                        | solana coin type.              |
| transaction | [SolanaTransaction](./doc/SolanaTransaction.md) | solana transaction parameters. |

**Returns**

`String` - signed transaction.

**Requirements**

* D'CENT Biometric Wallet version 2.16.5. or higher is required.
  * (for spl-token): 2.17.1. or higher is required.

**Example**

```java
String keyPath = "m/44'/501'/0'";
SolanaTransaction solanaTransaction;

solanaTransaction = new SolanaTransaction.Builder()
                    .keyPath(Bip44KeyPath.valueOf(keyPath))
                    .sigHash("010500137842adebf146d55498bf1e9c59cb1252338d79b9f9598eebd7fb2f76940b411814a9cc38c2da6f91700622eb3b8ddff7707a7e6bfcbb4301b9c442990b92dc8cc40a4939977b82afe23c3c6bf859cc74c8757a688e46c9633bb8051138b81b76eebcaf41d63734864c18aa510ca43c89f38eb80d40c1ad95d8f34a93b8364c8894b15d072786475a09b4944c100d830a7bd843bbab2955290bfbba9bb4650ae71314562a9db4410932a5510cbccf45294213ea9c9b237775d189801be4edde0504d42854e9bad4cef8bc8d53ec576586258bdf1f37ba15e7e253487bb4ee952659e944ad9682b34349abe72246ef5b999b273ae987903504e02e6f82c635eb5a2aa9d58c2c76ade93cb3a212a0a5ba5d75a03ce1dedb219ba48fc9d7d972eb44b3ae006f4a26f61d3ed71088b47d4a01a21b78f52344ab7276d784968f3b73b3174ef9f7ecee2165c5b31505e0d1755e5725829a4a760a6b61628bb098da935f73eb0c866fb2830342292ab39aed6bd16844507bcfcc34dc3e80be29f3066bc28f140d143f584f237abf346a017098929cb8dc686ef1905a9ab9469bfe4e3bf4272e2356580b6175fda5e189259dad62bb45308cf466e8cabd1083fa25b4d51706ddf6e1d765a193d9cbe146ceeb79ac1cb485ed5f5b37913a8cf5857eff00a94157b0580f31c5fce44a62582dbcf9d78ee75943a084a393b350368d22899308850f2d6e02a47af824d09ab69dc42d70cb28cbfa249fb7ee57b9d256c12762ef444718f31bf2796cbdb84c8d1e5e2b23913531c84d40fcacc7c349ee58b214c14bd949c43602c33f207790ed16a3524ca1b9975cf121a2a90cffec7df8b68acdd4e1e0977eeb6eea0dddf7d99524fd7748c2b13dcb08146e4bda783811815e000112120e010f0203040510060708090a0b110c0d000930a1b10400000000f125b10d00000000")
                    .fee("0.0021")
                    .decimals(10)
                    .symbol("SOL")
                    .optionParam("030000000000000500") // dapp(03) + amount(0000000000000500)
                    .build();

String response = mDcentmanager.getSolanaSignedTransaction(CoinType.SPL_TOKEN, solanaTransaction);
```

#### getConfluxSignedTransaction

**This function for :**

* `CONFLUX` (CRX)

**Parameters**

| Parameter   | Type                                                                                                                  | Description                     |
| :---------- | --------------------------------------------------------------------------------------------------------------------- | ------------------------------- |
| coinType    | `CoinType`                                                                                                          | conflux coin type.              |
| transaction | [ConfluxTransaction](./doc/ConfluxTransaction.md) | conflux transaction parameters. |

**Returns**

`String` - signed transaction.

**Requirements**

* D'CENT Biometric Wallet version 2.18.3 or higher is required.

**Example**

```java
String keyPath = "m/44'/60'/0'/0/0";
ConfluxTransaction confluxTransaction;

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

String response = mDcentmanager.getConfluxSignedTransaction(CoinType.CONFLUX, confluxTransaction);
```

#### getConfluxCrc20SignedTransaction

**This function for :**

* `CFX_CRC20` - Conflux Token

**Parameters**

| Parameter   | Type                                                                                                                            | Description                           |
| :---------- | ------------------------------------------------------------------------------------------------------------------------------- | ------------------------------------- |
| coinType    | `CoinType`                                                                                                                    | conflux crc20 coin type.              |
| transaction | [ConfluxCrc20Transaction](./doc/ConfluxCrc20Transaction.md) | conflux crc20 transaction parameters. |

**Returns**

`String` - signed transaction.

**Requirements**

* D'CENT Biometric Wallet version 2.18.3 or higher is required.

**Example**

```java
String keyPath = "m/44'/60'/0'/0/0";
ConfluxCrc20Transaction confluxCrc20Transaction;

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

String response = mDcentmanager.getConfluxCrc20SignedTransaction(CoinType.CFX_CRC20, confluxCrc20Transaction);
```

#### getPolkadotSignedTransaction

**This function for :**

* `POLKADOT` (DOT)

**Parameters**

| Parameter   | Type                                                                                                                    | Description                      |
| :---------- | ----------------------------------------------------------------------------------------------------------------------- | -------------------------------- |
| coinType    | `CoinType`                                                                                                            | polkadot coin type.              |
| transaction | [PolkadotTransaction](./doc/PolkadotTransaction.md) | polkadot transaction parameters. |

**Returns**

`String` - signed transaction.

**Requirements**

* D'CENT Biometric Wallet version 2.19.1 or higher is required.

**Example**

```java
String keyPath = "m/44'/354'/0'/0/0";
PolkadotTransaction polkadotTransaction;

polkadotTransaction = new PolkadotTransaction.Builder()
                    .keyPath(Bip44KeyPath.valueOf(keyPath))
                    .sigHash("040000163a5ee36b1243ce5241c0a45010dd1717869e9918c040bf5d305be4a5af9e7a0b00407a10f35a003400a223000007000000e143f23803ac50e8f6f8e62695d1ce9e4e1d68aa36c1cd2cfd15340213f3423ee143f23803ac50e8f6f8e62695d1ce9e4e1d68aa36c1cd2cfd15340213f3423e")
                    .fee("0.0000000005")
                    .decimals(12)
                    .symbol("DOT")
                    .build();

String response = mDcentmanager.getPolkadotSignedTransaction(CoinType.POLKADOT, polkadotTransaction);
```

#### getCosmosSignedTransaction

**This function for :**

* `COSMOS` (ATOM)
* `COREUM` (CORE)

**Parameters**

| Parameter   | Type                                                                                                                | Description                     |
| :---------- | ------------------------------------------------------------------------------------------------------------------- | ------------------------------- |
| coinType    | `CoinType`                                                                                                        | cosmos coin type.               |
| transaction | [CosmosTransaction](./doc/CosmosTransaction.md) | cosmos transaction parameters. |

**Returns**

`String` - signed transaction.

**Requirements**

* D'CENT Biometric Wallet version 2.21.0 or higher is required.
  * COREUM: 2.25.0 or higher is required.

**Example**

```java
String keyPath = "m/44'/118'/0'/0/0";
CosmosTransaction cosmosTransaction;

cosmosTransaction = new CosmosTransaction.Builder()
                    .keyPath(Bip44KeyPath.valueOf(keyPath))
                    .sigHash("0a94010a8f010a1c2f636f736d6f732e62616e6b2e763162657461312e4d736753656e64126f0a2d636f736d6f73317235763573726461377866746833686e327332367478767263726e746c646a756d74386d686c122d636f736d6f733138766864637a6a7574343467707379383034637266686e64356e713030336e7a306e663230761a0f0a057561746f6d1206313030303030120012670a500a460a1f2f636f736d6f732e63727970746f2e736563703235366b312e5075624b657912230a21ffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffff12040a020801180a12130a0d0a057561746f6d12043530303010c09a0c1a0b636f736d6f736875622d34208f3a")
                    .fee("0.00025")
                    .decimals(6)
                    .symbol("ATOM")
                    .build();

String response = mDcentmanager.getCosmosSignedTransaction(CoinType.COSMOS, cosmosTransaction);
```

#### getTezosSignedTransaction

**This function for :**

* `TEZOS` (XTZ)
* `XTZ_FA`
* `TEZOS_TESTNET` (XTZ)
* `XTZ_FA_TESTNET`

**Parameters**

| Parameter   | Type                                                                                                              | Description                   |
| :---------- | ----------------------------------------------------------------------------------------------------------------- | ----------------------------- |
| coinType    | `CoinType`                                                                                                      | tezos coin type.              |
| transaction | [TezosTransaction](./doc/TezosTransaction.md) | tezos transaction parameters. |

**Returns**

`String` - signed transaction.

**Requirements**

* D'CENT Biometric Wallet version 2.23.1 or higher is required.
  * (for testnet) 2.24.1 or higher is required.

**Example**

```java
String keyPath = "m/44'/1729'/0'/0/0";
TezosTransaction tezosTransaction;

tezosTransaction = new TezosTransaction.Builder()
                    .keyPath(Bip44KeyPath.valueOf(keyPath))
                    .sigHash("03151ca887e6eda35fa2a169fda1b45bb0d3b47333b238b158481831b15a4161cd6c0021298384724bff62370492fbb56f408bf6f77bcfa907cdd6f804de22480001a91dcdedf09a5bf550e561e7eb4e00d5a6372c3d00ffff087472616e736665720000005907070100000024747a314e664e6973683565785351723146474563734b786b4c66454b347177616175696e07070100000024747a3156355733543845696176796252655166664e454c526f39334437663166506f575600a90f")
                    .fee("0.00314")
                    .decimals(9)
                    .symbol("GOLD")
                    .build();

String response = mDcentmanager.getTezosSignedTransaction(CoinType.TEZOS, tezosTransaction);
```

#### getVechainSignedTransaction

**This function for :**

* `VECHAIN`(VET)
* `VECHAIN_ERC20`

**Parameters**

| Parameter   | Type                                                                                                                  | Description                      |
| :---------- | --------------------------------------------------------------------------------------------------------------------- | -------------------------------- |
| coinType    | `CoinType`                                                                                                          | vechain coin type.               |
| transaction | [VechainTransaction](./doc/VechainTransaction.md) | vechain transaction parameters. |

**Returns**

`String` - signed transaction.

**Requirements**

* D'CENT Biometric Wallet version 2.23.2 or higher is required.

**Example**

```java
String keyPath = "m/44'/818'/0'/0/0";
VechainTransaction vechainTransaction;

vechainTransaction = new VechainTransaction.Builder()
                    .keyPath(Bip44KeyPath.valueOf(keyPath))
                    .sigHash("f83b2787c6143a04c08fe18202d0e1e094a57105e43efa47e787d84bb6dfedb19bdcaa8a908908e3f50b173c100001808082520880860152671166bdc0")
                    .fee("0.21")
                    .decimals(18)
                    .symbol("VET")
                    .build();

String response = mDcentmanager.getVechainSignedTransaction(CoinType.VECHAIN, vechainTransaction);
```

#### getNearSignedTransaction

**This function for :**

* `NEAR`(NEAR)
* `NEAR_TOKEN`
* `NEAR_TESTNET`(NEARt)

**Parameters**

| Parameter   | Type                                                                                                            | Description                  |
| :---------- | --------------------------------------------------------------------------------------------------------------- | ---------------------------- |
| coinType    | `CoinType`                                                                                                    | near coin type.              |
| transaction | [NearTransaction](./doc/NearTransaction.md) | near transaction parameters. |

**Returns**

`String` - signed transaction.

**Requirements**

* D'CENT Biometric Wallet version 2.24.0 or higher is required.
  * (for near-token): 2.27.1 or higher is required.

**Example**

```java
String keyPath = "m/44'/397'/0'";
NearTransaction nearTransaction;

nearTransaction = new NearTransaction.Builder()
                    .keyPath(Bip44KeyPath.valueOf(keyPath))
                    .sigHash("4000000033666164666339326631633631643261303138626166333738383566376633363331313439616331356163303438613263303137316566316661356139633366003fadfc92f1c61d2a018baf37885f7f3631149ac15ac048a2c0171ef1fa5a9c3f41b15753844300004000000033666164666339326631633631643261303138626166333738383566376633363331313439616331356163303438613263303137316566316661356139633366d5e91d9515257370e4763c0da089ca544c1292bd188ad3fee466e17024e941f40100000003000000a1edccce1bc2d3000000000000")
                    .fee("0.000860039223625")
                    .decimals(24)
                    .symbol("NEAR")
                    .build();


String response = mDcentmanager.getNearSignedTransaction(CoinType.NEAR, nearTransaction);
```

#### getHavahSignedTransaction

**This function for :**

* `HAVAH`(HVH)
* `HAVAH_HSP20`(HSP20)
* `HAVAH_TESTNET`(HVH)
* `HAVAH_HSP20_TESTNET`

**Parameters**

| Parameter   | Type                                                                                                              | Description                   |
| :---------- | ----------------------------------------------------------------------------------------------------------------- | ----------------------------- |
| coinType    | `CoinType`                                                                                                      | havah coin type.              |
| transaction | [HavahTransaction](./doc/HavahTransaction.md) | havah transaction parameters. |

**Returns**

* `String` - signed transaction.

**Requirements**

* D'CENT Biometric Wallet version 2.26.0 or higher is required.

**Example**

```java
String keyPath = "m/44'/858'/0'/0/0";
HavahTransaction havahTransaction;

havahTransaction = new HavahTransaction.Builder()
                    .keyPath(Bip44KeyPath.valueOf(keyPath))
                    .sigHash("6963785f73656e645472616e73616374696f6e2e66726f6d2e6878316531333433353935303532383335613064396137643064396533353839633433323831623262642e6e69642e30783130302e6e6f6e63652e3078312e737465704c696d69742e307831616462302e74696d657374616d702e3078356661316631343633666161302e746f2e6878353833323164313731633833393465613434303638376562623462353832623037353739356663352e76616c75652e307833636235396163376237353734652e76657273696f6e2e307833")
                    .fee("0.001375")
                    .decimals(18)
                    .symbol("HVH")
                    .build();

String response = mDcentmanager.getHavahSignedTransaction(CoinType.HAVAH, havahTransaction);
```

---

## Supported Coin List

[D`CENT Supported Coin List](https://dcentwallet.com/SupportedCoin)

## Firmware Version List

[D`CENT Firmware Update History](https://dcentwallet.com/support/FirmwareUpdate)
