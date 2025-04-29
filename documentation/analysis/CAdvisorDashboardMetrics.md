
# Cadvisor Exporter Dashboard - Metric Breakdown and Analysis

[Official Docs cAdvisor_exporter](https://github.com/google/cadvisor/blob/master/docs/storage/prometheus.md#container_cpu_usage_seconds_total)

[Oficial Docs promQl](https://prometheus.io/docs/prometheus/latest/migration/#promql)

This document provides a comprehensive explanation of the metrics used in the **Cadvisor Exporter** Grafana dashboard.

---

## 1. CPU Usage

### PromQL Expression
```promql
sum(rate(container_cpu_usage_seconds_total{instance=~"$host",name=~"$container",name=~".+"}[5m])) by (name) * 100
```

### Metric: `container_cpu_usage_seconds_total`
- **Definition**: Total cumulative CPU time consumed by the container in seconds.
- **Unit**: Seconds.
- **Explanation**: This metric shows how much CPU time your container has used.
- **Why use `rate()`**: `rate()` calculates how quickly the counter increases, giving us CPU **usage per second** over the last 5 minutes.
- **Why use `sum(...) by (name)`**: To aggregate all CPU cores usage per container.
- **Why `* 100`**: Converts the fraction to a percentage.
---

## 2. Memory Usage

### PromQL Expression
```promql
sum(container_memory_rss{instance=~"$host",name=~"$container",name=~".+"}) by (name)
```

### Metric: `container_memory_rss`
- **Definition**: Resident Set Size – the portion of memory occupied in RAM.
- **Unit**: Bytes.
- **Explanation**: This metric tells you how much memory is **actively used** (not cached or swapped).
- **Why use `sum(...) by (name)`**: To show memory usage per container.

---

## 3. Memory Cached

### PromQL Expression
```promql
sum(container_memory_cache{instance=~"$host",name=~"$container",name=~".+"}) by (name)
```

### Metric: `container_memory_cache`
- **Definition**: Amount of memory used for caching (filesystem buffers).
- **Unit**: Bytes.
- **Explanation**: Helps understand memory that can be reclaimed without impacting running applications.

---

## 4. Network - Received Bytes

### PromQL Expression
```promql
sum(rate(container_network_receive_bytes_total{instance=~"$host",name=~"$container",name=~".+"}[5m])) by (name)
```

### Metric: `container_network_receive_bytes_total`
- **Definition**: Total bytes received over the network by a container.
- **Unit**: Bytes per second.
- **Why use `rate()`**: Converts total counter into a per-second rate.
- **Why aggregate with `sum(...) by (name)`**: To group traffic by container name.

---

## 5. Network - Sent Bytes

### PromQL Expression
```promql
sum(rate(container_network_transmit_bytes_total{instance=~"$host",name=~"$container",name=~".+"}[5m])) by (name)
```

### Metric: `container_network_transmit_bytes_total`
- **Definition**: Total bytes sent over the network.
- **Unit**: Bytes per second.

---

## 6. Container Uptime (Running Days)

### PromQL Expression
```promql
(time() - container_start_time_seconds{instance=~"$host",name=~"$container",name=~".+"}) / 86400
```

### Metric: `container_start_time_seconds`
- **Definition**: Start time of the container in Unix seconds.
- **Formula Explanation**:
  - `time()` — Current timestamp in seconds.
  - `container_start_time_seconds` — Timestamp when container started.
  - Subtracting gives total runtime in seconds.
  - Divide by `86400` to convert seconds into days.

---

## PromQL Function Reference


- **`rate(metric[5m])`**: Calculates per-second average increase over 5 minutes (for counters).
- **`sum(...) by (label)`**: Aggregates values grouped by label.
- **`time()`**: Returns current time (Unix timestamp).
- **`/ 86400`**: Converts seconds to days (60×60×24).
