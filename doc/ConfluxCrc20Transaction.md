# `ConfluxCrc20Transaction`

#### Parameter

| Name            | Type             | Description                                               |
| --------------- | ---------------- | --------------------------------------------------------- |
| nonce           | `String`       | account nonce for Conflux transaction                     |
| gasPrice        | `String`       | gas price for Conflux transaction                         |
| gasLimit        | `int`          | gas limit value of Conflux transaction                    |
| toAddr          | `String`       | address to send                                           |
| amount          | `String`       | amount of CFX to send of Conflux transaction              |
| storageLimit    | `String`       | storageLimit for Conflux transaction                      |
| epochHeight     | `String`       | epochHeight for Conflux transaction                       |
| chainId         | `String`       | chain id for Conflux transaction                          |
| data            | `String`       | (optional)transaction input data  for Conflux transaction |
| hdKeyPath       | `Bip44KeyPath` | sign key path for Conflux transaction                     |
| name            | `String`       | token name of Crc20 transaction                           |
| contractAddress | `String`       | contractAddress of Crc20 transaction                      |
| toAddress       | `String`       | toAddress of Crc20 transaction                            |
| decimals        | `String`       | decimals of of Crc20 transaction                          |
| value           | `String`       | amount of ether to be sent of Crc20. ( Drip unit value )  |
| symbol          | `String`       | symbol of Crc20 transaction                               |
