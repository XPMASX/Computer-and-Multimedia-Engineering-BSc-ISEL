
# Mathematics for Computer Graphics - 3D Geometric Transformations for Ray Tracing

**Project Overview**  
Developed for the Mathematics for Computer Graphics course, this Python module implements a Transformacao (Transformation) class to compute 3D geometric transformations using homogeneous coordinates. Designed to integrate with a ray tracing pipeline, the project applies transformations (translation, scaling, shearing, rotations) to triangular meshes, which are then rendered into PPM images via a companion RayTracer class. The work demonstrates core graphics principles by manipulating 3D objects in a virtual scene and validating results through rendered outputs.

**Key Features Implemented**  
1. **Core Transformations for Ray Tracing**:  
   - **Translation, Scaling, Shearing, Rotations**: Applied to triangular faces (`FaceTriangular`) to deform or reposition objects in a 3D scene.  
   - **Matrix Composition**: All operations combine into a 4x4 homogeneous matrix, ensuring compatibility with the `RayTracer`'s rendering pipeline.  
   - **Validation via Ray Tracing**: Each transformation was tested by rendering scenes (e.g., translated or rotated objects) into PPM files (`projeto_imagem1.ppm`–`projeto_imagem6.ppm`).  

2. **Integration with Ray Tracer**:  
   - **Scene Manipulation**: Transformed objects (e.g., scaled cubes, rotated pyramids) were passed to the `RayTracer` to generate images, proving correctness.  
   - **Pipeline Synergy**: The `Transformacao` class works with supporting classes (`Matrix`, `Vetor3D`, `FaceTriangular`) to ensure seamless data flow for rendering.  

3. **Technical Execution**:  
   - **Homogeneous Coordinates**: Used to unify linear transformations (critical for ray-object intersection math in the `RayTracer`).  
   - **OOP Design**: Clean separation between transformations (`Transformacao`), geometry (`FaceTriangular`), and rendering (`RayTracer`).  

**What Earned the 13/20 Grade**  
- **Full Basic + Intermediate Implementation**: Covered all transformations up to rotations (missing only projections for higher marks).  
- **Ray Tracing-Powered Validation**: Delivered 6 PPM images, each showing a transformed scene rendered by the `RayTracer`.  
- **Robust Math Foundations**: Matrix operations optimized for performance in a ray tracing context.  

**Technologies Used**  
- **Languages**: Python 3 (custom `Matrix` class for affine transforms).  
- **Graphics**: PPM rendering via `RayTracer` (photon simulation, shading).  
- **Math**: Homogeneous coordinates, cross/dot products for ray-triangle intersections.  

--- 
