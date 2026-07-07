# Load Testing Theory

## Key Load Types

Within load testing theory, several key load types are distinguished. The four below are a practical teaching taxonomy; each is mapped to the corresponding ISTQB standard type where one exists (see the note after *Volume Load Testing*).

### 1. Peak Load Testing
**Description:**
Tests are conducted to identify the maximum number of requests or users the system can handle without violating SLAs/SLOs. The load gradually increases (a *stepped ramp*) until the system reaches its limit — the point where response time degrades sharply or the error rate rises. This corresponds to ISTQB **capacity testing** (how many users/transactions the system supports while still meeting objectives) and, at the ceiling, to **stress testing**.

**Goals:**
- Identify the system's performance ceiling (maximum sustainable **throughput**).
- Validate compliance with non-functional requirements (e.g. RPS > 1000 at p95 < 500 ms).
- Locate the **bottleneck** — the single resource that caps throughput (CPU, DB, connection pool).

**Use Cases:**
- Public REST APIs.
- Infrastructure components such as load balancers or databases.

**Example:**
An online store is tested to handle up to 10,000 concurrent users. The scenario ramps from 1,000 to 12,000 users to find the point where response time first exceeds 1 second — that inflection point is the reported capacity.

### 2. Steady Load Testing
**Description:**
Validates how the system performs under a constant, pre-defined load over an extended period. When the observation window is long (hours), this is ISTQB **endurance (soak) testing**; over shorter windows it is ordinary **load testing** at a fixed level.

**Goals:**
- Detect resource leaks and slow degradation (memory leaks, unclosed DB connections, growing thread pools, log/disk growth, cache bloat).
- Ensure system stability during realistic sustained operation — the failure mode here is *gradual*, not immediate, so it is invisible to short tests.

**Use Cases:**
- Backend services.
- Microservices with stable traffic patterns.

**Example:**
A REST API processing 1,000 requests per second is tested over 8 hours. CPU, memory, response time, and connection-pool usage are monitored for an upward drift that would eventually breach the SLO.

### 3. Stress Load Testing
**Description:**
Simulates extreme conditions to find system failure points by exceeding normal limits — ISTQB **stress testing** (behaviour at or beyond anticipated workloads, or under reduced resources). A closely related variant is **spike testing**: a *sudden* burst of peak load, after which the system must return to a steady state.

**Goals:**
- Assess system resilience to abnormal spikes and overload.
- Observe **graceful degradation** (shedding load, queueing, returning 429/503) versus catastrophic failure.
- Observe **recovery** after the load subsides — does the system self-heal, or stay degraded?

**Use Cases:**
- Financial and retail systems (e.g. Black Friday sales).
- Services with highly concurrent, bursty user loads.

**Example:**
A payment system is tested at 10× the normal load, emulating a peak of 5,000 RPS, then dropped back to normal to verify it recovers to baseline latency.

### 4. Volume Load Testing
**Description:**
Checks system performance with large *data* volumes (large databases, large payloads, big file storages) rather than large *request* volumes. The variable under test is the amount of data, not the number of concurrent users.

**Goals:**
- Measure read/write speed and stability as data size grows.
- Analyze how data size impacts query plans, index efficiency, and response time (e.g. a query that is fast on 10k rows but slow on 10M rows).

**Use Cases:**
- Databases.
- File storage systems and reporting/export endpoints.

**Example:**
The system is tested with over 1 TB of data to measure search and update performance against a realistically sized dataset.

> **Standards note.** *Volume testing* is an established industry practice but is **not** enumerated among the types in the ISTQB Performance Testing syllabus (which lists load, stress, scalability, spike, endurance, concurrency, and capacity testing). Present it as a widely used practice, not as a term from that syllabus. See *Purpose, Object, and Subject of Load Testing → Types of Load Testing* for the full standard list.

---

## General Load Testing Goals
- Validate compliance with non-functional requirements, expressed as measurable SLOs/SLIs.
- Define system performance and resilience limits (capacity, breaking point, recovery behaviour).
- Identify bottlenecks and produce actionable, quantified recommendations.
- Prepare the system for real-world operation and de-risk releases.

---

## Load Models: Closed vs Open

The workload model is one of the most consequential — and most often mis-set — choices in a load test. It determines *how new requests are generated*, and the wrong choice can invalidate the entire result. The distinction is formalized in the canonical paper Schroeder, Wierman & Harchol-Balter, *"Open Versus Closed: A Cautionary Tale"* (USENIX NSDI 2006).

### Closed Load Model
A fixed number of virtual users (VUs) interact with the system. Each user sends a request, **waits for the response**, optionally thinks (think time), and only then sends the next request. New arrivals are triggered by completions.

**Characteristics:**
- Fixed user count / concurrency (N, sometimes called MPL — multiprogramming level).
- Inter-arrival time depends on system response time: **if the system slows down, the load offered to it also slows down.**
- Tool mapping — k6: *"the execution time of each iteration dictates the number of iterations executed"* (`constant-vus`, `ramping-vus`); Gatling: *"closed systems, where you control the concurrent number of users."*

**Key Metrics:**
- Response Time, Throughput, Concurrency.

**Governing law — Little's Law.** In steady state the three are not independent; they are tied by Little's Law (`L = λW`), which in load-testing form is:

- `N = X × R` — concurrency = throughput × response time; and, including think time `Z` (the *Interactive Response Time Law*):
- `N = X × (R + Z)`, equivalently `R = N / X − Z`.

