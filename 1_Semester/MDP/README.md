# MDP - Discrete Mathematics and Programming - Connect 4 

### Project Description:
A complete Python implementation of the Connect 4 game developed for the Discrete Mathematics and Programming course. The project includes three core components: a game engine, an AI agent, and a text-based interface, all developed according to strict specifications.

### Key Components:

1. **Game Engine (j4_em_linha_motor_48965.py)**
   - Implements all game logic including:
     - 6x7 game board representation
     - Move validation and disc placement
     - Win condition checking (horizontal, vertical, diagonal)
     - Game state management

2. **AI Agent (j4_em_linha_agente_48965.py)**
   - Basic version: Random valid move selection
   - Advanced version: Implements minimax algorithm with:
     - Depth-limited search
     - Alpha-beta pruning
     - Strategic evaluation function

3. **Text Interface (j4_em_linha_modo_texto_48965.py)**
   - Modified version of provided template
   - Handles player input/output
   - Displays current game state
   - Manages turn alternation

### Additional Features:
- **2048 Game Integration**
  - Includes working 2048 game engine
  - Implements ranking system analysis:
    - Player connectivity graphs
    - Score aggregation algorithms
