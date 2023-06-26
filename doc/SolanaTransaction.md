# `SolanaTransaction`

#### Parameter

| Name        | Type             | Description                                                                                                                                                                                                                           |
| ----------- | ---------------- | ------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- |
| hdKeyPath   | `Bip44KeyPath` | sign key path for solana transaction                                                                                                                                                                                                 |
| sigHash     | `String`       | transaction that has not been signed of solana transaction                                                                                                                                                                          |
| fee         | `String`       | transaction fee of solana transaction<br />* Fee value is configured in solana(SOL) unit.                                                                                                                                            |
| decimals    | `int`          | transaction decimals of solana transaction                                                                                                                                                                                            |
| symbol      | `String`       | transaction symbol of solana transaction                                                                                                                                                                                              |
| optionParam | `String`       | (optional)hexadecimal value of the token method type is used only in spl token.<br />- '01': spl-token transaction<br />- '02': create assosiate<br />- '03' + amount(8bytes) : for dapp (amount value is configured in lamport unit) |
