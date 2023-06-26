# `XrpTransaction`

#### Parameter

| Name               | Type             | Description                                                                                                                  |
| ------------------ | ---------------- | ---------------------------------------------------------------------------------------------------------------------------- |
| hdKeyPath          | `Bip44KeyPath` | sign key path for xrp transaction                                                                                          |
| sourceAddress      | `String`       | Source address of xrp transaction<br /> Only used in `getXrpSignedTransaction()`                                           |
| destinationAddress | `String`       | Destination addresse of xrptransaction<br /> Only used in `getXrpSignedTransaction()`                                      |
| amountDrops        | `long`         | Amount to be send ( Drops unit value)<br />Only used in `getXrpSignedTransaction()`                                        |
| feeDrops           | `long`         | Fee of this transaction ( Drops unit value)<br />Only used in `getXrpSignedTransaction()`                                  |
| sequence           | `int`          | The sequence number, relative to the initiating account, of xrp transaction<br />Only used in `getXrpSignedTransaction`() |
| destinationTag     | `long`         | Destination tag of xrp transaction<br />Only used in `getXrpSignedTransaction()`                                           |
| unsignedTx         | `String`       | Encoded unsigned Trasaction value for xrp transaction<br />Only used in `getXrpSingedTransactionWithUnsignedTx()`          |
