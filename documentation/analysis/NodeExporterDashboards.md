# Node Exporter Full

## Formulas from "Quick CPU / Mem / Disk" section

#### 1. CPU Pressure

   ```promql
   irate(node_pressure_cpu_waiting_seconds_total{instance="$node", job="$job"}[$__rate_interval]) 
   ```

- Metric: `node_pressure_cpu_waiting_seconds_total`
- What it shows:
  How often processes have to wait for CPU access. The higher this number, the more tasks are waiting for the CPU, which
  is not good. This indicates the CPU is overloaded.

This metric measures CPU pressure by showing the percentage of time processes had to wait for CPU resources.
The irate() function calculates the instantaneous rate of change over the selected interval, useful for detecting sudden
spikes in CPU load.

#### 2. Memory Pressure

   ```promql
   irate(node_pressure_memory_waiting_seconds_total{instance="$node", job="$job"}[$__rate_interval])
   ```

- Metric: `node_pressure_memory_waiting_seconds_total`
- What it shows: Time that processes spend waiting for access to RAM. A high value indicates insufficient memory,
  slowing down the system because it's constantly trying to free space.

Similar to CPU pressure but for memory. It indicates the percentage of time processes are waiting for memory resources.
High values indicate significant memory shortages, critically affecting system performance.

#### 3. Disk I/O Pressure

   ```promql
   irate(node_pressure_io_waiting_seconds_total{instance="$node", job="$job"}[$__rate_interval])
   ```

- Metric: `node_pressure_io_waiting_seconds_total`
- What it shows: The amount of time processes wait for disk read/write operations. The higher this number, the longer
  processes wait for disk access, slowing down the entire system.

Measures the duration processes spend waiting for disk I/O operations.
High values suggest potential disk performance issues or excessive load on the disk subsystem.

#### 4. CPU Busy

   ```promql
   100 * (1 - avg(rate(node_cpu_seconds_total{mode="idle", instance="$node"}[$__rate_interval])))
   ```

- Metric: `node_cpu_seconds_total{mode="idle"}`
- What it shows: The percentage of time all CPU cores are busy (i.e., not idle). The higher the percentage, the heavier
  the CPU load.

Represents total CPU usage across all cores as a percentage.
Calculated as 100% minus the average idle time, effectively showing real CPU utilization by tasks.

#### 5. System Load (Overall CPU Load)

   ```promql
   scalar(node_load1{instance="$node", job="$job"}) * 100 / count(count(node_cpu_seconds_total{instance="$node", job="$job"}) by (cpu))
   ```

- Metric: `node_load1`
- What it shows: The average CPU load over the last minute relative to the total number of CPU cores. For example, 100%
  means all cores are fully busy. Above 100% means there are more tasks than cores, indicating CPU overload.

Expresses the average system load over the past minute as a percentage relative to the total number of CPU cores.
Values consistently above 100% indicate an overloaded system with tasks queuing for CPU time.

#### 6. Used RAM (RAM Used)

   ```promql
   (1 - (node_memory_MemAvailable_bytes{instance="$node", job="$job"} / node_memory_MemTotal_bytes{instance="$node", job="$job"})) * 100
   ```

- Metrics: `node_memory_MemAvailable_bytes` and `node_memory_MemTotal_bytes`
- What it shows:The percentage of RAM currently in use. A high value means less available memory, potentially slowing
  down programs and the operating system.

Shows the percentage of RAM currently in use.
Useful for detecting memory leaks or indicating the need for additional RAM resources.

#### 7. Used SWAP

   ```promql
   ((node_memory_SwapTotal_bytes{instance="$node", job="$job"} - node_memory_SwapFree_bytes{instance="$node", job="$job"}) / node_memory_SwapTotal_bytes{instance="$node", job="$job"}) * 100
   ```

- Metric: `node_memory_SwapTotal_bytes` and `node_memory_SwapFree_bytes`
- What it shows: Usage of swap space—a special disk area used temporarily when RAM is full. High swap usage indicates a
  RAM shortage and can greatly slow down system performance.

Percentage of used SWAP space.
High values indicate a shortage of RAM and excessive reliance on slower SWAP space, potentially affecting system
performance.

#### 8. Root Filesystem Usage (Root FS Used)

   ```promql
   100 - ((node_filesystem_avail_bytes{instance="$node", job="$job", mountpoint="/", fstype!="rootfs"} * 100) / node_filesystem_size_bytes{instance="$node", job="$job", mountpoint="/", fstype!="rootfs"})
   ```

- Metric: `node_filesystem_avail_bytes` and `node_filesystem_size_bytes`
- What it shows: The percentage of used space on the main disk (root filesystem). A high percentage means less space
  left for storing files, logs, and applications. Running out of space can cause system failures.

Percentage of used space on the root filesystem.
Critical to monitor, as a full filesystem can severely disrupt system stability and operations.

#### 9. Number of CPU Cores

   ```promql
   count(count(node_cpu_seconds_total{instance="$node", job="$job"}) by (cpu))
   ```

- Metric: `node_cpu_seconds_total`
- What it shows: The number of physical or virtual CPU cores available. Simply tells you how many cores your server has,
  indicating how many tasks it can handle simultaneously.

Returns the total number of CPU cores available on the server.
Useful for informational purposes and capacity planning for workload distribution.

#### 10. System Uptime

   ```promql
   node_time_seconds{instance="$node", job="$job"} - node_boot_time_seconds{instance="$node", job="$job"}
   ```

- Metric: `node_time_seconds` and `node_boot_time_seconds`
- What it shows: The amount of time the system has been running without restarts. This helps you see system stability or
  when the last reboot occurred.

