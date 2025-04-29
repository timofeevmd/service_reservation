# Load Testing Theory

## Key Load Types

Within load testing theory, several key load types are distinguished:

### 1. Peak Load Testing
**Description:**  
Tests are conducted to identify the maximum number of requests or users the system can handle without violating SLAs. The load gradually increases until the system reaches its limit.

**Goals:**
- Identify the system's performance ceiling (Throughput).
- Validate compliance with non-functional requirements (e.g., RPS > 1000).

**Use Cases:**
- Public REST APIs.
- Infrastructure components such as load balancers or databases.

**Example:**  
An online store is tested to handle up to 10,000 concurrent users. The scenario ramps from 1,000 to 12,000 users to find when response time exceeds 1 second.

### 2. Steady Load Testing
**Description:**  
Validates how the system performs under a constant, pre-defined load over an extended period.

**Goals:**
- Detect resource leaks (e.g., memory).
- Ensure system stability during real operations.

**Use Cases:**
- Backend services.
- Microservices with stable traffic patterns.

**Example:**  
A REST API processing 1,000 requests per second is tested over 8 hours. Metrics like CPU, memory, and response time are monitored.

### 3. Stress Load Testing
**Description:**  
Simulates extreme conditions to find system failure points by exceeding normal limits.

**Goals:**
- Assess system resilience to abnormal spikes.
- Observe system recovery after failure.

**Use Cases:**
- Financial systems (e.g., Black Friday sales).
- Services with highly concurrent user loads.

**Example:**  
A payment system is tested at 10 times the normal load, emulating a peak of 5,000 RPS.

### 4. Volume Load Testing
**Description:**  
Checks system performance with large data volumes, such as large databases or file storages.

**Goals:**
- Measure read/write speed and stability.
- Analyze how data size impacts performance.

**Use Cases:**
- Databases.
- File storage systems.

**Example:**  
System is tested with over 1TB of data to measure search and update performance.

---

## General Load Testing Goals
- Validate compliance with non-functional requirements.
- Define system performance and resilience limits.
- Identify bottlenecks.
- Prepare the system for real-world operation.

---

## Load Models: Closed vs Open

### Closed Load Model
A fixed number of virtual users interact with the system. Each user completes actions and waits before starting the next.

**Characteristics:**
- Fixed user count (N).
- Inter-arrival time depends on system response time.

**Key Metrics:**
- Response Time.
- Throughput.
- Concurrency.

**Use Cases:**
- Enterprise applications with a limited number of sessions.

**Example:**  
An internal CRM with 500 concurrent users. Tests run with 50 to 200 users in increments of 50.

### Open Load Model
Simulates a constant flow of new user requests, independent of previous ones.

**Characteristics:**
- Defined request rate (RPS).
- Independent users.
- Continuous system load.

**Key Metrics:**
- Request Rate.
- Response Time.
- Error Rate.

**Use Cases:**
- Public APIs.
- Microservices under constant external load.

**Example:**  
An online store API receives 500, 1,000, and 2,000 RPS to simulate real-world traffic.

---

## Why Model Selection Matters
- **Closed Model** fits for limited-session systems (e.g., databases with connection pools).
- **Open Model** reflects real API usage where request frequency matters.

Incorrect model choice can distort test results:
- **Closed Model** might underestimate infrastructure load.
- **Open Model** might miss connection pool constraints.

---

## Technology Stack
For studying and implementing load testing theory:
- **Load Generators:**  
  Apache JMeter, Gatling.

- **Monitoring Systems:**  
  Prometheus, Grafana, InfluxDB.

- **Infrastructure Tools:**  
  Docker, cAdvisor, node_exporter, prometheus_exporter.

---

## Summary
Load testing theory covers different testing approaches for various operational conditions:
- **Peak Load:** find system limits.
- **Steady Load:** ensure long-term stability.
- **Stress Load:** identify critical failure points.
- **Volume Load:** evaluate large data handling.

These types form the foundation for designing testing strategies and choosing appropriate tools.
