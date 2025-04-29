# Postgres Exporter Dashboard - Metric Breakdown and Analysis

[Official Docs cAdvisor_exporter](https://grafana.com/oss/prometheus/exporters/postgres-exporter/)

[Oficial Docs promQl](https://prometheus.io/docs/prometheus/latest/migration/#promql)

This document provides a comprehensive explanation of the metrics used in the **Postgres Exporter** Grafana dashboard.


## 1. Version
- **PromQL Formula**:   Description: Selects static PostgreSQL version data filtered by release and instance.

 ```promql
  pg_static{release="$release", instance="$instance"}
  ```

- **Metrics Used**:
  `pg_static` - Displays the PostgreSQL version and basic static information about the database server.

## 2. Start Time
- **PromQL Formula**:   Description: Fetches the server start time in seconds and converts it to milliseconds.

  ```promql
  pg_postmaster_start_time_seconds{release="$release", instance="$instance"} * 1000
  ```

- **Metrics Used**:
  `pg_postmaster_start_time_seconds` - The start time of the PostgreSQL process in seconds since the Unix epoch. Helps determine when the server was last restarted.

## 3. Current fetch data
- **PromQL Formula**:  Description: Calculates the total number of rows fetched across databases.
  ```promql
  SUM(pg_stat_database_tup_fetched{datname=~"$datname", instance=~"$instance"})
  ```

- **Metrics Used**:
  `pg_stat_database_tup_fetched` - The number of rows fetched from the database (typically via SELECT queries).

## 4. Current insert data
- **PromQL Formula**:   Description: Calculates the total number of rows inserted across databases.

  ```promql
  SUM(pg_stat_database_tup_inserted{release="$release", datname=~"$datname", instance=~"$instance"})
  ```

- **Metrics Used**:
  `pg_stat_database_tup_inserted` -  Total number of rows inserted into the database.

## 5. Current update data
- **PromQL Formula**:   Description: Calculates the total number of rows updated across databases.

  ```promql
  SUM(pg_stat_database_tup_updated{datname=~"$datname", instance=~"$instance"})
  ```

- **Metrics Used**:
  `pg_stat_database_tup_updated` - The number of rows that have been updated (via UPDATE queries).

## 6. Max Connections
- **PromQL Formula**:   Description: Retrieves the maximum allowed number of database connections.

  ```promql
  pg_settings_max_connections{release="$release", instance="$instance"}
  ```

- **Metrics Used**:
  `pg_settings_max_connections` - Maximum number of concurrent connections allowed to PostgreSQL, as defined in configuration.

## 7. Average CPU Usage
- **PromQL Formula**:   Description: Calculates the average CPU usage rate, converting it into milliseconds.

  ```promql
  avg(rate(process_cpu_seconds_total{release="$release", instance="$instance"}[5m]) * 1000)
  ```

- **Metrics Used**:
  `process_cpu_seconds_total` -  Average CPU time consumed by PostgreSQL, in milliseconds, over a given interval.

## 8. Average Memory Usage
- **PromQL Formula**:   Description: Measures the average physical memory consumption rate.

  ```promql
  avg(rate(process_resident_memory_bytes{release="$release", instance="$instance"}[5m]))
  ```
- **PromQL Formula**:   Description: Measures the average virtual memory consumption rate.

  ```promql
  avg(rate(process_virtual_memory_bytes{release="$release", instance="$instance"}[5m]))
  ```

- **Metrics Used**:
  `process_virtual_memory_bytes` - The total size of virtual memory used by the PostgreSQL process.
  `process_resident_memory_bytes` - The size of physical RAM actually used by the PostgreSQL process.

## 9. Open File Descriptors
- **PromQL Formula**:   Description: Fetches the number of currently opened files and connections.

  ```promql
  process_open_fds{release="$release", instance="$instance"}
  ```

- **Metrics Used**:
  `process_open_fds` - The number of file descriptors (open files and network connections) opened by the PostgreSQL process.

## 10. Shared Buffers
- **PromQL Formula**:   Description: Retrieves the size of memory allocated for shared buffers.

  ```promql
  pg_settings_shared_buffers_bytes{instance="$instance"}
  ```

- **Metrics Used**:
  `pg_settings_shared_buffers_bytes` - The amount of memory allocated to shared buffers — the primary memory area used for caching database pages.

## 11. Effective Cache
- **PromQL Formula**:  Description: Shows how much memory is available for caching disk data.

  ```promql
  pg_settings_effective_cache_size_bytes{instance="$instance"}
  ```

- **Metrics Used**:
  `pg_settings_effective_cache_size_bytes` - The estimated amount of memory available for disk caching by the operating system and PostgreSQL.

## 12. Maintenance Work Mem
- **PromQL Formula**: Description: Displays how much memory is allocated for maintenance operations.
  ```promql
  pg_settings_maintenance_work_mem_bytes{instance="$instance"}
  ```

- **Metrics Used**:
  `pg_settings_maintenance_work_mem_bytes` - Amount of memory allocated for maintenance operations like VACUUM, CREATE

## 13. Work Mem
- **PromQL Formula**: Description: Displays the amount of memory available for internal operations like sorting and hashing.
  ```promql
  pg_settings_work_mem_bytes{instance="$instance"}
  ```

- **Metrics Used**:
  `pg_settings_work_mem_bytes` -  Memory allocated for internal sort operations and hash tables before writing to temporary disk files.

## 14. Max WAL Size
- **PromQL Formula**: Description: Shows the maximum size the WAL (Write-Ahead Log) can grow before a checkpoint occurs.
  ```promql
  pg_settings_max_wal_size_bytes{instance="$instance"}
  ```

- **Metrics Used**:
  `pg_settings_max_wal_size_bytes` - Maximum size to which the Write-Ahead Logging (WAL) files can grow before triggering a checkpoint.

## 15. Random Page Cost
- **PromQL Formula**: Description: Displays the cost for reading a random page from disk.
  ```promql
  pg_settings_random_page_cost{instance="$instance"}
  ```

- **Metrics Used**:
  `pg_settings_random_page_cost` - The cost estimation for fetching a random page from disk — used by the query planner.

## 16. Seq Page Cost
- **PromQL Formula**: Description: Displays the cost for reading sequential pages from disk.
  ```promql
  pg_settings_seq_page_cost{instance="$instance"}
  ```

- **Metrics Used**:
  `pg_settings_seq_page_cost` - The cost estimation for sequential page reads — helps in query planning decisions.

## 17. Max Worker Processes
- **PromQL Formula**: Description: Shows the maximum number of worker processes PostgreSQL can use.
  ```promql
  pg_settings_max_worker_processes{instance="$instance"}
  ```

- **Metrics Used**:
  `pg_settings_max_worker_processes` - Maximum number of background processes that PostgreSQL can run simultaneously.

## 18. Max Parallel Workers
- **PromQL Formula**: Description: Shows the maximum number of parallel workers available for queries.
  ```promql
  pg_settings_max_parallel_workers{instance="$instance"}
  ```

- **Metrics Used**:
  `pg_settings_max_parallel_workers` - Maximum number of parallel workers that can be used by parallel queries.

## 19. Active sessions
- **PromQL Formula**: Description: Counts the number of active sessions currently running.
  ```promql
  pg_stat_activity_count{datname=~"$datname", instance=~"$instance", state="active"} !=0
  ```

- **Metrics Used**:
  `pg_stat_activity_count` - Number of currently active database sessions (connections that are executing a query).

## 20. Transactions
- **PromQL Formula**: Description: Measures the speed of successful transaction commits.
  ```promql
  irate(pg_stat_database_xact_commit{instance="$instance", datname=~"$datname"}[5m])
  ```
- **PromQL Formula**: Description: Measures the speed of transaction rollbacks.
  ```promql
  irate(pg_stat_database_xact_rollback{instance="$instance", datname=~"$datname"}[5m])
  ```

- **Metrics Used**:
  `pg_stat_database_xact_commit` - Number of successful transaction commits.
  `pg_stat_database_xact_rollback` - Number of transaction rollbacks (failures or cancellations).

## 21. Update data
- **PromQL Formula**: Description: Shows how many rows were updated.
  ```promql
  pg_stat_database_tup_updated{datname=~"$datname", instance=~"$instance"} != 0
  ```

- **Metrics Used**:
  `pg_stat_database_tup_updated` - Number of rows updated in the database.

## 22. Fetch data (SELECT)
- **PromQL Formula**: Description: Checks if rows have been fetched (retrieved) from tables.
  ```promql
  pg_stat_database_tup_fetched{datname=~"$datname", instance=~"$instance"} != 0
  ```

- **Metrics Used**:
  `pg_stat_database_tup_fetched` - Number of rows fetched (retrieved) from tables using SELECT queries.

## 23. Insert data
- **PromQL Formula**: Description: Checks if rows have been inserted into tables.
  ```promql
  pg_stat_database_tup_inserted{datname=~"$datname", instance=~"$instance"} != 0
  ```

- **Metrics Used**:
  `pg_stat_database_tup_inserted` - Number of rows inserted into the tables.

## 24. Lock tables
- **PromQL Formula**: Description: Checks if any locks are currently held on tables.
  ```promql
  pg_locks_count{datname=~"$datname", instance=~"$instance", mode=~"$mode"} != 0
  ```

- **Metrics Used**:
  `pg_locks_count` - Number of locks currently held on database tables. Locks ensure data consistency and prevent conflicts.

## 25. Return data
- **PromQL Formula**: Description: Checks if rows were returned by queries.
  ```promql
  pg_stat_database_tup_returned{datname=~"$datname", instance=~"$instance"} != 0
  ```

- **Metrics Used**:
  `pg_stat_database_tup_returned` - Number of rows returned by queries (typically after SELECT operations).

## 26. Idle sessions
- **PromQL Formula**: Description: Counts the number of idle database sessions.
  ```promql
  pg_stat_activity_count{datname=~"$datname", instance=~"$instance", state=~"idle|idle in transaction|idle in transaction (aborted)"}
  ```

- **Metrics Used**:
  `pg_stat_activity_count` - Number of database connections that are currently idle (not executing a query but still connected).

## 27. Delete data
- **PromQL Formula**: Description: Checks if rows were deleted from tables.
  ```promql
  pg_stat_database_tup_deleted{datname=~"$datname", instance=~"$instance"} != 0
  ```

- **Metrics Used**:
  `pg_stat_database_tup_deleted` -  Number of rows deleted from tables.

## 28. Cache Hit Rate
- **PromQL Formula**: Description: Calculates the cache hit rate: how often data is read from memory instead of disk.
  ```promql
  pg_stat_database_blks_hit{instance="$instance", datname=~"$datname"} / (pg_stat_database_blks_read{instance="$instance", datname=~"$datname"} + pg_stat_database_blks_hit{instance="$instance", datname=~"$datname"})
  ```

- **Metrics Used**:
  `pg_stat_database_blks_read` - Number of times a block had to be read from disk.
  `pg_stat_database_blks_hit` - Number of times a requested table block was found already in PostgreSQL's memory.

High cache hit rate means better performance because fewer reads are needed from slower storage.

## 29. Buffers (bgwriter)
- **PromQL Formula**: Description: Shows how fast backend processes write buffers.
  ```promql
  irate(pg_stat_bgwriter_buffers_backend_total{instance="$instance"}[5m])
  ```
- **PromQL Formula**:  Description: Shows how fast new buffers are allocated.
  ```promql
  irate(pg_stat_bgwriter_buffers_alloc_total{instance="$instance"}[5m])
  ```
- **PromQL Formula**: Description: Shows how fast backend processes are forced to write and sync buffers.
  ```promql
  irate(pg_stat_bgwriter_buffers_backend_fsync_total{instance="$instance"}[5m])
  ```
- **PromQL Formula**: Description: Shows how fast buffers are written at checkpoints.
  ```promql
  irate(pg_stat_bgwriter_buffers_checkpoint_total{instance="$instance"}[5m])
  ```
- **PromQL Formula**: Description: Shows how fast buffers are cleaned by background processes.
  ```promql
  irate(pg_stat_bgwriter_buffers_clean_total{instance="$instance"}[5m])
  ```

- **Metrics Used**: `pg_stat_bgwriter_buffers_checkpoint_total`, `pg_stat_bgwriter_buffers_alloc_total`, `pg_stat_bgwriter_buffers_backend_total`, `pg_stat_bgwriter_buffers_clean_total`, `pg_stat_bgwriter_buffers_backend_fsync_total`
  Various metrics showing how PostgreSQL background writer manages shared memory buffers: how many buffers are written at checkpoints, cleaned, allocated, or written directly by backend processes.

## 30. Conflicts/Deadlocks
- **PromQL Formula**: Description: Measures how often query conflicts happen.
  ```promql
  irate(pg_stat_database_conflicts{instance="$instance", datname=~"$datname"}[5m])
  ```
- **PromQL Formula**: Description: Measures how often deadlocks are detected.
  ```promql
  irate(pg_stat_database_deadlocks{instance="$instance", datname=~"$datname"}[5m])
  ```

- **Metrics Used**:
  `pg_stat_database_conflicts` - Counts the number of query cancellations due to recovery conflicts.
  `pg_stat_database_deadlocks` - Number of deadlocks detected where transactions block each other and must be aborted.

## 31. Temp File (Bytes)
- **PromQL Formula**:  Description: Measures the speed of temporary file creation during queries.
  ```promql
  irate(pg_stat_database_temp_bytes{instance="$instance", datname=~"$datname"}[5m])
  ```

- **Metrics Used**:
  `pg_stat_database_temp_bytes` - Amount of temporary disk space used by queries for operations like sorting large datasets.

## 32. Checkpoint Stats
- **PromQL Formula**: Description: Measures time spent writing data to disk during checkpoints.
  ```promql
  irate(pg_stat_bgwriter_checkpoint_write_time_total{instance="$instance"}[5m])
  ```
- **PromQL Formula**: Description: Measures time spent syncing files to disk during checkpoints.
  ```promql
  irate(pg_stat_bgwriter_checkpoint_sync_time_total{instance="$instance"}[5m])
  ```

- **Metrics Used**:
  `pg_stat_bgwriter_checkpoint_write_time_total` - Amount of temporary disk space used by queries for operations like sorting large datasets.
  `pg_stat_bgwriter_checkpoint_sync_time_total` - Total time spent syncing files to disk during checkpoints.