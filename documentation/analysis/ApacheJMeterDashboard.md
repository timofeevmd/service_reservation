# Apache JMeter Dashboard Metrics (InfluxDB Backend Listener)

Complete list of metrics used in the Grafana dashboard "Apache JMeter Dashboard using Core InfluxdbBackendListenerClient".

---

## General Metrics

### `count`
- **Description**: Total number of requests.
- **Used in**: Total Requests, Throughput, Errors per Transaction, Error Info

### `countError`
- **Description**: Number of failed requests.
- **Used in**: Failed Requests, Total Errors, Num of Errors

### `error`
- **Description**: Number of errors (used in Error Rate % calculations).
- **Used in**: Error Rate %, Error Rate % - $transaction

---

## Network Traffic

### `rb` (Received Bytes)
- **Description**: Total number of bytes received.
- **Used in**: Received Bytes, Network Traffic

### `sb` (Sent Bytes)
- **Description**: Total number of bytes sent.
- **Used in**: Sent Bytes, Network Traffic

---

## Threads

### `maxAT`
- **Description**: Maximum number of active threads.
- **Used in**: Active Threads

---

## Timing Metrics

### `avg`
- **Description**: Average response time.
- **Used in**: Response Times - Average

### `pct50.0`
- **Description**: 50th percentile (median) of response time.
- **Used in**: Response Times - Median

### `pct90.0`
- **Description**: 90th percentile of response time.
- **Used in**: Response Times - 90th Percentile

### `pct95.0`
- **Description**: 95th percentile of response time.
- **Used in**: Transactions Response Times (95th pct), Response Times - 95th Percentile

### `pct99.0`
- **Description**: 99th percentile of response time.
- **Used in**: Response Times - 99th Percentile

### `max`
- **Description**: Maximum response time.
- **Used in**: Response Times - Max

---

## HTTP Errors

### `responseCode`, `responseMessage`
- **Description**: HTTP response code and message.
- **Used in**: Error Info

---

## Tags (Filters / Variables)

- `transaction` — transaction name (e.g., /api/auth/login)
- `application` — application name (e.g., sr)
- `statut` — request status (`ok`, `ko`, `all`)
