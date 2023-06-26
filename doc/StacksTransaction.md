# `StacksTransaction`

#### Parameter

| Name        | Type             | Description                                                                                                                                                                                                                         |
| :---------- | ---------------- | ----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- |
| hdKeyPath   | `Bip44KeyPath` | sign key path for stacks transaction                                                                                                                                                                                              |
| sigHash     | `String`       | transaction that has not been signed of stacks transaction                                                                                                                                                                        |
| authType    | `int`          | transaction authorization type of stacks transaction (refer to[Stacks Doc](https://docs.stacks.co/docs/stacks-academy/technical-specs#transactions))<br />- standard authorization: 0x04<br />- sponsored authorization: 0x05          |
| fee         | `String`       | transaction fee of stacks transaction<br />* Fee value is configured in stacks(STX) unit.                                                                                                                                           |
| nonce       | `String`       | transaction nonce of stacks transaction                                                                                                                                                                                             |
| decimals    | `int`          | transaction decimals of stacks transaction                                                                                                                                                                                          |
| symbol      | `String`       | transaction symbol of stacks transaction                                                                                                                                                                                            |
| optionParam | `String`       | (optional)hexadecimal value of the token method type<br />- '01' : assert transfer<br />- '02' : token stake<br />- '03' + amount(8bytes) : token revoke (amount value is configured in micro-STX unit)<br />- '04': dapp contract |
