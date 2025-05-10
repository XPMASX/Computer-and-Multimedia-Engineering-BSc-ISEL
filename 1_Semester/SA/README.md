# Sensors and Actuators - Automated Pet Feeder System

### Project Description:  
An Arduino-based automated pet feeder system developed for the Sensors and Actuators course. The project combines sensor inputs 
(ultrasonic distance measurement, button press) with actuator outputs (servo motor, LEDs, piezo buzzer) to create an interactive feeding system that rewards pets for following trained behaviors.  

---

### Key Features  

#### **Interactive Feeding Mechanism**  
- **Weight Simulation:**  
  Uses a potentiometer to simulate weight measurement (`autoPeso()`). Valid weight range triggers a green LED; invalid weight lights a red LED.  
- **Button Press Requirement:**  
  Pets must press a button (`button()`) when the weight is valid to activate the feeder.  
- **Servo-Controlled Dispenser:**  
  Servo motor (`autoServo()`) opens/closes the food dispenser when conditions are met.  

#### **Visual/Auditory Feedback**  
- **LED Indicators:**  
  - Green LED: Valid weight detected.  
  - Red LED: Invalid weight or system idle.  
- **Piezo Buzzer:**  
  Emits sound when the button is pressed (success) or when the system resets (`cancela()`).  

#### **Distance Monitoring (Sonar)**  
- Ultrasonic sensor ensures the pet is in the correct position before dispensing food.  

---

### Technologies & Components  
- **Hardware:**  
  - Arduino Uno  
  - Servo motor (food gate)  
  - HC-SR04 ultrasonic sensor  
  - Piezo buzzer (active)  
  - Potentiometer (weight simulation)  
  - Push button, LEDs (feedback)  
- **Software:**  
  - Arduino IDE (C/C++)  
  - TinkerCAD (circuit simulation)  

---
