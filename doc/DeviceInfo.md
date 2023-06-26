# DeviceInfo

#### Parameter

| Name       | Type                | Description                                                                                                                                                                                                             |
| ---------- | ------------------- | ----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- |
| label      | `String`          | Label of connected device                                                                                                                                                                                               |
| deviceId   | `String`          | Device Identifier of connected device                                                                                                                                                                                   |
| fwVersion  | `String`          | Firmware version of connected device                                                                                                                                                                                    |
| ksmVersion | `String`          | KSM version fo connected device                                                                                                                                                                                         |
| state      | `State`           | State of connected device<br />D'CENT biometric wallet's state values are as follows.<br />- `init`, `ready`, `secure`, `locked_fp`, `locked_pin`<br />**Only "`secure`" state dongle can be used.** |
| coinList   | `List<CoinType> ` | Supported coin list of connected device                                                                                                                                                                                 |
