
---

# Cardiac Rehabilitation Support System

The **Cardiac Rehabilitation Support System** project aims to design and implement a software solution that provides both **patients** and **healthcare professionals** with real-time access to essential information for **monitoring and managing cardiac rehabilitation** from any location.

This project enables:

- **Patients** to view and track their rehabilitation progress, interact with personalized rehabilitation plans and questionnaires, and submit them for approval by healthcare professionals.
- **Doctors** to create personalized rehabilitation plans and questionnaires, monitor patient progress, review and approve rehabilitation plans, and provide feedback on patient activities.
- **Heartbeat and Step Monitoring**: The system allows the monitoring of patient heartbeats and step counts. Although this data is intended to be gathered through wearables, that functionality was not implemented in time by the company. As a result, the development team simulated the heartbeat and step data using **Perlin noise**, and this data is handled within the **server_projeto** module.

### System Overview

The solution is divided into four key components:

- **Frontend**: Developed using **React** for an intuitive and responsive user interface.
- **Backend**: Built with **Falcon** to manage communication between the front-end, database, and external APIs.
- **Database**: A **MySQL** database for storing patient data, rehabilitation plans, and doctor feedback.
- **API**: Integration with the CardioID API, which is provided by the company for communication with AWS services, hence it is not present in this repository.

The system architecture is divided into four main modules:

1. **connheart-front-end-web/**: React code for the front-end interface.
2. **database/**: The MySQL database storing user data, including both patient and doctor interactions.
3. **isel-cheart-api-2024/**: The CardioID API for communication with AWS services, provided by the company and not included in this repository.
4. **server_projeto/**: Falcon-based Python server for communication between the database, front-end, and API. This module also contains the simulated heartbeat and step data generated using Perlin noise.

### Included Documentation
The project also includes the **report** and **presentation**, which provide further details on the development process, functionality, and objectives of the Cardiac Rehabilitation Support System. The report is in Portuguese, while the presentation is in English.

## Academic Information

| **Bachelor's Project #54 2023/24** |
| :---------------------------------- |
| [Licenciatura em Engenharia Informática e Multimédia (LEIM)](https://www.isel.pt/curso/licenciatura/licenciatura-em-engenharia-informatica-e-multimedia) |
| [Departamento de Engenharia Eletrónica e Telecomunicações e de Computadores (DEETC)](https://www.isel.pt/departamento/engenharia-electronica-e-telecomunicacoes-e-de-computadores) |
| [Instituto Superior de Engenharia de Lisboa (ISEL)](https://www.isel.pt/) |
| [Instituto Politécnico de Lisboa (IPL)](https://www.ipl.pt/) |

### Contributors

| Carried out by           | Pedro Silva 48965, Cláudia Sequeira 49247 & Maria Joana Pires 49778 |
| :----------------------- | :---------------------------------------------------------------- |
| Collaboration with        | [CardioID](https://www.cardio-id.com/) |
| **Advisors**              | Eng. Rui Jesus & Eng. André Lourenço |
| **Discussant**            | Eng. Carlos Gonçalves |


## Demonstration

### Questionnaire:

https://github.com/user-attachments/assets/d5273291-6a9c-488e-898c-5998f949dc42

### Plans:

https://github.com/user-attachments/assets/b9413f46-1dfd-4ae2-985c-a6bc5d0269e5

### Dashboard:


https://github.com/user-attachments/assets/6449ac63-9c70-41f1-b896-29917250e74f

---
