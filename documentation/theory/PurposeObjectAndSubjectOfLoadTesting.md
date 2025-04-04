# Purpose, Object, and Subject of Load Testing

## Overview
Load testing is the process of evaluating the performance of an application or system under different load conditions. It helps assess how efficiently the software operates under real or extreme scenarios, identifying bottlenecks and limitations.

The goal of load testing is to ensure that the system can:
- Handle the expected request volumes.
- Maintain stability and correctness.
- Meet SLA (Service Level Agreement) and non-functional requirements.

## Section Goals
- Define the purpose and business value of load testing.
- Identify objects of testing (system components under test).
- Understand who the testing subjects are (test participants and end users).

## Theory: Key Concepts

### Load Testing Purpose
Main tasks of load testing include:
- **Performance validation:** determine how many requests per second (RPS) or transactions the system can handle.
- **Scalability validation:** assess how the system responds to load increase and resource scaling.
- **Bottleneck identification:** detect components limiting performance (databases, application servers, APIs).
- **Production readiness:** model expected and peak loads.
- **Resilience validation:** test under extreme load conditions to find breaking points.

### Load Testing Object
An object is any part of the system under load. Typical objects:
- REST API: the core of microservice architecture.
- Microservices: individual services running in the system.
- Databases: database query handling under high load.
- Infrastructure: servers, load balancers, networks.
- Message queues: event processing systems (Kafka, RabbitMQ).

**Key metrics for objects:**
- **Latency:** time to process a request.
- **Throughput:** number of requests processed per time unit.
- **Error rate:** percentage of failed requests.
- **Resource utilization:** CPU, memory, network, disk usage.

### Load Testing Subject
Subjects are participants and consumers of test results:
- **End users:** API consumers.
- **Developers:** optimize code based on test results.
- **QA engineers:** organize and analyze tests.
- **DevOps engineers:** manage infrastructure, monitoring, and tuning.
- **Product owners/managers:** need system performance insights.

## Detailed Overview of Key Aspects

### Types of Load Testing
- **Standard load testing:** check system under normal/expected load.
- **Max performance testing:** find maximum throughput and resource usage limits.
- **Stability testing:** verify performance over prolonged periods.
- **Stress testing:** assess system resilience beyond expected load.
- **Volume testing:** evaluate system with large data volumes (e.g., bulk database inserts).
- **Regression load testing:** ensure new changes do not degrade performance.

### Load Testing Metrics
- **Latency:**
    - Average response time.
    - Time to First Byte (TTFB).
    - Time to Last Byte (TTLB).
    - 90th/95th/99th percentile response time.
- **Throughput:**
    - Requests per second (RPS) or/and transactions per second (TPS).
    - Data volume transferred per second.
- **Error rate:**
    - Percentage of 4xx/5xx HTTP codes.
    - Request timeouts.
    - Database connection errors.
- **Resource utilization:**
    - CPU usage (%).
    - Memory usage.
    - Disk I/O.
    - Network bandwidth.
- **Connection pool usage:**
    - Pool utilization.
    - Connection failures due to exhausted pool.
- **SLA compliance:**
    - Percentage of requests meeting response time targets.
    - Average system downtime.
- **Apdex score:** user satisfaction score.
- **Peak load:** max simultaneous requests and throughput.
- **Success rate:** percentage of successful (non-error) requests.
- **Latency distribution:** latency histogram.
- **Time to Interactive:** full page/API load time.
- **Retries:** number of retried requests.
- **Queue time:** average wait time before request processing.
- **Load efficiency:** ratio of generated to processed load.
- **Dropped requests:** unhandled request count.

### Why Load Testing is Critical for REST API
REST APIs often handle high concurrent requests and are the backbone of microservices. Without load testing:
- Bottlenecks lead to delays.
- Resource overuse causes system crashes.
- Unpredictable behavior with user growth.

## Technology Stack
- **JMeter:** load scenario creation.
- **Prometheus/Grafana:** metrics monitoring.
- **InfluxDB:** load data storage.
- **Docker:** test environment orchestration.

## Example REST API Testing Scenario
1. **Preparation:**
    - Define SLAs (e.g., max response time of 200ms).
    - Choose target API endpoints.
2. **Configuration:**
    - Create test plans in JMeter or K6.
    - Define load levels (normal, peak, extreme).
3. **Execution:**
    - Run tests with real-time monitoring.
4. **Analysis:**
    - Collect metrics (latency, error rate, resource usage).
5. **Recommendations:**
    - Optimize bottlenecks.
