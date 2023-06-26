# `TezosTransaction`

#### Parameter

| Name        | Type             | Description                                                                              |
| ----------- | ---------------- | ---------------------------------------------------------------------------------------- |
| hdKeyPath   | `Bip44KeyPath` | sign key path for tezos transaction                                                      |
| sigHash     | `String`       | transaction that has not been signed of tezos transaction                               |
| decimals    | `int`          | transaction decimals of tezos transaction                                                |
| symbol      | `String`       | transaction symbol of tezos transaction                                                  |
| fee         | `String`       | transaction fee of tezos transaction<br />* Fee value is configured in tezos(XTZ) unit. |
| nonce       | `String`       | (optional)transaction nonce of tezos transaction                                        |
| optionParam | `String`       | (optional)option parameter                                                               |
