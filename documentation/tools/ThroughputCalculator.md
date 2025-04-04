
# Load Testing Parameter Calculation Explanation

| Column                          | Description                                                 | Formula                                          | Example          |
|----------------------------------|-------------------------------------------------------------|--------------------------------------------------|------------------|
| **sec/hr**                     | Value sec per hr (CONST)                                    | 3600                                             | `3600`           |
| **ms/hr**                      | Value ms per hr (CONST)                                     | 3600000                                          | `3600000`        |
|  **%step**                      | The amount of load generated at each test step              | Manual input                                     | `20%`           |
| **Operation**                   | Name of the operation or scenario                           | Manual input                                     | `CreateWI`       |
| **Ops/hr**                      | Expected operations per hour                                | Manual input                                     | `50000`          |
| **Response time (sec)**         | Response time per operation                                 | Manual input                                     | `5.6`            |
| **Padded response time (sec)**  | Response time with buffer for delays                        | `Response time × 2`                              | `11.2`           |
| **Max ops/hr per 1 VU**         | Max hourly operations one virtual user can perform          | `3600 / Padded response time`                    | `321.43`         |
| **VU per step**                 | Number of virtual users at one load step (e.g., 20%)        | `(ROUND)((Ops/hr * %step) / Max ops/hr per 1 VU` | `32`             |
| **VUs at 100% load**            | Virtual users required at full load                         | `VU per step × (100 / step%)`                    | `160`            |
| **Ops/hr per VU at 20%**        | Operations per hour per virtual user at 20% load            | `(Ops/hr / VUs at 100%) × 0.2`                   | `62.5`           |
| **Ops/hr per VU at 100%**       | Operations per hour per virtual user at 100% load           | `Ops/hr / VUs at 100%`                           | `312.5`          |
| **Ops/min per 1 VU**            | Operations per minute per virtual user                      | `Ops/hr per VU at 100% / 60`                     | `5.21`           |
| **Validation @100%**           | Final check: operations match input when multiplied back    | `Ops/hr per VU at 100% × VUs at 100% = Ops/hr`   | `50000`          |
