# `BitcoinTransaction`

**Parameter**

| Name        | Type                               | Description                                                                                                           |
| ----------- | ---------------------------------- | --------------------------------------------------------------------------------------------------------------------- |
| version     | `int`                            | version of bitcoin transaction. Currently 1<br /> - `BCH` / `Dash `/`BTG `-> `2 `<br />- `ZCASH `-> `4` |
| input       | `List<UnspentTransactionOutput>` | previous transaction output information to be used                                                                    |
| output      | `List<TransactionOutput>`        | coin spending information                                                                                             |
| locktime    | `int`                            | locktime for this transaction                                                                                         |
| optionParam | `String`                         | (optional)option parameter                                                                                            |

---

# **`UnspentTransactionOutput`**

The class for previous transaction output information to be used for bitcoin network transaction.

**Parameter**

| Name     | Type             | Description                                     |
| -------- | ---------------- | ----------------------------------------------- |
| prev_tx  | `String`       | full of previous transaction data               |
| utxo_idx | `int`          | index of previous transaction output to be sent |
| type     | `String`       | bitcoin transaction type for this UTXO          |
| key      | `Bip44KeyPath` | BIP44 key path for unlocking UTXO               |


# **`TransactionOutput`**

The class for coin spending information of Bitcoin network Transaction

**Parameter**

| Name  | Type             | Description                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                              |
| ----- | ---------------- | ---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- |
| value | `long`         | amount of coin to spend. Satoshi unit.                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                   |
| to    | `List<String>` | if `type` is `p2pkh` or `p2sh`, Base58Check encoded address of the receiver.<br />The value of the field may follow the rule of version prefix.(`BITCOIN_BASE58CHECK`)<br />if the type is `p2pk`, Base58Check encoded non-compressed public key without version prefix.<br />if the type is `multisig`, Base58Check encoded non-compressed public key (without version prefix) list.<br />if the type is `change`, BIP44 formatted PATH to get change address. In this case, the transaction type is assumed as `p2pkh` |
| type  | `String`       | bitcoin network transaction type or this field can indicate output as a `change`                                                                                                                                                                                                                                                                                                                                                                                                                                                      |
