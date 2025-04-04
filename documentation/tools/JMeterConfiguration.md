how to config your JMeter for comfortable destroy
==================
additional plugin
==================

## How to install JMeter Plugin Manager and essential plugins
---
### 1. INSTALL PLUGIN MANAGER

1. Download Plugin Manager JAR file from: https://jmeter-plugins.org/get/
2. Move the file into your JMeter directory: `~/tools/apache-jmeter-5.4.1/lib/ext/PluginsManager.jar`
3. Restart JMeter
4. After launch, open the menu: `Options → Plugins Manager`
---
### 2. INSTALL REQUIRED PLUGINS
#### 1. Custom Thread Groups
- Go to: `Options → Plugins Manager → Available Plugins`
- Find and install: `Custom Thread Groups`
- Includes:
- Stepping Thread Group
- Concurrency Thread Group
- Arrivals Thread Group
- Plugin page: https://jmeter-plugins.org/?search=jpgc-casutg
---
- Find and install: `Dummy Sampler`
- Useful to mock requests or test logic
- Plugin page: https://jmeter-plugins.org/wiki/DummySampler/
---
- Find and install: `Parallel Controller`  
  (sometimes listed as `bzm - Parallel Controller`)
- Plugin page: https://jmeter-plugins.org/?search=parallel
---

### 3. RESTART JMETER
After installing plugins:
- Restart JMeter to load the new components
- Check for:
- **Custom Thread Groups** under `Thread Groups`
- **Dummy Sampler** under `Samplers`
- **Parallel Controller** under `Logic Controllers`
---

### how start your fist recording on `Test Script Recorder`

#### 0. On TestPlan
- add thread group
- add test script recorder
  - Port: `8888`
  - Target Controller: `Test Plan > Thread Group`
  - Grouping: `Put each group in a new transaction controller`
  - Click `Start` button -> JMeter generated security certificate and put it onto `~/tools/apache-jmeter-5.4.1/bin/`. Try to find `ApacheJMeterTemporaryRootCA.crt`

macOS
------------------

#### 1. SETUP CERTIFICATE
1. Open **Google Chrome**
2. Go to `Settings` → search for `certificates`
3. Navigate to:  
   **Privacy and Security** → **Security** → **Manage Certificates**
4. Click **Manage certificates from macOS**
5. Drag and drop the certificate file from: `~/tools/apache-jmeter-5.4.1/bin/ApacheJMeterTemporaryRootCA.crt`
6. Right-click → **Get Info**
7. Expand the **Trust** section
8. Set **Always trust**

---

#### 2. SETUP PROXY
1. Open **System Settings**
2. Go to **Network**
3. Select your current network → click **Details**
4. Open the **Proxies** tab
5. Enable the following:
  - **Web Proxy (HTTP)**
  - **Secure Web Proxy (HTTPS)**
6. Set both servers to:
  - **Server**: `127.0.0.1`
  - **Port**: `8888`
7. Save the settings

---

#### 3. in Google Chrome
1. Navigate to: `http://127.0.0.1.nip.io`
2. Open **DevTools** (F12) → go to the `Network` tab
3. Apply the filter: `Fetch/XHR`
4. Ensure requests go through JMeter

---

Linux
------------------

#### 1. SETUP CERTIFICATE
1. Open a terminal
2. Run the following command:
   ```bash
   sudo cp ~/tools/apache-jmeter-5.4.1/bin/ApacheJMeterTemporaryRootCA.crt /etc/pki/ca-trust/source/anchors/ &&
   sudo update-ca-trust
   ```

This will install the JMeter certificate into the system trust store

#### 2. SETUP PROXY 
1. Open Settings
2. Go to Network 
3. Select your active connection and click Settings 
4. Open the Proxy tab 
5. Enable Use proxy for this network 
6. Set the following values:
   - **HTTP Proxy**: `0.0.0.0, Port: 8888` 
   - **HTTPS Proxy**: `0.0.0.0, Port: 8888 `
7. Click Save

#### 3. in Firefox
1. Open: http://127.0.0.1.nip.io
2. Open DevTools (F12) → go to the Network tab ,
3. Apply the filter: `Fetch/XHR` 
4. Ensure requests are being captured by JMeter

Windows
------------------

### 1. SETUP CERTIFICATE
1. Open **Google Chrome**
2. Go to `Settings` → search for `certificates`
3. Navigate to:  
   **Privacy and Security** → **Security** → **Manage Certificates**
4. In the window that opens, select the `Trusted Root Certification Authorities` tab
5. Click `Import...`
6. Choose the JMeter certificate located at:  
   `~/apache-jmeter-5.4.1/bin/ApacheJMeterTemporaryRootCA.crt`
7. During the import, select:  
   📂 "Place all certificates in the following store" →  
   ✅ `Trusted Root Certification Authorities`
8. Confirm the installation → click `OK`

### 2. SETUP PROXY
1. Open **Control Panel** → **Internet Options**  
   *(or run `inetcpl.cpl` via Win+R)*
2. Go to the **Connections** tab → click **LAN settings**
3. Check the box: `Use a proxy server for your LAN`
4. Set **Address** to: `127.0.0.1`
5. Set **Port** to: `8888`
6. Click `OK`, then again `OK`

### 3. in Google Chrome
1. Navigate to: `http://127.0.0.1.nip.io`
2. Open **DevTools** (F12) → go to the `Network` tab
3. Apply the filter: `Fetch/XHR`
4. Verify that requests go through JMeter

YOOOOOHOOOO, now you can restart your `Test Script Recorder` and sniff the traffic throw the PROXY

### Command to start JMeter on non GUI mode

notice 
directory `report` must not be created before start the performance test

```bash
source ~/tools/apache-jmeter-5.4.1/bin/jmeter \
-n -t /Path/to/your/test_plan/test_fragment/test_plan_based_on_test_fragments.jmx \
-j /Path/to/your/test_plan/test_fragment/jmeter.log \
-l /Path/to/your/test_plan/test_fragment/results.jtl \
-e -o /Users/michaeltimofeev/projects/service_reservation_doc/examples_docs/test_plan/test_fragment/report/
```
if you try to start test which based on `Test fragment` you must provide the special param
```bash
-Jincludecontroller.prefix=./
```
