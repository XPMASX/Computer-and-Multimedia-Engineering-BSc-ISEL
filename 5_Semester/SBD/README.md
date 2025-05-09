# SBD - Database Systems - Health Club Management System

### Project Description:
A comprehensive health club management system developed for the *Database Systems* course, designed to streamline operations for managers, personal trainers, and clients. Built using JSP (JavaServer Pages), MySQL, and Eclipse IDE, the system emphasizes multi-role access, data integrity, and operational analytics.

### Key Features

#### 1. Database Design & Implementation

- **Entity-Relationship Model:**  
  - 15+ entities (e.g., `Clube`, `Cliente`, `PersonalTrainer`)  
  - Weak entity support and complex relationships  
  - Examples:  
    - 1:N → `Clube` → `Salas`  
    - M:N → `Clientes` ↔ `Recomendações`

- **Relational Schema:**  
  - 16 normalized tables with composite keys (e.g., `ManchaDisponibilidade(idMancha, Telemovel)`)  
  - Foreign key constraints (e.g., `Cliente.NIF → Recomendacao.NIF`)

- **Triggers:**  
  - `validar_horario_manha`: Ensures availability slots match club hours  
  - `CheckRecomendacao`: Prevents duplicate trainer recommendations

#### 2. Role-Specific Functionality

- **Managers:**  
  - Configure rooms and equipment (supports multimedia uploads)  
  - Import/export client profiles via XML  
  - View quarterly reports (e.g., top 5 least-used equipment)  
  - Generate weekly heatmaps for room occupancy

- **Personal Trainers:**  
  - Publish age-specific availability (`ManchaDisponibilidade`)  
  - Search client profiles with autocomplete  
  - Recommend equipment based on client conditions/goals

- **Clients:**  
  - Enroll in individual or group sessions  
  - View trainer recommendations  
  - Access weekly activity calendar

### Technologies Used

- **Backend:** JSP, Java Servlets  
- **Database:** MySQL 8.0 (with triggers and FK constraints)  
- **Frontend:** HTML, CSS, JavaScript (autocomplete)  

### Demonstration


https://github.com/user-attachments/assets/8f2033b7-0129-42af-8b01-5101767c5216



