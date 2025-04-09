# Computer and Multimedia Engineering Bachelor's Degree at ISEL
 
This repository contains all the assignments from my 3-year Bachelor's Degree in Computer and Multimedia Engineering @ ISEL (Instituto Superior de Engenharia de Lisboa) September 2021 - February 2025

It is organized by year, semester and subject. For every subject it was included its 'FUC' (Ficha de Unidade Curricular) which includes the syllabus.

The diagram of the bachelors program:

![LeimPlano](https://github.com/user-attachments/assets/27291093-020e-461b-b279-39a075744854)

# 1st Semester
## MAE - Applied Mathematics to Engineering -  Circuit Trajectory Simulator:
Project developed for the Mathematics Applied to Engineering course, simulates a car moving along a custom-designed circuit. The program ensures the object follows
a parametrized path composed of at least three distinct segments, including straight, parabolic sections and elliptical curves. Key features include adjustable speeds, 
dynamic orientation of the object, and real-time velocity display. Built with Python and Pygame, the project demonstrates parametric equations and trajectory modeling.

## MDP - Discrete Mathematics and Programming - Connect 4 Game Engine & AI:
Developed a complete Connect 4 implementation in Python featuring a game engine with recursive win detection (horizontal/vertical/diagonal pattern matching).
The modular architecture separates core game logic (j4_em_linha_motor.py) from decision-making (j4_em_linha_agente.py), allowing integration with both text-based
and PyGame interfaces. The system implements discrete math concepts for board state evaluation and includes multiple difficulty levels through heuristic optimization.

## SA - Sensors and Actuators - Automated Pet Feeder System:
Project developed for the Sensors and Actuators course, featuring an Arduino-based automated pet feeder that dispenses food when triggered by a button press and validated
by weight detection. The system integrates sensors (ultrasonic distance sensor, button, potentiometer) and actuators (servo motor, LEDs, piezo buzzer) to create an interactive feeding mechanism.
Key functionalities include weight verification, timed access control, and auditory/visual feedback. Developed using TinkerCad for simulation and Arduino IDE for embedded programming, the project
demonstrates practical applications of state machines and sensor-actuator coordination in IoT devices.

## TI - Information Technology - Recursion & Command Line Automation:
Developed Java recursive algorithms for string processing (palindrome detection, first occurrence indexing, and whitespace normalization) 
and created 12 batch scripts for advanced filesystem automation (drive mapping, directory traversal, file operations). Implemented recursive methods with O(n) 
efficiency and designed command-line workflows using relative paths, xcopy operations, and FOR loops with delayed variable expansion.

# 2nd Semester
## CF - Physical Computing - Smart Weather Station with Arduino-Python Integration:
Developed for the Physical Computing course, this project engineered a smart weather station using a state machine architecture to coordinate real-time data flow
between BMP180/GY-89 sensors, an I2C LCD, and a Python GUI. The system leverages three synchronized finite state machines (FSMs) to reliably measure temperature, pressure, and altitude, with updates every 10 seconds.

## MCG - Mathematics for Computer Graphics - 3D Geometric Transformations for Ray Tracing:
Developed for the Mathematics for Computer Graphics course, this Python module implements a Transformacao (Transformation) class to compute 3D geometric
transformations using homogeneous coordinates. Designed to integrate with a ray tracing pipeline, the project applies transformations (translation, scaling, shearing, rotations)
to triangular meshes, which are then rendered into PPM images via a companion RayTracer class. The work demonstrates core graphics principles by manipulating 3D objects in a virtual
scene and validating results through rendered outputs.

## MOP - Modeling and Programming -  Football Competition Simulator:
Developed as part of the Modeling and Programming course, this Java application simulates football leagues and tournaments with a focus on 
object-oriented design, graphical user interfaces (GUI), and persistent data storage in XML. The project demonstrates mastery of software modeling, 
event-driven programming, and data management, offering users an interactive way to simulate and track competitions.

## PDS - Digital Signal Processing - Audio Classification & Reverberation System:
A dual-component project implementing an audio classifier for whistle/handclap detection (85% accuracy using FFT and threshold-based classification) and a digital reverberation
system based on Schröder's acoustic model. Developed signal processing algorithms for feature extraction (spectral peaks, zero-crossing rates) and filter design (comb/allpass filters
with 30-45ms delays). Built with Python, NumPy/SciPy for analysis, and validated through impulse response testing and perceptual evaluation.

# 3rd Semester
## EMP - Entrepreneurship - NICE Headphones: High-Quality Modular Audio Solution:
A business project developed for the Entrepreneurship course, focusing on the creation of NICE Lda, a company dedicated to producing affordable, 
high-fidelity modular headphones. The product features interchangeable components (drivers, ear pads, headbands) and targets audiophiles, gamers, 
and music enthusiasts seeking premium sound at a competitive price.

## FSO - Operating Systems Fundamentals - Robot Control System:
Project developed for the Operating Systems Fundamentals course, featuring a Java-based GUI system to control an EV3 robot through three interconnected modules:
REI (King), SUBDITO (Subject), and GRAVAR (Recorder). The system enables manual/automatic robot control, command logging, and movement sequence recording/playback.

