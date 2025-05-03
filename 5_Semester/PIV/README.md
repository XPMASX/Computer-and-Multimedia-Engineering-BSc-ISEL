# PIV - Image Processing

## TP2 - Surveillance Motion Tracker

### Project Description:
Developed a Python/OpenCV-based algorithm to detect, classify, and track moving objects (people/cars) in surveillance footage. The system identifies active regions, classifies them in real-time, and generates trajectories with unique IDs, outputting an annotated video.

### Key Features:

- **Background Estimation:**  
  Used temporal median filtering to isolate moving objects.
  
- **Morphological Operations:**  
  Applied to refine detected regions (noise reduction, hole filling).
  
- **Object Classification:**  
  Categorized regions as **PERSON**, **CAR**, or **OTHER** based on extracted features (e.g., aspect ratio, area).
  
- **Tracking:**  
  Implemented trajectory generation using a reference method (Fuentes & Velastin, 2001).
  
- **Visualization:**  
  Annotated output video with color-coded bounding boxes and object IDs.

### Technical Stack:

- **Python**, **OpenCV** (for image processing), **Jupyter Notebook**  
- Median filtering, morphological ops (erosion/dilation), contour analysis

## Demonstration:



https://github.com/user-attachments/assets/be43ed46-5004-4e97-a1a4-5d48363410b1


