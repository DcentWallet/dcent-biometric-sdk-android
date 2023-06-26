# `VechainTransaction`

#### Parameter

| Name        | Type             | Description                                                                                  |
| ----------- | ---------------- | -------------------------------------------------------------------------------------------- |
| hdKeyPath   | `Bip44KeyPath` | sign key path for vechain transaction                                                        |
| sigHash     | `String`       | transaction that has not been signed of vechain transaction                                 |
| decimals    | `int`          | transaction decimals of vechain transaction                                                  |
| symbol      | `String`       | transaction symbol of vechain transaction                                                    |
| fee         | `String`       | transaction fee of vechain transaction<br />* Fee value is configured in vechain(VET) unit. |
| nonce       | `String`       | (optional)transaction nonce of vechain transaction                                           |
| optionParam | `String`       | (optional)option parameter                                                                   |
