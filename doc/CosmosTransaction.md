# `CosmosTransaction`

#### Parameter

| Name        | Type             | Description                                                                                                   |
| ----------- | ---------------- | ------------------------------------------------------------------------------------------------------------- |
| hdKeyPath   | `Bip44KeyPath` | sign key path for cosmos transaction                                                                          |
| sigHash     | `String`       | transaction that has not been signed of cosmos transaction                                                  |
| decimals    | `int`          | transaction decimals of cosmos transaction                                                                    |
| symbol      | `String`       | transaction symbol of cosmos transaction                                                                      |
| fee         | `String`       | transaction fee of cosmos transaction<br />* Fee value is configured in cosmos(ATOM) or coreum(CORE) unit. |
| nonce       | `String`       | (optional)transaction nonce of cosmos transaction                                                            |
| optionParam | `String`       | (optional)option parameter                                                                                    |
