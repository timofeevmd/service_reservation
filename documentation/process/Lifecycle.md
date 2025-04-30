# Load Testing Lifecycle and Best Practices (Shift-Left & Shift-Right)

*By a performance engineer with 5+ years of experience*

---

## Load Testing Lifecycle

### 1. Initiation
- **Description:** Define goals, roles, and the scope of load testing.
- **Objective:** Understand business goals, SLA (Service Level Agreement), and SLO (Service Level Objectives).
- **Output:** Document outlining testing goals and coverage areas.

### 2. Design
- **Description:** Develop methodology and test scenarios.
- **Objective:** Create documents covering user flows, risks, load levels, and boundaries.
- **Deliverables:** Methodology guide, load scenarios, target RPS (Requests per Second) calculation.

### 3. Preparation
- **Description:** Prepare data, environments, and tools.
- **Objective:** Ensure infrastructure and monitoring are ready.
- **Tools:** JMeter, Docker, Grafana, InfluxDB, Prometheus, k6.

### 4. Execution
- **Description:** Run the load tests.
- **Objective:** Obtain reproducible and valid results.
- **Load Types:** Smoke, Baseline, Stress, Spike, Soak.

### 5. Analysis
- **Description:** Collect and interpret metrics.
- **Metrics:** Latency, Throughput, Error rate, System resource usage (CPU, Memory, Disk I/O).
- **Tools:** Grafana, Prometheus, InfluxDB.

### 6. Reporting
- **Description:** Create reports with findings and recommendations.
- **Objective:** Present data in a format understandable by both technical and business audiences.

### 7. Retrospective
- **Description:** Analyze how tests went and what can be improved.
- **Result:** Better scenarios, approaches, and documentation.

---

## Shift-Left Techniques

###  1. Load Testing in CI/CD
- Run lightweight load smoke tests on every pull request.
- **Goal:** Catch regressions early.

### 2. Documenting Load Expectations at API Design Level
- Define SLOs/SLAs with developers and architects.
- Collect non-functional requirements per endpoint.

### 3. User Scenario Library
- Use Gherkin/YAML for defining scenarios linked to OpenAPI specs.

### 4. Mock/Stub Testing
- Use mocks to simulate services early in the development cycle.
- **Tools:** WireMock, MockServer.

---

## Shift-Right Practices

###  1. Production Monitoring
- Use **Prometheus**/**Grafana** to gather real-time metrics.
- Identify actual load patterns and user behavior.

###  2. Canary Deployment under Load
- Route partial traffic to new versions; compare metrics.
- **Tools:** Istio, Linkerd, Argo Rollouts.

###  3. Chaos Engineering + Load
- Simulate failures during load testing.
- **Tools:** Gremlin, Chaos Mesh.

###  4. A/B Performance Testing
- Compare different feature versions under real traffic.

---

##  Industry Standards and References

### Standards
- **ISO/IEC 25010** — Software Quality Model
- **IEEE 829** — Test Documentation Standard
- **ISTQB Performance Testing Add-on**
- **RFC 2119** — Keyword usage for test specs

### References
- [Apache JMeter](https://jmeter.apache.org)
- [k6 Documentation](https://k6.io/docs)
- [Grafana Docs](https://grafana.com/docs/)
- [Prometheus Docs](https://prometheus.io/docs/)

---

##  Key Terminology

- **SLA (Service Level Agreement):** Agreement defining acceptable performance (e.g., 95% of requests under 1s).
- **SLO (Service Level Objective):** Internal target for system reliability or speed.
- **Latency:** Delay between sending a request and receiving a response.
- **Throughput:** Number of processed requests per second.
- **Spike Test:** Sudden surge of traffic.
- **Soak Test:** Long-duration test under constant load.

---
