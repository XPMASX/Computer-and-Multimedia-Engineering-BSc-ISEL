# IASA - Artificial Intelligence for Autonomous Systems - Autonomous Agent Simulation
 
### Project Description:
A comprehensive AI agent framework developed for the *Artificial Intelligence for Autonomous Systems* course, exploring fundamental AI concepts through practical implementations. The project covers reactive and deliberative agents, state-space search algorithms, Markov Decision Processes (MDPs), and reinforcement learning principles. Implemented in Java and Python.

###  Key Features

#### 1. Reactive Agent Systems

- **Animal Tracking Game (Java):**  
  - Finite state machine (FSM) with states: Search, Inspection, Observation, Registration  
  - Event-driven interactions: noise detection, animal appearance, photography  

- **Obstacle Avoidance Agent (Python):**  
  - Priority-based behaviors: Target collection > Obstacle avoidance > Random exploration  
  - Stimulus-response architecture using `EstimuloAlvo` (target proximity) and `EstimuloObst` (collision detection)

#### 2. Deliberative Agents & Search Algorithms

- **State-Space Search Implementations:**  
  - *Uninformed:* DFS, BFS, Depth-Limited, Iterative Deepening  
  - *Informed:* Greedy Search, A* (admissible heuristics), Uniform Cost Search  

- **Pathfinding Applications:**  
  - Optimal route planning (e.g., Loc-0 → Loc-4 using UCS)  
  - Sequence rearrangement (e.g., [3,6,4,2,5,1] → [1,2,3,4,5,6] using A*)

#### 3. Markov Decision Processes (MDPs)

- **Policy Optimization:**  
  - Utility maximization via Bellman equations  
  - Discount factor γ = 0.85 for balancing short/long-term rewards  

- **Simulator Testing:**  
  - Grid-world navigation with dynamic rewards and obstacles  
  - Policy visualization using directional arrows for optimal actions

#### 4. Reinforcement Learning (Theory-Only)

- **Q-Learning & SARSA Concepts:**  
  - ε-greedy strategy (exploration vs. exploitation)  
  - Reward shaping for sequential decision tasks

### Technologies & Methodologies

- **Languages:** Java (FSM game), Python (AI agents)  
- **Algorithms:** A*, UCS, Greedy, DFS/BFS, MDPs  
- **Tools:** Custom simulators for agent testing  
- **Design Principles:**  
  - Modular architecture: `controlo_react`, `pdm`, `pee`  
  - Admissible heuristics for A*  
  - Policy iteration for MDPs

### Key Achievements

- Demonstrated trade-offs between search algorithms (A* > UCS/Greedy for pathfinding)  
- Enabled robust MDP-based planning for stochastic scenarios  
- Integrated theory (UML, FSM, PEE/PDM architecture) with practice

## Planning and Space Exploration Demonstration:


https://github.com/user-attachments/assets/fe5d2a6e-df8c-442b-9b0d-5da93aa2c8b7

