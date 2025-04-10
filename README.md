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
1. ### java
   1. #### **macOs**
      - `brew install openjdk@17`
   2. #### **linux**
      - [manual](https://www.digitalocean.com/community/tutorials/how-to-install-java-on-centos-and-fedora) 
      - if manual is not work
      - `wget https://github.com/adoptium/temurin17-binaries/releases/download/jdk-17.0.9+9/OpenJDK17U-jdk_x64_linux_hotspot_17.0.9_9.tar.gz`
      - if wget is not work try to download [openjdk-17-...](https://adoptium.net/temurin/releases/?arch=x64&os=linux&package=jdk&version=17)
      ```bash
        sudo mkdir -p /opt/java
        cd /opt/java
        sudo tar -xvzf ~/OpenJDK17U-jdk_x64_linux_hotspot_17.0.9_9.tar.gz
        sudo mv jdk-17.0.9+9 java-17
        sudo vim /etc/profile.d/java.sh
        ```
      - set of them
      ```bash
        export JAVA_HOME=/opt/java/java-17
        export PATH=$JAVA_HOME/bin:$PATH
        ```
      - `source /etc/profile.d/java.sh`
   3. #### **windows**
      - download [.zip](https://adoptium.net/temurin/releases/?arch=x64&os=windows&package=jdk&version=17)
      - Follow the installation wizard, keeping the default settings.
      - Note the installation path (usually C:\Program Files\Eclipse Adoptium\jdk-17 or C:\Program Files\Java\jdk-17).
      - Open Control Panel → System → Advanced system settings.
      - Go to the Advanced tab and click Environment Variables.
      - Under System Variables
      - Find the `JAVA_HOME` variable (if it does not exist, click New)
      - Set `JAVA_HOME=C:\Program Files\Eclipse Adoptium\jdk-17`
      - Find Path → click Edit → New
      - Set `%JAVA_HOME%\bin`
2. ### maven
   1. [manual](https://www.baeldung.com/install-maven-on-windows-linux-mac#bd-installing-maven-on-mac-os-x) for macOs/linux/windows
3. ### docker
   1. [manual](https://docs.docker.com/desktop/) for macOs/linux/windows

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
      wget https://dlcdn.apache.org//jmeter/binaries/apache-jmeter-5.4.1.tgz &&
      tar -xvzf apache-jmeter-5.4.1.tgz &&
      cd ./apache-jmeter-5.4.1/bin/ &&
      ./jmeter
      ```
   3. **windows**
      - downloads [.zip](https://dlcdn.apache.org//jmeter/binaries/apache-jmeter-5.4.1.zip)
      - unzip .zpi arch
      - run jmeter.bat

## services
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
         - local [grafata](http://localhost:3001/connections/add-new-connection)
         - select prometheus
         - click `add new data source` button
         - set `http://prometheus:9090` into the inputfield "Prometheus server URL"
         - click `Save and test` button onto the futter 
       - **influx** -> go to
         - local [grafana](http://localhost:3001/connections/add-new-connection)
         - select influxdb
         - click `add new data source` button
         - set` http:influxdb:8086` into http part field `URL`
         - on the `InfluxDB Details` part
         - fields:
           - database: `loadTestingDB`
           - user: `admin`
           - password: `admin`
     - **import dashboards:**
       - **for prometheus** 
         - for cAdvisor -> go to
           - local [grafata](http://localhost:3001/dashboards)
           - click `new` button -> `import` button
                - into the `Find and import dashboards` field set id `14282` for [cAdvisor](https://grafana.com/grafana/dashboards/14282-cadvisor-exporter/) dashboard
                - choose prometheus as datasource
                - click the `import` button
                - be happy - that's the most important!!!
         - for node_exporter -> go to
           - local [grafata](http://localhost:3001/dashboards)
           - click `new` button -> `import` button
             - into the `Find and import dashboards` field set id `1860` for [node_exporter](https://grafana.com/grafana/dashboards/1860-node-exporter-full/) dashboard
             - choose prometheus as database
             - click the `import` button
         - for postgres_exporter -> go to
           - local [grafata](http://localhost:3001/dashboards)
           - click `new` button -> `import` button
               - into the `Find and import dashboards` field set id `9628` for [postgres_exporter](https://grafana.com/grafana/dashboards/9628-postgresql-database/) dashboard
               - choose prometheus as database
               - click the `import` button
       - **influx** 
       - standard username: `admin`, password: `admin`
         - for **JMeter** -> go to
           - local [grafata](http://localhost:3001/dashboards)
           - click `new` button -> `import` button
             - into the `Find and import dashboards` field set id `5496` for [Apache JMeter](https://grafana.com/grafana/dashboards/5496-apache-jmeter-dashboard-by-ubikloadpack/) dashboard
             - choose influxDB as datasource
             - click the `import` button
3. #### **prometheus**
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
