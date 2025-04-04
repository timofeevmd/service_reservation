# Scenarios
---
### General Description
Load testing scenarios describe the sequence of actions and conditions that simulate real-world application usage. They include request sequences, user flows, load models, and load levels. Well-designed scenarios accurately model user behavior and assess system performance under conditions as close to real as possible. Scenarios are based on the business logic of the application, non-functional requirements (SLA), application usage statistics, and potential load peaks.

### Objectives
- Simulate real application usage:
    - Identify typical and critical user scenarios.
    - Simulate interactions of multiple users under varying load levels.
- Test system behavior under different load models:
    - Closed model (fixed number of users).
    - Open model (fixed request rate).
- Automate scenario execution:
    - Set up load generation and metric collection for further analysis.

### Tech Stack
For scenario development and execution:
- **Apache JMeter**:
    - Primary tool for creating and executing scenarios.
    - Capabilities: development of complex scenarios using GUI, REST API testing, integration with other tools.

### Theoretical Overview

#### Key Elements of a Load Testing Scenario
- **Request Sequence**:
    - Reflects user actions: login, page views, order creation.
    - Example:
        1. User authentication.
        2. Product search.
        3. Adding a product to the cart.
        4. Checkout.

- **User Flows**:
    - Scenarios may include various user types performing different actions (e.g., customers and administrators).
    - Example:
        - 70% of users place orders.
        - 20% browse the catalog.
        - 10% update account information.

- **Load Levels**:
    - Gradual load increase (ramp-up).
    - Steady load over a specified duration.
    - Stress load exceeding acceptable levels.

- **Load Models**:
    - Closed model: fixed number of users.
    - Open model: fixed request rate.

### Configuring Scenarios in Apache JMeter

1. **Install Apache JMeter**
    - Download JMeter from the official website.
    - Unpack and run `jmeter.bat` (Windows) or `jmeter` (Linux/Mac).
    - Ensure all plugins and dependencies are correctly installed.

2. **Key Components in JMeter**
    - **Test Plan**: container for all testing elements.
    - **Thread Group**: defines virtual users, ramp-up period, test duration.
        - Number of Threads (users).
        - Ramp-Up Period.
        - Loop Count.
    - **Samplers**: send requests to the target system.
        - Example: HTTP Request for REST API interaction.
    - **Listeners**: collect test results.
        - Examples: View Results Tree, Aggregate Report.
    - **Timers**: add delays between requests (e.g., Constant Timer).
    - **Config Elements**: configure shared parameters (e.g., HTTP Header Manager).

3. **Recording Scenarios in JMeter**
    - **Using HTTP(S) Test Script Recorder**:
        - Start JMeter's proxy server to record user actions.
        - Configure browser to use the proxy.
        - Auto-create Samplers based on actions.
        - Steps:
            - Add Thread Group.
            - Add HTTP(S) Test Script Recorder.
            - Set the port (default 8888) and start recording.
            - Perform actions in the app (e.g., login, search, checkout).
            - Stop recording. Requests will be added to the Test Plan.
    - **Manual Scenario Creation**:
        - Add HTTP Request to Thread Group.
        - Specify URL, method, and parameters.
        - Set headers and body via HTTP Header Manager and HTTP Body Data.

4. **Load Configuration**
    - **Ramp-Up Period**:
        - Example: 100 users over 60 seconds.
    - **Steady Load**:
        - Example: 1000 requests over 30 minutes.
    - **Stepping Thread Group**:
        - Gradually add users (e.g., 10 users every 30 seconds).

5. **Result Analysis**
    - Use **Aggregate Report** to analyze key metrics:
        - Average response time.
        - Throughput.
        - Error rate.
    - Save results to CSV for further analysis in Excel or Grafana.

### Sample REST API Scenario in JMeter

**Goal**:
- Assess the performance of the login API.

**Request Sequence**:
1. `POST /api/login` – authentication.
2. `GET /api/products` – retrieve product list.
3. `POST /api/cart` – add product to cart.
4. `POST /api/checkout` – complete checkout.

**Configuration**:
- Thread Group: 50 users, 10s ramp-up, 5 min duration.
- HTTP Header Manager: add headers (e.g., Authorization).
- Timer: 2s delay between requests.

### Recommended JMeter Plugins

1. **JMeter Plugins Manager**: Simplifies plugin management.
2. **Ultimate Thread Group**: Flexible load shaping.
3. **JSON Path Extractor**: Extract data from JSON responses.
4. **JSON Assertion**: Validate JSON response content.
5. **WebSocket Sampler**: Test WebSocket connections.
6. **WebSocket Request-Response Sampler**: Test WebSocket two-way communication.
7. **gRPC Request Sampler**: Test gRPC APIs.
8. **gRPC Plugin**: Use `.proto` files for gRPC testing.
9. **PerfMon Metrics Collector**: Collect server metrics (CPU, memory, network).
10. **Flexible File Writer**: Export test results in various formats.
11. **Response Times Over Time**: Visualize response time trends.
12. **Throughput Shaping Timer**: Control request intensity (RPS).
13. **Boundary Extractor**: Extract data using text boundaries.
14. **Parallel Controller**: Execute multiple requests in parallel.
15. **Dummy Sampler**: Emulate responses without hitting real APIs.
16. **Active Threads Over Time**: Visualize active user count over time.

### Summary
Load testing scenarios are the foundation of successful performance testing. With JMeter, you can configure request sequences, load models, and load levels. Both recorded and manually created scenarios offer flexibility to tailor tests to real-world conditions. Once executed, metrics are collected and analyzed to identify bottlenecks and optimize the system. The recommended plugins cover a wide range of API testing needs, including REST, gRPC, and WebSocket. Tools like gRPC Request Sampler, WebSocket Sampler, Ultimate Thread Group, and PerfMon Metrics Collector enhance testing efficiency, helping to create robust load testing strategies.