## MSSN - Modelling and Simulation of Natural Systems - Ecosystem Simulation:
Project developed for the Modeling and Simulation of Natural Systems course, featuring an agent-based ecosystem simulation with prey, predators, and herbivores.
The project explored evolutionary dynamics through five progressive simulations, each introducing new traits (speed, mass, vision) or species to analyze emergent 
behaviors and equilibrium states. Implemented in Java with cellular automata for terrain management.

## PCM - Multimedia Authoring - Image Search Application:
Project developed for the Multimedia Content Production course, featuring an interactive web application for searching and filtering digital photos
by keywords or color histograms. Built with HTML5, JavaScript, and XML, the app enables users to browse images from 14 categories and filter by 12 
predefined colors using advanced pixel analysis.

# 4th Semester
## AAV - Animation in Virtual Environments - Cowboy Game:
A cowboy-themed game developed in Unity for the Animation in Virtual Environments course, with the goal of learning the fundamentals
of game development, including player movement, physics interactions, AI behavior, lighting, particle effects, audio, and animation. The
project explores core concepts like scripting, UI design, and environmental effects.

## IASA - Artificial Intelligence for Autonomous Systems - Autonomous Agent Simulation:
Project developed for the Artificial Intelligence for Autonomous Systems course, focusing on the design and implementation of intelligent agents 
capable of autonomous decision-making in dynamic environments. The project explored various AI paradigms, including reactive and deliberative agents, 
state-space search algorithms (DFS, BFS, A*), and Markov Decision Processes (MDPs). Implemented in Python and Java, the simulation demonstrated adaptive
behaviors such as target collection, obstacle avoidance, and optimal pathfinding using heuristic-based planning.

## ICD -  Distributed Computational Infrastructures - Four in a Row LAN:
A full-stack Four in a Row game developed for the Distributed Computational Infrastructures course, built with Java, JavaServer Pages,
JavaScript, and XML. The application supports both console and web-based gameplay, featuring player authentication, profile
customization, real-time matchmaking, and a leaderboard.

## RCP - Computer Networks - Enterprise Network Deployment:
Project developed for the Computer Networks course, focused on deploying and configuring critical network services (DHCP, DNS, and Web) in a simulated
enterprise environment. The network topology included multiple LANs (A, B, and Servers), interconnected via routers with transit links. Key achievements
included automated IP address allocation via DHCP, DNS resolution, and web server accessibility. Implemented using MikroTik routers and
Cisco commands, with validation through ping and Telnet tests.

# 5th Semester
## AA - Machine Learning - Beer Review Analyser:
Developed for the Machine Learning course, this project aimed to analyze beer reviews from the RateBeer dataset to predict sentiment (positive/negative)
and multi-class ratings (smell, taste, overall), using natural language processing (NLP) techniques and machine learning models to classify reviews,
optimizing for accuracy and interpretability.

## IPM - Human Computer Interaction - ISEL Website Usability Testing:
Conducted a comprehensive usability evaluation of a redesigned ISEL university website prototype (developed in Figma) to address pain points in the existing system.
Focused on improving efficiency, learnability, and user satisfaction for 2nd/3rd-year engineering students.

## PIV - Image Processing - Sureveillance Motion Tracker:
Developed a Python/OpenCV-based algorithm to detect, classify, and track moving objects (people/cars) in surveillance footage. 
The system identifies active regions, classifies them in real-time, and generates trajectories with unique IDs, outputting an annotated video.

## SBD - Database Systems - Health Club Management System:
A full-stack application that provides role-specific functionalities for managers, personal trainers, and clients. Final project for the
Database Systems course, built with Java, JavaServer Pages, JavaScript, and MySQL. The system enables managers to oversee club
operations, personal trainers to manage schedules, and clients to enroll in training sessions, among other features.

# 6th Semester
## DAM - Mobile Applications - Video Game Collection Tracker:
Project developed for the Mobile Applications course, allows users to log, rate, and review video games while tracking their gaming history.
Built with Kotlin, Firebase, and the IGDB API.

## IAV - Interaction in Virtual Environments - Endless Runner Game:
Project developed for the Interaction in Virtual Environments course, this Unity game allows players to select an ML-Agents trained car
model that navigates a procedurally generated road, with sound as the primary control mechanism. The agents were trained by each
student in the course using deep reinforcement learning in Python.

## PRJ - Project - Cardiac Rehabilitation Support System:
A full-stack web application designed to provide real-time access to essential health data for both patients and healthcare professionals,
improving the monitoring and management of cardiac rehabilitation. Developed in collaboration with CardioID, the platform enables
personalized rehabilitation plans, patient progress tracking, and remote medical supervision. Final project of the degree built with React,
Falcon and MySQL.

## SMI - Multimedia Systems for the Internet - Velespedia:
Developed for the Internet Multimedia Systems course, this project involved implementing a MediaWiki-based CMS to create a platform similar to Wikipedia, 
but focused on satirical and esoteric content. The system allows users to create, edit, and manage articles, with features like user authentication, 
role-based permissions, and multimedia integration.


