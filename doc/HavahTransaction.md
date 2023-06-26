# `HavahTransaction`

#### Parameter

| Name        | Type             | Description                                                                                                                                                                                                                                           |
| ----------- | ---------------- | ----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- |
| hdKeyPath   | `Bip44KeyPath` | sign key path for havah transaction                                                                                                                                                                                                                   |
| sigHash     | `String`       | transaction that has not been signed of havah transaction                                                                                                                                                                                            |
| decimals    | `int`          | transaction decimals of havah transaction                                                                                                                                                                                                            |
| symbol      | `String`       | transaction symbol of havah transaction                                                                                                                                                                                                               |
| fee         | `String`       | transaction fee of havah transaction<br />* Fee value is configured in havah(HVH) unit.                                                                                                                                                             |
| nonce       | `String`       | (optional)transaction nonce of havah transaction                                                                                                                                                                                                     |
| optionParam | `String`       | (optional)hexadecimal value of the havah method type is used only in havah token<br />- '01' : Token Transfer<br />- '02' : Token Deposit<br />- '03' : Token Message<br />- ~'04': Token Deploy~ (**The method will be supported later)** |
