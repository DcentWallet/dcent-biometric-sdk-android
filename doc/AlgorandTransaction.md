# `AlgorandTransaction`

#### Parameter

| Name        | Type           | Description                                                                                  |
| ----------- | -------------- | -------------------------------------------------------------------------------------------- |
| hdKeyPath   | `Bip44KeyPath` | sign key path for algorand transaction                                                       |
| sigHash     | `String`       | transaction that has not been signed of algorand transaction                                 |
| decimals    | `int`          | transaction decimals of algorand transaction                                                 |
| symbol      | `String`       | transaction symbol of algorand transaction                                                   |
| fee         | `String`       | transaction fee of algorand transaction                                                      |
| nonce       | `String`       | Not used                                                                                     |
| optionParam | `String`       | (optional)option parameter:<br/>                                                             |
|             |                | ['00': ALGO transfer, '01': ASSET transfer, '02': ASSET Opt-in, '03': APP ContranctCall,<br />|
|             |                | '04': APP Opt-in, '05': ASSET FT Creation, '06': ASSET NFT Creation                          |
