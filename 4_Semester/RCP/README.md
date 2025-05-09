# RCP - Computer Networks - Enterprise Network Deployment

### Project Description: 
Project developed for the *Computer Networks* course, focused on deploying and configuring critical network services (DHCP, DNS, and Web) in a simulated enterprise environment. The network topology included multiple LANs (A, B, and Servers), interconnected via routers with transit links. Key achievements included automated IP address allocation via DHCP, DNS resolution for `www.company.com`, and web server accessibility. Implemented using MikroTik routers and Cisco commands, with validation through ping and Telnet tests.

### Key Features & Technologies

- **DHCP Server:**  
  - Configured to dynamically assign IPs to:  
    - LAN A: `192.168.5.128/26`  
    - LAN B: `192.168.5.192/27`  
  - Used relay agents on routers to handle cross-subnet DHCP requests.

- **DNS Server:**  
  - Resolved `www.company.com` to the web server’s IP: `192.168.5.3`  
  - Enabled domain-based access across the network.

- **Web Server:**  
  - Hosted the default RouterOS web page  
  - Accessible via HTTP on port 80.

- **Testing & Validation:**  
  - Verified DHCP leases: `VPCS> dhcp`  
  - Confirmed DNS resolution: `ping www.company.com`  
  - Tested HTTP access: `telnet www.company.com 80`
