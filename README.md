# service_reservation
this project was build for performance testing courses.  don't judge strictly


install infra

for linux
https://docs.docker.com/engine/install/centos/#install-using-the-repository

CentOS/RHEL

```bash
sudo dnf -y install dnf-plugins-core &&
sudo dnf config-manager --add-repo https://download.docker.com/linux/centos/docker-ce.repo &&
sudo dnf install docker-ce docker-ce-cli containerd.io docker-buildx-plugin docker-compose-plugin &&
sudo systemctl enable --now docker
```

notes
Error permission denied while trying to connect to the Docker daemon socket at unix:///var/run/docker.sock: Get "http://%2Fvar%2Frun%2Fdocker.sock/v1.48/containers/json?all=1": dial unix /var/run/docker.sock: connect: permission denied

```bash
sudo groupadd docker &&
sudo usermod -aG docker $USER &&
newgrp docker &&
reboot
```


into Grafana

connect prometheus db on grafana:

go to -> /connections/add-new-connection
select prometheus db
hit add new data source btn
set http:127.0.0.1:9090 to the inputfield "Prometheus server URL *"

dashboards
- [cAdvisor](https://grafana.com/grafana/dashboards/14282-cadvisor-exporter/) : 14282
- [node_exporter](https://grafana.com/grafana/dashboards/1860-node-exporter-full/) : 1860