Shows the total uptime of the server (in seconds) since the last boot.
Useful for assessing system stability and reliability over time.

#### 11. Total Root Filesystem Size (RootFS Total)

   ```promql
   node_filesystem_size_bytes{instance="$node", job="$job", mountpoint="/", fstype!="rootfs"}
   ```

- Metric: `node_filesystem_size_bytes`
- What it shows: The total size of your root filesystem in bytes, indicating the total available disk space.

Displays the total size of the root filesystem.
Useful for infrastructure monitoring and long-term storage planning.

#### 12. Total RAM (RAM Total)

   ```promql
   node_memory_MemTotal_bytes{instance="$node", job="$job"}
   ```

- Metric: `node_memory_MemTotal_bytes`
- What it shows: Total installed RAM on your system.

Shows the total available RAM on the server.
Critical for evaluating resource availability and planning for future capacity requirements.

#### 13. Total SWAP

   ```promql
   node_memory_SwapTotal_bytes{instance="$node", job="$job"}
   ```

- Metric: `node_memory_SwapTotal_bytes`
- What it shows: Total available swap space that your system can utilize when RAM is insufficient.

Indicates the total SWAP space available on the server.
Used to verify system configuration correctness and resource reservation strategies for peak usage scenarios.

## Formulas from "Basic CPU / Mem / Net / Disk" section

### 1. CPU Basic

This panel shows CPU usage by activity type:

#### 1.1 Busy System

   ```promql
   sum(irate(node_cpu_seconds_total{mode="system"}[$__rate_interval])) / scalar(count(node_cpu_seconds_total) by (cpu))
   ```

This shows the percentage of time all CPU cores spend executing system-level (kernel) operations.
Values consistently above 20% may indicate kernel bottlenecks, inefficient drivers, or high syscall usage.

#### 1.2 Busy User

   ```promql
   sum(irate(node_cpu_seconds_total{mode="user"}[$__rate_interval])) / scalar(count(node_cpu_seconds_total) by (cpu))
   ```

Indicates the proportion of time the CPU spends executing user-space processes (e.g., apps, scripts).
High user time is normal under load but should be investigated if unexpected.

#### 1.3 Busy Iowait

   ```promql
   sum(irate(node_cpu_seconds_total{mode="iowait"}[$__rate_interval])) / scalar(count(node_cpu_seconds_total) by (cpu))
   ```

CPU is idle, waiting for disk or network I/O to complete.
High values (>10–15%) may indicate slow disks or overloaded network interfaces.

#### 1.4 Busy IRQs

   ```promql
   sum(irate(node_cpu_seconds_total{mode=~".*irq"}[$__rate_interval])) / scalar(count(node_cpu_seconds_total) by (cpu))
   ```

CPU time spent handling hardware (irq) and software (softirq) interrupts.
Spikes in this metric can suggest hardware or driver issues.

#### 1.5 Busy Other

   ```promql
   sum(irate(node_cpu_seconds_total{mode!="idle",mode!="user",mode!="system",mode!="iowait",mode!="irq",mode!="softirq"}[$__rate_interval])) / scalar(count(node_cpu_seconds_total) by (cpu))
   ```
CPU time that doesn’t fall under the major categories above.
Often includes hypervisor overhead in virtualized environments.

#### 1.6  Idle

   ```promql
   sum(irate(node_cpu_seconds_total{mode="idle"}[$__rate_interval])) / scalar(count(node_cpu_seconds_total) by (cpu))
   ```

Time when the CPU is not doing any useful work.
High idle time (>70–80%) usually means the server is underutilized.


### 2. Memory Basic

#### 2.1. RAM Total

   ```promql
   node_memory_MemTotal_bytes
   ```
Total amount of installed physical memory (RAM) on the server in bytes.

#### 2.2 RAM Used

   ```promql
   node_memory_MemTotal_bytes - node_memory_MemFree_bytes - (node_memory_Cached_bytes + node_memory_Buffers_bytes + node_memory_SReclaimable_bytes)
   ```

Actual memory used by applications and system processes, excluding cache and buffers.
If this value nears 100%, you may be hitting RAM limits.

#### 2.3 RAM Cache + Buffer

   ```promql
   node_memory_Cached_bytes + node_memory_Buffers_bytes + node_memory_SReclaimable_bytes
   ```

Memory used for filesystem caching, disk buffers, and reclaimable kernel objects.
This is a good use of memory—it improves performance and is quickly reclaimable.

#### 2.4 RAM Free

   ```promql
   node_memory_MemFree_bytes
   ```
Completely unused memory. It’s okay for this to be low if enough is available in cache/buffers.

#### 2.5 SWAP Used

   ```promql
   node_memory_SwapTotal_bytes - node_memory_SwapFree_bytes
   ```
Amount of swap space in use.
Swap usage typically indicates RAM exhaustion and causes performance degradation.

### 3. Network Traffic Basic

#### 3.1 Received Traffic (Recv)

   ```promql
   irate(node_network_receive_bytes_total[$__rate_interval]) * 8
   ```

Rate at which the server receives network data, converted to bits per second.
Useful for identifying incoming traffic patterns and potential DDoS or backup spikes.

#### 3.2 Transmitted Traffic (Trans)

   ```promql
   irate(node_network_transmit_bytes_total[$__rate_interval]) * 8
   ```

Rate of outgoing network traffic in bits per second.
Helps detect bandwidth saturation or unexpected upload traffic.

### 4. Disk Space Used Basic

```promql
100 - (node_filesystem_avail_bytes * 100 / node_filesystem_size_bytes)
```

Percentage of used disk space per filesystem.
If this value exceeds 80–85%, it's a red flag and storage cleanup/expansion is required.ф5