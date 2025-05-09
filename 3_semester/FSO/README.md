# FSO - Operating Systems Fundamentals - Robot Control System
 
### Project Description:  
A multi-process Java application simulating the game "The King Commands," where a **King process** sends movement instructions to a **Subject process** controlling a LEGO EV3 robot. The system includes a **Recording task** to save/replay command sequences, implemented with thread synchronization, circular buffers, and semaphores.  

---

### Key Features  

#### 1. **Multi-Process Architecture**  
   - **King Process:** Generates commands (forward, turn, stop) with randomized parameters or manual input.  
   - **Subject Process:** Executes commands on the robot via Bluetooth using the LEGO EV3 API.  
   - **Recording Task:** Logs commands to files with timestamps for playback, enabling automated robot control.  

#### 2. **Thread Synchronization**  
   - **Circular Buffer:** Shared between King and Subject processes for thread-safe command transmission.  
   - **Semaphores:** Ensure mutual exclusion (`acessoElemento`) and manage buffer slots (`livreMyMensagem`, `ocupadaMyMensagem`).  

#### 3. **State Machines**  
   - **King:** Sleep → Send (if buffer not full) → Sleep.  
   - **Subject:** Sleep → Receive → Execute (if robot connected) → Sleep.  
   - **Recording:** Sleep → Log commands to file (during recording) or replay from file (with timed delays).  

#### 4. **GUI Control**  
   - Base GUI extended for King/Subject/Recording tasks.  
   - Real-time logs and toggle buttons for process activation/deactivation.  

---

### Technical Implementation  

#### **Core Components**  
- **BufferCircular:** Thread-safe FIFO queue for command transmission.  
  ```java
  public void insertElemento(Mensagem m) {
      livre.acquire();  // Wait for free slot
      acessoElemento.acquire();
      buffer[in] = m;
      in = (in + 1) % size;
      acessoElemento.release();
      ocupada.release();  // Signal new element
  }
  ```  
- **Mensagem Class:** Serializes commands (type, distance, angle, timestamp).  
- **RobotEV3 Spy:** Extends `RobotEV3` to intercept commands for recording.  

#### **State Management**  
- **Blocking Mechanism:** Semaphores freeze tasks when toggled off in the main GUI.  
  ```java
  case blocked:
      gui.txtLog.append("Blocked\n");
      bloqueado.acquire();  // Wait for GUI toggle
      state = sleep;
      break;
  ```  

#### **Recording Workflow**  
1. **Logging:** Commands saved as `type,arg1,arg2,timestamp` (e.g., `1,20,0,163456789`).  
2. **Playback:** Commands replayed with original timing using `Thread.sleep()`.  

---

### Technologies Used  
- **Java** (Threads, Semaphores, Swing GUI)  
- **LEGO EV3 API** for robot control.  
- **MagicDraw** for UML diagrams (class/activity).  

---

### Key Achievements  
- Achieved real-time command synchronization between 3 concurrent tasks.  
- Implemented file-based command replay with precise timing.  
- Demonstrated thread-safe resource sharing via semaphores.  

--- 
