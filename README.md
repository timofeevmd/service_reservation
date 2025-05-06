# service_reservation
this project was build for performance testing courses.  don't judge strictly

## [infrastructure](#install-infrastructure)
1. [java-17](#java)
2. [maven](#maven)
3. [docker](#docker)
4. [jmeter-5.4.1](#jmeter)

## [services](#services)
1. [docker-compose.yml](#docker-compose)
2. [grafana](#grafana)
3. [prometheus](#prometheus)
4. [influxdb](#influxdb)
5. [jmeter](#jmeter)

## install infrastructure
0. The working directory throughout the entire master class will be located in the root directory of your user account if you are using a Unix-like system, or on drive C if you are using Windows. This is where we will place the cloned project.
```bash
mkdir ~/projects/ &&
cd ~/projects/ &&
git clone https://github.com/timofeevmd/service_reservation.git
```


1. ### java
    1. #### **macOs**
       ```bash
       brew install openjdk@11
       ```
        - Expected result
        ```bash
        java --version
        ```
       Output
        ```bash
        openjdk 11.0.26 2025-01-21
        OpenJDK Runtime Environment Homebrew (build 11.0.26+0)
        OpenJDK 64-Bit Server VM Homebrew (build 11.0.26+0, mixed mode)
       ```
   troubleshooting
    - if you had different java version, check the [link](https://stackoverflow.com/questions/21964709/how-to-set-or-change-the-default-java-jdk-version-on-macos) for switch between your jdk's
    2. #### **linux**
        - [manual](https://www.digitalocean.com/community/tutorials/how-to-install-java-on-centos-and-fedora)
        - if manual is not work
        - `wget https://builds.openlogic.com/downloadJDK/openlogic-openjdk/11.0.26+4/openlogic-openjdk-11.0.26+4-linux-64.tar.gz`
        - if wget is not work try to download [openjdk-11-...](https://builds.openlogic.com/openjdk-downloads?field_java_parent_version_target_id=406&field_operating_system_target_id=426&field_architecture_target_id=391&field_java_package_target_id=396)
        ```bash
          sudo mkdir -p /opt/java &&
          cd /opt/java &&
          sudo tar -xvzf ~/openlogic-openjdk-11.0.26+4-linux-x64.tar.gz &&
          sudo mv openlogic-openjdk-11.0.26+4-linux-x64 java-11 &&
          sudo vim /etc/profile.d/java.sh
          ```
        - set of them
        ```bash
          export JAVA_HOME=/opt/java/java-11
          export PATH=$JAVA_HOME/bin:$PATH
          ```
        - `source /etc/profile.d/java.sh`
        - Expected result
         ```bash
         java --version
         ```
       Output
         ```bash
         openjdk 11.0.26 2025-01-21
         OpenJDK Runtime Environment Homebrew (build 11.0.26+0)
         OpenJDK 64-Bit Server VM Homebrew (build 11.0.26+0, mixed mode)
         ```
       troubleshooting
        - if you had different java version, check the [link](https://stackoverflow.com/questions/21964709/how-to-set-or-change-the-default-java-jdk-version-on-macos) for switch between your jdk's

    3. #### **windows**
        - download [.zip](https://builds.openlogic.com/downloadJDK/openlogic-openjdk/11.0.26+4/openlogic-openjdk-11.0.26+4-windows-x64.zip) or [.msi](https://builds.openlogic.com/downloadJDK/openlogic-openjdk/11.0.26+4/openlogic-openjdk-11.0.26+4-windows-x64.msi) to use installation wizard
        - Follow the installation wizard, choosing 'Set JAVA_HOME variable'
        - Note the installation path (usually C:\Program Files\OpenLogic\jdk-11.0.26.4-hotspot\ or C:\Program Files\Java\jdk-11).
        - Open Control Panel → System → Advanced system settings.
        - Go to the Advanced tab and click Environment Variables.
        - Under System Variables
        - Find the `JAVA_HOME` variable (if it does not exist, click New)
        - Set `JAVA_HOME=C:\Program Files\OpenLogic\jdk-11.0.26.4-hotspot\`
        - Find Path → click Edit → New
        - Set `%JAVA_HOME%\bin`

        - Expected result
        ```bash
        java --version
        ```
       Output
        ```bash
        openjdk 11.0.26 2025-01-21
        OpenJDK Runtime Environment OpenLogic-OpenJDK (build 11.0.26+4-adhoc..jdk11u)
        OpenJDK 64-Bit Server VM OpenLogic-OpenJDK (build 11.0.26+4-adhoc..jdk11u, mixed mode)
       ```
       troubleshooting
        - if you had different java version, check the [link](https://stackoverflow.com/questions/26993101/switching-between-different-jdk-versions-in-windows) for switch between your jdk's
2. ### maven
    1. [manual](https://www.baeldung.com/install-maven-on-windows-linux-mac#bd-installing-maven-on-mac-os-x) for macOs/linux/windows
        - Expected result
       ```bash
       mvn --version
       ```
       Output
       ```bash
       Apache Maven 3.9.9 (8e8579a9e76f7d015ee5ec7bfcdc97d260186937)
       Maven home: /opt/homebrew/Cellar/maven/3.9.9/libexec
       Java version: 11.0.19, vendor: Oracle Corporation, runtime: /Library/Java/JavaVirtualMachines/jdk-11.jdk/Contents/Home
       Default locale: en_US, platform encoding: UTF-8
       OS name: "mac os x", version: "15.3.2", arch: "aarch64", family: "mac"
       ```
3. ### docker
    1. [manual](https://docs.docker.com/desktop/) for macOs/linux/windows
    - Expected result
        ```bash
        docker --version &&
        docker-compose --version
        ```
      Output like this
        ```bash
       Docker version 24.0.6, build ed223bc
       Docker Compose version v2.23.0-desktop.1
       ```

   troubleshooting
    - Error permission denied while trying to connect to the Docker daemon socket at unix:///var/run/docker.sock: Get "http://%2Fvar%2Frun%2Fdocker.sock/v1.48/containers/json?all=1": dial unix /var/run/docker.sock: connect: permission denied

      ```bash
      sudo groupadd docker &&
      sudo usermod -aG docker $USER &&
      newgrp docker &&
      reboot
      ```
4. ### jmeter
    1. **macOs**
       ```bash
       mkdir ~/tools/ &&
       cd ~/tools/ &&
       curl -OL https://archive.apache.org/dist/jmeter/binaries/apache-jmeter-5.4.1.zip      
       unzip apache-jmeter-5.4.1.zip &&
       cd ./apache-jmeter-5.4.1/bin/ &&
       ./jmeter
       ```
    2. **linux**
       ```bash
       mkdir ~/tools/ &&
       cd ~/tools/ &&
       wget https://archive.apache.org/dist/jmeter/binaries/apache-jmeter-5.4.1.tgz &&
       tar -xvzf apache-jmeter-5.4.1.tgz &&
       cd ./apache-jmeter-5.4.1/bin/ &&
       ./jmeter
       ```
    3. **windows**
        - downloads [.zip](https://archive.apache.org/dist/jmeter/binaries/apache-jmeter-5.4.1.zip)
        - unzip .zpi arch
        - run jmeter.bat

## services
<p style="color: yellow"> WARNING </p>

0. open `.env` file on the root of project
    1. If your laptop has an Intel or Ryzen CPU, change `PLATFORM` parm to `linux/amd64`
    2. If your laptop has M1 or newer chip, change `PLATFORM` parm to `linux/arm64`
   
1. #### **docker-compose** 
   - from root directory start docker-compose reservation_service
      ```bash
       docker-compose -f docker-compose.yml up -d 
      ```
   - check current state
     ```bash
     docker ps -a
     ```
   - expected result
     ```bash
     CONTAINER ID   IMAGE                                     COMMAND                  CREATED      STATUS                      PORTS                                            NAMES
     8472204b071f   influxdb:1.8                              "/entrypoint.sh infl…"   2 days ago   Up 22 minutes               0.0.0.0:8083->8083/tcp, 0.0.0.0:8086->8086/tcp   influxdb
     5516036bb73b   michaelt1223/perf_sr_frontend:latest      "/docker-entrypoint.…"   5 days ago   Up 22 minutes               80/tcp, 0.0.0.0:3000->3000/tcp                   frontend
     7fde5b402724   michaelt1223/perf_sr_backend:latest       "java -jar app.jar"      5 days ago   Up 22 minutes               0.0.0.0:8080->8080/tcp                           backend
     f08d6be43900   postgres:latest                           "docker-entrypoint.s…"   5 days ago   Up 22 minutes (healthy)     0.0.0.0:5432->5432/tcp                           database
     de5d1b1f9e26   prom/prometheus:latest                    "/bin/prometheus --c…"   5 days ago   Up 22 minutes               0.0.0.0:9091->9090/tcp                           prometheus
     28fd29a8499a   grafana/grafana:latest                    "/run.sh"                5 days ago   Up 22 minutes               0.0.0.0:3001->3000/tcp                           grafana
     7e25219f1688   quay.io/prometheus/node-exporter:latest   "/bin/node_exporter"     5 days ago   Up 22 minutes               0.0.0.0:9100->9100/tcp                           node_exporter
     aee27e4ea7b1   gcr.io/cadvisor/cadvisor:latest           "/usr/bin/cadvisor -…"   5 days ago   Up 22 minutes (unhealthy)   8080/tcp, 0.0.0.0:8081->8081/tcp                 cadvisor
     ```
   
2. #### **grafana**
   - INFO
     - official [docs](https://grafana.com/docs/grafana/latest/)
     - grafana [dashboards](https://grafana.com/grafana/dashboards/)
     - standard username: `admin`, password: `admin`
   - **how to connect data bases:**
       - **prometheus** -> go to
         - local [grafana](http://localhost:3001/connections/add-new-connection)
         - select prometheus
         - click `add new data source` button
         - set `http://prometheus:9090` into the inputfield "Prometheus server URL"
         - click `Save and test` button onto the futter 
       - **influx** -> go to
         - local [grafana](http://localhost:3001/connections/add-new-connection)
         - select influxdb
         - click `add new data source` button
         - set` http://influxdb:8086` into http part field `URL`
         - on the `InfluxDB Details` part
         - fields:
           - database: `loadTestingDB`
           - user: `admin`
           - password: `admin`
     - **import dashboards:**
       - **for prometheus** 
         - for cAdvisor -> go to
           - local [grafana](http://localhost:3001/dashboards)
           - click `new` button -> `import` button
                - into the `Find and import dashboards` field set id `14282` for [cAdvisor](https://grafana.com/grafana/dashboards/14282-cadvisor-exporter/) dashboard
                - click `Load` button
                - choose prometheus as datasource
                - click the `import` button
                - be happy - that's the most important!!!
         - for node_exporter -> go to
           - local [grafana](http://localhost:3001/dashboards)
           - click `new` button -> `import` button
             - into the `Find and import dashboards` field set id `1860` for [node_exporter](https://grafana.com/grafana/dashboards/1860-node-exporter-full/) dashboard
             - click `Load` button
             - choose prometheus as database
             - click the `import` button
         - for postgres_exporter -> go to
           - local [grafana](http://localhost:3001/dashboards)
           - click `new` button -> `import` button
               - into the `Find and import dashboards` field set id `9628` for [postgres_exporter](https://grafana.com/grafana/dashboards/9628-postgresql-database/) dashboard
               - click `Load` button
               - choose prometheus as database
               - click the `import` button
       - **influx** 
       - standard username: `admin`, password: `admin`
         - for **JMeter** -> go to
           - local [grafana](http://localhost:3001/dashboards)
           - click `new` button -> `import` button
             - into the `Find and import dashboards` field set id `5496` for [Apache JMeter](https://grafana.com/grafana/dashboards/5496-apache-jmeter-dashboard-by-ubikloadpack/) dashboard
             - click `Load` button
             - choose influxDB as datasource
             - click the `import` button
3. #### **prometheus**
    - special config if your based system is `Windows`
      - go to prometheus config file `monitoring/config/prometheus.yml`
      - on the scrape config section you need to change each part of `job_name`
        - targets: ['cadvisor:8081'] -> targets: ['localhost:8081']
        - targets: ['node_exporter:9100'] -> targets: ['localhost:9100']
        - targets: ['postgres_exporter:9187'] -> targets: ['localhost:9187']
        - targets: ['backend:8080'] -> targets: ['localhost:8080']
    - INFO 
      - prometheus config file `monitoring/config/prometheus.yml`
      - prometheus node down alerting config `monitoring/config/node_down.yml`
      - official [docs](https://prometheus.io/docs/introduction/overview/)
4. #### **influxdb**
    - INFO
      - official [docs](https://docs.influxdata.com/influxdb/v1/)
5. #### **jmeter**
    - INFO
      - official [docs](https://jmeter.apache.org/usermanual/index.html)
