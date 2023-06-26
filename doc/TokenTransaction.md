# `TokenTransaction`

#### Parameter

| Name            | Type             | Description                                  |
| --------------- | ---------------- | -------------------------------------------- |
| hdKeyPath       | `Bip44KeyPath` | sign key path for ethereum transaction       |
| nonce           | `String`       | account nonce for ethereum transaction       |
| toAddr          | `String`       | address to send                              |
| amount          | `String`       | amount of ETH to send                        |
| gasLimit        | `String`       | gas limit value                              |
| gasPrice        | `String`       | gas price for ethereum transaction           |
| tokenName       | `String`       | token name of contract for ERC20 transaction |
| contractAddress | `String`       | address of contract for ERC20 transaction    |
| decimals        | `String`       | decimals of contract for ERC20 transaction   |
| symbol          | `String`       | symbol of contract for ERC20 transaction     |
| chainId         | `String`       | chain id                                     |
| feeSymbol       | `String`       | symbol of fee Account for ERC20 transaction  |
