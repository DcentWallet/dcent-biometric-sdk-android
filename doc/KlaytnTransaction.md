# `KlaytnTransaction`

#### Parameter

| Name      | Type             | Description                                                                                                                             |
| :-------- | ---------------- | --------------------------------------------------------------------------------------------------------------------------------------- |
| hdKeyPath | `Bip44KeyPath` | sign key path for klaytn transaction                                                                                                    |
| nonce     | `String`       | account nonce for klaytn transaction                                                                                                    |
| toAddr    | `String`       | address to send                                                                                                                         |
| amount    | `String`       | amount of ETH to send                                                                                                                   |
| gasLimit  | `String`       | gas limit value                                                                                                                         |
| gasPrice  | `String`       | gas price for klaytn transaction                                                                                                        |
| data      | `String`       | transaction data of klaytn transaction                                                                                                  |
| chainId   | `int`          | chain id                                                                                                                                |
| txType    | `KlaytnTxType` | (optional)Type of klaytn transaction<br /> This can be set using the `setOptionAttribute()` method. The default is `TX_TYPE_LEGACY` |
|           |                  |                                                                                                                                         |
| fromAddr  | `String`       | (optional)Signer's Address of klaytn transaction<br />This can be set using the `setOptionAttribute()` method.                        |
| feeRatio  | `int`          | (optional)Fee Ratio of klaytn transaction<br />This can be set using the `setOptionAttribute()` method.                               |
| tokenName | `String`       | (optional)token name of contract for `KLAYTN_ERC20` transaction<br />This can be set using the `setTokenAttribute()`method.        |
| decimals  | `String`       | (optional)decimals of contract for `KLAYTN_ERC20` transaction<br />This can be set using the `setTokenAttribute()`method.          |
| symbol    | `String`       | (optional)symbol of contract for `KLAYTN_ERC20` transaction<br />This can be set using the `setTokenAttribute()`method.            |
