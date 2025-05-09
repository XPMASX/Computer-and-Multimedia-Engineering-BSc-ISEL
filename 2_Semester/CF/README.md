# Physical Computing - Smart Weather Station with Arduino-Python Integration

### Project Description:  
An **IoT weather station** combining Arduino-based sensor data acquisition with a Python GUI dashboard. The system monitors **temperature, pressure, and altitude** using BMP180/GY-89 sensors, displays real-time data on an LCD, and visualizes it via a PyGame interface with dynamic graphics.  

---

### Key Features  

#### **1. Hardware Setup**  
- **Sensors:** BMP180 (pressure/temperature) + GY-89 (altitude) via **I2C** protocol.  
- **Display:** 16x2 LCD with I2C backpack for local readouts.  
- **Communication:** Serial link (UART) between Arduino and Python GUI.  

#### **2. State Machine Architecture**  
- **Three Concurrent State Machines:**  
  - **Main Controller (`app()`):** Orchestrates sensor polling (every 10s).  
  - **Temperature/Pressure Modules:** Handle sensor-specific calculations (e.g., `calculatorTemperatura()`).  
- **Timed Transitions:** Non-blocking delays using `millis()`.  

#### **3. Python GUI**  
- **Dynamic Visualization:**  
  - **Thermometer Animation:** 26 image states mapped to temperature ranges (*Figure 9*).  
  - **Color Theory Applied:** High-contrast orange/blue palette for outdoor visibility (*Figure 8*).  
- **Serial Protocol:** Tagged data transmission (e.g., `"T25.5T"` for temperature) parsed via `pySerial`.  

#### **4. Data Processing**  
- **Sensor Calibration:** Compensation algorithms for raw BMP180 readings.  
- **Altitude Calculation:** Barometric formula with sea-level reference.  

---

### Challenges & Solutions  
- **Sensor Synchronization:** State machines ensure non-blocking operation.  
- **Data Corruption:** Tagged serial protocol (`T/P/A` prefixes) prevents misreads.  
- **GUI Performance:** PyGame’s `blit()` optimizes image updates.  

---

### Results  
- **Accuracy:** ±0.5°C (temperature), ±1 hPa (pressure).  
- **Update Rate:** 10-second intervals.  
- **User Feedback:** Intuitive dashboard with time/date display.  

---

### Technologies Used  
- **Arduino C++** (I2C, UART, state machines)  
- **Python** (PyGame, pySerial)  
- **Electronics:** BMP180, GY-89, I2C LCD  

---
