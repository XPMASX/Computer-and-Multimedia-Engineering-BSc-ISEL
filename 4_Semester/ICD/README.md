# ICD - Distributed Computational Infrastructures - Four in a Row LAN
 
### Project Description

A full-stack Four in a Row game developed for the Distributed Computational Infrastructures course, built with Java, JavaServer Pages, JavaScript, and XML. The application supports both console and web-based gameplay, featuring player authentication, profile customization, real-time matchmaking, and a leaderboard.

### Key Features

#### 1. Web Interface & User Management

- **Login/Registration:**
  - JSP forms validated via `LoginServlet` and `RegisterServlet`
  - Singleton `UserDataStore` manages credentials stored in XML

- **Profile Customization:**
  - Color picker sets custom background
  - Nationality dropdown using Bootstrap flags (ISO codes: `PT`, `US`, etc.)

- **Leaderboard:**
  - Ranks top 25 players by number of wins, with average game time as a tiebreaker
  - Autocomplete player search

#### 2. Gameplay Engine

- **Multiplayer Logic:**
  - `JogoServlet` processes player moves via POST requests
  - 30-second move timer enforces activity or disconnects players

- **Cross-Compatibility:**
  - Compatible with major browsers: Chrome, Edge, Firefox, Safari, Opera
  - Supports all combinations: browser↔browser, console↔browser, console↔console

#### 3. Data Handling

- **XML Schema:**
  - Stores wins, losses, total playtime, profile images
  - Extended to include `cor` (color) and ISO nationality codes

- **Triggers & Validation:**
  - Prevents duplicate usernames
  - Handles session timeouts for inactive users

### Technologies Used

- **Frontend:** JSP, HTML/CSS, JavaScript (autocomplete, timers)  
- **Backend:** Java Servlets (Tomcat), Singleton pattern (`UserDataStore`)  
- **Data & UI:** XML for persistent data, Bootstrap for flag icons and styling

## Demonstration:

https://github.com/user-attachments/assets/1f2a47ff-b7f2-41f9-ba50-d9b0e52c6a02



