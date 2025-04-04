# Welcome to the Load Testing Course

---

# Load Testing Course Program

## 1. Introduction to Load Testing

### 1.1 [Purpose, Object, and Subject of Load Testing](/theory/PurposeObjectAndSubjectOfLoadTesting.md)

**Overview:**  
Fundamentals of load testing simulating real application workloads. Focus on REST-based API systems.

**Goals:**

- Understand the need for load testing.
- Define objectives: prevent system failures, detect bottlenecks, check scalability.
- Identify subjects (users) and objects (systems, components).

**Theory:**  
The goal is to ensure the system can handle target loads. Objects: APIs, databases, services. Subjects: users and
development teams.

**Summary:**  
Main objectives and actors of load testing are outlined.

### [1.2 Load Testing Theory](/theory/LoadTestingTheory.md)

**Overview:**  
Basic concepts: types of load (peak, steady, stress, volume).

**Goals:**

- Learn about different load types and their purposes.

**Theory:**

- Peak: discover system limits.
- Stress: check stability thresholds.
- Volume: validate system under large datasets.

**Summary:**  
Understanding of each load type role is achieved.

### [1.3 Team Composition and Responsibilities](/theory/TeamCompositionAndResponsibilitiesInLoadTesting.md)

**Overview:**  
Organization of the load testing team.

**Goals:**

- Clarify roles and responsibilities.

**Technology Stack:**  
Agile methodology, task management tools (Jira, Trello).

**Theory:**

- Load Testing Engineer: test preparation and execution.
- Analyst: data analysis.
- DevOps: infrastructure management.

**Summary:**  
Team structure established.

---

## 2. Load Testing Documentation

### 2.1 Methodology

**Overview:**  
Develop a general strategy for conducting load tests.

**Goals:**

- Outline testing steps.
- Align methodology with the team.

**Technology Stack:**  
Markdown, Confluence, Google Docs.

**Theory:**  
Includes goal setting, approach selection, data preparation, execution, and analysis.

**Summary:**  
Testing roadmap created.

### 2.2 Test Scenarios

**Overview:**  
Design and implementation of load test scenarios.

**Goals:**

- Simulate realistic application workloads.

**Technology Stack:**  
JMeter, K6.

**Theory:**  
Scenarios define request sequences, user flows, load levels, and models.

**Summary:**  
Scenarios ready for execution.

### 2.3 Reporting

**Overview:**  
Generate reports on test results.

**Goals:**

- Communicate results to stakeholders.

**Technology Stack:**  
Grafana, Excel, Confluence.

**Theory:**  
Reports include summaries, performance metrics, graphs, and conclusions.

**Summary:**  
Report templates approved.

---

## 3. Load Testing Tools

### 3.1 Test Data Preparation

**Overview:**  
Create test datasets for load testing.

**Goals:**

- Simulate realistic datasets.

**Technology Stack:**  
Python/Java, PostgreSQL, MySQL.

**Theory:**  
Includes generation of test users and scenarios.

**Summary:**  
Test data prepared.

### 3.2 Load Testing Tools

#### 3.2.1 System Architecture

**Overview:**  
Design architecture for load testing.

**Goals:**

- Build a reliable load testing environment.

**Technology Stack:**  
JMeter, Docker, InfluxDB, Prometheus, Grafana, Node-exporter, cAdvisor.

**Theory:**  
Composed of load generators (JMeter), monitoring tools (Prometheus, Grafana), and containers (Docker).

**Summary:**  
Architecture implemented.

#### 3.2.2 JMeter Overview

**Overview:**  
Basic introduction to JMeter.

**Goals:**

- Master essential features.

**Technology Stack:**  
JMeter.

**Theory:**  
Key components: Thread Groups, Samplers, Listeners.

**Summary:**  
Tool familiarization completed.

### 3.3 Load Testing Execution

**Overview:**  
Conduct actual load testing.

**Goals:**

- Validate system under load.

**Technology Stack:**  
JMeter, Prometheus, Grafana.

**Theory:**  
Includes preparation, execution, and data collection.

**Summary:**  
Load testing conducted.

---

## 4. Load Testing Analytics

### 4.1 Metrics Collection

**Overview:**  
Collect server and application metrics.

**Goals:**

- Obtain complete system performance data.

**Technology Stack:**  
Prometheus, Grafana.

**Theory:**  
Monitoring tools visualize system performance.

**Summary:**  
Metrics collected.

### 4.2 Data Analysis

**Overview:**  
Analyze collected metrics.

**Goals:**

- Identify bottlenecks.

**Technology Stack:**  
Grafana.

**Theory:**  
Build dashboards, find correlations.

**Summary:**  
Analysis completed.

### 4.3 Reporting

**Overview:**  
Create final documentation.

**Goals:**

- Deliver insights to stakeholders.

**Technology Stack:**  
Confluence, Excel.

**Theory:**  
Includes graphs, conclusions, and recommendations.

**Summary:**  
Report finalized.

---

## 5. Load Testing in Agile Environment

### 5.1 Release Cycle

**Overview:**  
Integrate load testing into the release cycle.

**Goals:**

- Align with SLA and non-functional requirements.

**Technology Stack:**  
Jira, CI/CD tools.

**Theory:**  
Continuous load testing across development stages.

**Summary:**  
Release cycle integrated.

### 5.2 Collaboration with QA

**Overview:**  
Work alongside functional QA teams.

**Goals:**

- Avoid redundant efforts.

**Technology Stack:**  
Jira, Slack.

**Theory:**  
Unified testing strategy combining functional and load testing.

**Summary:**  
Collaboration established.

### 5.3 Solo Load Testing Engineer

**Overview:**  
Strategies when being the only load testing engineer on a project.

**Goals:**

- Maximize efficiency.

**Technology Stack:**  
JMeter, automation tools.

**Theory:**  
Focus on automation and task prioritization.

**Summary:**  
Solo strategy formulated.
