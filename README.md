# Sorting Algorithm Performance Evaluator

## 📌 Project Overview
This project is a Java-based application developed for the **Programming III** module at the **University of Colombo**.  
The application allows users to upload a CSV dataset, select a numeric column, apply multiple sorting algorithms, and evaluate their performance based on execution time.

---

## 🎯 Objectives
- Upload and read CSV datasets
- Select numeric columns for sorting
- Apply multiple sorting algorithms
- Measure execution time of each algorithm
- Identify and highlight the best-performing algorithm
- Present results in a user-friendly GUI

---

## 🛠 Technologies Used
- **Language:** Java  
- **GUI Framework:** JavaFX (FXML)  
- **Build Tool:** Maven  
- **File Handling:** CSV  
- **Performance Measurement:** `System.nanoTime()`

---

## 📂 Project Structure
# Sorting Algorithm Performance Evaluator

## 📌 Project Overview
This project is a Java-based application developed for the **Programming III** module at the **University of Colombo**.  
The application allows users to upload a CSV dataset, select a numeric column, apply multiple sorting algorithms, and evaluate their performance based on execution time.

---

## 🎯 Objectives
- Upload and read CSV datasets
- Select numeric columns for sorting
- Apply multiple sorting algorithms
- Measure execution time of each algorithm
- Identify and highlight the best-performing algorithm
- Present results in a user-friendly GUI

---

## 🛠 Technologies Used
- **Language:** Java  
- **GUI Framework:** JavaFX (FXML)  
- **Build Tool:** Maven  
- **File Handling:** CSV  
- **Performance Measurement:** `System.nanoTime()`

---

## 📂 Project Structure
src/main/java/com/sortingapp/sortingalgorithmevaluator/
│
├── Main.java
├── controllers/
│ └── MainController.java
├── algorithms/
│ ├── InsertionSort.java
│ ├── ShellSort.java
│ ├── MergeSort.java
│ ├── QuickSort.java
│ ├── HeapSort.java
│ └── SortingAlgorithm.java
├── utils/
│ └── FileHandler.java
├── models/
│ ├── CSVData.java
│ └── SortingResult.java


---

## ⚙️ Implemented Sorting Algorithms
- Insertion Sort
- Shell Sort
- Merge Sort
- Quick Sort
- Heap Sort

---

## 📊 Performance Evaluation
- Execution time measured using `System.nanoTime()`
- Each algorithm runs on identical data
- Best-performing algorithm is highlighted

---

## ▶️ How to Run the Project
1. Clone the repository
2. Open the project in an IDE (IntelliJ IDEA / Eclipse)
3. Ensure JavaFX is configured
4. Run `Main.java`
5. Upload a CSV file
6. Select a numeric column and click **Sort & Compare**

---

## 👥 Team Members & Contributions
- **Yoshani (Leader)**  
  - UI/UX design  
  - Merge Sort & Heap Sort  
  - Project coordination  

- **Deesara**  
  - Project setup & structure  
  - Quick Sort  
  - Performance evaluation  
  - Data preprocessing & integration  

- **Thakshila**  
  - CSV handling & validation  
  - Shell Sort & Insertion Sort  
  - Result presentation  
  - Documentation  

---

## ✅ Notes
- Only numeric columns are sortable
- Invalid inputs are handled gracefully
