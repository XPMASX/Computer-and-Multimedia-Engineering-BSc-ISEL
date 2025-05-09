# Modeling and Programming - Football Competition Simulator  

### Project Description:  
A Java application simulating football leagues and tournaments, featuring XML data storage, GUI interfaces, and dynamic match generation. The system models competitions (leagues/tournaments) with teams, matches, and rankings, providing both manual and automated simulation modes.  

---

### Key Features  

#### 1. **Competition Types**  
   - **League:** Round-robin format with 8 teams; points-based ranking.  
   - **Tournament:** Knockout format with 4 competitions (min. 2 leagues); winner advances.  

#### 2. **Core Functionality**  
   - **Match Simulation:**  
     - Manual input (`det_jogo`) or auto-generated results (`sim_jogo`) using weighted randomness (higher-scoring teams favored).  
     - Real-time updates to team stats (goals, points, GD).  
   - **Data Persistence:**  
     - XML storage via DOM/XPath (e.g., `<Liga comp="L"><Equipa adeptos="20000">SCP</Equipa></Liga>`).  
     - DTD validation ensures structured data.  

#### 3. **GUI Interface**  
   - **Swing-based UI:**  
     - `JOptionPane` dialogs for user input.  
     - Dynamic tables for league standings.  
     - Visual brackets for tournaments and podium/trophy displays.  

#### 4. **Class Hierarchy**  
   - **`Competicao` (Abstract Parent):**  
     - Shared methods: `get_random_golo`, `sim_jogo`, `det_jogo`.  
   - **`Liga` & `Torneio` (Child Classes):**  
     - League: Implements `Tabela_final` for ranking.  
     - Tournament: Handles knockout phases via `sim_todos`.  

---

### Technical Implementation  

#### **Key Classes**  
- **`Pontuacao`:** Tracks team stats (wins, goals, GD) with validation.  
- **`Equipa`:** Represents teams (name, stadium, fan count) and links to `Pontuacao`.  
- **`Main`:** Orchestrates competition creation and XML serialization.  

#### **XML Workflow**  
1. **DTD Schema:** Defines valid elements (e.g., `<!ELEMENT Torneio (Nome, Liga+, Torneio?)>`).  
2. **XPath Queries:** Fetch teams/competitions from XML (e.g., `build_liga`).  
3. **DOM Manipulation:** `createElement` generates XML nodes for teams/competitions.  
---

### Technologies Used  
- **Java** (Swing, DOM/XPath)  
- **XML** (DTD validation)  
- **UML** (Class diagrams)  

---

### Key Achievements  
- Unified league/tournament logic via abstract class `Competicao`.  
- Interactive GUI with visual feedback (tables, brackets).  
- Robust XML integration for data persistence.  

---