This is why, in a closed model, you cannot independently dial throughput and response time: fixing N caps the achievable throughput once R grows.

**Use Cases:**
- Enterprise applications with a limited, known number of sessions (internal CRMs, call-centre / ticketing systems, systems fronted by a fixed connection pool).

**Example:**
An internal CRM with 500 concurrent users. Tests run from 50 to 200 users in increments of 50.

### Open Load Model
Simulates a constant flow of *new* user requests arriving independently of previous ones (e.g. a Poisson arrival process or a fixed arrival-rate schedule). Arrivals do **not** wait for prior requests to complete.

**Characteristics:**
- Defined *arrival rate* (RPS), not a fixed user count.
- Independent users; a slow server does **not** slow the offered load — requests keep arriving and pile up, so queues (and response time) can grow without bound under overload.
- Tool mapping — k6: *"the open model decouples VU iterations from the iteration duration; the response times of the target system no longer influence the load"* (`constant-arrival-rate`, `ramping-arrival-rate`); Gatling: *"open systems, where you control the arrival rate of users… Most websites behave this way."*

**Key Metrics:**
- Request/Arrival Rate, Response Time, Error Rate.

**Use Cases:**
- Public APIs and websites.
- Microservices under constant external load.

**Example:**
An online store API receives 500, 1,000, and 2,000 RPS to simulate real-world traffic, regardless of how fast the API responds.

---

## Why Model Selection Matters
- **Closed Model** fits limited-session systems (e.g. databases behind a fixed connection pool, internal tools with a known user population).
- **Open Model** reflects real public API/website usage, where request frequency is externally driven and does not back off when the server struggles.

Incorrect model choice can distort test results — the Schroeder paper's central finding is that *"the impact is huge… under a fixed load, the mean response time for an open system model can exceed that for a closed system model by an order of magnitude or more."* Concretely:
- **A closed model can underestimate infrastructure load**, because when the system slows, the generator automatically eases off — masking the runaway queueing that a real open workload would cause. Gatling puts it bluntly: *"if you're using a closed workload model… while your system actually is an open one, your test is broken."*
- **An open model can miss connection-pool / session constraints** that only bind when concurrency is fixed.
- Open and closed models are **antinomical** — do not mix them in the same injection profile.

> **Measurement pitfall — Coordinated Omission (Gil Tene).** Closed-loop generators that wait for a response before sending the next request *coordinate* with the server: when the server stalls, the generator also stalls and simply **fails to send** the requests that would have suffered the long delay. Those slow samples are omitted, so the reported p99/p99.9 look far better than reality. Correct for this by measuring latency from the time each request *should* have been sent according to the intended schedule (as done by `wrk2`/HdrHistogram), or by using an open model with a fixed arrival rate.

> **Think time vs pacing.** *Think time* is a pause **within** a user flow, between individual actions, modelling human reading/typing (in k6 it is `sleep()` and is excluded from `http_req_duration`). *Pacing* is the delay **between successive iterations** of the whole script/transaction, controlling how often each VU restarts — the primary lever for hitting a target iteration/throughput rate (and it ties back to Little's Law: iterations/s ≈ N / (R + think + pacing)).

---

## Technology Stack
For studying and implementing load testing theory:
- **Load Generators:**
  Apache JMeter, Gatling, k6 (choose one that supports the workload model you need — open-model/arrival-rate execution is essential for public-API tests).

- **Monitoring Systems:**
  Prometheus, Grafana, InfluxDB.

- **Infrastructure Tools:**
  Docker, cAdvisor, node_exporter, postgres_exporter.

---

## Summary
Load testing theory covers different testing approaches for various operational conditions:
- **Peak Load:** find system limits (capacity / stress at the ceiling).
- **Steady Load:** ensure long-term stability (endurance/soak — catches leaks).
- **Stress / Spike Load:** identify critical failure points and verify recovery.
- **Volume Load:** evaluate large-data handling.

Underpinning all of them is the **workload model** (open vs closed) and **Little's Law**, which govern how offered load, concurrency, throughput, and response time relate. Getting the model right — and avoiding measurement traps like coordinated omission — is what makes results trustworthy. These choices form the foundation for designing testing strategies and selecting the appropriate tools.

---

## References
- Schroeder, Wierman, Harchol-Balter, "Open Versus Closed: A Cautionary Tale", USENIX NSDI 2006. https://www.usenix.org/legacy/event/nsdi06/tech/full_papers/schroeder/schroeder.pdf
- Little's Law (queueing theory): J.D.C. Little (1961); K. Sigman, Columbia lecture notes. http://www.columbia.edu/~ks20/stochastic-I/stochastic-I-LL.pdf — Interactive Response Time Law: Lazowska, Zahorjan, Graham, Sevcik, *Quantitative System Performance*, Ch. 3. https://homes.cs.washington.edu/~lazowska/qsp/
- k6 (Grafana) — *Open and closed models*. https://grafana.com/docs/k6/latest/using-k6/scenarios/concepts/open-vs-closed/
- Gatling — *Workload models* (closed vs open). https://docs.gatling.io/testing-concepts/workload-models/
- Coordinated omission — Gil Tene, `wrk2` / HdrHistogram. https://github.com/giltene/wrk2 · http://hdrhistogram.org/
- ISTQB *Certified Tester — Performance Testing (CT-PT) Syllabus* (test types). https://astqb.org/assets/documents/ISTQB-CTFL-PT-Syllabus-2018.pdf
