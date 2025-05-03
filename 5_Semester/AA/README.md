# AA - Machine Learning - Beer Review Analyser

### Project Description: 
Developed for the Machine Learning course, this project aimed to analyze beer reviews from the RateBeer dataset to predict sentiment (positive/negative) and multi-class ratings (smell, taste, overall). The team employed natural language processing (NLP) techniques and machine learning models to classify reviews, optimizing for accuracy and interpretability.

### Key Features:

- **Binary Classification:**  
  Predicts whether a review is *very good* (score ≥ 9) or *very bad* (score ≤ 2) using Logistic Regression and Naive Bayes.

- **Multi-Class Classification:**  
  Predicts numerical ratings for smell, taste, and overall experience (limited success due to dataset constraints).

### NLP Pipeline:

- Text preprocessing (tokenization, stop-word removal, stemming)  
- Bag-of-Words (BoW) with TF-IDF weighting and n-grams (1–4 words)  
- Identified key positive terms (e.g., *chocolate*, *caramel*) and negative terms (e.g., *watery*, *metallic*)

### Model Evaluation:

- Binary classification achieved:
  - **92% accuracy** with Logistic Regression  
  - **90% accuracy** with Naive Bayes  
- Multi-class classification yielded suboptimal results due to dataset limitations  
- PCA used for dimensionality reduction:
  - Reduced from **6,718 features → 81 components**  
  - Minimal accuracy drop: **93% → 91%**

###  Technologies Used:

- Python (scikit-learn, NLTK, pandas), Jupyter Notebook
- NLP techniques: Bag-of-Words, TF-IDF, n-grams  
- ML models: Logistic Regression, Naive Bayes  
- PCA for feature reduction
