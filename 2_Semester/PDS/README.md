# Digital Signal Processing - Audio Classification and Reverberation System  

### Project Description:  
A practical implementation of digital signal processing (DSP) techniques for **automatic audio classification** (whistles vs. claps) and **artificial reverberation** using Schroeder’s model. Developed for the Digital Signal Processing course, this project combines time/frequency domain analysis with filter design.  

---

### Key Features  

#### **1. Automatic Audio Classification**  
- **Target Classes:** Whistles and claps.  
- **Feature Extraction:**  
  - **Time Domain:** Statistical parameters (e.g., RMS amplitude, zero-crossing rate).  
  - **Frequency Domain:** Spectral centroid, bandwidth, FFT-based energy distribution.  
- **Classifier:**  
  - Threshold-based or machine learning (e.g., SVM) trained on extracted features.  
- **Pitch Detection (Whistles):**  
  - Autocorrelation or cepstrum analysis to estimate fundamental frequency.  
- **Validation:**  
  - Precision/recall metrics tested on a dedicated dataset.  

#### **2. Artificial Reverberation**  
- **Schroeder’s Model:**  
  - **Comb Filters (FBCF):**  
    - Transfer function: \( H(z) = \frac{1}{1 - gz^{-N}} \) (delays: 30–45 ms).  
  - **All-Pass Filters (AP):**  
    - Transfer function: \( H(z) = \frac{-g + z^{-N}}{1 - gz^{-N}} \) (delays: 1.7–5 ms).  
- **Room Simulation:**  
  - Tuned parameters (\( g_i \), \( D_i \)) emulate small rooms (dense reverb) or concert halls (long decay).  
- **Testing:**  
  - Applied to voice recordings; evaluated via perceptual listening tests.  

---

### Technologies Used  
- **Python** (NumPy, SciPy, Librosa, Scikit-learn)  
- **MATLAB** (Validation of filter responses)  

---
