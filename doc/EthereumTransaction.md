# `EthereumTransaction`

#### Parameter

| Name             | Type             | Description                                         |
| ---------------- | ---------------- | --------------------------------------------------- |
| hdKeyPath        | `Bip44KeyPath` | sign key path for ethereum transaction             |
| nonce            | `String`       | account nonce for ethereum transaction              |
| toAddr           | `String`       | recipient's address of ethereum transaction        |
| amount           | `String`       | amount of ether to be sent. ( WEI unit value )      |
| gasLimit         | `String`       | gas limit value of ethereum transaction           |
| gasPrice         | `String`       | gas price for ethereum transaction                 |
| data             | `String`       | transaction data of ethereum transaction            |
| chainId          | `int`          | chain id                                            |
| symbol           | `String`       | symbol of contract for ethereum transaction        |
| tx_type          | `byte`         | (optional)Type of ethereum transaction              |
| max_priority_fpg | `String`       | (optional)max_priority_fpg of ethereum transaction |
| max_fee_per_gas  | `String`       | (optional)max_fee_per gas of ethereum transaction   |
| access_list      | `String`       | (optional)access_list of ethereum transaction       |
