# MSSN - Modelling and Simulation of Natural Systems - Ecosystem Simulation

### Project Description:  
Project developed for the Modeling and Simulation of Natural Systems course, featuring an agent-based ecosystem simulation with prey, predators, and herbivores. The project explored evolutionary dynamics through five progressive simulations, each introducing new traits (speed, mass, vision) or species to analyze emergent behaviors and equilibrium states. Implemented in Java with cellular automata for terrain management.

### Key Features  

- **Agent-Based Ecosystem:**  
  - **Prey Agents:** Wander terrain, consume food (energy +4), reproduce at energy ≥25, and lose energy based on metabolic rules.  
  - **Predators & Herbivores:** Added in later stages, with unique energy requirements and behaviors.  

- **Evolutionary Traits:**  
  - **Speed:** Mutates (±0.2 per generation; energy loss = mass × velocity²).  
  - **Mass:** Affects metabolism (energy loss = mass³ × velocity²) and cannibalism (prey eat smaller prey).  
  - **Vision:** Enables targeted movement ("Seek" behavior); wider vision increases energy cost.  

- **Dynamic Terrain:**  
  - 45×60 grid with 40-43% food cells (regenerates after consumption).  
  - Configurations adjusted for species balance (e.g., 57% empty cells for predator-prey scenarios).  

- **Five Simulations:**  
  1. **Speed:** Baseline with prey and speed mutations.  
  2. **Mass:** Introduces size-based cannibalism.  
  3. **Vision:** Adds directional foraging/hunting.  
  4. **Predators:** Tests ecosystem stability with apex species.  
  5. **Herbivores:** Balances food chains with a third species.  

### Key Findings  

- **Speed & Terrain:** Dense food clusters favored slower, energy-efficient prey.  
- **Mass:** Larger prey dominated resource-rich zones; smaller prey thrived in dispersed food areas.  
- **Vision:** Reduced randomness, stabilized populations (~60-70 agents), and enabled sustainable ecosystems.  
- **Predators:** Achieved temporary equilibrium but risked extinction (70% predator dominance).  
- **Herbivores:** Enhanced stability (7+ minutes runtime) by providing alternate food sources.  

### Conclusion  
The project demonstrated emergent behaviors (e.g., cannibalism decline with vision) and validated theoretical models (e.g., metabolic trade-offs). Proposed improvements include fine-tuning herbivore parameters to extend equilibrium beyond 10 minutes.  

---  
